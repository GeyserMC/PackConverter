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

package org.geysermc.pack.converter.type.entity.gecko.raw;

import com.google.gson.JsonElement;

/**
 * Raw representation of a single GeckoLib/Bedrock-style cube.
 * <p>
 * {@link #uv} is intentionally left as a raw {@link JsonElement} rather than a typed object,
 * because both GeckoLib and Bedrock allow it to be specified in two different shapes:
 * <ul>
 *     <li>Shorthand box-UV: a plain {@code [u, v]} 2-element array, from which all 6 face
 *     rectangles are derived automatically using the standard "cube net" layout.</li>
 *     <li>Per-face: an object with optional {@code north}/{@code south}/{@code east}/{@code west}/
 *     {@code up}/{@code down} keys, each specifying its own {@code uv} and {@code uv_size}.</li>
 * </ul>
 * See {@link org.geysermc.pack.converter.type.entity.gecko.BoxUvMapper} for where the shorthand
 * form is expanded into explicit per-face rectangles.
 */
public class GeckoCube {

    public float[] origin;

    public float[] size;

    public float[] pivot;

    public float[] rotation;

    public Boolean mirror;

    public Float inflate;

    public JsonElement uv;
}
