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

public class ChestNormalConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/chest/normal.png"});
        defaultData.add(new Object[] {"textures/entity/chest/trapped.png"});
        defaultData.add(new Object[] {"textures/entity/chest/ender.png"});
        defaultData.add(new Object[] {"textures/entity/chest/christmas.png"});
    }

    public ChestNormalConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String chest = (String) this.data[0];

            File chestFile = storage.resolve(chest).toFile();

            if (!chestFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Convert normal chest %s", chest));

            BufferedImage chestImage = ImageIO.read(chestFile);

            chestImage = ImageUtils.ensureMinWidth(chestImage, 64);

            int factor = chestImage.getWidth() / 64;

            BufferedImage newChestImage = new BufferedImage((64 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newChestImage.getGraphics();

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, 0, (14 * factor), (14 * factor), (5 * factor)), 180), 0, (14 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, 0, (33 * factor), (14 * factor), (10 * factor)), 180), 0, (33 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(chestImage, (28 * factor), 0, (14 * factor), (14 * factor)), false, true), (14 * factor), 0, null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (42 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (14 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(chestImage, (28 * factor), (19 * factor), (14 * factor), (14 * factor)),false, true), (14 * factor), (19 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (42 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (14 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(chestImage, (14 * factor), 0, (14 * factor), (14 * factor)),false, true), (28 * factor), 0, null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (28 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (28 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(chestImage, (14 * factor), (19 * factor), (14 * factor), (14 * factor)),false, true), (28 * factor), (19 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (28 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (28 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (14 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (42 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(chestImage, (14 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (42 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.crop(chestImage, 0, 0, (6 * factor), (6 * factor)), 0, 0, null);

            ImageUtils.write(newChestImage, "png", chestFile);
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
