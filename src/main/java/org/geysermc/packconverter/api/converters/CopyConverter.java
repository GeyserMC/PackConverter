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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CopyConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Battern
        defaultData.add(new String[] {"textures/entity/banner/banner_pattern_illager.tga", "textures/entity/banner/banner_illager.tga"});

        // Beef
        defaultData.add(new String[] {"textures/items/beef_cooked.png", "textures/items/hoglin_meat_cooked.png"});
        defaultData.add(new String[] {"textures/items/beef_raw.png", "textures/items/hoglin_meat_raw.png"});

        // Cat
        defaultData.add(new String[] {"textures/entity/cat/redtabby.png","textures/entity/cat/red.png"});
        defaultData.add(new String[] {"textures/entity/cat/siamesecat.png", "textures/entity/cat/siamese.png"});
        defaultData.add(new String[] {"textures/entity/cat/tuxedo.png", "textures/entity/cat/blackcat.png"});

        // Cartography table
        defaultData.add(new String[] {"textures/blocks/cartography_table_top.png", "textures/ui/cartography_table_empty.png"});

        // Command block
        defaultData.add(new String[] {"textures/blocks/command_block_back_mipmap.png", "textures/blocks/command_block.png"});

        // Compass & clock
        defaultData.add(new String[] {"textures/items/compass_atlas.png", "textures/items/lodestonecompass_atlas.png"});
        defaultData.add(new String[] {"textures/items/compass_item.png", "textures/items/lodestonecompass_item.png"});

        // Fire
        defaultData.add(new String[] {"textures/blocks/fire_0.png", "textures/flame_atlas.png"});

        // Kelp
        defaultData.add(new String[] {"textures/blocks/kelp_a.tga", "textures/blocks/kelp_b.tga"});
        defaultData.add(new String[] {"textures/blocks/kelp_a.tga", "textures/blocks/kelp_c.tga"});
        defaultData.add(new String[] {"textures/blocks/kelp_a.tga", "textures/blocks/kelp_d.tga"});
        defaultData.add(new String[] {"textures/blocks/kelp_top.tga", "textures/blocks/kelp_top_bulb.tga"});

        // Lever
        defaultData.add(new String[] {"textures/blocks/lever.png", "textures/items/lever.png"});

        // Llama
        defaultData.add(new String[] {"textures/entity/llama/llama_creamy.png", "textures/entity/llama/llama.png"});

        // Pattern
        defaultData.add(new String[] {"textures/items/skull_banner_pattern.png", "textures/items/banner_pattern.png"});

        // Skull
        defaultData.add(new String[] {"textures/entity/creeper/creeper.png", "textures/entity/skulls/creeper.png"});
        defaultData.add(new String[] {"textures/entity/skeleton/skeleton.png", "textures/entity/skulls/skeleton.png"});
        defaultData.add(new String[] {"textures/entity/skeleton/wither_skeleton.png", "textures/entity/skulls/wither_skeleton.png"});
        defaultData.add(new String[] {"textures/entity/zombie/zombie.png", "textures/entity/skulls/zombie.png"});

        // UI
        defaultData.add(new String[] {"textures/blocks/brick.png", "textures/ui/icon_recipe_construction.png"});
        defaultData.add(new String[] {"textures/blocks/chest_front.png", "textures/ui/inventory_icon.png"});
        defaultData.add(new String[] {"textures/blocks/grass_side_carried.png", "textures/ui/icon_recipe_nature.png"});
        defaultData.add(new String[] {"textures/items/book_normal.png", "textures/ui/creative_icon.png"});
        defaultData.add(new String[] {"textures/items/bed_red.png", "textures/ui/icon_recipe_item.png"});
        defaultData.add(new String[] {"textures/items/compass_item.png", "textures/ui/magnifyingGlass.png"});
        defaultData.add(new String[] {"textures/items/diamond_sword.png", "textures/ui/icon_recipe_equipment.png"});
        defaultData.add(new String[] {"textures/items/empty_armor_slot_boots.png", "textures/ui/empty_armor_slot_boots.png"});
        defaultData.add(new String[] {"textures/items/empty_armor_slot_chestplate.png", "textures/ui/empty_armor_slot_chestplate.png"});
        defaultData.add(new String[] {"textures/items/empty_armor_slot_helmet.png", "textures/ui/empty_armor_slot_helmet.png"});
        defaultData.add(new String[] {"textures/items/empty_armor_slot_leggings.png", "textures/ui/empty_armor_slot_leggings.png"});
        defaultData.add(new String[] {"textures/items/empty_armor_slot_shield.png", "textures/ui/empty_armor_slot_shield.png"});

        // Wither
        defaultData.add(new String[] {"textures/entity/creeper/creeper_armor.png", "textures/entity/wither_boss/wither_armor_blue.png"});

        defaultData.add(new String[] {"bedrock_textures/", "textures/"});
    }

    public CopyConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            if (!storage.resolve(from).toFile().exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Copy %s to %s", from, to));

            Files.copy(storage.resolve(from), storage.resolve(to));
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
