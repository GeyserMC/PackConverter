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

package org.geysermc.packconverter;

import org.geysermc.packconverter.converters.AbstractConverter;
import org.geysermc.packconverter.converters.AtlasConverter;
import org.geysermc.packconverter.converters.BannerPatternBlackConverter;
import org.geysermc.packconverter.converters.BannerPatternConverter;
import org.geysermc.packconverter.converters.BannerPatternPreviewMaxSizeConverter;
import org.geysermc.packconverter.converters.BarConverter;
import org.geysermc.packconverter.converters.BedConverter;
import org.geysermc.packconverter.converters.BeeConverter;
import org.geysermc.packconverter.converters.ChestFrontConverter;
import org.geysermc.packconverter.converters.ChestLeftRightDoubleConverter;
import org.geysermc.packconverter.converters.ChestNormalConverter;
import org.geysermc.packconverter.converters.ChestSideConverter;
import org.geysermc.packconverter.converters.ColorizeOverlayConverter;
import org.geysermc.packconverter.converters.CopyConverter;
import org.geysermc.packconverter.converters.CustomModelDataConverter;
import org.geysermc.packconverter.converters.DeleteConverter;
import org.geysermc.packconverter.converters.DespriteConverter;
import org.geysermc.packconverter.converters.DespriteExperimentalConverter;
import org.geysermc.packconverter.converters.DestroyStageConverter;
import org.geysermc.packconverter.converters.DolphinConverter;
import org.geysermc.packconverter.converters.DrownedConverter;
import org.geysermc.packconverter.converters.EnchantedItemGlintConverter;
import org.geysermc.packconverter.converters.FireworksConverter;
import org.geysermc.packconverter.converters.FishHookConverter;
import org.geysermc.packconverter.converters.FixWrongRootFolderConverter;
import org.geysermc.packconverter.converters.FoxConverter;
import org.geysermc.packconverter.converters.HorseConverter;
import org.geysermc.packconverter.converters.IconsConverter;
import org.geysermc.packconverter.converters.MapIconsConverter;
import org.geysermc.packconverter.converters.MetadataConverter;
import org.geysermc.packconverter.converters.NineSliceConverter;
import org.geysermc.packconverter.converters.OpaqueConverter;
import org.geysermc.packconverter.converters.OverlayToTranslateConverter;
import org.geysermc.packconverter.converters.Particles1_13Converter;
import org.geysermc.packconverter.converters.PistonArmConverter;
import org.geysermc.packconverter.converters.PlaceholderConverter;
import org.geysermc.packconverter.converters.PngToTgaConverter;
import org.geysermc.packconverter.converters.RedstoneDustConverter;
import org.geysermc.packconverter.converters.RenameConverter;
import org.geysermc.packconverter.converters.SheepConverter;
import org.geysermc.packconverter.converters.SideRotateConverter;
import org.geysermc.packconverter.converters.SpriteConverter;
import org.geysermc.packconverter.converters.TitleConverter;
import org.geysermc.packconverter.converters.TurtleConverter;
import org.geysermc.packconverter.converters.VillagerConverter;
import org.geysermc.packconverter.converters.WaterConverter;
import org.geysermc.packconverter.converters.WeatherConverter;

import java.util.ArrayList;
import java.util.List;

public class ConverterHandler {
    public static final List<Class<? extends AbstractConverter>> converterList = new ArrayList<>();

    public static boolean enableExperimental = false;

    static {
        converterList.add(FixWrongRootFolderConverter.class);
        converterList.add(MetadataConverter.class);
        converterList.add(RenameConverter.class);
        converterList.add(AtlasConverter.class);
        converterList.add(BannerPatternConverter.class);
        converterList.add(BedConverter.class);
        converterList.add(ChestNormalConverter.class);
        converterList.add(ChestLeftRightDoubleConverter.class);
        converterList.add(ChestFrontConverter.class);
        converterList.add(ChestSideConverter.class);
        converterList.add(DrownedConverter.class);
        converterList.add(DolphinConverter.class);
        converterList.add(FireworksConverter.class);
        converterList.add(FishHookConverter.class);
        converterList.add(FoxConverter.class);
        converterList.add(HorseConverter.class);
        converterList.add(IconsConverter.class);
        converterList.add(BannerPatternBlackConverter.class);
        converterList.add(MapIconsConverter.class);
        converterList.add(PistonArmConverter.class);
        converterList.add(RedstoneDustConverter.class);
        converterList.add(SheepConverter.class);
        converterList.add(VillagerConverter.class);
        converterList.add(TurtleConverter.class);
        converterList.add(WeatherConverter.class);
        converterList.add(OpaqueConverter.class);
        converterList.add(WaterConverter.class);
        converterList.add(BeeConverter.class);
        converterList.add(TitleConverter.class);
        converterList.add(DespriteConverter.class);
        if (enableExperimental) { converterList.add(DespriteExperimentalConverter.class); } // Experimental
        converterList.add(BarConverter.class);
        if (enableExperimental) { converterList.add(NineSliceConverter.class); } // Experimental
        //if (enableExperimental) { converterList.add(DialogConverter.class); } // Experimental TODO: Finish
        converterList.add(OverlayToTranslateConverter.class);
        converterList.add(ColorizeOverlayConverter.class);
        converterList.add(PlaceholderConverter.class);
        converterList.add(SideRotateConverter.class);
        //converterList.add(ArrowConverter.class); // This is disabled as its broken and the intended output it just the original
        converterList.add(Particles1_13Converter.class);
        converterList.add(SpriteConverter.class);
        converterList.add(DestroyStageConverter.class);
        converterList.add(EnchantedItemGlintConverter.class);
        converterList.add(BannerPatternPreviewMaxSizeConverter.class);
        converterList.add(PngToTgaConverter.class);
        converterList.add(CopyConverter.class);

        // Custom, not part of the original lib
        converterList.add(CustomModelDataConverter.class);

        converterList.add(DeleteConverter.class);
    }
}
