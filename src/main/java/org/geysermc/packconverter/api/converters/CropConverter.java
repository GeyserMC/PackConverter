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
import org.geysermc.packconverter.api.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CropConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Conduit
        defaultData.add(new Object[] {"textures/blocks/conduit_base.png", 0.75f, 0.75f});
        defaultData.add(new Object[] {"textures/blocks/conduit_closed.png", 0.5f, 0.5f});
        defaultData.add(new Object[] {"textures/blocks/conduit_open.png", 0.5f, 0.5f});
    }

    public CropConverter(Path storage, Object[] data) {
        super(storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            float width = (float) this.data[1];
            float height = (float) this.data[2];

            File imgFile = storage.resolve(from).toFile();
            BufferedImage image = ImageIO.read(imgFile);

            int newWidth = Math.round(image.getWidth() * width);
            int newHeight = Math.round(image.getHeight() * height);

            ImageIO.write(ImageUtils.crop(image, newWidth, newHeight), "png", imgFile);

            System.out.println(String.format("Crop %s to %dx%d", from, newWidth, newHeight));
        } catch (IOException e) { e.printStackTrace(); }

        return new ArrayList<>();
    }
}
