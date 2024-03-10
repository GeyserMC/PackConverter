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

package org.geysermc.pack.converter.converter.model;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.util.LogListener;
import org.geysermc.pack.converter.util.VanillaPackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.model.Element;
import team.unnamed.creative.model.ItemOverride;
import team.unnamed.creative.model.ItemTransform;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelStitcher {
    private final Provider provider;
    private final Model baseModel;

    private boolean ambientOcclusion;
    private final Map<ItemTransform.Type, ItemTransform> display = new HashMap<>();

    private List<ModelTexture> textureLayers = new ArrayList<>();
    private ModelTexture textureParticle;
    private Map<String, ModelTexture> textureVariables = new HashMap<>();

    private Model.GuiLight guiLight;
    private final List<Element> elements = new ArrayList<>();
    private final List<ItemOverride> overrides = new ArrayList<>();

    public ModelStitcher(@NotNull ModelStitcher.Provider provider, @NotNull Model baseModel) {
        this.provider = provider;
        this.baseModel = baseModel;
        this.ambientOcclusion = baseModel.ambientOcclusion();
        this.elements.addAll(baseModel.elements());
        this.overrides.addAll(baseModel.overrides());
        this.guiLight = baseModel.guiLight();
        this.display.putAll(baseModel.display());

        ModelTextures textures = baseModel.textures();
        if (textures != null) {
            this.textureLayers.addAll(textures.layers());
            this.textureParticle = textures.particle();
            this.textureVariables.putAll(textures.variables());
        }

        this.inheritTraits(baseModel);
    }

    private void inheritTraits(@NotNull Model model) {
        // If we have no parent model, that means this is as far as we're
        // going to get with the base model.
        if (model == this.baseModel && this.baseModel.parent() == null) {
            return;
        }

        List<Element> elements = model.elements();
        if (elements != null && !elements.isEmpty()) {
            this.elements.addAll(elements);
        }

        List<ItemOverride> overrides = model.overrides();
        if (overrides != null && !overrides.isEmpty()) {
            this.overrides.addAll(overrides);
        }

        Map<ItemTransform.Type, ItemTransform> display = model.display();
        if (display != null && !display.isEmpty()) {
            for (Map.Entry<ItemTransform.Type, ItemTransform> entry : display.entrySet()) {
                // Only add the display if we don't already have it
                if (this.display.containsKey(entry.getKey())) {
                    continue;
                }

                this.display.put(entry.getKey(), entry.getValue());
            }
        }

        ModelTextures textures = model.textures();
        if (textures != null) {
            List<ModelTexture> layers = textures.layers();
            if (layers != null && !layers.isEmpty()) {
                if (this.textureLayers == null) {
                    this.textureLayers = new ArrayList<>();
                }

                this.textureLayers.addAll(layers);
            }

            ModelTexture particle = textures.particle();
            if (particle != null && this.textureParticle == null) {
                this.textureParticle = particle;
            }

            Map<String, ModelTexture> variables = textures.variables();
            if (variables != null && !variables.isEmpty()) {
                if (this.textureVariables == null) {
                    this.textureVariables = new HashMap<>();
                }

                for (Map.Entry<String, ModelTexture> entry : variables.entrySet()) {
                    // Only add the variable if we don't already have it
                    if (this.textureVariables.containsKey(entry.getKey())) {
                        continue;
                    }

                    this.textureVariables.put(entry.getKey(), entry.getValue());
                }
            }
        }

        Model.GuiLight guiLight = model.guiLight();
        if (guiLight != null && this.guiLight == null) {
            this.guiLight = guiLight;
        }

        Key parentKey = model.parent();
        if (parentKey != null) {
            Model parentModel = this.provider.model(parentKey);
            if (parentModel == null) {
                System.err.println("Could not find parent model " + parentKey + " for model " + model.key());
                return;
            }

            this.inheritTraits(parentModel);
        }
    }

    public Model stitch() {
        return Model.model()
                .key(this.baseModel.key())
                .ambientOcclusion(this.ambientOcclusion)
                .display(this.display)
                .elements(this.elements)
                .guiLight(this.guiLight)
                .overrides(this.overrides)
                .textures(ModelTextures.builder()
                        .layers(this.textureLayers)
                        .particle(this.textureParticle)
                        .variables(this.textureVariables)
                        .build()
                )
                .build();
    }

    public interface Provider {

        @Nullable
        Model model(@NotNull Key key);
    }

    public static Provider baseProvider(@NotNull ResourcePack pack) {
        return pack::model;
    }

    public static Provider vanillaProvider(@NotNull ResourcePack pack, @NotNull LogListener log) {
        // Need to download the client jar, then use the
        // client jar to get the vanilla models, so we can
        // ensure all parents exist to convert them to Bedrock.
        // TODO Make the location of this configurable
        Path vanillaPackPath = Paths.get("vanilla-pack.zip");
        VanillaPackProvider.create(vanillaPackPath, log);

        ResourcePack vanillaResourcePack = MinecraftResourcePackReader.minecraft().readFromZipFile(vanillaPackPath);
        return key -> {
            Model model = pack.model(key);
            if (model == null) {
                return vanillaResourcePack.model(key);
            }

            return model;
        };
    }
}
