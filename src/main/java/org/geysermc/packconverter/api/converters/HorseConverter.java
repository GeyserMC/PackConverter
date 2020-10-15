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

public class HorseConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/entity/horse2/donkey.png", "textures/entity/horse/donkey.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_black.png", "textures/entity/horse/horse_black.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_brown.png", "textures/entity/horse/horse_brown.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_chestnut.png", "textures/entity/horse/horse_chestnut.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_creamy.png", "textures/entity/horse/horse_creamy.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_darkbrown.png", "textures/entity/horse/horse_darkbrown.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_gray.png", "textures/entity/horse/horse_gray.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_skeleton.png", "textures/entity/horse/horse_skeleton.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_white.png", "textures/entity/horse/horse_white.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_zombie.png", "textures/entity/horse/horse_zombie.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/mule.png", "textures/entity/horse/mule.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_markings_blackdots.png", "textures/entity/horse/horse_markings_blackdots.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_markings_white.png", "textures/entity/horse/horse_markings_white.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_whitedots.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitefield.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/armor/horse_armor_diamond.png", "textures/entity/horse/armor/horse_armor_diamond.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_gold.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_iron.png"});
        defaultData.add(new Object[] {"textures/entity/horse2/armor/horse_armor_leather.png", "textures/entity/horse/armor/horse_armor_leather.png"});
    }

    public HorseConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            File fromFile = storage.resolve(from).toFile();

            if (!fromFile.exists()) {
                return delete;
            }

            packConverter.log(String.format("Convert horse %s", to));

            BufferedImage fromImage = ImageIO.read(fromFile);

            int factor = fromImage.getWidth() / 64;

            BufferedImage newImage = new BufferedImage((fromImage.getWidth() * 2), (fromImage.getWidth() * 2), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();

            // Chest
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (21 * factor), (22 * factor), (11 * factor)), 0, (34 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (21 * factor), (22 * factor), (11 * factor)), 0, (47 * factor), null);

            // Saddle (Gray part)
            g.drawImage(ImageUtils.crop(fromImage, (29 * factor), (5 * factor), (6 * factor), (4 * factor)), (74 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (29 * factor), (5 * factor), (6 * factor), (4 * factor)), (74 * factor), (4 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (29 * factor), (5 * factor), (6 * factor), (4 * factor)), (74 * factor), (13 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (31 * factor), (5 * factor), factor, factor), (81 * factor), (26 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (31 * factor), (5 * factor), factor, factor), (87 * factor), (26 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (31 * factor), (5 * factor), factor, factor), (101 * factor), (26 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (31 * factor), (5 * factor), factor, factor), (107 * factor), (26 * factor), null);

            // Saddle (Color part)
            g.drawImage(ImageUtils.crop(fromImage, (35 * factor), 0, (10 * factor), (9 * factor)), (88 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (35 * factor), 0, (10 * factor), (9 * factor)), (98 * factor), 0, null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), (2 * factor)), (82 * factor), (9 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (7 * factor), (2 * factor)), (91 * factor), (9 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (6 * factor), (2 * factor)), (108 * factor), (9 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (8 * factor), factor), (80 * factor), (8 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (8 * factor), factor), (108 * factor), (8 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), factor), (80 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), factor), (89 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (2 * factor), factor), (98 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), factor), (106 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, factor), (115 * factor), (11 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), factor), (92 * factor), (13 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, factor), (101 * factor), (13 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (9 * factor), factor), (92 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, factor), (101 * factor), (19 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (2 * factor), factor), (71 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (35 * factor), 0, (4 * factor), (6 * factor)), (70 * factor), factor, null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (2 * factor), factor), (81 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, (35 * factor), 0, (4 * factor), (6 * factor)), (80 * factor), factor, null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (3 * factor), factor), (60 * factor), (22 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (8 * factor), factor), (63 * factor), (23 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (3 * factor), factor), (71 * factor), (22 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (2 * factor), factor), (74 * factor), (21 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (3 * factor), factor), (60 * factor), (27 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (8 * factor), factor), (63 * factor), (28 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (3 * factor), factor), (71 * factor), (27 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (2 * factor), factor), (74 * factor), (26 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (81 * factor), (24 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (81 * factor), (27 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (5 * factor), factor), (82 * factor), (26 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (87 * factor), (24 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (87 * factor), (27 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (101 * factor), (24 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (101 * factor), (27 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), (5 * factor), factor), (102 * factor), (26 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (107 * factor), (24 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (26 * factor), (9 * factor), factor, (2 * factor)), (107 * factor), (27 * factor), null);

            // Horse
            g.drawImage(ImageUtils.crop(fromImage, 0, (54 * factor), factor, (10 * factor)), 0, (58 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (54 * factor), factor, (10 * factor)), factor, (58 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (54 * factor), (64 * factor), (10 * factor)), (2 * factor), (58 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (62 * factor), (54 * factor), factor, (10 * factor)), (66 * factor), (58 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (62 * factor), (54 * factor), factor, (10 * factor)), (67 * factor), (58 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (22 * factor), (32 * factor), (20 * factor), factor), (24 * factor), (34 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (22 * factor), (32 * factor), (20 * factor), (22 * factor)), (24 * factor), (35 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (22 * factor), (53 * factor), (20 * factor), factor), (24 * factor), (57 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, 0, (35 * factor), (22 * factor), factor), factor, (12 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (35 * factor), (22 * factor), factor), factor, (13 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (35 * factor), (22 * factor), (19 * factor)), factor, (14 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (15 * factor), (42 * factor), (7 * factor), factor), (16 * factor), (20 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (42 * factor), (7 * factor), factor), factor, (20 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (53 * factor), (22 * factor), factor), factor, (33 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, factor, (20 * factor), factor, (14 * factor)), 0, (20 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (22 * factor), (20 * factor), factor, (14 * factor)), (23 * factor), (20 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (14 * factor), (8 * factor)), (44 * factor), (33 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (14 * factor), (8 * factor)), (60 * factor), (33 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (16 * factor), (8 * factor)), (79 * factor), (34 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (32 * factor), (16 * factor), factor), (79 * factor), (42 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (79 * factor), (34 * factor), factor, (9 * factor)), (78 * factor), (34 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (94 * factor), (34 * factor), factor, (9 * factor)), (95 * factor), (34 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (78 * factor), (34 * factor), (18 * factor), (9 * factor)), (96 * factor), (34 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (52 * factor), (21 * factor), (8 * factor), (4 * factor)), (48 * factor), (51 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (52 * factor), (21 * factor), (8 * factor), (4 * factor)), (64 * factor), (51 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (52 * factor), (21 * factor), (8 * factor), (4 * factor)), (82 * factor), (51 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (52 * factor), (21 * factor), (8 * factor), (4 * factor)), (100 * factor), (51 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (33 * factor), (16 * factor), (3 * factor)), (44 * factor), (55 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (33 * factor), (16 * factor), (3 * factor)), (60 * factor), (55 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (33 * factor), (16 * factor), (3 * factor)), (78 * factor), (55 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (33 * factor), (16 * factor), (3 * factor)), (96 * factor), (55 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, 0, (12 * factor), (6 * factor), (8 * factor)), 0, (12 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (7 * factor), (13 * factor), (10 * factor), (8 * factor)), (7 * factor), 0, null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (20 * factor), (9 * factor), (5 * factor)), 0, (7 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (10 * factor), (20 * factor), (14 * factor), (5 * factor)), (9 * factor), (7 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (25 * factor), (20 * factor), factor, (5 * factor)), (23 * factor), (7 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, 0, (25 * factor), (18 * factor), factor), (25 * factor), (18 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (25 * factor), (18 * factor), (8 * factor)), (25 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (25 * factor), factor, (8 * factor)), (24 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (25 * factor), factor, (8 * factor)), (43 * factor), (19 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (25 * factor), (18 * factor), (5 * factor)), (24 * factor), (27 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, 0, (33 * factor), (18 * factor), (2 * factor)), (24 * factor), (32 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (4 * factor)), (48 * factor), (29 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (4 * factor)), (64 * factor), (29 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (78 * factor), (34 * factor), (8 * factor), (5 * factor)), (83 * factor), (29 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (78 * factor), (34 * factor), (8 * factor), (5 * factor)), (101 * factor), (29 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (3 * factor)), (47 * factor), (41 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (3 * factor)), (63 * factor), (41 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (3 * factor)), (81 * factor), (43 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (3 * factor)), (99 * factor), (43 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (44 * factor), (44 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (50 * factor), (44 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (60 * factor), (44 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (66 * factor), (44 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (78 * factor), (46 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (84 * factor), (46 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (96 * factor), (46 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (6 * factor), (5 * factor)), (102 * factor), (46 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (48 * factor), (25 * factor), (4 * factor), (3 * factor)), (47 * factor), 0, null);
            g.drawImage(ImageUtils.crop(newImage, (78 * factor), (34 * factor), factor, (2 * factor)), (44 * factor), (3 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (78 * factor), (34 * factor), (8 * factor), (2 * factor)), (45 * factor), (3 * factor), null);
            g.drawImage(ImageUtils.crop(newImage, (85 * factor), (34 * factor), factor, (2 * factor)), (53 * factor), (3 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (42 * factor), (40 * factor), (6 * factor), (7 * factor)), (45 * factor), (7 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (42 * factor), (40 * factor), (10 * factor), (4 * factor)), (38 * factor), (14 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (42 * factor), (40 * factor), (10 * factor), (4 * factor)), (48 * factor), (14 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (42 * factor), (40 * factor), (6 * factor), (5 * factor)), (31 * factor), (5 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (42 * factor), (40 * factor), (13 * factor), (4 * factor)), (26 * factor), (10 * factor), null);
            g.drawImage(ImageUtils.crop(fromImage, (43 * factor), (52 * factor), (2 * factor), (2 * factor)), (39 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.flip(ImageUtils.crop(fromImage, (43 * factor), (52 * factor), (2 * factor), (2 * factor)), true, false), (24 * factor), (11 * factor), null);
            g.drawImage(ImageUtils.flip(ImageUtils.flip(ImageUtils.crop(fromImage, (43 * factor), (52 * factor), (2 * factor), (2 * factor)), true, false), false, true), (31 * factor), (3 * factor), null);
            g.drawImage(ImageUtils.flip(ImageUtils.flip(ImageUtils.crop(fromImage, (43 * factor), (52 * factor), (2 * factor), (2 * factor)), true, false), false, true), (34 * factor), (3 * factor), null);

            g.drawImage(ImageUtils.crop(fromImage, (19 * factor), (16 * factor), (6 * factor), (4 * factor)), 0, 0, null);

            ImageUtils.write(newImage, "png", storage.resolve(to).toFile());
        } catch (IOException e) { }

        return delete;
    }
}
