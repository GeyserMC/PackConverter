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
import team.unnamed.creative.font.*;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@AutoService(TextureTransformer.class)
public class FontTransformer implements TextureTransformer {
    private static final List<FontMapping> DEFAULT8_MAPPINGS = new ArrayList<>();
    private static final Map<String, FontData> FONT_DATA = Map.of(
            "ascii", new FontData(8, 8),
            "accented", new FontData(9, 12, 0.888f, 0.666f),
            "nonlatin_european", new FontData(8, 8)
    );

    private static final FontData DEFAULT_FONT_DATA = new FontData(8, 8);

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        transformDefault8(context);

        List<UnicodeFontData> unicodeFontData = new ArrayList<>();

        for (Font font : context.javaResourcePack().fonts()) {
            // TODO Can we register custom fonts to bedrock and use those instead?
            if (!font.key().equals(Key.key(Key.MINECRAFT_NAMESPACE, "default"))) continue;

            for (FontProvider fontProvider : font.providers()) {
                unicodeFontData.addAll(handleFont(context, fontProvider));
            }
        }

        // if we have no data, don't stop yet, we may still want to generate some stuff from vanilla
        if (unicodeFontData.isEmpty()) {
            if (
                    !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png")) &&
                            !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png")) &&
                            !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"))
            ) return;

            for (Font font : context.vanillaPack().fonts()) {
                // TODO Can we register custom fonts to bedrock and use those instead?
                if (!font.key().equals(Key.key(Key.MINECRAFT_NAMESPACE, "default"))) continue;

                for (FontProvider fontProvider : font.providers()) {
                    unicodeFontData.addAll(handleFont(context, fontProvider));
                }
            }
        }

        // Now we need to gather the images and calculate the sizes for this
        // This could use with a little bit of optimisation...
        Map<Byte, List<UnicodeFontData>> containedCharacters = new HashMap<>();
        Map<Key, BufferedImage> images = new HashMap<>(); // Just so we aren't reading an image several times over

