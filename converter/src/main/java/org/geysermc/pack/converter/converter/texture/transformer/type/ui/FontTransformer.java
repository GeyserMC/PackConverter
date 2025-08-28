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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.HexUtils;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.Vec2i;
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
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

@AutoService(TextureTransformer.class)
public class FontTransformer implements TextureTransformer {
    // Mappings for where characters should go in the default8.png file
    private static final Map<Character, Vec2i> DEFAULT8_MAPPINGS = new HashMap<>();

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

        Map<Key, BufferedImage> cachedImages = new HashMap<>(); // Just so we aren't reading an image several times over

        for (UnicodeFontData fontData : unicodeFontData) {
            fontData.computeCache(context, cachedImages);
        }

        Map<String, BufferedImage> finishedImages = new HashMap<>();

        finishedImages.putAll(new GlyphWriter().generateImage(context, unicodeFontData, cachedImages));
        finishedImages.putAll(new Default8Writer().generateImage(context, unicodeFontData, cachedImages));

        for (Map.Entry<String, BufferedImage> imageEntry : finishedImages.entrySet()) {
            context.bedrockResourcePack().addExtraFile(
                    ImageUtil.toByteArray(imageEntry.getValue(), "png"),
                    imageEntry.getKey()
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
            for (char i = 0; i < 65535; i++) {
                // If the skip strings contain this character, we don't want it
                if (String.join("", trueTypeFontProvider.skip()).contains(String.valueOf(i))) continue;

                unicodeFontData.add(new TTFFontData(
                        "assets/%s/%s".formatted(trueTypeFontProvider.file().namespace(), trueTypeFontProvider.file().value()),
                        i,
                        trueTypeFontProvider.oversample()
                ));
            }
        }

        return unicodeFontData;
    }

    static {
        JsonArray mappingArray = JsonParser.parseReader(
                new InputStreamReader(FontTransformer.class.getResourceAsStream("/mappings/default8.json"))
        ).getAsJsonObject().getAsJsonArray("mappings");

        int x = 0;
        int y = 0;

        for (JsonElement element : mappingArray) {
            for (char character : element.getAsString().toCharArray()) {
                DEFAULT8_MAPPINGS.put(character, new Vec2i(x++, y));
            }
            y++;
            x = 0;
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
            if (imageCache.containsKey(textureName)) return; // Already cached

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

    // TrueType fonts
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
            return height();
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

    private interface FontWriter {
        Map<String, BufferedImage> generateImage(TransformContext context, List<UnicodeFontData> data, Map<Key, BufferedImage> cache) throws IOException;
    }

    private static class GlyphWriter implements FontWriter {
        @Override
        public Map<String, BufferedImage> generateImage(TransformContext context, List<UnicodeFontData> unicodeFontData, Map<Key, BufferedImage> cache) throws IOException {
            Map<String, BufferedImage> paths = new HashMap<>();

            Map<Byte, List<UnicodeFontData>> containedCharacters = new HashMap<>();
            for (UnicodeFontData fontData : unicodeFontData) {
                byte[] bytes = String.valueOf(fontData.character()).getBytes(StandardCharsets.UTF_16BE);
                byte upperData = bytes.length == 1 ? 0 : bytes[0]; // The first byte, determines which image this character will be written to

                containedCharacters.computeIfAbsent(upperData, ignored -> new ArrayList<>());

                containedCharacters.get(upperData).add(fontData);
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
                        javaImage = fontData.readJavaImage(cache, context.javaResourcePack());
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

                paths.put("font/glyph_%s.png"
                        .formatted(hexFormat.toHexDigits(data.getKey()).toUpperCase()), bedrockImage);
            }

            return paths;
        }
    }

    private static class Default8Writer implements FontWriter {
        @Override
        public Map<String, BufferedImage> generateImage(TransformContext context, List<UnicodeFontData> data, Map<Key, BufferedImage> cache) throws IOException {
            int maxWidth = data.stream().filter(fontData -> DEFAULT8_MAPPINGS.containsKey(fontData.character())).mapToInt(
                    fontData ->
                            (int) (fontData.width() * fontData.fontData().scaleX())
            ).max().orElse(1);

            int maxHeight = data.stream().filter(fontData -> DEFAULT8_MAPPINGS.containsKey(fontData.character())).mapToInt(
                    fontData ->
                            (int) (fontData.height() * fontData.fontData().scaleY())
            ).max().orElse(1);

            int size = Math.max(maxWidth, maxHeight);

            BufferedImage bedrockImage = new BufferedImage(size * 16, size * 16, BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            List<UnicodeFontData> default8Data = data.stream().filter(fontData -> DEFAULT8_MAPPINGS.containsKey(fontData.character())).toList();

            for (UnicodeFontData fontData : default8Data) {
                if (!fontData.shouldRead()) {
                    continue;
                }

                int dataWidth = fontData.width();
                int dataHeight = fontData.height();
                int dataX = fontData.x();
                int dataY = fontData.y();

                BufferedImage javaImage;
                try {
                    javaImage = fontData.readJavaImage(cache, context.javaResourcePack());
                } catch (FontFormatException e) {
                    throw new IOException(e);
                }
                if (javaImage == null) {
                    context.warn("Missing font file, unable to write character '%s'.".formatted(fontData.character()));
                    continue;
                }

                // We do some processing here just to prevent images that are scaled from scaling poorly
                if (javaImage.getWidth() > size) {
                    int startRows = 0;
                    xStartLoop: for (int x = 0; x < javaImage.getWidth(); x++) {
                        for (int y = 0; y < javaImage.getHeight(); y++) {
                            Color c = new Color(javaImage.getRGB(x, y), true);

                            if (c.getAlpha() > 0) break xStartLoop;
                        }

                        startRows++;
                    }

                    int endRows = 0;
                    xEndLoop: for (int x = javaImage.getWidth() - 1; x >= 0; x--) {
                        for (int y = javaImage.getHeight() - 1; y >= 0; y--) {
                            Color c = new Color(javaImage.getRGB(x, y), true);

                            if (c.getAlpha() > 0) break xEndLoop;
                        }

                        endRows++;
                    }

                    if (startRows > 0 || endRows > 0) {
                        context.info("Start rows: %d | End rows: %d".formatted(startRows, endRows));
                    }

                    javaImage = ImageUtil.crop(javaImage, startRows, 0, javaImage.getWidth() - (startRows + endRows), javaImage.getHeight());
                }

                // Now we can find where the character belongs in the bedrock image
                int desX = DEFAULT8_MAPPINGS.get(fontData.character()).x();
                int desY = DEFAULT8_MAPPINGS.get(fontData.character()).y();

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

            return Map.of("font/default8.png", bedrockImage);
        }
    }
}
