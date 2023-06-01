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
import org.geysermc.pack.converter.util.UnsafeKey;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class ColorizeTransformer implements TextureTransformer {
    private static final List<ColorizeData> COLORIZE_DATA = List.of(
        // Armor (Colors from px 9/1 from original cloth_1.png bedrock texture)
        new ColorizeData(new Overlay("models/armor/leather_layer_1.png", new Color(167, 105, 67)), "models/armor/cloth_1.png"),
        new ColorizeData(new Overlay("models/armor/leather_layer_2.png", new Color(167, 105, 67)), "models/armor/cloth_2.png"),

        // Grass, fern, water & co.
        new ColorizeData(new Overlay("block/large_fern_top.png", new Color(80, 121, 43)), "blocks/double_plant_fern_carried.png"), // 3/5 (double_plant_fern_carried.png)
        new ColorizeData(new Overlay("block/tall_grass_top.png", new Color(80, 121, 43)), "blocks/double_plant_grass_carried.png"), // 3/5 (double_plant_fern_carried.png)
        new ColorizeData(new Overlay("block/fern.png", new Color(50, 81, 44)), "blocks/fern_carried.png"), // 7/0 (fern_carried.tga)
        new ColorizeData(new Overlay("block/grass_block_top.png", new Color(78, 119, 42)), "blocks/grass_carried.png"), // 0/0 (grass_carried.png)
        new ColorizeData(new Overlay("block/acacia_leaves.png", new Color(42, 106, 9)), "blocks/leaves_acacia_carried.png"), // 0/0 (leaves_acacia_carried.tga)
        new ColorizeData(new Overlay("block/dark_oak_leaves.png", new Color(34, 90, 9)), "blocks/leaves_big_oak_carried.png"), // 0/0 (leaves_big_oak_carried.tga)
        new ColorizeData(new Overlay("block/birch_leaves.png", new Color(71, 92, 46)), "blocks/leaves_birch_carried.png"), // 0/0 (leaves_birch_carried.tga)
        new ColorizeData(new Overlay("block/jungle_leaves.png", new Color(42, 107, 9)), "blocks/leaves_jungle_carried.png"), // 0/1 (leaves_jungle_carried.tga)
        new ColorizeData(new Overlay("block/oak_leaves.png", new Color(23, 63, 3)), "blocks/leaves_oak_carried.png"), // 0/0 (leaves_oak_carried.tga)
        new ColorizeData(new Overlay("block/spruce_leaves.png", new Color(58, 92, 58)), "blocks/leaves_spruce_carried.png"), // 0/0 (leaves_spruce_carried.tga)
        new ColorizeData(new Overlay("block/mangrove_leaves.png", new Color(58, 92, 58)), "blocks/mangrove_leaves_carried.png"), // 0/0 (leaves_spruce_carried.tga)
        new ColorizeData(new Overlay("block/grass.png", new Color(81, 123, 44)), "blocks/tallgrass_carried.png"), // 1/5 (tallgrass_carried.tga)
        new ColorizeData(new Overlay("block/lily_pad.png", new Color(67, 102, 36)), "blocks/carried_waterlily.png"), // 4/2 (carried_waterlily.png)
        new ColorizeData(new Overlay("block/water_flow.png", new Color(86, 132, 254)), "blocks/water_flow.png"), // 0/0 (water_flow.png)
        new ColorizeData(new Overlay("block/water_still.png", new Color(215, 215, 215)), "blocks/cauldron_water.png"), // 0/0 (cauldron_water.png)
        new ColorizeData(new Overlay("block/water_still.png", new Color(86, 132, 254)), "blocks/water_still.png"), // 0/0 (water_flow.png)
        new ColorizeData(new Overlay("block/vine.png", new Color(80, 121, 43)), "blocks/vine_carried.png"), // 1/1 (vine_carried.png)

        // Lingering potion (Colors from px 7/9 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(88, 148, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(232, 58, 56)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_damageBoost.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 244, 92)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_fireResistance.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(106, 16, 14)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_harm.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 58, 56)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_heal.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(202, 208, 232)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_invisibility.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(54, 255, 120)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_jump.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(242, 255, 202)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_luck.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(142, 172, 204)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_moveSlowdown.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(196, 255, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_moveSpeed.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(50, 50, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_nightVision.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(124, 232, 78)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_poison.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 146, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_regeneration.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_slowFall.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(186, 144, 156)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_turtleMaster.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(72, 130, 242)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_waterBreathing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(114, 122, 114)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_weakness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(84, 66, 62)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_wither.png"),

        // Map (Colors from px 6/7 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/filled_map.png"),  new Overlay("item/filled_map_markings.png", new Color(82, 76, 68)) },"items/map_mansion.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/filled_map.png"),  new Overlay("item/filled_map_markings.png", new Color(67, 124, 111)) },"items/map_monument.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/filled_map.png"),  new Overlay("item/filled_map_markings.png", new Color(103, 90, 173)) },"items/map_nautilus.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/filled_map.png"),  new Overlay("item/filled_map_markings.png", new Color(131, 131, 131)) },"items/map_filled.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/filled_map.png"),  new Overlay("item/filled_map_markings.png", new Color(131, 131, 131), true) }, "items/map_locked.png"),
        // new ColorizeData(new Overlay[] { new Overlay("ui/cartography_table_map.png"),  new Overlay("ui/cartography_table_glass.png") }, "ui/cartography_table_glass.png"),

        // Potion (Colors from px 7/9 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(58, 130, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_absorption.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(50, 50, 56)), new Overlay("item/potion.png") }, "items/potion_bottle_blindness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(134, 46, 118)), new Overlay("item/potion.png") }, "items/potion_bottle_confusion.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(232, 58, 56)), new Overlay("item/potion.png") }, "items/potion_bottle_damageBoost.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(118, 104, 36)), new Overlay("item/potion.png") }, "items/potion_bottle_digSlowdown.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 106)), new Overlay("item/potion.png") }, "items/potion_bottle_digSpeed.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(88, 148, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_drinkable.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 244, 92)), new Overlay("item/potion.png") }, "items/potion_bottle_fireResistance.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(106, 16, 14)), new Overlay("item/potion.png") }, "items/potion_bottle_harm.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 58, 56)), new Overlay("item/potion.png") }, "items/potion_bottle_heal.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 198, 56)), new Overlay("item/potion.png") }, "items/potion_bottle_healthBoost.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(140, 186, 132)), new Overlay("item/potion.png") }, "items/potion_bottle_hunger.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(202, 208, 232)), new Overlay("item/potion.png") }, "items/potion_bottle_invisibility.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(54, 255, 120)), new Overlay("item/potion.png") }, "items/potion_bottle_jump.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(54, 255, 120)), new Overlay("item/potion.png") }, "items/potion_bottle_levitation.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(142, 172, 204)), new Overlay("item/potion.png") }, "items/potion_bottle_moveSlowdown.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(196, 255, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_moveSpeed.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(50, 50, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_nightVision.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(124, 232, 78)), new Overlay("item/potion.png") }, "items/potion_bottle_poison.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 146, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_regeneration.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(242, 110, 92)), new Overlay("item/potion.png") }, "items/potion_bottle_resistance.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 58, 56)), new Overlay("item/potion.png") }, "items/potion_bottle_saturation.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 255)), new Overlay("item/potion.png") }, "items/potion_bottle_slowFall.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(186, 144, 156)), new Overlay("item/potion.png") }, "items/potion_bottle_turtleMaster.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(72, 130, 242)), new Overlay("item/potion.png") }, "items/potion_bottle_waterBreathing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(114, 112, 114)), new Overlay("item/potion.png") }, "items/potion_bottle_weakness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(84, 66, 62)), new Overlay("item/potion.png") }, "items/potion_bottle_wither.png"),

        // Redstone dust
        // new ColorizeData(new Overlay[] { new Overlay("blocks/redstone_dust_cross.png"),  new Overlay("blocks/redstone_dust_overlay.png") }, "blocks/redstone_dust_cross.png"),
        // new ColorizeData(new Overlay[] { new Overlay("blocks/redstone_dust_line.png"),  new Overlay("blocks/redstone_dust_overlay.png", Color.WHITE, true) }, "blocks/redstone_dust_line.png"),

        // Saddle
        new ColorizeData(new Overlay[] { new Overlay("entity/pig/pig.png"),  new Overlay("entity/pig/pig_saddle.png") }, "entity/pig/pig_saddle.png"),
        new ColorizeData(new Overlay[] { new Overlay("entity/strider/strider.png"),  new Overlay("entity/strider/strider_saddle.png") }, "entity/strider/strider_saddled.png"),
        new ColorizeData(new Overlay[] { new Overlay("entity/strider/strider_cold.png"),  new Overlay("entity/strider/strider_saddle.png", Color.WHITE, true) }, "entity/strider/strider_suffocated_saddled.png"),

        // Splash potion (Colors from px 7/9 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(88, 184, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(58, 130, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_absorption.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(50, 50, 56)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_blindness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(134, 46, 118)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_confusion.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(232, 58, 56)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_damageBoost.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(118, 104, 36)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_digSlowdown.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 106)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_digSpeed.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 184)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_fireResistance.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(212, 32, 28)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_harm.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 116, 112)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_heal.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 198, 56)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_healthBoost.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(140, 186, 132)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_hunger.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(202, 208, 232)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_invisibility.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(54, 255, 120)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_jump.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(54, 255, 120)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_levitation.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(142, 172, 204)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_moveSlowdown.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(196, 255, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_moveSpeed.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(50, 50, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_nightVision.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(124, 232, 78)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_poison.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 146, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_regeneration.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(242, 110, 92)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_resistance.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 58, 56)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_saturation.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(255, 255, 255)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_slowFall.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(186, 144, 156)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_turtleMaster.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(72, 130, 242)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_waterBreathing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(114, 122, 114)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_weakness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(84, 66, 62)), new Overlay("item/splash_potion.png") }, "items/potion_bottle_splash_wither.png"),

        // Spawn egg (Colors from px 8/9 and 5/9 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(65, 53, 41)), new Overlay("item/spawn_egg_overlay.png", new Color(13, 13, 13)) }, "items/egg_bat.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(204, 167, 58)), new Overlay("item/spawn_egg_overlay.png", new Color(59, 32, 24)) }, "items/egg_bee.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(211, 153, 1)), new Overlay("item/spawn_egg_overlay.png", new Color(226, 220, 112)) }, "items/egg_blaze.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(239, 200, 142)), new Overlay("item/spawn_egg_overlay.png", new Color(135, 101, 74)) }, "items/egg_cat.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(10, 57, 67)), new Overlay("item/spawn_egg_overlay.png", new Color(149, 12, 12)) }, "items/egg_cave_spider.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(138, 138, 138)), new Overlay("item/spawn_egg_overlay.png", new Color(226, 0, 0)) }, "items/egg_chicken.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(205, 90, 18)), new Overlay("item/spawn_egg_overlay.png", new Color(226, 221, 212)) }, "items/egg_clownfish.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(166, 143, 91)), new Overlay("item/spawn_egg_overlay.png", new Color(203, 174, 123)) }, "items/egg_cod.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(58, 46, 33)), new Overlay("item/spawn_egg_overlay.png", new Color(143, 143, 143)) }, "items/egg_cow.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(11, 143, 9)), new Overlay("item/spawn_egg_overlay.png", new Color(0, 0, 0)) }, "items/egg_creeper.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(29, 51, 66)), new Overlay("item/spawn_egg_overlay.png", new Color(221, 221, 221)) }, "items/egg_dolphin.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(71, 59, 49)), new Overlay("item/spawn_egg_overlay.png", new Color(119, 104, 90)) }, "items/egg_donkey.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(123, 207, 185)), new Overlay("item/spawn_egg_overlay.png", new Color(107, 138, 90)) }, "items/egg_drowned.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(177, 175, 160)), new Overlay("item/spawn_egg_overlay.png", new Color(103, 105, 130)) }, "items/egg_elderguardian.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(19, 19, 19)), new Overlay("item/spawn_egg_overlay.png", new Color(19, 19, 19)) }, "items/egg_enderman.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(19, 19, 19)), new Overlay("item/spawn_egg_overlay.png", new Color(97, 97, 97)) }, "items/egg_endermite.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(128, 133, 133)), new Overlay("item/spawn_egg_overlay.png", new Color(27, 25, 23)) }, "items/egg_evoker.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(166, 143, 91)), new Overlay("item/spawn_egg_overlay.png", new Color(203, 174, 123)) }, "items/egg_fish.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(183, 156, 137)), new Overlay("item/spawn_egg_overlay.png", new Color(181, 93, 28)) }, "items/egg_fox.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(214, 214, 214)), new Overlay("item/spawn_egg_overlay.png", new Color(167, 167, 167)) }, "items/egg_ghast.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(77, 112, 98)), new Overlay("item/spawn_egg_overlay.png", new Color(214, 111, 43)) }, "items/egg_guardian.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(165, 136, 107)), new Overlay("item/spawn_egg_overlay.png", new Color(211, 203, 0)) }, "items/egg_horse.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(102, 99, 83)), new Overlay("item/spawn_egg_overlay.png", new Color(197, 191, 127)) }, "items/egg_husk.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(45, 0, 0)), new Overlay("item/spawn_egg_overlay.png", new Color(223, 223, 0)) }, "items/egg_lava_slime.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(165, 136, 107)), new Overlay("item/spawn_egg_overlay.png", new Color(136, 84, 57)) }, "items/egg_llama.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(219, 0, 0)), new Overlay("item/spawn_egg_overlay.png", new Color(240, 226, 0)) }, "items/egg_mask.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(23, 2, 0)), new Overlay("item/spawn_egg_overlay.png", new Color(72, 45, 26)) }, "items/egg_mule.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(137, 13, 14)), new Overlay("item/spawn_egg_overlay.png", new Color(162, 162, 162)) }, "items/egg_mushroomcow.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png"),  new Overlay("item/spawn_egg_overlay.png") }, "items/egg_null.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(205, 191, 107)), new Overlay("item/spawn_egg_overlay.png", new Color(76, 60, 46)) }, "items/egg_ocelot.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(217, 217, 215)), new Overlay("item/spawn_egg_overlay.png", new Color(19, 19, 25)) }, "items/egg_panda.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(11, 143, 9)), new Overlay("item/spawn_egg_overlay.png", new Color(226, 0, 0)) }, "items/egg_parrot.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(58, 70, 119)), new Overlay("item/spawn_egg_overlay.png", new Color(121, 226, 0)) }, "items/egg_phantom.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(206, 142, 139)), new Overlay("item/spawn_egg_overlay.png", new Color(194, 88, 84)) }, "items/egg_pig.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(201, 126, 126)), new Overlay("item/spawn_egg_overlay.png", new Color(67, 100, 36)) }, "items/egg_pigzombie.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(71, 40, 46)), new Overlay("item/spawn_egg_overlay.png", new Color(132, 137, 137)) }, "items/egg_pillager.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(208, 208, 208)), new Overlay("item/spawn_egg_overlay.png", new Color(132, 132, 128)) }, "items/egg_polarbear.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(211, 153, 1)), new Overlay("item/spawn_egg_overlay.png", new Color(49, 173, 214)) }, "items/egg_pufferfish.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(131, 82, 55)), new Overlay("item/spawn_egg_overlay.png", new Color(102, 64, 43)) }, "items/egg_rabbit.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(100, 100, 96)), new Overlay("item/spawn_egg_overlay.png", new Color(81, 71, 65)) }, "items/egg_ravager.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(137, 13, 14)), new Overlay("item/spawn_egg_overlay.png", new Color(12, 117, 103)) }, "items/egg_salmon.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(198, 198, 198)), new Overlay("item/spawn_egg_overlay.png", new Color(226, 160, 160)) }, "items/egg_sheep.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(127, 88, 127)), new Overlay("item/spawn_egg_overlay.png", new Color(68, 50, 73)) }, "items/egg_shulker.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(94, 94, 94)), new Overlay("item/spawn_egg_overlay.png", new Color(43, 43, 43)) }, "items/egg_silverfish.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(166, 166, 166)), new Overlay("item/spawn_egg_overlay.png", new Color(65, 65, 65)) }, "items/egg_skeleton.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(89, 89, 89)), new Overlay("item/spawn_egg_overlay.png", new Color(203, 203, 191)) }, "items/egg_skeletonhorse.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(70, 137, 53)), new Overlay("item/spawn_egg_overlay.png", new Color(112, 169, 97)) }, "items/egg_slime.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(45, 39, 33)), new Overlay("item/spawn_egg_overlay.png", new Color(149, 12, 12)) }, "items/egg_spider.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(29, 51, 66)), new Overlay("item/spawn_egg_overlay.png", new Color(99, 121, 136)) }, "items/egg_squid.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(82, 100, 101)), new Overlay("item/spawn_egg_overlay.png", new Color(193, 206, 205)) }, "items/egg_stray.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(198, 198, 198)), new Overlay("item/spawn_egg_overlay.png", new Color(0, 155, 155)) }, "items/egg_turtle.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(105, 124, 141)), new Overlay("item/spawn_egg_overlay.png", new Color(206, 210, 214)) }, "items/egg_vex.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(74, 52, 44)), new Overlay("item/spawn_egg_overlay.png", new Color(167, 123, 101)) }, "items/egg_villager.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(128, 133, 133)), new Overlay("item/spawn_egg_overlay.png", new Color(35, 83, 86)) }, "items/egg_vindicator.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(55, 84, 130)), new Overlay("item/spawn_egg_overlay.png", new Color(204, 142, 41)) }, "items/egg_wanderingtrader.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(45, 0, 0)), new Overlay("item/spawn_egg_overlay.png", new Color(72, 142, 55)) }, "items/egg_witch.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(17, 17, 17)), new Overlay("item/spawn_egg_overlay.png", new Color(63, 68, 68)) }, "items/egg_wither.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(185, 181, 181)), new Overlay("item/spawn_egg_overlay.png", new Color(183, 155, 133)) }, "items/egg_wolf.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(0, 150, 150)), new Overlay("item/spawn_egg_overlay.png", new Color(107, 138, 90)) }, "items/egg_zombie.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(36, 77, 47)), new Overlay("item/spawn_egg_overlay.png", new Color(117, 184, 113)) }, "items/egg_zombiehorse.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/spawn_egg.png", new Color(74, 52, 44)), new Overlay("item/spawn_egg_overlay.png", new Color(107, 138, 90)) }, "items/egg_zombievillager.png"),

        // Tipped arrow (Colors from px 12/3 from original bedrock textures)
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png") }, "items/tipped_arrow.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(214, 144, 54)) }, "items/tipped_arrow_fireres.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(65, 10, 9)) }, "items/tipped_arrow_harm.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(232, 34, 33)) }, "items/tipped_arrow_healing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(123, 127, 141)) }, "items/tipped_arrow_invisibility.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(33, 247, 74)) }, "items/tipped_arrow_leaping.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(49, 148, 0)) }, "items/tipped_arrow_luck.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(30, 30, 156)) }, "items/tipped_arrow_nightvision.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(74, 138, 46)) }, "items/tipped_arrow_poison.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(192, 86, 161)) }, "items/tipped_arrow_regen.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(87, 105, 125)) }, "items/tipped_arrow_slow.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(247, 232, 202)) }, "items/tipped_arrow_slowfalling.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(142, 35, 34)) }, "items/tipped_arrow_strength.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(116, 164, 186)) }, "items/tipped_arrow_swift.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(113, 88, 96)) }, "items/tipped_arrow_turtlemaster.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(45, 79, 148)) }, "items/tipped_arrow_waterbreathing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(70, 75, 70)) }, "items/tipped_arrow_weakness.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(50, 39, 36)) }, "items/tipped_arrow_wither.png")
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ColorizeData data : COLORIZE_DATA) {
            BufferedImage finalImage = null;

            for (Overlay overlay : data.overlays()) {
                String overlayPath = overlay.overlayPath();
                Color color = overlay.color();
                boolean deleteOverlay = overlay.deleteOverlay();

                Key key = Key.key(Key.MINECRAFT_NAMESPACE, overlayPath);
                Texture texture = deleteOverlay ? context.poll(key) : context.peek(key);
                if (texture == null) {
                    context.info("Missing overlay texture: " + overlayPath);
                    continue;
                }

                BufferedImage overlayImage = this.readImage(texture);
                if (finalImage == null) {
                    context.info(String.format("Colorizing and overlaying %s", overlay.overlayPath()));

                    finalImage = new BufferedImage(overlayImage.getWidth(), overlayImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                }

                overlayImage = ImageUtil.colorize(overlayImage, color);
                finalImage.getGraphics().drawImage(overlayImage, 0, 0, null);
            }

            if (finalImage != null) {
                context.offer(UnsafeKey.key(Key.MINECRAFT_NAMESPACE, data.outputPath()), finalImage, "png");
            }
        }
    }

    record ColorizeData(@NotNull Overlay[] overlays, @NotNull String outputPath) {

        public ColorizeData(@NotNull Overlay overlay, @NotNull String outputPath) {
            this(new Overlay[] { overlay }, outputPath);
        }
    }

    record Overlay(@NotNull String overlayPath, @NotNull Color color, boolean deleteOverlay) {

        public Overlay(@NotNull String overlayPath) {
            this(overlayPath, Color.WHITE, false);
        }
        
        public Overlay(@NotNull String overlayPath, @NotNull Color color) {
            this(overlayPath, color, false);
        }
    }
}
