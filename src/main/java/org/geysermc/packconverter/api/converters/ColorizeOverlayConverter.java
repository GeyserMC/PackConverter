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

public class ColorizeOverlayConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Armor (Colors from px 9/1 from original cloth_1.png bedrock texture)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/models/armor/leather_1.png", new Color(167, 105, 67)}}, "textures/models/armor/cloth_1.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/models/armor/leather_2.png", new Color(167, 105, 67)}}, "textures/models/armor/cloth_2.png"});

        // Grass, fern, water & co.
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/double_plant_fern_top.png", new Color(80, 121, 43)}}, "textures/blocks/double_plant_fern_carried.png"}); // 3/5 (double_plant_fern_carried.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/double_plant_grass_top.png", new Color(80, 121, 43)}}, "textures/blocks/double_plant_grass_carried.png"}); // 3/5 (double_plant_fern_carried.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/fern.png", new Color(50, 81, 44)}}, "textures/blocks/fern_carried.png"}); // 7/0 (fern_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/grass_top.png", new Color(78, 119, 42)}}, "textures/blocks/grass_carried.png"}); // 0/0 (grass_carried.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_acacia.png", new Color(42, 106, 9)}}, "textures/blocks/leaves_acacia_carried.png"}); // 0/0 (leaves_acacia_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_big_oak.png", new Color(34, 90, 9)}}, "textures/blocks/leaves_big_oak_carried.png"}); // 0/0 (leaves_big_oak_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_birch.png", new Color(71, 92, 46)}}, "textures/blocks/leaves_birch_carried.png"}); // 0/0 (leaves_birch_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_jungle.png", new Color(42, 107, 9)}}, "textures/blocks/leaves_jungle_carried.png"}); // 0/1 (leaves_jungle_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_oak.png", new Color(23, 63, 3)}}, "textures/blocks/leaves_oak_carried.png"}); // 0/0 (leaves_oak_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/leaves_spruce.png", new Color(58, 92, 58)}}, "textures/blocks/leaves_spruce_carried.png"}); // 0/0 (leaves_spruce_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/tallgrass.png", new Color(81, 123, 44)}}, "textures/blocks/tallgrass_carried.png"}); // 1/5 (tallgrass_carried.tga)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/waterlily.png", new Color(67, 102, 36)}}, "textures/blocks/carried_waterlily.png"}); // 4/2 (carried_waterlily.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/water_flow_grey.png", new Color(86, 132, 254)}}, "textures/blocks/water_flow.png"}); // 0/0 (water_flow.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/water_still_grey.png", new Color(215, 215, 215)}}, "textures/blocks/cauldron_water.png"}); // 0/0 (cauldron_water.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/water_still_grey.png", new Color(86, 132, 254)}}, "textures/blocks/water_still.png"}); // 0/0 (water_flow.png)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/vine.png", new Color(80, 121, 43)}}, "textures/blocks/vine_carried.png"}); // 1/1 (vine_carried.png)

        // Lingering potion (Colors from px 7/9 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(88, 148, 255)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(232, 58, 56)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_damageBoost.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 244, 92)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_fireResistance.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(106, 16, 14)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_harm.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 58, 56)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_heal.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(202, 208, 232)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_invisibility.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(54, 255, 120)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_jump.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(242, 255, 202)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_luck.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(142, 172, 204)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_moveSlowdown.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(196, 255, 255)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_moveSpeed.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(50, 50, 255)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_nightVision.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(124, 232, 78)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_poison.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 146, 255)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_regeneration.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 255)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_slowFall.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(186, 144, 156)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_turtleMaster.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(72, 130, 242)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_waterBreathing.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(114, 122, 114)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_weakness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(84, 66, 62)}, new Object[] {"textures/items/potion_bottle_lingering_empty.png"}}, "textures/items/potion_bottle_lingering_wither.png"});

        // Map (Colors from px 6/7 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/map_filled.png"}, new Object[] {"textures/items/map_filled_markings.png", new Color(82, 76, 68)}}, "textures/items/map_mansion.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/map_filled.png"}, new Object[] {"textures/items/map_filled_markings.png", new Color(67, 124, 111)}}, "textures/items/map_monument.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/map_filled.png"}, new Object[] {"textures/items/map_filled_markings.png", new Color(103, 90, 173)}}, "textures/items/map_nautilus.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/map_filled.png"}, new Object[] {"textures/items/map_filled_markings.png", new Color(131, 131, 131)}}, "textures/items/map_filled.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/map_filled.png"}, new Object[] {"textures/items/map_filled_markings.png", new Color(131, 131, 131), true}}, "textures/items/map_locked.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/ui/cartography_table_map.png"}, new Object[] {"textures/ui/cartography_table_glass.png"}}, "textures/ui/cartography_table_glass.png"});

        // Potion (Colors from px 7/9 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(58, 130, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_absorption.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(50, 50, 56)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_blindness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(134, 46, 118)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_confusion.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(232, 58, 56)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_damageBoost.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(118, 104, 36)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_digSlowdown.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 106)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_digSpeed.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(88, 148, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_drinkable.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 244, 92)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_fireResistance.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(106, 16, 14)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_harm.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 58, 56)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_heal.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 198, 56)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_healthBoost.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(140, 186, 132)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_hunger.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(202, 208, 232)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_invisibility.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(54, 255, 120)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_jump.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(54, 255, 120)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_levitation.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(142, 172, 204)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_moveSlowdown.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(196, 255, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_moveSpeed.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(50, 50, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_nightVision.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(124, 232, 78)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_poison.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 146, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_regeneration.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(242, 110, 92)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_resistance.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 58, 56)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_saturation.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 255)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_slowFall.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(186, 144, 156)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_turtleMaster.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(72, 130, 242)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_waterBreathing.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(114, 112, 114)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_weakness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(84, 66, 62)}, new Object[] {"textures/items/potion_bottle_empty.png"}}, "textures/items/potion_bottle_wither.png"});

        // Redstone dust
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/redstone_dust_cross.png"}, new Object[] {"textures/blocks/redstone_dust_overlay.png"}}, "textures/blocks/redstone_dust_cross.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/blocks/redstone_dust_line.png"}, new Object[] {"textures/blocks/redstone_dust_overlay.png", null, true}}, "textures/blocks/redstone_dust_line.png"});

        // Saddle
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/entity/pig/pig.png"}, new Object[] {"textures/entity/saddle.png"}}, "textures/entity/pig/pig_saddle.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/entity/strider/strider.png"}, new Object[] {"textures/entity/strider_saddle.png"}}, "textures/entity/strider/strider_saddled.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/entity/strider/strider_suffocated.png"}, new Object[] {"textures/entity/strider_saddle.png", null, true}}, "textures/entity/strider/strider_suffocated_saddled.png"});

        // Splash potion (Colors from px 7/9 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(88, 184, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(58, 130, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_absorption.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(50, 50, 56)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_blindness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(134, 46, 118)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_confusion.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(232, 58, 56)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_damageBoost.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(118, 104, 36)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_digSlowdown.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 106)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_digSpeed.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 184)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_fireResistance.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(212, 32, 28)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_harm.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 116, 112)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_heal.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 198, 56)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_healthBoost.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(140, 186, 132)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_hunger.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(202, 208, 232)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_invisibility.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(54, 255, 120)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_jump.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(54, 255, 120)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_levitation.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(142, 172, 204)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_moveSlowdown.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(196, 255, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_moveSpeed.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(50, 50, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_nightVision.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(124, 232, 78)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_poison.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 146, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_regeneration.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(242, 110, 92)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_resistance.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 58, 56)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_saturation.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(255, 255, 255)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_slowFall.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(186, 144, 156)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_turtleMaster.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(72, 130, 242)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_waterBreathing.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(114, 122, 114)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_weakness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/potion_overlay.png", new Color(84, 66, 62)}, new Object[] {"textures/items/potion_bottle_splash_empty.png"}}, "textures/items/potion_bottle_splash_wither.png"});

        // Spawn egg (Colors from px 8/9 and 5/9 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(65, 53, 41)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(13, 13, 13)}}, "textures/items/egg_bat.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(204, 167, 58)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(59, 32, 24)}}, "textures/items/egg_bee.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(211, 153, 1)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(226, 220, 112)}}, "textures/items/egg_blaze.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(239, 200, 142)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(135, 101, 74)}}, "textures/items/egg_cat.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(10, 57, 67)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(149, 12, 12)}}, "textures/items/egg_cave_spider.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(138, 138, 138)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(226, 0, 0)}}, "textures/items/egg_chicken.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(205, 90, 18)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(226, 221, 212)}}, "textures/items/egg_clownfish.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(166, 143, 91)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(203, 174, 123)}}, "textures/items/egg_cod.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(58, 46, 33)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(143, 143, 143)}}, "textures/items/egg_cow.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(11, 143, 9)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(0, 0, 0)}}, "textures/items/egg_creeper.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(29, 51, 66)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(221, 221, 221)}}, "textures/items/egg_dolphin.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(71, 59, 49)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(119, 104, 90)}}, "textures/items/egg_donkey.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(123, 207, 185)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(107, 138, 90)}}, "textures/items/egg_drowned.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(177, 175, 160)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(103, 105, 130)}}, "textures/items/egg_elderguardian.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(19, 19, 19)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(19, 19, 19)}}, "textures/items/egg_enderman.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(19, 19, 19)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(97, 97, 97)}}, "textures/items/egg_endermite.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(128, 133, 133)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(27, 25, 23)}}, "textures/items/egg_evoker.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(166, 143, 91)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(203, 174, 123)}}, "textures/items/egg_fish.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(183, 156, 137)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(181, 93, 28)}}, "textures/items/egg_fox.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(214, 214, 214)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(167, 167, 167)}}, "textures/items/egg_ghast.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(77, 112, 98)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(214, 111, 43)}}, "textures/items/egg_guardian.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(165, 136, 107)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(211, 203, 0)}}, "textures/items/egg_horse.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(102, 99, 83)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(197, 191, 127)}}, "textures/items/egg_husk.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(45, 0, 0)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(223, 223, 0)}}, "textures/items/egg_lava_slime.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(165, 136, 107)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(136, 84, 57)}}, "textures/items/egg_llama.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(219, 0, 0)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(240, 226, 0)}}, "textures/items/egg_mask.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(23, 2, 0)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(72, 45, 26)}}, "textures/items/egg_mule.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(137, 13, 14)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(162, 162, 162)}}, "textures/items/egg_mushroomcow.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png"}, new Object[] {"textures/items/spawn_egg_overlay.png"}}, "textures/items/egg_null.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(205, 191, 107)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(76, 60, 46)}}, "textures/items/egg_ocelot.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(217, 217, 215)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(19, 19, 25)}}, "textures/items/egg_panda.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(11, 143, 9)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(226, 0, 0)}}, "textures/items/egg_parrot.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(58, 70, 119)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(121, 226, 0)}}, "textures/items/egg_phantom.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(206, 142, 139)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(194, 88, 84)}}, "textures/items/egg_pig.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(201, 126, 126)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(67, 100, 36)}}, "textures/items/egg_pigzombie.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(71, 40, 46)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(132, 137, 137)}}, "textures/items/egg_pillager.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(208, 208, 208)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(132, 132, 128)}}, "textures/items/egg_polarbear.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(211, 153, 1)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(49, 173, 214)}}, "textures/items/egg_pufferfish.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(131, 82, 55)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(102, 64, 43)}}, "textures/items/egg_rabbit.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(100, 100, 96)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(81, 71, 65)}}, "textures/items/egg_ravager.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(137, 13, 14)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(12, 117, 103)}}, "textures/items/egg_salmon.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(198, 198, 198)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(226, 160, 160)}}, "textures/items/egg_sheep.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(127, 88, 127)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(68, 50, 73)}}, "textures/items/egg_shulker.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(94, 94, 94)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(43, 43, 43)}}, "textures/items/egg_silverfish.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(166, 166, 166)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(65, 65, 65)}}, "textures/items/egg_skeleton.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(89, 89, 89)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(203, 203, 191)}}, "textures/items/egg_skeletonhorse.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(70, 137, 53)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(112, 169, 97)}}, "textures/items/egg_slime.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(45, 39, 33)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(149, 12, 12)}}, "textures/items/egg_spider.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(29, 51, 66)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(99, 121, 136)}}, "textures/items/egg_squid.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(82, 100, 101)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(193, 206, 205)}}, "textures/items/egg_stray.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(198, 198, 198)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(0, 155, 155)}}, "textures/items/egg_turtle.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(105, 124, 141)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(206, 210, 214)}}, "textures/items/egg_vex.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(74, 52, 44)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(167, 123, 101)}}, "textures/items/egg_villager.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(128, 133, 133)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(35, 83, 86)}}, "textures/items/egg_vindicator.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(55, 84, 130)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(204, 142, 41)}}, "textures/items/egg_wanderingtrader.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(45, 0, 0)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(72, 142, 55)}}, "textures/items/egg_witch.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(17, 17, 17)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(63, 68, 68)}}, "textures/items/egg_wither.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(185, 181, 181)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(183, 155, 133)}}, "textures/items/egg_wolf.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(0, 150, 150)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(107, 138, 90)}}, "textures/items/egg_zombie.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(36, 77, 47)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(117, 184, 113)}}, "textures/items/egg_zombiehorse.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/spawn_egg.png", new Color(74, 52, 44)}, new Object[] {"textures/items/spawn_egg_overlay.png", new Color(107, 138, 90)}}, "textures/items/egg_zombievillager.png"});

        // Tipped arrow (Colors from px 12/3 from original bedrock textures)
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png"}}, "textures/items/tipped_arrow.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(214, 144, 54)}}, "textures/items/tipped_arrow_fireres.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(65, 10, 9)}}, "textures/items/tipped_arrow_harm.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(232, 34, 33)}}, "textures/items/tipped_arrow_healing.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(123, 127, 141)}}, "textures/items/tipped_arrow_invisibility.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(33, 247, 74)}}, "textures/items/tipped_arrow_leaping.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(49, 148, 0)}}, "textures/items/tipped_arrow_luck.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(30, 30, 156)}}, "textures/items/tipped_arrow_nightvision.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(74, 138, 46)}}, "textures/items/tipped_arrow_poison.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(192, 86, 161)}}, "textures/items/tipped_arrow_regen.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(87, 105, 125)}}, "textures/items/tipped_arrow_slow.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(247, 232, 202)}}, "textures/items/tipped_arrow_slowfalling.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(142, 35, 34)}}, "textures/items/tipped_arrow_strength.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(116, 164, 186)}}, "textures/items/tipped_arrow_swift.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(113, 88, 96)}}, "textures/items/tipped_arrow_turtlemaster.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(45, 79, 148)}}, "textures/items/tipped_arrow_waterbreathing.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(70, 75, 70)}}, "textures/items/tipped_arrow_weakness.png"});
        defaultData.add(new Object[] {new Object[] {new Object[] {"textures/items/tipped_arrow_base.png"}, new Object[] {"textures/items/tipped_arrow_head.png", new Color(50, 39, 36)}}, "textures/items/tipped_arrow_wither.png"});
    }

    public ColorizeOverlayConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            Object[] overlays = (Object[]) this.data[0];
            String to = (String) this.data[1];
            
            BufferedImage finalImage = null;

            for (Object overlay : overlays) {
                Object[] overlayArr = (Object[]) overlay;
                String overlayPath = (String) overlayArr[0];
                Color color = overlayArr.length > 1 && overlayArr[1] != null ? (Color) overlayArr[1] : Color.white;
                boolean deleteOverlay = overlayArr.length > 2 ? (boolean) overlayArr[2] : false;

                File overlayFile = storage.resolve(overlayPath).toFile();
                if (!overlayFile.exists()) {
                    continue;
                }

                BufferedImage overlayImage = ImageIO.read(overlayFile);
                
                if (finalImage == null) {
                    packConverter.log(String.format("Colorize and overlay %s", to));

                    finalImage = new BufferedImage(overlayImage.getWidth(), overlayImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                }

                overlayImage = ImageUtils.colorize(overlayImage, color);

                finalImage.getGraphics().drawImage(overlayImage, 0, 0, null);

                if (deleteOverlay) {
                    delete.add(new DeleteConverter(packConverter, storage, new Object[] {overlayPath}));
                }
            }

            if (finalImage != null) {
                ImageUtils.write(finalImage, "png", storage.resolve(to).toFile());
            }
        } catch (IOException e) { }

        return delete;
    }
}
