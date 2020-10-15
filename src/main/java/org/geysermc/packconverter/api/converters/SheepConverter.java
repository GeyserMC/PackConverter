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

public class SheepConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/sheep/sheep.png", "textures/entity/sheep/sheep_fur.png"});
    }

    public SheepConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String sheep = (String) this.data[0];
            String sheepFur = (String) this.data[1];

            File sheepFile = storage.resolve(sheep).toFile();
            File sheepFurFile = storage.resolve(sheepFur).toFile();

            if (!sheepFile.exists() || !sheepFurFile.exists()) {
                return delete;
            }

            packConverter.log("Convert sheep");

            BufferedImage sheepImage = ImageIO.read(sheepFile);
            BufferedImage sheepFurImage = ImageIO.read(sheepFurFile);

            int width = Math.max(sheepImage.getWidth(), sheepFurImage.getWidth());
            sheepImage = ImageUtils.ensureMinWidth(sheepImage, width);
            sheepFurImage = ImageUtils.ensureMinWidth(sheepFurImage, width);

            BufferedImage newImage = new BufferedImage(sheepImage.getWidth(), sheepImage.getHeight() + sheepFurImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            g.drawImage(sheepImage, 0, 0, null);

            g.drawImage(sheepFurImage, 0, sheepImage.getHeight(), null);

            for (int x = 0; x < newImage.getWidth(); x++) {
                for (int y = 0; y < sheepImage.getHeight(); y++) {
                    Color c = new Color(newImage.getRGB(x, y), true);
                    if (c.getAlpha() == 255) {
                        Color tmpCol = new Color(c.getRed(), c.getGreen(), c.getBlue(), 1);
                        newImage.setRGB(x, y, tmpCol.getRGB());
                    }
                }
            }

            ImageUtils.write(newImage, "png", sheepFile);

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {sheepFur}));
        } catch (IOException e) { }

        return delete;
    }
}
