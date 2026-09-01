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

package org.geysermc.pack.converter.type.entity.gecko;

import com.google.gson.Gson;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.converter.type.entity.EntityModelParser;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoBone;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoCube;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoDescription;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoGeometry;
import org.geysermc.pack.converter.type.entity.gecko.raw.GeckoModel;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Entity model parser for GeckoLib {@code .geo.json} files. The
 * file shape maps almost 1:1 to a Bedrock entity geometry, so the
 * conversion is mostly re-naming identifiers and applying
 * {@link BoxUvMapper} for shorthand UVs.
 */
public final class GeckoLibEntityParser implements EntityModelParser {

    private static final String[] EXTS = {".geo.json"};
    private static final Gson GSON = new Gson();

    @Override
    public String id() {
        return "geckolib";
    }

    @Override
    public String[] supportedExtensions() {
        return EXTS;
    }

    @Override
    public BedrockModel parse(String path, ResourcePack pack) {
        Map<String, Writable> files = pack.unknownFiles();
        Writable body = files.get(path);
        if (body == null) return null;

        GeckoModel raw;
        try {
            raw = GSON.fromJson(body.toUTF8String(), GeckoModel.class);
        } catch (IOException e) {
            return null;
        }
        if (raw == null || raw.geometry == null || raw.geometry.isEmpty()) return null;

        ParsedPath pp = ParsedPath.from(path);
        if (pp == null) return null;

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion("1.16.0");
        List<Geometry> geometries = new ArrayList<>();
        for (GeckoGeometry sourceGeometry : raw.geometry) {
            geometries.add(convertGeometry(pp, sourceGeometry));
        }
        modelEntity.geometry(geometries);

        return new BedrockModel(BedrockModel.ModelType.ENTITY, pp.namespace() + "." + pp.fileName() + ".json", modelEntity);
    }

    private Geometry convertGeometry(ParsedPath pp, GeckoGeometry sourceGeometry) {
        Geometry geometry = new Geometry();
        Description description = new Description();
        String identifier = sourceGeometry.description != null && sourceGeometry.description.identifier != null
                ? sourceGeometry.description.identifier
                : "geometry." + pp.namespace() + "." + pp.fileName();
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
            bones.add(convertBone(rawBone));
        }
        geometry.bones(bones);
        return geometry;
    }

    private Bones convertBone(GeckoBone rawBone) {
        Bones bone = new Bones();
        bone.name(rawBone.name);
        if (rawBone.parent != null) bone.parent(rawBone.parent);
        bone.pivot(rawBone.pivot != null ? rawBone.pivot : new float[]{0, 0, 0});
        if (rawBone.rotation != null) bone.rotation(rawBone.rotation);
        if (rawBone.mirror != null) bone.mirror(rawBone.mirror);
        if (rawBone.inflate != null) bone.inflate(rawBone.inflate);

        List<Cubes> cubes = new ArrayList<>();
        for (GeckoCube rawCube : rawBone.cubes) {
            cubes.add(convertCube(rawCube, rawBone.name));
        }
        bone.cubes(cubes);
        return bone;
    }

    private Cubes convertCube(GeckoCube rawCube, String boneName) {
        Cubes cube = new Cubes();
        cube.origin(rawCube.origin != null ? rawCube.origin : new float[]{0, 0, 0});
        cube.size(rawCube.size != null ? rawCube.size : new float[]{0, 0, 0});
        if (rawCube.pivot != null) cube.pivot(rawCube.pivot);
        if (rawCube.rotation != null) cube.rotation(rawCube.rotation);
        if (rawCube.mirror != null) cube.mirror(rawCube.mirror);
        if (rawCube.inflate != null) cube.inflate(rawCube.inflate);
        cube.uv(convertUv(rawCube, boneName));
        return cube;
    }

    private Uv convertUv(GeckoCube rawCube, String boneName) {
        return BoxUvMapper.convert(rawCube.uv, rawCube.size != null ? rawCube.size : new float[]{0, 0, 0});
    }

    /**
     * Pulls the Bedrock-style {@code <namespace>.<file>} identifier
     * from a resource path like
     * {@code assets/<ns>/.../<file>.geo.json}. Tolerates the
     * conventional {@code assets/<ns>/geo/} layout as well as flat
     * layouts (alexsmobs-style: files under {@code assets/<ns>/}
     * with no {@code geo/} subfolder).
     */
    static final class ParsedPath {
        private final String namespace;
        private final String fileName;

        private ParsedPath(String namespace, String fileName) {
            this.namespace = namespace;
            this.fileName = fileName;
        }

        String namespace() { return namespace; }
        String fileName() { return fileName; }

        static ParsedPath from(String path) {
            if (!path.startsWith("assets/")) return null;
            String[] parts = path.split("/", 3);
            if (parts.length < 3 || !parts[0].equals("assets")) return null;
            String namespace = parts[1];
            int lastSlash = path.lastIndexOf('/');
            String fileNameWithExt = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
            if (!fileNameWithExt.toLowerCase().endsWith(".geo.json")) return null;
            return new ParsedPath(namespace, fileNameWithExt.substring(0, fileNameWithExt.length() - ".geo.json".length()));
        }
    }
}
