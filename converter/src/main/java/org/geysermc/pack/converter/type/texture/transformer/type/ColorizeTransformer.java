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

package org.geysermc.pack.converter.type.texture.transformer.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
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
        new ColorizeData(new Overlay("entity/equipment/humanoid/leather.png", new Color(167, 105, 67)), "models/armor/cloth_1.png"),
        new ColorizeData(new Overlay("entity/equipment/humanoid_leggings/leather.png", new Color(167, 105, 67)), "models/armor/cloth_2.png"),

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
        new ColorizeData(new Overlay("block/short_grass.png", new Color(81, 123, 44)), "blocks/tallgrass_carried.png"), // 1/5 (tallgrass_carried.tga)
        new ColorizeData(new Overlay("block/bush.png", new Color(60, 92, 51)), "items/bush.png"), // 0/10 (items/bush.png)
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
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(140, 155, 140)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_infested.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(153, 255, 163)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_oozing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(189, 201, 255)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_windCharged.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(120, 105, 90)), new Overlay("item/lingering_potion.png") },"items/potion_bottle_lingering_weaving.png"),

        // Map
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
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(140, 155, 140)), new Overlay("item/potion.png") },"items/potion_bottle_infested.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(153, 255, 163)), new Overlay("item/potion.png") },"items/potion_bottle_oozing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(189, 201, 255)), new Overlay("item/potion.png") },"items/potion_bottle_windCharged.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(120, 105, 90)), new Overlay("item/potion.png") },"items/potion_bottle_weaving.png"),

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
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(140, 155, 140)), new Overlay("item/splash_potion.png") },"items/potion_bottle_splash_infested.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(153, 255, 163)), new Overlay("item/splash_potion.png") },"items/potion_bottle_splash_oozing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(189, 201, 255)), new Overlay("item/splash_potion.png") },"items/potion_bottle_splash_windCharged.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/potion_overlay.png", new Color(120, 105, 90)), new Overlay("item/splash_potion.png") },"items/potion_bottle_splash_weaving.png"),

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
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(50, 39, 36)) }, "items/tipped_arrow_wither.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(140, 155, 140)) }, "items/tipped_arrow_infested.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(153, 255, 163)) }, "items/tipped_arrow_oozing.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(120, 105, 90)) }, "items/tipped_arrow_weaving.png"),
        new ColorizeData(new Overlay[] { new Overlay("item/tipped_arrow_base.png"),  new Overlay("item/tipped_arrow_head.png", new Color(189, 201, 255)) }, "items/tipped_arrow_windCharged.png")
    );
    
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (ColorizeData data : COLORIZE_DATA) {
            BufferedImage finalImage = null;

            for (Overlay overlay : data.overlays()) {
                String overlayPath = overlay.overlayPath();
                Color color = overlay.color();
                boolean deleteOverlay = overlay.deleteOverlay();

                Key key = KeyUtil.key(Key.MINECRAFT_NAMESPACE, overlayPath);
                Texture texture = deleteOverlay ? context.poll(key) : context.peek(key);
                if (texture == null) {
                    context.debug("Missing overlay texture: " + overlayPath);
                    continue;
                }

                BufferedImage overlayImage = this.readImage(texture);
                if (finalImage == null) {
                    context.debug(String.format("Colorizing and overlaying %s", overlay.overlayPath()));

                    finalImage = new BufferedImage(overlayImage.getWidth(), overlayImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                }

                overlayImage = ImageUtil.colorize(overlayImage, color);
                finalImage.getGraphics().drawImage(overlayImage, 0, 0, null);
            }

            if (finalImage != null) {
                context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, data.outputPath()), finalImage, "png");
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
