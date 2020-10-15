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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PngToTgaConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/blocks/cactus_bottom.png", "textures/blocks/cactus_bottom.tga"});
        defaultData.add(new Object[] {"textures/blocks/cactus_side.png", "textures/blocks/cactus_side.tga"});
        defaultData.add(new Object[] {"textures/blocks/cactus_top.png", "textures/blocks/cactus_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_fern_bottom.png", "textures/blocks/double_plant_fern_bottom.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_fern_top.png", "textures/blocks/double_plant_fern_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_grass_bottom.png", "textures/blocks/double_plant_grass_bottom.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_grass_top.png", "textures/blocks/double_plant_grass_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_syringa_bottom.png", "textures/blocks/double_plant_syringa_bottom.tga"});
        defaultData.add(new Object[] {"textures/blocks/double_plant_syringa_top.png", "textures/blocks/double_plant_syringa_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/fern.png", "textures/blocks/fern.tga"});
        defaultData.add(new Object[] {"textures/blocks/fern_carried.png", "textures/blocks/fern_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/grass_side.png", "textures/blocks/grass_side.tga"});
        defaultData.add(new Object[] {"textures/blocks/grass_side_snowed.png", "textures/blocks/grass_side_snowed.tga", true});
        defaultData.add(new Object[] {"textures/blocks/grindstone_pivot.png", "textures/blocks/grindstone_pivot.tga"});
        defaultData.add(new Object[] {"textures/blocks/grindstone_round.png", "textures/blocks/grindstone_round.tga"});
        defaultData.add(new Object[] {"textures/blocks/grindstone_side.png", "textures/blocks/grindstone_side.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_a.png", "textures/blocks/kelp_a.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_b.png", "textures/blocks/kelp_b.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_c.png", "textures/blocks/kelp_c.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_d.png", "textures/blocks/kelp_d.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_top.png", "textures/blocks/kelp_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/kelp_top_bulb.png", "textures/blocks/kelp_top_bulb.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_acacia.png", "textures/blocks/leaves_acacia.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_acacia_carried.png", "textures/blocks/leaves_acacia_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_big_oak.png", "textures/blocks/leaves_big_oak.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_big_oak_carried.png", "textures/blocks/leaves_big_oak_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_birch.png", "textures/blocks/leaves_birch.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_birch_carried.png", "textures/blocks/leaves_birch_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_jungle.png", "textures/blocks/leaves_jungle.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_jungle_carried.png", "textures/blocks/leaves_jungle_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_oak.png", "textures/blocks/leaves_oak.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_oak_carried.png", "textures/blocks/leaves_oak_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_spruce.png", "textures/blocks/leaves_spruce.tga"});
        defaultData.add(new Object[] {"textures/blocks/leaves_spruce_carried.png", "textures/blocks/leaves_spruce_carried.tga"});
        defaultData.add(new Object[] {"textures/blocks/reeds.png", "textures/blocks/reeds.tga"});
        defaultData.add(new Object[] {"textures/blocks/scaffolding_bottom.png", "textures/blocks/scaffolding_bottom.tga"});
        defaultData.add(new Object[] {"textures/blocks/scaffolding_side.png", "textures/blocks/scaffolding_side.tga"});
        defaultData.add(new Object[] {"textures/blocks/scaffolding_top.png", "textures/blocks/scaffolding_top.tga"});
        defaultData.add(new Object[] {"textures/blocks/seagrass_doubletall_bottom_a.png", "textures/blocks/seagrass_doubletall_bottom_a.tga"});
        defaultData.add(new Object[] {"textures/blocks/seagrass_doubletall_bottom_b.png", "textures/blocks/seagrass_doubletall_bottom_b.tga"});
        defaultData.add(new Object[] {"textures/blocks/seagrass_doubletall_top_a.png", "textures/blocks/seagrass_doubletall_top_a.tga"});
        defaultData.add(new Object[] {"textures/blocks/seagrass_doubletall_top_b.png", "textures/blocks/seagrass_doubletall_top_b.tga"});
        defaultData.add(new Object[] {"textures/blocks/stonecutter2_saw.png", "textures/blocks/stonecutter2_saw.tga"});
        defaultData.add(new Object[] {"textures/blocks/tallgrass.png", "textures/blocks/tallgrass.tga", true});
        defaultData.add(new Object[] {"textures/blocks/tallgrass_carried.png", "textures/blocks/tallgrass_carried.tga"});
        defaultData.add(new Object[] {"textures/entity/blaze.png", "textures/entity/blaze.tga"});
        defaultData.add(new Object[] {"textures/entity/phantom.png", "textures/entity/phantom.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/banner.png", "textures/entity/banner/banner.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/banner_pattern_illager.png", "textures/entity/banner/banner_pattern_illager.tga"});
        defaultData.add(new Object[] {"textures/entity/banner_base.png", "textures/entity/banner/banner_base.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/border.png", "textures/entity/banner/banner_border.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/bricks.png", "textures/entity/banner/banner_bricks.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/circle.png", "textures/entity/banner/banner_circle.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/creeper.png", "textures/entity/banner/banner_creeper.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/cross.png", "textures/entity/banner/banner_cross.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/curly_border.png", "textures/entity/banner/banner_curly_border.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_left.png", "textures/entity/banner/banner_diagonal_left.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_right.png", "textures/entity/banner/banner_diagonal_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_up_left.png", "textures/entity/banner/banner_diagonal_up_left.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_up_right.png", "textures/entity/banner/banner_diagonal_up_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/flower.png", "textures/entity/banner/banner_flower.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/gradient.png", "textures/entity/banner/banner_gradient.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/gradient_up.png", "textures/entity/banner/banner_gradient_up.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/half_horizontal.png", "textures/entity/banner/banner_half_horizontal.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/half_horizontal_bottom.png", "textures/entity/banner/banner_half_horizontal_bottom.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/half_vertical.png", "textures/entity/banner/banner_half_vertical.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/half_vertical_right.png", "textures/entity/banner/banner_half_vertical_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/mojang.png", "textures/entity/banner/banner_mojang.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/piglin.png", "textures/entity/banner/banner_piglin.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/rhombus.png", "textures/entity/banner/banner_rhombus.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/skull.png", "textures/entity/banner/banner_skull.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/small_stripes.png", "textures/entity/banner/banner_small_stripes.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/square_bottom_left.png", "textures/entity/banner/banner_square_bottom_left.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/square_bottom_right.png", "textures/entity/banner/banner_square_bottom_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/square_top_left.png", "textures/entity/banner/banner_square_top_left.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/square_top_right.png", "textures/entity/banner/banner_square_top_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/straight_cross.png", "textures/entity/banner/banner_straight_cross.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_bottom.png", "textures/entity/banner/banner_stripe_bottom.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_center.png", "textures/entity/banner/banner_stripe_center.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_downleft.png", "textures/entity/banner/banner_stripe_downleft.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_downright.png", "textures/entity/banner/banner_stripe_downright.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_left.png", "textures/entity/banner/banner_stripe_left.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_middle.png", "textures/entity/banner/banner_stripe_middle.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_right.png", "textures/entity/banner/banner_stripe_right.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_top.png", "textures/entity/banner/banner_stripe_top.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/triangle_bottom.png", "textures/entity/banner/banner_triangle_bottom.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/triangle_top.png", "textures/entity/banner/banner_triangle_top.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/triangles_bottom.png", "textures/entity/banner/banner_triangles_bottom.tga"});
        defaultData.add(new Object[] {"textures/entity/banner/triangles_top.png", "textures/entity/banner/banner_triangles_top.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/allblackcat_tame.png", "textures/entity/cat/allblackcat_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/britishshorthair_tame.png", "textures/entity/cat/britishshorthair_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/calico_tame.png", "textures/entity/cat/calico_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/graytabby_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/jellie_tame.png", "textures/entity/cat/jellie_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/ocelot_tame.png", "textures/entity/cat/ocelot_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/persian_tame.png", "textures/entity/cat/persian_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/ragdoll_tame.png", "textures/entity/cat/ragdoll_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/redtabby_tame.png", "textures/entity/cat/redtabby_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/siamesecat_tame.png", "textures/entity/cat/siamesecat_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/tabby_tame.png", "textures/entity/cat/tabby_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/tuxedo_tame.png", "textures/entity/cat/tuxedo_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/cat/white_tame.png", "textures/entity/cat/white_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/dragon/dragon.png", "textures/entity/dragon/dragon.tga"});
        defaultData.add(new Object[] {"textures/entity/enderman/enderman.png", "textures/entity/enderman/enderman.tga"});
        defaultData.add(new Object[] {"textures/entity/ghast/ghast_shooting.png", "textures/entity/ghast/ghast_shooting.tga"});
        defaultData.add(new Object[] {"textures/entity/horse/armor/horse_armor_leather.png", "textures/entity/horse/armor/horse_armor_leather.tga"});
        defaultData.add(new Object[] {"textures/entity/horse2/armor/horse_armor_leather.png", "textures/entity/horse2/armor/horse_armor_leather.tga"});
        defaultData.add(new Object[] {"textures/entity/sheep/sheep.png", "textures/entity/sheep/sheep.tga"});
        defaultData.add(new Object[] {"textures/entity/slime/magmacube.png", "textures/entity/slime/magmacube.tga"});
        defaultData.add(new Object[] {"textures/entity/spider/cave_spider.png", "textures/entity/spider/cave_spider.tga"});
        defaultData.add(new Object[] {"textures/entity/spider/spider.png", "textures/entity/spider/spider.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/armorer.png", "textures/entity/villager2/professions/armorer.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/butcher.png", "textures/entity/villager2/professions/butcher.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/cartographer.png", "textures/entity/villager2/professions/cartographer.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/cleric.png", "textures/entity/villager2/professions/cleric.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/farmer.png", "textures/entity/villager2/professions/farmer.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/fisherman.png", "textures/entity/villager2/professions/fisherman.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/fletcher.png", "textures/entity/villager2/professions/fletcher.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/leatherworker.png", "textures/entity/villager2/professions/leatherworker.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/librarian.png", "textures/entity/villager2/professions/librarian.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/nitwit.png", "textures/entity/villager2/professions/nitwit.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/shepherd.png", "textures/entity/villager2/professions/shepherd.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/stonemason.png", "textures/entity/villager2/professions/stonemason.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/toolsmith.png", "textures/entity/villager2/professions/toolsmith.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/unskilled.png", "textures/entity/villager2/professions/unskilled.tga"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/weaponsmith.png", "textures/entity/villager2/professions/weaponsmith.tga"});
        defaultData.add(new Object[] {"textures/entity/wolf/wolf_tame.png", "textures/entity/wolf/wolf_tame.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie/drowned.png", "textures/entity/zombie/drowned.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/armorer.png", "textures/entity/zombie_villager2/professions/armorer.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/butcher.png", "textures/entity/zombie_villager2/professions/butcher.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/cartographer.png", "textures/entity/zombie_villager2/professions/cartographer.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/cleric.png", "textures/entity/zombie_villager2/professions/cleric.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/farmer.png", "textures/entity/zombie_villager2/professions/farmer.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/fisherman.png", "textures/entity/zombie_villager2/professions/fisherman.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/fletcher.png", "textures/entity/zombie_villager2/professions/fletcher.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/leatherworker.png", "textures/entity/zombie_villager2/professions/leatherworker.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/librarian.png", "textures/entity/zombie_villager2/professions/librarian.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/nitwit.png", "textures/entity/zombie_villager2/professions/nitwit.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/shepherd.png", "textures/entity/zombie_villager2/professions/shepherd.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/stonemason.png", "textures/entity/zombie_villager2/professions/stonemason.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/toolsmith.png", "textures/entity/zombie_villager2/professions/toolsmith.tga"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/weaponsmith.png", "textures/entity/zombie_villager2/professions/weaponsmith.tga"});
        defaultData.add(new Object[] {"textures/items/fireworks_charge.png", "textures/items/fireworks_charge.tga"});
        defaultData.add(new Object[] {"textures/items/leather_boots.png", "textures/items/leather_boots.tga"});
        defaultData.add(new Object[] {"textures/items/leather_helmet.png", "textures/items/leather_helmet.tga"});
        defaultData.add(new Object[] {"textures/items/leather_horse_armor.png", "textures/items/leather_horse_armor.tga"});
        defaultData.add(new Object[] {"textures/items/leather_leggings.png", "textures/items/leather_leggings.tga"});
        defaultData.add(new Object[] {"textures/models/armor/leather_1.png", "textures/models/armor/leather_1.tga"});
        defaultData.add(new Object[] {"textures/models/armor/leather_2.png", "textures/models/armor/leather_2.tga"});
    }

    public PngToTgaConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];
            boolean dont_delete = this.data.length > 2 ? (boolean) this.data[2] : false;

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Create tga %s", from));

            BufferedImage fromImage = ImageIO.read(fromFile);
            ImageUtils.write(fromImage, "tga", storage.resolve(to).toFile());

            if (!dont_delete) {
                delete.add(new DeleteConverter(packConverter, storage, new Object[] {from}));
            }
        } catch (IOException e) { }

        return delete;
    }
}
