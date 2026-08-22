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

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Raw representation of a GeckoLib {@code .geo.json} model file.
 * <p>
 * GeckoLib's model format was intentionally designed to closely mirror Bedrock Edition's own
 * entity geometry format (both ultimately trace back to the same "Minecraft geometry" schema
 * used by Blockbench), which is what makes a fairly direct, mostly 1:1 conversion possible here.
 * <p>
 * This class only models the subset of the format PackConverter can meaningfully translate.
 * GeckoLib-specific extensions with no Bedrock resource-pack equivalent (poly meshes, item display
 * transforms, etc.) are intentionally not modelled here and will be ignored during conversion.
 *
 * @see <a href="https://github.com/bernie-g/geckolib/wiki/Making-Your-Models-(Blockbench)">GeckoLib model docs</a>
 */
public class GeckoModel {

    @SerializedName("format_version")
    public String formatVersion;

    @SerializedName("minecraft:geometry")
    public List<GeckoGeometry> geometry = new ArrayList<>();
}
