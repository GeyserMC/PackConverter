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

package org.geysermc.pack.converter.type.texture.transformer.type.ui;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class TitleTransformer implements TextureTransformer {
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture javaTexture = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "gui/title/minecraft.png"));
        if (javaTexture == null) return;

        BufferedImage javaImage = this.readImage(javaTexture);

        float scale = javaImage.getHeight() / 256f;

        // Magic numbers! title.png width and height, bedrock doesn't appear it like different sizes, so we scale
        BufferedImage bedrockImage = new BufferedImage(1937, 333, BufferedImage.TYPE_INT_ARGB);

        Graphics g = bedrockImage.getGraphics();

        g.drawImage(ImageUtil.resize(ImageUtil.crop(javaImage, 0, 0, javaImage.getWidth(), javaImage.getHeight() - ((int) (scale * 79))), 1937, 333), 0, 0, null);

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "ui/title.png"), bedrockImage, "png");
    }
}
