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

package org.geysermc.packconverter.api;

import org.geysermc.packconverter.api.converters.*;

import java.util.ArrayList;
import java.util.List;

public class ConveterHandler {
    public static final List<Class<? extends AbstractConverter>> converterList = new ArrayList<>();

    static {
        converterList.add(MetadataConverter.class);
        converterList.add(RenameConverter.class);
        converterList.add(AtlasConverter.class);
        converterList.add(BannerPatternConverter.class);

        converterList.add(IconsConverter.class);
        converterList.add(MapIconsConverter.class);

        converterList.add(RedstoneDustConverter.class);

        converterList.add(WeatherConverter.class);
        converterList.add(OpaqueConverter.class);
        converterList.add(WaterConverter.class);

        converterList.add(TitleConverter.class);
        converterList.add(DespriteConverter.class);
        //converterList.add(DespriteExperimentalConverter.class); // Experimental
        converterList.add(BarConverter.class);
        //converterList.add(NineSliceConverter.class); // Experimental
        //converterList.add(DialogConverter.class); // Experimental
        converterList.add(OverlayToTranslateConverter.class);
        converterList.add(ColorizeOverlayConverter.class);

        converterList.add(PlaceholderConverter.class);

        converterList.add(PngToTgaConverter.class);
        converterList.add(CopyConverter.class);
        converterList.add(DeleteConverter.class);
    }
}
