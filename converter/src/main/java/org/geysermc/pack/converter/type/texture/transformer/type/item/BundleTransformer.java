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

package org.geysermc.pack.converter.type.texture.transformer.type.item;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class BundleTransformer implements TextureTransformer {
    private static final List<String> COLORS = List.of(
            "black",
            "blue",
            "brown",
            "cyan",
            "gray",
            "green",
            "light_blue",
            "light_gray",
            "lime",
            "magenta",
            "orange",
            "pink",
            "purple",
            "red",
            "white",
            "yellow"
    );

    private static final String FRONT = "item/%s_bundle_open_front.png";
    private static final String BACK = "item/%s_bundle_open_back.png";

    private static final String BEDROCK_OUTPUT = "items/bundle_%s_open.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (String color : COLORS) {
            Texture backTexture = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, FRONT.formatted(color)));
            Texture frontTexture = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BACK.formatted(color)));
            if (backTexture == null || frontTexture == null) continue; // TODO: If one is missing, pull from vanilla pack

            BufferedImage backImage = this.readImage(backTexture);
            BufferedImage frontImage = this.readImage(frontTexture);

            BufferedImage bedrockImage = new BufferedImage(backImage.getWidth(), backImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g = bedrockImage.getGraphics();

            g.drawImage(backImage, 0, 0, null);
            g.drawImage(frontImage, 0, 0, null);

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_OUTPUT.formatted(color)), bedrockImage, "png");
        }
    }
}
