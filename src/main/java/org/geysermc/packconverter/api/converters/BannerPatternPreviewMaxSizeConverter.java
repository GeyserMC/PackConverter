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

public class BannerPatternPreviewMaxSizeConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/banner_base.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/border.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/bricks.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/circle.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/creeper.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/cross.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/curly_border.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_left.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_up_left.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/diagonal_up_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/flower.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/gradient.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/gradient_up.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/half_horizontal.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/half_horizontal_bottom.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/half_vertical.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/half_vertical_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/mojang.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/piglin.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/rhombus.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/skull.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/small_stripes.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/square_bottom_left.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/square_bottom_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/square_top_left.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/square_top_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/straight_cross.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_bottom.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_center.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_downleft.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_downright.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_left.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_middle.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_right.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/stripe_top.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/triangle_bottom.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/triangle_top.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/triangles_bottom.png", 64});
        defaultData.add(new Object[] {"textures/entity/banner/triangles_top.png", 64});
    }

    public BannerPatternPreviewMaxSizeConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            Integer max_width = (Integer) this.data[1];

            File patternFile = storage.resolve(from).toFile();
            if (!patternFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Fix banner pattern preview max size %s", from));

            BufferedImage patternImage = ImageIO.read(patternFile);

            patternImage = ImageUtils.ensureMaxWidth(patternImage, max_width);

            ImageUtils.write(patternImage, "png", patternFile);
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
