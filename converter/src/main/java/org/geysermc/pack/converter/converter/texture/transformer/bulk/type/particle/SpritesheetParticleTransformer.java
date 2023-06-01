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

package org.geysermc.pack.converter.converter.texture.transformer.bulk.type.particle;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.Spritesheet;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.BitSet;

public class SpritesheetParticleTransformer implements BulkTextureTransformer {
    private final String javaPath;
    private final String bedrockPath;
    private final String vanillaSpritesheet;
    private final int atlasCount;

    public SpritesheetParticleTransformer(String javaPath, String bedrockPath, String vanillaSpritesheet, int atlasCount) {
        this.javaPath = javaPath;
        this.bedrockPath = bedrockPath;
        this.vanillaSpritesheet = vanillaSpritesheet;
        this.atlasCount = atlasCount;
    }

    @Override
    public void transform(@NotNull BulkTransformContext context) throws IOException {
        int size = -1;
        BitSet occupiedSectors = new BitSet(this.atlasCount);
        Spritesheet spritesheet = new Spritesheet();
        for (int i = 0; i < this.atlasCount; i++) {
            Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, String.format(this.javaPath, i)));
            if (texture == null) {
                continue;
            }

            BufferedImage image = this.readImage(texture);
            if (size == -1) {
                size = image.getWidth();
            }

            occupiedSectors.set(i);
            spritesheet.addRow(image);
        }

        if (!spritesheet.hasSprites() || size == -1) {
            return;
        }

        BufferedImage spriteImage = spritesheet.compile();
        BufferedImage vanillaSprite = ImageUtil.resize(ImageUtil.loadImage("/" + this.vanillaSpritesheet + ".png"), spriteImage.getWidth(), spriteImage.getHeight());

        Graphics2D graphics = vanillaSprite.createGraphics();
        graphics.setBackground(new Color(0, 0, 0, 0));

        for (int i = 0; i < occupiedSectors.size(); i++) {
            if (occupiedSectors.get(i)) {
                int y = i * size;
                graphics.clearRect(0, y, size, size);
            }
        }

        graphics.drawImage(spriteImage, 0, 0, null);
        graphics.dispose();

        context.info(String.format("Creating particle spritesheet %s", this.bedrockPath));

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, this.bedrockPath), vanillaSprite, "png");
    }
}
