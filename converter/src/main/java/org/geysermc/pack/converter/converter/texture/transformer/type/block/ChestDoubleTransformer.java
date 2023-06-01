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

package org.geysermc.pack.converter.converter.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class ChestDoubleTransformer implements TextureTransformer {
    private static final List<ChestData> CHEST_DATA = List.of(
            new ChestData("entity/chest/normal_left.png", "entity/chest/normal_right.png", "entity/chest/double_normal.png"),
            new ChestData("entity/chest/trapped_left.png", "entity/chest/trapped_right.png", "entity/chest/trapped_double.png"),
            new ChestData("entity/chest/christmas_left.png", "entity/chest/christmas_right.png", "entity/chest/christmas_double.png")
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ChestData chest : CHEST_DATA) {
            Texture leftTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, chest.javaNameLeft()));
            Texture rightTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, chest.javaNameRight()));
            if (leftTexture == null || rightTexture == null) {
                continue;
            }

            context.info(String.format("Creating double chest texture %s", chest.bedrockName()));

            BufferedImage leftImage = ImageUtil.ensureMinWidth(this.readImage(leftTexture), 64);
            BufferedImage rightImage = ImageUtil.ensureMinWidth(this.readImage(rightTexture), 64);

            int factor = leftImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage((128 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = newImage.getGraphics();

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, 0, (14 * factor), (14 * factor), (5 * factor)), 180), 0, (14 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (29 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (44 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, 0, (33 * factor), (14 * factor), (10 * factor)), 180), 0, (33 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (29 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (44 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(rightImage, (29 * factor), 0, (15 * factor), (14 * factor)), false, true), (14 * factor), 0, null);
            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(leftImage, (29 * factor), 0, (15 * factor), (14 * factor)), false, true), (29 * factor), 0, null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, (43 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (14 * factor), (14 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (43 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (29 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(rightImage, (29 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (14 * factor), (19 * factor), null);
            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(leftImage, (29 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (29 * factor), (19 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, (43 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (14 * factor), (33 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (43 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (29 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(rightImage, (14 * factor), 0, (15 * factor), (14 * factor)), false, true), (44 * factor), 0, null);
            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(leftImage, (14 * factor), 0, (15 * factor), (14 * factor)), false, true), (59 * factor), 0, null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(rightImage, (14 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (44 * factor), (19 * factor), null);
            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(leftImage, (14 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (59 * factor), (19 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (73 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (58 * factor), (14 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (73 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(leftImage, (14 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (58 * factor), (33 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(rightImage, (14 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (73 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.crop(leftImage, 0, 0, (6 * factor), (6 * factor)), 0, 0, null);
            graphics.drawImage(ImageUtil.crop(rightImage, 0, 0, (6 * factor), (6 * factor)), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, chest.bedrockName()), newImage, "png");
        }
    }

    record ChestData(@NotNull String javaNameLeft, @NotNull String javaNameRight, @NotNull String bedrockName) {
    }
}
