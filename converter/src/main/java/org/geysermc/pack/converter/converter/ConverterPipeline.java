/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
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
import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConverterPipeline<JavaAsset, BedrockAsset>
        implements AssetExtractor<JavaAsset>, AssetConverter<JavaAsset, BedrockAsset>, AssetCombiner<BedrockAsset> {
    private final AssetExtractor<JavaAsset> extractor;
    private final AssetConverter<JavaAsset, BedrockAsset> converter;
    private final AssetCombiner<BedrockAsset> combiner;
    private final Optional<ActionListener<JavaAsset, BedrockAsset>> listener;

    public ConverterPipeline(AssetExtractor<JavaAsset> extractor,
                             AssetConverter<JavaAsset, BedrockAsset> converter,
                             AssetCombiner<BedrockAsset> combiner,
                             Optional<ActionListener<JavaAsset, BedrockAsset>> listener) {
        this.extractor = extractor;
        this.converter = converter;
        this.combiner = combiner;
        this.listener = listener;
    }

    @Override
    public Collection<JavaAsset> extract(ResourcePack pack, ExtractionContext context) {
        Collection<JavaAsset> extracted = extractor.extract(pack, context);
        listener.ifPresent(actionListener -> actionListener.postExtract(pack, extracted, context));
        return extracted;
    }

    @Override
    public @Nullable BedrockAsset convert(JavaAsset asset, ConversionContext context) throws Exception {
        BedrockAsset converted = converter.convert(asset, context);
        return listener.map(actionListener -> actionListener.postConvert(asset, converted, context))
                .orElse(converted);
    }

    @Override
    public void include(BedrockResourcePack pack, List<BedrockAsset> assets, CombineContext context) {
        combiner.include(pack, assets, context);
        listener.ifPresent(actionListener -> actionListener.postInclude(pack, assets, context));
    }

    public int convert(ResourcePack pack, Optional<ResourcePack> vanillaPack, BedrockResourcePack bedrockPack, String packName, String textureSubDirectory, LogListener logListener) {
        ExtractionContext extractionContext = new ExtractionContext(bedrockPack, vanillaPack, logListener);
        ConversionContext conversionContext = new ConversionContext(packName, logListener);
        CombineContext combineContext = new CombineContext(textureSubDirectory, logListener);

        AtomicInteger errors = new AtomicInteger(0);
        List<BedrockAsset> converted = extract(pack, extractionContext).parallelStream()
                .map(asset -> {
                    try {
                        return convert(asset, conversionContext);
                    } catch (Exception exception) {
                        errors.incrementAndGet();
                        logListener.error("Failed to convert asset", exception);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
        if (!converted.isEmpty()) {
            include(bedrockPack, converted, combineContext);
        }

        return errors.get();
    }

    public ConverterPipeline<JavaAsset, BedrockAsset> withActionListener(ActionListener<JavaAsset, BedrockAsset> listener) {
        return new ConverterPipeline<>(extractor, converter, combiner, Optional.of(listener));
    }
}
