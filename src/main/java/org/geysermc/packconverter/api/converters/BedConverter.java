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

public class BedConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/bed/black.png"});
        defaultData.add(new Object[] {"textures/entity/bed/blue.png"});
        defaultData.add(new Object[] {"textures/entity/bed/brown.png"});
        defaultData.add(new Object[] {"textures/entity/bed/cyan.png"});
        defaultData.add(new Object[] {"textures/entity/bed/gray.png"});
        defaultData.add(new Object[] {"textures/entity/bed/green.png"});
        defaultData.add(new Object[] {"textures/entity/bed/light_blue.png"});
        defaultData.add(new Object[] {"textures/entity/bed/lime.png"});
        defaultData.add(new Object[] {"textures/entity/bed/magenta.png"});
        defaultData.add(new Object[] {"textures/entity/bed/orange.png"});
        defaultData.add(new Object[] {"textures/entity/bed/pink.png"});
        defaultData.add(new Object[] {"textures/entity/bed/purple.png"});
        defaultData.add(new Object[] {"textures/entity/bed/red.png"});
        defaultData.add(new Object[] {"textures/entity/bed/silver.png"});
        defaultData.add(new Object[] {"textures/entity/bed/white.png"});
        defaultData.add(new Object[] {"textures/entity/bed/yellow.png"});
    }

    public BedConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String bed = (String) this.data[0];

            File bedFile = storage.resolve(bed).toFile();

            if (!bedFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Convert bed %s", bed));

            BufferedImage bedImage = ImageIO.read(bedFile);

            bedImage = ImageUtils.ensureMinWidth(bedImage, 64);

            int factor = bedImage.getWidth() / 64;

            BufferedImage newBedImage = new BufferedImage(bedImage.getWidth(), bedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newBedImage.getGraphics();

            // Top part
            g.drawImage(ImageUtils.crop(bedImage, 0, 0, (44 * factor), (22 * factor)), 0, 0, null);

            // Bottom part
            g.drawImage(ImageUtils.crop(bedImage, 0, (28 * factor), (44 * factor), (16 * factor)), 0, (22 * factor), null);

            // Bottom side
            g.drawImage(ImageUtils.crop(bedImage, (22 * factor), (22 * factor), (16 * factor), (6 * factor)), (22 * factor), 0, null);

            // Feeds
            List<int[]> feedsList = new ArrayList<>();
            feedsList.add(new int[] {50, 0, 0, 44, 0});
            feedsList.add(new int[] {50, 6, 0, 38, 90});
            feedsList.add(new int[] {50, 12, 12, 44, -90});
            feedsList.add(new int[] {50, 18, 12, 38, 180});

            for (int[] values : feedsList) {
                int from_x = values[0];
                int from_y = values[1];
                int to_x = values[2];
                int to_y = values[3];
                int rotate_bottom = values[4];

                g.drawImage(ImageUtils.crop(bedImage, ((from_x + 3) * factor), (from_y * factor), (3 * factor), (3 * factor)), ((to_x + 3) * factor), ((to_y + 3) * factor), null);
                g.drawImage(ImageUtils.rotate(ImageUtils.crop(bedImage, ((from_x + 6) * factor), (from_y * factor), (3 * factor), (3 * factor)), rotate_bottom), ((to_x + 9) * factor), ((to_y + 3) * factor), null);
                g.drawImage(ImageUtils.rotate(ImageUtils.crop(bedImage, (from_x * factor), ((from_y + 3) * factor), (3 * factor), (3 * factor)), -90), (to_x * factor), ((to_y + 3) * factor), null);
                g.drawImage(ImageUtils.rotate(ImageUtils.crop(bedImage, ((from_x + 3) * factor), ((from_y + 3) * factor), (3 * factor), (3 * factor)), 180), ((to_x + 6) * factor), (to_y * factor), null);
                g.drawImage(ImageUtils.rotate(ImageUtils.crop(bedImage, ((from_x + 6) * factor), ((from_y + 3) * factor), (3 * factor), (3 * factor)), 90), ((to_x + 6) * factor), ((to_y + 3) * factor), null);
                g.drawImage(ImageUtils.rotate(ImageUtils.crop(bedImage, ((from_x + 9) * factor), ((from_y + 3) * factor), (3 * factor), (3 * factor)), 180), ((to_x + 3) * factor), (to_y * factor), null);
            }

            ImageUtils.write(newBedImage, "png", bedFile);
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
