/*
 * Copyright (c) 2026 GeyserMC. http://geysermc.org
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

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class HangingSignTransformer implements TextureTransformer {
    private static final List<String> SIGNS = List.of(
            "acacia_hanging_sign", "bamboo_hanging_sign",
            "birch_hanging_sign", "cherry_hanging_sign",
            "crimson_hanging_sign", "dark_oak_hanging_sign",
            "jungle_hanging_sign", "mangrove_hanging_sign",
            "oak_hanging_sign", "spruce_hanging_sign",
            "warped_hanging_sign"
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (String sign : SIGNS) {
            Texture texture = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + sign + ".png"));
            if (texture == null) continue;

            BufferedImage image = this.readImage(texture);

            BufferedImage bedrockImage = new BufferedImage(image.getWidth() * 2, image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bedrockImage.getGraphics();

            g.drawImage(
                    ImageUtil.crop(
                            image,
                            0, 0, 16, 6
                    ),
                    4, 0, null
            );
            g.drawImage(
                    ImageUtil.crop(
                            image,
                            16, 4, 4, 2
                    ),
                    20, 4, null
            );

            g.drawImage(
                    ImageUtil.crop(
                            image,
                            0, 9, 16, 4
                    ),
                    20, 0, null
            );
            g.drawImage(
                    ImageUtil.crop(
                            image,
                            0, 7, 16, 2
                    ),
                    24, 4, null
            );
            g.drawImage(
                    ImageUtil.crop(
                            image,
                            16, 7, 4, 2
                    ),
                    0, 4, null
            );

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/" + sign + ".png"), bedrockImage, "png");
        }
    }
}
