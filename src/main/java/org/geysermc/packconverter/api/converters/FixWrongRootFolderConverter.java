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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import org.geysermc.packconverter.api.PackConverter;
import org.geysermc.packconverter.api.utils.ResourcePackManifest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FixWrongRootFolderConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"pack.mcmeta", new String[] {"pack.png", "assets/", "bedrock_textures/", "bedrock_uuid_header", "bedrock_uuid_module"}});
    }

    public FixWrongRootFolderConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String packMcmeta = (String) this.data[0];
            String[] moveFiles = (String[]) this.data[1];

            if (storage.resolve(packMcmeta).toFile().exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("%s not found in root folder (But are needed in the root folder, even in the Java version) - Try to lookup in sub folders ...", packMcmeta));

            Path rootPath = null;
            for (Path filePath : Files.walk(storage).filter(Files::isRegularFile).collect(Collectors.toList())) {
                if (filePath.getFileName().toString().equals(packMcmeta)) {
                    rootPath = filePath.getParent();
                    break;
                }
            }

            if (rootPath == null) {
                throw new AssertionError(String.format("%s not found! Is this really a Java texture pack?", packMcmeta));
            }

            packConverter.log(String.format("Root folder found in sub folder %s", rootPath.relativize(storage).toString()));

            List<String> moveFilesList = new ArrayList<>();
            moveFilesList.add(packMcmeta);
            moveFilesList.addAll(Arrays.asList(moveFiles));

            for (String fileName : moveFilesList) {
                if (rootPath.resolve(fileName).toFile().exists()) {
                    Files.move(rootPath.resolve(fileName), storage.resolve(fileName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
