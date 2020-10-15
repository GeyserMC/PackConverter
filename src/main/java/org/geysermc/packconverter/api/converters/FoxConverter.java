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

public class FoxConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/fox/fox.png", "textures/entity/fox/fox_sleep.png", "textures/entity/fox/fox.png"});
        defaultData.add(new Object[] {"textures/entity/fox/arctic_fox.png", "textures/entity/fox/arctic_fox_sleep.png", "textures/entity/fox/arctic_fox.png"});
    }

    public FoxConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String fromSleep = (String) this.data[1];
            String to = (String) this.data[2];

            File fromFile = storage.resolve(from).toFile();
            File fromSleepFile = storage.resolve(fromSleep).toFile();

            if (!fromFile.exists() || !fromSleepFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Convert fox %s", to));

            BufferedImage fromImage = ImageIO.read(fromFile);
            BufferedImage fromSleepImage = ImageIO.read(fromSleepFile);

            int factor = fromImage.getWidth() / 48;

            BufferedImage newImage = new BufferedImage((64 * factor), (32 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            // Ears
            g.drawImage(ImageUtils.crop(fromImage, (8 * factor), factor, (6 * factor), (3 * factor)), 0, 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (15 * factor), factor, (6 * factor), (3 * factor)), (22 * factor), 0, null);

            // Head normal
            g.drawImage(ImageUtils.crop(fromImage, factor, (5 * factor), (28 * factor), (12 * factor)), 0, 0, null);

            // Head sleep
            g.drawImage(ImageUtils.crop(fromSleepImage, factor, (5 * factor), (28 * factor), (12 * factor)), 0, (12 * factor), null);

            // Mount
            g.drawImage(ImageUtils.crop(fromImage, (6 * factor), (18 * factor), (14 * factor), (5 * factor)), 0, (24 * factor), null);

            // Body
            g.drawImage(ImageUtils.crop(fromImage, (24 * factor), (21 * factor), (6 * factor), (11 * factor)), (30 * factor), (21 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (30 * factor), (15 * factor), (18 * factor), (17 * factor)), (36 * factor), (15 * factor), null);

            // Tail
            g.drawImage(ImageUtils.crop(fromImage, (30 * factor), 0, (18 * factor), (14 * factor)), (28 * factor), 0, null);

            // Legs
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (24 * factor), (8 * factor), (8 * factor)), (14 * factor), (24 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (24 * factor), (8 * factor), (8 * factor)), (22 * factor), (24 * factor), null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return delete;
    }
}