        for (UnicodeFontData fontData : unicodeFontData) {
            byte[] bytes = String.valueOf(fontData.character()).getBytes(StandardCharsets.UTF_16BE);
            byte upperData = bytes.length == 1 ? 0 : bytes[0];

            containedCharacters.computeIfAbsent(upperData, ignored -> new ArrayList<>());

            containedCharacters.get(upperData).add(fontData);

            images.computeIfAbsent(fontData.filename(), filename -> {
                Texture texture = context.peekOrVanilla(filename); // TODO Should we poll this instead?
                try {
                    return texture == null ? null : this.readImage(texture);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        HexFormat hexFormat = HexFormat.of();

        for (Map.Entry<Byte, List<UnicodeFontData>> data : containedCharacters.entrySet()) {
            int maxWidth = data.getValue().stream().mapToInt(fontData -> {
                return (int) (fontData.width() * FONT_DATA.getOrDefault(fontData.filename().value().substring(5, fontData.filename().value().length() - 4), DEFAULT_FONT_DATA).scaleX());
            }).max().getAsInt();
            int maxHeight = data.getValue().stream().mapToInt(fontData -> {
                return (int) (fontData.height * FONT_DATA.getOrDefault(fontData.filename().value().substring(5, fontData.filename().value().length() - 4), DEFAULT_FONT_DATA).scaleY());
            }).max().getAsInt();

            BufferedImage bedrockImage = new BufferedImage(maxWidth * 16, maxHeight * 16, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            for (UnicodeFontData fontData : data.getValue()) {
                BufferedImage javaImage = images.get(fontData.filename());
                if (javaImage == null) {
                    context.warn("Missing %s, unable to write character.".formatted(fontData.filename().asString()));
                    continue;
                }

                byte[] bytes = String.valueOf(fontData.character()).getBytes(StandardCharsets.UTF_16BE);
                int position = (bytes.length == 1 ? bytes[0] : bytes[1]) & 0xff;

                // Now we can find where the character belongs in the bedrock image
                int desX = position % 16;
                int desY = position / 16;

                g.drawImage(
                        ImageUtil.scale(
                                ImageUtil.crop(
                                        javaImage,
                                        fontData.x() * fontData.width(),
                                        fontData.y() * fontData.height(),
                                        fontData.width(),
                                        fontData.height()
                                ),
                                (float) maxWidth / fontData.width(),
                                (float) maxHeight / fontData.height()
                        ),
                        desX * maxWidth,
                        desY * maxHeight,
                        null
                );
            }

            context.bedrockResourcePack().addExtraFile(
                    ImageUtil.toByteArray(bedrockImage, "png"),
                    "font/glyph_%s.png"
                            .formatted(hexFormat.toHexDigits(data.getKey()).toUpperCase())
            );
        }
    }

    private List<UnicodeFontData> handleFont(@NotNull TransformContext context, FontProvider fontProvider) throws IOException {
        List<UnicodeFontData> unicodeFontData = new ArrayList<>();

        if (fontProvider instanceof SpaceFontProvider spaceFontProvider) {
            // TODO Handle this type of font
        } else if (fontProvider instanceof BitMapFontProvider bitMapFontProvider) {
            // First of all we need to determine the width and height of the characters

            Texture texture = context.peek(bitMapFontProvider.file());
            if (texture == null) return unicodeFontData; // We don't have the texture, so we can't continue

            BufferedImage image = this.readImage(texture);

            int width = image.getWidth() / bitMapFontProvider.characters().getFirst().length();
            int height = bitMapFontProvider.height();

            int x = 0;
            int y = 0;

            for (String charLines : bitMapFontProvider.characters()) {
                for (char character : charLines.toCharArray()) {
                    unicodeFontData.add(new UnicodeFontData(
                            bitMapFontProvider.file(),
                            character,
                            x, y, width, height
                    ));

                    x++;
                }
                x = 0;
                y++;
            }
        } else if (fontProvider instanceof ReferenceFontProvider referenceFontProvider) {
            for (FontProvider fontProvider1 : context.javaResourcePack().font(referenceFontProvider.id()).providers()) {
                unicodeFontData.addAll(handleFont(context, fontProvider1));
            }
        } else if (fontProvider instanceof UnihexFontProvider unihexFontProvider) {
            // TODO Handle this type of font
        }

        return unicodeFontData;
    }

    private void transformDefault8(@NotNull TransformContext context) throws IOException {
        if (
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png")) &&
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png")) &&
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"))
        ) return;

        Map<String, BufferedImage> imgs = new HashMap<>();
        Map<String, Integer> scales = new HashMap<>();

        Texture ascii = context.peekOrVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png"));
        if (ascii != null) {
            BufferedImage image = this.readImage(ascii);
            imgs.put("ascii", image);
            scales.put("ascii", image.getWidth() / 128);
        }

        Texture accented = context.peekOrVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png"));
        if (accented != null) {
            BufferedImage image = this.readImage(accented);
            imgs.put("accented", image);
            scales.put("accented", image.getWidth() / 144);
        }

        Texture nonlatin_european = context.peekOrVanilla(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"));
        if (nonlatin_european != null) {
            BufferedImage image = this.readImage(nonlatin_european);
            imgs.put("nonlatin_european", image);
            scales.put("nonlatin_european", image.getWidth() / 128);
        }

        int charWidth = scales.get("ascii") * 8;
        int charHeight = scales.get("ascii") * 8;

        BufferedImage bedrockImage = new BufferedImage(16 * charWidth, 16 * charHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics g = bedrockImage.getGraphics();

        for (FontMapping fontMapping : DEFAULT8_MAPPINGS) {
            FontData fontData = FONT_DATA.get(fontMapping.javaTexture);

            int realCharX = fontData.charSizeX * scales.get(fontMapping.javaTexture);
            int realCharY = fontData.charSizeY * scales.get(fontMapping.javaTexture);

            g.drawImage(
                    ImageUtil.scale(
                            ImageUtil.crop(
                                    imgs.getOrDefault(fontMapping.javaTexture, new BufferedImage((fontMapping.javaX * realCharX) + realCharX, (fontMapping.javaY * realCharY) + realCharY, BufferedImage.TYPE_INT_ARGB)),
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
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 0, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 1, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 2, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 8, 0, 3, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 10, 0, 4, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 11, 0, 5, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 12, 0, 6, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 3, 1, 7, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 4, 1, 8, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 5, 1, 9, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 8, 1, 10, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 6, 10, 11, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 3, 0, 12, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 5, 1, 13, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 5, 5, 14, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 6, 15, 0));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 7, 6, 0, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 4, 8, 1, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 5, 8, 2, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 0, 9, 3, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 1, 9, 4, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 10, 5, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 7, 10, 6, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 15, 10, 7, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 14, 15, 8, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 15, 37, 9, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 13, 14, 10, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 15, 11, 11, 1));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 12, 1));

        // We have a few good matching ones, so lets save some time
        int x = 0;
        int y = 2;

        for (int i = 0; i < 92; i++) {
            DEFAULT8_MAPPINGS.add(new FontMapping(x, y));
            x++;
            if (x == 16) {
                x = 0;
                y++;
            }
        }

        // Back to manual
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 1, 1, 15, 7));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 7, 0, 0, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 1, 3, 1, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 13, 15, 2, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 14, 1, 3, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 0, 2, 4, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 12, 1, 5, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 12, 30, 6, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 3, 2, 7, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 14, 15, 8, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 7, 13, 9, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 13, 10, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 7, 2, 11, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 2, 12, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 4, 2, 13, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 3, 14, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 11, 30, 15, 8));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 8, 0, 0, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 2, 2, 1, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 0, 2, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 11, 2, 3, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 13, 2, 4, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 9, 2, 5, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 0, 3, 6, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 14, 2, 7, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 3, 3, 8, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 6, 1, 9, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 10, 1, 10, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 11, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping(0, 3, 13, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 10, 16, 14, 9));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 13, 1, 0, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 4, 2, 1, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 10, 2, 2, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 15, 2, 3, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 8, 2, 4, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("accented", 1, 1, 5, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping(6, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping(7, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 6, 0, 8, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 14, 14, 9, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping(10, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 2, 15, 11, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 1, 15, 12, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 0, 0, 13, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping(14, 10));
        DEFAULT8_MAPPINGS.add(new FontMapping(15, 10));

        // More duplicates
        x = 0;
        y = 11;

        for (int i = 0; i < 48; i++) {
            DEFAULT8_MAPPINGS.add(new FontMapping(x, y));
            x++;
            if (x == 16) {
                x = 0;
                y++;
            }
        }

        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 6, 2, 0, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 7, 2, 1, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 0, 1, 2, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 5, 3, 3, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 15, 1, 4, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 8, 3, 5, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 1, 3, 6, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 9, 3, 7, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 2, 2, 8, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 5, 1, 9, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 5, 2, 10, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 9, 2, 11, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 2, 17, 12, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping(13, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping(14, 14));
        DEFAULT8_MAPPINGS.add(new FontMapping("nonlatin_european", 2, 17, 15, 14));

        // Even more duplicates
        x = 0;
        y = 15;

        for (int i = 0; i < 16; i++) {
            DEFAULT8_MAPPINGS.add(new FontMapping(x, y));
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

    private record UnicodeFontData(Key filename, char character, int x, int y, int width, int height) {}
}
