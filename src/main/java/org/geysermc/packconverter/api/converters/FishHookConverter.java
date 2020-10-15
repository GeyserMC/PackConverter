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

public class FishHookConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/fishhook.png", "textures/entity/fishhook.png"});
    }

    public FishHookConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return delete;
            }

            packConverter.log("Convert fishhook");

            BufferedImage fromImage = ImageIO.read(fromFile);

            int factor = fromImage.getWidth() / 8;

            BufferedImage newImage = new BufferedImage((24 * factor), (3 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, factor, factor), 0, 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, factor, factor), (2 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, factor, factor), (2 * factor), (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, factor, factor), 0, (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), factor, factor, factor), factor, 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), factor, factor, factor), (2 * factor), factor, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), factor, factor, factor), factor, (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), factor, factor, factor), 0, factor, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (4 * factor), factor, factor), factor, factor, null);

            g.drawImage(ImageUtils.crop(fromImage, (5 * factor), (3 * factor), factor, factor), (3 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (5 * factor), (3 * factor), factor, factor), (5 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (5 * factor), (3 * factor), factor, factor), (5 * factor), (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (5 * factor), (3 * factor), factor, factor), (3 * factor), (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (3 * factor), factor, factor), (4 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (3 * factor), factor, factor), (5 * factor), factor, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (3 * factor), factor, factor), (4 * factor), (2 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (3 * factor), factor, factor), (3 * factor), factor, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (4 * factor), factor, factor), (4 * factor), factor, null);

            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, (3 * factor), (3 * factor)), (6 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, (3 * factor), (3 * factor)), (9 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, (3 * factor), (3 * factor)), (12 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (3 * factor), factor, (3 * factor), (3 * factor)), (15 * factor), 0, null);

            g.drawImage(ImageUtils.crop(fromImage, (2 * factor), (5 * factor), (3 * factor), (3 * factor)), (18 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (4 * factor), (4 * factor), factor, factor), (22 * factor), (2 * factor), null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return delete;
    }
}
