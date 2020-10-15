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

public class DrownedConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/zombie/drowned.png", "textures/entity/zombie/drowned_outer_layer.png", "textures/entity/zombie/drowned.png"});
    }

    public DrownedConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String overlay = (String) this.data[1];
            String to = (String) this.data[2];

            File fromFile = storage.resolve(from).toFile();
            File overlayFile = storage.resolve(overlay).toFile();

            if (!fromFile.exists() || !overlayFile.exists()) {
                return delete;
            }

            packConverter.log("Convert drowned");

            BufferedImage fromImage = ImageIO.read(fromFile);
            BufferedImage overlayImage = ImageIO.read(overlayFile);

            fromImage = ImageUtils.ensureMinWidth(fromImage, 64);

            int factor = fromImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage(fromImage.getWidth(), fromImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            g.drawImage(fromImage, 0, 0, null);

            g.drawImage(ImageUtils.crop(overlayImage, 0, 0, (32 * factor), (16 * factor)), (32 * factor), 0, null);

            g.drawImage(ImageUtils.crop(overlayImage, 0, (16 * factor), (64 * factor), (16 * factor)), 0, (32 * factor), null);

            g.drawImage(ImageUtils.crop(overlayImage, (16 * factor), (48 * factor), (16 * factor), (16 * factor)), 0, (48 * factor), null);

            g.drawImage(ImageUtils.crop(overlayImage, (32 * factor), (48 * factor), (16 * factor), (16 * factor)), (48 * factor), (48 * factor), null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {overlay}));
        } catch (IOException e) { }

        return delete;
    }
}
