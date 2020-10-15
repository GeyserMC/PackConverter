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

public class PistonArmConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/blocks/piston_top_normal.png", "textures/blocks/piston_top_normal.png", "textures/blocks/piston_side.png", "textures/entity/pistonarm/pistonArm.png"});
        defaultData.add(new Object[] {"textures/blocks/piston_top_sticky.png", "textures/blocks/piston_top_normal.png", "textures/blocks/piston_side.png", "textures/entity/pistonarm/pistonArmSticky.png"});
    }

    public PistonArmConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String top1 = (String) this.data[0];
            String top2 = (String) this.data[1];
            String side = (String) this.data[2];
            String to = (String) this.data[3];

            File top1File = storage.resolve(top1).toFile();
            File top2File = storage.resolve(top2).toFile();
            File sideFile = storage.resolve(side).toFile();

            if (!top1File.exists() || !top2File.exists() || !sideFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Create piston arm %s", to));

            BufferedImage top1Image = ImageIO.read(top1File);
            BufferedImage top2Image = ImageIO.read(top2File);
            BufferedImage sideImage = ImageIO.read(sideFile);

            top1Image = ImageUtils.ensureMinWidth(top1Image, 16);
            top2Image = ImageUtils.ensureMinWidth(top2Image, 16);
            sideImage = ImageUtils.ensureMinWidth(sideImage, 16);

            int factor = top1Image.getWidth() / 16;

            BufferedImage newImage = new BufferedImage((128 * factor), (32 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            g.drawImage(top1Image, (16 * factor), 0, null);
            g.drawImage(top2Image, (32 * factor), 0, null);

            sideImage = ImageUtils.crop(sideImage, 0, 0, sideImage.getWidth(), (4 * factor));
            g.drawImage(sideImage, 0, (16 * factor), null);
            g.drawImage(sideImage, (16 * factor), (16 * factor), null);
            g.drawImage(sideImage, (32 * factor), (16 * factor), null);
            g.drawImage(sideImage, (48 * factor), (16 * factor), null);

            // Arm top
            BufferedImage side2Image = ImageUtils.rotate(ImageUtils.crop(sideImage, 0, 0, (8 * factor), (4 * factor)), -90);

            g.drawImage(side2Image, (64 * factor), (4 * factor), null);
            g.drawImage(side2Image, (68 * factor), (4 * factor), null);
            g.drawImage(side2Image, (72 * factor), (4 * factor), null);
            g.drawImage(side2Image, (76 * factor), (4 * factor), null);

            // Arm bottom top
            BufferedImage side3Image = ImageUtils.crop(side2Image, 0, (side2Image.getHeight() - factor), side2Image.getWidth(), factor);
            BufferedImage side4Image = ImageUtils.crop(sideImage, (7 * factor), 0, factor, sideImage.getHeight());
            BufferedImage side5Image = ImageUtils.crop(side2Image, 0, (2 * factor), side2Image.getWidth(), (4 * factor));

            g.drawImage(side3Image, (70 * factor), (18 * factor), null);
            g.drawImage(side3Image, (74 * factor), (18 * factor), null);
            g.drawImage(side3Image, (78 * factor), (18 * factor), null);

            g.drawImage(side4Image, (70 * factor), (19 * factor), null);
            g.drawImage(side4Image, (75 * factor), (19 * factor), null);
            g.drawImage(side4Image, (76 * factor), (19 * factor), null);
            g.drawImage(side4Image, (81 * factor), (19 * factor), null);

            g.drawImage(side3Image, (70 * factor), (23 * factor), null);
            g.drawImage(side3Image, (74 * factor), (23 * factor), null);
            g.drawImage(side3Image, (78 * factor), (23 * factor), null);

            // Arm bottom
            g.drawImage(side3Image, (64 * factor), (24 * factor), null);
            g.drawImage(side3Image, (68 * factor), (24 * factor), null);
            g.drawImage(side3Image, (72 * factor), (24 * factor), null);
            g.drawImage(side3Image, (76 * factor), (24 * factor), null);
            g.drawImage(side3Image, (80 * factor), (24 * factor), null);
            g.drawImage(side3Image, (84 * factor), (24 * factor), null);

            g.drawImage(side4Image, (64 * factor), (25 * factor), null);
            g.drawImage(side4Image, (64 * factor), (29 * factor), null);
            g.drawImage(side4Image, (69 * factor), (25 * factor), null);
            g.drawImage(side4Image, (69 * factor), (29 * factor), null);
            g.drawImage(side4Image, (70 * factor), (25 * factor), null);
            g.drawImage(side4Image, (70 * factor), (29 * factor), null);
            g.drawImage(side4Image, (75 * factor), (25 * factor), null);
            g.drawImage(side4Image, (75 * factor), (29 * factor), null);
            g.drawImage(side4Image, (76 * factor), (25 * factor), null);
            g.drawImage(side4Image, (76 * factor), (29 * factor), null);
            g.drawImage(side4Image, (81 * factor), (25 * factor), null);
            g.drawImage(side4Image, (81 * factor), (29 * factor), null);
            g.drawImage(side4Image, (82 * factor), (25 * factor), null);
            g.drawImage(side4Image, (82 * factor), (29 * factor), null);
            g.drawImage(side4Image, (87 * factor), (25 * factor), null);
            g.drawImage(side4Image, (87 * factor), (29 * factor), null);

            g.drawImage(side5Image, (65 * factor), (25 * factor), null);
            g.drawImage(side5Image, (65 * factor), (29 * factor), null);
            g.drawImage(side5Image, (71 * factor), (25 * factor), null);
            g.drawImage(side5Image, (71 * factor), (29 * factor), null);
            g.drawImage(side5Image, (77 * factor), (25 * factor), null);
            g.drawImage(side5Image, (77 * factor), (29 * factor), null);
            g.drawImage(side5Image, (83 * factor), (25 * factor), null);
            g.drawImage(side5Image, (83 * factor), (29 * factor), null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return delete;
    }
}
