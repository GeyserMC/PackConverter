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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.PathUtils;
import org.geysermc.pack.converter.type.texture.transformer.type.OverlayTransformer;
import org.geysermc.pack.converter.type.texture.transformer.type.entity.SheepTransformer;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class VanillaPackProvider {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    private static final Map<String, Asset> ASSET_MAP = new HashMap<>();

    private static final List<String> REQUIRED_ASSETS = List.of(); // While not used yet, it's possible we will need other assets as some point

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
            log.info("Fetching vanilla jar file download...");
            // Get the version manifest from Mojang
            VersionManifest versionManifest = GSON.fromJson(
                    WebUtils.getBody("https://launchermeta.mojang.com/mc/game/version_manifest.json"), VersionManifest.class);

            // Get the url for the latest version of the games manifest
            String latestInfoURL = "";
            for (Version version : versionManifest.getVersions()) {
                if (version.getId().equals("1.21.7")) { // TODO De-hardcode this
                    latestInfoURL = version.getUrl();
                    break;
                }
            }

            // Make sure we definitely got a version
            if (latestInfoURL.isEmpty()) {
                throw new IOException("Unable to find a valid version!");
            }

            // Get the individual version manifest
            VersionInfo versionInfo = GSON.fromJson(WebUtils.getBody(latestInfoURL), VersionInfo.class);

            // Get the client jar for use when downloading the en_us locale
            log.debug(GSON.toJson(versionInfo.getDownloads()));
            VersionDownload clientJarInfo = versionInfo.getDownloads().get("client");
            log.debug(GSON.toJson(clientJarInfo));

            JsonObject assets = JsonParser.parseString(WebUtils.getBody(versionInfo.getAssetIndex().getUrl())).getAsJsonObject().get("objects").getAsJsonObject();

            // Put each asset into an array for use later
            for (Map.Entry<String, JsonElement> entry : assets.entrySet()) {
                if (!REQUIRED_ASSETS.contains(entry.getKey())) {
                    // No need to cache this asset, we don't use it
                    continue;
                }

                Asset asset = GSON.fromJson(entry.getValue(), Asset.class);
                ASSET_MAP.put(entry.getKey(), asset);
            }

            log.info("Downloading vanilla jar...");

            if (path.getParent() != null) Files.createDirectories(path.getParent());

            PathUtils.copyFile(new URL(clientJarInfo.url), path);
            // Clean the jar
            clean(path, log);
            log.info("Downloaded vanilla jar!");
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

            if (builtinEntity != null) {
                Files.write(builtinModelsDirectory.resolve("entity.json"), IOUtils.toByteArray(builtinEntity));
            } else {
                log.error("`entity.json` was not found. Continuing without, issues may occur!");
            }

            if (builtinGenerated != null) {
                Files.write(builtinModelsDirectory.resolve("generated.json"), IOUtils.toByteArray(builtinGenerated));
            } else {
                log.error("`generated.json` was not found. Continuing without, issues may occur!");
            }

            try (Stream<Path> paths = Files.walk(rootPath)) {
                paths.forEach(path -> {
                    try {
                        if (Files.isDirectory(path)) {
                            return;
                        }

                        List<String> validPaths = new ArrayList<>();

                        for (OverlayTransformer.OverlayData overlayData : OverlayTransformer.OVERLAYS) {
                            validPaths.add("/assets/minecraft/textures/" + overlayData.javaName());
                            validPaths.add("/assets/minecraft/textures/" + overlayData.overlay());
                        }

                        validPaths.add("/assets/minecraft/textures/" + SheepTransformer.SHEEP);
                        validPaths.add("/assets/minecraft/textures/" + SheepTransformer.SHEEP_WOOL);
                        validPaths.add("/assets/minecraft/textures/" + SheepTransformer.SHEEP_UNDERCOAT);
                        validPaths.add("/assets/minecraft/textures/misc/unknown_pack.png");

                        // At the moment, we only care about models and blockstate info from vanilla.
                        String pathName = path.toString();
                        if (
                                !pathName.startsWith("/assets/minecraft/models") &&
                                !pathName.startsWith("/assets/minecraft/blockstates") &&
                                !pathName.startsWith("/assets/minecraft/textures/font") &&
                                !validPaths.contains(pathName)
                        ) {
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

            for (Map.Entry<String, Asset> asset : ASSET_MAP.entrySet()) {
                String bytes2 = asset.getValue().hash.substring(0, 2);

                PathUtils.copyFile(
                        new URL("https://resources.download.minecraft.net/%s/%s"
                                .formatted(bytes2, asset.getValue().hash)),
                        rootPath.resolve("assets/" + asset.getKey())
                );
            }
        });
    }

    @Getter
    static class VersionManifest {
        private LatestVersion latest;

        private List<Version> versions;
    }

    @Getter
    static class LatestVersion {
        private String release;

        private String snapshot;
    }

    @Getter
    static class Version {
        private String id;

        private String type;

        private String url;

        private String time;

        private String releaseTime;
    }

    @Getter
    static class VersionInfo {
        private String id;

        private String type;

        private String time;

        private String releaseTime;

        private AssetIndex assetIndex;

        private Map<String, VersionDownload> downloads;
    }

    @Getter
    static class VersionDownload {
        private String sha1;

        private int size;

        private String url;
    }

    @Getter
    static class AssetIndex {
        private String id;

        private String sha1;

        private int size;

        private int totalSize;

        private String url;
    }

    @Getter
    public static class Asset {
        private String hash;

        private int size;
    }
}
