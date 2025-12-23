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

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class CamelTransformer implements TextureTransformer {
    private static final Key CAMEL = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/camel/camel.png");
    private static final Key CAMEL_SADDLE = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/equipment/camel_saddle/saddle.png");
    private static final Key CAMEL_HUSK = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/camel/camel_husk.png");
    private static final Key CAMEL_HUSK_BEDROCK = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/camel_husk/camel_husk.png");
    private static final Key CAMEL_HUSK_SADDLE = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/equipment/camel_husk_saddle/saddle.png");

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        if (context.isTexturePresent(CAMEL) || context.isTexturePresent(CAMEL_SADDLE)) {
            Texture camelTexture = context.pollOrPeekVanilla(CAMEL);
            Texture saddleTexture = context.pollOrPeekVanilla(CAMEL_SADDLE);
            handleCamel(context, camelTexture, saddleTexture, CAMEL);
        }

        if (context.isTexturePresent(CAMEL_HUSK) || context.isTexturePresent(CAMEL_HUSK_SADDLE)) {
            Texture camelTexture = context.pollOrPeekVanilla(CAMEL_HUSK);
            Texture saddleTexture = context.pollOrPeekVanilla(CAMEL_HUSK_SADDLE);
            handleCamel(context, camelTexture, saddleTexture, CAMEL_HUSK_BEDROCK);
        }
    }

    private void handleCamel(@NotNull TransformContext context, Texture camelTexture, Texture saddleTexture, Key bedrockKey) throws IOException {
        BufferedImage camelImage = this.readImage(camelTexture);
        if (camelImage == null) {
            context.error("Unable to read camel image! (%s) Skipping...".formatted(camelTexture.key().asString()));
            return;
        }

        BufferedImage saddleImage = this.readImage(saddleTexture);
        if (saddleImage == null) {
            context.error("Unable to read camel saddle image! (%s) Skipping...".formatted(saddleTexture.key().asString()));
            return;
        }

        float scale = camelImage.getWidth() / 128f;

        float saddlePreScale = scale / (saddleImage.getWidth() / 128f);

        BufferedImage scaledSaddleImage = ImageUtil.scale(saddleImage, saddlePreScale);

        Graphics graphics = camelImage.getGraphics();

        graphics.drawImage(
                ImageUtil.crop(
                        scaledSaddleImage,
                        0, 64 * scale,
                        128 * scale, 64 * scale
                ),
                0, (int) (64 * scale), null
        );

        graphics.drawImage(
                ImageUtil.crop(
                        scaledSaddleImage,
                        84 * scale, 51 * scale,
                        44 * scale, 13 * scale
                ),
                (int) (84 * scale), (int) (51 * scale), null
        );

        context.offer(bedrockKey, camelImage, "png");
    }
}
