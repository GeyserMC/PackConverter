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

package org.geysermc.pack.converter.converter.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
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
        for (String bedColor : BED_COLORS) {
            Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, BED_PATH + "/" + bedColor + ".png"));
            if (texture == null) {
                continue;
            }

            context.info(String.format("Convert bed %s", bedColor));

            BufferedImage bedImage = ImageUtil.ensureMinWidth(this.readImage(texture), 64);

            int factor = bedImage.getWidth() / 64;

            BufferedImage newBedImage = new BufferedImage(bedImage.getWidth(), bedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = newBedImage.getGraphics();

            // Top part
            graphics.drawImage(ImageUtil.crop(bedImage, 0, 0, (44 * factor), (22 * factor)), 0, 0, null);

            // Bottom part
            graphics.drawImage(ImageUtil.crop(bedImage, 0, (28 * factor), (44 * factor), (16 * factor)), 0, (22 * factor), null);

            // Bottom side
            graphics.drawImage(ImageUtil.crop(bedImage, (22 * factor), (22 * factor), (16 * factor), (6 * factor)), (22 * factor), 0, null);

            // Feet
            List<int[]> feet = new ArrayList<>();
            feet.add(new int[] {50, 0, 0, 44, 0});
            feet.add(new int[] {50, 6, 0, 38, 90});
            feet.add(new int[] {50, 12, 12, 44, -90});
            feet.add(new int[] {50, 18, 12, 38, 180});

            for (int[] values : feet) {
                int fromX = values[0];
                int fromY = values[1];
                int toX = values[2];
                int toY = values[3];
                int rotateBottom = values[4];

                graphics.drawImage(ImageUtil.crop(bedImage, ((fromX + 3) * factor), (fromY * factor), (3 * factor), (3 * factor)), ((toX + 3) * factor), ((toY + 3) * factor), null);
                graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(bedImage, ((fromX + 6) * factor), (fromY * factor), (3 * factor), (3 * factor)), rotateBottom), ((toX + 9) * factor), ((toY + 3) * factor), null);
                graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(bedImage, (fromX * factor), ((fromY + 3) * factor), (3 * factor), (3 * factor)), -90), (toX * factor), ((toY + 3) * factor), null);
                graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(bedImage, ((fromX + 3) * factor), ((fromY + 3) * factor), (3 * factor), (3 * factor)), 180), ((toX + 6) * factor), (toY * factor), null);
                graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(bedImage, ((fromX + 6) * factor), ((fromY + 3) * factor), (3 * factor), (3 * factor)), 90), ((toX + 6) * factor), ((toY + 3) * factor), null);
                graphics.drawImage(ImageUtil.rotate(ImageUtil.crop(bedImage, ((fromX + 9) * factor), ((fromY + 3) * factor), (3 * factor), (3 * factor)), 180), ((toX + 3) * factor), (toY * factor), null);
            }

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, BED_PATH + "/" + bedColor + ".png"), newBedImage, "png");
        }
    }
}
