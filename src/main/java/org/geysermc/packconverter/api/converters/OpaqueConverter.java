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

public class OpaqueConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Leaves
        defaultData.add(new Object[] {"textures/blocks/leaves_acacia.png", "textures/blocks/leaves_acacia_opaque.png"});
        defaultData.add(new Object[] {"textures/blocks/leaves_big_oak.png", "textures/blocks/leaves_big_oak_opaque.png"});
        defaultData.add(new Object[] {"textures/blocks/leaves_birch.png", "textures/blocks/leaves_birch_opaque.png"});
        defaultData.add(new Object[] {"textures/blocks/leaves_jungle.png", "textures/blocks/leaves_jungle_opaque.png"});
        defaultData.add(new Object[] {"textures/blocks/leaves_oak.png", "textures/blocks/leaves_oak_opaque.png"});
        defaultData.add(new Object[] {"textures/blocks/leaves_spruce.png", "textures/blocks/leaves_spruce_opaque.png"});
    }

    public OpaqueConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Create opaque %s", to));

            BufferedImage fromImage = ImageIO.read(fromFile);

            BufferedImage toImage = new BufferedImage(fromImage.getWidth(), fromImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g = toImage.getGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, toImage.getWidth(), toImage.getHeight());

            g.drawImage(fromImage, 0, 0, null);

            ImageUtils.write(toImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
