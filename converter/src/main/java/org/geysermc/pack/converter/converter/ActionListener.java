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

package org.geysermc.pack.converter.converter;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import team.unnamed.creative.ResourcePack;

import java.util.Collection;
import java.util.List;

/**
 * Listeners for actions that occur during execution of a {@link ConverterPipeline}. All implemented listeners should be as pure as possible:
 * no side effects should occur, and no modifications should be made to received arguments, unless specifically stated this is allowed.
 */
public interface ActionListener<JavaAsset, BedrockAsset> {

    /**
     * Executed after a {@link ConverterPipeline} has extracted all applicable {@link JavaAsset}s for conversion of a {@link ResourcePack}. This method
     * can be used to add extra {@link JavaAsset}s for conversion by adding them to the {@code extracted} collection, which is mutable.
     *
     * @param pack the resource pack that is used for extraction of {@link JavaAsset}s
     * @param extracted the {@link JavaAsset}s the {@link ConverterPipeline} has extracted from the resource pack
     * @param context the {@link ExtractionContext}
     */
    default void postExtract(ResourcePack pack, Collection<JavaAsset> extracted, ExtractionContext context) {
    }

    /**
     * Executed after a {@link ConverterPipeline} has converted a {@link JavaAsset} to a {@link BedrockAsset}. This method
     * can be used to modify the {@link BedrockAsset} after conversion.
     *
     * <p><em>Please note that the {@link BedrockAsset} should be considered immutable, and no modifications should be made to it. Instead,
     * return a modified copy.</em></p>
     *
     * @param asset the {@link JavaAsset} that was converted
     * @param bedrockAsset the resulting {@link BedrockAsset}
     * @param context the {@link ConversionContext}
     * @return the modified {@link BedrockAsset}, or return the original if no modifications were made
     */
    default BedrockAsset postConvert(JavaAsset asset, BedrockAsset bedrockAsset, ConversionContext context) {
        return bedrockAsset;
    }

    /**
     * Executed after a {@link ConverterPipeline} has added all converted {@link BedrockAsset}s to the {@link BedrockResourcePack}. This method
     * can be used to add additional assets to the output {@code pack}.
     *
     * @param pack the bedrock resource pack
     * @param assets the assets added to the bedrock resource pack (immutable)
     * @param context the {@link CombineContext}
     */
    default void postInclude(BedrockResourcePack pack, List<BedrockAsset> assets, CombineContext context) {
    }
}
