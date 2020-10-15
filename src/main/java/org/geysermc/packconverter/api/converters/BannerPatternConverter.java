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

public class BannerPatternConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/banner_base.png", new Object[]{
                // https://www.planetminecraft.com/banner/pillager-banner-199281/
                // Colors from original bedrock texture
                new Object[] {"textures/entity/banner/base.png", new Color(255, 255, 255)},
                new Object[] {"textures/entity/banner/rhombus.png", new Color(76, 127, 153)},
                new Object[] {"textures/entity/banner/stripe_bottom.png", new Color(146, 146, 146)},
                new Object[] {"textures/entity/banner/stripe_center.png", new Color(79, 79, 79)},
                new Object[] {"textures/entity/banner/stripe_middle.png", new Color(0, 0, 0)},
                new Object[] {"textures/entity/banner/half_horizontal.png", new Color(146, 146, 146)},
                new Object[] {"textures/entity/banner/circle.png", new Color(146, 146, 146)},
                new Object[] {"textures/entity/banner/border.png", new Color(0, 0, 0)}
            }, "textures/entity/banner/banner_pattern_illager.png"});
    }

    public BannerPatternConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String base = (String) this.data[0];
            Object[] patterns = (Object[]) this.data[1];
            String to = (String) this.data[2];
            
            BufferedImage bannerImage = null;

            for (Object pattern : patterns) {
                Object[] patternArr = (Object[]) pattern;
                String path = (String) patternArr[0];
                Color color = (Color) patternArr[1];

                File patternFile = storage.resolve(path).toFile();
                if (!patternFile.exists()) {
                    continue;
                }

                BufferedImage patternImage = ImageIO.read(patternFile);
                
                if (bannerImage == null) {
                    packConverter.log(String.format("Convert pattern banner %s", to));

                    bannerImage = ImageIO.read(storage.resolve(base).toFile());

                    int factor = bannerImage.getWidth() / 64;

                    Graphics g = bannerImage.getGraphics();
                    g.drawImage(ImageUtils.crop(bannerImage, (44 * factor), 0, (8 * factor), (44 * factor)), (52 * factor), 0, null);
                    g.drawImage(ImageUtils.crop(bannerImage, (44 * factor), (5 * factor), (8 * factor), (20 * factor)), (52 * factor), (44 * factor), null);
                }

                for (int x = 0; x < bannerImage.getWidth(); x++) {
                    for (int y = 0; y < bannerImage.getHeight(); y++) {
                        Color c = new Color(patternImage.getRGB(x, y), true);
                        if (c.getRed() > 0 && c.getAlpha() > 0) {
                            bannerImage.setRGB(x, y, color.getRGB());
                        }
                    }
                }
            }

            if (bannerImage != null) {
                ImageUtils.write(bannerImage, "png", storage.resolve(to).toFile());
            }
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
