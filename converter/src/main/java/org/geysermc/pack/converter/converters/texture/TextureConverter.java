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

package org.geysermc.pack.converter.converters.texture;

import com.google.auto.service.AutoService;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converters.BaseConverter;
import org.geysermc.pack.converter.converters.Converter;
import org.geysermc.pack.converter.data.BaseConversionData;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@AutoService(Converter.class)
public class TextureConverter extends BaseConverter {
    public static final String BEDROCK_TEXTURES_LOCATION = "textures";

    private final List<TextureTransformer> transformers = StreamSupport.stream(ServiceLoader.load(TextureTransformer.class).spliterator(), false).toList();

    @Override
    public void convert(@NotNull PackConversionContext<BaseConversionData> context) throws Exception {
        Collection<Texture> textures = context.javaResourcePack().textures();

        for (Texture texture : textures) {
            String output = texture.key().value();
            Path outputPath = context.outputDirectory().resolve(BEDROCK_TEXTURES_LOCATION).resolve(output);

            TransformedTexture transformedTexture = new TransformedTexture(texture, outputPath);
            for (TextureTransformer transformer : this.transformers) {
                if (!transformer.filter(texture)) {
                    continue;
                }

                transformedTexture = transformer.transform(context, transformedTexture);

                if (transformedTexture == null) {
                    break;
                }
            }

            if (transformedTexture != null) {
                Path textureOutput = transformedTexture.output();
                if (textureOutput.getParent() != null && Files.notExists(textureOutput.getParent())) {
                    Files.createDirectories(textureOutput.getParent());
                }

                try (OutputStream stream = Files.newOutputStream(textureOutput)) {
                    texture.data().write(stream);
                }
            }
        }
    }
}
