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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CustomModelDataHandler {

    public static String handle(ObjectMapper mapper, Path storage, String filePath) {
        ObjectNode item = mapper.createObjectNode();
        item.put("format_version", "1.16.0");
        ObjectNode itemData = mapper.createObjectNode();
        ObjectNode itemDescription = mapper.createObjectNode();
        String identifier = "geysercmd:" + filePath.replace("item/", "");
        itemDescription.put("identifier", identifier);
        itemData.set("description", itemDescription);
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

}
