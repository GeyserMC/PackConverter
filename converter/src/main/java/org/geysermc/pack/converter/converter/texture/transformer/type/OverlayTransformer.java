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

package org.geysermc.pack.converter.converter.texture.transformer.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class OverlayTransformer implements TextureTransformer {
    // This is used in VanillaPackProvider in order to get textures if one is missing out of the two
    public static final List<OverlayData> OVERLAYS = List.of(
            // Cat
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/all_black.png", "entity/cat/allblackcat_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/british_shorthair.png", "entity/cat/britishshorthair_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/calico.png", "entity/cat/calico_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/jellie.png", "entity/cat/jellie_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/ocelot.png", "entity/cat/ocelot_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/persian.png", "entity/cat/persian_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/ragdoll.png", "entity/cat/ragdoll_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/red.png", "entity/cat/redtabby_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/siamese.png", "entity/cat/siamesecat_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/tabby.png", "entity/cat/tabby_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/black.png", "entity/cat/tuxedo_tame.png", true, true),
            new OverlayData("entity/cat/cat_collar.png", "entity/cat/white.png", "entity/cat/white_tame.png", true, true),

            // Enderman
            new OverlayData("entity/enderman/enderman.png", "entity/enderman/enderman_eyes.png", "entity/enderman/enderman.png", false),

            // Firework
            new OverlayData("item/firework_star_overlay.png", "item/firework_star.png", "items/fireworks_charge.png", false),

            // Grass
            new OverlayData("block/grass_block_side_overlay.png", "block/grass_block_side.png", "blocks/grass_side.png", true, true),

            // Leather
            new OverlayData("item/leather_boots.png", "item/leather_boots_overlay.png", "items/leather_boots.png"),
            new OverlayData("item/leather_chestplate.png", "item/leather_chestplate_overlay.png", "items/leather_chestplate.png"),
            new OverlayData("item/leather_helmet.png", "item/leather_helmet_overlay.png", "items/leather_helmet.png"),
            new OverlayData("item/leather_leggings.png", "item/leather_leggings_overlay.png", "items/leather_leggings.png"),
            new OverlayData("entity/equipment/humanoid/leather.png", "entity/equipment/humanoid/leather_overlay.png", "models/armor/leather_1.png"),
            new OverlayData("entity/equipment/humanoid_leggings/leather.png", "entity/equipment/humanoid_leggings/leather_overlay.png", "models/armor/leather_2.png"),

            // Phantom
            new OverlayData("entity/phantom.png", "entity/phantom_eyes.png", "entity/phantom.png", false),

            // Spider
            new OverlayData("entity/spider/cave_spider.png", "entity/spider_eyes.png", "entity/spider/cave_spider.png", false, true),
            new OverlayData("entity/spider/spider.png", "entity/spider_eyes.png", "entity/spider/spider.png"),

            // Wolf
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_tame.png", "entity/wolf/wolf_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_ashen_tame.png", "entity/wolf/wolf_ashen_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_black_tame.png", "entity/wolf/wolf_black_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_chestnut_tame.png", "entity/wolf/wolf_chestnut_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_rusty_tame.png", "entity/wolf/wolf_rusty_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_snowy_tame.png", "entity/wolf/wolf_snowy_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_spotted_tame.png", "entity/wolf/wolf_spotted_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_striped_tame.png", "entity/wolf/wolf_striped_tame.png", true, true),
            new OverlayData("entity/wolf/wolf_collar.png", "entity/wolf/wolf_woods_tame.png", "entity/wolf/wolf_woods_tame.png", true, true)
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (OverlayData overlay : OVERLAYS) {
            String javaName = overlay.javaName();
            String overlayName = overlay.overlay();
            String bedrockName = overlay.bedrockName();
            boolean noReplace = overlay.noReplace();
            boolean keep = overlay.keep();

            Key javaKey = Key.key(Key.MINECRAFT_NAMESPACE, javaName);
            Key overlayKey = Key.key(Key.MINECRAFT_NAMESPACE, overlayName);

            // We don't have either textures, skip this
            if (!context.isTexturePresent(javaKey) && !context.isTexturePresent(overlayKey)) continue;

            Texture texture = context.peekOrVanilla(javaKey);
            if (texture == null) { // This ideally, shouldn't happen anymore
                context.info(String.format("Base overlay texture %s not found", javaName));
                continue;
            }

            BufferedImage image = this.readImage(texture);

            Texture overlayTexture = keep ? context.peekOrVanilla(overlayKey) : context.pollOrPeekVanilla(overlayKey);
            if (overlayTexture == null) { // This ideally, shouldn't happen anymore
                context.info(String.format("Overlay texture %s not found", overlayName));
                continue;
            }

            context.debug(String.format("Overlaying %s and %s onto %s", overlayName, javaName, bedrockName));

            BufferedImage imageOverlay = this.readImage(overlayTexture);

            // Scale to the base image so ensure we don't go out of bounds in the image
            float resizeX = (float) image.getWidth() / imageOverlay.getWidth();
            float resizeY = (float) image.getHeight() / imageOverlay.getHeight();
            imageOverlay = ImageUtil.scale(imageOverlay, resizeX, resizeY);

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if (noReplace) {
                        Color c = new Color(image.getRGB(x, y), true);

                        if (c.getAlpha() > 0) continue;
                    }

                    Color c = new Color(imageOverlay.getRGB(x, y), true);

                    if (c.getAlpha() == 255) {
                        Color newCol = new Color(c.getRed(), c.getGreen(), c.getBlue(), 2);

                        image.setRGB(x, y, ImageUtil.colorToARGB(newCol));
                    }
                }
            }

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, bedrockName), image, "png");
        }
    }
    
    public record OverlayData(@NotNull String javaName, @NotNull String overlay, @NotNull String bedrockName, boolean noReplace, boolean keep) {
        public OverlayData(@NotNull String javaName, @NotNull String overlay, @NotNull String bedrockName, boolean noReplace) {
            this(javaName, overlay, bedrockName, noReplace, false);
        }

        public OverlayData(@NotNull String javaName, @NotNull String overlay, @NotNull String bedrockName) {
            this(javaName, overlay, bedrockName, false, false);
        }
    }
}
