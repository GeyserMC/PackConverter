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

public class BannerPatternBlackConverter extends AbstractConverter {

    @Getter
    private static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/banner/border.png",
                "textures/entity/banner/bricks.png",
                "textures/entity/banner/circle.png",
                "textures/entity/banner/creeper.png",
                "textures/entity/banner/cross.png",
                "textures/entity/banner/curly_border.png",
                "textures/entity/banner/diagonal_left.png",
                "textures/entity/banner/diagonal_right.png",
                "textures/entity/banner/diagonal_up_left.png",
                "textures/entity/banner/diagonal_up_right.png",
                "textures/entity/banner/flower.png",
                "textures/entity/banner/gradient.png",
                "textures/entity/banner/gradient_up.png",
                "textures/entity/banner/half_horizontal.png",
                "textures/entity/banner/half_horizontal_bottom.png",
                "textures/entity/banner/half_vertical.png",
                "textures/entity/banner/half_vertical_right.png",
                "textures/entity/banner/mojang.png",
                "textures/entity/banner/piglin.png",
                "textures/entity/banner/rhombus.png",
                "textures/entity/banner/skull.png",
                "textures/entity/banner/small_stripes.png",
                "textures/entity/banner/square_bottom_left.png",
                "textures/entity/banner/square_bottom_right.png",
                "textures/entity/banner/square_top_left.png",
                "textures/entity/banner/square_top_right.png",
                "textures/entity/banner/straight_cross.png",
                "textures/entity/banner/stripe_bottom.png",
                "textures/entity/banner/stripe_center.png",
                "textures/entity/banner/stripe_downleft.png",
                "textures/entity/banner/stripe_downright.png",
                "textures/entity/banner/stripe_left.png",
                "textures/entity/banner/stripe_middle.png",
                "textures/entity/banner/stripe_right.png",
                "textures/entity/banner/stripe_top.png",
                "textures/entity/banner/triangle_bottom.png",
                "textures/entity/banner/triangle_top.png",
                "textures/entity/banner/triangles_bottom.png",
                "textures/entity/banner/triangles_top.png"});
    }

    public BannerPatternBlackConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];

            File patternFile = storage.resolve(from).toFile();
            if (!patternFile.exists()) {
                return new ArrayList<>();
            }

            BufferedImage patternImage = ImageIO.read(patternFile);

            for (int x = 0; x < patternImage.getWidth(); x++) {
                for (int y = 0; y < patternImage.getHeight(); y++) {
                    Color c = new Color(patternImage.getRGB(x, y), true);
                    if (c.getRGB() == 0 && c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0 && c.getAlpha() == 255) {
                        patternImage.setRGB(x, y, Color.TRANSLUCENT);
                    }
                }
            }

            ImageUtils.write(patternImage, "png", patternFile);

            System.out.print(String.format("Fix banner pattern black %s", from));
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
