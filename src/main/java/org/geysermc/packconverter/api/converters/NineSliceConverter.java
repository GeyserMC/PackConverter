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

public class NineSliceConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {
            "textures/gui/recipe_book.png",
            256,
            new Object[] {
                new Object[] {54, 206, 24, 24, 5, new String[] {
                    "textures/ui/cell_image_red",
                    "textures/ui/recipe_book_red_button",
                    "textures/ui/recipe_book_red_button_pressed",
                    "textures/ui/button_trade_red",
                    "textures/ui/button_trade_red_pressed"
                }}
            },
            new String[0]
        });
        defaultData.add(new Object[] {
            "textures/gui/widgets.png",
            256,
            new Object[] {
                new Object[] {0, 46, 200, 20, 5, new String[] {
                    "textures/ui/button_borderless_darkpressednohover",
                    "textures/ui/button_borderless_lightpressednohover",
                    "textures/ui/disabledButtonNoBorder",
                    "textures/ui/recipe_book_button_borderless_lightpressednohover"
                }},
                new Object[] {0, 66, 200, 20, 5, new String[] {
                    "textures/ui/button_borderless_dark",
                    "textures/ui/button_borderless_light",
                    "textures/ui/pocket_button_default",
                    "textures/ui/recipe_book_button_borderless_light",
                    "textures/ui/recipe_book_dark_button",
                    "textures/ui/recipe_book_light_button"
                }},
                new Object[] {0, 86, 200, 20, 5, new String[] {
                    "textures/ui/button_borderless_darkhover",
                    "textures/ui/button_borderless_darkpressed",
                    "textures/ui/button_borderless_lighthover",
                    "textures/ui/button_borderless_lightpressed",
                    "textures/ui/pocket_button_hover",
                    "textures/ui/pocket_button_pressed",
                    "textures/ui/recipe_book_button_borderless_lighthover",
                    "textures/ui/recipe_book_button_borderless_lightpressed",
                    "textures/ui/recipe_book_dark_button_pressed",
                    "textures/ui/recipe_book_light_button_pressed"
                }},
                new Object[] {0, 22, 24, 24, 5, new String[] {
                    "textures/ui/cell_image_invert"
                }}
            },
            new String[] {
                "textures/ui/focus_border_selected",
                "textures/ui/focus_border_white",
                "textures/ui/pack_borders",
                "textures/ui/pause_screen_border",
                "textures/ui/square_image_border_white",
                "textures/ui/world_screenshot_focus_border"
            }
        });
        defaultData.add(new Object[] {
            "textures/gui/container/beacon.png",
            256,
            new Object[] {
                new Object[] {0, 219, 22, 22, 5, new String[] {
                    "textures/ui/beacon_button_default"
                }},
                new Object[] {22, 219, 22, 22, 5, new String[] {
                    "textures/ui/beacon_button_pressed"
                }},
                new Object[] {44, 219, 22, 22, 5, new String[] {
                    "textures/ui/beacon_button_locked"
                }},
                new Object[] {66, 219, 22, 22, 5, new String[] {
                    "textures/ui/beacon_button_hover"
                }}
            },
            new String[0]
        });
        defaultData.add(new Object[] {
            "textures/gui/container/enchanting_table.png",
            256,
            new Object[] {
                new Object[] {0, 166, 108, 19, 5, new String[] {
                    "textures/ui/enchanting_active_background"
                }},
                new Object[] {0, 185, 108, 19, 5, new String[] {
                    "textures/ui/enchanting_dark_background"
                }},
                new Object[] {0, 204, 108, 19, 5, new String[] {
                    "textures/ui/enchanting_active_background_with_hover_text"
                }}
            },
            new String[0]
        });
        defaultData.add(new Object[] {
            "textures/gui/container/generic_54.png",
            256,
            new Object[] {
                new Object[] {7, 17, 18, 18, 5, new String[] {
                    "textures/ui/cell_image",
                    "textures/ui/cell_image_normal",
                    "textures/ui/item_cell",
                    "textures/ui/recipe_book_item_bg"
                }},
                new Object[] {2, 2, 1, 1, 0, new String[] {
                    "textures/ui/dialog_divider",
                    "textures/ui/divider",
                    "textures/ui/divider2",
                    "textures/ui/divider3",
                    "textures/ui/HowToPlayDivider",
                    "textures/ui/lightgreybars",
                    "textures/ui/list_item_divider_line_light",
                    "textures/ui/StoreTopBar",
                    "textures/ui/StoreTopBarFiller"
                }}
            },
            new String[] {
                "textures/ui/recipe_book_pane_bg",
                "textures/ui/recipe_book_touch_cell_selected"
            }
        });
        defaultData.add(new Object[] {
            "textures/gui/container/inventory.png",
            256,
            new Object[] {
                new Object[] {25, 7, 51, 72, 5, new String[] {
                    "textures/ui/player_preview_border"
                }}
            },
            new String[0]
        });
        defaultData.add(new Object[] {
            "textures/gui/container/creative_inventory/tab_item_search.png",
            256,
            new Object[] {
                new Object[] {80, 4, 90, 12, 5, new String[] {
                    "textures/ui/edit_box_indent",
                    "textures/ui/edit_box_indent_hover"
                }}
            },
            new String[0]
        });
        defaultData.add(new Object[] {
            "textures/gui/container/creative_inventory/tabs.png",
            256,
            new Object[] {
                new Object[] {0, 0, 28, 32, 5, new String[] {
                    "textures/ui/pocket_tab_left_side",
                    "textures/ui/recipe_book_side_toggle_dark",
                    "textures/ui/TabLeftBack",
                    "textures/ui/TabLeftBackBottomMost",
                    "textures/ui/TabLeftBackTopMost",
                    "textures/ui/TabTopBackLeftMost"
                }},
                new Object[] {28, 0, 28, 32, 5, new String[] {
                    "textures/ui/recipe_back_panel",
                    "textures/ui/TabTopBack",
                    "textures/ui/toolbar_background"
                }},
                new Object[] {140, 0, 28, 32, 5, new String[] {
                    "textures/ui/pocket_tab_right_side",
                    "textures/ui/TabRightBack",
                    "textures/ui/TabRightBackBottomMost",
                    "textures/ui/TabRightBackTopMost",
                    "textures/ui/TabTopBackRightMost",
                    "textures/ui/TabTopBackRightMostDark",
                    "textures/ui/XTab"
                }},
                new Object[] {0, 32, 28, 32, 5, new String[] {
                    "textures/ui/recipe_book_side_toggle_dark_hover",
                    "textures/ui/TabLeftFront",
                    "textures/ui/TabLeftFrontBottomMost",
                    "textures/ui/TabLeftFrontTopMost",
                    "textures/ui/TabTopFrontLeftMost"
                }},
                new Object[] {28, 32, 28, 32, 5, new String[] {
                    "textures/ui/TabTopFront"
                }},
                new Object[] {140, 32, 28, 32, 5, new String[] {
                    "textures/ui/TabRightFront",
                    "textures/ui/TabRightFrontBottomMost",
                    "textures/ui/TabRightFrontTopMost",
                    "textures/ui/TabTopFrontRightMost",
                    "textures/ui/TabTopFrontRightMostDark"
                }}
            },
            new String[0]
        });
    }

    public NineSliceConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        try {
            String from = (String) this.data[0];
            int factorDetect = (int) this.data[1];
            Object[] buttons = (Object[]) this.data[2];
            String[] borders = (String[]) this.data[3];

            File fromFile = storage.resolve(from).toFile();
            if (!fromFile.exists()) {
                return new ArrayList<>();
            }
            
            BufferedImage fromImage = ImageIO.read(fromFile);
            fromImage = ImageUtils.ensureMinWidth(fromImage, factorDetect);

            int factor = (fromImage.getWidth() / factorDetect);

            ObjectMapper mapper = new ObjectMapper().enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

            for (Object button : buttons) {
                Object[] buttonArr = (Object[]) button;
                int x = (int) buttonArr[0];
                int y = (int) buttonArr[1];
                int width = (int) buttonArr[2];
                int height = (int) buttonArr[3];
                int size = (int) buttonArr[4];
                String[] tos = (String[]) buttonArr[5];

                BufferedImage toImage = ImageUtils.crop(fromImage, (x * factor), (y * factor), (width * factor), (height * factor));

                // image.autoCropTransparent();

                JsonNode metadata = mapper.readTree("{nineslice_size: " + size + ", base_size: [" + width + ", " + height + "]}");

                for (String toPath : tos) {
                    packConverter.log(String.format("Convert button %s (Experimental)", toPath));

                    ImageUtils.write(toImage, "png", storage.resolve(toPath + ".png").toFile());

                    mapper.writeValue(storage.resolve(toPath + ".json").toFile(), metadata);
                }
            }
            
            BufferedImage transparentImage = new BufferedImage(factor, factor, BufferedImage.TYPE_INT_ARGB);
            JsonNode metadata = mapper.readTree("{nineslice_size: 0, base_size: [1, 1]}");
            for (String border : borders) {
                packConverter.log(String.format("Convert button %s (Experimental)", border));

                ImageUtils.write(transparentImage, "png", storage.resolve(border + ".png").toFile());

                mapper.writeValue(storage.resolve(border + ".json").toFile(), metadata);
            }
            
        } catch (IOException e) { e.printStackTrace(); }

        return new ArrayList<>();
    }
}
