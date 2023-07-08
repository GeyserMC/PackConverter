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

package org.geysermc.pack.converter.converter.model;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.*;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.BaseConverter;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.data.BaseConversionData;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.CubeFace;
import team.unnamed.creative.base.Vector4Float;
import team.unnamed.creative.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AutoService(Converter.class)
public class ModelConverter extends BaseConverter {
    private static final String FORMAT_VERSION = "1.12.0";
    private static final String GEOMETRY_FORMAT = "geometry.%s";

    private static final float[] ELEMENT_OFFSET = new float[] { 8, 0, 8 };

    @Override
    public void convert(@NotNull PackConversionContext<BaseConversionData> context) throws Exception {
        ResourcePack javaPack = context.javaResourcePack();
        BedrockResourcePack bedrockPack = context.bedrockResourcePack();
        Collection<Model> models = javaPack.models();

        for (Model model : models) {
            List<Element> elements = model.elements();
            if (elements.isEmpty()) {
                continue;
            }

            // ModelTextures modelTextures = model.textures();
            // if (modelTextures == null) {
            //     continue;
            // }

            // Collection<ModelTexture> textures = modelTextures.variables().values();
            // if (textures.isEmpty()) {
            //     continue;
            // }

            // TODO: Combine textures into one texture
            // ModelTexture modelTexture = textures.iterator().next();


            String value = model.key().value();
            context.debug("Converting model " + model.key().key() + ":" + value);

            ModelEntity modelEntity = new ModelEntity();
            modelEntity.formatVersion(FORMAT_VERSION);

            Geometry geometry = new Geometry();

            String namespace = model.key().namespace();
            String fileName = value.substring(value.lastIndexOf('/') + 1);

            String geoName = (namespace.equals(Key.MINECRAFT_NAMESPACE) ? "" : namespace + ".") + fileName;

            // TODO: Don't hardcode all this
            Description description = new Description();
            description.identifier(String.format(GEOMETRY_FORMAT, geoName));
            description.textureWidth(16);
            description.textureHeight(16);
            description.visibleBoundsWidth(2);
            description.visibleBoundsHeight(2);
            description.visibleBoundsOffset(new float[] { 0.0f, 0.25f, 0.0f });
            geometry.description(description);

            List<Bones> bones = new ArrayList<>();

            // TODO: Should each element be its own bone rather
            //       than its own cube in the same bone?
            int i = 0;
            for (Element element : elements) {
                float[] from = element.from().toArray();
                float[] to = element.to().toArray();

                Bones bone = new Bones();
                bone.name("bone_" + i++);
                bone.pivot(new float[] { 0, 0, 0 });

                Cubes cube = new Cubes();
                cube.origin(new float[] { ELEMENT_OFFSET[0] - to[0], from[1], from[2] - ELEMENT_OFFSET[2] });
                cube.size(new float[] { to[0] - from[0], to[1] - from[1], to[2] - from[2] });

                ElementRotation elementRotation = element.rotation();
                if (elementRotation != null) {
                    float[] origin = elementRotation.origin().toArray();
                    cube.pivot(new float[] { ELEMENT_OFFSET[0] - origin[0], ELEMENT_OFFSET[1] - origin[1], origin[2] - ELEMENT_OFFSET[2] });

                    float angle = elementRotation.angle();
                    float[] rotation = new float[3];
                    switch (elementRotation.axis()) {
                        case X -> rotation[0] = -angle;
                        case Y -> rotation[1] = -angle;
                        case Z -> rotation[2] = -angle;
                    }

                    cube.rotation(rotation);
                }

                Uv uv = new Uv();
                for (Map.Entry<CubeFace, ElementFace> entry : element.faces().entrySet()) {
                    CubeFace face = entry.getKey();
                    ElementFace elementFace = entry.getValue();
                    if (elementFace.uv() == null) {
                        continue;
                    }

                    // The Java pack lib we use does this weird thing where it
                    // divides the UV by 16, so we need to multiply it by 16
                    applyUv(uv, face, elementFace.uv().multiply(ElementFace.MINECRAFT_UV_UNIT));
                }

                cube.uv(uv);
                bone.cubes(List.of(cube));

                bones.add(bone);
            }

            geometry.bones(bones);

            modelEntity.geometry(List.of(geometry));

            if (model.key().namespace().contains("entity")) {
                bedrockPack.addEntityModel(modelEntity, fileName + ".json");
            } else {
                // Bedrock only has a concept of entity or block models
                bedrockPack.addBlockModel(modelEntity, fileName + ".json");
            }
        }
    }

    private static void applyUv(Uv uv, CubeFace face, Vector4Float faceUv) {
        float[] uvs;
        float[] uvSize;

        // These values are flipped for some reason
        if (face == CubeFace.DOWN || face == CubeFace.UP) {
            uvs = new float[] { faceUv.x2(), faceUv.y2() };
            uvSize = new float[] { faceUv.x() - faceUv.x2(), faceUv.y() - faceUv.y2() };
        } else {
            uvs = new float[] { faceUv.x(), faceUv.y() };
            uvSize = new float[] { faceUv.x2() - faceUv.x(), faceUv.y2() - faceUv.y() };
        }

        switch (face) {
            case NORTH -> {
                North north = new North();
                north.uv(uvs);
                north.uvSize(uvSize);

                uv.north(north);
            }
            case SOUTH -> {
                South south = new South();
                south.uv(uvs);
                south.uvSize(uvSize);

                uv.south(south);
            }
            case EAST -> {
                East east = new East();
                east.uv(uvs);
                east.uvSize(uvSize);

                uv.east(east);
            }
            case WEST -> {
                West west = new West();
                west.uv(uvs);
                west.uvSize(uvSize);

                uv.west(west);
            }
            case UP -> {
                Up up = new Up();
                up.uv(uvs);
                up.uvSize(uvSize);

                uv.up(up);
            }
            case DOWN -> {
                Down down = new Down();
                down.uv(uvs);
                down.uvSize(uvSize);

                uv.down(down);
            }
        }
    }
}
