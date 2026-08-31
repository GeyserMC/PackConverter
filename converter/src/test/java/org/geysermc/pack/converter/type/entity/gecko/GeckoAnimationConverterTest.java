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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.AssetConverters;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeckoAnimationConverterTest {

    private static final String SAMPLE_ANIMATION_JSON = """
            {
              "format_version": "1.8.0",
              "animations": {
                "animation.example_mob.walk": {
                  "animation_length": 1.5,
                  "loop": true,
                  "bones": {
                    "body": {
                      "rotation": [0, 0, 5],
                      "position": {"0.0": [0, 1, 0], "0.75": [0, 2, 0]},
                      "scale": 1.25
                    },
                    "head": {
                      "rotation": {"0.0": [0, 0, 0], "0.5": [0, 30, 0]}
                    }
                  },
                  "particle_effects": {
                    "walk_dust": "example_mob:dust"
                  },
                  "sound_effects": {
                    "step": {"sound": "example_mob:step"}
                  }
                }
              }
            }
            """;

    @TempDir
    Path tempDir;

    @Test
    void productionConverterListIncludesAnimationPipelineOnlyWhenExperimentalFeaturesAreEnabled() {
        assertTrue(AssetConverters.converters(true).contains(AssetConverters.GECKO_ANIMATION));
        assertFalse(AssetConverters.converters(false).contains(AssetConverters.GECKO_ANIMATION));
    }

    @Test
    void convert_anchorsShorthandChannelsAtTimeZero() throws Exception {
        GeckoAnimationAsset result = convertSample();
        JsonObject bones = result.animation()
                .getAsJsonObject("animations")
                .getAsJsonObject("animation.example_mob.walk")
                .getAsJsonObject("bones");

        // Constant array shorthand becomes a keyframe anchored at 0.0
        JsonObject bodyRotation = bones.getAsJsonObject("body").getAsJsonObject("rotation");
        assertTrue(bodyRotation.has("0.0"));
        assertEquals("[0,0,5]", bodyRotation.getAsJsonArray("0.0").toString());

        // Constant scalar shorthand is anchored the same way
        JsonObject bodyScale = bones.getAsJsonObject("body").getAsJsonObject("scale");
        assertEquals("1.25", bodyScale.get("0.0").getAsString());

        // Already-keyframed channels are left untouched
        JsonObject bodyPosition = bones.getAsJsonObject("body").getAsJsonObject("position");
        assertEquals("[0,1,0]", bodyPosition.getAsJsonArray("0.0").toString());
        assertEquals("[0,2,0]", bodyPosition.getAsJsonArray("0.75").toString());
        JsonObject headRotation = bones.getAsJsonObject("head").getAsJsonObject("rotation");
        assertEquals("[0,30,0]", headRotation.getAsJsonArray("0.5").toString());
    }

    @Test
    void convert_stripsJavaOnlyEffectReferences() throws Exception {
        GeckoAnimationAsset result = convertSample();
        JsonObject animation = result.animation()
                .getAsJsonObject("animations")
                .getAsJsonObject("animation.example_mob.walk");

        assertFalse(animation.has("particle_effects"));
        assertFalse(animation.has("sound_effects"));
        // Core metadata survives the cleanup
        assertEquals(1.5f, animation.get("animation_length").getAsFloat());
        assertEquals("true", animation.get("loop").getAsString());
    }

    @Test
    void convert_defaultsFormatVersion_whenMissing() throws Exception {
        JsonObject raw = JsonParser.parseString(SAMPLE_ANIMATION_JSON).getAsJsonObject();
        raw.remove("format_version");

        GeckoAnimationAsset result = GeckoAnimationConverter.INSTANCE.convert(
                new GeckoAnimationAsset("testmod", "example_mob", raw), testContext());

        assertEquals("1.8.0", result.animation().get("format_version").getAsString());
    }

    @Test
    void convert_returnsNull_whenNoAnimations() throws Exception {
        GeckoAnimationAsset result = GeckoAnimationConverter.INSTANCE.convert(
                new GeckoAnimationAsset("testmod", "empty", new JsonObject()), testContext());

        assertNull(result);
    }

    @Test
    void convert_doesNotMutateTheSourceAsset() throws Exception {
        JsonObject raw = JsonParser.parseString(SAMPLE_ANIMATION_JSON).getAsJsonObject();
        GeckoAnimationAsset source = new GeckoAnimationAsset("testmod", "example_mob", raw);

        GeckoAnimationConverter.INSTANCE.convert(source, testContext());

        // The original document still holds the effects and the shorthand channel
        assertTrue(source.animation()
                .getAsJsonObject("animations")
                .getAsJsonObject("animation.example_mob.walk")
                .has("particle_effects"));
        assertTrue(source.animation()
                .getAsJsonObject("animations")
                .getAsJsonObject("animation.example_mob.walk")
                .getAsJsonObject("bones")
                .getAsJsonObject("body")
                .get("rotation").isJsonArray());
    }

    @Test
    void include_writesAnimationUnderBedrockAnimationsDirectory() throws Exception {
        GeckoAnimationAsset converted = convertSample();
        BedrockResourcePack pack = new BedrockResourcePack(tempDir);

        GeckoAnimationConverter.INSTANCE.include(pack, List.of(converted, converted), combineContext());

        String expectedPath = "animations/testmod.example_mob.animation.json";
        assertTrue(pack.extraFiles().containsKey(expectedPath), "expected " + expectedPath + " in " + pack.extraFiles().keySet());

        JsonElement written = JsonParser.parseString(new String(pack.extraFiles().get(expectedPath)));
        assertTrue(written.getAsJsonObject().has("animations"));
        assertTrue(written.getAsJsonObject().get("format_version").isJsonPrimitive());
    }

    private GeckoAnimationAsset convertSample() throws Exception {
        JsonObject raw = JsonParser.parseString(SAMPLE_ANIMATION_JSON).getAsJsonObject();
        return GeckoAnimationConverter.INSTANCE.convert(
                new GeckoAnimationAsset("testmod", "example_mob", raw), testContext());
    }

    private static ConversionContext testContext() {
        return new ConversionContext("test-pack", silentListener());
    }

    private static CombineContext combineContext() {
        return new CombineContext("test-pack", silentListener());
    }

    private static LogListener silentListener() {
        return new LogListener() {
            @Override
            public void debugUnchecked(@NotNull String message) {
            }

            @Override
            public void info(@NotNull String message) {
            }

            @Override
            public void warn(@NotNull String message) {
            }

            @Override
            public void error(@NotNull String message) {
            }

            @Override
            public void error(@NotNull String message, @Nullable Throwable exception) {
            }
        };
    }
}
