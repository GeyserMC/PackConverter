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

public class DespriteConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {
                "textures/gui/recipe_button.png",
                256,
                new Object[] {
                        new Object[] {0, 0, 20, 18, "textures/ui/recipe_book_icon.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/widgets.png",
                256,
                new Object[] {
                        new Object[] {0, 0, 1, 22, "textures/ui/hotbar_start_cap.png"},
                        new Object[] {1, 0, 20, 22, "textures/ui/hotbar_0.png"},
                        new Object[] {21, 0, 20, 22, "textures/ui/hotbar_1.png"},
                        new Object[] {41, 0, 20, 22, "textures/ui/hotbar_2.png"},
                        new Object[] {61, 0, 20, 22, "textures/ui/hotbar_3.png"},
                        new Object[] {81, 0, 20, 22, "textures/ui/hotbar_4.png"},
                        new Object[] {101, 0, 20, 22, "textures/ui/hotbar_5.png"},
                        new Object[] {121, 0, 20, 22, "textures/ui/hotbar_6.png"},
                        new Object[] {141, 0, 20, 22, "textures/ui/hotbar_7.png"},
                        new Object[] {161, 0, 20, 22, "textures/ui/hotbar_8.png"},
                        new Object[] {181, 0, 1, 22, "textures/ui/hotbar_end_cap.png"},
                        new Object[] {0, 22, 24, 24, "textures/ui/selected_hotbar_slot.png", new int[] {4, 4, 16, 16}}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/cartography_table.png",
                256,
                new Object[] {
                        new Object[] {176, 0, 66, 66, "textures/ui/cartography_table_map.png"},
                        new Object[] {176, 66, 66, 66, "textures/ui/cartography_table_zoom.png"},
                        new Object[] {176, 132, 50, 50, "textures/ui/cartography_table_copy.png"},
                        new Object[] {0, 166, 66, 66, "textures/ui/cartography_table_glass.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/inventory.png",
                256,
                new Object[] {
                        new Object[] {141, 166, 24, 24, "textures/ui/hud_mob_effect_background.png"}
                }
        });
    }

    public DespriteConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            int factorDetect = (int) this.data[1];
            Object[] sprites = (Object[]) this.data[2];

            File fromFile = storage.resolve(from).toFile();
            if (!fromFile.exists()) {
                return new ArrayList<>();
            }
            
            BufferedImage fromImage = ImageIO.read(fromFile);
            fromImage = ImageUtils.ensureMinWidth(fromImage, factorDetect);

            int factor = (fromImage.getWidth() / factorDetect);

            for (Object sprite : sprites) {
                Object[] spriteArr = (Object[]) sprite;
                int x = (int) spriteArr[0];
                int y = (int) spriteArr[1];
                int width = (int) spriteArr[2];
                int height = (int) spriteArr[3];
                String to = (String) spriteArr[4];
                int[] emptyOverlay = spriteArr.length > 5 ? (int[]) spriteArr[5] : null;

                packConverter.log(String.format("Desprite %s", to));

                BufferedImage spriteImage = ImageUtils.crop(fromImage, (x * factor), (y * factor), (width * factor), (height * factor));

                if (emptyOverlay != null) {
                    Graphics g = spriteImage.getGraphics();
                    g.setColor(new Color(Color.TRANSLUCENT, true));
                    g.fillRect((emptyOverlay[0] * factor), (emptyOverlay[1] * factor), (emptyOverlay[2] * factor), (emptyOverlay[3] * factor));
                }

                ImageUtils.write(spriteImage, "png", storage.resolve(to).toFile());
            }
        } catch (IOException e) { }

        return new ArrayList<>();
    }
}
