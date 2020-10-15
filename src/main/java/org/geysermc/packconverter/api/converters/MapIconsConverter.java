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

public class MapIconsConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/map/map_icons.png", "textures/map/map_icons.png"});
    }

    public MapIconsConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            File iconsFile = storage.resolve(from).toFile();

            if (!iconsFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Convert map icons %s", to));

            BufferedImage iconsImage = ImageIO.read(iconsFile);

            int factor = iconsImage.getWidth() / 128;

            BufferedImage newIconsImage = new BufferedImage((64 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newIconsImage.getGraphics();

            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, 0, 0, (8 * factor), (8 * factor)), 2f), -factor, factor, null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (8 * factor), 0, (8 * factor), (8 * factor)), 2f), (15 * factor), factor, null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (16 * factor), 0, (8 * factor), (8 * factor)), 2f), (31 * factor), factor, null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (24 * factor), 0, (8 * factor), (8 * factor)), 2f), (47 * factor), factor, null);

            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (80 * factor), (8 * factor), (8 * factor), (8 * factor)), 2f), 0, (16 * factor), null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (40 * factor), 0, (8 * factor), (8 * factor)), 2f), (15 * factor), (16 * factor), null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (48 * factor), 0, (8 * factor), (8 * factor)), 2f), (32 * factor), (16 * factor), null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (48 * factor), (8 * factor), (8 * factor), (8 * factor)), 2f), (48 * factor), (16 * factor), null); // Alternative icon

            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (96 * factor), 0, (8 * factor), (8 * factor)), 2f), 0, (32 * factor), null); // Alternative icon
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (88 * factor), 0, (8 * factor), (8 * factor)), 2f), (16 * factor), (32 * factor), null); // Alternative icon
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (112 * factor), 0, (8 * factor), (8 * factor)), 2f), (32 * factor), (32 * factor), null); // Alternative icon
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (24 * factor), (8 * factor), (8 * factor), (8 * factor)), 2f), (48 * factor), (32 * factor), null); // Alternative icon

            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (120 * factor), 0, (8 * factor), (8 * factor)), 2f), 0, (48 * factor), null); // Alternative icon
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (56 * factor), 0, (8 * factor), (8 * factor)), 2f), (15 * factor), (48 * factor), null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (64 * factor), 0, (8 * factor), (8 * factor)), 2f), (32 * factor), (48 * factor), null);
            g.drawImage(ImageUtils.scale(ImageUtils.crop(iconsImage, (72 * factor), 0, (8 * factor), (8 * factor)), 2f), (48 * factor), (48 * factor), null);

            ImageUtils.write(newIconsImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
