/*
 * Copyright (c) 2026 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture.transformer.type.particle;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.KeyUtil;
import org.geysermc.pack.converter.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GridSpritesheetParticleTransformer implements TextureTransformer {
    private final String[] javaPaths;
    private final String bedrockPath;
    private final int rows;
    private final int columns;
    private final int particleWidth;
    private final int particleHeight;

    public GridSpritesheetParticleTransformer(String bedrockPath, String javaPath, int rows, int columns, int particleWidth, int particleHeight) {
        String[] javaPaths = new String[rows * columns];
        for (int i = 0; i < rows * columns; i++) {
            javaPaths[i] = javaPath.formatted(i);
        }
        this.javaPaths = javaPaths;
        this.bedrockPath = bedrockPath;
        this.rows = rows;
        this.columns = columns;
        this.particleWidth = particleWidth;
        this.particleHeight = particleHeight;
    }

    public GridSpritesheetParticleTransformer(String bedrockPath, int rows, int columns, int particleWidth, int particleHeight, String... javaPaths) {
        this.javaPaths = javaPaths;
        this.bedrockPath = bedrockPath;
        this.rows = rows;
        this.columns = columns;
        this.particleWidth = particleWidth;
        this.particleHeight = particleHeight;
    }

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        List<ItemData> itemDatas = new ArrayList<>();
        boolean anyTexturePresent = false;

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String javaPath = javaPaths[k++];
                Key key = javaPath == null ? null : KeyUtil.key(Key.MINECRAFT_NAMESPACE, javaPath);
                Texture texture = context.pollOrPeekVanilla(key);
                itemDatas.add(new ItemData(
                        key,
                        texture,
                        j * particleWidth,
                        i * particleHeight
                ));

                if (texture != null) anyTexturePresent = true;
            }
        }

        if (!anyTexturePresent) return;

        context.debug(String.format("Creating particle spritesheet %s", this.bedrockPath));

        BufferedImage image = new BufferedImage(columns * particleWidth, rows * particleHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        for (ItemData itemData : itemDatas) {
            if (itemData.texture() == null) {
                context.warn("%s is missing. Please report this.".formatted(itemData.key()));
                continue;
            }

            BufferedImage javaImage = this.preProcessImage(this.readImage(itemData.texture()));

            g.drawImage(javaImage, itemData.x(), itemData.y(), null);
        }

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, this.bedrockPath), image, "png");
    }

    public BufferedImage preProcessImage(BufferedImage image) {
        return image;
    }

    private record ItemData(@Nullable Key key, @Nullable Texture texture, int x, int y) {}

    public static abstract class Row extends GridSpritesheetParticleTransformer {
        public Row(String bedrockPath, int particleWidth, int particleHeight, String javaPath, int amount) {
            super(bedrockPath, 1, amount, particleWidth, particleHeight, StringUtils.formatStringFor(javaPath, amount));
        }

        public Row(String bedrockPath, int particleWidth, int particleHeight, String... javaPaths) {
            super(bedrockPath, 1, javaPaths.length, particleWidth, particleHeight, javaPaths);
        }
    }

    public static abstract class Column extends GridSpritesheetParticleTransformer {
        public Column(String bedrockPath, int particleWidth, int particleHeight, String javaPath, int amount) {
            super(bedrockPath, amount, 1, particleWidth, particleHeight, StringUtils.formatStringFor(javaPath, amount));
        }

        public Column(String bedrockPath, int particleWidth, int particleHeight, String... javaPaths) {
            super(bedrockPath, javaPaths.length, 1, particleWidth, particleHeight, javaPaths);
        }
    }
}
