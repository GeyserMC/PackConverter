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

package org.geysermc.pack.converter.converter.texture.transformer.type.entity;

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
import java.util.List;

@AutoService(TextureTransformer.class)
public class ZombieTransformer implements TextureTransformer {
    private static final List<ZombieData> ZOMBIES = List.of(
            new ZombieData("zombie", true),
            new ZombieData("husk", false)
    );

    private static final String TEXTURE_PATH = "entity/zombie/%s.png";
    private static final String MOB_HEAD_PATH = "entity/skulls/%s.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ZombieData zombieData : ZOMBIES) {
            String zombie = zombieData.name();
            Key path = Key.key(Key.MINECRAFT_NAMESPACE, TEXTURE_PATH.formatted(zombie));
            Texture texture = context.poll(path);
            if (texture == null) continue;

            BufferedImage javaImage = this.readImage(texture);

            int scale = javaImage.getWidth() / 64;

            BufferedImage bedrockImage = new BufferedImage(javaImage.getWidth(), scale * 32, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(ImageUtil.crop(javaImage, 0, 0, javaImage.getWidth(), scale * 32), 0, 0, null);

            context.offer(path, bedrockImage, "png");

            if (zombieData.mobHead()) {
                context.offer(Key.key(Key.MINECRAFT_NAMESPACE, MOB_HEAD_PATH.formatted(zombie)), bedrockImage, "png");
            }
        }
    }

    private record ZombieData(String name, boolean mobHead) {}
}
