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
public class ChestSideTransformer implements TextureTransformer {
    private static final List<ChestData> CHESTS = List.of(
            new ChestData("entity/chest/normal.png", "blocks/chest_side.png"),
            new ChestData("entity/chest/ender.png", "blocks/ender_chest_side.png")
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ChestData chest : CHESTS) {
            Texture texture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, chest.javaName()));
            if (texture == null) {
                continue;
            }

            context.info(String.format("Creating chest side texture %s", chest.bedrockName()));

            BufferedImage fromImage = ImageUtil.ensureMinWidth(this.readImage(texture), 64);

            int factor = fromImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage((14 * factor), (14 * factor), BufferedImage.TYPE_INT_ARGB);

            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(ImageUtil.crop(fromImage, (28 * factor), (14 * factor), (14 * factor), (5 * factor)), 0, 0, null);
            graphics.drawImage(ImageUtil.crop(fromImage, (28 * factor), (34 * factor), (14 * factor), (9 * factor)), 0, (5 * factor), null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, chest.bedrockName()), newImage, "png");
        }
    }

    record ChestData(@NotNull String javaName, @NotNull String bedrockName) {

    }
}
