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

package org.geysermc.pack.converter.data;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.model.ModelStitcher;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.model.Model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModelConversionData extends BaseConversionData {
    private final Map<Key, Model> stitchedModels = new HashMap<>();
    @Getter
    private final ModelStitcher.Provider modelProvider;

    public ModelConversionData(@NotNull Path inputDirectory, @NotNull Path outputDirectory, ModelStitcher.Provider modelProvider) {
        super(inputDirectory, outputDirectory);
        this.modelProvider = modelProvider;
    }

    public void addStitchedModel(@NotNull Model model) {
        this.stitchedModels.put(model.key(), model);
    }

    @NotNull
    public Model model(@NotNull Key key) {
        return this.stitchedModels.get(key);
    }
}
