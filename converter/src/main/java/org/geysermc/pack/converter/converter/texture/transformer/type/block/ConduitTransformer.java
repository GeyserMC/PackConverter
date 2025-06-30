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

package org.geysermc.pack.converter.converter.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class ConduitTransformer implements TextureTransformer {
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture baseTexture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "entity/conduit/base.png"));
        if (baseTexture != null) {
            BufferedImage baseImage = this.readImage(baseTexture);

            int scale = baseImage.getHeight() / 16;

            BufferedImage bedrockBaseImage = new BufferedImage((24 * scale), (12 * scale), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockBaseImage.getGraphics();
            g.drawImage(ImageUtil.crop(baseImage, 0, 0, (24 * scale), (12 * scale)), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "blocks/conduit_base.png"), bedrockBaseImage, "png");
        }

        Texture eyeTexture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "entity/conduit/closed_eye.png"));
        if (eyeTexture != null) {
            BufferedImage image = this.readImage(eyeTexture);

            int scale = image.getHeight() / 16;

            BufferedImage bedrockImage = new BufferedImage((8 * scale), (8 * scale), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();
            g.drawImage(ImageUtil.crop(image, 0, 0, (8 * scale), (8 * scale)), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "blocks/conduit_closed.png"), bedrockImage, "png");
        }

        Texture eyeOpenTexture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "entity/conduit/open_eye.png"));
        if (eyeOpenTexture != null) {
            BufferedImage image = this.readImage(eyeOpenTexture);

            int scale = image.getHeight() / 16;

            BufferedImage bedrockImage = new BufferedImage((8 * scale), (8 * scale), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();
            g.drawImage(ImageUtil.crop(image, 0, 0, (8 * scale), (8 * scale)), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "blocks/conduit_open.png"), bedrockImage, "png");
        }
    }
}
