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

package org.geysermc.pack.converter.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class JSONMappings {
    private static final Map<String, JSONMappings> MAPPINGS = new HashMap<>();
    private static final Map<String, List<JSONMappings>> ADDITIONAL_MAPPINGS = new HashMap<>();
    private final LinkedHashMap<String, List<String>> processedMappings;

    public JSONMappings(LinkedHashMap<String, List<String>> processedMappings) {
        this.processedMappings = processedMappings;
    }

    public List<String> map(String javaValue) {
        return processedMappings.getOrDefault(javaValue, Collections.singletonList(javaValue));
    }

    public JSONMappings merge(JSONMappings jsonMappings) {
        LinkedHashMap<String, List<String>> tmpMappings = new LinkedHashMap<>(processedMappings);
        tmpMappings.putAll(jsonMappings.processedMappings);

        return new JSONMappings(tmpMappings);
    }

    public static JSONMappings mappings(String name) {
        JSONMappings mappings;

        if (MAPPINGS.containsKey(name)) {
            mappings = MAPPINGS.get(name);
            for (JSONMappings jsonMappings : ADDITIONAL_MAPPINGS.getOrDefault(name, new ArrayList<>())) {
                mappings = mappings.merge(jsonMappings);
            }
        } else {
            InputStream mappingsStream = JSONMappings.class.getResourceAsStream("/mappings/%s.json".formatted(name));
            if (mappingsStream == null) {
                throw new RuntimeException("Could not find %s.json mappings file!".formatted(name));
            }
            mappings = mappings(name, mappingsStream);
        }

        return mappings;
    }

    public static JSONMappings mappings(String name, InputStream mappingsStream) {
        JsonObject object = JsonParser.parseReader(new InputStreamReader(mappingsStream)).getAsJsonObject();

        LinkedHashMap<String, List<String>> mappings = new LinkedHashMap<>();

        createMappings(null, object, mappings);

        JSONMappings jsonMappings = new JSONMappings(mappings);

        if (MAPPINGS.containsKey(name)) {
            addAdditionalMappings(name, jsonMappings);
            return mappings(name); // Return the merged version
        } else {
            MAPPINGS.put(name, jsonMappings);
            return jsonMappings;
        }
    }

    private static void addAdditionalMappings(String name, JSONMappings jsonMappings) {
        if (!MAPPINGS.containsKey(name)) {
            MAPPINGS.put(name, jsonMappings);
            return;
        }

        ADDITIONAL_MAPPINGS.computeIfAbsent(name, s -> new ArrayList<>());
        ADDITIONAL_MAPPINGS.get(name).add(jsonMappings);
    }

    private static void createMappings(@Nullable String parentPath, JsonObject object, LinkedHashMap<String, List<String>> mappings) {
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String key = parentPath == null ? entry.getKey() : parentPath + "/" + entry.getKey();

            if (entry.getValue().isJsonObject()) {
                createMappings(key, entry.getValue().getAsJsonObject(), mappings);
            } else if (entry.getValue().isJsonArray()) {
                List<String> values = new ArrayList<>();
                for (JsonElement element : entry.getValue().getAsJsonArray()) {
                    if (!element.isJsonPrimitive()) throw new IllegalStateException("Invalid array contents when parsing mapping file.");

                    String value = parentPath == null ? element.getAsString() : parentPath + "/" + element.getAsString();
                    values.add(value);
                }
                mappings.put(key, values);
            } else if (entry.getValue().isJsonPrimitive()) {
                String value = parentPath == null ? entry.getValue().getAsString() : parentPath + "/" + entry.getValue().getAsString();
                mappings.put(key, Collections.singletonList(value));
            }
        }
    }
}
