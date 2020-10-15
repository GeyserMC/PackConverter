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

public class OverlayToTranslateConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        // Cat
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/allblackcat.png", "textures/entity/cat/allblackcat_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/britishshorthair.png", "textures/entity/cat/britishshorthair_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/calico.png", "textures/entity/cat/calico_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/jellie.png", "textures/entity/cat/jellie_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/ocelot.png", "textures/entity/cat/ocelot_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/persian.png", "textures/entity/cat/persian_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/ragdoll.png", "textures/entity/cat/ragdoll_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/redtabby.png", "textures/entity/cat/redtabby_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/siamesecat.png", "textures/entity/cat/siamesecat_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/tabby.png", "textures/entity/cat/tabby_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/tuxedo.png", "textures/entity/cat/tuxedo_tame.png", false, true});
        defaultData.add(new Object[] {"textures/entity/cat/graytabby_tame.png", "textures/entity/cat/white.png", "textures/entity/cat/white_tame.png", false, true});

        // Enderman
        defaultData.add(new Object[] {"textures/entity/enderman/enderman.png", "textures/entity/enderman/enderman_eyes.png", "textures/entity/enderman/enderman.png", true});

        // Firework
        defaultData.add(new Object[] {"textures/items/fireworks_charge.png", "textures/items/firework_star.png", "textures/items/fireworks_charge.png", false});

        // Grass
        defaultData.add(new Object[] {"textures/blocks/grass_side.png", "textures/blocks/grass_side_carried.png", "textures/blocks/grass_side.png", false, true});

        // Leather
        defaultData.add(new Object[] {"textures/items/leather_boots.png", "textures/items/leather_boots_overlay.png", "textures/items/leather_boots.png", true});
        defaultData.add(new Object[] {"textures/items/leather_chestplate.png", "textures/items/leather_chestplate_overlay.png", "textures/items/leather_chestplate.png", true});
        defaultData.add(new Object[] {"textures/items/leather_helmet.png", "textures/items/leather_helmet_overlay.png", "textures/items/leather_helmet.png", true});
        defaultData.add(new Object[] {"textures/items/leather_leggings.png", "textures/items/leather_leggings_overlay.png", "textures/items/leather_leggings.png", true});
        defaultData.add(new Object[] {"textures/models/armor/leather_1.png", "textures/models/armor/leather_1_overlay.png", "textures/models/armor/leather_1.png", true});
        defaultData.add(new Object[] {"textures/models/armor/leather_2.png", "textures/models/armor/leather_2_overlay.png", "textures/models/armor/leather_2.png", true});

        // Phantom
        defaultData.add(new Object[] {"textures/entity/phantom.png", "textures/entity/phantom_eyes.png", "textures/entity/phantom.png", true});

        // Spider
        defaultData.add(new Object[] {"textures/entity/spider/cave_spider.png", "textures/entity/spider_eyes.png", "textures/entity/spider/cave_spider.png", true, true});
        defaultData.add(new Object[] {"textures/entity/spider/spider.png", "textures/entity/spider_eyes.png", "textures/entity/spider/spider.png", true});

        // Wolf
        defaultData.add(new Object[] {"textures/entity/wolf/wolf_collar.png", "textures/entity/wolf/wolf_tame.png", "textures/entity/wolf/wolf_tame.png", false, true});
    }

    public OverlayToTranslateConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String overlay = (String) this.data[1];
            String to = (String) this.data[2];
            boolean reverse = (boolean) this.data[3];
            boolean dontDelete = this.data.length > 4 && (boolean) this.data[4];

            File fromFile = storage.resolve(from).toFile();
            File overlayFile = storage.resolve(overlay).toFile();

            if (!fromFile.exists() || !overlayFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Create translated overlay %s", to));

            BufferedImage image = ImageIO.read(fromFile);
            BufferedImage imageOverlay = ImageIO.read(overlayFile);

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color c = new Color(image.getRGB(x, y), true);
                    if (reverse ? c.getAlpha() > 0 : c.getAlpha() < 255) {
                        Color newCol = new Color(imageOverlay.getRGB(x, y), true);
                        newCol = new Color(newCol.getRed(), newCol.getGreen(), newCol.getBlue(), 2);

                        image.setRGB(x, y, ImageUtils.colorToARGB(newCol));
                    }
                }
            }

            ImageUtils.write(image, "png", storage.resolve(to).toFile());

            if (!dontDelete) {
                delete.add(new DeleteConverter(packConverter, storage, new Object[] {overlay}));
            }
        } catch (IOException e) { }

        return delete;
    }
}
