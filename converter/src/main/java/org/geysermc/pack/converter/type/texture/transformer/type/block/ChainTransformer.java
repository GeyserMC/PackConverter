/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture.transformer.type.block;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ChainTransformer implements TextureTransformer {
    private static final List<ChainData> CHAIN_DATA = List.of(
            new ChainData("chain"),
            new ChainData("copper_chain"),
            new ChainData("exposed_copper_chain"),
            new ChainData("weathered_copper_chain"),
            new ChainData("oxidized_copper_chain")
    );

    private static final String JAVA_NAME = "block/%s.png";
    private static final String BEDROCK_1 = "blocks/%s1.png";
    private static final String BEDROCK_2 = "blocks/%s2.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ChainData chainData : CHAIN_DATA) {
            Key javaKey = KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_NAME.formatted(chainData.name()));

            Texture javaTexture = context.poll(javaKey);
            if (javaTexture == null) continue;

            BufferedImage javaImage = this.readImage(javaTexture);

            float scale = javaImage.getHeight() / 16f;

            Key bedrock1Key = KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_1.formatted(chainData.name()));
            Key bedrock2Key = KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_2.formatted(chainData.name()));

            BufferedImage bedrock1Image = new BufferedImage(javaImage.getWidth(), javaImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            BufferedImage bedrock2Image = new BufferedImage(javaImage.getWidth(), javaImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Yes, 2 than 1, since the numbers are in the wrong order on bedrock, fun.
            bedrock2Image.getGraphics().drawImage(
                    ImageUtil.crop(
                            javaImage,
                            0,
                            0,
                            (int) (3 * scale),
                            javaImage.getHeight()
                    ),
                    0,
                    0,
                    null
            );
            bedrock1Image.getGraphics().drawImage(
                    ImageUtil.crop(
                            javaImage,
                            (int) (3 * scale),
                            0,
                            (int) (6 * scale),
                            javaImage.getHeight()
                    ),
                    0,
                    0,
                    null
            );

            context.offer(bedrock1Key, bedrock1Image, "png");
            context.offer(bedrock2Key, bedrock2Image, "png");
        }
    }

    public record ChainData(String name) {}
}
