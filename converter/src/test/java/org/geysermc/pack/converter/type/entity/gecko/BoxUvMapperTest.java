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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BoxUvMapperTest {

    // A symmetric 8x8x8 cube at UV anchor (0,0) - e.g. a vanilla arm/leg cube.
    // Every face occupies an equal 8x8 texel square, laid out left-to-right:
    // [down][up]
    // [west][north][east][south]
    @Test
    void expand_symmetricCube_atOrigin() {
        Uv uv = BoxUvMapper.expand(0, 0, new float[] { 8, 8, 8 });

        assertArrayEquals(new float[] { 8, 0 }, uv.down().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.down().uvSize());

        assertArrayEquals(new float[] { 16, 0 }, uv.up().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.up().uvSize());

        assertArrayEquals(new float[] { 0, 8 }, uv.west().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.west().uvSize());

        assertArrayEquals(new float[] { 8, 8 }, uv.north().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.north().uvSize());

        assertArrayEquals(new float[] { 16, 8 }, uv.east().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.east().uvSize());

        assertArrayEquals(new float[] { 24, 8 }, uv.south().uv());
        assertArrayEquals(new float[] { 8, 8 }, uv.south().uvSize());
    }

    // An asymmetric 4(x) x 12(y) x 2(z) cube at a non-zero anchor (10, 20), to make sure
    // width/height/depth aren't accidentally swapped between faces.
    @Test
    void expand_asymmetricCube_nonZeroAnchor() {
        Uv uv = BoxUvMapper.expand(10, 20, new float[] { 4, 12, 2 });

        // down:  x = u+dz = 12,          y = v = 20,  w = dx = 4, h = dz = 2
        assertArrayEquals(new float[] { 12, 20 }, uv.down().uv());
        assertArrayEquals(new float[] { 4, 2 }, uv.down().uvSize());

        // up:    x = u+dz+dx = 16,       y = v = 20,  w = dx = 4, h = dz = 2
        assertArrayEquals(new float[] { 16, 20 }, uv.up().uv());
        assertArrayEquals(new float[] { 4, 2 }, uv.up().uvSize());

        // west:  x = u = 10,             y = v+dz = 22, w = dz = 2, h = dy = 12
        assertArrayEquals(new float[] { 10, 22 }, uv.west().uv());
        assertArrayEquals(new float[] { 2, 12 }, uv.west().uvSize());

        // north: x = u+dz = 12,          y = v+dz = 22, w = dx = 4, h = dy = 12
        assertArrayEquals(new float[] { 12, 22 }, uv.north().uv());
        assertArrayEquals(new float[] { 4, 12 }, uv.north().uvSize());

        // east:  x = u+dz+dx = 16,       y = v+dz = 22, w = dz = 2, h = dy = 12
        assertArrayEquals(new float[] { 16, 22 }, uv.east().uv());
        assertArrayEquals(new float[] { 2, 12 }, uv.east().uvSize());

        // south: x = u+dz+dx+dz = 18,    y = v+dz = 22, w = dx = 4, h = dy = 12
        assertArrayEquals(new float[] { 18, 22 }, uv.south().uv());
        assertArrayEquals(new float[] { 4, 12 }, uv.south().uvSize());
    }

    @Test
    void expand_allSixFacesArePresent() {
        Uv uv = BoxUvMapper.expand(0, 0, new float[] { 1, 1, 1 });

        assertArrayEquals(new float[] { 1, 0 }, uv.down().uv());
        assertArrayEquals(new float[] { 2, 0 }, uv.up().uv());
        assertArrayEquals(new float[] { 0, 1 }, uv.west().uv());
        assertArrayEquals(new float[] { 1, 1 }, uv.north().uv());
        assertArrayEquals(new float[] { 2, 1 }, uv.east().uv());
        assertArrayEquals(new float[] { 3, 1 }, uv.south().uv());
    }
}
