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

public class ChestLeftRightDoubleConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/chest/normal_left.png", "textures/entity/chest/normal_right.png", "textures/entity/chest/double_normal.png"});
        defaultData.add(new Object[] {"textures/entity/chest/trapped_left.png", "textures/entity/chest/trapped_right.png", "textures/entity/chest/trapped_double.png"});
        defaultData.add(new Object[] {"textures/entity/chest/christmas_left.png", "textures/entity/chest/christmas_right.png", "textures/entity/chest/christmas_double.png"});
    }

    public ChestLeftRightDoubleConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String fromLeft = (String) this.data[0];
            String fromRight = (String) this.data[1];
            String to = (String) this.data[2];

            File leftFile = storage.resolve(fromLeft).toFile();
            File rightFile = storage.resolve(fromRight).toFile();

            if (!leftFile.exists() || !rightFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Convert double chest %s", to));

            BufferedImage leftImage = ImageIO.read(leftFile);
            BufferedImage rightImage = ImageIO.read(rightFile);

            leftImage = ImageUtils.ensureMinWidth(leftImage, 64);
            rightImage = ImageUtils.ensureMinWidth(rightImage, 64);

            int factor = leftImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage((128 * factor), (64 * factor), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, 0, (14 * factor), (14 * factor), (5 * factor)), 180), 0, (14 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (29 * factor), (14 * factor), (14 * factor), (5 * factor)), 180), (44 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, 0, (33 * factor), (14 * factor), (10 * factor)), 180), 0, (33 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (29 * factor), (33 * factor), (14 * factor), (10 * factor)), 180), (44 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(rightImage, (29 * factor), 0, (15 * factor), (14 * factor)), false, true), (14 * factor), 0, null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(leftImage, (29 * factor), 0, (15 * factor), (14 * factor)), false, true), (29 * factor), 0, null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, (43 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (14 * factor), (14 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (43 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (29 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(rightImage, (29 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (14 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(leftImage, (29 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (29 * factor), (19 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, (43 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (14 * factor), (33 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (43 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (29 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(rightImage, (14 * factor), 0, (15 * factor), (14 * factor)), false, true), (44 * factor), 0, null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(leftImage, (14 * factor), 0, (15 * factor), (14 * factor)), false, true), (59 * factor), 0, null);

            g.drawImage(ImageUtils.flip(ImageUtils.crop(rightImage, (14 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (44 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(leftImage, (14 * factor), (19 * factor), (15 * factor), (14 * factor)), false, true), (59 * factor), (19 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (73 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (58 * factor), (14 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, (14 * factor), (14 * factor), (15 * factor), (5 * factor)), 180), (73 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.rotate(ImageUtils.crop(leftImage, (14 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (58 * factor), (33 * factor), null);
            g.drawImage(ImageUtils.rotate(ImageUtils.crop(rightImage, (14 * factor), (33 * factor), (15 * factor), (10 * factor)), 180), (73 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.crop(leftImage, 0, 0, (6 * factor), (6 * factor)), 0, 0, null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {fromLeft}));
            delete.add(new DeleteConverter(packConverter, storage, new Object[] {fromRight}));
        } catch (IOException e) { }

        return delete;
    }
}
