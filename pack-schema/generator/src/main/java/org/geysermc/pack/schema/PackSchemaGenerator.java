/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.schema;

import org.geysermc.pack.schema.converter.ConverterOptions;
import org.geysermc.pack.schema.converter.JsonTemplateToClassConverter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PackSchemaGenerator {
    private static final String PACKAGE_NAME = "org.geysermc.pack.bedrock.resource";

    private static final String GENERAL_SCHEMA_PATH = "/schema/source/general";
    private static final String RESOURCE_SCHEMA_PATH = "/schema/source/resource";

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?!\\.)(\\d+(\\.\\d+)+)");

    public static void main(String[] args) throws Exception {
        generateSchema(GENERAL_SCHEMA_PATH);
        generateSchema(RESOURCE_SCHEMA_PATH);
    }

    private static void generateSchema(@NotNull String schemaPath) throws Exception {
        URI uri = PackSchemaGenerator.class.getResource(schemaPath).toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            myPath = fileSystem.getPath(schemaPath);
        } else {
            myPath = Paths.get(uri);
        }

        Path output = Paths.get("pack-schema/bedrock/src/main/java");
        try (Stream<Path> paths = Files.walk(myPath)) {
            paths.forEach(path -> {
                if (Files.isDirectory(path) || !path.toString().endsWith(".json")) {
                    return;
                }

                try {
                    Path relativePath = myPath.relativize(path);
                    String packageName = PACKAGE_NAME;
                    if (relativePath.getParent() != null) {
                        String pathName = relativePath.getParent().toString();
                        Matcher matcher = VERSION_PATTERN.matcher(pathName);
                        if (matcher.find()) {
                            pathName = matcher.replaceAll(result -> "v" + result.group(1).replace(".", "_"));
                        }

                        packageName += "." + pathName.replace(File.separator, ".");
                    }

                    JsonTemplateToClassConverter.convert(
                            schemaPath + "/" +
                                    relativePath,
                            packageName,
                            output,
                            ConverterOptions.builder()
                                    .collisionPrefix("Minecraft")
                                    .schemaConfig("schema-config.json")
                                    .build()
                    );
                } catch (Exception ex) {
                    System.err.println("An error occurred converting " + path);
                    ex.printStackTrace();
                }
            });
        }
    }
}
