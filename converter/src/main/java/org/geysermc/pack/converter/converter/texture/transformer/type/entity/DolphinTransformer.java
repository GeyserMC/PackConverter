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

@AutoService(TextureTransformer.class)
public class DolphinTransformer implements TextureTransformer {
    private static final String LOCATION = "entity/dolphin.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture javaTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, LOCATION));
        if (javaTexture == null) return;

        BufferedImage javaImage = this.readImage(javaTexture);

        int scale = javaImage.getWidth() / 64;

        BufferedImage bedrockImage = new BufferedImage(javaImage.getWidth(), javaImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bedrockImage.getGraphics();

        // Now we can start to copy some pieces, we'll start with matching parts.

        // Head
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        0,
                        0,
                        28 * scale,
                        13 * scale
                ),
                0,
                0,
                null
        );

        // Nose
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        0,
                        13 * scale,
                        12 * scale,
                        6 * scale
                ),
                0,
                13 * scale,
                null
        );

        // Tail 1/2
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        0,
                        30 * scale,
                        30 * scale,
                        5 * scale
                ),
                0,
                44 * scale,
                null
        );

        // Tail 2/2
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        11 * scale,
                        19 * scale,
                        8 * scale,
                        11 * scale
                ),
                11 * scale,
                33 * scale,
                null
        );

        // Body 1/2
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        22 * scale,
                        13 * scale,
                        42 * scale,
                        7 * scale
                ),
                0,
                26 * scale,
                null
        );

        // Body 2/2
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        35 * scale,
                        0,
                        16 * scale,
                        13 * scale
                ),
                13 * scale,
                13 * scale,
                null
        );

        // Tail fin
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        19 * scale,
                        20 * scale,
                        32 * scale,
                        7 * scale
                ),
                0,
                49 * scale,
                null
        );

        // Back fin - West
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                57 * scale,
                                5 * scale,
                                5 * scale,
                                4 * scale
                        ),
                        270
                ),
                34 * scale,
                4 * scale,
                null
        );

        // Back fin - East
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                51 * scale,
                                5 * scale,
                                5 * scale,
                                4 * scale
                        ),
                        90
                ),
                29 * scale,
                4 * scale,
                null
        );

        // Back fin - North
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                5 * scale,
                                scale,
                                4 * scale
                        ),
                        180
                ),
                33 * scale,
                0,
                null
        );

        // Back fin - South
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                62 * scale,
                                5 * scale,
                                scale,
                                4 * scale
                        ),
                        180
                ),
                34 * scale,
                0,
                null
        );

        // Back fin - Up
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        56 * scale,
                        0,
                        scale,
                        5 * scale
                ),
                33 * scale,
                4 * scale,
                null
        );

        // Back fin - Down
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        57 * scale,
                        0,
                        scale,
                        5 * scale
                ),
                38 * scale,
                4 * scale,
                null
        );

        // Only one side fin exists on java, but the bedrock texture has both seperate
        // The bedrock dolphin also has longer fins
        // Side fin - West -> Up
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.flip(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                27 * scale,
                                7 * scale,
                                4 * scale
                        ),
                        false,
                        true
                ),
                44 * scale,
                0,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.flip(
                        ImageUtil.crop(
                                javaImage,
                                62 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        false,
                        true
                ),
                51 * scale,
                0,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                27 * scale,
                                7 * scale,
                                4 * scale
                        ),
                        180
                ),
                45 * scale,
                6 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                62 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        180
                ),
                44 * scale,
                6 * scale,
                null
        );

        // Side fin - East -> Down
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.flip(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                27 * scale,
                                7 * scale,
                                4 * scale
                        ),
                        false,
                        true
                ),
                52 * scale,
                0,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.flip(
                        ImageUtil.crop(
                                javaImage,
                                62 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        false,
                        true
                ),
                59 * scale,
                0,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                27 * scale,
                                7 * scale,
                                4 * scale
                        ),
                        180
                ),
                53 * scale,
                6 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                62 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        180
                ),
                52 * scale,
                6 * scale,
                null
        );

        // Side fin - Up -> North
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                55 * scale,
                                20 * scale,
                                scale,
                                7 * scale
                        ),
                        90
                ),
                44 * scale,
                4 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        55 * scale,
                        20 * scale,
                        scale,
                        scale
                ),
                51 * scale,
                4 * scale,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                55 * scale,
                                20 * scale,
                                scale,
                                7 * scale
                        ),
                        270
                ),
                45 * scale,
                10 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        55 * scale,
                        20 * scale,
                        scale,
                        scale
                ),
                44 * scale,
                10 * scale,
                null
        );

        // Side fin - Down -> South
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                20 * scale,
                                scale,
                                7 * scale
                        ),
                        90
                ),
                57 * scale,
                4 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        56 * scale,
                        22 * scale,
                        scale,
                        scale
                ),
                56 * scale,
                4 * scale,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                56 * scale,
                                20 * scale,
                                scale,
                                7 * scale
                        ),
                        270
                ),
                56 * scale,
                10 * scale,
                null
        );

        // Extra piece
        g.drawImage(
                ImageUtil.crop(
                        javaImage,
                        56 * scale,
                        22 * scale,
                        scale,
                        scale
                ),
                63 * scale,
                10 * scale,
                null
        );

        // Side fin - North -> East
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                55 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        90
                ),
                40 * scale,
                4 * scale,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                55 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        270
                ),
                52 * scale,
                10 * scale,
                null
        );

        // Side fin - South -> West
        // Left fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                63 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        90
                ),
                52 * scale,
                4 * scale,
                null
        );

        // Right fin on bedrock
        g.drawImage(
                ImageUtil.rotate(
                        ImageUtil.crop(
                                javaImage,
                                63 * scale,
                                27 * scale,
                                scale,
                                4 * scale
                        ),
                        270
                ),
                40 * scale,
                10 * scale,
                null
        );

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, LOCATION), bedrockImage, "png");
    }
}
