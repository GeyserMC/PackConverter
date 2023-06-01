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

package org.geysermc.pack.converter.converter.texture.transformer.type.entity;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class SheepTransformer implements TextureTransformer {
    private static final String SHEEP = "entity/sheep/sheep.png";
    private static final String SHEEP_FUR = "entity/sheep/sheep_fur.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture sheepTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, SHEEP));
        Texture sheepFurTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, SHEEP_FUR));

        if (sheepTexture == null || sheepFurTexture == null) {
            return;
        }

        context.info(String.format("Converting sheep texture %s", SHEEP));

        BufferedImage sheepImage = this.readImage(sheepTexture);
        BufferedImage sheepFurImage = this.readImage(sheepFurTexture);

        int width = Math.max(sheepImage.getWidth(), sheepFurImage.getWidth());
        sheepImage = ImageUtil.ensureMinWidth(sheepImage, width);
        sheepFurImage = ImageUtil.ensureMinWidth(sheepFurImage, width);

        BufferedImage newImage = new BufferedImage(sheepImage.getWidth(), sheepImage.getHeight() + sheepFurImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();

        g.drawImage(sheepImage, 0, 0, null);

        g.drawImage(sheepFurImage, 0, sheepImage.getHeight(), null);

        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < sheepImage.getHeight(); y++) {
                Color c = new Color(newImage.getRGB(x, y), true);
                if (c.getAlpha() == 255) {
                    Color tmpCol = new Color(c.getRed(), c.getGreen(), c.getBlue(), 1);
                    newImage.setRGB(x, y, tmpCol.getRGB());
                }
            }
        }

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, SHEEP), newImage, "png");
    }
}
