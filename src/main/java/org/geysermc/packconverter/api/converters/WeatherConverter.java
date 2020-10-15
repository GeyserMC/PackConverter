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

public class WeatherConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Bed
        defaultData.add(new Object[] {"textures/environment/snow.png", "textures/environment/rain.png", "textures/environment/weather.png"});
    }

    public WeatherConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String snow = (String) this.data[0];
            String rain = (String) this.data[1];
            String to = (String) this.data[2];

            File snowFile = storage.resolve(snow).toFile();
            File rainFile = storage.resolve(rain).toFile();

            if (!snowFile.exists() || !rainFile.exists()) {
                return delete;
            }

            packConverter.log("Convert weather");

            BufferedImage snowImage = ImageIO.read(snowFile);
            BufferedImage rainImage = ImageIO.read(rainFile);

            int factor = snowImage.getWidth() / 64;

            BufferedImage weatherImage = new BufferedImage((32 * factor), (32 * factor), BufferedImage.TYPE_INT_ARGB);

            Graphics g = weatherImage.getGraphics();

            // Snow
            g.drawImage(ImageUtils.cover(ImageUtils.crop(snowImage, snowImage.getWidth(), (3 * factor)), weatherImage.getWidth(), (3 * factor)), 0, 0, null);

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {snow}));

            // Rain
            g.drawImage(ImageUtils.cover(ImageUtils.crop(rainImage, rainImage.getWidth(), (5 * factor)), weatherImage.getWidth(), (5 * factor)), 0, (5 * factor), null);

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {rain}));

            ImageUtils.write(weatherImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
