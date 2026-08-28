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

package org.geysermc.pack.converter.type.entity.vanilla;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.converter.type.entity.EntityModelParser;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.io.IOException;
import java.util.Map;

/**
 * Pass-through parser for mod resource packs that already contain a
 * Bedrock-format entity definition (e.g. a mod that ships an
 * {@code entity/foo.entity.json} directly). Re-uses the existing
 * JSON, only normalising the output key.
 */
public final class VanillaBedrockEntityParser implements EntityModelParser {

    private static final String[] EXTS = {".entity.json"};
    private static final Gson GSON = new Gson();

    @Override
    public String id() {
        return "vanilla-bedrock";
    }

    @Override
    public String[] supportedExtensions() {
        return EXTS;
    }

    @Override
    public BedrockModel parse(String path, ResourcePack pack) {
        // Bedrock entity definitions sit under entity/<key>.entity.json
        if (!path.startsWith("entity/") && !path.startsWith("assets/")) {
            return null;
        }
        Map<String, Writable> files = pack.unknownFiles();
        Writable body = files.get(path);
        if (body == null) {
            // Some pack formats might have already-classified entity JSONs elsewhere;
            // let other parsers try.
            return null;
        }
        try {
            String json = body.toUTF8String();
            ModelEntity modelEntity = GSON.fromJson(json, ModelEntity.class);
            if (modelEntity == null) {
                return null;
            }
            // Sanity: the file must be a client entity definition.
            if (json == null || !json.contains("minecraft:client_entity")) {
                return null;
            }
            String key = pathToBedrockKey(path);
            return new BedrockModel(BedrockModel.ModelType.ENTITY, key, modelEntity);
        } catch (IOException | JsonSyntaxException e) {
            return null;
        }
    }

    private static String pathToBedrockKey(String path) {
        // Strip leading "entity/" and trailing ".entity.json" - the rest is the identifier.
        String stripped = path;
        if (stripped.startsWith("entity/")) stripped = stripped.substring("entity/".length());
        if (stripped.startsWith("assets/")) {
            int second = stripped.indexOf('/', "assets/".length());
            if (second > 0) stripped = stripped.substring(second + 1);
            // After assets/<ns>/ we still have an "entity/" segment from the path layout.
            if (stripped.startsWith("entity/")) stripped = stripped.substring("entity/".length());
        }
        if (stripped.endsWith(".entity.json")) stripped = stripped.substring(0, stripped.length() - ".entity.json".length());
        return stripped;
    }
}
