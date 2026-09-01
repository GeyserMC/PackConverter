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

package org.geysermc.pack.converter.type.entity.obj;

import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.converter.type.entity.EntityModelParser;
import org.geysermc.pack.converter.type.entity.gecko.BoxUvMapper;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Parser for the Wavefront OBJ format. OBJ is a polygon mesh format;
 * Bedrock's entity geometry format is an axis-aligned box list. To
 * bridge the two we group every 8 vertices of the OBJ mesh that
 * form the corners of an axis-aligned box into a single Bedrock cube.
 * This is an approximation, not a true mesh-to-cube conversion, but
 * is good enough for placeholder rendering and to give a sense of
 * the model's bounding box on screen.
 *
 * <p>Use a tool like Blockbench to author a proper Bedrock geometry
 * for production; this parser exists so mods that ship an OBJ
 * export get something rather than nothing.</p>
 */
public final class ObjWavefrontEntityParser implements EntityModelParser {

    private static final String[] EXTS = {".obj"};
    private static final int MAX_SOURCE_CHARS = 16 * 1024 * 1024;
    private static final int MAX_VERTICES = 65_536;
    private static final int MAX_CUBES = 4_096;
    private static final long MAX_PARSE_NANOS = 2_000_000_000L;

    @Override
    public String id() {
        return "obj";
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

        String content;
        try {
            content = body.toUTF8String();
        } catch (IOException e) {
            return null;
        }
        if (content == null || content.length() > MAX_SOURCE_CHARS) return null;

        List<float[]> vertices = new ArrayList<>();
        long deadline = System.nanoTime() + MAX_PARSE_NANOS;
        try (BufferedReader br = new BufferedReader(new StringReader(content))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (System.nanoTime() > deadline) return null;
                if (line.startsWith("v ")) {
                    String[] t = line.substring(2).trim().split("\\s+");
                    if (t.length >= 3) {
                        try {
                            vertices.add(new float[]{
                                    Float.parseFloat(t[0]),
                                    Float.parseFloat(t[1]),
                                    Float.parseFloat(t[2])
                            });
                            if (vertices.size() > MAX_VERTICES) return null;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }

        if (vertices.isEmpty()) return null;

        ParsedPath pp = ParsedPath.from(path);
        if (pp == null) return null;

        // Greedy box extraction: pick 8 vertices whose min/max defines a
        // box and that contain at least 6 of the 8 corners - approximate
        // but cheap and usually fine for mod models that are already
        // built from boxes.
        boolean[] consumed = new boolean[vertices.size()];
        List<float[]> boxes = new ArrayList<>();
        extraction:
        for (int i = 0; i < vertices.size(); i++) {
            if (System.nanoTime() > deadline || boxes.size() >= MAX_CUBES) break;
            if (consumed[i]) continue;
            float[] a = vertices.get(i);
            for (int j = i + 1; j < vertices.size(); j++) {
                if (consumed[j]) continue;
                float[] b = vertices.get(j);
                // Look for a third vertex sharing an axis.
                for (int k = j + 1; k < vertices.size(); k++) {
                    if (System.nanoTime() > deadline) break extraction;
                    if (consumed[k]) continue;
                    float[] c = vertices.get(k);
                    float[] box = tryBoxFromAxes(a, b, c);
                    if (box == null) continue;
                    boxes.add(box);
                    consumed[i] = consumed[j] = consumed[k] = true;
                    break;
                }
                if (consumed[i]) break;
            }
        }

        if (boxes.isEmpty()) {
            // Fallback: collapse all vertices to a single axis-aligned box.
            float[] bounds = boundsOf(vertices);
            boxes.add(bounds);
        }

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion("1.16.0");
        Geometry geometry = new Geometry();
        Description description = new Description();
        description.identifier("geometry." + pp.namespace() + "." + pp.fileName());
        description.textureWidth(64);
        description.textureHeight(64);
        geometry.description(description);

        Bones root = new Bones();
        root.name("root");
        root.pivot(new float[]{0, 0, 0});
        List<Cubes> cubes = new ArrayList<>();
        for (float[] box : boxes) {
            // box: {xMin, yMin, zMin, xMax, yMax, zMax}
            float w = box[3] - box[0];
            float h = box[4] - box[1];
            float d = box[5] - box[2];
            Cubes c = new Cubes();
            c.origin(new float[]{
                    (box[0] + box[3]) / 2f,
                    (box[1] + box[4]) / 2f,
                    (box[2] + box[5]) / 2f
            });
            c.size(new float[]{w, h, d});
            c.uv(BoxUvMapper.expand(0, 0, new float[]{w, h, d}));
            cubes.add(c);
        }
        root.cubes(cubes);
        geometry.bones(List.of(root));
        modelEntity.geometry(List.of(geometry));

        return new BedrockModel(BedrockModel.ModelType.ENTITY, pp.namespace() + "." + pp.fileName() + ".json", modelEntity);
    }

    /**
     * Try to build an axis-aligned box from three vertices that share
     * exactly one axis. Returns the min/max bounds, or {@code null} if
     * the three vertices do not lock down the same axis. This is a
     * lossy pass; for irregular meshes it skips vertices.
     */
    private static float[] tryBoxFromAxes(float[] a, float[] b, float[] c) {
        // Pick the axis on which all three points share the same value.
        int sharedAxis = -1;
        if (a[0] == b[0] && b[0] == c[0]) sharedAxis = 0;
        else if (a[1] == b[1] && b[1] == c[1]) sharedAxis = 1;
        else if (a[2] == b[2] && b[2] == c[2]) sharedAxis = 2;
        if (sharedAxis < 0) return null;
        // From two points on a different axis pick min/max.
        float min0 = Math.min(a[0], b[0]);
        float max0 = Math.max(a[0], b[0]);
        float min1 = Math.min(a[1], b[1]);
        float max1 = Math.max(a[1], b[1]);
        float min2 = Math.min(a[2], b[2]);
        float max2 = Math.max(a[2], b[2]);
        return new float[]{min0, min1, min2, max0, max1, max2};
    }

    private static float[] boundsOf(List<float[]> vertices) {
        float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
        for (float[] v : vertices) {
            if (v[0] < minX) minX = v[0];
            if (v[1] < minY) minY = v[1];
            if (v[2] < minZ) minZ = v[2];
            if (v[0] > maxX) maxX = v[0];
            if (v[1] > maxY) maxY = v[1];
            if (v[2] > maxZ) maxZ = v[2];
        }
        return new float[]{minX, minY, minZ, maxX, maxY, maxZ};
    }

    static final class ParsedPath {
        private final String namespace;
        private final String fileName;

        private ParsedPath(String namespace, String fileName) {
            this.namespace = namespace;
            this.fileName = fileName;
        }

        static ParsedPath from(String path) {
            if (!path.startsWith("assets/")) return null;
            String[] parts = path.split("/", 3);
            if (parts.length < 3 || !parts[0].equals("assets")) return null;
            String namespace = parts[1];
            int lastSlash = path.lastIndexOf('/');
            String fileNameWithExt = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
            String baseName = fileNameWithExt.toLowerCase(Locale.ROOT).endsWith(".obj")
                    ? fileNameWithExt.substring(0, fileNameWithExt.length() - 4)
                    : fileNameWithExt;
            return new ParsedPath(namespace, baseName);
        }

        String namespace() { return namespace; }
        String fileName() { return fileName; }
    }
}
