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

package org.geysermc.pack.converter.converter.texture.transformer.type.ui;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoService(TextureTransformer.class)
public class FontTransformer implements TextureTransformer {
    private static final List<FontMapping> MAPPINGS = new ArrayList<>();
    private static final Map<String, FontData> FONT_DATA = Map.of(
            "ascii", new FontData(8, 8),
            "accented", new FontData(9, 12, 0.888f, 0.666f),
            "nonlatin_european", new FontData(8, 8)
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        if (
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png")) &&
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png")) &&
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"))
        ) return;

        Map<String, BufferedImage> imgs = new HashMap<>();
        Map<String, Integer> scales = new HashMap<>();

        Texture ascii = context.pollOrPeekVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png"));
        if (ascii != null) {
            BufferedImage image = this.readImage(ascii);
            imgs.put("ascii", image);
            scales.put("ascii", image.getWidth() / 128);
        }

        Texture accented = context.pollOrPeekVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png"));
        if (accented != null) {
            BufferedImage image = this.readImage(accented);
            imgs.put("accented", image);
            scales.put("accented", image.getWidth() / 128);
        }

        Texture nonlatin_european = context.pollOrPeekVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"));
        if (nonlatin_european != null) {
            BufferedImage image = this.readImage(nonlatin_european);
            imgs.put("nonlatin_european", image);
            scales.put("nonlatin_european", image.getWidth() / 128);
        }

        int charWidth = scales.get("ascii") * 8;
        int charHeight = scales.get("ascii") * 8;

        BufferedImage bedrockImage = new BufferedImage(16 * charWidth, 16 * charHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = bedrockImage.getGraphics();

        for (FontMapping fontMapping : MAPPINGS) {
            FontData fontData = FONT_DATA.get(fontMapping.javaTexture);

            int realCharX = fontData.charSizeX * scales.get(fontMapping.javaTexture);
            int realCharY = fontData.charSizeY * scales.get(fontMapping.javaTexture);

            g.drawImage(
                    ImageUtil.scale(
                            ImageUtil.crop(
                                    imgs.getOrDefault(fontMapping.javaTexture, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)),
                                    fontMapping.javaX * realCharX,
                                    fontMapping.javaY * realCharY,
                                    realCharX,
                                    realCharY
                            ),
                            fontData.scaleX,
                            fontData.scaleY
                    ),
                    (fontMapping.bedrockX * charWidth),
                    (fontMapping.bedrockY * charHeight),
                    null
            );
        }

        context.bedrockResourcePack().addExtraFile(ImageUtil.toByteArray(bedrockImage, "png"), "font/default8.png");
    }

