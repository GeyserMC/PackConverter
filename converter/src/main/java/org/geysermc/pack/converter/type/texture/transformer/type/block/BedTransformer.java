/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AutoService(TextureTransformer.class)
public class BedTransformer implements TextureTransformer {
    private static final String BED_PATH = "entity/bed";
    private static final List<String> BED_COLORS = List.of(
            "black",
            "blue",
            "brown",
            "cyan",
            "gray",
            "green",
            "light_blue",
            "light_gray",
            "lime",
            "magenta",
            "orange",
            "pink",
            "purple",
            "red",
            "white",
            "yellow"
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture bedHeadNorth = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/bed_head_north.png"));
        BufferedImage bedHeadNorthImage = this.readImage(bedHeadNorth);
        Texture bedBottom = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/bed_down.png"));
        BufferedImage bedBottomImage = this.readImage(bedBottom);

        float scale = bedBottomImage.getWidth() / 16f;

        for (String bedColor : BED_COLORS) {
            Texture bedFootEast = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_foot_east.png"));
            BufferedImage bedFootEastImage = this.readImage(bedFootEast);
            Texture bedFootWest = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_foot_west.png"));
            BufferedImage bedFootWestImage = this.readImage(bedFootWest);
            Texture bedFootSouth = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_foot_south.png"));
            BufferedImage bedFootSouthImage = this.readImage(bedFootSouth);
            Texture bedFootUp = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_foot_up.png"));
            BufferedImage bedFootUpImage = this.readImage(bedFootUp);
            Texture bedHeadEast = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_head_east.png"));
            BufferedImage bedHeadEastImage = this.readImage(bedHeadEast);
            Texture bedHeadWest = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_head_west.png"));
            BufferedImage bedHeadWestImage = this.readImage(bedHeadWest);
            Texture bedHeadUp = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + bedColor + "_bed_head_up.png"));
            BufferedImage bedHeadUpImage = this.readImage(bedHeadUp);

            context.debug(String.format("Convert bed %s", bedColor));

            BufferedImage newBedImage = new BufferedImage((int) (scale * 64), (int) (scale * 64), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = newBedImage.getGraphics();

            // Head Bed North
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedHeadNorthImage,
                                    0, 7,
                                    16, 6
                            ),
                            180
                    ),
                    6, 0, null
            );
            // Head Bed Up
            graphics.drawImage(bedHeadUpImage, 6, 6, null);
            // Head Bed East
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedHeadEastImage,
                                    0, 7,
                                    16, 6
                            ),
                            270
                    ),
                    22, 6, null
            );
            // Head Bed West
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedHeadWestImage,
                                    0, 7,
                                    16, 6
                            ),
                            90
                    ),
                    0, 6, null
            );

            // Foot Bed South
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootSouthImage,
                                    0, 7,
                                    16, 6
                            ),
                            180
                    ),
                    22, 0, null
            );
            // Foot Bed Up
            graphics.drawImage(bedFootUpImage, 6, 22, null);
            // Foot Bed East
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootEastImage,
                                    0, 7,
                                    16, 6
                            ),
                            270
                    ),
                    22, 22, null
            );
            // Foot Bed West
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedHeadWestImage,
                                    0, 7,
                                    16, 6
                            ),
                            90
                    ),
                    0, 22, null
            );

            // Bottom
            graphics.drawImage(
                    ImageUtil.rotate(bedBottomImage, 180),
                    28, 6, null
            );
            graphics.drawImage(
                    ImageUtil.rotate(bedBottomImage, 180),
                    28, 22, null
            );

            // Feet
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedHeadNorthImage,
                                    0, 13, 6, 3
                            ), false, true
                    ),
                    3, 38, null
            );
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedHeadNorthImage,
                                    10, 13, 6, 3
                            ), false, true
                    ),
                    15, 38, null
            );

            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootSouthImage,
                                    0, 13, 6, 3
                            ), 180
                    ),
                    15, 44, null
            );
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootSouthImage,
                                    10, 13, 6, 3
                            ), 180
                    ),
                    3, 44, null
            );

            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootSouthImage,
                                    0, 13, 6, 3
                            ), 180
                    ),
                    15, 44, null
            );
            graphics.drawImage(
                    ImageUtil.rotate(
                            ImageUtil.crop(
                                    bedFootSouthImage,
                                    10, 13, 6, 3
                            ), 180
                    ),
                    3, 44, null
            );

            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedHeadEastImage,
                                    7, 13, 6, 3
                            ), true, false
                    ),
                    0, 41, null
            );
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedHeadEastImage,
                                    7, 13, 6, 3
                            ), true, true
                    ),
                    0, 47, null
            );

            graphics.drawImage(
                    ImageUtil.crop(
                            bedHeadWestImage,
                            3, 13, 6, 3
                    ),
                    6, 41, null
            );
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedHeadWestImage,
                                    3, 13, 6, 3
                            ), false, true
                    ),
                    6, 47, null
            );

            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedFootWestImage,
                                    7, 13, 6, 3
                            ), true, false
                    ),
                    12, 41, null
            );
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedFootWestImage,
                                    7, 13, 6, 3
                            ), true, true
                    ),
                    12, 47, null
            );

            graphics.drawImage(
                    ImageUtil.crop(
                            bedFootEastImage,
                            3, 13, 6, 3
                    ),
                    18, 41, null
            );
            graphics.drawImage(
                    ImageUtil.flip(
                            ImageUtil.crop(
                                    bedFootEastImage,
                                    3, 13, 6, 3
                            ), false, true
                    ),
                    18, 47, null
            );

            if (bedColor.equals("light_gray")) bedColor = "sliver";

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BED_PATH + "/" + bedColor + ".png"), newBedImage, "png");
        }
    }
}
