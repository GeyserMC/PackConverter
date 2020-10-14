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

package org.geysermc.packconverter.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CustomModelDataHandler {

    public static String handleItemData(ObjectMapper mapper, Path storage, String filePath) {
        ObjectNode item = mapper.createObjectNode();
        item.put("format_version", "1.16.0");
        ObjectNode itemData = mapper.createObjectNode();
        ObjectNode itemDescription = mapper.createObjectNode();
        // Full identifier with geysercmd prefix
        String identifier = "geysercmd:" + filePath.replace("item/", "");
        itemDescription.put("identifier", identifier);
        itemData.set("description", itemDescription);
        ObjectNode itemComponent = mapper.createObjectNode();
        itemComponent.put("minecraft:icon", identifier.replace("geysercmd:", ""));
        itemComponent.put("minecraft:render_offsets", "tools");
        itemData.set("components", itemComponent);
        item.set("minecraft:item", itemData);
        File itemJsonFile = storage.resolve("items").toFile();
        if (!itemJsonFile.exists()) {
            itemJsonFile.mkdir();
        }
        Path path = itemJsonFile.toPath().resolve(filePath.replace("item/", "") + ".json");
        try (OutputStream outputStream = Files.newOutputStream(path,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            mapper.writer().writeValue(outputStream, item);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return identifier;
    }

    public static ObjectNode handleItemTexture(ObjectMapper mapper, Path storage, String filePath) {
        String cleanIdentifier = filePath.replace("item/", "");

        InputStream stream;
        JsonNode textureFile;
        try {
            stream = new FileInputStream(storage.resolve("assets/minecraft/models/" + filePath + ".json").toFile());
            textureFile = mapper.readTree(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(textureFile);
        // TODO: Don't rely on getting the 0 texture
        if (textureFile.has("textures")) {
            if (textureFile.get("textures").has("0") || textureFile.get("textures").has("layer0")) {
                String determine = textureFile.get("textures").has("0") ? "0" : "layer0";
                ObjectNode textureData = mapper.createObjectNode();
                ObjectNode textureName = mapper.createObjectNode();
                textureName.put("textures", textureFile.get("textures").get(determine).textValue().replace("item/", "textures/items/"));
                textureData.set(cleanIdentifier, textureName);
                return textureData;
            }
        }

        return null;
    }

}
