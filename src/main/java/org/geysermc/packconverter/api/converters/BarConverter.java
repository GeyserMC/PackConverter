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

public class BarConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {
                "textures/gui/icons.png",
                256,
                new Object[] {
                        new Object[] {64, new Object[] {
                                new Object[] {"textures/gui/achievements/hotdogempty"},
                                new Object[] {"textures/ui/empty_progress_bar"},
                                new Object[] {"textures/ui/experiencebarempty"},
                                new Object[] {"textures/ui/experience_bar_empty_blue"}
                        }},
                        new Object[] {69, new Object[] {
                                new Object[] {"textures/gui/achievements/hotdogfull"},
                                new Object[] {"textures/ui/experiencebarfull"}
                        }},
                        new Object[] {69, new Object[] {
                                new Object[] {"textures/ui/experience_bar_full_blue", new Color(112, 215, 225)},
                                new Object[] {"textures/ui/experience_bar_full_white", new Color(236, 236, 236)},
                                new Object[] {"textures/ui/filled_progress_bar", new Color(236, 236, 236)}
                        }},
                        new Object[] {89, new Object[] {
                                new Object[] {"textures/ui/horse_jump_full"}
                        }}
                },
                new String[] {
                        "textures/gui/achievements/nub.png",
                        "textures/ui/experiencenub.png",
                        "textures/ui/experience_bar_nub_blue.png"
                }
        });
    }

    public BarConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            int factorDetect = (int) this.data[1];
            Object[] bars = (Object[]) this.data[2];
            String[] nubs = (String[]) this.data[3];

            File fromFile = storage.resolve(from).toFile();
            if (!fromFile.exists()) {
                return new ArrayList<>();
            }
            
            BufferedImage fromImage = ImageIO.read(fromFile);
            fromImage = ImageUtils.ensureMinWidth(fromImage, factorDetect);

            int factor = (fromImage.getWidth() / factorDetect);

            ObjectMapper mapper = new ObjectMapper().enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

            JsonNode metadata = mapper.readTree("{nineslice_size: [1, 0, 1, 0], base_size: [182, 5]}");

            for (Object bar : bars) {
                Object[] barArr = (Object[]) bar;
                int y = (int) barArr[0];
                Object[] tos = (Object[]) barArr[1];

                BufferedImage toImage = ImageUtils.crop(fromImage, 0, (y * factor), (182 * factor), (5 * factor));

                for (Object to : tos) {
                    Object[] toArr = (Object[]) to;
                    String toPath = (String) toArr[0];
                    Color color = toArr.length > 1 && toArr[1] != null ? (Color) toArr[1] : Color.white;

                    packConverter.log(String.format("Convert bar %s", toPath));

                    ImageUtils.write(ImageUtils.colorize(toImage, color), "png", storage.resolve(toPath + ".png").toFile());

                    mapper.writeValue(storage.resolve(toPath + ".json").toFile(), metadata);
                }
            }
            
            BufferedImage transparentImage = new BufferedImage(factor, (5 * factor), BufferedImage.TYPE_INT_ARGB);
            for (String nub : nubs) {
                packConverter.log(String.format("Convert bar %s", nub));

                ImageUtils.write(transparentImage, "png", storage.resolve(nub).toFile());
            }
            
        } catch (IOException e) { e.printStackTrace(); }

        return new ArrayList<>();
    }
}
