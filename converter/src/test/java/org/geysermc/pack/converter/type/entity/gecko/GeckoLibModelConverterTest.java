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
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.type.entity.gecko;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoModel;
import org.geysermc.pack.converter.type.model.BedrockModel;
import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeckoLibModelConverterTest {

    private static final Gson GSON = new Gson();

    private static final String SAMPLE_MODEL_JSON = """
            {
              "format_version": "1.12.0",
              "minecraft:geometry": [
                {
                  "description": {
                    "identifier": "geometry.example_gecko_mob",
                    "texture_width": 32,
                    "texture_height": 32
                  },
                  "bones": [
                    {
                      "name": "body",
                      "pivot": [0, 8, 0],
                      "cubes": [
                        {
                          "origin": [-4, 4, -4],
                          "size": [8, 8, 8],
                          "uv": [0, 0]
                        }
                      ]
                    },
                    {
                      "name": "head",
                      "parent": "body",
                      "pivot": [0, 16, 0],
                      "rotation": [0, 45, 0],
                      "cubes": [
                        {
                          "origin": [-2, 14, -2],
                          "size": [4, 4, 4],
                          "uv": {
                            "north": {"uv": [0, 16], "uv_size": [4, 4]},
                            "south": {"uv": [8, 16], "uv_size": [4, 4]},
                            "east": {"uv": [4, 16], "uv_size": [4, 4]},
                            "west": {"uv": [12, 16], "uv_size": [4, 4]},
                            "up": {"uv": [4, 12], "uv_size": [4, 4]},
                            "down": {"uv": [8, 12], "uv_size": [4, 4]}
                          }
                        }
                      ]
                    }
                  ]
                }
              ]
            }
            """;

    @Test
    void convert_producesEntityModel_withExpectedIdentifierAndFormatVersion() throws Exception {
        BedrockModel result = convertSample();

        assertNotNull(result);
        assertEquals(BedrockModel.ModelType.ENTITY, result.type());

        ModelEntity entity = result.model();
        assertEquals("1.16.0", entity.formatVersion());
        assertEquals(1, entity.geometry().size());

        Geometry geometry = entity.geometry().get(0);
        assertEquals("geometry.example_gecko_mob", geometry.description().identifier());
        assertEquals(32f, geometry.description().textureWidth());
        assertEquals(32f, geometry.description().textureHeight());
    }

    @Test
    void convert_carriesOverBoneHierarchy() throws Exception {
        BedrockModel result = convertSample();
        Geometry geometry = result.model().geometry().get(0);

        assertEquals(2, geometry.bones().size());

        Bones body = geometry.bones().get(0);
        assertEquals("body", body.name());
        assertArrayEquals(new float[] { 0, 8, 0 }, body.pivot());

        Bones head = geometry.bones().get(1);
        assertEquals("head", head.name());
        assertEquals("body", head.parent());
        assertArrayEquals(new float[] { 0, 45, 0 }, head.rotation());
    }

    @Test
    void convert_expandsShorthandBoxUv_usingBoxUvMapper() throws Exception {
        BedrockModel result = convertSample();
        Cubes bodyCube = result.model().geometry().get(0).bones().get(0).cubes().get(0);

        // origin/size should be carried over untouched
        assertArrayEquals(new float[] { -4, 4, -4 }, bodyCube.origin());
        assertArrayEquals(new float[] { 8, 8, 8 }, bodyCube.size());

        // uv: [0, 0] with size [8,8,8] should match BoxUvMapper.expand(0, 0, [8,8,8]) exactly
        var expected = BoxUvMapper.expand(0, 0, new float[] { 8, 8, 8 });
        assertArrayEquals(expected.north().uv(), bodyCube.uv().north().uv());
        assertArrayEquals(expected.down().uv(), bodyCube.uv().down().uv());
    }

    @Test
    void convert_preservesExplicitPerFaceUv_withoutModification() throws Exception {
        BedrockModel result = convertSample();
        Cubes headCube = result.model().geometry().get(0).bones().get(1).cubes().get(0);

        assertArrayEquals(new float[] { 0, 16 }, headCube.uv().north().uv());
        assertArrayEquals(new float[] { 4, 4 }, headCube.uv().north().uvSize());
        assertArrayEquals(new float[] { 4, 12 }, headCube.uv().up().uv());
    }

    @Test
    void convert_returnsNull_whenGeometryIsEmpty() throws Exception {
        GeckoModel empty = new GeckoModel();
        empty.formatVersion = "1.12.0";
        // geometry left as the default empty list

        BedrockModel result = GeckoLibModelConverter.INSTANCE.convert(
                new GeckoModelAsset("test", "empty", empty), testContext());

        assertTrue(result == null, "Expected null result for a model with no geometry entries");
    }

    private BedrockModel convertSample() throws Exception {
        GeckoModel model = GSON.fromJson(JsonParser.parseString(SAMPLE_MODEL_JSON), GeckoModel.class);
        return GeckoLibModelConverter.INSTANCE.convert(
                new GeckoModelAsset("testmod", "example_gecko_mob", model), testContext());
    }

    private static ConversionContext testContext() {
        return new ConversionContext("test-pack", new LogListener() {
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
        });
    }
}
