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

package org.geysermc.pack.converter.converter.texture.transformer.type.entity;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class DrownedTransformer implements TextureTransformer {
    private static final String DROWNED_TEXTURE = "drowned";
    private static final String DROWNED_OUTER_LAYER_TEXTURE = "drowned_outer_layer";
    
    private static final String TEXTURE_PATH = "entity/zombie/%s.png";
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture drownedTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, String.format(TEXTURE_PATH, DROWNED_TEXTURE)));
        Texture outerLayerTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, String.format(TEXTURE_PATH, DROWNED_OUTER_LAYER_TEXTURE)));
        
        if (drownedTexture == null || outerLayerTexture == null) {
            return;
        }
        
        context.info("Converting drowned texture");

        BufferedImage fromImage = this.readImage(drownedTexture);
        BufferedImage overlayImage = ImageUtil.ensureMinWidth(this.readImage(outerLayerTexture), 64);

        int factor = fromImage.getWidth() / 64;

        BufferedImage newImage = new BufferedImage(fromImage.getWidth(), fromImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = newImage.getGraphics();

        graphics.drawImage(fromImage, 0, 0, null);

        graphics.drawImage(ImageUtil.crop(overlayImage, 0, 0, (32 * factor), (16 * factor)), (32 * factor), 0, null);
        graphics.drawImage(ImageUtil.crop(overlayImage, 0, (16 * factor), (64 * factor), (16 * factor)), 0, (32 * factor), null);
        graphics.drawImage(ImageUtil.crop(overlayImage, (16 * factor), (48 * factor), (16 * factor), (16 * factor)), 0, (48 * factor), null);
        graphics.drawImage(ImageUtil.crop(overlayImage, (32 * factor), (48 * factor), (16 * factor), (16 * factor)), (48 * factor), (48 * factor), null);

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, String.format(TEXTURE_PATH, DROWNED_TEXTURE)), newImage, "png");
    }
}
