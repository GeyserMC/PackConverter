/*
 * Copyright (c) 2025-2025 GeyserMC. http://geysermc.org
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

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.converter.texture.transformer.TransformedTexture;
import org.geysermc.pack.converter.converter.AssetCombiner;
import org.geysermc.pack.converter.converter.AssetExtractor;
import org.geysermc.pack.converter.converter.CombineContext;
import org.geysermc.pack.converter.converter.ConversionContext;
import org.geysermc.pack.converter.converter.ExtractionContext;
import org.geysermc.pack.converter.converter.KeyedAssetConverter;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class TextureConverter implements AssetExtractor<Texture>, KeyedAssetConverter<Texture, TransformedTexture>, AssetCombiner<TransformedTexture> {
    public static final TextureConverter INSTANCE = new TextureConverter();
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
    public Collection<Texture> extract(ResourcePack pack, ExtractionContext context) {
        TextureMappings mappings = TextureMappings.textureMappings();
        List<Texture> textures = new ArrayList<>(pack.textures());

        context.info("Transforming textures...");
        TransformContext transformContext = new TransformContext(
                mappings,
                textures,
                context.bedrockResourcePack(),
                pack,
                context.vanillaPack(),
                context.logListener()
        );
        for (TextureTransformer transformer : this.transformers) {
            try {
                transformer.transform(transformContext);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        context.info("Transformed textures!");

        return textures;
    }

    @Override
    public @Nullable TransformedTexture convert(Texture texture, ConversionContext context) throws Exception {
        TextureMappings mappings = TextureMappings.textureMappings();
        TransformedTexture transformed = new TransformedTexture(texture);

        String input = texture.key().value();
        String relativePath = input.replaceAll("\\.png$", "");

        String rootPath = relativePath.substring(0, relativePath.indexOf('/'));
        String bedrockRoot = DIRECTORY_LOCATIONS.getOrDefault(rootPath, rootPath);

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
                transformed.output(bedrockRoot + "/" + bedrockPath + ".png");
            } else if (bedrockOutput instanceof List<?> paths) {
                for (String bedrockPath : (List<String>) paths) {
                    transformed.output(bedrockRoot + "/" + bedrockPath + ".png");
                }
            } else { // Fallback
                transformed.output(fallbackPath);
            }
        } else if (mappingObject instanceof String str) { // Direct mappings
            transformed.output(str + ".png");
        } else if (mappingObject instanceof List<?> paths) { // Mappings where duplicate code paths exist
            for (String path : (List<String>) paths) {
                transformed.output(path + ".png");
            }
        } else { // Fallback
            transformed.output(fallbackPath);
        }

        return transformed;
    }

    @Override
    public void include(BedrockResourcePack pack, List<TransformedTexture> transformedTextures, CombineContext context) {
        Path texturePath = pack.directory().resolve(BEDROCK_TEXTURES_LOCATION);
        List<String> exportedPaths = new ArrayList<>();

        for (TransformedTexture textureToExport : transformedTextures) {
            String bedrockDirectory = "%s/%s";
            if (context.textureSubDirectory() != null) {
                bedrockDirectory = "%s/" + context.textureSubDirectory() + "/%s";
            }

            List<Path> outputs = new ArrayList<>();
            for (String outputPath : textureToExport.output()) {
                if (exportedPaths.contains(outputPath)) {
                    context.warn("Conflicting texture " + outputPath + "!");
                    continue;
                }
                exportedPaths.add(outputPath);

                String root = outputPath.substring(0, outputPath.indexOf('/'));
                String value = outputPath.substring(outputPath.indexOf('/') + 1);

                outputs.add(texturePath.resolve((
                        bedrockDirectory.formatted(root, value)
                ).replace('/', File.separatorChar)));
            }

            try {
                byte[] bytes = textureToExport.texture().data().toByteArray();

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

                for (Path output : outputs) {
                    if (output.getParent() != null && Files.notExists(output.getParent())) {
                        Files.createDirectories(output.getParent());
                    }

                    BufferedImage bedrockImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g = bedrockImage.createGraphics();
                    g.setComposite(AlphaComposite.Src);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();

                    String pngKey = pack.directory().relativize(output).toString().replace(File.separatorChar, '/');
                    PngToTgaMappings.TgaMapping mapping = PngToTgaMappings.mapping(pngKey);
                    if (mapping != null) {
                        Path tgaPath = pack.directory().resolve(mapping.value());
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
                }
            } catch (IOException exception) {
                context.error("Failed to write texture " + textureToExport.texture().key() + "!", exception);
            }
        }
    }
}
