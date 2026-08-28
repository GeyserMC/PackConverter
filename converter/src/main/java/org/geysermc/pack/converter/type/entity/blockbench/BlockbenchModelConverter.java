/*
 * Copyright (c) 2019-2026 GeyserMC. http://geysermc.org
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
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR THE OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.type.entity.blockbench;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.converter.pipeline.AssetCombiner;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.AssetExtractor;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.pipeline.ExtractionContext;
import org.geysermc.pack.converter.type.entity.blockbench.raw.BlockbenchGeometry;
import org.geysermc.pack.converter.type.entity.blockbench.raw.BlockbenchModel;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoBone;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoCube;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Converts Blockbench project files (`.bbmodel` / `.bmodel`) into Bedrock
 * entity geometry. Blockbench's "Bedrock Entity" export uses the same
 * {@code minecraft:geometry[]} shape as GeckoLib, so the conversion
 * is structurally identical; the only meaningful difference is the
 * file extension and namespace of the emitted output.
 *
 * <p>This parser complements {@link org.geysermc.pack.converter.type.entity.gecko.GeckoLibModelConverter}:
 * mods that ship a Blockbench export under {@code assets/<ns>/models/}
 * (regardless of whether they also use GeckoLib) now get picked up.</p>
 */
public record BlockbenchModelConverter() implements AssetExtractor<BlockbenchModelAsset>,
        AssetConverter<BlockbenchModelAsset, BedrockModel>, AssetCombiner<BedrockModel> {

    public static final BlockbenchModelConverter INSTANCE = new BlockbenchModelConverter();

    private static final String FORMAT_VERSION = "1.16.0";
    // Both `.bbmodel` (Blockbench native) and `.bmodel` (legacy/Bedrock compressed) share the same
    // JSON shape. Lowercased to dodge any case variance on the resource file system.
    private static final Set<String> EXTENSIONS = Set.of(".bbmodel", ".bmodel");
    private static final Gson GSON = new Gson();

    @Override
    public Collection<BlockbenchModelAsset> extract(ResourcePack pack, ExtractionContext context) {
        List<BlockbenchModelAsset> assets = new ArrayList<>();

        int totalUnknown = pack.unknownFiles().size();
        int candidates = 0;
        int extracted = 0;

        for (Map.Entry<String, Writable> entry : pack.unknownFiles().entrySet()) {
            String path = entry.getKey();
            String lower = path.toLowerCase(Locale.ROOT);
            boolean matchedExtension = false;
            for (String ext : EXTENSIONS) {
                if (lower.endsWith(ext)) { matchedExtension = true; break; }
            }
            if (!matchedExtension) continue;
            candidates++;

            if (!path.startsWith("assets/")) {
                context.debug("Skipping Blockbench model outside assets/: " + path);
                continue;
            }

            String[] parts = path.split("/", 3);
            if (parts.length < 3 || !parts[0].equals("assets")) {
                context.warn("Skipping Blockbench model at unexpected path: " + path);
                continue;
            }
            String namespace = parts[1];

            int lastSlash = path.lastIndexOf('/');
            String fileNameWithExt = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
            String fileName = fileNameWithExt;
            for (String ext : EXTENSIONS) {
                if (fileName.toLowerCase(Locale.ROOT).endsWith(ext)) {
                    fileName = fileName.substring(0, fileName.length() - ext.length());
                    break;
                }
            }

            try {
                BlockbenchModel model = GSON.fromJson(entry.getValue().toUTF8String(), BlockbenchModel.class);
                if (model == null || model.geometry == null || model.geometry.isEmpty()) {
                    context.debug("Blockbench model " + fileName + " has no geometry, skipping");
                    continue;
                }
                assets.add(new BlockbenchModelAsset(namespace, fileName, model));
                extracted++;
                context.debug("Extracted Blockbench model: " + namespace + ":" + fileName + " from " + path);
            } catch (IOException | JsonSyntaxException e) {
                context.warn("Failed to parse Blockbench model at " + path + ": " + e.getMessage());
            }
        }

        context.info("Blockbench extraction: " + totalUnknown + " unknown files, " + candidates + " .bbmodel/.bmodel candidates, " + extracted + " extracted");

        return assets;
    }

    @Override
    public BedrockModel convert(BlockbenchModelAsset asset, ConversionContext context) throws Exception {
        BlockbenchModel raw = asset.model();
        if (raw == null || raw.geometry.isEmpty()) {
            context.debug("Blockbench model " + asset.fileName() + " has no geometry, skipping");
            return null;
        }

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion(FORMAT_VERSION);

        List<Geometry> geometries = new ArrayList<>();
        for (BlockbenchGeometry sourceGeometry : raw.geometry) {
            geometries.add(convertGeometry(asset, sourceGeometry, context));
        }
        modelEntity.geometry(geometries);

        return new BedrockModel(BedrockModel.ModelType.ENTITY, asset.namespace() + "." + asset.fileName() + ".json", modelEntity);
    }

    private Geometry convertGeometry(BlockbenchModelAsset asset, BlockbenchGeometry sourceGeometry, ConversionContext context) {
        Geometry geometry = new Geometry();

        Description description = new Description();
        String identifier = sourceGeometry.description != null && sourceGeometry.description.identifier != null
                ? sourceGeometry.description.identifier
                : "geometry." + asset.namespace() + "." + asset.fileName();
        description.identifier(identifier);
        description.textureWidth(sourceGeometry.description != null && sourceGeometry.description.textureWidth != null
                ? sourceGeometry.description.textureWidth : 64);
        description.textureHeight(sourceGeometry.description != null && sourceGeometry.description.textureHeight != null
                ? sourceGeometry.description.textureHeight : 64);
        if (sourceGeometry.description != null && sourceGeometry.description.visibleBoundsWidth != null) {
            description.visibleBoundsWidth(sourceGeometry.description.visibleBoundsWidth);
        }
        if (sourceGeometry.description != null && sourceGeometry.description.visibleBoundsHeight != null) {
            description.visibleBoundsHeight(sourceGeometry.description.visibleBoundsHeight);
        }
        if (sourceGeometry.description != null && sourceGeometry.description.visibleBoundsOffset != null) {
            description.visibleBoundsOffset(sourceGeometry.description.visibleBoundsOffset);
        }
        geometry.description(description);

        List<Bones> bones = new ArrayList<>();
        for (GeckoBone rawBone : sourceGeometry.bones) {
            bones.add(convertBone(rawBone, context));
        }
        geometry.bones(bones);

        return geometry;
    }

    private Bones convertBone(GeckoBone rawBone, ConversionContext context) {
        Bones bone = new Bones();
        bone.name(rawBone.name);
        if (rawBone.parent != null) {
            bone.parent(rawBone.parent);
        }
        bone.pivot(rawBone.pivot != null ? rawBone.pivot : new float[] { 0, 0, 0 });
        if (rawBone.rotation != null) {
            bone.rotation(rawBone.rotation);
        }
        if (rawBone.mirror != null) {
            bone.mirror(rawBone.mirror);
        }
        if (rawBone.inflate != null) {
            bone.inflate(rawBone.inflate);
        }

        List<Cubes> cubes = new ArrayList<>();
        for (GeckoCube rawCube : rawBone.cubes) {
            cubes.add(convertCube(rawCube, context, rawBone.name));
        }
        bone.cubes(cubes);

        return bone;
    }

    private Cubes convertCube(GeckoCube rawCube, ConversionContext context, String boneName) {
        Cubes cube = new Cubes();
        cube.origin(rawCube.origin != null ? rawCube.origin : new float[] { 0, 0, 0 });
        cube.size(rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
        if (rawCube.pivot != null) {
            cube.pivot(rawCube.pivot);
        }
        if (rawCube.rotation != null) {
            cube.rotation(rawCube.rotation);
        }
        if (rawCube.mirror != null) {
            cube.mirror(rawCube.mirror);
        }
        if (rawCube.inflate != null) {
            cube.inflate(rawCube.inflate);
        }
        cube.uv(convertUv(rawCube, context, boneName));

        return cube;
    }

    private Uv convertUv(GeckoCube rawCube, ConversionContext context, String boneName) {
        return org.geysermc.pack.converter.type.entity.gecko.BoxUvMapper.expand(0, 0, rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
    }

    @Override
    public void include(BedrockResourcePack pack, List<BedrockModel> models, CombineContext context) {
        List<String> seen = new ArrayList<>();
        for (BedrockModel model : models) {
            if (model == null) continue;
            String fileName = model.fileName();
            if (seen.contains(fileName)) {
                context.warn("Conflicting Blockbench model " + fileName + "!");
                continue;
            }
            seen.add(fileName);
            pack.addEntityModel(model.model(), fileName);
        }
    }
}
