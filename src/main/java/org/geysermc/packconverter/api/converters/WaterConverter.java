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

public class WaterConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/blocks/lava_flow.png", "textures/blocks/lava_flow.png", 32});
        defaultData.add(new Object[] {"textures/blocks/lava_still.png", "textures/blocks/lava_still.png", 16});
        defaultData.add(new Object[] {"textures/blocks/water_flow_grey.png", "textures/blocks/water_flow_grey.png", 32, true});
        defaultData.add(new Object[] {"textures/blocks/water_still_grey.png", "textures/blocks/water_still_grey.png", 16, true});
    }

    public WaterConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];
            int minWidth = (int) this.data[2];
            boolean grayscale = this.data.length > 3 ? (boolean) this.data[3] : false;

            File waterFile = storage.resolve(from).toFile();

            if (!waterFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Convert water %s", from));

            BufferedImage waterImage = ImageIO.read(waterFile);

            if (grayscale) {
                waterImage = ImageUtils.grayscale(waterImage);
            }

            waterImage = ImageUtils.ensureMinWidth(waterImage, minWidth);

            ImageUtils.write(waterImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
