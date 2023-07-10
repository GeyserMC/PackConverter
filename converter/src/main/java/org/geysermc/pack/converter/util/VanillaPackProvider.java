/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.PathUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class VanillaPackProvider {
    private static final String JAR_DOWNLOAD = "https://piston-data.mojang.com/v1/objects/%s/client.jar";
    private static final String JAR_HASH = "0c3ec587af28e5a785c0b4a7b8a30f9a8f78f838";

    /**
     * Downloads the vanilla jar from Mojang's servers.
     *
     * @param path The path to download the jar to.
     */
    public static void create(@NotNull Path path, @NotNull LogListener log) {
        // Jar already exists; do nothing
        if (Files.exists(path)) {
            log.debug("Vanilla jar already exists, skipping download");
            return;
        }

        try {
            // Download vanilla jar
            log.info("Downloading vanilla jar...");
            PathUtils.copyFile(new URL(String.format(JAR_DOWNLOAD, JAR_HASH)), path);
            log.info("Downloaded vanilla jar successfully!");

            // Clean the jar
            clean(path, log);
        } catch (IOException e) {
            log.error("Error downloading vanilla jar", e);
        }
    }

    /**
     * Strips the jar of all files that are not needed for pack conversion
     * and cleans up the jar of any potentially problematic files.
     *
     * @param jarPath The path to the jar to strip and clean.
     */
    private static void clean(@NotNull Path jarPath, @NotNull LogListener log) throws IOException {
        ZipUtils.openFileSystem(jarPath, true, rootPath -> {
            // Copy the builtin assets into the MC jar
            InputStream builtinEntity = VanillaPackProvider.class.getResourceAsStream("/vanilla/builtin/entity.json");
            InputStream builtinGenerated = VanillaPackProvider.class.getResourceAsStream("/vanilla/builtin/generated.json");

            Path builtinModelsDirectory = rootPath.resolve("assets/minecraft/models/builtin");
            if (!Files.exists(builtinModelsDirectory)) {
                Files.createDirectories(builtinModelsDirectory);
            }

            Files.write(builtinModelsDirectory.resolve("entity.json"), IOUtils.toByteArray(builtinEntity));
            Files.write(builtinModelsDirectory.resolve("generated.json"), IOUtils.toByteArray(builtinGenerated));

            try (Stream<Path> paths = Files.walk(rootPath)) {
                paths.forEach(path -> {
                    try {
                        if (Files.isDirectory(path)) {
                            return;
                        }

                        // At the moment, we only care about models and blockstate info from vanilla.
                        String pathName = path.toString();
                        if (!pathName.startsWith("/assets/minecraft/models") && !pathName.startsWith("/assets/minecraft/blockstates")) {
                            PathUtils.delete(path);
                            return;
                        }

                        // Fix a bug where the wrong cullface is set for scaffolding
                        if (pathName.endsWith("scaffolding_unstable.json")) {
                            try (BufferedReader reader = Files.newBufferedReader(path)) {
                                String line;
                                StringBuilder builder = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    builder.append(line).append("\n");
                                }

                                String json = builder.toString();
                                json = json.replace("\"cullface\": \"bottom\"", "\"cullface\": \"down\"");

                                Files.write(path, json.getBytes());
                            }
                        }
                    } catch (IOException e) {
                        log.error("Error stripping vanilla jar", e);
                    }
                });
            }
        });
    }
}
