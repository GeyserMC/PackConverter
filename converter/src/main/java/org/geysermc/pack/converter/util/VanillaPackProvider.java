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
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class VanillaPackProvider {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    private static final Map<String, Asset> ASSET_MAP = new HashMap<>();

    private static final List<String> REQUIRED_ASSETS = List.of(); // While not used yet, it's possible we will need other assets as some point

    public static final String DEFAULT_VERSION = "26.2";

    /**
     * Downloads the vanilla jar from Mojang's servers using {@link #DEFAULT_VERSION}.
     *
     * @param path The path to download the jar to.
     */
    public static void create(@NotNull Path path, @NotNull LogListener log) {
        create(path, DEFAULT_VERSION, log);
    }

    /**
     * Downloads the vanilla jar from Mojang's servers.
     *
     * @param path The path to download the jar to.
     * @param minecraftVersion The Minecraft version to download.
     */
    public static void create(@NotNull Path path, @NotNull String minecraftVersion, @NotNull LogListener log) {
        Path versionPath = path.resolveSibling(path.getFileName() + ".version");
        String cachedVersion = readCachedVersion(versionPath);

        if (Files.exists(path)) {
            // Jar already exists and was built from the requested version; do nothing
            if (minecraftVersion.equals(cachedVersion)) {
                log.debug("Vanilla pack already exists, skipping download");
                return;
            }

            log.info("Vanilla pack was built from %s but %s was requested, re-downloading..."
                    .formatted(cachedVersion == null ? "an unknown version" : cachedVersion, minecraftVersion));
        }

        try {
            // Download vanilla jar
            log.info("Fetching vanilla jar file download...");
            // Get the version manifest from Mojang
            VersionManifest versionManifest = GSON.fromJson(
                    WebUtils.getBody("https://launchermeta.mojang.com/mc/game/version_manifest.json"), VersionManifest.class);

            // Get the url for the requested version of the games manifest
            String latestInfoURL = "";
            for (Version version : versionManifest.getVersions()) {
                if (version.getId().equals(minecraftVersion)) {
                    latestInfoURL = version.getUrl();
                    break;
                }
            }

            // Make sure we definitely got a version
            if (latestInfoURL.isEmpty()) {
                throw new IOException("Unable to find the version '" + minecraftVersion + "'!");
            }

            log.info("Using Minecraft version " + minecraftVersion);

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

            // Drop the old version first so a failure part way through isn't mistaken for a good jar
            Files.deleteIfExists(versionPath);

            PathUtils.copyFile(new URL(clientJarInfo.url), path, StandardCopyOption.REPLACE_EXISTING);
            // Clean the jar
            clean(path, log);
            Files.writeString(versionPath, minecraftVersion);
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

                        validPaths.add("/pack.png");
                        validPaths.add("/version.json"); // Kept so it can be converted into a pack.mcmeta below

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

            // The client jar has no pack.mcmeta of its own, so build one from version.json
            Path versionJson = rootPath.resolve("version.json");
            if (Files.exists(versionJson)) {
                JsonObject version = JsonParser.parseString(Files.readString(versionJson)).getAsJsonObject();

                JsonObject pack = new JsonObject();
                pack.addProperty("pack_format", version.getAsJsonObject("pack_version").get("resource_major").getAsInt());
                pack.addProperty("description", version.get("name").getAsString());

                JsonObject root = new JsonObject();
                root.add("pack", pack);

                Files.writeString(rootPath.resolve("pack.mcmeta"), GSON.toJson(root));
                Files.delete(versionJson);
            } else {
                log.error("`version.json` was not found. Continuing without, issues may occur!");
            }
        });

        moveMetaToFront(jarPath);
    }

    /**
     * Rewrites the jar with its {@code pack.mcmeta} first, dropping directory entries.
     * <p>
     * We have to write the pack.mcmeta first otherwise creative doesn't read it until last
     *
     * @param jarPath The path to the jar to reorder.
     */
    private static void moveMetaToFront(@NotNull Path jarPath) throws IOException {
        Path tempPath = jarPath.resolveSibling(jarPath.getFileName() + ".tmp");

        try {
            try (ZipFile jar = new ZipFile(jarPath.toFile());
                 ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(tempPath))) {
                ZipEntry meta = jar.getEntry("pack.mcmeta");
                if (meta == null) {
                    return;
                }

                // Directory entries aren't needed, and the strip above leaves plenty of empty ones behind
                List<ZipEntry> entries = new ArrayList<>();
                entries.add(meta);
                jar.stream()
                        .filter(entry -> !entry.isDirectory() && !entry.getName().equals(meta.getName()))
                        .forEach(entries::add);

                for (ZipEntry entry : entries) {
                    out.putNextEntry(new ZipEntry(entry.getName()));

                    try (InputStream in = jar.getInputStream(entry)) {
                        IOUtils.copy(in, out);
                    }

                    out.closeEntry();
                }
            }

            Files.move(tempPath, jarPath, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(tempPath);
        }
    }

    /**
     * Reads the Minecraft version a previously downloaded jar was built from.
     *
     * @param versionPath The path to the version file.
     * @return the version, or {@code null} if it couldn't be determined.
     */
    @Nullable
    private static String readCachedVersion(@NotNull Path versionPath) {
        if (!Files.exists(versionPath)) {
            return null;
        }

        try {
            return Files.readString(versionPath).trim();
        } catch (IOException e) {
            return null;
        }
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
