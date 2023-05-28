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

package org.geysermc.pack.converter.converter.texture.transformer.path;

import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.texture.TextureConverter;
import org.geysermc.pack.converter.converter.texture.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.TransformedTexture;
import org.geysermc.pack.converter.data.TextureConversionData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.texture.Texture;

import java.nio.file.Path;

public class PathTransformer implements TextureTransformer {
    private final String input;
    private final String output;

    public PathTransformer(@NotNull String input, @NotNull String output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean filter(@NotNull Texture texture) {
        return texture.key().value().startsWith(this.input);
    }

    @Override
    public @Nullable TransformedTexture transform(@NotNull PackConversionContext<TextureConversionData> context, @NotNull TransformedTexture texture) {
        String output = texture.texture().key().value();
        Path outputDir = context.outputDirectory()
                .resolve(TextureConverter.BEDROCK_TEXTURES_LOCATION)
                .resolve(this.output + output.substring(this.input.length()));

        texture.output(outputDir);
        return texture;
    }
}
