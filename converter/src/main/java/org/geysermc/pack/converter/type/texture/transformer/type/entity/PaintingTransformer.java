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
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AutoService(TextureTransformer.class)
public class PaintingTransformer implements TextureTransformer {
    private static final Integer PAINTING_BASE_SIZE = 16;
    private static final Map<String, PaintingData> PAINTING_DATA = new HashMap<>();
    private static final String JAVA_LOCATION = "painting/%s.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        // Let's check if we actually need to convert anything.
        boolean isAnyPresent = PAINTING_DATA.keySet().stream().anyMatch(key -> {
            return context.isTexturePresent(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_LOCATION.formatted(key)));
        });

        // We don't map this like the others because bedrock is difficult, so it isn't in PAINTING_DATA.
        isAnyPresent = isAnyPresent || context.isTexturePresent(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_LOCATION.formatted("back")));

        if (!isAnyPresent) return; // we have nothing to convert, skip this

        // Now lets determine our scale base image, we'll scale anything else if needed
        Map<String, BufferedImage> javaImages = new HashMap<>();
        Map<String, Float> scales = new HashMap<>();

        PAINTING_DATA.forEach((key, value) -> {
            Texture javaTexture = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_LOCATION.formatted(key)));
            if (javaTexture == null) {
                scales.put(key, 1f);
                return;
            }

            BufferedImage image;
            try {
                image = this.readImage(javaTexture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            javaImages.put(key, image);
            scales.put(key, (float) image.getWidth() / value.baseWidth());
        });

        // Again, bedrock being difficult with back.
        Texture backTexture = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_LOCATION.formatted("back")));
        if (backTexture != null) {
            BufferedImage image = this.readImage(backTexture);

            javaImages.put("back", image);
            scales.put("back", (float) image.getWidth() / 16);
        }

        float finalScale = (float) scales.values().stream().mapToDouble(f -> f).max().getAsDouble();

        BufferedImage bedrockImage = new BufferedImage((int) (256 * finalScale), (int) (256 * finalScale), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bedrockImage.getGraphics();

        for (Map.Entry<String, BufferedImage> entry : javaImages.entrySet()) {
            float scale = finalScale / scales.get(entry.getKey());

            if (PAINTING_DATA.containsKey(entry.getKey())) {
                PaintingData paintingData = PAINTING_DATA.get(entry.getKey());

                g.drawImage(
                        ImageUtil.scale(
                                javaImages.get(entry.getKey()),
                                scale
                        ),
                        (int) finalScale * paintingData.bedrockX(),
                        (int) finalScale * paintingData.bedrockY(),
                        null
                );
            } else { // *back* is a special case here, we need to tile it in a 4x4 grid
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        g.drawImage(
                                ImageUtil.scale(
                                        javaImages.get(entry.getKey()),
                                        scale
                                ),
                                (int) (finalScale * 192) + (int) (finalScale * 16 * x),
                                (int) (finalScale * 16 * y),
                                null
                        );
                    }
                }
            }
        }

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "painting/kz.png"), bedrockImage, "png");
    }

    // BedrockX and BedrockY determine where the image is in kz.png (Bedrock painting atlas)
    // Modern paintings are not in this atlas, but old ones are so we need to map
    private record PaintingData(int baseWidth, int bedrockX, int bedrockY) {

    }

    static {
        // 1x1 Paintings
        PAINTING_DATA.put("kebab", new PaintingData(PAINTING_BASE_SIZE, 0, 0));
        PAINTING_DATA.put("aztec", new PaintingData(PAINTING_BASE_SIZE, 16, 0));
        PAINTING_DATA.put("alban", new PaintingData(PAINTING_BASE_SIZE, 32, 0));
        PAINTING_DATA.put("aztec2", new PaintingData(PAINTING_BASE_SIZE, 48, 0));
        PAINTING_DATA.put("bomb", new PaintingData(PAINTING_BASE_SIZE, 64, 0));
        PAINTING_DATA.put("plant", new PaintingData(PAINTING_BASE_SIZE, 80, 0));
        PAINTING_DATA.put("wasteland", new PaintingData(PAINTING_BASE_SIZE, 96, 0));

        // 2x1 Paintings
        PAINTING_DATA.put("pool", new PaintingData(PAINTING_BASE_SIZE * 2, 0, 32));
        PAINTING_DATA.put("courbet", new PaintingData(PAINTING_BASE_SIZE * 2, 32, 32));
        PAINTING_DATA.put("sea", new PaintingData(PAINTING_BASE_SIZE * 2, 64, 32));
        PAINTING_DATA.put("sunset", new PaintingData(PAINTING_BASE_SIZE * 2, 96, 32));
        PAINTING_DATA.put("creebet", new PaintingData(PAINTING_BASE_SIZE * 2, 128, 32));

        // 1x2 Paintings
        PAINTING_DATA.put("wanderer", new PaintingData(PAINTING_BASE_SIZE, 0, 64));
        PAINTING_DATA.put("graham", new PaintingData(PAINTING_BASE_SIZE, 16, 64));

        // 4x2 Painting
        PAINTING_DATA.put("fighters", new PaintingData(PAINTING_BASE_SIZE * 4, 0, 96));

        // 2x2 Paintings
        PAINTING_DATA.put("match", new PaintingData(PAINTING_BASE_SIZE * 2, 0, 128));
        PAINTING_DATA.put("bust", new PaintingData(PAINTING_BASE_SIZE * 2, 32, 128));
        PAINTING_DATA.put("stage", new PaintingData(PAINTING_BASE_SIZE * 2, 64, 128));
        PAINTING_DATA.put("void", new PaintingData(PAINTING_BASE_SIZE * 2, 96, 128));
        PAINTING_DATA.put("skull_and_roses", new PaintingData(PAINTING_BASE_SIZE * 2, 128, 128));
        PAINTING_DATA.put("wither", new PaintingData(PAINTING_BASE_SIZE * 2, 160, 128));
        PAINTING_DATA.put("earth", new PaintingData(PAINTING_BASE_SIZE * 2, 0, 160));
        PAINTING_DATA.put("wind", new PaintingData(PAINTING_BASE_SIZE * 2, 32, 160));
        PAINTING_DATA.put("fire", new PaintingData(PAINTING_BASE_SIZE * 2, 64, 160));
        PAINTING_DATA.put("water", new PaintingData(PAINTING_BASE_SIZE * 2, 96, 160));

        // 4x4 Paintings
        PAINTING_DATA.put("pointer", new PaintingData(PAINTING_BASE_SIZE * 4, 0, 192));
        PAINTING_DATA.put("pigscene", new PaintingData(PAINTING_BASE_SIZE * 4, 64, 192));
        PAINTING_DATA.put("burning_skull", new PaintingData(PAINTING_BASE_SIZE * 4, 128, 192));

        // 4x3 Paintings
        PAINTING_DATA.put("skeleton", new PaintingData(PAINTING_BASE_SIZE * 4, 192, 64));
        PAINTING_DATA.put("donkey_kong", new PaintingData(PAINTING_BASE_SIZE * 4, 192, 112));
    }
}
