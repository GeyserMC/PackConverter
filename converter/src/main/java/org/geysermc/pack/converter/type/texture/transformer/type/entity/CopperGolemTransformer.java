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

package org.geysermc.pack.converter.type.texture.transformer.type.entity;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CopperGolemTransformer implements TextureTransformer {
    private static final Key POPPY = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/poppy.png");
    private static final Key COPPER_POPPY = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/copper_golem/copper_golem_flower.png");

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        if (context.isTexturePresent(POPPY)) {
            Texture texture = context.peek(POPPY);
            if (texture == null) return;
            BufferedImage javaImage = this.readImage(texture);

            float scale = javaImage.getHeight() / 16f;

            BufferedImage bedrockImage = new BufferedImage((int) (scale * 4), (int) (scale * 4), BufferedImage.TYPE_INT_ARGB);

            bedrockImage.getGraphics().drawImage(javaImage, (int) (44 * scale), (int) (33 * scale), null);

            context.offer(COPPER_POPPY, bedrockImage, "png");
        }
    }
}
