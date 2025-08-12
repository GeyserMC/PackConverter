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
import org.geysermc.pack.converter.util.HexUtils;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.ZipUtils;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.*;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

@AutoService(TextureTransformer.class)
public class FontTransformer implements TextureTransformer {
    // Mappings for where characters should go in the default8.png file
    private static final List<FontMapping> DEFAULT8_MAPPINGS = new ArrayList<>();

    // Data for the 3 common font images in java
    private static final Map<String, FontData> FONT_DATA = Map.of(
            "ascii", new FontData(8, 8),
            "accented", new FontData(9, 12, 0.888f, 0.666f),
            "nonlatin_european", new FontData(8, 8)
    );

    // A fallback for the above
    private static final FontData DEFAULT_FONT_DATA = new FontData(8, 8);

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        // We first do default8, since we may return if needed below
        transformDefault8(context);

        List<UnicodeFontData> unicodeFontData = new ArrayList<>();

        // Currently, only the default font is converted, custom fonts are not supported on bedrock
        for (Font font : context.javaResourcePack().fonts()) {
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
            byte upperData = bytes.length == 1 ? 0 : bytes[0]; // The first byte, determines which image this character will be written to

            containedCharacters.computeIfAbsent(upperData, ignored -> new ArrayList<>());

            containedCharacters.get(upperData).add(fontData);

            fontData.computeCache(context, images);
        }

        // A formatter for our hex value, used when writing the glyph files
        HexFormat hexFormat = HexFormat.of();

