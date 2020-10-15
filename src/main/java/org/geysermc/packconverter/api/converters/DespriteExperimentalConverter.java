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

public class DespriteExperimentalConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {
                "textures/gui/recipe_book.png",
                256,
                new Object[] {
                        new Object[] {152, 41, 26, 16, "textures/ui/craft_toggle_off.png"},
                        new Object[] {152, 59, 26, 16, "textures/ui/craft_toggle_off_hover.png"},
                        new Object[] {180, 41, 26, 16, "textures/ui/craft_toggle_on.png"},
                        new Object[] {180, 59, 26, 16, "textures/ui/craft_toggle_on_hover.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/widgets.png",
                256,
                new Object[] {
                        new Object[] {0, 22, 24, 24, "textures/ui/pocket_ui_highlight_selected_slot.png", null, new int[] {4, 4, 16, 16}},
                        new Object[] {0, 22, 24, 24, "textures/ui/pocket_ui_highlight_slot.png", null, new int[] {4, 4, 16, 16}}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/anvil.png",
                256,
                new Object[] {
                        new Object[] {99, 45, 22, 15, "textures/ui/arrow_large.png"},
                        new Object[] {17, 7, 30, 30, "textures/ui/anvil_icon.png"},
                        new Object[] {53, 49, 13, 13, "textures/ui/anvil-plus.png"},
                        new Object[] {176, 0, 28, 21, "textures/ui/crossout.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/beacon.png",
                256,
                new Object[] {
                        new Object[] {58, 108, 5, 18, "textures/ui/beacon_item_seperator_pocket.png"},
                        new Object[] {58, 108, 5, 18, "textures/ui/item_seperator.png"},
                        new Object[] {18, 22, 19, 22, "textures/ui/pyramid_level_1.png"},
                        new Object[] {18, 47, 19, 22, "textures/ui/pyramid_level_2.png"},
                        new Object[] {18, 72, 19, 22, "textures/ui/pyramid_level_3.png"},
                        new Object[] {158, 22, 19, 22, "textures/ui/pyramid_level_4.png"},
                        new Object[] {88, 219, 22, 22, "textures/ui/confirm.png"},
                        new Object[] {110, 219, 22, 22, "textures/ui/cancel.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/brewing_stand.png",
                256,
                new Object[] {
                        new Object[] {17, 17, 16, 16, "textures/ui/brewing_fuel_empty.png"},
                        new Object[] {56, 51, 16, 16, "textures/ui/bottle_empty.png"},
                        new Object[] {60, 43, 19, 6, "textures/ui/brewing_fuel_bar_empty.png"},
                        new Object[] {176, 29, 18, 4, "textures/ui/brewing_fuel_bar_full.png"},
                        new Object[] {63, 14, 12, 29, "textures/ui/bubbles_empty.png"},
                        new Object[] {185, 0, 12, 29, "textures/ui/bubbles_full.png"},
                        new Object[] {34, 28, 26, 20, "textures/ui/brewing_fuel_pipes.png"},
                        new Object[] {73, 34, 28, 23, "textures/ui/brewing_pipes.png"},
                        new Object[] {73, 34, 28, 23, "textures/ui/brewing_pipes_large.png"},
                        new Object[] {97, 16, 9, 28, "textures/ui/brewing_arrow_empty.png"},
                        new Object[] {97, 16, 9, 28, "textures/ui/brewing_arrow_large_empty.png"},
                        new Object[] {176, 0, 9, 28, "textures/ui/brewing_arrow_full.png"},
                        new Object[] {176, 0, 9, 28, "textures/ui/brewing_arrow_large_full.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/enchanting_table.png",
                256,
                new Object[] {
                        new Object[] {35, 47, 16, 16, "textures/ui/lapis.png"},
                        new Object[] {35, 47, 16, 16, "textures/ui/lapis_image.png"},
                        new Object[] {3, 225, 13, 11, "textures/ui/dust_selectable_1.png"},
                        new Object[] {19, 225, 13, 11, "textures/ui/dust_selectable_2.png"},
                        new Object[] {34, 225, 13, 11, "textures/ui/dust_selectable_3.png"},
                        new Object[] {3, 241, 13, 11, "textures/ui/dust_unselectable_1.png"},
                        new Object[] {19, 241, 13, 11, "textures/ui/dust_unselectable_2.png"},
                        new Object[] {34, 241, 13, 11, "textures/ui/dust_unselectable_3.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/furnace.png",
                256,
                new Object[] {
                        new Object[] {79, 35, 24, 17, "textures/ui/arrow_inactive.png"},
                        new Object[] {176, 14, 24, 17, "textures/ui/arrow_active.png"},
                        new Object[] {56, 36, 14, 14, "textures/ui/flame_empty_image.png"},
                        new Object[] {176, 0, 14, 14, "textures/ui/flame_full_image.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/horse.png",
                256,
                new Object[] {
                        new Object[] {1, 221, 16, 16, "textures/ui/empty_horse_slot_armor.png"},
                        new Object[] {19, 221, 16, 16, "textures/ui/empty_horse_slot_saddle.png"},
                        new Object[] {37, 221, 16, 16, "textures/ui/empty_llama_slot_carpet.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/inventory.png",
                256,
                new Object[] {
                        new Object[] {135, 29, 16, 13, "textures/ui/arrow.png"}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/loom.png",
                256,
                new Object[] {
                        new Object[] {176, 0, 16, 16, "textures/ui/loom_banner_empty.png", new int[] {13, 26, 16, 16}},
                        new Object[] {192, 0, 16, 16, "textures/ui/loom_dye_empty.png", new int[] {33, 26, 16, 16}},
                        new Object[] {208, 0, 16, 16, "textures/ui/loom_pattern_item_empty.png", new int[] {23, 45, 16, 16}}
                }
        });
        defaultData.add(new Object[] {
                "textures/gui/container/smithing.png",
                256,
                new Object[] {
                        new Object[] {17, 7, 30, 30, "textures/ui/smithing_icon.png"},
                        new Object[] {53, 49, 13, 13, "textures/ui/smithing-table-plus.png"},
                        new Object[] {76, 47, 16, 16, "textures/ui/ingot_image.png"}
                }
        });
    }

    public DespriteExperimentalConverter(PackConverter packConverter, Path storage, Object[] data) {
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
                int[] emptyOverlayAlt = spriteArr.length > 5 ? (int[]) spriteArr[5] : null;
                int[] emptyOverlay = spriteArr.length > 6 ? (int[]) spriteArr[6] : null;

                packConverter.log(String.format("Desprite %s (Experimental)", to));

                BufferedImage spriteImage = ImageUtils.crop(fromImage, (x * factor), (y * factor), (width * factor), (height * factor));

                if (emptyOverlayAlt != null && ImageUtils.isEmptyArea(spriteImage, 0, 0, spriteImage.getWidth(), spriteImage.getHeight())) {
                    spriteImage = ImageUtils.crop(spriteImage, (emptyOverlayAlt[0] * factor), (emptyOverlayAlt[1] * factor), (emptyOverlayAlt[2] * factor), (emptyOverlayAlt[3] * factor));
                }

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
