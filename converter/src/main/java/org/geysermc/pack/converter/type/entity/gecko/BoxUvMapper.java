/*
 * Copyright (c) 2019-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.entity.gecko;

import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Down;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.East;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.North;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.South;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Up;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.West;

/**
 * Expands a shorthand "box UV" anchor point ({@code [u, v]}) into the 6 explicit per-face UV
 * rectangles Bedrock's {@link Uv} schema expects, using the standard Minecraft "cube net" layout
 * that both Java Edition (see {@code net.minecraft.client.model.geom.ModelPart}) and Bedrock
 * Edition use for box-UV cubes. This is the same layout Blockbench uses when "Box UV" is enabled
 * for a cube, which is also the mode GeckoLib model cubes commonly use.
 * <p>
 * Layout (top-left corner of each face's rectangle, given anchor {@code (u, v)} and cube size
 * {@code (dx, dy, dz)}):
 * <pre>
 *              +----------+----------+
 *              |   down   |    up    |   (row at y = v, each dx wide, dz tall)
 *   +----------+----------+----------+----------+
 *   |   west   |  north   |   east   |  south   |   (row at y = v+dz, alternating dz/dx wide, dy tall)
 *   +----------+----------+----------+----------+
 * </pre>
 * <b>Important:</b> this specific algorithm has not been validated in-game against a real Bedrock
 * client by us — it is implemented directly from the well-documented, long-stable community
 * specification of the box-UV layout. Before relying on this for a real model, export the same
 * cube from Blockbench with "Box UV" enabled and diff the generated per-face rectangles against
 * this method's output to confirm there's no off-by-one or up/down orientation mismatch.
 */
public final class BoxUvMapper {

    private BoxUvMapper() {
    }

    /**
     * Expands a box-UV anchor into a full per-face {@link Uv}.
     *
     * @param anchorU the U (horizontal) texture coordinate of the anchor, in texels
     * @param anchorV the V (vertical) texture coordinate of the anchor, in texels
     * @param size    the cube's {@code [x, y, z]} size, in model units (equal to texel counts
     *                for an unscaled texture, which is the standard Minecraft convention)
     * @return a fully populated {@link Uv} with all 6 faces set
     */
    public static Uv expand(float anchorU, float anchorV, float[] size) {
        float dx = size[0];
        float dy = size[1];
        float dz = size[2];

        Uv uv = new Uv();

        Down down = new Down();
        down.uv(new float[] { anchorU + dz, anchorV });
        down.uvSize(new float[] { dx, dz });
        uv.down(down);

        Up up = new Up();
        up.uv(new float[] { anchorU + dz + dx, anchorV });
        up.uvSize(new float[] { dx, dz });
        uv.up(up);

        West west = new West();
        west.uv(new float[] { anchorU, anchorV + dz });
        west.uvSize(new float[] { dz, dy });
        uv.west(west);

        North north = new North();
        north.uv(new float[] { anchorU + dz, anchorV + dz });
        north.uvSize(new float[] { dx, dy });
        uv.north(north);

        East east = new East();
        east.uv(new float[] { anchorU + dz + dx, anchorV + dz });
        east.uvSize(new float[] { dz, dy });
        uv.east(east);

        South south = new South();
        south.uv(new float[] { anchorU + dz + dx + dz, anchorV + dz });
        south.uvSize(new float[] { dx, dy });
        uv.south(south);

        return uv;
    }
}
