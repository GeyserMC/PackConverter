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

package org.geysermc.pack.converter.type.texture.transformer.type.block;

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
import java.util.List;

@AutoService(TextureTransformer.class)
public class SignTransformer implements TextureTransformer {
    private static final List<SignData> SIGNS = List.of(
            new SignData("acacia_sign", "sign_acacia"),
            new SignData("bamboo_sign", "bamboo_sign"),
            new SignData("birch_sign", "sign_birch"),
            new SignData("cherry_sign", "cherry_sign"),
            new SignData("crimson_sign", "sign_crimson"),
            new SignData("dark_oak_sign", "sign_darkoak"),
            new SignData("jungle_sign", "sign_jungle"),
            new SignData("mangrove_sign", "mangrove_sign"),
            new SignData("oak_sign", "sign"),
            new SignData("pale_oak_sign", "pale_oak_sign"),
            new SignData("spruce_sign", "sign_spruce"),
            new SignData("warped_sign", "sign_warped")
    );

    private static final String JAVA_LOCATION = "block/%s.png";
    private static final String BEDROCK_LOCATION = "entity/%s.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (SignData signData : SIGNS) {
            Texture javaTexture = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_LOCATION.formatted(signData.name)));
            if (javaTexture == null) continue;

            BufferedImage javaImage = this.readImage(javaTexture);

            float scale = javaImage.getHeight() / 32f;

            BufferedImage bedrockImage = new BufferedImage((int) (scale * 24), (int) (scale * 12), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(ImageUtil.crop(javaImage, 0, (int) (2 * scale), (int) (scale * 24), (int) (scale * 12)), (int) (2 * scale), (int) (2 * scale), null);
            g.drawImage(ImageUtil.crop(javaImage, 0, 0, (int) (scale * 24), (int) (scale * 2)), (int) (2 * scale), 0, null);
            g.drawImage(
                    ImageUtil.flip(ImageUtil.crop(
                            javaImage, (int) (24 * scale),
                            (int) (2 * scale), (int) (scale * 2),
                            (int) (scale * 12)
                    ), true, false),
                    (int) (50 * scale), (int) (2 * scale), null
            );

            g.drawImage(ImageUtil.crop(javaImage, 0, (int) (18 * scale), (int) (scale * 24), (int) (scale * 12)), (int) (26 * scale), (int) (2 * scale), null);
            g.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(javaImage, 0, (int) (28 * scale), (int) (scale * 24), (int) (scale * 2)),
                            false, true
                    ), (int) (26 * scale), 0, null);
            g.drawImage(
                    ImageUtil.flip(ImageUtil.crop(
                            javaImage, (int) (24 * scale),
                            (int) (16 * scale), (int) (scale * 2),
                            (int) (scale * 12)
                    ), true, false),
                    0, (int) (2 * scale), null
            );

            g.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    javaImage,
                                    (int) (28 * scale), 0,
                                    (int) (4 * scale), (int) (14 * scale)
                            ), true, false
                    ),
                    0, (int) (16 * scale), null
            );
            g.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    javaImage,
                                    (int) (28 * scale), (int) (16 * scale),
                                    (int) (4 * scale), (int) (14 * scale)
                            ), true, false
                    ),
                    (int) (4 * scale), (int) (16 * scale), null
            );
            g.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    javaImage,
                                    (int) (28 * scale), (int) (30 * scale),
                                    (int) (2 * scale), (int) (2 * scale)
                            ), false, true
                    ),
                    (int) (2 * scale), (int) (14 * scale), null
            );
            g.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    javaImage,
                                    (int) (28 * scale), (int) (30 * scale),
                                    (int) (2 * scale), (int) (2 * scale)
                            ), false, true
                    ),
                    (int) (4 * scale), (int) (14 * scale), null
            );

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_LOCATION.formatted(signData.bedrockName)), bedrockImage, "png");
        }
    }

    private record SignData(String name, String bedrockName) {}
}
