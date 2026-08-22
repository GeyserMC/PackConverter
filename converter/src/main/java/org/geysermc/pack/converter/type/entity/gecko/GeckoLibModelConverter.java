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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.converter.pipeline.AssetCombiner;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.AssetExtractor;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.pipeline.ExtractionContext;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoBone;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoCube;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoGeometry;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoModel;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Converts GeckoLib {@code .geo.json} entity models into Bedrock entity geometry.
 * <p>
 * Unlike {@link org.geysermc.pack.converter.type.model.ModelConverter}, which converts vanilla
 * Java block/item models (built from cuboid "elements") by re-deriving Bedrock geometry from
 * scratch, this converter's job is much closer to a structural re-serialization: GeckoLib's
 * {@code .geo.json} format was deliberately designed to mirror Bedrock's own entity geometry
 * format, so most fields map across directly.
 * <p>
 * <b>Known limitations of this first version:</b>
 * <ul>
 *     <li>{@code poly_mesh} and {@code texture_meshes} cubes (an experimental GeckoLib/Bedrock
 *     feature) are not supported and will be skipped with a warning.</li>
 *     <li>Locators are not carried over.</li>
 *     <li>The box-UV shorthand expansion in {@link BoxUvMapper} has not been validated in-game -
 *     see its class documentation.</li>
 * </ul>
 */
public record GeckoLibModelConverter() implements AssetExtractor<GeckoModelAsset>,
        AssetConverter<GeckoModelAsset, BedrockModel>, AssetCombiner<BedrockModel> {

    public static final GeckoLibModelConverter INSTANCE = new GeckoLibModelConverter();

    private static final String FORMAT_VERSION = "1.16.0";
    private static final String GEO_SUFFIX = ".geo.json";
    private static final Gson GSON = new Gson();

    @Override
    public Collection<GeckoModelAsset> extract(ResourcePack pack, ExtractionContext context) {
        List<GeckoModelAsset> assets = new ArrayList<>();

        // `unknownFiles()` is how team.unnamed's creative-api (1.13.6, as pinned by this project)
        // exposes files that don't belong to any of its typed resource categories (models,
        // textures, sounds, ...) - `.geo.json`/`.animation.json` fall into this bucket.
        // It returns a plain path -> Writable map (confirmed against the actual compiled API,
        // unlike the Collection<UnknownResource>-shaped guess this replaced).
        for (Map.Entry<String, Writable> entry : pack.unknownFiles().entrySet()) {
            String path = entry.getKey();
            if (!path.endsWith(GEO_SUFFIX) || !path.contains("/geo/")) {
                continue;
            }

            // Expected shape: assets/<namespace>/geo/[.../]<fileName>.geo.json
            String[] parts = path.split("/", 3);
            if (parts.length < 3 || !parts[0].equals("assets")) {
                context.warn("Skipping GeckoLib model at unexpected path: " + path);
                continue;
            }
            String namespace = parts[1];
            String fileName = path.substring(path.lastIndexOf('/') + 1, path.length() - GEO_SUFFIX.length());

            try {
                GeckoModel model = GSON.fromJson(entry.getValue().toUTF8String(), GeckoModel.class);
                assets.add(new GeckoModelAsset(namespace, fileName, model));
            } catch (JsonSyntaxException e) {
                context.warn("Failed to parse GeckoLib model at " + path + ": " + e.getMessage());
            }
        }

        return assets;
    }

    @Override
    public BedrockModel convert(GeckoModelAsset asset, ConversionContext context) throws Exception {
        GeckoModel raw = asset.model();
        if (raw == null || raw.geometry.isEmpty()) {
            context.debug("GeckoLib model " + asset.fileName() + " has no geometry, skipping");
            return null;
        }

        // A .geo.json can technically define multiple named geometries; GeckoLib entities
        // typically only use the first one, so - like vanilla Java models - we take the first.
        GeckoGeometry sourceGeometry = raw.geometry.get(0);

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion(FORMAT_VERSION);

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

        modelEntity.geometry(List.of(geometry));

        return new BedrockModel(BedrockModel.ModelType.ENTITY, asset.namespace() + "." + asset.fileName() + ".json", modelEntity);
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
        JsonElement rawUv = rawCube.uv;
        if (rawUv == null) {
            // No UV specified - default to the origin, matching Minecraft's own default.
            return BoxUvMapper.expand(0, 0, rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
        }

        if (rawUv.isJsonArray()) {
            // Shorthand box-UV form: [u, v]
            JsonArray array = rawUv.getAsJsonArray();
            float u = array.get(0).getAsFloat();
            float v = array.get(1).getAsFloat();
            return BoxUvMapper.expand(u, v, rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
        }

        if (rawUv.isJsonObject()) {
            // Per-face form - this matches Bedrock's own `Uv` schema shape closely enough
            // to deserialize directly.
            try {
                return GSON.fromJson(rawUv, Uv.class);
            } catch (JsonSyntaxException e) {
                context.warn("Bone " + boneName + " has an unrecognised per-face UV shape, using [0,0] box UV instead: " + e.getMessage());
                return BoxUvMapper.expand(0, 0, rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
            }
        }

        context.warn("Bone " + boneName + " has an unrecognised UV value type, using [0,0] box UV instead");
        return BoxUvMapper.expand(0, 0, rawCube.size != null ? rawCube.size : new float[] { 0, 0, 0 });
    }

    @Override
    public void include(BedrockResourcePack pack, List<BedrockModel> bedrockModels, CombineContext context) {
        List<String> seen = new ArrayList<>();
        for (BedrockModel model : bedrockModels) {
            if (model == null) {
                continue;
            }
            if (seen.contains(model.fileName())) {
                context.warn("Conflicting GeckoLib entity model " + model.fileName() + "!");
                continue;
            }
            seen.add(model.fileName());
            pack.addEntityModel(model.model(), model.fileName());
        }
    }
}
