/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
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

package org.geysermc.packconverter.api.converters;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import org.geysermc.packconverter.api.PackConverter;
import org.geysermc.packconverter.api.utils.CustomModelDataHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CustomModelDataConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new String[] {"assets/minecraft/models/item", "textures/item_texture.json"});
    }

    public CustomModelDataConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        System.out.println("Checking for custom model data");
        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            ObjectMapper mapper = new ObjectMapper();

            // Create the texture_data file that will map all textures
            ObjectNode textureData = mapper.createObjectNode();
            textureData.put("resource_pack_name", "geysercmd");
            textureData.put("texture_name", "atlas.items");
            ObjectNode allTextures = mapper.createObjectNode();
            for (File file : storage.resolve(from).toFile().listFiles()) {
                InputStream stream = new FileInputStream(file);

                JsonNode node = mapper.readTree(stream);
                if (node.has("overrides")) {
                    for (JsonNode override : node.get("overrides")) {
                        JsonNode predicate = override.get("predicate");
                        if (predicate.has("custom_model_data")) {
                            int id = predicate.get("custom_model_data").asInt();
                            String identifier = CustomModelDataHandler.handleItemData(mapper, storage, override.get("model").asText());
                            Int2ObjectMap<String> data = packConverter.getCustomModelData().getOrDefault(file.getName().replace(".json", ""), null);
                            if (data == null) {
                                Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();
                                map.put(id, identifier);
                                packConverter.getCustomModelData().put(file.getName().replace(".json", ""), map);
                            } else {
                                data.put(id, identifier);
                            }

                            ObjectNode textureInfo = CustomModelDataHandler.handleItemTexture(mapper, storage, override.get("model").asText());
                            if (textureInfo != null) {
                                allTextures.setAll(textureInfo);
                            }
                        }
                    }
                }
            }

            textureData.set("texture_data", allTextures);

            OutputStream outputStream = Files.newOutputStream(storage.resolve(to), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            mapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream, textureData);

            System.out.println(String.format("Converted models %s", from));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
