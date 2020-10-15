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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RenameConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Icon
        defaultData.add(new String[] {"pack.png", "pack_icon.png"});

        // Base folder
        defaultData.add(new String[] {"assets/minecraft/textures/", "textures/"});
        defaultData.add(new String[] {"assets/minecraft/sounds/", "sounds/"});

        // Folder
        defaultData.add(new String[] {"textures/block/", "textures/blocks/"});
        defaultData.add(new String[] {"textures/item/", "textures/items/"});

        // Andesite
        defaultData.add(new String[] {"textures/blocks/andesite.png", "textures/blocks/stone_andesite.png"});
        defaultData.add(new String[] {"textures/blocks/polished_andesite.png", "textures/blocks/stone_andesite_smooth.png"});

        // Anvil
        defaultData.add(new String[] {"textures/blocks/anvil.png", "textures/blocks/anvil_base.png"});
        defaultData.add(new String[] {"textures/blocks/anvil_top.png", "textures/blocks/anvil_top_damaged_0.png"});
        defaultData.add(new String[] {"textures/blocks/chipped_anvil_top.png", "textures/blocks/anvil_top_damaged_1.png"});
        defaultData.add(new String[] {"textures/blocks/damaged_anvil_top.png", "textures/blocks/anvil_top_damaged_2.png"});

        // Apple
        defaultData.add(new String[] {"textures/items/golden_apple.png", "textures/items/apple_golden.png"});

        // Armor & tool
        defaultData.add(new String[] {"textures/entity/armorstand/wood.png", "textures/entity/armor_stand.png"});
        defaultData.add(new String[] {"textures/entity/elytra.png", "textures/models/armor/elytra.png"});
        defaultData.add(new String[] {"textures/items/golden_axe.png", "textures/items/gold_axe.png"});
        defaultData.add(new String[] {"textures/items/golden_boots.png", "textures/items/gold_boots.png"});
        defaultData.add(new String[] {"textures/items/golden_chestplate.png", "textures/items/gold_chestplate.png"});
        defaultData.add(new String[] {"textures/items/golden_helmet.png", "textures/items/gold_helmet.png"});
        defaultData.add(new String[] {"textures/items/golden_hoe.png", "textures/items/gold_hoe.png"});
        defaultData.add(new String[] {"textures/items/golden_horse_armor.png", "textures/items/gold_horse_armor.png"});
        defaultData.add(new String[] {"textures/items/golden_leggings.png", "textures/items/gold_leggings.png"});
        defaultData.add(new String[] {"textures/items/golden_pickaxe.png", "textures/items/gold_pickaxe.png"});
        defaultData.add(new String[] {"textures/items/golden_shovel.png", "textures/items/gold_shovel.png"});
        defaultData.add(new String[] {"textures/items/golden_sword.png", "textures/items/gold_sword.png"});
        defaultData.add(new String[] {"textures/items/wooden_axe.png", "textures/items/wood_axe.png"});
        defaultData.add(new String[] {"textures/items/wooden_hoe.png", "textures/items/wood_hoe.png"});
        defaultData.add(new String[] {"textures/items/wooden_pickaxe.png", "textures/items/wood_pickaxe.png"});
        defaultData.add(new String[] {"textures/items/wooden_shovel.png", "textures/items/wood_shovel.png"});
        defaultData.add(new String[] {"textures/items/wooden_sword.png", "textures/items/wood_sword.png"});
        defaultData.add(new String[] {"textures/models/armor/chainmail_layer_1.png", "textures/models/armor/chain_1.png"});
        defaultData.add(new String[] {"textures/models/armor/chainmail_layer_2.png", "textures/models/armor/chain_2.png"});
        defaultData.add(new String[] {"textures/models/armor/diamond_layer_1.png", "textures/models/armor/diamond_1.png"});
        defaultData.add(new String[] {"textures/models/armor/diamond_layer_2.png", "textures/models/armor/diamond_2.png"});
        defaultData.add(new String[] {"textures/models/armor/gold_layer_1.png", "textures/models/armor/gold_1.png"});
        defaultData.add(new String[] {"textures/models/armor/gold_layer_2.png", "textures/models/armor/gold_2.png"});
        defaultData.add(new String[] {"textures/models/armor/iron_layer_1.png", "textures/models/armor/iron_1.png"});
        defaultData.add(new String[] {"textures/models/armor/iron_layer_2.png", "textures/models/armor/iron_2.png"});
        defaultData.add(new String[] {"textures/models/armor/leather_layer_1.png", "textures/models/armor/leather_1.png"});
        defaultData.add(new String[] {"textures/models/armor/leather_layer_1_overlay.png", "textures/models/armor/leather_1_overlay.png"});
        defaultData.add(new String[] {"textures/models/armor/leather_layer_2.png", "textures/models/armor/leather_2.png"});
        defaultData.add(new String[] {"textures/models/armor/leather_layer_2_overlay.png", "textures/models/armor/leather_2_overlay.png"});
        defaultData.add(new String[] {"textures/models/armor/netherite_layer_1.png", "textures/models/armor/netherite_1.png"});
        defaultData.add(new String[] {"textures/models/armor/netherite_layer_2.png", "textures/models/armor/netherite_2.png"});
        defaultData.add(new String[] {"textures/models/armor/turtle_layer_1.png", "textures/models/armor/turtle_1.png"});

        // Arrow
        defaultData.add(new String[] {"textures/entity/arrow.png", "textures/entity/arrows.png"});

        // Bamboo
        defaultData.add(new String[] {"textures/blocks/bamboo_large_leaves.png", "textures/blocks/bamboo_leaf.png"});
        defaultData.add(new String[] {"textures/blocks/bamboo_small_leaves.png", "textures/blocks/bamboo_small_leaf.png"});
        defaultData.add(new String[] {"textures/blocks/bamboo_stage0.png", "textures/blocks/bamboo_sapling.png"});
        defaultData.add(new String[] {"textures/blocks/bamboo_stalk.png", "textures/blocks/bamboo_stem.png"});

        // Barrier
        defaultData.add(new String[] {"textures/items/barrier.png", "textures/blocks/barrier.png"});

        // Bear
        defaultData.add(new String[] {"textures/entity/bear/polarbear.png", "textures/entity/polarbear.png"});

        // Bed
        defaultData.add(new String[] {"textures/entity/bed/light_gray.png", "textures/entity/bed/silver.png"});

        // Bee
        defaultData.add(new String[] {"textures/blocks/beehive_end.png", "textures/blocks/beehive_top.png"});

        // Beetroot
        defaultData.add(new String[] {"textures/blocks/beetroots_stage0.png", "textures/blocks/beetroots_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/beetroots_stage1.png", "textures/blocks/beetroots_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/beetroots_stage2.png", "textures/blocks/beetroots_stage_2.png"});
        defaultData.add(new String[] {"textures/blocks/beetroots_stage3.png", "textures/blocks/beetroots_stage_3.png"});

        // Bell
        defaultData.add(new String[] {"textures/entity/bell/bell_body.png", "textures/entity/bell/bell.png"});
        defaultData.add(new String[] {"textures/items/bell.png", "textures/items/villagebell.png"});

        // Boat
        defaultData.add(new String[] {"textures/entity/boat/acacia.png", "textures/entity/boat/boat_acacia.png"});
        defaultData.add(new String[] {"textures/entity/boat/birch.png", "textures/entity/boat/boat_birch.png"});
        defaultData.add(new String[] {"textures/entity/boat/dark_oak.png", "textures/entity/boat/boat_darkoak.png"});
        defaultData.add(new String[] {"textures/entity/boat/jungle.png", "textures/entity/boat/boat_jungle.png"});
        defaultData.add(new String[] {"textures/entity/boat/oak.png", "textures/entity/boat/boat_oak.png"});
        defaultData.add(new String[] {"textures/entity/boat/spruce.png", "textures/entity/boat/boat_spruce.png"});
        defaultData.add(new String[] {"textures/items/acacia_boat.png", "textures/items/boat_acacia.png"});
        defaultData.add(new String[] {"textures/items/birch_boat.png", "textures/items/boat_birch.png"});
        defaultData.add(new String[] {"textures/items/dark_oak_boat.png", "textures/items/boat_darkoak.png"});
        defaultData.add(new String[] {"textures/items/jungle_boat.png", "textures/items/boat_jungle.png"});
        defaultData.add(new String[] {"textures/items/oak_boat.png", "textures/items/boat_oak.png"});
        defaultData.add(new String[] {"textures/items/spruce_boat.png", "textures/items/boat_spruce.png"});

        // Bone
        defaultData.add(new String[] {"textures/items/bone_meal.png", "textures/items/dye_powder_white.png"});

        // Book
        defaultData.add(new String[] {"textures/items/book.png", "textures/items/book_normal.png"});
        defaultData.add(new String[] {"textures/items/enchanted_book.png", "textures/items/book_enchanted.png"});
        defaultData.add(new String[] {"textures/items/knowledge_book.png", "textures/items/book_knowledge.png"});
        defaultData.add(new String[] {"textures/items/writable_book.png", "textures/items/book_writable.png"});
        defaultData.add(new String[] {"textures/items/written_book.png", "textures/items/book_written.png"});

        // Bow
        defaultData.add(new String[] {"textures/items/bow.png", "textures/items/bow_standby.png"});

        // Brick
        defaultData.add(new String[] {"textures/blocks/bricks.png", "textures/blocks/brick.png"});

        // Bucket
        defaultData.add(new String[] {"textures/items/bucket.png", "textures/items/bucket_empty.png"});
        defaultData.add(new String[] {"textures/items/cod_bucket.png", "textures/items/bucket_cod.png"});
        defaultData.add(new String[] {"textures/items/lava_bucket.png", "textures/items/bucket_lava.png"});
        defaultData.add(new String[] {"textures/items/milk_bucket.png", "textures/items/bucket_milk.png"});
        defaultData.add(new String[] {"textures/items/pufferfish_bucket.png", "textures/items/bucket_pufferfish.png"});
        defaultData.add(new String[] {"textures/items/salmon_bucket.png", "textures/items/bucket_salmon.png"});
        defaultData.add(new String[] {"textures/items/tropical_fish_bucket.png", "textures/items/bucket_tropical.png"});
        defaultData.add(new String[] {"textures/items/water_bucket.png", "textures/items/bucket_water.png"});

        // Campfire
        defaultData.add(new String[] {"textures/blocks/campfire_fire.png", "textures/blocks/campfire.png"});
        defaultData.add(new String[] {"textures/blocks/soul_campfire_fire.png", "textures/blocks/soul_campfire.png"});

        // Carrot
        defaultData.add(new String[] {"textures/blocks/carrots_stage0.png", "textures/blocks/carrots_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/carrots_stage1.png", "textures/blocks/carrots_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/carrots_stage2.png", "textures/blocks/carrots_stage_2.png"});
        defaultData.add(new String[] {"textures/blocks/carrots_stage3.png", "textures/blocks/carrots_stage_3.png"});
        defaultData.add(new String[] {"textures/items/golden_carrot.png", "textures/items/carrot_golden.png"});

        // Cat
        defaultData.add(new String[] {"textures/entity/cat/all_black.png", "textures/entity/cat/allblackcat.png"});
        defaultData.add(new String[] {"textures/entity/cat/black.png", "textures/entity/cat/tuxedo.png"});
        defaultData.add(new String[] {"textures/entity/cat/british_shorthair.png", "textures/entity/cat/britishshorthair.png"});
        defaultData.add(new String[] {"textures/entity/cat/cat_collar.png", "textures/entity/cat/graytabby_tame.png"});
        defaultData.add(new String[] {"textures/entity/cat/red.png", "textures/entity/cat/redtabby.png"});
        defaultData.add(new String[] {"textures/entity/cat/siamese.png", "textures/entity/cat/siamesecat.png"});

        // Chest
        defaultData.add(new String[] {"textures/entity/chest/normal_double.png", "textures/entity/chest/double_normal.png"});

        // Chorus fruit
        defaultData.add(new String[] {"textures/items/popped_chorus_fruit.png", "textures/items/chorus_fruit_popped.png"});

        // Cobblestone
        defaultData.add(new String[] {"textures/blocks/mossy_cobblestone.png", "textures/blocks/cobblestone_mossy.png"});

        // Cobweb
        defaultData.add(new String[] {"textures/blocks/cobweb.png", "textures/blocks/web.png"});

        // Cocoa
        defaultData.add(new String[] {"textures/blocks/cocoa_stage0.png", "textures/blocks/cocoa_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/cocoa_stage1.png", "textures/blocks/cocoa_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/cocoa_stage2.png", "textures/blocks/cocoa_stage_2.png"});
        defaultData.add(new String[] {"textures/items/cocoa_beans.png", "textures/items/dye_powder_brown.png"});

        // Comparator
        defaultData.add(new String[] {"textures/blocks/comparator.png", "textures/blocks/comparator_off.png"});

        // Composter
        defaultData.add(new String[] {"textures/blocks/composter_compost.png", "textures/blocks/compost.png"});
        defaultData.add(new String[] {"textures/blocks/composter_ready.png", "textures/blocks/compost_ready.png"});

        // Concrete
        defaultData.add(new String[] {"textures/blocks/black_concrete.png", "textures/blocks/concrete_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_concrete.png", "textures/blocks/concrete_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_concrete.png", "textures/blocks/concrete_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_concrete.png", "textures/blocks/concrete_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_concrete.png", "textures/blocks/concrete_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_concrete.png", "textures/blocks/concrete_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_concrete.png", "textures/blocks/concrete_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_concrete.png", "textures/blocks/concrete_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_concrete.png", "textures/blocks/concrete_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_concrete.png", "textures/blocks/concrete_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_concrete.png", "textures/blocks/concrete_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_concrete.png", "textures/blocks/concrete_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_concrete.png", "textures/blocks/concrete_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_concrete.png", "textures/blocks/concrete_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_concrete.png", "textures/blocks/concrete_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_concrete.png", "textures/blocks/concrete_yellow.png"});

        // Concrete powder
        defaultData.add(new String[] {"textures/blocks/black_concrete_powder.png", "textures/blocks/concrete_powder_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_concrete_powder.png", "textures/blocks/concrete_powder_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_concrete_powder.png", "textures/blocks/concrete_powder_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_concrete_powder.png", "textures/blocks/concrete_powder_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_concrete_powder.png", "textures/blocks/concrete_powder_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_concrete_powder.png", "textures/blocks/concrete_powder_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_concrete_powder.png", "textures/blocks/concrete_powder_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_concrete_powder.png", "textures/blocks/concrete_powder_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_concrete_powder.png", "textures/blocks/concrete_powder_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_concrete_powder.png", "textures/blocks/concrete_powder_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_concrete_powder.png", "textures/blocks/concrete_powder_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_concrete_powder.png", "textures/blocks/concrete_powder_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_concrete_powder.png", "textures/blocks/concrete_powder_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_concrete_powder.png", "textures/blocks/concrete_powder_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_concrete_powder.png", "textures/blocks/concrete_powder_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_concrete_powder.png", "textures/blocks/concrete_powder_yellow.png"});

        // Conduit
        defaultData.add(new String[] {"textures/entity/conduit/base.png", "textures/blocks/conduit_base.png"});
        defaultData.add(new String[] {"textures/entity/conduit/cage.png", "textures/blocks/conduit_cage.png"});
        defaultData.add(new String[] {"textures/entity/conduit/closed_eye.png", "textures/blocks/conduit_closed.png"});
        defaultData.add(new String[] {"textures/entity/conduit/open_eye.png", "textures/blocks/conduit_open.png"});
        defaultData.add(new String[] {"textures/entity/conduit/wind.png", "textures/blocks/conduit_wind_horizontal.png"});
        defaultData.add(new String[] {"textures/entity/conduit/wind_vertical.png", "textures/blocks/conduit_wind_vertical.png"});
        defaultData.add(new String[] {"textures/items/heart_of_the_sea.png", "textures/items/heartofthesea_closed.png"});

        // Coral
        defaultData.add(new String[] {"textures/blocks/brain_coral.png", "textures/blocks/coral_plant_pink.png"});
        defaultData.add(new String[] {"textures/blocks/bubble_coral.png", "textures/blocks/coral_plant_purple.png"});
        defaultData.add(new String[] {"textures/blocks/fire_coral.png", "textures/blocks/coral_plant_red.png"});
        defaultData.add(new String[] {"textures/blocks/horn_coral.png", "textures/blocks/coral_plant_yellow.png"});
        defaultData.add(new String[] {"textures/blocks/tube_coral.png", "textures/blocks/coral_plant_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brain_coral_block.png", "textures/blocks/coral_pink.png"});
        defaultData.add(new String[] {"textures/blocks/bubble_coral_block.png", "textures/blocks/coral_purple.png"});
        defaultData.add(new String[] {"textures/blocks/fire_coral_block.png", "textures/blocks/coral_red.png"});
        defaultData.add(new String[] {"textures/blocks/horn_coral_block.png", "textures/blocks/coral_yellow.png"});
        defaultData.add(new String[] {"textures/blocks/tube_coral_block.png", "textures/blocks/coral_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brain_coral_fan.png", "textures/blocks/coral_fan_pink.png"});
        defaultData.add(new String[] {"textures/blocks/bubble_coral_fan.png", "textures/blocks/coral_fan_purple.png"});
        defaultData.add(new String[] {"textures/blocks/fire_coral_fan.png", "textures/blocks/coral_fan_red.png"});
        defaultData.add(new String[] {"textures/blocks/horn_coral_fan.png", "textures/blocks/coral_fan_yellow.png"});
        defaultData.add(new String[] {"textures/blocks/tube_coral_fan.png", "textures/blocks/coral_fan_blue.png"});
        defaultData.add(new String[] {"textures/blocks/dead_brain_coral_block.png", "textures/blocks/coral_pink_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_bubble_coral_block.png", "textures/blocks/coral_purple_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_fire_coral_block.png", "textures/blocks/coral_red_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_horn_coral_block.png", "textures/blocks/coral_yellow_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_tube_coral_block.png", "textures/blocks/coral_blue_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_brain_coral_fan.png", "textures/blocks/coral_fan_pink_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_bubble_coral_fan.png", "textures/blocks/coral_fan_purple_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_fire_coral_fan.png", "textures/blocks/coral_fan_red_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_horn_coral_fan.png", "textures/blocks/coral_fan_yellow_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_tube_coral_fan.png", "textures/blocks/coral_fan_blue_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_brain_coral.png", "textures/blocks/coral_plant_pink_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_bubble_coral.png", "textures/blocks/coral_plant_purple_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_fire_coral.png", "textures/blocks/coral_plant_red_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_horn_coral.png", "textures/blocks/coral_plant_yellow_dead.png"});
        defaultData.add(new String[] {"textures/blocks/dead_tube_coral.png", "textures/blocks/coral_plant_blue_dead.png"});

        // Cow
        defaultData.add(new String[] {"textures/entity/cow/red_mooshroom.png", "textures/entity/cow/mooshroom.png"});

        // Crimson
        defaultData.add(new String[] {"textures/blocks/crimson_nylium.png", "textures/blocks/crimson_nylium_top.png"});
        defaultData.add(new String[] {"textures/blocks/crimson_planks.png", "textures/blocks/huge_fungus/crimson_planks.png"});
        defaultData.add(new String[] {"textures/blocks/crimson_stem.png", "textures/blocks/huge_fungus/crimson_log_side.png"});
        defaultData.add(new String[] {"textures/blocks/crimson_stem_top.png", "textures/blocks/huge_fungus/crimson_log_top.png"});
        defaultData.add(new String[] {"textures/blocks/stripped_crimson_stem.png", "textures/blocks/huge_fungus/stripped_crimson_stem_side.png"});
        defaultData.add(new String[] {"textures/blocks/stripped_crimson_stem_top.png", "textures/blocks/huge_fungus/stripped_crimson_stem_top.png"});

        // Dead bush
        defaultData.add(new String[] {"textures/blocks/dead_bush.png", "textures/blocks/deadbush.png"});

        // Destroy stage
        defaultData.add(new String[] {"textures/blocks/destroy_stage_0.png", "textures/environment/destroy_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_1.png", "textures/environment/destroy_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_2.png", "textures/environment/destroy_stage_2.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_3.png", "textures/environment/destroy_stage_3.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_4.png", "textures/environment/destroy_stage_4.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_5.png", "textures/environment/destroy_stage_5.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_6.png", "textures/environment/destroy_stage_6.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_7.png", "textures/environment/destroy_stage_7.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_8.png", "textures/environment/destroy_stage_8.png"});
        defaultData.add(new String[] {"textures/blocks/destroy_stage_9.png", "textures/environment/destroy_stage_9.png"});

        // Diorite
        defaultData.add(new String[] {"textures/blocks/diorite.png", "textures/blocks/stone_diorite.png"});
        defaultData.add(new String[] {"textures/blocks/polished_diorite.png", "textures/blocks/stone_diorite_smooth.png"});

        // Dispenser
        defaultData.add(new String[] {"textures/blocks/dispenser_front.png", "textures/blocks/dispenser_front_horizontal.png"});

        // Door
        defaultData.add(new String[] {"textures/blocks/acacia_door_bottom.png", "textures/blocks/door_acacia_lower.png"});
        defaultData.add(new String[] {"textures/blocks/birch_door_bottom.png", "textures/blocks/door_birch_lower.png"});
        defaultData.add(new String[] {"textures/blocks/crimson_door_bottom.png", "textures/blocks/huge_fungus/crimson_door_lower.png"});
        defaultData.add(new String[] {"textures/blocks/crimson_door_top.png", "textures/blocks/huge_fungus/crimson_door_top.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_door_bottom.png", "textures/blocks/door_dark_oak_lower.png"});
        defaultData.add(new String[] {"textures/blocks/iron_door_bottom.png", "textures/blocks/door_iron_lower.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_door_bottom.png", "textures/blocks/door_jungle_lower.png"});
        defaultData.add(new String[] {"textures/blocks/oak_door_bottom.png", "textures/blocks/door_wood_lower.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_door_bottom.png", "textures/blocks/door_spruce_lower.png"});
        defaultData.add(new String[] {"textures/blocks/acacia_door_top.png", "textures/blocks/door_acacia_upper.png"});
        defaultData.add(new String[] {"textures/blocks/birch_door_top.png", "textures/blocks/door_birch_upper.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_door_top.png", "textures/blocks/door_dark_oak_upper.png"});
        defaultData.add(new String[] {"textures/blocks/iron_door_top.png", "textures/blocks/door_iron_upper.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_door_top.png", "textures/blocks/door_jungle_upper.png"});
        defaultData.add(new String[] {"textures/blocks/oak_door_top.png", "textures/blocks/door_wood_upper.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_door_top.png", "textures/blocks/door_spruce_upper.png"});
        defaultData.add(new String[] {"textures/blocks/warped_door_bottom.png", "textures/blocks/huge_fungus/warped_door_lower.png"});
        defaultData.add(new String[] {"textures/blocks/warped_door_top.png", "textures/blocks/huge_fungus/warped_door_top.png"});
        defaultData.add(new String[] {"textures/items/acacia_door.png", "textures/items/door_acacia.png"});
        defaultData.add(new String[] {"textures/items/birch_door.png", "textures/items/door_birch.png"});
        defaultData.add(new String[] {"textures/items/dark_oak_door.png", "textures/items/door_dark_oak.png"});
        defaultData.add(new String[] {"textures/items/iron_door.png", "textures/items/door_iron.png"});
        defaultData.add(new String[] {"textures/items/jungle_door.png", "textures/items/door_jungle.png"});
        defaultData.add(new String[] {"textures/items/oak_door.png", "textures/items/door_wood.png"});
        defaultData.add(new String[] {"textures/items/spruce_door.png", "textures/items/door_spruce.png"});

        // Dragon
        defaultData.add(new String[] {"textures/entity/enderdragon/", "textures/entity/dragon/"});
        defaultData.add(new String[] {"textures/entity/dragon/dragon_fireball.png", "textures/items/dragon_fireball.png"});
        defaultData.add(new String[] {"textures/items/dragon_breath.png", "textures/items/dragons_breath.png"});
        defaultData.add(new String[] {"textures/items/fire_charge.png", "textures/items/fireball.png"});

        // Dropper
        defaultData.add(new String[] {"textures/blocks/dropper_front.png", "textures/blocks/dropper_front_horizontal.png"});

        // Dye
        defaultData.add(new String[] {"textures/items/cactus_green.png", "textures/items/dye_powder_green.png"}); // 1.13
        defaultData.add(new String[] {"textures/items/dandelion_yellow.png", "textures/items/dye_powder_yellow.png"}); // 1.13
        defaultData.add(new String[] {"textures/items/rose_red.png", "textures/items/dye_powder_red.png"}); // 1.13
        defaultData.add(new String[] {"textures/items/black_dye.png", "textures/items/dye_powder_black_new.png"});
        defaultData.add(new String[] {"textures/items/blue_dye.png", "textures/items/dye_powder_blue_new.png"});
        defaultData.add(new String[] {"textures/items/brown_dye.png", "textures/items/dye_powder_brown_new.png"});
        defaultData.add(new String[] {"textures/items/cyan_dye.png", "textures/items/dye_powder_cyan.png"});
        defaultData.add(new String[] {"textures/items/gray_dye.png", "textures/items/dye_powder_gray.png"});
        defaultData.add(new String[] {"textures/items/green_dye.png", "textures/items/dye_powder_green.png"});
        defaultData.add(new String[] {"textures/items/light_blue_dye.png", "textures/items/dye_powder_light_blue.png"});
        defaultData.add(new String[] {"textures/items/light_gray_dye.png", "textures/items/dye_powder_silver.png"});
        defaultData.add(new String[] {"textures/items/lime_dye.png", "textures/items/dye_powder_lime.png"});
        defaultData.add(new String[] {"textures/items/magenta_dye.png", "textures/items/dye_powder_magenta.png"});
        defaultData.add(new String[] {"textures/items/orange_dye.png", "textures/items/dye_powder_orange.png"});
        defaultData.add(new String[] {"textures/items/pink_dye.png", "textures/items/dye_powder_pink.png"});
        defaultData.add(new String[] {"textures/items/purple_dye.png", "textures/items/dye_powder_purple.png"});
        defaultData.add(new String[] {"textures/items/red_dye.png", "textures/items/dye_powder_red.png"});
        defaultData.add(new String[] {"textures/items/white_dye.png", "textures/items/dye_powder_white_new.png"});
        defaultData.add(new String[] {"textures/items/yellow_dye.png", "textures/items/dye_powder_yellow.png"});

        // End crystal
        defaultData.add(new String[] {"textures/entity/end_crystal/", "textures/entity/endercrystal/"});
        defaultData.add(new String[] {"textures/entity/endercrystal/end_crystal.png", "textures/entity/endercrystal/endercrystal.png"});
        defaultData.add(new String[] {"textures/entity/endercrystal/end_crystal_beam.png", "textures/entity/endercrystal/endercrystal_beam.png"});

        // End portal
        defaultData.add(new String[] {"textures/blocks/end_portal_frame_eye.png", "textures/blocks/endframe_eye.png"});
        defaultData.add(new String[] {"textures/blocks/end_portal_frame_side.png", "textures/blocks/endframe_side.png"});
        defaultData.add(new String[] {"textures/blocks/end_portal_frame_top.png", "textures/blocks/endframe_top.png"});

        // End stone
        defaultData.add(new String[] {"textures/blocks/end_stone_bricks.png", "textures/blocks/end_bricks.png"});

        // Farmland
        defaultData.add(new String[] {"textures/blocks/farmland.png", "textures/blocks/farmland_dry.png"});
        defaultData.add(new String[] {"textures/blocks/farmland_moist.png", "textures/blocks/farmland_wet.png"});

        // Fern
        defaultData.add(new String[] {"textures/blocks/large_fern_bottom.png", "textures/blocks/double_plant_fern_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/large_fern_top.png", "textures/blocks/double_plant_fern_top.png"});

        // Firework
        defaultData.add(new String[] {"textures/items/firework_rocket.png", "textures/items/fireworks.png"});
        defaultData.add(new String[] {"textures/items/firework_star_overlay.png", "textures/items/fireworks_charge.png"});

        // Fish
        defaultData.add(new String[] {"textures/entity/fishing_hook.png", "textures/entity/fishhook.png"});
        defaultData.add(new String[] {"textures/items/cod.png", "textures/items/fish_raw.png"});
        defaultData.add(new String[] {"textures/items/cooked_cod.png", "textures/items/fish_cooked.png"});
        defaultData.add(new String[] {"textures/items/cooked_salmon.png", "textures/items/fish_salmon_cooked.png"});
        defaultData.add(new String[] {"textures/items/fishing_rod.png", "textures/items/fishing_rod_uncast.png"});
        defaultData.add(new String[] {"textures/items/pufferfish.png", "textures/items/fish_pufferfish_raw.png"});
        defaultData.add(new String[] {"textures/items/salmon.png", "textures/items/fish_salmon_raw.png"});
        defaultData.add(new String[] {"textures/items/tropical_fish.png", "textures/items/fish_clownfish_raw.png"});

        // Flesh
        defaultData.add(new String[] {"textures/items/beef.png", "textures/items/beef_raw.png"});
        defaultData.add(new String[] {"textures/items/chicken.png", "textures/items/chicken_raw.png"});
        defaultData.add(new String[] {"textures/items/cooked_beef.png", "textures/items/beef_cooked.png"});
        defaultData.add(new String[] {"textures/items/cooked_chicken.png", "textures/items/chicken_cooked.png"});
        defaultData.add(new String[] {"textures/items/cooked_mutton.png", "textures/items/mutton_cooked.png"});
        defaultData.add(new String[] {"textures/items/cooked_porkchop.png", "textures/items/porkchop_cooked.png"});
        defaultData.add(new String[] {"textures/items/mutton.png", "textures/items/mutton_raw.png"});
        defaultData.add(new String[] {"textures/items/porkchop.png", "textures/items/porkchop_raw.png"});

        // Fletching table
        defaultData.add(new String[] {"textures/blocks/fletching_table_front.png", "textures/blocks/fletcher_table_side2.png"});
        defaultData.add(new String[] {"textures/blocks/fletching_table_side.png", "textures/blocks/fletcher_table_side1.png"});
        defaultData.add(new String[] {"textures/blocks/fletching_table_top.png", "textures/blocks/fletcher_table_top.png"});

        // Flower
        defaultData.add(new String[] {"textures/blocks/allium.png", "textures/blocks/flower_allium.png"});
        defaultData.add(new String[] {"textures/blocks/azure_bluet.png", "textures/blocks/flower_houstonia.png"});
        defaultData.add(new String[] {"textures/blocks/blue_orchid.png", "textures/blocks/flower_blue_orchid.png"});
        defaultData.add(new String[] {"textures/blocks/cornflower.png", "textures/blocks/flower_cornflower.png"});
        defaultData.add(new String[] {"textures/blocks/dandelion.png", "textures/blocks/flower_dandelion.png"});
        defaultData.add(new String[] {"textures/blocks/lilac_bottom.png", "textures/blocks/double_plant_syringa_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/lilac_top.png", "textures/blocks/double_plant_syringa_top.png"});
        defaultData.add(new String[] {"textures/blocks/lily_of_the_valley.png", "textures/blocks/flower_lily_of_the_valley.png"});
        defaultData.add(new String[] {"textures/blocks/orange_tulip.png", "textures/blocks/flower_tulip_orange.png"});
        defaultData.add(new String[] {"textures/blocks/poppy.png", "textures/blocks/flower_rose.png"});
        defaultData.add(new String[] {"textures/blocks/oxeye_daisy.png", "textures/blocks/flower_oxeye_daisy.png"});
        defaultData.add(new String[] {"textures/blocks/peony_bottom.png", "textures/blocks/double_plant_paeonia_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/peony_top.png", "textures/blocks/double_plant_paeonia_top.png"});
        defaultData.add(new String[] {"textures/blocks/pink_tulip.png", "textures/blocks/flower_tulip_pink.png"});
        defaultData.add(new String[] {"textures/blocks/red_tulip.png", "textures/blocks/flower_tulip_red.png"});
        defaultData.add(new String[] {"textures/blocks/rose_bush_bottom.png", "textures/blocks/double_plant_rose_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/rose_bush_top.png", "textures/blocks/double_plant_rose_top.png"});
        defaultData.add(new String[] {"textures/blocks/sunflower_back.png", "textures/blocks/double_plant_sunflower_back.png"});
        defaultData.add(new String[] {"textures/blocks/sunflower_bottom.png", "textures/blocks/double_plant_sunflower_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/sunflower_front.png", "textures/blocks/double_plant_sunflower_front.png"});
        defaultData.add(new String[] {"textures/blocks/sunflower_top.png", "textures/blocks/double_plant_sunflower_top.png"});
        defaultData.add(new String[] {"textures/blocks/white_tulip.png", "textures/blocks/flower_tulip_white.png"});
        defaultData.add(new String[] {"textures/blocks/wither_rose.png", "textures/blocks/flower_wither_rose.png"});

        // Fox
        defaultData.add(new String[] {"textures/entity/fox/snow_fox.png", "textures/entity/fox/arctic_fox.png"});
        defaultData.add(new String[] {"textures/entity/fox/snow_fox_sleep.png", "textures/entity/fox/arctic_fox_sleep.png"});

        // Furnace
        defaultData.add(new String[] {"textures/blocks/blast_furnace_front.png", "textures/blocks/blast_furnace_front_off.png"});
        defaultData.add(new String[] {"textures/blocks/furnace_front.png", "textures/blocks/furnace_front_off.png"});
        defaultData.add(new String[] {"textures/blocks/smoker_front.png", "textures/blocks/smoker_front_off.png"});

        // Glass
        defaultData.add(new String[] {"textures/blocks/black_stained_glass.png", "textures/blocks/glass_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_stained_glass.png", "textures/blocks/glass_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_stained_glass.png", "textures/blocks/glass_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_stained_glass.png", "textures/blocks/glass_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_stained_glass.png", "textures/blocks/glass_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_stained_glass.png", "textures/blocks/glass_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_stained_glass.png", "textures/blocks/glass_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_stained_glass.png", "textures/blocks/glass_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_stained_glass.png", "textures/blocks/glass_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_stained_glass.png", "textures/blocks/glass_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_stained_glass.png", "textures/blocks/glass_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_stained_glass.png", "textures/blocks/glass_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_stained_glass.png", "textures/blocks/glass_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_stained_glass.png", "textures/blocks/glass_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_stained_glass.png", "textures/blocks/glass_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_stained_glass.png", "textures/blocks/glass_yellow.png"});

        // Glass pane
        defaultData.add(new String[] {"textures/blocks/black_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_stained_glass_pane_top.png", "textures/blocks/glass_pane_top_yellow.png"});

        // Glazed terracotta
        defaultData.add(new String[] {"textures/blocks/black_glazed_terracotta.png", "textures/blocks/glazed_terracotta_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_glazed_terracotta.png", "textures/blocks/glazed_terracotta_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_glazed_terracotta.png", "textures/blocks/glazed_terracotta_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_glazed_terracotta.png", "textures/blocks/glazed_terracotta_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_glazed_terracotta.png", "textures/blocks/glazed_terracotta_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_glazed_terracotta.png", "textures/blocks/glazed_terracotta_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_glazed_terracotta.png", "textures/blocks/glazed_terracotta_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_glazed_terracotta.png", "textures/blocks/glazed_terracotta_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_glazed_terracotta.png", "textures/blocks/glazed_terracotta_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_glazed_terracotta.png", "textures/blocks/glazed_terracotta_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_glazed_terracotta.png", "textures/blocks/glazed_terracotta_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_glazed_terracotta.png", "textures/blocks/glazed_terracotta_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_glazed_terracotta.png", "textures/blocks/glazed_terracotta_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_glazed_terracotta.png", "textures/blocks/glazed_terracotta_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_glazed_terracotta.png", "textures/blocks/glazed_terracotta_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_glazed_terracotta.png", "textures/blocks/glazed_terracotta_yellow.png"});

        // Granite
        defaultData.add(new String[] {"textures/blocks/granite.png", "textures/blocks/stone_granite.png"});
        defaultData.add(new String[] {"textures/blocks/polished_granite.png", "textures/blocks/stone_granite_smooth.png"});

        // Grass
        defaultData.add(new String[] {"textures/blocks/grass.png", "textures/blocks/tallgrass.png"});
        defaultData.add(new String[] {"textures/blocks/grass_block_side.png", "textures/blocks/grass_side_carried.png"});
        defaultData.add(new String[] {"textures/blocks/grass_block_side_overlay.png", "textures/blocks/grass_side.png"});
        defaultData.add(new String[] {"textures/blocks/grass_block_snow.png", "textures/blocks/grass_side_snowed.png"});
        defaultData.add(new String[] {"textures/blocks/grass_block_top.png", "textures/blocks/grass_top.png"});
        defaultData.add(new String[] {"textures/blocks/tall_grass_bottom.png", "textures/blocks/double_plant_grass_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/tall_grass_top.png", "textures/blocks/double_plant_grass_top.png"});

        // Hoglin
        defaultData.add(new String[] {"textures/entity/hoglin/zoglin.png", "textures/entity/zoglin/zoglin.png"});

        // Honey
        defaultData.add(new String[] {"textures/blocks/honey_block_bottom.png", "textures/blocks/honey_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/honey_block_side.png", "textures/blocks/honey_side.png"});
        defaultData.add(new String[] {"textures/blocks/honey_block_top.png", "textures/blocks/honey_top.png"});
        defaultData.add(new String[] {"textures/blocks/honeycomb_block.png", "textures/blocks/honeycomb.png"});

        // Horse
        defaultData.add(new String[] {"textures/entity/horse/", "textures/entity/horse2/"});

        // Ice
        defaultData.add(new String[] {"textures/blocks/packed_ice.png", "textures/blocks/ice_packed.png"});

        // Illager & pillager
        defaultData.add(new String[] {"textures/entity/illager/evoker_fangs.png", "textures/entity/illager/fangs.png"});
        defaultData.add(new String[] {"textures/entity/illager/pillager.png", "textures/entity/pillager.png"});
        defaultData.add(new String[] {"textures/entity/illager/vex.png", "textures/entity/vex/vex.png"});
        defaultData.add(new String[] {"textures/entity/illager/vex_charging.png", "textures/entity/vex/vex_charging.png"});
        defaultData.add(new String[] {"textures/entity/illager/vindicator.png", "textures/entity/vindicator.png"});

        // Ink sac
        defaultData.add(new String[] {"textures/items/ink_sac.png", "textures/items/dye_powder_black.png"});

        // Item frame
        defaultData.add(new String[] {"textures/blocks/item_frame.png", "textures/blocks/itemframe_background.png"});

        // Iron golem
        defaultData.add(new String[] {"textures/entity/iron_golem/iron_golem.png", "textures/entity/iron_golem.png"});

        // Jigsaw
        defaultData.add(new String[] {"textures/blocks/jigsaw_bottom.png", "textures/blocks/jigsaw_back.png"});
        defaultData.add(new String[] {"textures/blocks/jigsaw_top.png", "textures/blocks/jigsaw_front.png"});

        // Kelp
        defaultData.add(new String[] {"textures/blocks/dried_kelp_side.png", "textures/blocks/dried_kelp_side_a.png"});
        defaultData.add(new String[] {"textures/blocks/kelp.png", "textures/blocks/kelp_top.png"});
        defaultData.add(new String[] {"textures/blocks/kelp_plant.png", "textures/blocks/kelp_a.png"});

        // Lapis lazuli
        defaultData.add(new String[] {"textures/items/lapis_lazuli.png", "textures/items/dye_powder_blue.png"});

        // Leaves
        defaultData.add(new String[] {"textures/blocks/acacia_leaves.png", "textures/blocks/leaves_acacia.png"});
        defaultData.add(new String[] {"textures/blocks/birch_leaves.png", "textures/blocks/leaves_birch.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_leaves.png", "textures/blocks/leaves_big_oak.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_leaves.png", "textures/blocks/leaves_jungle.png"});
        defaultData.add(new String[] {"textures/blocks/oak_leaves.png", "textures/blocks/leaves_oak.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_leaves.png", "textures/blocks/leaves_spruce.png"});

        // Lily Pad
        defaultData.add(new String[] {"textures/blocks/lily_pad.png", "textures/blocks/waterlily.png"});

        // Llama
        defaultData.add(new String[] {"textures/entity/llama/brown.png", "textures/entity/llama/llama_brown.png"});
        defaultData.add(new String[] {"textures/entity/llama/creamy.png", "textures/entity/llama/llama_creamy.png"});
        defaultData.add(new String[] {"textures/entity/llama/gray.png", "textures/entity/llama/llama_gray.png"});
        defaultData.add(new String[] {"textures/entity/llama/white.png", "textures/entity/llama/llama_white.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/black.png", "textures/entity/llama/decor/decor_black.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/blue.png", "textures/entity/llama/decor/decor_blue.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/brown.png", "textures/entity/llama/decor/decor_brown.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/cyan.png", "textures/entity/llama/decor/decor_cyan.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/gray.png", "textures/entity/llama/decor/decor_gray.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/green.png", "textures/entity/llama/decor/decor_green.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/light_blue.png", "textures/entity/llama/decor/decor_light_blue.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/light_gray.png", "textures/entity/llama/decor/decor_silver.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/lime.png", "textures/entity/llama/decor/decor_lime.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/magenta.png", "textures/entity/llama/decor/decor_magenta.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/orange.png", "textures/entity/llama/decor/decor_orange.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/pink.png", "textures/entity/llama/decor/decor_pink.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/purple.png", "textures/entity/llama/decor/decor_purple.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/red.png", "textures/entity/llama/decor/decor_red.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/trader_llama.png", "textures/entity/llama/decor/trader_llama_decor.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/white.png", "textures/entity/llama/decor/decor_white.png"});
        defaultData.add(new String[] {"textures/entity/llama/decor/yellow.png", "textures/entity/llama/decor/decor_yellow.png"});

        // Log
        defaultData.add(new String[] {"textures/blocks/acacia_log.png", "textures/blocks/log_acacia.png"});
        defaultData.add(new String[] {"textures/blocks/birch_log.png", "textures/blocks/log_birch.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_log.png", "textures/blocks/log_big_oak.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_log.png", "textures/blocks/log_jungle.png"});
        defaultData.add(new String[] {"textures/blocks/oak_log.png", "textures/blocks/log_oak.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_log.png", "textures/blocks/log_spruce.png"});

        // Log top
        defaultData.add(new String[] {"textures/blocks/acacia_log_top.png", "textures/blocks/log_acacia_top.png"});
        defaultData.add(new String[] {"textures/blocks/birch_log_top.png", "textures/blocks/log_birch_top.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_log_top.png", "textures/blocks/log_big_oak_top.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_log_top.png", "textures/blocks/log_jungle_top.png"});
        defaultData.add(new String[] {"textures/blocks/oak_log_top.png", "textures/blocks/log_oak_top.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_log_top.png", "textures/blocks/log_spruce_top.png"});

        // Map
        defaultData.add(new String[] {"textures/items/filled_map.png", "textures/items/map_filled.png"});
        defaultData.add(new String[] {"textures/items/filled_map_markings.png", "textures/items/map_filled_markings.png"});
        defaultData.add(new String[] {"textures/items/map.png", "textures/items/map_empty.png"});

        // Melon
        defaultData.add(new String[] {"textures/blocks/attached_melon_stem.png", "textures/blocks/melon_stem_connected.png"});
        defaultData.add(new String[] {"textures/blocks/melon_stem.png", "textures/blocks/melon_stem_disconnected.png"});
        defaultData.add(new String[] {"textures/items/glistering_melon_slice.png", "textures/items/melon_speckled.png"});
        defaultData.add(new String[] {"textures/items/melon_slice.png", "textures/items/melon.png"});

        // Minecart
        defaultData.add(new String[] {"textures/items/chest_minecart.png", "textures/items/minecart_chest.png"});
        defaultData.add(new String[] {"textures/items/command_block_minecart.png", "textures/items/minecart_command_block.png"});
        defaultData.add(new String[] {"textures/items/furnace_minecart.png", "textures/items/minecart_furnace.png"});
        defaultData.add(new String[] {"textures/items/hopper_minecart.png", "textures/items/minecart_hopper.png"});
        defaultData.add(new String[] {"textures/items/minecart.png", "textures/items/minecart_normal.png"});
        defaultData.add(new String[] {"textures/items/tnt_minecart.png", "textures/items/minecart_tnt.png"});

        // Mob effect
        defaultData.add(new String[] {"textures/mob_effect/absorption.png", "textures/ui/absorption_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/bad_omen.png", "textures/ui/bad_omen_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/blindness.png", "textures/ui/blindness_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/conduit_power.png", "textures/ui/conduit_power_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/dolphins_grace.png", "textures/ui/dolphins_grace_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/fire_resistance.png", "textures/ui/fire_resistance_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/glowing.png", "textures/ui/glowing_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/haste.png", "textures/ui/haste_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/health_boost.png", "textures/ui/health_boost_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/hero_of_the_village.png", "textures/ui/village_hero_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/hunger.png", "textures/ui/hunger_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/instant_damage.png", "textures/ui/instant_damage_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/instant_health.png", "textures/ui/instant_health_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/invisibility.png", "textures/ui/invisibility_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/jump_boost.png", "textures/ui/jump_boost_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/levitation.png", "textures/ui/levitation_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/luck.png", "textures/ui/luck_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/mining_fatigue.png", "textures/ui/mining_fatigue_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/nausea.png", "textures/ui/nausea_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/night_vision.png", "textures/ui/night_vision_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/poison.png", "textures/ui/poison_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/regeneration.png", "textures/ui/regeneration_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/resistance.png", "textures/ui/resistance_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/saturation.png", "textures/ui/saturation_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/slow_falling.png", "textures/ui/slow_falling_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/slowness.png", "textures/ui/slowness_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/speed.png", "textures/ui/speed_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/strength.png", "textures/ui/strength_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/unluck.png", "textures/ui/unluck_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/water_breathing.png", "textures/ui/water_breathing_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/weakness.png", "textures/ui/weakness_effect.png"});
        defaultData.add(new String[] {"textures/mob_effect/wither.png", "textures/ui/wither_effect.png"});

        // Mushroom
        defaultData.add(new String[] {"textures/blocks/brown_mushroom.png", "textures/blocks/mushroom_brown.png"});
        defaultData.add(new String[] {"textures/blocks/red_mushroom.png", "textures/blocks/mushroom_red.png"});
        defaultData.add(new String[] {"textures/blocks/brown_mushroom_block.png", "textures/blocks/mushroom_block_skin_brown.png"});
        defaultData.add(new String[] {"textures/blocks/red_mushroom_block.png", "textures/blocks/mushroom_block_skin_red.png"});
        defaultData.add(new String[] {"textures/blocks/mushroom_stem.png", "textures/blocks/mushroom_block_skin_stem.png"});

        // Music disc
        defaultData.add(new String[] {"textures/items/music_disc_11.png", "textures/items/record_11.png"});
        defaultData.add(new String[] {"textures/items/music_disc_13.png", "textures/items/record_13.png"});
        defaultData.add(new String[] {"textures/items/music_disc_blocks.png", "textures/items/record_blocks.png"});
        defaultData.add(new String[] {"textures/items/music_disc_cat.png", "textures/items/record_cat.png"});
        defaultData.add(new String[] {"textures/items/music_disc_chirp.png", "textures/items/record_chirp.png"});
        defaultData.add(new String[] {"textures/items/music_disc_far.png", "textures/items/record_far.png"});
        defaultData.add(new String[] {"textures/items/music_disc_mall.png", "textures/items/record_mall.png"});
        defaultData.add(new String[] {"textures/items/music_disc_mellohi.png", "textures/items/record_mellohi.png"});
        defaultData.add(new String[] {"textures/items/music_disc_pigstep.png", "textures/items/record_pigstep.png"});
        defaultData.add(new String[] {"textures/items/music_disc_stal.png", "textures/items/record_stal.png"});
        defaultData.add(new String[] {"textures/items/music_disc_strad.png", "textures/items/record_strad.png"});
        defaultData.add(new String[] {"textures/items/music_disc_wait.png", "textures/items/record_wait.png"});
        defaultData.add(new String[] {"textures/items/music_disc_ward.png", "textures/items/record_ward.png"});

        // Nether brick
        defaultData.add(new String[] {"textures/blocks/nether_bricks.png", "textures/blocks/nether_brick.png"});
        defaultData.add(new String[] {"textures/blocks/red_nether_bricks.png", "textures/blocks/red_nether_brick.png"});
        defaultData.add(new String[] {"textures/items/nether_brick.png", "textures/items/netherbrick.png"});

        // Nether portal
        defaultData.add(new String[] {"textures/blocks/nether_portal.png", "textures/blocks/portal.png"});

        // Nether wart
        defaultData.add(new String[] {"textures/blocks/nether_wart_stage0.png", "textures/blocks/nether_wart_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/nether_wart_stage1.png", "textures/blocks/nether_wart_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/nether_wart_stage2.png", "textures/blocks/nether_wart_stage_2.png"});

        // Note block
        defaultData.add(new String[] {"textures/blocks/note_block.png", "textures/blocks/noteblock.png"});

        // Nautilus shell
        defaultData.add(new String[] {"textures/items/nautilus_shell.png", "textures/items/nautilus.png"});

        // Observer
        defaultData.add(new String[] {"textures/blocks/observer_back_on.png", "textures/blocks/observer_back_lit.png"});

        // Painting
        defaultData.add(new String[] {"textures/painting/paintings_kristoffer_zetterstrand.png", "textures/painting/kz.png"}); // 1.13

        // Panda
        defaultData.add(new String[] {"textures/entity/panda/aggressive_panda.png", "textures/entity/panda/panda_aggressive.png"});
        defaultData.add(new String[] {"textures/entity/panda/brown_panda.png", "textures/entity/panda/panda_brown.png"});
        defaultData.add(new String[] {"textures/entity/panda/lazy_panda.png", "textures/entity/panda/panda_lazy.png"});
        defaultData.add(new String[] {"textures/entity/panda/playful_panda.png", "textures/entity/panda/panda_playful.png"});
        defaultData.add(new String[] {"textures/entity/panda/weak_panda.png", "textures/entity/panda/panda_sneezy.png"});
        defaultData.add(new String[] {"textures/entity/panda/worried_panda.png", "textures/entity/panda/panda_worried.png"});

        // Piglin
        defaultData.add(new String[] {"textures/entity/piglin/zombified_piglin.png", "textures/entity/piglin/zombie_piglin.png"});

        // Piston
        defaultData.add(new String[] {"textures/blocks/piston_top.png", "textures/blocks/piston_top_normal.png"});

        // Planks
        defaultData.add(new String[] {"textures/blocks/acacia_planks.png", "textures/blocks/planks_acacia.png"});
        defaultData.add(new String[] {"textures/blocks/birch_planks.png", "textures/blocks/planks_birch.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_planks.png", "textures/blocks/planks_big_oak.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_planks.png", "textures/blocks/planks_jungle.png"});
        defaultData.add(new String[] {"textures/blocks/oak_planks.png", "textures/blocks/planks_oak.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_planks.png", "textures/blocks/planks_spruce.png"});

        // Podzol
        defaultData.add(new String[] {"textures/blocks/podzol_side.png", "textures/blocks/dirt_podzol_side.png"});
        defaultData.add(new String[] {"textures/blocks/podzol_top.png", "textures/blocks/dirt_podzol_top.png"});

        // Potato
        defaultData.add(new String[] {"textures/blocks/potatoes_stage0.png", "textures/blocks/potatoes_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/potatoes_stage1.png", "textures/blocks/potatoes_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/potatoes_stage2.png", "textures/blocks/potatoes_stage_2.png"});
        defaultData.add(new String[] {"textures/blocks/potatoes_stage3.png", "textures/blocks/potatoes_stage_3.png"});
        defaultData.add(new String[] {"textures/items/baked_potato.png", "textures/items/potato_baked.png"});
        defaultData.add(new String[] {"textures/items/poisonous_potato.png", "textures/items/potato_poisonous.png"});

        // Potion
        defaultData.add(new String[] {"textures/items/lingering_potion.png", "textures/items/potion_bottle_lingering_empty.png"});
        defaultData.add(new String[] {"textures/items/potion.png", "textures/items/potion_bottle_empty.png"});
        defaultData.add(new String[] {"textures/items/splash_potion.png", "textures/items/potion_bottle_splash_empty.png"});

        // Prismarine
        defaultData.add(new String[] {"textures/blocks/dark_prismarine.png", "textures/blocks/prismarine_dark.png"});
        defaultData.add(new String[] {"textures/blocks/prismarine.png", "textures/blocks/prismarine_rough.png"});

        // Pumpkin
        defaultData.add(new String[] {"textures/blocks/attached_pumpkin_stem.png", "textures/blocks/pumpkin_stem_connected.png"});
        defaultData.add(new String[] {"textures/blocks/carved_pumpkin.png", "textures/blocks/pumpkin_face_off.png"});
        defaultData.add(new String[] {"textures/blocks/jack_o_lantern.png", "textures/blocks/pumpkin_face_on.png"});
        defaultData.add(new String[] {"textures/blocks/pumpkin_stem.png", "textures/blocks/pumpkin_stem_disconnected.png"});

        // Quartz
        defaultData.add(new String[] {"textures/blocks/chiseled_quartz_block.png", "textures/blocks/quartz_block_chiseled.png"});
        defaultData.add(new String[] {"textures/blocks/chiseled_quartz_block_top.png", "textures/blocks/quartz_block_chiseled_top.png"});
        defaultData.add(new String[] {"textures/blocks/nether_quartz_ore.png", "textures/blocks/quartz_ore.png"});
        defaultData.add(new String[] {"textures/blocks/quartz_pillar.png", "textures/blocks/quartz_block_lines.png"});
        defaultData.add(new String[] {"textures/blocks/quartz_pillar_top.png", "textures/blocks/quartz_block_lines_top.png"});

        // Rabbit
        defaultData.add(new String[] {"textures/entity/rabbit/black.png", "textures/entity/rabbit/blackrabbit.png"});
        defaultData.add(new String[] {"textures/items/cooked_rabbit.png", "textures/items/rabbit_cooked.png"});
        defaultData.add(new String[] {"textures/items/rabbit.png", "textures/items/rabbit_raw.png"});

        // Rail
        defaultData.add(new String[] {"textures/blocks/activator_rail.png", "textures/blocks/rail_activator.png"});
        defaultData.add(new String[] {"textures/blocks/activator_rail_on.png", "textures/blocks/rail_activator_powered.png"});
        defaultData.add(new String[] {"textures/blocks/detector_rail.png", "textures/blocks/rail_detector.png"});
        defaultData.add(new String[] {"textures/blocks/detector_rail_on.png", "textures/blocks/rail_detector_powered.png"});
        defaultData.add(new String[] {"textures/blocks/powered_rail.png", "textures/blocks/rail_golden.png"});
        defaultData.add(new String[] {"textures/blocks/powered_rail_on.png", "textures/blocks/rail_golden_powered.png"});
        defaultData.add(new String[] {"textures/blocks/rail.png", "textures/blocks/rail_normal.png"});
        defaultData.add(new String[] {"textures/blocks/rail_corner.png", "textures/blocks/rail_normal_turned.png"});

        // Red sand
        defaultData.add(new String[] {"textures/blocks/chiseled_red_sandstone.png", "textures/blocks/red_sandstone_carved.png"});
        defaultData.add(new String[] {"textures/blocks/cut_red_sandstone.png", "textures/blocks/red_sandstone_smooth.png"});
        defaultData.add(new String[] {"textures/blocks/red_sandstone.png", "textures/blocks/red_sandstone_normal.png"});

        // Redstone
        defaultData.add(new String[] {"textures/items/redstone.png", "textures/items/redstone_dust.png"});

        // Redstone lamp
        defaultData.add(new String[] {"textures/blocks/redstone_lamp.png", "textures/blocks/redstone_lamp_off.png"});

        // Repeater
        defaultData.add(new String[] {"textures/blocks/repeater.png", "textures/blocks/repeater_off.png"});

        // Saddle
        defaultData.add(new String[] {"textures/entity/pig/pig_saddle.png", "textures/entity/saddle.png"});

        // Sand
        defaultData.add(new String[] {"textures/blocks/chiseled_sandstone.png", "textures/blocks/sandstone_carved.png"});
        defaultData.add(new String[] {"textures/blocks/cut_sandstone.png", "textures/blocks/sandstone_smooth.png"});
        defaultData.add(new String[] {"textures/blocks/sandstone.png", "textures/blocks/sandstone_normal.png"});

        // Sapling
        defaultData.add(new String[] {"textures/blocks/acacia_sapling.png", "textures/blocks/sapling_acacia.png"});
        defaultData.add(new String[] {"textures/blocks/birch_sapling.png", "textures/blocks/sapling_birch.png"});
        defaultData.add(new String[] {"textures/blocks/dark_oak_sapling.png", "textures/blocks/sapling_roofed_oak.png"});
        defaultData.add(new String[] {"textures/blocks/jungle_sapling.png", "textures/blocks/sapling_jungle.png"});
        defaultData.add(new String[] {"textures/blocks/oak_sapling.png", "textures/blocks/sapling_oak.png"});
        defaultData.add(new String[] {"textures/blocks/spruce_sapling.png", "textures/blocks/sapling_spruce.png"});

        // Sea grass
        defaultData.add(new String[] {"textures/blocks/tall_seagrass_top.png", "textures/blocks/seagrass_doubletall_top_a.png"});
        defaultData.add(new String[] {"textures/blocks/tall_seagrass_bottom.png", "textures/blocks/seagrass_doubletall_bottom_a.png"});
        defaultData.add(new String[] {"textures/items/seagrass.png", "textures/blocks/seagrass_carried.png"});

        // Seed
        defaultData.add(new String[] {"textures/items/beetroot_seeds.png", "textures/items/seeds_beetroot.png"});
        defaultData.add(new String[] {"textures/items/melon_seeds.png", "textures/items/seeds_melon.png"});
        defaultData.add(new String[] {"textures/items/pumpkin_seeds.png", "textures/items/seeds_pumpkin.png"});
        defaultData.add(new String[] {"textures/items/wheat_seeds.png", "textures/items/seeds_wheat.png"});

        // Shield
        defaultData.add(new String[] {"textures/entity/shield_base_nopattern.png", "textures/entity/shield.png"});

        // Shulker
        defaultData.add(new String[] {"textures/blocks/black_shulker_box.png", "textures/blocks/shulker_top_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_shulker_box.png", "textures/blocks/shulker_top_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_shulker_box.png", "textures/blocks/shulker_top_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_shulker_box.png", "textures/blocks/shulker_top_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_shulker_box.png", "textures/blocks/shulker_top_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_shulker_box.png", "textures/blocks/shulker_top_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_shulker_box.png", "textures/blocks/shulker_top_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_shulker_box.png", "textures/blocks/shulker_top_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_shulker_box.png", "textures/blocks/shulker_top_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_shulker_box.png", "textures/blocks/shulker_top_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_shulker_box.png", "textures/blocks/shulker_top_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_shulker_box.png", "textures/blocks/shulker_top_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_shulker_box.png", "textures/blocks/shulker_top_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_shulker_box.png", "textures/blocks/shulker_top_red.png"});
        defaultData.add(new String[] {"textures/blocks/shulker_box.png", "textures/blocks/shulker_top_undyed.png"});
        defaultData.add(new String[] {"textures/blocks/white_shulker_box.png", "textures/blocks/shulker_top_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_shulker_box.png", "textures/blocks/shulker_top_yellow.png"});
        defaultData.add(new String[] {"textures/entity/shulker/shulker.png", "textures/entity/shulker/shulker_undyed.png"});
        defaultData.add(new String[] {"textures/entity/shulker/shulker_light_gray.png", "textures/entity/shulker/shulker_silver.png"});

        // Sign
        defaultData.add(new String[] {"textures/entity/signs/acacia.png", "textures/entity/sign_acacia.png"});
        defaultData.add(new String[] {"textures/entity/signs/birch.png", "textures/entity/sign_birch.png"});
        defaultData.add(new String[] {"textures/entity/signs/crimson.png", "textures/entity/sign_crimson.png"});
        defaultData.add(new String[] {"textures/entity/signs/dark_oak.png", "textures/entity/sign_darkoak.png"});
        defaultData.add(new String[] {"textures/entity/signs/jungle.png", "textures/entity/sign_jungle.png"});
        defaultData.add(new String[] {"textures/entity/signs/oak.png", "textures/entity/sign.png"});
        defaultData.add(new String[] {"textures/entity/signs/spruce.png", "textures/entity/sign_spruce.png"});
        defaultData.add(new String[] {"textures/entity/signs/warped.png", "textures/entity/sign_warped.png"});
        defaultData.add(new String[] {"textures/items/acacia_sign.png", "textures/items/sign_acacia.png"});
        defaultData.add(new String[] {"textures/items/birch_sign.png", "textures/items/sign_birch.png"});
        defaultData.add(new String[] {"textures/items/crimson_sign.png", "textures/items/sign_crimson.png"});
        defaultData.add(new String[] {"textures/items/dark_oak_sign.png", "textures/items/sign_darkoak.png"});
        defaultData.add(new String[] {"textures/items/jungle_sign.png", "textures/items/sign_jungle.png"});
        defaultData.add(new String[] {"textures/items/oak_sign.png", "textures/items/sign.png"});
        defaultData.add(new String[] {"textures/items/spruce_sign.png", "textures/items/sign_spruce.png"});
        defaultData.add(new String[] {"textures/items/warped_sign.png", "textures/items/sign_warped.png"});

        // Slime
        defaultData.add(new String[] {"textures/blocks/slime_block.png", "textures/blocks/slime.png"});
        defaultData.add(new String[] {"textures/items/slime_ball.png", "textures/items/slimeball.png"});

        // Smooth stone
        defaultData.add(new String[] {"textures/blocks/smooth_stone.png", "textures/blocks/stone_slab_top.png"});
        defaultData.add(new String[] {"textures/blocks/smooth_stone_slab_side.png", "textures/blocks/stone_slab_side.png"});

        // Spawner
        defaultData.add(new String[] {"textures/blocks/spawner.png", "textures/blocks/mob_spawner.png"});

        // Spider
        defaultData.add(new String[] {"textures/items/fermented_spider_eye.png", "textures/items/spider_eye_fermented.png"});

        // Sponge
        defaultData.add(new String[] {"textures/blocks/wet_sponge.png", "textures/blocks/sponge_wet.png"});

        // Stone brick
        defaultData.add(new String[] {"textures/blocks/chiseled_stone_bricks.png", "textures/blocks/stonebrick_carved.png"});
        defaultData.add(new String[] {"textures/blocks/cracked_stone_bricks.png", "textures/blocks/stonebrick_cracked.png"});
        defaultData.add(new String[] {"textures/blocks/mossy_stone_bricks.png", "textures/blocks/stonebrick_mossy.png"});
        defaultData.add(new String[] {"textures/blocks/stone_bricks.png", "textures/blocks/stonebrick.png"});

        // Stone cutter
        defaultData.add(new String[] {"textures/blocks/stonecutter_bottom.png", "textures/blocks/stonecutter2_bottom.png"});
        defaultData.add(new String[] {"textures/blocks/stonecutter_saw.png", "textures/blocks/stonecutter2_saw.png"});
        defaultData.add(new String[] {"textures/blocks/stonecutter_side.png", "textures/blocks/stonecutter2_side.png"});
        defaultData.add(new String[] {"textures/blocks/stonecutter_top.png", "textures/blocks/stonecutter2_top.png"});

        // Strider
        defaultData.add(new String[] {"textures/entity/strider/strider_cold.png", "textures/entity/strider/strider_suffocated.png"});

        // Structure
        defaultData.add(new String[] {"textures/items/structure_void.png", "textures/blocks/structure_void.png"});

        // Sugar cane
        defaultData.add(new String[] {"textures/blocks/sugar_cane.png", "textures/blocks/reeds.png"});
        defaultData.add(new String[] {"textures/items/sugar_cane.png", "textures/items/reeds.png"});

        // Terracotta
        defaultData.add(new String[] {"textures/blocks/black_terracotta.png", "textures/blocks/hardened_clay_stained_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_terracotta.png", "textures/blocks/hardened_clay_stained_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_terracotta.png", "textures/blocks/hardened_clay_stained_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_terracotta.png", "textures/blocks/hardened_clay_stained_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_terracotta.png", "textures/blocks/hardened_clay_stained_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_terracotta.png", "textures/blocks/hardened_clay_stained_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_terracotta.png", "textures/blocks/hardened_clay_stained_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_terracotta.png", "textures/blocks/hardened_clay_stained_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_terracotta.png", "textures/blocks/hardened_clay_stained_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_terracotta.png", "textures/blocks/hardened_clay_stained_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_terracotta.png", "textures/blocks/hardened_clay_stained_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_terracotta.png", "textures/blocks/hardened_clay_stained_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_terracotta.png", "textures/blocks/hardened_clay_stained_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_terracotta.png", "textures/blocks/hardened_clay_stained_red.png"});
        defaultData.add(new String[] {"textures/blocks/terracotta.png", "textures/blocks/hardened_clay.png"});
        defaultData.add(new String[] {"textures/blocks/white_terracotta.png", "textures/blocks/hardened_clay_stained_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_terracotta.png", "textures/blocks/hardened_clay_stained_yellow.png"});

        // Torch
        defaultData.add(new String[] {"textures/blocks/redstone_torch.png", "textures/blocks/redstone_torch_on.png"});
        defaultData.add(new String[] {"textures/blocks/torch.png", "textures/blocks/torch_on.png"});

        // Totem of undying
        defaultData.add(new String[] {"textures/items/totem_of_undying.png", "textures/items/totem.png"});

        // Trapdoor
        defaultData.add(new String[] {"textures/blocks/crimson_trapdoor.png", "textures/blocks/huge_fungus/crimson_trapdoor.png"});
        defaultData.add(new String[] {"textures/blocks/oak_trapdoor.png", "textures/blocks/trapdoor.png"});
        defaultData.add(new String[] {"textures/blocks/warped_trapdoor.png", "textures/blocks/huge_fungus/warped_trapdoor.png"});

        // Tripwire
        defaultData.add(new String[] {"textures/blocks/tripwire.png", "textures/blocks/trip_wire.png"});
        defaultData.add(new String[] {"textures/blocks/tripwire_hook.png", "textures/blocks/trip_wire_source.png"});

        // Turtle
        defaultData.add(new String[] {"textures/blocks/turtle_egg.png", "textures/blocks/turtle_egg_not_cracked.png"});
        defaultData.add(new String[] {"textures/entity/turtle/big_sea_turtle.png", "textures/entity/sea_turtle.png"});
        defaultData.add(new String[] {"textures/items/scute.png", "textures/items/turtle_shell_piece.png"});

        // UI
        defaultData.add(new String[] {"textures/gui/options_background.png", "textures/ui/background.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_0.png", "textures/ui/panorama_0.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_1.png", "textures/ui/panorama_1.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_2.png", "textures/ui/panorama_2.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_3.png", "textures/ui/panorama_3.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_4.png", "textures/ui/panorama_4.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_5.png", "textures/ui/panorama_5.png"});
        defaultData.add(new String[] {"textures/gui/title/background/panorama_overlay.png", "textures/ui/panorama_overlay.png"});

        // Villager
        defaultData.add(new String[] {"textures/entity/villager/", "textures/entity/villager2/"});
        defaultData.add(new String[] {"textures/entity/villager2/profession/", "textures/entity/villager2/professions/"});
        defaultData.add(new String[] {"textures/entity/villager2/profession_level/", "textures/entity/villager2/levels/"});
        defaultData.add(new String[] {"textures/entity/villager2/type/", "textures/entity/villager2/biomes/"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/desert.png", "textures/entity/villager2/biomes/biome_desert.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/jungle.png", "textures/entity/villager2/biomes/biome_jungle.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/plains.png", "textures/entity/villager2/biomes/biome_plains.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/savanna.png", "textures/entity/villager2/biomes/biome_savanna.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/snow.png", "textures/entity/villager2/biomes/biome_snow.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/swamp.png", "textures/entity/villager2/biomes/biome_swamp.png"});
        defaultData.add(new String[] {"textures/entity/villager2/biomes/taiga.png", "textures/entity/villager2/biomes/biome_taiga.png"});
        defaultData.add(new String[] {"textures/entity/villager2/levels/diamond.png", "textures/entity/villager2/levels/level_diamond.png"});
        defaultData.add(new String[] {"textures/entity/villager2/levels/emerald.png", "textures/entity/villager2/levels/level_emerald.png"});
        defaultData.add(new String[] {"textures/entity/villager2/levels/gold.png", "textures/entity/villager2/levels/level_gold.png"});
        defaultData.add(new String[] {"textures/entity/villager2/levels/iron.png", "textures/entity/villager2/levels/level_iron.png"});
        defaultData.add(new String[] {"textures/entity/villager2/levels/stone.png", "textures/entity/villager2/levels/level_stone.png"});
        defaultData.add(new String[] {"textures/entity/villager2/professions/mason.png", "textures/entity/villager2/professions/stonemason.png"});

        // Vine
        defaultData.add(new Object[] {"textures/blocks/twisting_vines.png", "textures/blocks/twisting_vines_bottom.png"});
        defaultData.add(new Object[] {"textures/blocks/twisting_vines_plant.png", "textures/blocks/twisting_vines_base.png"});
        defaultData.add(new Object[] {"textures/blocks/weeping_vines.png", "textures/blocks/weeping_vines_bottom.png"});
        defaultData.add(new Object[] {"textures/blocks/weeping_vines_plant.png", "textures/blocks/weeping_vines_base.png"});

        // Warped
        defaultData.add(new Object[] {"textures/blocks/stripped_warped_stem.png", "textures/blocks/huge_fungus/stripped_warped_stem_side.png"});
        defaultData.add(new Object[] {"textures/blocks/stripped_warped_stem_top.png", "textures/blocks/huge_fungus/stripped_warped_stem_top.png"});
        defaultData.add(new Object[] {"textures/blocks/warped_nylium.png", "textures/blocks/warped_nylium_top.png"});
        defaultData.add(new Object[] {"textures/blocks/warped_planks.png", "textures/blocks/huge_fungus/warped_planks.png"});
        defaultData.add(new Object[] {"textures/blocks/warped_stem.png", "textures/blocks/huge_fungus/warped_stem_side.png"});
        defaultData.add(new Object[] {"textures/blocks/warped_stem_top.png", "textures/blocks/huge_fungus/warped_stem_top.png"});

        // Water
        defaultData.add(new String[] {"textures/blocks/water_flow.png", "textures/blocks/water_flow_grey.png"});
        defaultData.add(new String[] {"textures/blocks/water_still.png", "textures/blocks/water_still_grey.png"});

        // Wheat
        defaultData.add(new String[] {"textures/blocks/wheat_stage0.png", "textures/blocks/wheat_stage_0.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage1.png", "textures/blocks/wheat_stage_1.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage2.png", "textures/blocks/wheat_stage_2.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage3.png", "textures/blocks/wheat_stage_3.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage4.png", "textures/blocks/wheat_stage_4.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage5.png", "textures/blocks/wheat_stage_5.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage6.png", "textures/blocks/wheat_stage_6.png"});
        defaultData.add(new String[] {"textures/blocks/wheat_stage7.png", "textures/blocks/wheat_stage_7.png"});

        // Wither
        defaultData.add(new String[] {"textures/entity/wither/", "textures/entity/wither_boss/"});
        defaultData.add(new String[] {"textures/entity/wither_boss/wither_armor.png", "textures/entity/wither_boss/wither_armor_white.png"});

        // Wool
        defaultData.add(new String[] {"textures/blocks/black_wool.png", "textures/blocks/wool_colored_black.png"});
        defaultData.add(new String[] {"textures/blocks/blue_wool.png", "textures/blocks/wool_colored_blue.png"});
        defaultData.add(new String[] {"textures/blocks/brown_wool.png", "textures/blocks/wool_colored_brown.png"});
        defaultData.add(new String[] {"textures/blocks/cyan_wool.png", "textures/blocks/wool_colored_cyan.png"});
        defaultData.add(new String[] {"textures/blocks/gray_wool.png", "textures/blocks/wool_colored_gray.png"});
        defaultData.add(new String[] {"textures/blocks/green_wool.png", "textures/blocks/wool_colored_green.png"});
        defaultData.add(new String[] {"textures/blocks/light_blue_wool.png", "textures/blocks/wool_colored_light_blue.png"});
        defaultData.add(new String[] {"textures/blocks/light_gray_wool.png", "textures/blocks/wool_colored_silver.png"});
        defaultData.add(new String[] {"textures/blocks/lime_wool.png", "textures/blocks/wool_colored_lime.png"});
        defaultData.add(new String[] {"textures/blocks/magenta_wool.png", "textures/blocks/wool_colored_magenta.png"});
        defaultData.add(new String[] {"textures/blocks/orange_wool.png", "textures/blocks/wool_colored_orange.png"});
        defaultData.add(new String[] {"textures/blocks/pink_wool.png", "textures/blocks/wool_colored_pink.png"});
        defaultData.add(new String[] {"textures/blocks/purple_wool.png", "textures/blocks/wool_colored_purple.png"});
        defaultData.add(new String[] {"textures/blocks/red_wool.png", "textures/blocks/wool_colored_red.png"});
        defaultData.add(new String[] {"textures/blocks/white_wool.png", "textures/blocks/wool_colored_white.png"});
        defaultData.add(new String[] {"textures/blocks/yellow_wool.png", "textures/blocks/wool_colored_yellow.png"});

        // Zombie
        defaultData.add(new String[] {"textures/entity/zombie_pigman.png", "textures/entity/pig/pigzombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager/", "textures/entity/zombie_villager2/"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/profession/", "textures/entity/zombie_villager2/professions/"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/profession_level/", "textures/entity/zombie_villager2/levels/"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/type/", "textures/entity/zombie_villager2/biomes/"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/zombie_villager.png", "textures/entity/zombie_villager2/zombie-villager.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/desert.png", "textures/entity/zombie_villager2/biomes/biome-desert-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/jungle.png", "textures/entity/zombie_villager2/biomes/biome-jungle-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/plains.png", "textures/entity/zombie_villager2/biomes/biome-plains-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/savanna.png", "textures/entity/zombie_villager2/biomes/biome-savanna-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/snow.png", "textures/entity/zombie_villager2/biomes/biome-snow-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/swamp.png", "textures/entity/zombie_villager2/biomes/biome-swamp-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/biomes/taiga.png", "textures/entity/zombie_villager2/biomes/biome-taiga-zombie.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/levels/diamond.png", "textures/entity/zombie_villager2/levels/level_diamond.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/levels/emerald.png", "textures/entity/zombie_villager2/levels/level_emerald.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/levels/gold.png", "textures/entity/zombie_villager2/levels/level_gold.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/levels/iron.png", "textures/entity/zombie_villager2/levels/level_iron.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/levels/stone.png", "textures/entity/zombie_villager2/levels/level_stone.png"});
        defaultData.add(new String[] {"textures/entity/zombie_villager2/professions/mason.png", "textures/entity/zombie_villager2/professions/stonemason.png"});
    }

    public RenameConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            Path fromPath = storage.resolve(from);

            if (!fromPath.toFile().exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Rename %s to %s", from, to));

            Files.move(fromPath, storage.resolve(to));
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