    static {
        // These mappings were mostly found by manually work and checking if the unicodes match correctly
        // Some characters were found just by seeing they completely match between default8 (bedrock) and ascii (java)

        // Manual stuff...
        MAPPINGS.add(new FontMapping("accented", 0, 0));
        MAPPINGS.add(new FontMapping("accented", 1, 0));
        MAPPINGS.add(new FontMapping("accented", 2, 0));
        MAPPINGS.add(new FontMapping("accented", 8, 0, 3, 0));
        MAPPINGS.add(new FontMapping("accented", 10, 0, 4, 0));
        MAPPINGS.add(new FontMapping("accented", 11, 0, 5, 0));
        MAPPINGS.add(new FontMapping("accented", 12, 0, 6, 0));
        MAPPINGS.add(new FontMapping("accented", 3, 1, 7, 0));
        MAPPINGS.add(new FontMapping("accented", 4, 1, 8, 0));
        MAPPINGS.add(new FontMapping("accented", 5, 1, 9, 0));
        MAPPINGS.add(new FontMapping("accented", 8, 1, 10, 0));
        MAPPINGS.add(new FontMapping("nonlatin_european", 6, 10, 11, 0));
        MAPPINGS.add(new FontMapping("accented", 3, 0, 12, 0));
        MAPPINGS.add(new FontMapping("accented", 5, 1, 13, 0));
        MAPPINGS.add(new FontMapping("accented", 5, 5, 14, 0));
        MAPPINGS.add(new FontMapping("accented", 6, 6, 15, 0));
        MAPPINGS.add(new FontMapping("accented", 7, 6, 0, 1));
        MAPPINGS.add(new FontMapping("accented", 4, 8, 1, 1));
        MAPPINGS.add(new FontMapping("accented", 5, 8, 2, 1));
        MAPPINGS.add(new FontMapping("accented", 0, 9, 3, 1));
        MAPPINGS.add(new FontMapping("accented", 1, 9, 4, 1));
        MAPPINGS.add(new FontMapping("accented", 6, 10, 5, 1));
        MAPPINGS.add(new FontMapping("accented", 7, 10, 6, 1));
        MAPPINGS.add(new FontMapping("accented", 15, 10, 7, 1));
        MAPPINGS.add(new FontMapping("accented", 14, 15, 8, 1));
        MAPPINGS.add(new FontMapping("nonlatin_european", 15, 37, 9, 1));
        MAPPINGS.add(new FontMapping("nonlatin_european", 13, 14, 10, 1));
        MAPPINGS.add(new FontMapping("nonlatin_european", 15, 11, 11, 1));
        MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 12, 1));

        // We have a few good matching ones, so lets save some time
        int x = 0;
        int y = 2;

        for (int i = 0; i < 92; i++) {
            MAPPINGS.add(new FontMapping(x, y));
            x++;
            if (x == 16) {
                x = 0;
                y++;
            }
        }

        // Back to manual
        MAPPINGS.add(new FontMapping("nonlatin_european", 1, 1, 15, 7));
        MAPPINGS.add(new FontMapping("accented", 7, 0, 0, 8));
        MAPPINGS.add(new FontMapping("accented", 1, 3, 1, 8));
        MAPPINGS.add(new FontMapping("accented", 13, 15, 2, 8));
        MAPPINGS.add(new FontMapping("accented", 14, 1, 3, 8));
        MAPPINGS.add(new FontMapping("accented", 0, 2, 4, 8));
        MAPPINGS.add(new FontMapping("accented", 12, 1, 5, 8));
        MAPPINGS.add(new FontMapping("accented", 12, 30, 6, 8));
        MAPPINGS.add(new FontMapping("accented", 3, 2, 7, 8));
        MAPPINGS.add(new FontMapping("accented", 14, 15, 8, 8));
        MAPPINGS.add(new FontMapping("accented", 7, 13, 9, 8));
        MAPPINGS.add(new FontMapping("accented", 6, 13, 10, 8));
        MAPPINGS.add(new FontMapping("accented", 7, 2, 11, 8));
        MAPPINGS.add(new FontMapping("accented", 6, 2, 12, 8));
        MAPPINGS.add(new FontMapping("accented", 4, 2, 13, 8));
        MAPPINGS.add(new FontMapping("accented", 6, 3, 14, 8));
        MAPPINGS.add(new FontMapping("accented", 11, 30, 15, 8));
        MAPPINGS.add(new FontMapping("accented", 8, 0, 0, 9));
        MAPPINGS.add(new FontMapping("accented", 2, 2, 1, 9));
        MAPPINGS.add(new FontMapping("accented", 6, 0, 2, 9));
        MAPPINGS.add(new FontMapping("accented", 11, 2, 3, 9));
        MAPPINGS.add(new FontMapping("accented", 13, 2, 4, 9));
        MAPPINGS.add(new FontMapping("accented", 9, 2, 5, 9));
        MAPPINGS.add(new FontMapping("accented", 0, 3, 6, 9));
        MAPPINGS.add(new FontMapping("accented", 14, 2, 7, 9));
        MAPPINGS.add(new FontMapping("accented", 3, 3, 8, 9));
        MAPPINGS.add(new FontMapping("accented", 6, 1, 9, 9));
        MAPPINGS.add(new FontMapping("accented", 10, 1, 10, 9));
        MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 11, 9));
        MAPPINGS.add(new FontMapping(0, 3, 13, 9));
        MAPPINGS.add(new FontMapping("nonlatin_european", 10, 16, 14, 9));
        MAPPINGS.add(new FontMapping("accented", 13, 1, 0, 10));
        MAPPINGS.add(new FontMapping("accented", 4, 2, 1, 10));
        MAPPINGS.add(new FontMapping("accented", 10, 2, 2, 10));
        MAPPINGS.add(new FontMapping("accented", 15, 2, 3, 10));
        MAPPINGS.add(new FontMapping("accented", 8, 2, 4, 10));
        MAPPINGS.add(new FontMapping("accented", 1, 1, 5, 10));
        MAPPINGS.add(new FontMapping(6, 10));
        MAPPINGS.add(new FontMapping(7, 10));
        MAPPINGS.add(new FontMapping("nonlatin_european", 6, 0, 8, 10));
        MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 9, 10));
        MAPPINGS.add(new FontMapping(10, 10));
        MAPPINGS.add(new FontMapping("nonlatin_european", 2, 15, 11, 10));
        MAPPINGS.add(new FontMapping("nonlatin_european", 1, 15, 12, 10));
        MAPPINGS.add(new FontMapping("nonlatin_european", 0, 0, 13, 10));
        MAPPINGS.add(new FontMapping(14, 10));
        MAPPINGS.add(new FontMapping(15, 10));

        // More duplicates
        x = 0;
        y = 11;

        for (int i = 0; i < 48; i++) {
            MAPPINGS.add(new FontMapping(x, y));
            x++;
            if (x == 16) {
                x = 0;
                y++;
            }
        }

        MAPPINGS.add(new FontMapping("nonlatin_european", 6, 2, 0, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 7, 2, 1, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 0, 1, 2, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 5, 3, 3, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 15, 1, 4, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 8, 3, 5, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 1, 3, 6, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 9, 3, 7, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 2, 2, 8, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 5, 1, 9, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 5, 2, 10, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 9, 2, 11, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 2, 17, 12, 14));
        MAPPINGS.add(new FontMapping(13, 14));
        MAPPINGS.add(new FontMapping(14, 14));
        MAPPINGS.add(new FontMapping("nonlatin_european", 2, 17, 15, 14));

        // Even more duplicates
        x = 0;
        y = 15;

        for (int i = 0; i < 16; i++) {
            MAPPINGS.add(new FontMapping(x, y));
            x++;
            if (x == 16) {
                x = 0;
                y++;
            }
        }
    }

    private record FontMapping(String javaTexture, int javaX, int javaY, int bedrockX, int bedrockY) {
        public FontMapping(int javaX, int javaY, int bedrockX, int bedrockY) {
            this("ascii", javaX, javaY, bedrockX, bedrockY);
        }

        public FontMapping(String javaTexture, int x, int y) {
            this(javaTexture, x, y, x, y);
        }

        public FontMapping(int x, int y) {
            this("ascii", x, y, x, y);
        }
    }

    private record FontData(int charSizeX, int charSizeY, float scaleX, float scaleY) {
        public FontData(int charSizeX, int charSizeY) {
            this(charSizeX, charSizeY, 1, 1);
        }
    }
}
