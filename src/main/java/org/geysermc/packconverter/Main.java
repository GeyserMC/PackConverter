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

package org.geysermc.packconverter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {
    private static final String packname = "Faithful-1.15";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(JsonParser.Feature.ALLOW_COMMENTS);

    public static void main(String[] args) {
        File inFile = new File(packname + ".zip");
        File outFile = new File(packname + ".mcpack");
        File outDir = new File(packname + "_mcpack/");

        //if (inFile.exists() && !outFile.exists()) {
        if (true) {
            try {
                // Open zip
                ZipFile zipFile = new ZipFile(inFile);

                // Read mapping json
                Map<String, String> pathMappings = OBJECT_MAPPER.readValue(Main.class.getClassLoader().getResourceAsStream("paths.json"), Map.class);
                Map<String, String> fileMappings = OBJECT_MAPPER.readValue(Main.class.getClassLoader().getResourceAsStream("files.json"), Map.class);

                // pack.mcmeta match
                Pattern metaPattern = Pattern.compile("(.*)/pack.mcmeta");
                String metaPath = "";
                String metaDesc = "";

                // Regex match files over
                ZipEntry entry;
                final Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    entry = entry = entries.nextElement();
                    if (metaPath.equals("")) {
                        Matcher m = metaPattern.matcher(entry.getName());
                        if (m.find()) {
                            metaPath = m.group(1);
                            metaDesc = OBJECT_MAPPER.readTree(zipFile.getInputStream(entry)).get("pack").get("description").asText();

                            continue;
                        }
                    }

                    String curName = entry.getName();
                    String newName = entry.getName();
                    for (Map.Entry<String, String> mapping : pathMappings.entrySet()) {
                        newName = curName.replaceAll(mapping.getKey(), mapping.getValue());
                        if (newName != curName) {
                            break;
                        }
                    }

                    Path newNamePath = Paths.get(newName);
                    for (Map.Entry<String, String> mapping : fileMappings.entrySet()) {
                        String filename = newNamePath.getFileName().toString();
                        if (filename.matches(mapping.getKey())) {
                            newNamePath = newNamePath.getParent().resolve(filename.replaceAll(mapping.getKey(), mapping.getValue()));
                            newName = newNamePath.toString();
                            break;
                        }
                    }

                    if (newName != curName) {
                        System.out.format("%s => %s\n", curName, newName);

                        File newFile = outDir.toPath().resolve(newName).toFile();
                        newFile.getParentFile().mkdirs();

                        InputStream fileStream = zipFile.getInputStream(entry);
                        FileOutputStream outStream = new FileOutputStream(newFile);

                        byte[] buf = new byte[fileStream.available()];
                        int length;
                        while ((length = fileStream.read(buf)) != -1) {
                            outStream.write(buf, 0, length);
                        }

                        outStream.flush();
                        outStream.close();
                    }
                }

                // Some fancy TGA trickery
                //fixGrass(outDir.toPath().resolve(metaPath));

                // Build manifest.json
                ResourcePackManifest.Header header = new ResourcePackManifest.Header();
                header.setName(packname);
                header.setDescription(metaDesc);
                header.setUuid(UUID.randomUUID());
                header.setVersion(new int[] { 1, 0, 0});

                ResourcePackManifest.Module module = new ResourcePackManifest.Module();
                module.setDescription(metaDesc);
                module.setType("resources");
                module.setUuid(UUID.randomUUID());
                module.setVersion(new int[] { 1, 0, 0});

                ResourcePackManifest manifest = new ResourcePackManifest();
                manifest.setFormatVersion(1);
                manifest.setHeader(header);
                Collection<ResourcePackManifest.Module> modules = new ArrayList();
                modules.add(module);
                manifest.setModules(modules);

                ObjectWriter writer = OBJECT_MAPPER.writer(new DefaultPrettyPrinter());
                writer.writeValue(outDir.toPath().resolve(metaPath).resolve("manifest.json").toFile(), manifest);

                // Save
                ZipUtils zipUtils = new ZipUtils(outDir);
                zipUtils.generateFileList();
                zipUtils.zipIt(outFile.toString());

                // Delete temp folder
                //Files.delete(outDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is meant to write a dirt texture to a image with all alpha 0
     * then write the grass_side on top while preserving the RGB of the
     * dirt texture, only seems to be possible with TGA and this doesnt work
     * @param rootDir
     */
    private static void fixGrass(Path rootDir) {
        File grassSide = rootDir.resolve("textures/blocks/grass_side.png").toFile();
        File grassSideNew = rootDir.resolve("textures/blocks/grass_side.tga").toFile();
        File dirt = rootDir.resolve("textures/blocks/dirt.png").toFile();

        if (grassSide.exists() && dirt.exists()) {
            ImageIO.scanForPlugins();

            try {
                BufferedImage grassSideImg = ImageIO.read(grassSide);
                BufferedImage dirtImg = ImageIO.read(dirt);

                BufferedImage newGrassSideImg = new BufferedImage(grassSideImg.getWidth(), grassSideImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = newGrassSideImg.createGraphics();

                float[] scales = { 1f, 1f, 1f, 0f };
                float[] offsets = new float[4];
                RescaleOp rop = new RescaleOp(scales, offsets, null);
                g2.drawImage(dirtImg, rop, 0, 0);

                scales = new float[] { 1f, 1f, 1f, 1f };
                rop = new RescaleOp(scales, offsets, null);
                g2.drawImage(grassSideImg, rop, 0, 0);

                g2.dispose();
                ImageIO.write(newGrassSideImg, "TGA", grassSideNew);
                Files.delete(grassSide.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
