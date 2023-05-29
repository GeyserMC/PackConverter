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

package org.geysermc.pack.converter.converter.texture.transformer.bulk.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@AutoService(BulkTextureTransformer.class)
public class OverlayTransformer implements BulkTextureTransformer {
    private static final List<OverlayData> OVERLAYS = List.of(
            // Cat
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/all_black.png", "entity/cat/allblackcat_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/british_shorthair.png", "entity/cat/britishshorthair_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/calico.png", "entity/cat/calico_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/jellie.png", "entity/cat/jellie_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/ocelot.png", "entity/cat/ocelot_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/persian.png", "entity/cat/persian_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/ragdoll.png", "entity/cat/ragdoll_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/red.png", "entity/cat/redtabby_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/siamese.png", "entity/cat/siamesecat_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/tabby.png", "entity/cat/tabby_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/black.png", "entity/cat/tuxedo_tame.png", false, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/white.png", "entity/cat/white_tame.png", false, true),

            // Enderman
            new OverlayData("entity/enderman/enderman.png", "entity/enderman/enderman_eyes.png", "entity/enderman/enderman.png", true),

            // Firework
            new OverlayData("item/firework_star_overlay.png", "item/firework_star.png", "items/fireworks_charge.png", false),

            // Grass
            new OverlayData("block/grass_block_side_overlay.png", "block/grass_block_side.png", "blocks/grass_side.png", false, true),

            // Leather
            new OverlayData("item/leather_boots.png", "item/leather_boots_overlay.png", "items/leather_boots.png", true),
            new OverlayData("item/leather_chestplate.png", "item/leather_chestplate_overlay.png", "items/leather_chestplate.png", true),
            new OverlayData("item/leather_helmet.png", "item/leather_helmet_overlay.png", "items/leather_helmet.png", true),
            new OverlayData("item/leather_leggings.png", "item/leather_leggings_overlay.png", "items/leather_leggings.png", true),
            new OverlayData("models/armor/leather_layer_1.png", "models/armor/leather_layer_1_overlay.png", "models/armor/leather_1.png", true),
            new OverlayData("models/armor/leather_layer_2.png", "models/armor/leather_layer_2_overlay.png", "models/armor/leather_2.png", true),

            // Phantom
            new OverlayData("entity/phantom.png", "entity/phantom_eyes.png", "entity/phantom.png", true),

            // Spider
            new OverlayData("entity/spider/cave_spider.png", "entity/spider_eyes.png", "entity/spider/cave_spider.png", true, true),
            new OverlayData("entity/spider/spider.png", "entity/spider_eyes.png", "entity/spider/spider.png", true),

            // Wolf
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_tame.png", "entity/wolf/wolf_tame.png", false, true)
    );
    
    @Override
    public void transform(@NotNull BulkTransformContext context) throws IOException {
        for (OverlayData overlay : OVERLAYS) {
            String javaName = overlay.javaName();
            String overlayName = overlay.overlay();
            String bedrockName = overlay.bedrockName();
            boolean reverse = overlay.reverse();
            boolean keep = overlay.keep();

            Texture texture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, javaName));
            if (texture == null) {
                context.info(String.format("Base overlay texture %s not found", javaName));
                continue;
            }

            Texture overlayTexture = keep ? context.peek(Key.key(Key.MINECRAFT_NAMESPACE, overlayName)) : context.poll(Key.key(Key.MINECRAFT_NAMESPACE, overlayName));
            if (overlayTexture == null) {
                context.info(String.format("Overlay texture %s not found", overlayName));
                continue;
            }

            context.info(String.format("Overlaying %s and %s onto %s", overlayName, javaName, bedrockName));

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(texture.data().toByteArray()));
            BufferedImage imageOverlay = ImageIO.read(new ByteArrayInputStream(overlayTexture.data().toByteArray()));

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color c = new Color(image.getRGB(x, y), true);
                    if (reverse ? c.getAlpha() > 0 : c.getAlpha() < 255) {
                        Color newCol = new Color(imageOverlay.getRGB(x, y), true);
                        newCol = new Color(newCol.getRed(), newCol.getGreen(), newCol.getBlue(), 2);

                        image.setRGB(x, y, ImageUtil.colorToARGB(newCol));
                    }
                }
            }

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, bedrockName), image, "png");
        }
    }
    
    record OverlayData(@NotNull String javaName, @NotNull String overlay, @NotNull String bedrockName, boolean reverse, boolean keep) {

        public OverlayData(@NotNull String javaName, @NotNull String overlay, @NotNull String bedrockName, boolean reverse) {
            this(javaName, overlay, bedrockName, reverse, false);
        }
    }
}
