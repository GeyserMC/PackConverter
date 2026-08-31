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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/** Extracts client resource-pack data from Minecraft mod JARs. */
public final class ModJarExtractor {
    private static final String ASSETS_PREFIX = "assets/";
    private static final int MAX_ENTRIES = 100_000;
    private static final long MAX_ENTRY_BYTES = 256L * 1024 * 1024;
    private static final long MAX_TOTAL_BYTES = 2L * 1024 * 1024 * 1024;

    private ModJarExtractor() {
    }

    /** Immutable result of a deterministic multi-mod extraction. */
    public record ExtractionReport(@NotNull List<Path> mods, int filesExtracted,
                                   @NotNull List<String> collisions) {
        public ExtractionReport {
            mods = List.copyOf(mods);
            collisions = List.copyOf(collisions);
        }
    }

    /** Returns whether the supplied path looks like a mod JAR. */
    public static boolean isModJar(@NotNull Path path) {
        String fileName = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return fileName.endsWith(".jar") || fileName.endsWith(".jarx");
    }

    /** Returns whether a directory contains one or more mod JARs directly inside it. */
    public static boolean isModDirectory(@NotNull Path path) throws IOException {
        if (!Files.isDirectory(path)) return false;
        try (var files = Files.list(path)) {
            return files.anyMatch(ModJarExtractor::isModJar);
        }
    }

    /** Extracts one mod JAR, including its pack metadata, into a resource-pack directory. */
    public static @NotNull Path extract(@NotNull Path jar, @NotNull Path destination) throws IOException {
        extractInternal(jar, destination, new LinkedHashSet<>(), new LinkedHashSet<>(), true, new ExtractionBudget());
        return destination.toAbsolutePath().normalize();
    }

    /**
     * Extracts every mod JAR directly inside a directory in deterministic filename order.
     * Multi-mod extraction intentionally merges only {@code assets/}. Pack-level metadata
     * such as {@code pack.mcmeta} and {@code pack.png} belongs to the generated pack and
     * must not be selected accidentally from whichever mod happens to sort last.
     */
    public static @NotNull ExtractionReport extractAll(@NotNull Path directory, @NotNull Path destination) throws IOException {
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
        Set<String> extracted = new LinkedHashSet<>();
        Set<String> collisions = new LinkedHashSet<>();
        ExtractionBudget budget = new ExtractionBudget();
        int filesExtracted = 0;
        for (Path jar : jars) {
            filesExtracted += extractInternal(jar, destination, extracted, collisions, false, budget);
        }
        return new ExtractionReport(jars, filesExtracted, new ArrayList<>(collisions));
    }

    private static int extractInternal(Path jar, Path destination, Set<String> extracted,
                                       Set<String> collisions, boolean includePackMetadata,
                                       ExtractionBudget budget) throws IOException {
        Path root = destination.toAbsolutePath().normalize();
        Files.createDirectories(root);
        int count = 0;

        try (InputStream input = Files.newInputStream(jar); ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String name = entry.getName().replace('\\', '/');
                if (entry.isDirectory() || !isResourcePackEntry(name, includePackMetadata)) continue;
                if (++count > MAX_ENTRIES) throw new IOException("Mod JAR contains too many resource entries");
                if (entry.getSize() > MAX_ENTRY_BYTES) throw new IOException("Mod JAR entry is too large: " + name);

                Path target = root.resolve(name).normalize();
                if (!target.startsWith(root)) {
                    throw new IOException("Unsafe mod JAR entry: " + name);
                }

                Path parent = target.getParent();
                if (parent != null) Files.createDirectories(parent);
                if (!extracted.add(name)) collisions.add(name);
                long entryBytes = copyEntry(zip, target, name);
                budget.totalBytes += entryBytes;
                if (budget.totalBytes > MAX_TOTAL_BYTES) {
                    Files.deleteIfExists(target);
                    throw new IOException("Mod JAR resources exceed the extraction limit for this invocation");
                }
            }
        }
        return count;
    }

    private static long copyEntry(InputStream input, Path target, String name) throws IOException {
        long written = 0;
        byte[] buffer = new byte[8192];
        try (OutputStream output = Files.newOutputStream(target)) {
            int read;
            while ((read = input.read(buffer)) != -1) {
                written += read;
                if (written > MAX_ENTRY_BYTES) {
                    throw new IOException("Mod JAR entry is too large: " + name);
                }
                output.write(buffer, 0, read);
            }
        } catch (IOException exception) {
            Files.deleteIfExists(target);
            throw exception;
        }
        return written;
    }

    private static boolean isResourcePackEntry(String name, boolean includePackMetadata) {
        if (name.startsWith(ASSETS_PREFIX)) return true;
        return includePackMetadata && (name.equals("pack.mcmeta") || name.equals("pack.png"));
    }

    private static final class ExtractionBudget {
        private long totalBytes;
    }
}
