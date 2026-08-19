/*
 * Copyright (c) 2026 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.geysermc.pack.converter.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Extracts the resource-pack portion of Minecraft mod JARs.
 *
 * <p>Mod JARs commonly keep their client resources under {@code assets/} rather
 * than being distributed as standalone Java resource packs. PackConverter can
 * consume those resources directly without requiring the user to unpack JARs
 * first.</p>
 */
public final class ModJarExtractor {
    private static final String ASSETS_PREFIX = "assets/";

    private ModJarExtractor() {
    }

    /** Returns whether the supplied path looks like a mod JAR. */
    public static boolean isModJar(@NotNull Path path) {
        String fileName = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return fileName.endsWith(".jar") || fileName.endsWith(".jarx");
    }

    /**
     * Returns whether a directory contains one or more mod JARs directly inside it.
     * Resource-pack directories are intentionally not classified as mod directories
     * unless at least one JAR is present.
     */
    public static boolean isModDirectory(@NotNull Path path) throws IOException {
        if (!Files.isDirectory(path)) return false;
        try (var files = Files.list(path)) {
            return files.anyMatch(ModJarExtractor::isModJar);
        }
    }

    /**
     * Extracts one mod JAR. Existing files are replaced so this method can also
     * participate in deterministic multi-mod overlays.
     */
    public static @NotNull Path extract(@NotNull Path jar, @NotNull Path destination) throws IOException {
        Path root = destination.toAbsolutePath().normalize();
        Files.createDirectories(root);

        try (InputStream input = Files.newInputStream(jar); ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String name = entry.getName().replace('\\', '/');
                if (entry.isDirectory() || !isResourcePackEntry(name)) continue;

                Path target = root.resolve(name).normalize();
                if (!target.startsWith(root)) {
                    throw new IOException("Unsafe mod JAR entry: " + name);
                }

                Path parent = target.getParent();
                if (parent != null) Files.createDirectories(parent);
                Files.copy(zip, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        }

        return root;
    }

    /**
     * Extracts every mod JAR directly inside a directory into one merged resource
     * tree. JARs are sorted by normalized filename, making the overlay deterministic;
     * later files win when two mods provide the same resource path.
     *
     * @return the list of processed JARs, in overlay order
     */
    public static @NotNull List<Path> extractAll(@NotNull Path directory, @NotNull Path destination) throws IOException {
        Path root = directory.toAbsolutePath().normalize();
        if (!Files.isDirectory(root)) throw new IOException("Not a mod directory: " + directory);

        List<Path> jars;
        try (var files = Files.list(root)) {
            jars = files.filter(Files::isRegularFile)
                    .filter(ModJarExtractor::isModJar)
                    .sorted(Comparator.comparing(path -> path.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .toList();
        }

        if (jars.isEmpty()) throw new IOException("No mod JARs found in: " + directory);
        Files.createDirectories(destination);
        for (Path jar : jars) extract(jar, destination);
        return List.copyOf(jars);
    }

    private static boolean isResourcePackEntry(String name) {
        return name.equals("pack.mcmeta")
                || name.equals("pack.png")
                || name.startsWith(ASSETS_PREFIX);
    }
}
