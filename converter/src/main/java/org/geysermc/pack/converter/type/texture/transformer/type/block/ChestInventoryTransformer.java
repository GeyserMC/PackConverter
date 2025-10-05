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

package org.geysermc.pack.converter.type.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
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
public class ChestInventoryTransformer implements TextureTransformer {
    private static final List<ChestData> CHESTS = List.of(
            new ChestData(
                    "entity/chest/normal.png",
                    "blocks/chest_front.png",
                    "blocks/chest_side.png",
                    "blocks/chest_top.png"
            ),
            new ChestData(
                    "entity/chest/trapped.png",
                    "blocks/trapped_chest_front.png",
                    null, // Bedrock uses normal chest stuff here, so we can just skip this
                    null
            ),
            new ChestData(
                    "entity/chest/ender.png",
                    "blocks/ender_chest_front.png",
                    "blocks/ender_chest_side.png",
                    "blocks/ender_chest_top.png"
            ),
            new ChestData(
                    "entity/chest/copper.png",
                    "blocks/copper_chest_inventory_front.png",
                    "blocks/copper_chest_inventory_side.png",
                    "blocks/copper_chest_inventory_top.png"
            ),
            new ChestData(
                    "entity/chest/copper_exposed.png",
                    "blocks/exposed_copper_chest_inventory_front.png",
                    "blocks/exposed_copper_chest_inventory_side.png",
                    "blocks/exposed_copper_chest_inventory_top.png"
            ),
            new ChestData(
                    "entity/chest/copper_weathered.png",
                    "blocks/weathered_copper_chest_inventory_front.png",
                    "blocks/weathered_copper_chest_inventory_side.png",
                    "blocks/weathered_copper_chest_inventory_top.png"
            ),
            new ChestData(
                    "entity/chest/copper_oxidized.png",
                    "blocks/oxidized_copper_chest_inventory_front.png",
                    "blocks/oxidized_copper_chest_inventory_side.png",
                    "blocks/oxidized_copper_chest_inventory_top.png"
            )
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ChestData chest : CHESTS) {
            Texture texture = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, chest.javaName()));
            if (texture == null) {
                continue;
            }
            
            context.debug(String.format("Creating chest inventory textures %s", chest.javaName()));

            BufferedImage fromImage = ImageUtil.ensureMinWidth(this.readImage(texture), 64);

            float scale = fromImage.getWidth() / 64f;

            BufferedImage frontImage = new BufferedImage((int) (14 * scale), (int) (14 * scale), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = frontImage.getGraphics();

            graphics.drawImage(ImageUtil.crop(fromImage, (int) (14 * scale), (int) (14 * scale), (int) (14 * scale), (int) (5 * scale)), 0, 0, null);
            graphics.drawImage(ImageUtil.crop(fromImage, (int) (14 * scale), (int) (34 * scale), (int) (14 * scale), (int) (9 * scale)), 0, (int) (5 * scale), null);
            graphics.drawImage(ImageUtil.crop(fromImage, (int) scale, (int) scale, (int) (2 * scale), (int) (4 * scale)), (int) (6 * scale), (int) (3 * scale), null);

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, chest.bedrockFront()), frontImage, "png");

            if (chest.bedrockSide() != null) {
                BufferedImage sideImage = new BufferedImage((int) (14 * scale), (int) (14 * scale), BufferedImage.TYPE_INT_ARGB);

                graphics = sideImage.getGraphics();
                graphics.drawImage(ImageUtil.crop(fromImage, (int) (28 * scale), (int) (14 * scale), (int) (14 * scale), (int) (5 * scale)), 0, 0, null);
                graphics.drawImage(ImageUtil.crop(fromImage, (int) (28 * scale), (int) (34 * scale), (int) (14 * scale), (int) (9 * scale)), 0, (int) (5 * scale), null);

                context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, chest.bedrockSide()), sideImage, "png");
            }

            if (chest.bedrockTop() != null) {
                BufferedImage topImage = new BufferedImage((int) (14 * scale), (int) (14 * scale), BufferedImage.TYPE_INT_ARGB);

                graphics = topImage.getGraphics();
                graphics.drawImage(ImageUtil.crop(fromImage, (int) (28 * scale), 0, (int) (14 * scale), (int) (14 * scale)), 0, 0, null);

                context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, chest.bedrockTop()), topImage, "png");
            }
        }
    }
    
    record ChestData(@NotNull String javaName, @NotNull String bedrockFront, @Nullable String bedrockSide, @Nullable String bedrockTop) {
    }
}
