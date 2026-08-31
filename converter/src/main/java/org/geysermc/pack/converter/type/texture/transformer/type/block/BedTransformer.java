/*
 * Copyright (c) 2019-2026 GeyserMC. http://geysermc.org
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
        BufferedImage bedHeadNorthImage = this.texture(context, "bed_head_north");
        BufferedImage bedDownImage = this.texture(context, "bed_down");

        if (bedHeadNorthImage == null || bedDownImage == null) {
            return;
        }

        float scale = bedDownImage.getWidth() / 16f;

        // Both halves share the underside
        BufferedImage bedDownFace = ImageUtil.rotate(bedDownImage, 180);

        for (String bedColor : BED_COLORS) {
            BufferedImage bedHeadUpImage = this.texture(context, bedColor + "_bed_head_up");
            if (bedHeadUpImage == null) {
                continue;
            }

            BufferedImage bedHeadEastImage = this.texture(context, bedColor + "_bed_head_east");
            BufferedImage bedHeadWestImage = this.texture(context, bedColor + "_bed_head_west");
            BufferedImage bedFootUpImage = this.texture(context, bedColor + "_bed_foot_up");
            BufferedImage bedFootSouthImage = this.texture(context, bedColor + "_bed_foot_south");
            BufferedImage bedFootEastImage = this.texture(context, bedColor + "_bed_foot_east");
            BufferedImage bedFootWestImage = this.texture(context, bedColor + "_bed_foot_west");

            context.debug(String.format("Convert bed %s", bedColor));

            BufferedImage newBedImage = new BufferedImage((int) (scale * 64), (int) (scale * 64), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = newBedImage.getGraphics();

            // Head mattress
            graphics.drawImage(ImageUtil.rotate(this.mattressFace(bedHeadNorthImage, scale), 180), (int) (6 * scale), 0, null);
            graphics.drawImage(ImageUtil.rotate(this.mattressFace(bedHeadEastImage, scale), 90), (int) (22 * scale), (int) (6 * scale), null);
            graphics.drawImage(ImageUtil.rotate(this.mattressFace(bedHeadWestImage, scale), 270), 0, (int) (6 * scale), null);
            graphics.drawImage(bedHeadUpImage, (int) (6 * scale), (int) (6 * scale), null);
            graphics.drawImage(bedDownFace, (int) (28 * scale), (int) (6 * scale), null);

            // Foot mattress
            graphics.drawImage(ImageUtil.flip(this.mattressFace(bedFootSouthImage, scale), false, true), (int) (22 * scale), 0, null);
            graphics.drawImage(ImageUtil.rotate(this.mattressFace(bedFootEastImage, scale), 90), (int) (22 * scale), (int) (22 * scale), null);
            graphics.drawImage(ImageUtil.rotate(this.mattressFace(bedFootWestImage, scale), 270), 0, (int) (22 * scale), null);
            graphics.drawImage(bedFootUpImage, (int) (6 * scale), (int) (22 * scale), null);
            graphics.drawImage(bedDownFace, (int) (28 * scale), (int) (22 * scale), null);

            // Java has no up face for a leg, so its down face fills both ends, turned to match Bedrock
            BufferedImage headWestLegEnd = ImageUtil.rotate(this.legFace(bedHeadWestImage, scale, 6), 180);
            BufferedImage headEastLegEnd = ImageUtil.rotate(this.legFace(bedHeadEastImage, scale, 7), 180);
            BufferedImage footWestLegEnd = ImageUtil.rotate(this.legFace(bedFootWestImage, scale, 7), 180);
            BufferedImage footEastLegEnd = ImageUtil.rotate(this.legFace(bedFootEastImage, scale, 6), 180);

            // Head leg at the north west corner
            graphics.drawImage(ImageUtil.flip(this.legFace(bedHeadWestImage, scale, 0), false, true), (int) (3 * scale), (int) (38 * scale), null); // west
            graphics.drawImage(ImageUtil.flip(this.legFace(bedHeadWestImage, scale, 3), false, true), (int) (6 * scale), (int) (38 * scale), null); // south
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedHeadNorthImage, scale, 13), 90), 0, (int) (41 * scale), null); // north
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedHeadNorthImage, scale, 10), 90), (int) (6 * scale), (int) (41 * scale), null); // east
            graphics.drawImage(headWestLegEnd, (int) (3 * scale), (int) (41 * scale), null);
            graphics.drawImage(headWestLegEnd, (int) (9 * scale), (int) (41 * scale), null);

            // Head leg at the north east corner
            graphics.drawImage(ImageUtil.flip(this.legFace(bedHeadNorthImage, scale, 0), true, false), (int) (15 * scale), (int) (38 * scale), null); // north
            graphics.drawImage(ImageUtil.flip(this.legFace(bedHeadEastImage, scale, 10), false, true), (int) (18 * scale), (int) (38 * scale), null); // south
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedHeadNorthImage, scale, 3), 270), (int) (12 * scale), (int) (41 * scale), null); // west
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedHeadEastImage, scale, 13), 90), (int) (18 * scale), (int) (41 * scale), null); // east
            graphics.drawImage(headEastLegEnd, (int) (15 * scale), (int) (41 * scale), null);
            graphics.drawImage(headEastLegEnd, (int) (21 * scale), (int) (41 * scale), null);

            // Foot leg at the south west corner
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootWestImage, scale, 10), 180), (int) (3 * scale), (int) (44 * scale), null); // north
            graphics.drawImage(ImageUtil.flip(this.legFace(bedFootWestImage, scale, 13), true, false), (int) (6 * scale), (int) (44 * scale), null); // west
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootSouthImage, scale, 0), 90), 0, (int) (47 * scale), null); // south
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootSouthImage, scale, 3), 90), (int) (6 * scale), (int) (47 * scale), null); // east
            graphics.drawImage(footWestLegEnd, (int) (3 * scale), (int) (47 * scale), null);
            graphics.drawImage(footWestLegEnd, (int) (9 * scale), (int) (47 * scale), null);

            // Foot leg at the south east corner
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootEastImage, scale, 3), 180), (int) (15 * scale), (int) (44 * scale), null); // north
            graphics.drawImage(ImageUtil.flip(this.legFace(bedFootEastImage, scale, 0), true, false), (int) (18 * scale), (int) (44 * scale), null); // east
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootSouthImage, scale, 10), 270), (int) (12 * scale), (int) (47 * scale), null); // west
            graphics.drawImage(ImageUtil.rotate(this.legFace(bedFootSouthImage, scale, 13), 270), (int) (18 * scale), (int) (47 * scale), null); // south
            graphics.drawImage(footEastLegEnd, (int) (15 * scale), (int) (47 * scale), null);
            graphics.drawImage(footEastLegEnd, (int) (21 * scale), (int) (47 * scale), null);

            graphics.dispose();

            // Bedrock calls the light gray bed silver
            String bedrockColor = bedColor.equals("light_gray") ? "silver" : bedColor;

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BED_PATH + "/" + bedrockColor + ".png"), newBedImage, "png");
        }
    }

    /**
     * Read a bed block texture, falling back to the vanilla one
     *
     * @param context Context to read from
     * @param name Name of the texture, without its directory or extension
     * @return The texture image
     */
    private BufferedImage texture(TransformContext context, String name) throws IOException {
        Texture texture = context.pollOrPeekVanilla(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "block/" + name + ".png"));
        return texture == null ? null :this.readImage(texture);
    }

    /**
     * Crop the mattress out of one of the bed side textures
     *
     * @param side Side texture to crop from
     * @param scale Resolution multiplier of the pack
     * @return The mattress face
     */
    private BufferedImage mattressFace(BufferedImage side, float scale) {
        return ImageUtil.crop(side, 0, 7 * scale, 16 * scale, 6 * scale);
    }

    /**
     * Crop a leg face from a bed side texture, offsets come from the template_bed_head/foot uvs
     *
     * @param side Side texture to crop from
     * @param scale Resolution multiplier of the pack
     * @param sourceX Vanilla X the face starts at
     * @return The leg face
     */
    private BufferedImage legFace(BufferedImage side, float scale, int sourceX) {
        return ImageUtil.crop(side, sourceX * scale, 13 * scale, 3 * scale, 3 * scale);
    }
}
