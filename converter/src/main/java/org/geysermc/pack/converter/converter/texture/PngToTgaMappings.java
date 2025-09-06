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
 *  THE SOFTWARE IS PROVIDED "AS IS", new Mapping(WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
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

package org.geysermc.pack.converter.converter.texture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

// TODO: This should really be a JSON file
public final class PngToTgaMappings {
    private static final Map<String, TgaMapping> MAPPINGS = new HashMap<>() {
        {
            put("textures/blocks/cactus_bottom.png", new TgaMapping("textures/blocks/cactus_bottom.tga"));
            put("textures/blocks/cactus_side.png", new TgaMapping("textures/blocks/cactus_side.tga"));
            put("textures/blocks/cactus_top.png", new TgaMapping("textures/blocks/cactus_top.tga"));
            put("textures/blocks/double_plant_fern_bottom.png", new TgaMapping("textures/blocks/double_plant_fern_bottom.tga"));
            put("textures/blocks/double_plant_fern_top.png", new TgaMapping("textures/blocks/double_plant_fern_top.tga"));
            put("textures/blocks/double_plant_grass_bottom.png", new TgaMapping("textures/blocks/double_plant_grass_bottom.tga"));
            put("textures/blocks/double_plant_grass_top.png", new TgaMapping("textures/blocks/double_plant_grass_top.tga"));
            put("textures/blocks/double_plant_syringa_bottom.png", new TgaMapping("textures/blocks/double_plant_syringa_bottom.tga"));
            put("textures/blocks/double_plant_syringa_top.png", new TgaMapping("textures/blocks/double_plant_syringa_top.tga"));
            put("textures/blocks/fern.png", new TgaMapping("textures/blocks/fern.tga"));
            put("textures/blocks/fern_carried.png", new TgaMapping("textures/blocks/fern_carried.tga"));
            put("textures/blocks/grass_side.png", new TgaMapping("textures/blocks/grass_side.tga"));
            put("textures/blocks/grass_side_snowed.png", new TgaMapping("textures/blocks/grass_side_snowed.tga", true));
            put("textures/blocks/grindstone_pivot.png", new TgaMapping("textures/blocks/grindstone_pivot.tga"));
            put("textures/blocks/grindstone_round.png", new TgaMapping("textures/blocks/grindstone_round.tga"));
            put("textures/blocks/grindstone_side.png", new TgaMapping("textures/blocks/grindstone_side.tga"));
            put("textures/blocks/kelp_a.png", new TgaMapping("textures/blocks/kelp_a.tga"));
            put("textures/blocks/kelp_b.png", new TgaMapping("textures/blocks/kelp_b.tga"));
            put("textures/blocks/kelp_c.png", new TgaMapping("textures/blocks/kelp_c.tga"));
            put("textures/blocks/kelp_d.png", new TgaMapping("textures/blocks/kelp_d.tga"));
            put("textures/blocks/kelp_top.png", new TgaMapping("textures/blocks/kelp_top.tga"));
            put("textures/blocks/kelp_top_bulb.png", new TgaMapping("textures/blocks/kelp_top_bulb.tga"));
            put("textures/blocks/leaves_acacia.png", new TgaMapping("textures/blocks/leaves_acacia.tga"));
            put("textures/blocks/leaves_acacia_carried.png", new TgaMapping("textures/blocks/leaves_acacia_carried.tga"));
            put("textures/blocks/leaves_big_oak.png", new TgaMapping("textures/blocks/leaves_big_oak.tga"));
            put("textures/blocks/leaves_big_oak_carried.png", new TgaMapping("textures/blocks/leaves_big_oak_carried.tga"));
            put("textures/blocks/leaves_birch.png", new TgaMapping("textures/blocks/leaves_birch.tga"));
            put("textures/blocks/leaves_birch_carried.png", new TgaMapping("textures/blocks/leaves_birch_carried.tga"));
            put("textures/blocks/leaves_jungle.png", new TgaMapping("textures/blocks/leaves_jungle.tga"));
            put("textures/blocks/leaves_jungle_carried.png", new TgaMapping("textures/blocks/leaves_jungle_carried.tga"));
            put("textures/blocks/leaves_oak.png", new TgaMapping("textures/blocks/leaves_oak.tga"));
            put("textures/blocks/leaves_oak_carried.png", new TgaMapping("textures/blocks/leaves_oak_carried.tga"));
            put("textures/blocks/leaves_spruce.png", new TgaMapping("textures/blocks/leaves_spruce.tga"));
            put("textures/blocks/leaves_spruce_carried.png", new TgaMapping("textures/blocks/leaves_spruce_carried.tga"));
            put("textures/blocks/reeds.png", new TgaMapping("textures/blocks/reeds.tga"));
            put("textures/blocks/scaffolding_bottom.png", new TgaMapping("textures/blocks/scaffolding_bottom.tga"));
            put("textures/blocks/scaffolding_side.png", new TgaMapping("textures/blocks/scaffolding_side.tga"));
            put("textures/blocks/scaffolding_top.png", new TgaMapping("textures/blocks/scaffolding_top.tga"));
            put("textures/blocks/seagrass_doubletall_bottom_a.png", new TgaMapping("textures/blocks/seagrass_doubletall_bottom_a.tga"));
            put("textures/blocks/seagrass_doubletall_bottom_b.png", new TgaMapping("textures/blocks/seagrass_doubletall_bottom_b.tga"));
            put("textures/blocks/seagrass_doubletall_top_a.png", new TgaMapping("textures/blocks/seagrass_doubletall_top_a.tga"));
            put("textures/blocks/seagrass_doubletall_top_b.png", new TgaMapping("textures/blocks/seagrass_doubletall_top_b.tga"));
            put("textures/blocks/stonecutter2_saw.png", new TgaMapping("textures/blocks/stonecutter2_saw.tga"));
            put("textures/blocks/tallgrass.png", new TgaMapping("textures/blocks/tallgrass.tga", true));
            put("textures/blocks/tallgrass_carried.png", new TgaMapping("textures/blocks/tallgrass_carried.tga"));
            put("textures/entity/blaze.png", new TgaMapping("textures/entity/blaze.tga"));
            put("textures/entity/phantom.png", new TgaMapping("textures/entity/phantom.tga"));
            put("textures/entity/banner/banner.png", new TgaMapping("textures/entity/banner/banner.tga"));
            put("textures/entity/banner/banner_pattern_illager.png", new TgaMapping("textures/entity/banner/banner_pattern_illager.tga"));
            put("textures/entity/banner_base.png", new TgaMapping("textures/entity/banner/banner_base.tga"));
            put("textures/entity/banner/border.png", new TgaMapping("textures/entity/banner/banner_border.tga"));
            put("textures/entity/banner/bricks.png", new TgaMapping("textures/entity/banner/banner_bricks.tga"));
            put("textures/entity/banner/circle.png", new TgaMapping("textures/entity/banner/banner_circle.tga"));
            put("textures/entity/banner/creeper.png", new TgaMapping("textures/entity/banner/banner_creeper.tga"));
            put("textures/entity/banner/cross.png", new TgaMapping("textures/entity/banner/banner_cross.tga"));
            put("textures/entity/banner/curly_border.png", new TgaMapping("textures/entity/banner/banner_curly_border.tga"));
            put("textures/entity/banner/diagonal_left.png", new TgaMapping("textures/entity/banner/banner_diagonal_left.tga"));
            put("textures/entity/banner/diagonal_right.png", new TgaMapping("textures/entity/banner/banner_diagonal_right.tga"));
            put("textures/entity/banner/diagonal_up_left.png", new TgaMapping("textures/entity/banner/banner_diagonal_up_left.tga"));
            put("textures/entity/banner/diagonal_up_right.png", new TgaMapping("textures/entity/banner/banner_diagonal_up_right.tga"));
            put("textures/entity/banner/flower.png", new TgaMapping("textures/entity/banner/banner_flower.tga"));
            put("textures/entity/banner/gradient.png", new TgaMapping("textures/entity/banner/banner_gradient.tga"));
            put("textures/entity/banner/gradient_up.png", new TgaMapping("textures/entity/banner/banner_gradient_up.tga"));
            put("textures/entity/banner/half_horizontal.png", new TgaMapping("textures/entity/banner/banner_half_horizontal.tga"));
            put("textures/entity/banner/half_horizontal_bottom.png", new TgaMapping("textures/entity/banner/banner_half_horizontal_bottom.tga"));
            put("textures/entity/banner/half_vertical.png", new TgaMapping("textures/entity/banner/banner_half_vertical.tga"));
            put("textures/entity/banner/half_vertical_right.png", new TgaMapping("textures/entity/banner/banner_half_vertical_right.tga"));
            put("textures/entity/banner/mojang.png", new TgaMapping("textures/entity/banner/banner_mojang.tga"));
            put("textures/entity/banner/piglin.png", new TgaMapping("textures/entity/banner/banner_piglin.tga"));
            put("textures/entity/banner/rhombus.png", new TgaMapping("textures/entity/banner/banner_rhombus.tga"));
            put("textures/entity/banner/skull.png", new TgaMapping("textures/entity/banner/banner_skull.tga"));
            put("textures/entity/banner/small_stripes.png", new TgaMapping("textures/entity/banner/banner_small_stripes.tga"));
            put("textures/entity/banner/square_bottom_left.png", new TgaMapping("textures/entity/banner/banner_square_bottom_left.tga"));
            put("textures/entity/banner/square_bottom_right.png", new TgaMapping("textures/entity/banner/banner_square_bottom_right.tga"));
            put("textures/entity/banner/square_top_left.png", new TgaMapping("textures/entity/banner/banner_square_top_left.tga"));
            put("textures/entity/banner/square_top_right.png", new TgaMapping("textures/entity/banner/banner_square_top_right.tga"));
            put("textures/entity/banner/straight_cross.png", new TgaMapping("textures/entity/banner/banner_straight_cross.tga"));
            put("textures/entity/banner/stripe_bottom.png", new TgaMapping("textures/entity/banner/banner_stripe_bottom.tga"));
            put("textures/entity/banner/stripe_center.png", new TgaMapping("textures/entity/banner/banner_stripe_center.tga"));
            put("textures/entity/banner/stripe_downleft.png", new TgaMapping("textures/entity/banner/banner_stripe_downleft.tga"));
            put("textures/entity/banner/stripe_downright.png", new TgaMapping("textures/entity/banner/banner_stripe_downright.tga"));
            put("textures/entity/banner/stripe_left.png", new TgaMapping("textures/entity/banner/banner_stripe_left.tga"));
            put("textures/entity/banner/stripe_middle.png", new TgaMapping("textures/entity/banner/banner_stripe_middle.tga"));
            put("textures/entity/banner/stripe_right.png", new TgaMapping("textures/entity/banner/banner_stripe_right.tga"));
            put("textures/entity/banner/stripe_top.png", new TgaMapping("textures/entity/banner/banner_stripe_top.tga"));
            put("textures/entity/banner/triangle_bottom.png", new TgaMapping("textures/entity/banner/banner_triangle_bottom.tga"));
            put("textures/entity/banner/triangle_top.png", new TgaMapping("textures/entity/banner/banner_triangle_top.tga"));
            put("textures/entity/banner/triangles_bottom.png", new TgaMapping("textures/entity/banner/banner_triangles_bottom.tga"));
            put("textures/entity/banner/triangles_top.png", new TgaMapping("textures/entity/banner/banner_triangles_top.tga"));
            put("textures/entity/cat/allblackcat_tame.png", new TgaMapping("textures/entity/cat/allblackcat_tame.tga"));
            put("textures/entity/cat/britishshorthair_tame.png", new TgaMapping("textures/entity/cat/britishshorthair_tame.tga"));
            put("textures/entity/cat/calico_tame.png", new TgaMapping("textures/entity/cat/calico_tame.tga"));
            put("textures/entity/cat/graytabby_tame.png", new TgaMapping("textures/entity/cat/graytabby_tame.tga"));
            put("textures/entity/cat/jellie_tame.png", new TgaMapping("textures/entity/cat/jellie_tame.tga"));
            put("textures/entity/cat/ocelot_tame.png", new TgaMapping("textures/entity/cat/ocelot_tame.tga"));
            put("textures/entity/cat/persian_tame.png", new TgaMapping("textures/entity/cat/persian_tame.tga"));
            put("textures/entity/cat/ragdoll_tame.png", new TgaMapping("textures/entity/cat/ragdoll_tame.tga"));
            put("textures/entity/cat/redtabby_tame.png", new TgaMapping("textures/entity/cat/redtabby_tame.tga"));
            put("textures/entity/cat/siamesecat_tame.png", new TgaMapping("textures/entity/cat/siamesecat_tame.tga"));
            put("textures/entity/cat/tabby_tame.png", new TgaMapping("textures/entity/cat/tabby_tame.tga"));
            put("textures/entity/cat/tuxedo_tame.png", new TgaMapping("textures/entity/cat/tuxedo_tame.tga"));
            put("textures/entity/cat/white_tame.png", new TgaMapping("textures/entity/cat/white_tame.tga"));
            put("textures/entity/dragon/dragon.png", new TgaMapping("textures/entity/dragon/dragon.tga"));
            put("textures/entity/enderman/enderman.png", new TgaMapping("textures/entity/enderman/enderman.tga"));
            put("textures/entity/ghast/ghast_shooting.png", new TgaMapping("textures/entity/ghast/ghast_shooting.tga"));
            put("textures/entity/horse/armor/horse_armor_leather.png", new TgaMapping("textures/entity/horse/armor/horse_armor_leather.tga"));
            put("textures/entity/horse2/armor/horse_armor_leather.png", new TgaMapping("textures/entity/horse2/armor/horse_armor_leather.tga"));
            put("textures/entity/sheep/sheep.png", new TgaMapping("textures/entity/sheep/sheep.tga"));
            put("textures/entity/slime/magmacube.png", new TgaMapping("textures/entity/slime/magmacube.tga"));
            put("textures/entity/spider/cave_spider.png", new TgaMapping("textures/entity/spider/cave_spider.tga"));
            put("textures/entity/spider/spider.png", new TgaMapping("textures/entity/spider/spider.tga"));
            put("textures/entity/villager2/professions/armorer.png", new TgaMapping("textures/entity/villager2/professions/armorer.tga"));
            put("textures/entity/villager2/professions/butcher.png", new TgaMapping("textures/entity/villager2/professions/butcher.tga"));
            put("textures/entity/villager2/professions/cartographer.png", new TgaMapping("textures/entity/villager2/professions/cartographer.tga"));
            put("textures/entity/villager2/professions/cleric.png", new TgaMapping("textures/entity/villager2/professions/cleric.tga"));
            put("textures/entity/villager2/professions/farmer.png", new TgaMapping("textures/entity/villager2/professions/farmer.tga"));
            put("textures/entity/villager2/professions/fisherman.png", new TgaMapping("textures/entity/villager2/professions/fisherman.tga"));
            put("textures/entity/villager2/professions/fletcher.png", new TgaMapping("textures/entity/villager2/professions/fletcher.tga"));
            put("textures/entity/villager2/professions/leatherworker.png", new TgaMapping("textures/entity/villager2/professions/leatherworker.tga"));
            put("textures/entity/villager2/professions/librarian.png", new TgaMapping("textures/entity/villager2/professions/librarian.tga"));
            put("textures/entity/villager2/professions/nitwit.png", new TgaMapping("textures/entity/villager2/professions/nitwit.tga"));
            put("textures/entity/villager2/professions/shepherd.png", new TgaMapping("textures/entity/villager2/professions/shepherd.tga"));
            put("textures/entity/villager2/professions/stonemason.png", new TgaMapping("textures/entity/villager2/professions/stonemason.tga"));
            put("textures/entity/villager2/professions/toolsmith.png", new TgaMapping("textures/entity/villager2/professions/toolsmith.tga"));
            put("textures/entity/villager2/professions/unskilled.png", new TgaMapping("textures/entity/villager2/professions/unskilled.tga"));
            put("textures/entity/villager2/professions/weaponsmith.png", new TgaMapping("textures/entity/villager2/professions/weaponsmith.tga"));
            put("textures/entity/wolf/wolf_tame.png", new TgaMapping("textures/entity/wolf/wolf_tame.tga"));
            put("textures/entity/zombie/drowned.png", new TgaMapping("textures/entity/zombie/drowned.tga"));
            put("textures/entity/zombie_villager2/professions/armorer.png", new TgaMapping("textures/entity/zombie_villager2/professions/armorer.tga"));
            put("textures/entity/zombie_villager2/professions/butcher.png", new TgaMapping("textures/entity/zombie_villager2/professions/butcher.tga"));
            put("textures/entity/zombie_villager2/professions/cartographer.png", new TgaMapping("textures/entity/zombie_villager2/professions/cartographer.tga"));
            put("textures/entity/zombie_villager2/professions/cleric.png", new TgaMapping("textures/entity/zombie_villager2/professions/cleric.tga"));
            put("textures/entity/zombie_villager2/professions/farmer.png", new TgaMapping("textures/entity/zombie_villager2/professions/farmer.tga"));
            put("textures/entity/zombie_villager2/professions/fisherman.png", new TgaMapping("textures/entity/zombie_villager2/professions/fisherman.tga"));
            put("textures/entity/zombie_villager2/professions/fletcher.png", new TgaMapping("textures/entity/zombie_villager2/professions/fletcher.tga"));
            put("textures/entity/zombie_villager2/professions/leatherworker.png", new TgaMapping("textures/entity/zombie_villager2/professions/leatherworker.tga"));
            put("textures/entity/zombie_villager2/professions/librarian.png", new TgaMapping("textures/entity/zombie_villager2/professions/librarian.tga"));
            put("textures/entity/zombie_villager2/professions/nitwit.png", new TgaMapping("textures/entity/zombie_villager2/professions/nitwit.tga"));
            put("textures/entity/zombie_villager2/professions/shepherd.png", new TgaMapping("textures/entity/zombie_villager2/professions/shepherd.tga"));
            put("textures/entity/zombie_villager2/professions/stonemason.png", new TgaMapping("textures/entity/zombie_villager2/professions/stonemason.tga"));
            put("textures/entity/zombie_villager2/professions/toolsmith.png", new TgaMapping("textures/entity/zombie_villager2/professions/toolsmith.tga"));
            put("textures/entity/zombie_villager2/professions/weaponsmith.png", new TgaMapping("textures/entity/zombie_villager2/professions/weaponsmith.tga"));
            put("textures/items/fireworks_charge.png", new TgaMapping("textures/items/fireworks_charge.tga"));
            put("textures/items/leather_boots.png", new TgaMapping("textures/items/leather_boots.tga"));
            put("textures/items/leather_helmet.png", new TgaMapping("textures/items/leather_helmet.tga"));
            put("textures/items/leather_horse_armor.png", new TgaMapping("textures/items/leather_horse_armor.tga"));
            put("textures/items/leather_leggings.png", new TgaMapping("textures/items/leather_leggings.tga"));
            put("textures/models/armor/leather_1.png", new TgaMapping("textures/models/armor/leather_1.tga"));
            put("textures/models/armor/leather_2.png", new TgaMapping("textures/models/armor/leather_2.tga"));
        }
    };
    
    @Nullable
    public static TgaMapping mapping(@NotNull String key) {
        return MAPPINGS.get(key);
    }

    // TODO make package-private again
    public record TgaMapping(String value, boolean keep) {
        
        public TgaMapping(String value) {
            this(value, false);
        }
    }
}
