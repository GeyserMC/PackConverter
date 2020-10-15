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

public class DestroyStageConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/environment/destroy_stage_0.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_1.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_2.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_3.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_4.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_5.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_6.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_7.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_8.png"});
        defaultData.add(new Object[] {"textures/environment/destroy_stage_9.png"});
    }

    public DestroyStageConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Convert destroy stage %s", from));

            BufferedImage fromImage = ImageIO.read(fromFile);

            Color blank = new Color(255, 255, 255, 0);
            for (int x = 0; x < fromImage.getWidth(); x++) {
                for (int y = 0; y < fromImage.getHeight(); y++) {
                    Color c = new Color(fromImage.getRGB(x, y), true);
                    if (c.getAlpha() == 0) {
                        fromImage.setRGB(x, y, blank.getRGB());
                    }
                }
            }

            ImageUtils.write(fromImage, "png", fromFile);
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
