/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
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

package org.geysermc.packconverter.api.converters;

import lombok.Getter;
import org.geysermc.packconverter.api.PackConverter;
import org.geysermc.packconverter.api.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlaceholderConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Bed
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 2, 6, 14, 14, 64, "textures/blocks/bed_head_top.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 0, 6, 6, 14, 64, "textures/blocks/bed_head_side.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 6, 0, 14, 6, 64, "textures/blocks/bed_head_end.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 2, 20, 14, 14, 64, "textures/blocks/bed_feet_top.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 22, 0, 14, 6, 64, "textures/blocks/bed_feet_end.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 0, 20, 6, 14, 64, "textures/blocks/bed_feet_side.png"});

        // Bed item
        defaultData.add(new Object[] {"textures/entity/bed/black.png", 6, 6, 16, 32, 64, "textures/items/bed_black.png"});
        defaultData.add(new Object[] {"textures/entity/bed/blue.png", 6, 6, 16, 32, 64, "textures/items/bed_blue.png"});
        defaultData.add(new Object[] {"textures/entity/bed/brown.png", 6, 6, 16, 32, 64, "textures/items/bed_brown.png"});
        defaultData.add(new Object[] {"textures/entity/bed/cyan.png", 6, 6, 16, 32, 64, "textures/items/bed_cyan.png"});
        defaultData.add(new Object[] {"textures/entity/bed/gray.png", 6, 6, 16, 32, 64, "textures/items/bed_gray.png"});
        defaultData.add(new Object[] {"textures/entity/bed/green.png", 6, 6, 16, 32, 64, "textures/items/bed_green.png"});
        defaultData.add(new Object[] {"textures/entity/bed/light_blue.png", 6, 6, 16, 32, 64, "textures/items/bed_light_blue.png"});
        defaultData.add(new Object[] {"textures/entity/bed/lime.png", 6, 6, 16, 32, 64, "textures/items/bed_lime.png"});
        defaultData.add(new Object[] {"textures/entity/bed/magenta.png", 6, 6, 16, 32, 64, "textures/items/bed_magenta.png"});
        defaultData.add(new Object[] {"textures/entity/bed/orange.png", 6, 6, 16, 32, 64, "textures/items/bed_orange.png"});
        defaultData.add(new Object[] {"textures/entity/bed/pink.png", 6, 6, 16, 32, 64, "textures/items/bed_pink.png"});
        defaultData.add(new Object[] {"textures/entity/bed/purple.png", 6, 6, 16, 32, 64, "textures/items/bed_purple.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png", 6, 6, 16, 32, 64, "textures/items/bed_red.png"});
        defaultData.add(new Object[] {"textures/entity/bed/silver.png", 6, 6, 16, 32, 64, "textures/items/bed_silver.png"});
        defaultData.add(new Object[] {"textures/entity/bed/white.png", 6, 6, 16, 32, 64, "textures/items/bed_white.png"});
        defaultData.add(new Object[] {"textures/entity/bed/yellow.png", 6, 6, 16, 32, 64, "textures/items/bed_yellow.png"});

        // Chain
        defaultData.add(new Object[] {"textures/blocks/chain.png", 3, 0, 3, 16, 16, "textures/blocks/chain1.png", 1});
        defaultData.add(new Object[] {"textures/blocks/chain.png", 0, 0, 3, 16, 16, "textures/blocks/chain2.png", 1});

        // Chest
        defaultData.add(new Object[] {"textures/entity/chest/normal.png", 14, 0, 14, 14, 64, "textures/blocks/chest_top.png"});
        defaultData.add(new Object[] {"textures/entity/chest/ender.png", 14, 0, 14, 14, 64, "textures/blocks/ender_chest_top.png"});

        // Conduit
        defaultData.add(new Object[] {"textures/blocks/conduit_base.png", 0, 0, 24, 12, 32, "textures/blocks/conduit_base.png", 2});
        defaultData.add(new Object[] {"textures/blocks/conduit_closed.png", 0, 0, 8, 8, 16, "textures/blocks/conduit_closed.png", 2, 5});
        defaultData.add(new Object[] {"textures/blocks/conduit_open.png", 0, 0, 8, 8, 16, "textures/blocks/conduit_open.png", 2, 5});

        // Command block
        defaultData.add(new Object[] {"textures/blocks/chain_command_block_back.png", 0, 0, 16, 16, 16, "textures/blocks/chain_command_block_back_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/chain_command_block_conditional.png", 0, 0, 16, 16, 16, "textures/blocks/chain_command_block_conditional_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/chain_command_block_front.png", 0, 0, 16, 16, 16, "textures/blocks/chain_command_block_front_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/chain_command_block_side.png", 0, 0, 16, 16, 16, "textures/blocks/chain_command_block_side_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/command_block_back.png", 0, 0, 16, 16, 16, "textures/blocks/command_block_back_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/command_block_conditional.png", 0, 0, 16, 16, 16, "textures/blocks/command_block_conditional_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/command_block_front.png", 0, 0, 16, 16, 16, "textures/blocks/command_block_front_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/command_block_side.png", 0, 0, 16, 16, 16, "textures/blocks/command_block_side_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/repeating_command_block_back.png", 0, 0, 16, 16, 16, "textures/blocks/repeating_command_block_back_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/repeating_command_block_conditional.png", 0, 0, 16, 16, 16, "textures/blocks/repeating_command_block_conditional_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/repeating_command_block_front.png", 0, 0, 16, 16, 16, "textures/blocks/repeating_command_block_front_mipmap.png"});
        defaultData.add(new Object[] {"textures/blocks/repeating_command_block_side.png", 0, 0, 16, 16, 16, "textures/blocks/repeating_command_block_side_mipmap.png"});

        // Compass & clock
        defaultData.add(new Object[] {"textures/items/compass_atlas.png", 0, 0, 16, 16, 16, "textures/items/compass_item.png"});
        defaultData.add(new Object[] {"textures/items/watch_atlas.png", 0, 0, 16, 16, 16, "textures/items/clock_item.png"});

        // Sign
        defaultData.add(new Object[] {"textures/entity/sign_acacia.png", 2, 2, 24, 12, 64, "textures/ui/sign_acacia.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_birch.png", 2, 2, 24, 12, 64, "textures/ui/sign_birch.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_crimson.png", 2, 2, 24, 12, 64, "textures/ui/sign_crimson.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_darkoak.png", 2, 2, 24, 12, 64, "textures/ui/sign_darkoak.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_jungle.png", 2, 2, 24, 12, 64, "textures/ui/sign_jungle.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign.png", 2, 2, 24, 12, 64, "textures/ui/sign.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_spruce.png", 2, 2, 24, 12, 64, "textures/ui/sign_spruce.png", 2});
        defaultData.add(new Object[] {"textures/entity/sign_warped.png", 2, 2, 24, 12, 64, "textures/ui/sign_warped.png", 2});

        // Water, lava & co.
        defaultData.add(new Object[] {"textures/blocks/cauldron_water.png", 0, 0, 16, 16, 16, "textures/blocks/cauldron_water_placeholder.png"});
        defaultData.add(new Object[] {"textures/blocks/fire_0.png", 0, 0, 16, 16, 16, "textures/blocks/fire_0_placeholder.png"});
        defaultData.add(new Object[] {"textures/blocks/fire_1.png", 0, 0, 16, 16, 16, "textures/blocks/fire_1_placeholder.png"});
        defaultData.add(new Object[] {"textures/blocks/lava_still.png", 0, 0, 16, 16, 16, "textures/blocks/lava_placeholder.png"});
        defaultData.add(new Object[] {"textures/blocks/portal.png", 0, 0, 16, 16, 16, "textures/blocks/portal_placeholder.png"});
        defaultData.add(new Object[] {"textures/blocks/water_still.png", 0, 0, 16, 16, 16, "textures/blocks/water_placeholder.png"});

        // Zombie
        defaultData.add(new Object[] {"textures/entity/pig/pigzombie.png", 0, 0, 64, 32, 64, "textures/entity/pig/pigzombie.png", 2});
        defaultData.add(new Object[] {"textures/entity/zombie/husk.png", 0, 0, 64, 32, 64, "textures/entity/zombie/husk.png", 2});
        defaultData.add(new Object[] {"textures/entity/zombie/zombie.png", 0, 0, 64, 32, 64, "textures/entity/zombie/zombie.png", 2});
    }

    public PlaceholderConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            int x = (int) this.data[1];
            int y = (int) this.data[2];
            int width = (int) this.data[3];
            int height = (int) this.data[4];
            int factorDetect = (int) this.data[5];
            String to = (String) this.data[6];
            int squareMode = this.data.length > 7 ? (int) this.data[7] : 0;
            int minPackFormat = this.data.length > 8 ? (int) this.data[8] : -1;

            if (minPackFormat > -1) {
                // TODO: Add support for min pack format
            }

            File placeholderFile = storage.resolve(from).toFile();

            if (!placeholderFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Create placeholder %s", to));

            BufferedImage placeholderImage = ImageUtils.ensureMinWidth(ImageIO.read(placeholderFile), factorDetect);

            int factor = placeholderImage.getWidth() / factorDetect;

            placeholderImage = ImageUtils.crop(placeholderImage, (x * factor), (y * factor), (width * factor), (height * factor));

            int size;
            BufferedImage newPlaceholderImage;
            switch (squareMode) {
                case 1:
                    // Left top
                    size = Math.max(width, height);
                    newPlaceholderImage = new BufferedImage((size * factor), (size * factor), BufferedImage.TYPE_INT_ARGB);

                    newPlaceholderImage.getGraphics().drawImage(placeholderImage, 0, 0, null);

                    placeholderImage = newPlaceholderImage;
                    break;

                case 2:
                    // No
                    break;

                case 0:
                default:
                    // Center
                    size = Math.max(width, height);
                    newPlaceholderImage = new BufferedImage((size * factor), (size * factor), BufferedImage.TYPE_INT_ARGB);

                    newPlaceholderImage.getGraphics().drawImage(placeholderImage, (((size * factor) - (width * factor)) / 2), (((size * factor) - (height * factor)) / 2), null);

                    placeholderImage = newPlaceholderImage;
            }

            ImageUtils.write(placeholderImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
