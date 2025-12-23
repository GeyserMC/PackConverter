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

package org.geysermc.pack.converter.type.texture.transformer.type.entity;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class ArrowTransformer implements TextureTransformer {
    private static final String INPUT = "entity/projectiles/arrow.png";
    private static final String OUTPUT = "entity/arrows.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture texture = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, INPUT));
        if (texture == null) {
            return;
        }

        context.debug(String.format("Converting arrow texture %s", OUTPUT));

        BufferedImage fromImage = this.readImage(texture);

        float factor = fromImage.getWidth() / 32f;
        BufferedImage newArrowImage = new BufferedImage((int) (32 * factor), (int) (32 * factor), BufferedImage.TYPE_INT_ARGB);
        fromImage = ImageUtil.crop(fromImage, 0, 0, fromImage.getWidth(), 10 * factor);

        Graphics graphics = newArrowImage.getGraphics();
        graphics.drawImage(fromImage, 0, 0, null);

        fromImage = ImageUtil.grayscale(fromImage);

        for (int x = 0; x < fromImage.getWidth(); x++) {
            for (int y = 0; y < fromImage.getHeight(); y++) {
                Color c = new Color(fromImage.getRGB(x, y), true);
                if (c.getRed() < 192 || c.getGreen() < 192 || c.getBlue() < 192) {
                    Color newCol = new Color(c.getRed() / 255 * 186, c.getGreen() / 255 * 98, c.getBlue() / 255 * 168, c.getAlpha());
                    fromImage.setRGB(x, y, newCol.getRGB());
                }
            }
        }

        graphics.drawImage(fromImage, 0, (int) (10 * factor), null);

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, OUTPUT), newArrowImage, "png");
    }
}
