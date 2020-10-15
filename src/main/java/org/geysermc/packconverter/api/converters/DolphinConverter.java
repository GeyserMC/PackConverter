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

public class DolphinConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/dolphin.png"});
    }

    public DolphinConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return delete;
            }

            packConverter.log("Convert dolphin");

            BufferedImage fromImage = ImageIO.read(fromFile);

            fromImage = ImageUtils.ensureMinWidth(fromImage, 64);

            int factor = fromImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage((64 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            // Nose
            g.drawImage(ImageUtils.crop(fromImage, 0, (13 * factor), (12 * factor), (6 * factor)), 0, (13 * factor), null);

            // Head
            g.drawImage(ImageUtils.crop(fromImage, 0, 0, (28 * factor), (13 * factor)), 0, 0, null);

            // Body
            g.drawImage(ImageUtils.crop(fromImage, (35 * factor), 0, (16 * factor), (13 * factor)), (13 * factor), (13 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (22 * factor), (13 * factor), (42 * factor), (7 * factor)), 0, (26 * factor), null);

            // Tail 1
            g.drawImage(ImageUtils.crop(fromImage, (11 * factor), (19 * factor), (8 * factor), (11 * factor)), (11 * factor), (33 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage,0, (30 * factor), (30 * factor), (5 * factor)), 0, (44 * factor), null);

            // Tail 2
            g.drawImage(ImageUtils.crop(fromImage, (19 * factor), (20 * factor), (32 * factor), (7 * factor)), 0, (49 * factor), null);

            // Top
            g.drawImage(ImageUtils.crop(fromImage, (62 * factor), (5 * factor), factor, (4 * factor)), (33 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (56 * factor), (5 * factor), factor, (4 * factor)), (34 * factor), 0, null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (51 * factor), (5 * factor), (5 * factor), (4 * factor)),-90), (29 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (56 * factor), 0, factor, (5 * factor)), (33 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (57 * factor), (5 * factor), (5 * factor), (4 * factor)),90), (34 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (57 * factor), 0, factor, (5 * factor)), (38 * factor), (4 * factor), null);

            // Right
            g.drawImage(ImageUtils.crop(fromImage, (56 * factor), (27 * factor), (8 * factor), (4 * factor)), (44 * factor), 0, null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(fromImage, (48 * factor), (27 * factor), (8 * factor), (4 * factor)), true, false), (52 * factor), 0, null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (55 * factor), (20 * factor), factor, (7 * factor)),90), (40 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (55 * factor), (20 * factor), factor, (5 * factor)),90), (47 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (56 * factor), (20 * factor), factor, (7 * factor)),90), (52 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (56 * factor), (20 * factor), factor, (5 * factor)),90), (59 * factor), (4 * factor), null);

            // Left
            g.drawImage(ImageUtils.flip(ImageUtils.crop(fromImage, (56 * factor), (27 * factor), (8 * factor), (4 * factor)), true, false), (44 * factor), (6 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (27 * factor), (8 * factor), (4 * factor)), (52 * factor), (6 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (55 * factor), (20 * factor), factor, (7 * factor)),90), (40 * factor), (10 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (55 * factor), (20 * factor), factor, (5 * factor)),90), (47 * factor), (10 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (56 * factor), (20 * factor), factor, (7 * factor)),90), (52 * factor), (10 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(fromImage, (56 * factor), (20 * factor), factor, (5 * factor)),90), (59 * factor), (10 * factor), null);

            ImageUtils.write(newImage, "png", fromFile);
        } catch (IOException e) { }

        return delete;
    }
}
