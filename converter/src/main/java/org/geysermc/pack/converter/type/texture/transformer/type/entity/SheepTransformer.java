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
public class SheepTransformer implements TextureTransformer {
    public static final String SHEEP = "entity/sheep/sheep.png";
    public static final String SHEEP_WOOL = "entity/sheep/sheep_wool.png";
    public static final String SHEEP_UNDERCOAT = "entity/sheep/sheep_wool_undercoat.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Key sheepKey = KeyUtil.key(Key.MINECRAFT_NAMESPACE, SHEEP);
        Key woolKey = KeyUtil.key(Key.MINECRAFT_NAMESPACE, SHEEP_WOOL);
        Key undercoatKey = KeyUtil.key(Key.MINECRAFT_NAMESPACE, SHEEP_UNDERCOAT);

        if (
                !context.isTexturePresent(sheepKey) &&
                !context.isTexturePresent(woolKey) &&
                !context.isTexturePresent(undercoatKey)
        ) return;

        Texture sheepTexture = context.pollOrPeekVanilla(sheepKey);
        Texture sheepWoolTexture = context.pollOrPeekVanilla(woolKey);
        Texture sheepUndercoatTexture = context.pollOrPeekVanilla(undercoatKey);

        if (sheepTexture == null || sheepWoolTexture == null || sheepUndercoatTexture == null) {
            return;
        }

        context.debug(String.format("Converting sheep texture %s", SHEEP));

        BufferedImage sheepImage = this.readImage(sheepTexture);
        BufferedImage sheepWoolImage = this.readImage(sheepWoolTexture);
        BufferedImage sheepUndercoatImage = this.readImage(sheepUndercoatTexture);

        int width = Math.max(Math.max(sheepImage.getWidth(), sheepWoolImage.getWidth()), sheepUndercoatImage.getWidth());
        sheepImage = ImageUtil.ensureMinWidth(sheepImage, width);
        sheepWoolImage = ImageUtil.ensureMinWidth(sheepWoolImage, width);
        sheepUndercoatImage = ImageUtil.ensureMinWidth(sheepUndercoatImage, width);

        BufferedImage newImage = new BufferedImage(sheepImage.getWidth(), sheepImage.getHeight() + sheepWoolImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();

        for (int x = 0; x < sheepImage.getWidth(); x++) {
            for (int y = 0; y < sheepImage.getHeight(); y++) {
                Color c = new Color(sheepImage.getRGB(x, y), true);
                if (c.getAlpha() == 255) {
                    Color tmpCol = new Color(c.getRed(), c.getGreen(), c.getBlue(), 2);
                    newImage.setRGB(x, y, tmpCol.getRGB());
                }
            }
        }

        g.drawImage(sheepUndercoatImage, 0, 0, null);

        g.drawImage(sheepWoolImage, 0, sheepImage.getHeight(), null);

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, SHEEP), newImage, "png");
    }
}
