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

package org.geysermc.pack.converter.converter.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class LiquidTransformer implements TextureTransformer {
    private static final List<LiquidData> LIQUID = List.of(
            new LiquidData("block/lava_flow.png", "blocks/lava_flow.png", 32),
            new LiquidData("block/lava_still.png", "blocks/lava_still.png", 16),
            new LiquidData("block/water_flow.png", "blocks/water_flow_grey.png", 32, true),
            new LiquidData("block/water_still.png", "blocks/water_still_grey.png", 16, true)
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (LiquidData liquid : LIQUID) {
            String javaName = liquid.javaName();
            String bedrockName = liquid.bedrockName();
            int minWidth = liquid.minWidth();
            boolean grayscale = liquid.grayscale();

            Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, javaName));
            if (texture == null) {
                continue;
            }

            context.info(String.format("Converting liquid %s", bedrockName));

            BufferedImage liquidImage = this.readImage(texture);

            if (grayscale) {
                liquidImage = ImageUtil.grayscale(liquidImage);
            }

            liquidImage = ImageUtil.ensureMinWidth(liquidImage, minWidth);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, bedrockName), liquidImage, "png");
        }
    }
    
    record LiquidData(@NotNull String javaName, @NotNull String bedrockName, int minWidth, boolean grayscale) {
        
        public LiquidData(@NotNull String javaName, @NotNull String bedrockName, int minWidth) {
            this(javaName, bedrockName, minWidth, false);
        }
    }
}
