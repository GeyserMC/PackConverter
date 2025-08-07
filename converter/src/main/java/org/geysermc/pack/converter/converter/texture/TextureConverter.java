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

package org.geysermc.pack.converter.converter.texture;

import com.google.auto.service.AutoService;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.converter.texture.transformer.TransformedTexture;
import org.geysermc.pack.converter.data.TextureConversionData;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@AutoService(Converter.class)
public class TextureConverter implements Converter<TextureConversionData> {
    public static final String BEDROCK_TEXTURES_LOCATION = "textures";

    private final List<TextureTransformer> transformers = StreamSupport.stream(ServiceLoader.load(TextureTransformer.class).spliterator(), false)
            .sorted(Comparator.comparingInt(TextureTransformer::order))
            .toList();

    public static final Map<String, String> DIRECTORY_LOCATIONS = Map.of(
            "block", "blocks",
            "item", "items",
            "gui", "ui"
    );

    @Override
    public void convert(@NotNull PackConversionContext<TextureConversionData> context) throws Exception {
        TextureMappings mappings = TextureMappings.textureMappings();

        List<Texture> textures = new ArrayList<>(context.javaResourcePack().textures());

        context.info("Transforming textures...");
        TransformContext transformContext = new TransformContext(
                context,
                mappings,
                textures,
                context.bedrockResourcePack(),
                context.javaResourcePack()
        );
        for (TextureTransformer transformer : this.transformers) {
            transformer.transform(transformContext);
        }
        context.info("Transformed textures!");

        context.info("Writing textures...");

        for (Texture texture : textures) {
            String input = texture.key().value();
            Path texturePath = context.outputDirectory().resolve(BEDROCK_TEXTURES_LOCATION);
            Path potentialOutput = texturePath.resolve(input);
            String relativePath = texturePath.relativize(potentialOutput).toString().replace(File.separatorChar, '/');

            if (relativePath.endsWith(".png")) relativePath = relativePath.substring(0, relativePath.length() - 4);

            String rootPath = relativePath.substring(0, relativePath.indexOf('/'));
            String bedrockRoot = DIRECTORY_LOCATIONS.getOrDefault(rootPath, rootPath);

            List<Path> outputs = new ArrayList<>();
            List<String> outputPaths = new ArrayList<>();

            Object mappingObject = mappings.textures(relativePath);

            if (mappingObject == null) {
                mappingObject = mappings.textures(rootPath);
            }

            String fallbackPath = bedrockRoot + "/" + relativePath.substring(relativePath.indexOf('/') + 1) + ".png";
            if (mappingObject instanceof Map<?,?> keyMappings) { // Handles common subdirectories
                String sanitizedName = input.substring(input.indexOf('/') + 1);
                if (sanitizedName.endsWith(".png")) sanitizedName = sanitizedName.substring(0, sanitizedName.length() - 4);

                Object bedrockOutput = keyMappings.get(sanitizedName);
                if (bedrockOutput instanceof String bedrockPath) {
                    outputPaths.add(bedrockRoot + "/" + bedrockPath + ".png");
                } else if (bedrockOutput instanceof List<?> paths) {
                    for (String bedrockPath : (List<String>) paths) {
                        outputPaths.add(bedrockRoot + "/" + bedrockPath + ".png");
                    }
                } else { // Fallback
                    outputPaths.add(fallbackPath);
                }
            } else if (mappingObject instanceof String str) { // Direct mappings
                outputPaths.add(str + ".png");
            } else if (mappingObject instanceof List<?> paths) { // Mappings where duplicate code paths exist
                for (String path : (List<String>) paths) {
                    outputPaths.add(path + ".png");
                }
            } else { // Fallback
                outputPaths.add(fallbackPath);
            }

            String bedrockDirectory = "%s/%s";
            if (context.data().textureSubdirectory() != null) {
                bedrockDirectory = "%s/" + context.data().textureSubdirectory() + "/%s";
            }

            for (String outputPath : outputPaths) {
                context.debug(String.format("Converted %s to %s, writing texture.", input, outputPath));

                String root = outputPath.substring(0, outputPath.indexOf('/'));
                String value = outputPath.substring(outputPath.indexOf('/') + 1);

                outputs.add(texturePath.resolve((
                        bedrockDirectory.formatted(root, value)
                ).replace('/', File.separatorChar)));
            }

            byte[] bytes = texture.data().toByteArray();

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

            for (Path output : outputs) {
                TransformedTexture transformedTexture = new TransformedTexture(texture, output);

                if (output.getParent() != null && Files.notExists(output.getParent())) {
                    Files.createDirectories(output.getParent());
                }

                BufferedImage bedrockImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Graphics2D g = bedrockImage.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, null);
                g.dispose();

                String pngKey = context.outputDirectory().relativize(output).toString().replace(File.separatorChar, '/');
                PngToTgaMappings.TgaMapping mapping = PngToTgaMappings.mapping(pngKey);
                if (mapping != null) {
                    Path tgaPath = context.outputDirectory().resolve(mapping.value());
                    if (Files.notExists(tgaPath.getParent())) {
                        Files.createDirectories(tgaPath.getParent());
                    }

                    ImageUtil.writeTGA(tgaPath, bedrockImage);
                    if (!mapping.keep()) {
                        Files.deleteIfExists(output);
                        continue;
                    }
                }

                try (OutputStream stream = Files.newOutputStream(output)) {
                    ImageIO.write(bedrockImage, "png", stream);
                }

                context.data().addTransformedTexture(transformedTexture);
            }
        }

        context.info("Written textures!");

        context.info("Texture conversion complete!");
    }

    @Override
    public TextureConversionData createConversionData(@NotNull ConversionDataCreationContext context) {
        return new TextureConversionData(
                context.inputDirectory(),
                context.outputDirectory(),
                context.converter().textureSubdirectory(),
                context.vanillaResourcePack()
        );
    }
}
