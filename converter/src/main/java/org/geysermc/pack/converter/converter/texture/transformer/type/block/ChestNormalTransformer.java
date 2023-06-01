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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class ChestNormalTransformer implements TextureTransformer {
    private static final String CHEST_PATH = "entity/chest";
    
    private static final List<String> VARIANTS = List.of(
            "christmas",
            "ender",
            "normal",
            "trapped"
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (String variant : VARIANTS) {
            Texture texture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, CHEST_PATH + "/" + variant + ".png"));
            if (texture == null) {
                continue;
            }
            
            context.info(String.format("Converting normal chest %s", variant));

            BufferedImage chestImage = ImageUtil.ensureMinWidth(this.readImage(texture), 64);
            int factor = chestImage.getWidth() / 64;

            BufferedImage newChestImage = new BufferedImage((64 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = newChestImage.getGraphics();

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, 0, (14 * factor), (14 * factor), (5 * factor)), 180), 0, (14 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, 0, (33 * factor), (14 * factor), (10 * factor)), 180), 0, (33 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(chestImage, (28 * factor), 0, (14 * factor), (14 * factor)), false, true), (14 * factor), 0, null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (42 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (14 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(chestImage, (28 * factor), (19 * factor), (14 * factor), (14 * factor)),false, true), (14 * factor), (19 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (42 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (14 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(chestImage, (14 * factor), 0, (14 * factor), (14 * factor)),false, true), (28 * factor), 0, null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (28 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (28 * factor), (14 * factor), null);

            graphics.drawImage(ImageUtil.flip(ImageUtil.crop(chestImage, (14 * factor), (19 * factor), (14 * factor), (14 * factor)),false, true), (28 * factor), (19 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (28 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (28 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (14 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (42 * factor), (14 * factor), null);
            graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(chestImage, (14 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (42 * factor), (33 * factor), null);

            graphics.drawImage(ImageUtil.crop(chestImage, 0, 0, (6 * factor), (6 * factor)), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, CHEST_PATH + "/" + variant + ".png"), newChestImage, "png");
        }
    }

    @Override
    public int order() {
        // Since we mutate the textures, make sure
        // the rest of the chest transformers get called
        // first before we do anything here.
        return ORDER_NORMAL;
    }
}