        for (Map.Entry<Byte, List<UnicodeFontData>> data : containedCharacters.entrySet()) {
            if (data.getValue().isEmpty()) continue; // We have nothing to work with

            // Determine the size the image should be to fit all our characters
            // Better to default to something than an exception, so lets default to 1
            int maxWidth = data.getValue().stream().mapToInt(
                    fontData ->
                            (int) (fontData.width() * fontData.fontData().scaleX())
            ).max().orElse(1);

            int maxHeight = data.getValue().stream().mapToInt(
                    fontData ->
                            (int) (fontData.height() * fontData.fontData().scaleY())
            ).max().orElse(1);

            int size = Math.max(maxWidth, maxHeight);

            BufferedImage bedrockImage = new BufferedImage(size * 16, size * 16, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            for (UnicodeFontData fontData : data.getValue()) {
                if (!fontData.shouldRead()) continue;

                int dataWidth = fontData.width();
                int dataHeight = fontData.height();
                int dataX = fontData.x();
                int dataY = fontData.y();

                BufferedImage javaImage;
                try {
                    javaImage = fontData.readJavaImage(images, context.javaResourcePack());
                } catch (FontFormatException e) {
                    throw new IOException(e);
                }
                if (javaImage == null) {
                    context.warn("Missing font file, unable to write character '%s'.".formatted(fontData.character()));
                    continue;
                }

                byte[] bytes = String.valueOf(fontData.character()).getBytes(StandardCharsets.UTF_16BE);
                int position = bytes[bytes.length - 1] & 0xff; // The last byte of the character

                // Now we can find where the character belongs in the bedrock image
                int desX = position % 16;
                int desY = position / 16;

                // Determine how to scale the image to ensure they're in line with every other character
                float scaleX = (float) maxWidth / dataWidth;
                float scaleY = (float) maxHeight / dataHeight;
                float scale = Math.min(scaleX, scaleY); // Prevent stretching, use the minimum one

                // Since we don't stretch fully, we should offset to ensure the character appears correctly in bedrock
                int xOffset = (size - dataWidth) / 2;
                int yOffset = (size - dataHeight) / 2;

                g.drawImage(
                        ImageUtil.scale(
                                ImageUtil.crop(
                                        javaImage,
                                        dataX * dataWidth,
                                        dataY * dataHeight,
                                        dataWidth,
                                        dataHeight
                                ),
                                scale
                        ),
                        (desX * size) + xOffset,
                        (desY * size) + yOffset,
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
            // Simple space fonts, easy to handle
            for (Map.Entry<String, Integer> entry : spaceFontProvider.advances().entrySet()) {
                unicodeFontData.add(new SpaceFontData(entry.getKey().charAt(0), entry.getValue()));
            }
        } else if (fontProvider instanceof BitMapFontProvider bitMapFontProvider) {
            // First of all we need to determine the width and height of the characters
            Texture texture = context.peek(bitMapFontProvider.file());
            if (texture == null) return unicodeFontData; // We don't have the texture, so we can't continue

            BufferedImage image = this.readImage(texture);

            int width = image.getWidth() / bitMapFontProvider.characters().get(0).length();
            int height = image.getHeight() / bitMapFontProvider.characters().size();

            int x = 0;
            int y = 0;

            for (String charLines : bitMapFontProvider.characters()) {
                for (char character : charLines.toCharArray()) {
                    unicodeFontData.add(new BitMapFontData(
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
            // Refers to other fonts, so we need to read those
            Font font = context.javaResourcePack().font(referenceFontProvider.id());
            if (font == null) { // Just maybe, the vanilla files are used
                font = context.vanillaPack().font(referenceFontProvider.id());
            }

            if (font == null) {
                context.warn("Unable to find font %s, continuing without.".formatted(referenceFontProvider.id().asString()));
                return unicodeFontData;
            }

            for (FontProvider fontProvider1 : font.providers()) {
                unicodeFontData.addAll(handleFont(context, fontProvider1));
            }
        } else if (fontProvider instanceof UnihexFontProvider unihexFontProvider) {
            String fileLocation = "assets/%s/%s".formatted(unihexFontProvider.file().namespace(), unihexFontProvider.file().value());

            ZipUtils.openFileSystem(context.javaPackPath().resolve(fileLocation), true, (path) -> {
                Files.list(path).forEach(file -> {
                    if (Files.isDirectory(file)) return; // Sub-directories are ignored with hex fonts

                    if (!file.getFileName().toString().endsWith(".hex")) return;

                    // We found a hex file!
                    String hexFontContents;
                    try {
                        hexFontContents = Files.readString(file); // Assumes UTF_8
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    String[] hexEntries = hexFontContents.split("\n");

                    for (String hexEntry : hexEntries) {
                        hexEntry = hexEntry.replaceAll("\\s+",""); // Remove whitespace

                        String[] data = hexEntry.split(":");
                        if (data.length != 2) {
                            if (data.length != 0) { // There might have been an empty line, don't throw there
                                context.warn("Invalid data found in hex font, a character will likely be missing.");
                            }
                        }

                        int characterValue = Integer.parseInt(data[0], 16);

                        if (characterValue > Character.MAX_VALUE) continue; // We can't translate this, bedrock only supports 2 bytes worth of data for each character

                        char character = (char) characterValue;
                        // Now, this is the messy part, but helps to make a nice image later

                        List<Boolean> binaryData = Arrays.stream(HexUtils.hexToBinary(data[1]).split("")).map("1"::equals).toList();

                        unicodeFontData.add(new UniHexFontData(binaryData, character));
                    }
                });
            });
        } else if (fontProvider instanceof TrueTypeFontProvider trueTypeFontProvider) {
//            for (char i = 0; i < 65535; i++) {
//                // If the skip strings contain this character, we don't want it
//                if (String.join("", trueTypeFontProvider.skip()).contains(String.valueOf(i))) continue;
//
//                unicodeFontData.add(new TTFFontData(
//                        "assets/%s/%s".formatted(trueTypeFontProvider.file().namespace(), trueTypeFontProvider.file().value()),
//                        i,
//                        trueTypeFontProvider.oversample()
//                ));
//            }
        }

        return unicodeFontData;
    }

    private void transformDefault8(@NotNull TransformContext context) throws IOException {
        // Don't attempt to write default8 if we have no data to pull from, otherwise it's vanilla to vanilla
        if (
                !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/ascii.png")) &&
                        !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/accented.png")) &&
                        !context.isTexturePresent(Key.key(Key.MINECRAFT_NAMESPACE, "font/nonlatin_european.png"))
        ) return;

        // Store the java images to prevent constant image reading
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

        // Use ASCII as a base, since bedrock's default8 has the same character size as ASCII
        int charSize = scales.get("ascii") * 8;

        // default8 is 16 by 16 characters in size
        BufferedImage bedrockImage = new BufferedImage(16 * charSize, 16 * charSize, BufferedImage.TYPE_INT_ARGB);

        Graphics g = bedrockImage.getGraphics();

        for (FontMapping fontMapping : DEFAULT8_MAPPINGS) {
            FontData fontData = FONT_DATA.get(fontMapping.javaTexture);

            // Determines the position in the java image, accounting for scale
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
                    (fontMapping.bedrockX * charSize),
                    (fontMapping.bedrockY * charSize),
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

    // The base for our unicode fonts
    private interface UnicodeFontData {
        BufferedImage readJavaImage(Map<Key, BufferedImage> imageCache, ResourcePack pack) throws IOException, FontFormatException;

        default boolean shouldRead() {
            return true;
        }

        default void computeCache(TransformContext context, Map<Key, BufferedImage> imageCache) {} // No caching if not needed

        char character();

        int x();
        int y();

        int width();
        int height();

        default FontData fontData() {
            return DEFAULT_FONT_DATA;
        }
    }

    // Bitmap implementation of our fonts, the simplest to read
    private record BitMapFontData(Key textureName, char character, int x, int y, int width, int height) implements UnicodeFontData {
        @Override
        public BufferedImage readJavaImage(Map<Key, BufferedImage> imageCache, ResourcePack pack) {
            return imageCache.get(textureName);
        }

        @Override
        public void computeCache(TransformContext context, Map<Key, BufferedImage> imageCache) {
            Texture texture = context.pollOrPeekVanilla(textureName);

            if (texture != null) {
                try {
                    imageCache.put(
                            textureName,
                            ImageUtil.ensure32BitImage(
                                    ImageIO.read(new ByteArrayInputStream(texture.data().toByteArray()))
                            )
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public FontData fontData() {
            return FONT_DATA.getOrDefault(textureName.value().substring(5, textureName.value().length() - 4), DEFAULT_FONT_DATA);
        }
    }

    // The simplest font, *empty*
    private record SpaceFontData(char character, int spaces) implements UnicodeFontData {
        @Override
        public BufferedImage readJavaImage(Map<Key, BufferedImage> imageCache, ResourcePack pack) {
            BufferedImage javaImage = new BufferedImage(spaces, 1, BufferedImage.TYPE_INT_ARGB);

            Graphics javaGraphics = javaImage.getGraphics();
            javaGraphics.setColor(new Color(255, 255, 255, 1)); // Just so bedrock knows the width of our character, not noticable to the human eye
            javaGraphics.drawRect(0, 0, spaces, 1);

            return javaImage;
        }

        @Override
        public boolean shouldRead() {
            return spaces > 0;
        }

        @Override
        public int x() {
            return 0;
        }

        @Override
        public int y() {
            return 0;
        }

        @Override
        public int width() {
            return spaces;
        }

        @Override
        public int height() {
            return 1;
        }
    }

    // Bitmap implementation of our fonts, the simplest to read
    private record TTFFontData(String fileLocation, char character, float size) implements UnicodeFontData {
        @Override
        public BufferedImage readJavaImage(Map<Key, BufferedImage> imageCache, ResourcePack pack) throws IOException, FontFormatException {
            BufferedImage image = new BufferedImage((int) Math.ceil(size), (int) Math.ceil(size), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();

            Writable fontAsset = pack.unknownFile(fileLocation);

            if (fontAsset == null) return null;

            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new ByteArrayInputStream(fontAsset.toByteArray()))
                    .deriveFont(java.awt.Font.PLAIN, size);

            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(character), 0, 0);

            return image;
        }

        @Override
        public int x() {
            return 0;
        }

        @Override
        public int y() {
            return 0;
        }

        @Override
        public int width() {
            return (int) Math.ceil(size);
        }

        @Override
        public int height() {
            return (int) Math.ceil(size);
        }
    }

    private record UniHexFontData(List<Boolean> data, char character) implements UnicodeFontData {
        @Override
        public BufferedImage readJavaImage(Map<Key, BufferedImage> imageCache, ResourcePack pack) {
            BufferedImage image = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_ARGB);

            image.getGraphics().setColor(new Color(0, 0, 0, 0));
            image.getGraphics().drawRect(0, 0, width(), height());

            int x = 0;
            int y = 0;

            for (Boolean pixel : data) {
                Color color = pixel ? Color.WHITE : new Color(0, 0, 0, 0);
                image.setRGB(x, y, color.getRGB());

                x++;

                if (x == width()) {
                    x = 0;
                    y++;
                }
            }

            return image;
        }

        @Override
        public int x() {
            return 0;
        }

        @Override
        public int y() {
            return 0;
        }

        @Override
        public int width() {
            return Math.max((int) Math.ceil(data.size() / (float) 16), 1); // Because the height is always known, we can find the width easily
        }

        @Override
        public int height() {
            return 16; // UniHex fonts are *always* 16 in height
        }
    }
}
