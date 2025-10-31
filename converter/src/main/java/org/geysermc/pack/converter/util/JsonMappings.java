/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMappings {
    private static final Map<String, JsonMappings> CACHE = new HashMap<>();

    public static JsonMappings getMapping(String name) {
        if (CACHE.containsKey(name)) {
            return CACHE.get(name);
        }
        InputStream mappingsStream = JsonMappings.class.getResourceAsStream("/mappings/%s.json".formatted(name));
        if (mappingsStream == null) {
            throw new RuntimeException("Could not find %s.json mappings file!".formatted(name));
        }

        JsonObject jsonMappings = JsonParser.parseReader(new InputStreamReader(mappingsStream)).getAsJsonObject();

        Map<String, List<String>> mappings = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonMappings.entrySet()) {
            mappings.putAll(extractMapping(entry.getValue(), entry.getKey(), List.of()));
        }

        JsonMappings instance = new JsonMappings(mappings);
        CACHE.put(name, instance);

        return instance;
    }

    private static Map<String, List<String>> extractMapping(JsonElement element, String key, List<String> parents) {
        if (element.isJsonObject()) {
            Map<String, List<String>> mappings = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                List<String> newParents = new ArrayList<>(parents);
                newParents.add(key);
                mappings.putAll(extractMapping(entry.getValue(), key + "/" + entry.getKey(), newParents));
            }

            return mappings;
        } else if (element.isJsonArray()) {
            List<String> paths = new ArrayList<>();

            for (JsonElement arrayElement : element.getAsJsonArray()) {
                if (arrayElement.isJsonPrimitive()) {
                    paths.add(arrayElement.getAsString());
                } else {
                    throw new RuntimeException("Invalid item found within mapping file, items in an array must be primitives.");
                }
            }

            return Map.of(key, paths);
        } else if (element.isJsonPrimitive()) {
            String prefix = "";
            if (!parents.isEmpty()) prefix = String.join("/", parents) + "/";
            return Map.of(key, List.of(prefix + element.getAsString()));
        }

        return Map.of();
    }

    private final Map<String, List<String>> mappings;

    private JsonMappings(Map<String, List<String>> mappings) {
        this.mappings = mappings;
    }

    public List<String> map(String input) {
        return mappings.getOrDefault(input, List.of(input));
    }
}
