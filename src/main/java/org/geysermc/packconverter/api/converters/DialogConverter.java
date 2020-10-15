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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class DialogConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {
            "textures/gui/container/generic_54.png",
            256,
            new Object[] {
                new Object[] {0, 0, 176, 222, new int[] {7, 17, 7, 7}, new Object[] {
                    new Object[] {"textures/ui/achievements_dialog", new int[] {6, 20, 6, 6}},
                    new Object[] {"textures/ui/dialog_background_hollow_1", new int[] {8, 23, 8, 76}},
                    new Object[] {"textures/ui/dialog_background_hollow_2", new int[] {8, 23, 8, 42}},
                    new Object[] {"textures/ui/dialog_background_hollow_3", new int[] {8, 23, 8, 8}},
                    new Object[] {"textures/ui/dialog_background_hollow_4", new int[] {8, 8, 8, 8}},
                    new Object[] {"textures/ui/dialog_background_hollow_4_thin", new int[] {6, 6, 6, 6}},
                    new Object[] {"textures/ui/dialog_background_hollow_5", new int[] {8, 17, 8, 42}},
                    new Object[] {"textures/ui/dialog_background_hollow_6", new int[] {8, 23, 8, 104}},
                    new Object[] {"textures/ui/dialog_background_hollow_7", new int[] {8, 66, 8, 8}},
                    new Object[] {"textures/ui/dialog_background_hollow_8", new int[] {8, 8, 33, 33}},
                    new Object[] {"textures/ui/dialog_background_opaque", new int[] {4, 4, 4, 4}},
                    new Object[] {"textures/ui/dialog_background_opaque_overlap_bottom", new int[] {4, 4, 4, 4}},
                    new Object[] {"textures/ui/menubackground", new int[] {4, 4, 4, 4}},
                    new Object[] {"textures/ui/thin_dialog", new int[] {5, 5, 5, 5}}
                }}
            }
        });
    }

    public DialogConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            int factorDetect = (int) this.data[1];
            Object[] dialogs = (Object[]) this.data[2];

            File fromFile = storage.resolve(from).toFile();
            if (!fromFile.exists()) {
                return new ArrayList<>();
            }
            
            BufferedImage fromImage = ImageIO.read(fromFile);
            fromImage = ImageUtils.ensureMinWidth(fromImage, factorDetect);

            int factor = (fromImage.getWidth() / factorDetect);

            ObjectMapper mapper = new ObjectMapper().enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

            for (Object dialog : dialogs) {
                Object[] dialogArr = (Object[]) dialog;
                int x = (int) dialogArr[0];
                int y = (int) dialogArr[1];
                int width = (int) dialogArr[2];
                int height = (int) dialogArr[3];
                int[] sizes = (int[]) dialogArr[4];
                Object[] tos = (Object[]) dialogArr[5];

                for (int i = 0; i < sizes.length; i++) {
                    sizes[i] = sizes[i] * factor;
                }

                BufferedImage dialogImage = ImageUtils.crop(fromImage, (x * factor), (y * factor), (width * factor), (height * factor));

                for (Object to : tos) {
                    Object[] toArr = (Object[]) to;
                    String toPath = (String) toArr[0];
                    int[] toSizes = (int[]) toArr[1];

                    for (int i = 0; i < toSizes.length; i++) {
                        toSizes[i] = toSizes[i] * factor;
                    }

                    BufferedImage toImage = new BufferedImage((toSizes[0] + toSizes[2] + (6 * factor)), (toSizes[1] + toSizes[3] + (6 * factor)), BufferedImage.TYPE_INT_ARGB);
                    Graphics g = toImage.getGraphics();

                    // TODO: Fix and finish this
                    g.drawImage(ImageUtils.borderImage(ImageUtils.crop(dialogImage, sizes[0], sizes[1]), (2 * factor), (2 * factor), (2 * factor), (2 * factor), toSizes[0], toSizes[1]), 0, 0, null);

                    JsonNode metadata = mapper.readTree("{nineslice_size: " + mapper.writeValueAsString(toSizes) + ", base_size: [" + (toImage.getWidth() / factor) + ", " + (toImage.getHeight() / factor) + "]}");

                    packConverter.log(String.format("Convert dialog %s (Experimental)", toPath));

                    ImageUtils.write(toImage, "png", storage.resolve(toPath + ".png").toFile());

                    mapper.writeValue(storage.resolve(toPath + ".json").toFile(), metadata);
                }
            }
            
        } catch (IOException e) { e.printStackTrace(); }

        return new ArrayList<>();
    }
}
