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
     * Serialises cache creation: consumers may warm the cache concurrently with
     * the conversion-time call, and two simultaneous downloads would corrupt
     * the shared cache file.
     */
    private static final Object DOWNLOAD_LOCK = new Object();

    /**
     * Downloads the vanilla jar from Mojang's servers, resolving the version
     * from the {@code packconverter.vanillaVersion} system property or, when
     * unset, the latest release.
     *
     * @param path The path to download the jar to.
     * @param log the log listener
     */
    public static void create(@NotNull Path path, @NotNull LogListener log) {
        create(path, System.getProperty("packconverter.vanillaVersion", ""), log);
    }

    /**
     * Downloads the vanilla jar from Mojang's servers for the given version.
     * An empty {@code vanillaVersion} resolves to the latest release. The
     * cache is invalidated automatically when the requested version changes -
     * a jar cached for a different Minecraft version produces parent-model
     * lookups that silently fail against mods built for the running version.
     *
     * @param path The path to download the jar to.
     * @param vanillaVersion the Minecraft version to fetch, or empty for latest release
     * @param log the log listener
     */
    public static void create(@NotNull Path path, @NotNull String vanillaVersion, @NotNull LogListener log) {
        Path versionMarker = path.resolveSibling(path.getFileName() + ".version");

        synchronized (DOWNLOAD_LOCK) {
        // With an explicit version, an up-to-date cache can skip all network access.
        if (!vanillaVersion.isEmpty() && Files.isRegularFile(path) && Files.isRegularFile(versionMarker)) {
            try {
                if (Files.readString(versionMarker).trim().equals(vanillaVersion)) {
                    log.debug("Vanilla jar for " + vanillaVersion + " already cached, skipping download");
                    return;
                }
            } catch (IOException ignored) {
                // Fall through to a full re-download.
            }
        }

        try {
            // Get the version manifest from Mojang
            VersionManifest versionManifest = GSON.fromJson(
                    WebUtils.getBody("https://launchermeta.mojang.com/mc/game/version_manifest.json"), VersionManifest.class);

            if (vanillaVersion.isEmpty()) {
                vanillaVersion = versionManifest.getLatest().getRelease();
            }

            if (Files.isRegularFile(path) && Files.isRegularFile(versionMarker)) {
                try {
                    String cachedVersion = Files.readString(versionMarker).trim();
                    if (cachedVersion.equals(vanillaVersion)) {
                        log.debug("Vanilla jar for " + vanillaVersion + " already cached, skipping download");
                        return;
                    }
                    log.info("Cached vanilla jar is for " + cachedVersion + ", re-downloading for " + vanillaVersion);
                } catch (IOException ignored) {
                    // Fall through and re-download.
                }
            }

            // Get the url for the requested version's manifest
            log.info("Fetching vanilla jar file download for " + vanillaVersion + "...");
            String latestInfoURL = "";
            for (Version version : versionManifest.getVersions()) {
                if (version.getId().equals(vanillaVersion)) {
                    latestInfoURL = version.getUrl();
                    break;
                }
            }

            // Make sure we definitely got a version
            if (latestInfoURL.isEmpty()) {
                throw new IOException("Unable to find version " + vanillaVersion + " in the Mojang manifest!");
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

            WebUtils.downloadToFile(clientJarInfo.url, path, clientJarInfo.sha1, clientJarInfo.size);
            // Clean the jar
            clean(path, log);
            Files.writeString(versionMarker, vanillaVersion);
            log.info("Downloaded vanilla jar for " + vanillaVersion + "!");
        } catch (IOException e) {
            log.error("Error downloading vanilla jar", e);
        }
        }
    }

    /**
     * Downloads an unmodified vanilla client jar for runtime model extractors.
     * The resource-pack cache is intentionally stripped, so it cannot supply
     * client rendering classes.
     */
    public static void createClientRuntime(@NotNull Path path, @NotNull String vanillaVersion, @NotNull LogListener log) {
        Path marker = path.resolveSibling(path.getFileName() + ".version");
        synchronized (DOWNLOAD_LOCK) {
            try {
                if (Files.isRegularFile(path) && Files.isRegularFile(marker)
                        && Files.readString(marker).trim().equals(vanillaVersion)) {
                    return;
                }
                VersionManifest manifest = GSON.fromJson(
                        WebUtils.getBody("https://launchermeta.mojang.com/mc/game/version_manifest.json"), VersionManifest.class);
                String versionUrl = manifest.getVersions().stream()
                        .filter(version -> version.getId().equals(vanillaVersion))
                        .map(Version::getUrl).findFirst()
                        .orElseThrow(() -> new IOException("Unable to find Minecraft " + vanillaVersion));
                VersionInfo info = GSON.fromJson(WebUtils.getBody(versionUrl), VersionInfo.class);
                VersionDownload client = info.getDownloads().get("client");
                if (client == null) throw new IOException("Minecraft " + vanillaVersion + " has no client download");
                if (path.getParent() != null) Files.createDirectories(path.getParent());
                log.info("Downloading Minecraft client runtime for entity model extraction...");
                WebUtils.downloadToFile(client.getUrl(), path, client.getSha1(), client.getSize());
                Files.writeString(marker, vanillaVersion);
            } catch (IOException | RuntimeException e) {
                log.warn("Runtime entity extraction is unavailable: " + e.getMessage());
            }
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

                        // Modern sign templates use element rotation angles beyond
                        // the [-45, 45] range the model deserializer accepts; the
                        // rejected file then cascades into missing-parent errors for
                        // every modded sign. Clamp so the templates deserialize.
                        if (pathName.startsWith("/assets/minecraft/models/block/template_")
                                && pathName.contains("sign")
                                && pathName.endsWith(".json")) {
                            try (BufferedReader reader = Files.newBufferedReader(path)) {
                                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                                if (clampModelAngles(root)) {
                                    Files.write(path, GSON.toJson(root).getBytes());
                                }
                            }
                        }
                    } catch (IOException e) {
                        log.error("Error stripping vanilla jar", e);
                    }
                });
            }

            for (Map.Entry<String, Asset> asset : ASSET_MAP.entrySet()) {
                String bytes2 = asset.getValue().hash.substring(0, 2);

                WebUtils.downloadToFile(
                        "https://resources.download.minecraft.net/%s/%s".formatted(bytes2, asset.getValue().hash),
                        rootPath.resolve("assets/" + asset.getKey()), asset.getValue().hash, asset.getValue().size);
            }
        });
    }

    /**
     * Clamps every element rotation angle in the model to the deserializer's
     * accepted [-45, 45] range.
     *
     * @param model the parsed model json
     * @return {@code true} if any angle was rewritten
     */
    private static boolean clampModelAngles(@NotNull JsonObject model) {
        boolean changed = false;
        JsonElement elements = model.get("elements");
        if (elements == null || !elements.isJsonArray()) {
            return false;
        }

        for (JsonElement element : elements.getAsJsonArray()) {
            if (!element.isJsonObject()) {
                continue;
            }
            JsonObject elementObj = element.getAsJsonObject();
            JsonElement rotation = elementObj.get("rotation");
            if (rotation == null || !rotation.isJsonObject() || !rotation.getAsJsonObject().has("angle")) {
                continue;
            }

            JsonObject rotationObj = rotation.getAsJsonObject();
            try {
                float angle = rotationObj.get("angle").getAsFloat();
                if (angle > 45f) {
                    rotationObj.addProperty("angle", 45f);
                    changed = true;
                } else if (angle < -45f) {
                    rotationObj.addProperty("angle", -45f);
                    changed = true;
                }
            } catch (NumberFormatException | UnsupportedOperationException ignored) {
                // Not a numeric angle - leave it for the deserializer to reject.
            }
        }
        return changed;
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
