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
import com.google.gson.Gson;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.PackConverter;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformedTexture;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTransformContext;
import org.geysermc.pack.converter.data.TextureConversionData;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@AutoService(Converter.class)
public class TextureConverter implements Converter<TextureConversionData> {
    public static final String BEDROCK_TEXTURES_LOCATION = "textures";

    private final List<BulkTextureTransformer> bulkTransformers = StreamSupport.stream(ServiceLoader.load(BulkTextureTransformer.class).spliterator(), false).toList();
    private final List<TextureTransformer> transformers = StreamSupport.stream(ServiceLoader.load(TextureTransformer.class).spliterator(), false).toList();

    @Override
    public void convert(@NotNull PackConversionContext<TextureConversionData> context) throws Exception {
        InputStream mappingsStream = this.getClass().getResourceAsStream("/mappings/textures.json");
        if (mappingsStream == null) {
            throw new RuntimeException("Could not find textures.json mappings file!");
        }

        TextureMappings mappings = new Gson().fromJson(new InputStreamReader(mappingsStream), TextureMappings.class);

        List<Texture> textures = new ArrayList<>(context.javaResourcePack().textures());

        context.info("Bulk transforming textures...");
        BulkTransformContext bulkContext = new BulkTransformContext(context, mappings, textures);
        for (BulkTextureTransformer bulkTransformer : this.bulkTransformers) {
            bulkTransformer.transform(bulkContext);
        }

        context.info("Transforming and writing textures...");
        for (Texture texture : textures) {
            String output = texture.key().value();
            Path outputPath = context.outputDirectory().resolve(BEDROCK_TEXTURES_LOCATION).resolve(output);

            TransformedTexture transformedTexture = new TransformedTexture(texture, outputPath);
            for (TextureTransformer transformer : this.transformers) {
                if (!transformer.filter(texture)) {
                    continue;
                }

                transformedTexture = transformer.transform(context, mappings, transformedTexture);
                if (transformedTexture == null) {
                    break;
                }
            }

            if (transformedTexture != null) {
                Path textureOutput = transformedTexture.output();
                if (textureOutput.getParent() != null && Files.notExists(textureOutput.getParent())) {
                    Files.createDirectories(textureOutput.getParent());
                }

                byte[] bytes = texture.data().toByteArray();

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

                String pngKey = "textures/" + texture.key().value();
                PngToTgaMappings.TgaMapping mapping = PngToTgaMappings.mapping(pngKey);
                if (mapping != null) {
                    Path tgaPath = context.outputDirectory().resolve(mapping.value());
                    if (Files.notExists(tgaPath.getParent())) {
                        Files.createDirectories(tgaPath.getParent());
                    }

                    try (OutputStream tgaStream = Files.newOutputStream(tgaPath)) {
                        ImageIO.write(image, "tga", tgaStream);
                        if (!mapping.keep()) {
                            Files.deleteIfExists(textureOutput);
                            continue;
                        }
                    }
                }

                if (!image.getColorModel().hasAlpha()) {
                    BufferedImage newImage = new BufferedImage(
                            image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g = newImage.createGraphics();
                    g.drawImage(image, 0, 0, null);
                    g.dispose();

                    image = newImage;
                }

                try (OutputStream stream = Files.newOutputStream(textureOutput)) {
                    ImageIO.write(image, "png", stream);
                }

                context.data().addTransformedTexture(transformedTexture);
            }
        }

        context.info("Texture conversion complete!");
    }

    @Override
    public TextureConversionData createConversionData(@NotNull PackConverter converter, @NotNull Path inputDirectory, @NotNull Path outputDirectory) {
        return new TextureConversionData(inputDirectory, outputDirectory);
    }
}
