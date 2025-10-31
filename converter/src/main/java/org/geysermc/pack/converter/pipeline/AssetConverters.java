/*
 * Copyright (c) 2025-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.pipeline;

import com.google.gson.JsonElement;
import net.kyori.adventure.key.Keyed;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.bedrock.resource.Manifest;
import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions;
import org.geysermc.pack.converter.type.base.PackIconConverter;
import org.geysermc.pack.converter.type.base.PackManifestConverter;
import org.geysermc.pack.converter.type.lang.BedrockLanguage;
import org.geysermc.pack.converter.type.lang.LangConverter;
import org.geysermc.pack.converter.type.misc.SplashTextConverter;
import org.geysermc.pack.converter.type.model.BedrockModel;
import org.geysermc.pack.converter.type.model.ModelConverter;
import org.geysermc.pack.converter.type.sound.SoundConverter;
import org.geysermc.pack.converter.type.sound.SoundRegistryConverter;
import org.geysermc.pack.converter.type.texture.TextureConverter;
import org.geysermc.pack.converter.type.texture.transformer.TransformedTexture;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.lang.Language;
import team.unnamed.creative.metadata.pack.PackMeta;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.part.ResourcePackPart;
import team.unnamed.creative.serialize.minecraft.ResourceCategory;
import team.unnamed.creative.serialize.minecraft.language.LanguageSerializer;
import team.unnamed.creative.serialize.minecraft.sound.SoundSerializer;
import team.unnamed.creative.sound.Sound;
import team.unnamed.creative.sound.SoundRegistry;
import team.unnamed.creative.texture.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public final class AssetConverters {
    private static final List<ConverterPipeline<?, ?>> CONVERTERS = new ArrayList<>();
    private static boolean bootstrapped = false;

    public static final ConverterPipeline<PackMeta, Manifest> MANIFEST = createSingle(
            (pack, context) -> pack.packMeta(),
            PackManifestConverter.INSTANCE,
            BedrockResourcePack::manifest);
    public static final ConverterPipeline<Writable, byte[]> ICON = createSingle(PackIconConverter::extractIcon, PackIconConverter.INSTANCE, BedrockResourcePack::icon);
    public static final ConverterPipeline<Writable, JsonElement> SPLASH_TEXT = createSingle(
            (pack, context) -> pack.unknownFile("assets/minecraft/texts/splashes.txt"),
            SplashTextConverter.INSTANCE,
            (pack, splashes) -> pack.addExtraFile(splashes, "splashes.json"));
    public static final ConverterPipeline<Language, BedrockLanguage> LANGUAGE = create(extractor(LanguageSerializer.CATEGORY), LangConverter.INSTANCE);
    public static final ConverterPipeline<Model, BedrockModel> MODEL = create(ModelConverter.INSTANCE);
    public static final ConverterPipeline<SoundRegistry, Map<String, SoundDefinitions>> SOUND_REGISTRY = create(
            (pack, context) -> pack.soundRegistries(), SoundRegistryConverter.INSTANCE);
    public static final ConverterPipeline<Sound, Sound> SOUND = create(SoundConverter.INSTANCE);
    public static final ConverterPipeline<Texture, TransformedTexture> TEXTURE = create(TextureConverter.INSTANCE);

    private static <JavaAsset, BedrockAsset> ConverterPipeline<JavaAsset, BedrockAsset> createSingle(BiFunction<ResourcePack, ExtractionContext, JavaAsset> extractor,
                                                                                                     AssetConverter<JavaAsset, BedrockAsset> converter,
                                                                                                     BiConsumer<BedrockResourcePack, BedrockAsset> collector) {
        return create(
                (pack, context) -> Optional.ofNullable(extractor.apply(pack, context))
                        .map(List::of)
                        .orElse(List.of()),
                converter,
                (pack, assets, context) -> collector.accept(pack, assets.get(0)));
    }

    public static <JavaAsset, BedrockAsset,
            ConverterCombiner extends AssetConverter<JavaAsset, BedrockAsset>
                    & AssetCombiner<BedrockAsset>> ConverterPipeline<JavaAsset, BedrockAsset> create(AssetExtractor<JavaAsset> extractor,
                                                                                                     ConverterCombiner converterCombiner) {
        return create(extractor, converterCombiner, converterCombiner);
    }

    public static <JavaAsset, BedrockAsset,
            Pipeline extends AssetExtractor<JavaAsset> & AssetConverter<JavaAsset, BedrockAsset>
                    & AssetCombiner<BedrockAsset>> ConverterPipeline<JavaAsset, BedrockAsset> create(Pipeline pipeline) {
        return create(pipeline, pipeline, pipeline);
    }

    public static <JavaAsset, BedrockAsset> ConverterPipeline<JavaAsset, BedrockAsset> create(AssetExtractor<JavaAsset> extractor,
                                                                                              AssetConverter<JavaAsset, BedrockAsset> converter,
                                                                                              AssetCombiner<BedrockAsset> combiner) {
        ConverterPipeline<JavaAsset, BedrockAsset> pipeline = new ConverterPipeline<>(extractor, converter, combiner, false, Optional.empty());
        if (!bootstrapped) {
            CONVERTERS.add(pipeline);
        }
        return pipeline;
    }

    public static List<ConverterPipeline<?, ?>> converters(boolean experimental) {
        return List.copyOf(CONVERTERS).stream()
                .filter(converter -> experimental || !converter.experimental())
                .toList();
    }

    static {
        // This will cause 3rd-party converters made using the utility methods here to not be added to the CONVERTERS array
        bootstrapped = true;
    }

    private static <JavaAsset extends Keyed & ResourcePackPart> AssetExtractor<JavaAsset> extractor(ResourceCategory<JavaAsset> category) {
        return (pack, context) -> category.lister().apply(pack);
    }
}
