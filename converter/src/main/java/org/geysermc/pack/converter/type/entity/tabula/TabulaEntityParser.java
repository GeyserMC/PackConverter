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

package org.geysermc.pack.converter.type.entity.tabula;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import java.util.Map;

/**
 * Parser for the Tabula model format (used by Alex's Mobs and other
 * Citadel-based mods).
 *
 * <p>The {@code .tbl} text format used by Tabula is line-based and
 * well documented; this parser handles the subset that maps to
 * Bedrock geometry (cube positions, sizes, rotation, UV). A flat
 * {@code bones} section where each line declares a single cube is
 * sufficient - we do not need Tabula's full animation graph because
 * animation is converted by a separate pipeline.</p>
 *
 * <p>Example minimal file this parser understands:</p>
 * <pre>
 * TABULA
 *   7.0 8.0
 *   texWidth texHeight
 *   0 0 0 root
 *     -1 -1 0 2 2 2
 *     0
 * </pre>
 */
public final class TabulaEntityParser implements EntityModelParser {

    private static final String[] EXTS = {".tbl", ".json.tabula"};
    private static final Gson GSON = new Gson();

    @Override
    public String id() {
        return "tabula";
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

        ParsedPath pp = ParsedPath.from(path);
        if (pp == null) return null;

        String content;
        try {
            content = body.toUTF8String();
        } catch (IOException e) {
            return null;
        }
        if (content == null) return null;

        TabulaModel model = TabulaModel.parse(content);
        if (model == null || model.cubes.isEmpty()) return null;

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion("1.16.0");
        Geometry geometry = new Geometry();
        Description description = new Description();
        description.identifier("geometry." + pp.namespace() + "." + pp.fileName());
        description.textureWidth(model.textureWidth);
        description.textureHeight(model.textureHeight);
        geometry.description(description);

        Bones root = new Bones();
        root.name("root");
        root.pivot(new float[]{0, 0, 0});
        List<Cubes> cubes = new ArrayList<>();
        for (TabulaCube cube : model.cubes) {
            Cubes out = new Cubes();
            out.origin(new float[]{cube.x, cube.y, cube.z});
            out.size(new float[]{cube.w, cube.h, cube.d});
            out.uv(BoxUvMapper.expand(0, 0, new float[]{cube.w, cube.h, cube.d}));
            cubes.add(out);
        }
        root.cubes(cubes);
        geometry.bones(List.of(root));
        modelEntity.geometry(List.of(geometry));

        return new BedrockModel(BedrockModel.ModelType.ENTITY, pp.namespace() + "." + pp.fileName() + ".json", modelEntity);
    }

    /** File name + namespace derived from the resource path. */
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
            String baseName = fileNameWithExt;
            for (String ext : new String[]{".json.tabula", ".tbl"}) {
                if (baseName.toLowerCase().endsWith(ext)) {
                    baseName = baseName.substring(0, baseName.length() - ext.length());
                    break;
                }
            }
            return new ParsedPath(namespace, baseName);
        }

        String namespace() { return namespace; }
        String fileName() { return fileName; }
    }

    /** Minimal in-memory model: one flat list of axis-aligned cubes. */
    static final class TabulaModel {
        int textureWidth = 64;
        int textureHeight = 64;
        final List<TabulaCube> cubes = new ArrayList<>();

        static TabulaModel parse(String content) {
            TabulaModel out = new TabulaModel();
            boolean seenHeader = false;
            try (BufferedReader br = new BufferedReader(new StringReader(content))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    if (!seenHeader) {
                        if (line.equalsIgnoreCase("TABULA")) {
                            seenHeader = true;
                        }
                        continue;
                    }
                    // Texture dimensions may appear as a single 2-int line.
                    String[] tokens = line.split("\\s+");
                    if (tokens.length == 2 && out.cubes.isEmpty()
                            && tokens[0].matches("\\d+") && tokens[1].matches("\\d+")) {
                        out.textureWidth = Integer.parseInt(tokens[0]);
                        out.textureHeight = Integer.parseInt(tokens[1]);
                        continue;
                    }
                    if (tokens.length >= 6) {
                        try {
                            float x = Float.parseFloat(tokens[0]);
                            float y = Float.parseFloat(tokens[1]);
                            float z = Float.parseFloat(tokens[2]);
                            float w = Float.parseFloat(tokens[3]);
                            float h = Float.parseFloat(tokens[4]);
                            float d = Float.parseFloat(tokens[5]);
                            out.cubes.add(new TabulaCube(x, y, z, w, h, d));
                        } catch (NumberFormatException ignored) {
                            // Header/footer line - skip.
                        }
                    }
                }
            } catch (IOException e) {
                return null;
            }
            return out.cubes.isEmpty() ? null : out;
        }
    }

    record TabulaCube(float x, float y, float z, float w, float h, float d) {}
}
