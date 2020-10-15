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

public class VillagerConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/villager2/professions/armorer.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/butcher.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/cartographer.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/cleric.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/farmer.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/fisherman.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/fletcher.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/leatherworker.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/librarian.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/nitwit.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/shepherd.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/stonemason.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/toolsmith.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/unskilled.png"});
        defaultData.add(new Object[] {"textures/entity/villager2/professions/weaponsmith.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/armorer.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/butcher.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/cartographer.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/cleric.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/farmer.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/fisherman.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/fletcher.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/leatherworker.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/librarian.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/nitwit.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/shepherd.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/stonemason.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/toolsmith.png"});
        defaultData.add(new Object[] {"textures/entity/zombie_villager2/professions/weaponsmith.png"});
    }

    public VillagerConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return new ArrayList<>();
            }

            packConverter.log(String.format("Convert villager %s", from));

            BufferedImage fromImage = ImageIO.read(fromFile);

            BufferedImage newImage = new BufferedImage(fromImage.getWidth(), fromImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            Color blank = new Color(255, 255, 255, 0);
            for (int x = 0; x < newImage.getWidth(); x++) {
                for (int y = 0; y < newImage.getHeight(); y++) {
                    Color c = new Color(fromImage.getRGB(x, y), true);
                    if (c.getAlpha() == 0) {
                        newImage.setRGB(x, y, blank.getRGB());
                    } else {
                        newImage.setRGB(x, y, c.getRGB());
                    }
                }
            }

            ImageUtils.write(newImage, "png", fromFile);
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
