/*
 * Copyright (c) 2019-2025 GeyserMC. http://geysermc.org
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
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.type.entity.gecko;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.AssetCombiner;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.AssetExtractor;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.pipeline.ExtractionContext;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Converts GeckoLib {@code .animation.json} files into Bedrock actor animations.
 * <p>
 * Like {@link GeckoLibModelConverter} for geometry, this is mostly a structural
 * re-serialization: GeckoLib's animation format was designed to mirror Bedrock's
 * own actor animation format, so bones, channels, keyframes and molang
 * expressions carry across unchanged. The converter only strips constructs that
 * cannot resolve on a Bedrock client and normalises the few shorthand forms
 * that older Bedrock parsers reject.
 * <p>
 * <b>Known limitations of this first version:</b>
 * <ul>
 *     <li>{@code particle_effects} and {@code sound_effects} entries are removed -
 *     they reference Java Edition particles/sounds that do not exist on Bedrock.</li>
 *     <li>Timeline molang that calls Java-only queries is passed through untouched
 *     and may silently no-op on Bedrock.</li>
 *     <li>Nothing is validated against a real Bedrock client yet.</li>
 * </ul>
 */
public record GeckoAnimationConverter() implements AssetExtractor<GeckoAnimationAsset>,
        AssetConverter<GeckoAnimationAsset, GeckoAnimationAsset>, AssetCombiner<GeckoAnimationAsset> {

    public static final GeckoAnimationConverter INSTANCE = new GeckoAnimationConverter();

    private static final String FORMAT_VERSION = "1.8.0";
    private static final String ANIMATION_SUFFIX = ".animation.json";
    private static final Gson GSON = new Gson();

    private static final Set<String> BONE_CHANNELS = Set.of("rotation", "position", "scale");

    @Override
    public Collection<GeckoAnimationAsset> extract(ResourcePack pack, ExtractionContext context) {
        List<GeckoAnimationAsset> assets = new ArrayList<>();

        for (Map.Entry<String, Writable> entry : pack.unknownFiles().entrySet()) {
            String path = entry.getKey();
            if (!path.endsWith(ANIMATION_SUFFIX) || !path.contains("/animations/")) {
                continue;
            }

            // Expected shape: assets/<namespace>/animations/[.../]<fileName>.animation.json
            String[] parts = path.split("/", 3);
            if (parts.length < 3 || !parts[0].equals("assets")) {
                context.warn("Skipping GeckoLib animation at unexpected path: " + path);
                continue;
            }
            String namespace = parts[1];
            String fileName = path.substring(path.lastIndexOf('/') + 1, path.length() - ANIMATION_SUFFIX.length);

            try {
                JsonElement parsed = com.google.gson.JsonParser.parseString(entry.getValue().toUTF8String());
                if (!parsed.isJsonObject()) {
                    context.warn("Skipping GeckoLib animation that is not a JSON object: " + path);
                    continue;
                }
                assets.add(new GeckoAnimationAsset(namespace, fileName, parsed.getAsJsonObject()));
            } catch (IOException | JsonParseException e) {
                context.warn("Failed to parse GeckoLib animation at " + path + ": " + e.getMessage());
            }
        }

        return assets;
    }

    @Override
    public GeckoAnimationAsset convert(GeckoAnimationAsset asset, ConversionContext context) throws Exception {
        JsonObject source = asset.animation();
        if (source == null || !source.has("animations") || !source.get("animations").isJsonObject()) {
            context.debug("GeckoLib animation " + asset.fileName() + " has no animations, skipping");
            return null;
        }

        // Deep copy so the extracted asset stays untouched for other consumers.
        JsonObject result = GSON.fromJson(source, JsonObject.class);
        JsonObject animations = result.getAsJsonObject("animations");

        if (result.has("format_version") && result.get("format_version").isJsonPrimitive()) {
            result.addProperty("format_version", result.get("format_version").getAsString());
        } else {
            result.addProperty("format_version", FORMAT_VERSION);
        }

        for (Map.Entry<String, JsonElement> animationEntry : animations.entrySet()) {
            if (!animationEntry.getValue().isJsonObject()) {
                context.warn("Animation " + animationEntry.getKey() + " in " + asset.fileName() + " is malformed, skipping entry");
                continue;
            }
            normalizeAnimation(animationEntry.getKey(), animationEntry.getValue().getAsJsonObject(), context);
        }

        return new GeckoAnimationAsset(asset.namespace(), asset.fileName(), result);
    }

    private void normalizeAnimation(String name, JsonObject animation, ConversionContext context) {
        if (animation.has("particle_effects")) {
            animation.remove("particle_effects");
            context.warn("Animation " + name + ": particle_effects removed (Java-only particles cannot resolve on Bedrock)");
        }
        if (animation.has("sound_effects")) {
            animation.remove("sound_effects");
            context.warn("Animation " + name + ": sound_effects removed (Java-only sounds cannot resolve on Bedrock)");
        }

        JsonElement bonesElement = animation.get("bones");
        if (bonesElement == null || !bonesElement.isJsonObject()) {
            return;
        }

        for (Map.Entry<String, JsonElement> boneEntry : bonesElement.getAsJsonObject().entrySet()) {
            if (!boneEntry.getValue().isJsonObject()) {
                continue;
            }
            JsonObject bone = boneEntry.getValue().getAsJsonObject();
            for (String channel : BONE_CHANNELS) {
                normalizeChannel(name, boneEntry.getKey(), channel, bone, context);
            }
        }
    }

    private void normalizeChannel(String animationName, String boneName, String channel, JsonObject bone, ConversionContext context) {
        JsonElement value = bone.get(channel);
        if (value == null) {
            return;
        }

        // Constant shorthand ("rotation": [0, 0, 45] or a molang string) is valid
        // modern Bedrock but rejected by some older parsers - anchor it at t=0.
        if (value.isJsonArray() || value.isJsonPrimitive()) {
            JsonObject anchored = new JsonObject();
            anchored.add("0.0", value);
            bone.add(channel, anchored);
            return;
        }

        // Keyframed form: {"0.0": [..], "0.5": {"pre": [..], "post": [..]}} - the
        // per-keyframe shapes already match Bedrock, nothing to rewrite.
        if (value.isJsonObject() && value.getAsJsonObject().size() == 0) {
            context.debug("Animation " + animationName + " bone " + boneName + " has an empty " + channel + " channel");
        }
    }

    @Override
    public void include(BedrockResourcePack pack, List<GeckoAnimationAsset> animations, CombineContext context) {
        List<String> seen = new ArrayList<>();
        for (GeckoAnimationAsset animation : animations) {
            if (animation == null) {
                continue;
            }
            String fileName = animation.namespace() + "." + animation.fileName() + ".animation.json";
            if (seen.contains(fileName)) {
                context.warn("Conflicting GeckoLib animation " + fileName + "!");
                continue;
            }
            seen.add(fileName);
            pack.addExtraFile(animation.animation(), "animations/" + fileName);
        }
    }
}
