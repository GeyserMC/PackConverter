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

public class RedstoneDustConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/blocks/redstone_dust_dot.png", "textures/blocks/redstone_dust_line0.png", "textures/blocks/redstone_dust_line1.png", "textures/blocks/redstone_dust_cross.png", "textures/blocks/redstone_dust_line.png"});
    }

    public RedstoneDustConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String dot = (String) this.data[0];
            String line0 = (String) this.data[1];
            String line1 = (String) this.data[2];
            String to_cross = (String) this.data[3];
            String to_line = (String) this.data[4];

            File dotFile = storage.resolve(dot).toFile();
            File line0File = storage.resolve(line0).toFile();
            File line1File = storage.resolve(line1).toFile();

            if (!dotFile.exists() || !line0File.exists() || !line1File.exists()) {
                return delete;
            }

            packConverter.log("Convert redstone dust");

            BufferedImage newImage = ImageIO.read(line0File);
            newImage = ImageUtils.rotate(newImage, 90);
            ImageUtils.write(newImage, "png", storage.resolve(to_line).toFile());

            BufferedImage line1Image = ImageIO.read(line1File);
            if (ImageUtils.isEmptyArea(line1Image, 0, 0, line1Image.getWidth(), (line1Image.getHeight() / 16))) {
                line1Image = ImageUtils.rotate(line1Image, 90);
            }
            newImage.getGraphics().drawImage(line1Image, 0, 0, null);


            BufferedImage dotImage = ImageIO.read(dotFile);
            newImage.getGraphics().drawImage(dotImage, 0, 0, null);

            ImageUtils.write(newImage, "png", storage.resolve(to_cross).toFile());

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {dot}));
            delete.add(new DeleteConverter(packConverter, storage, new Object[] {line0}));
            delete.add(new DeleteConverter(packConverter, storage, new Object[] {line1}));
        } catch (IOException e) { }

        return delete;
    }
}
