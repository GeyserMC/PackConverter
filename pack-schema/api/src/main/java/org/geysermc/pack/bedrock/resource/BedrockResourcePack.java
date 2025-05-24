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

package org.geysermc.pack.bedrock.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.geysermc.pack.bedrock.resource.attachables.Attachables;
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.render_controllers.RenderControllers;
import org.geysermc.pack.bedrock.resource.sounds.SoundDefinitions;
import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.Sounds;
import org.geysermc.pack.bedrock.resource.textures.FlipbookTexture;
import org.geysermc.pack.bedrock.resource.textures.ItemTexture;
import org.geysermc.pack.bedrock.resource.textures.TerrainTexture;
import org.geysermc.pack.bedrock.resource.textures.itemtexture.TextureData;
import org.geysermc.pack.bedrock.resource.textures.terraintexture.texturedata.Textures;
import org.geysermc.pack.util.gson.EmptyArrayAdapterFactory;
import org.geysermc.pack.util.gson.EmptyMapAdapterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.geysermc.pack.util.FileUtil.exportJson;
import static org.geysermc.pack.util.FileUtil.exportProperties;

/**
 * Represents a Bedrock resource pack.
 */
public class BedrockResourcePack {
    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(new EmptyArrayAdapterFactory())
            .registerTypeAdapterFactory(new EmptyMapAdapterFactory())
            .create();

    private final Path directory;
    private Manifest manifest;
    private byte[] icon;

    private ItemTexture itemTexture;
    private TerrainTexture terrainTexture;
    private Map<String, Attachables> attachables;
    private SoundDefinitions soundDefinitions;
    private Languages languages;
    private Map<String, RenderControllers> renderControllers;
    private Map<String, FlipbookTexture> flipbookTextures;

    private Map<String, ModelEntity> blockModels;
    private Map<String, ModelEntity> entityModels;
    private Map<String, byte[]> extraFiles;

    public BedrockResourcePack(@NotNull Path directory) {
        this(directory, null, null, null);
    }

    public BedrockResourcePack(@NotNull Path directory, @Nullable Manifest manifest, @Nullable ItemTexture itemTexture, @Nullable TerrainTexture terrainTexture) {
        this.directory = directory;
        this.manifest = manifest;
        this.itemTexture = itemTexture;
        this.terrainTexture = terrainTexture;
    }

    /**
     * Get the manifest of the resource pack.
     *
     * @return the manifest of the resource pack
     */
    @Nullable
    public Manifest manifest() {
        return this.manifest;
    }

    /**
     * Get the icon of the resource pack.
     *
     * @return the icon of the resource pack
     */
    public byte @Nullable[] icon() {
        return this.icon;
    }

    /**
     * Set the icon of the resource pack.
     *
     * @param icon the icon of the resource pack
     */
    public void icon(byte @Nullable[] icon) {
        this.icon = icon;
    }

    /**
     * Set the manifest of the resource pack.
     *
     * @param manifest the manifest of the resource pack
     */
    public void manifest(@NotNull Manifest manifest) {
        this.manifest = manifest;
    }

    /**
     * Get the item texture of the resource pack.
     *
     * @return the item texture of the resource pack
     */
    @Nullable
    public ItemTexture itemTexture() {
        return this.itemTexture;
    }

    /**
     * Set the item texture of the resource pack.
     *
     * @param itemTexture the item texture of the resource pack
     */
    public void itemTexture(@Nullable ItemTexture itemTexture) {
        this.itemTexture = itemTexture;
    }

    /**
     * Get the terrain texture of the resource pack.
     *
     * @return the terrain texture of the resource pack
     */
    @Nullable
    public TerrainTexture terrainTexture() {
        return this.terrainTexture;
    }

    /**
     * Set the terrain texture of the resource pack.
     *
     * @param terrainTexture the terrain texture of the resource pack
     */
    public void terrainTexture(@Nullable TerrainTexture terrainTexture) {
        this.terrainTexture = terrainTexture;
    }


    /**
     * Get the flipbook textures of the resource pack.
     *
     * @return the flipbook textures of the resource pack
     */
    @Nullable
    public Map<String, FlipbookTexture> flipbookTextures() {
        return this.flipbookTextures;
    }

    /**
     * Set the flipbook textures of the resource pack.
     *
     * @param flipbookTextures the flipbook textures of the resource pack
     */
    public void flipbookTextures(@Nullable Map<String, FlipbookTexture> flipbookTextures) {
        this.flipbookTextures = flipbookTextures;
    }

    /**
     * Get the attachables of the resource pack.
     *
     * @return the attachables of the resource pack
     */
    @Nullable
    public Map<String, Attachables> attachables() {
        return this.attachables;
    }

    /**
     * Set the attachables of the resource pack.
     *
     * @param attachables the attachables of the resource pack
     */
    public void attachables(@Nullable Map<String, Attachables> attachables) {
        this.attachables = attachables;
    }

    /**
     * Get the render controllers of the resource pack.
     *
     * @return the render controllers of the resource pack
     */
    @Nullable
    public Map<String, RenderControllers> renderControllers() {
        return this.renderControllers;
    }

    /**
     * Set the render controllers of the resource pack.
     *
     * @param renderControllers the render controllers of the resource pack
     */
    public void renderControllers(@Nullable Map<String, RenderControllers> renderControllers) {
        this.renderControllers = renderControllers;
    }

    /**
     * Get the block models of the resource pack.
     *
     * @return the block models of the resource pack
     */
    @Nullable
    public Map<String, ModelEntity> blockModels() {
        return this.blockModels;
    }

    /**
     * Set the block models of the resource pack.
     *
     * @param blockModels the block models of the resource pack
     */
    public void blockModels(@Nullable Map<String, ModelEntity> blockModels) {
        this.blockModels = blockModels;
    }

    /**
     * Get the entity models of the resource pack.
     *
     * @return the entity models of the resource pack
     */
    @Nullable
    public Map<String, ModelEntity> entityModels() {
        return this.entityModels;
    }

    /**
     * Set the entity models of the resource pack.
     *
     * @param entityModels the entity models of the resource pack
     */
    public void entityModels(@Nullable Map<String, ModelEntity> entityModels) {
        this.entityModels = entityModels;
    }

    /**
     * Get the sound definitions of the resource pack.
     *
     * @return the sound definitions of the resource pack
     */
    @Nullable
    public SoundDefinitions soundDefinitions() {
        return this.soundDefinitions;
    }

    /**
     * Set the sound definitions of the resource pack.
     *
     * @param soundDefinitions the sound definitions of the resource pack
     */
    public void soundDefinitions(@Nullable SoundDefinitions soundDefinitions) {
        this.soundDefinitions = soundDefinitions;
    }

    /**
     * Get the languages of the resource pack.
     *
     * @return the languages of the resource pack
     */
    @Nullable
    public Languages languages() {
        return this.languages;
    }

    /**
     * Set the languages of the resource pack.
     *
     * @param languages the languages of the resource pack
     */
    public void languages(@Nullable Languages languages) {
        this.languages = languages;
    }

    /**
     * Get the extra files of the resource pack.
     *
     * @return the extra files of the resource pack
     */
    @Nullable
    public Map<String, byte[]> extraFiles() {
        return this.extraFiles;
    }

    /**
     * Set the files of the resource pack.
     *
     * @param extraFiles the extra files of the resource pack
     */
    public void extraFiles(@Nullable Map<String, byte[]> extraFiles) {
        this.extraFiles = extraFiles;
    }

    /**
     * Add an item to the resource pack.
     *
     * @param id the id of the item
     * @param textureLocation the location of the texture
     */
    public void addItemTexture(@NotNull String id, @NotNull String textureLocation) {
        if (this.itemTexture == null) {
            this.itemTexture = new ItemTexture();
            this.itemTexture.resourcePackName(this.manifest.header().name());
            this.itemTexture.textureName("atlas.items");
        }

        TextureData data = new TextureData();
        data.textures(textureLocation);

        this.itemTexture.textureData().put(id, data);
    }

    /**
     * Add a block texture to the resource pack.
     *
     * @param id the id of the block texture
     * @param textureLocation the location of the texture
     */
    public void addBlockTexture(@NotNull String id, @NotNull String textureLocation) {
        if (this.terrainTexture == null) {
            this.terrainTexture = new TerrainTexture();
            this.terrainTexture.resourcePackName(this.manifest.header().name());
            this.terrainTexture.textureName("atlas.terrain");
            this.terrainTexture.padding(8);
            this.terrainTexture.numMipLevels(4);
        }

        org.geysermc.pack.bedrock.resource.textures.terraintexture.TextureData data = new org.geysermc.pack.bedrock.resource.textures.terraintexture.TextureData();
        Textures textures = new Textures();
        textures.path(textureLocation);
        data.textures(textures);

        this.terrainTexture.textureData().put(id, data);
    }

    public void addFlipbookTexture(@NotNull String id, @NotNull String textureLocation, @NotNull int ticksPerFrame) {
        if (this.flipbookTextures == null) {
            this.flipbookTextures = new HashMap<>();
        }

        FlipbookTexture texture = new FlipbookTexture();
        texture.atlasTile(id);
        texture.flipbookTexture(textureLocation);
        texture.ticksPerFrame(ticksPerFrame);

        this.flipbookTextures.put(id, texture);
    }

    /**
     * Add an attachable to the resource pack.
     *
     * @param armorAttachable the data of the attachable
     * @param location the location of the final json
     */
    public void addAttachable(@NotNull Attachables armorAttachable, @NotNull String location) {
        if (this.attachables == null) {
            this.attachables = new HashMap<>();
        }

        this.attachables.put(location, armorAttachable);
    }

    /**
     * Add a render controller to the resource pack.
     *
     * @param renderController the data of the render controller
     * @param location the location of the final json
     */
    public void addRenderController(@NotNull RenderControllers renderController, String location) {
        if (this.renderControllers == null) {
            this.renderControllers = new HashMap<>();
        }

        this.renderControllers.put(location, renderController);
    }

    /**
     * Add a block model to the resource pack.
     *
     * @param model the data of the block model
     * @param location the location of the final json
     */
    public void addBlockModel(@NotNull ModelEntity model, @NotNull String location) {
        if (this.blockModels == null) {
            this.blockModels = new HashMap<>();
        }

        this.blockModels.put("models/blocks/" + location, model);
    }

    /**
     * Add an entity model to the resource pack.
     *
     * @param model the data of the entity model
     * @param location the location of the final json
     */
    public void addEntityModel(@NotNull ModelEntity model, @NotNull String location) {
        if (this.entityModels == null) {
            this.entityModels = new HashMap<>();
        }

        this.entityModels.put("models/entity/" + location, model);
    }

    /**
     * Add a sound to the resource pack with the default options set.
     *
     * @param id the id of the sound
     * @param soundLocation the location of the sound
     */
    public void addDefaultSound(@NotNull String id, @NotNull String soundLocation) {
        if (this.soundDefinitions == null) {
            this.soundDefinitions = new SoundDefinitions();
            this.soundDefinitions.formatVersion("1.14.0");
        }

        Sounds sounds = new Sounds();
        sounds.name(soundLocation);
        sounds.loadOnLowMemory(true);
        sounds.stream(true);
        sounds.volume(1);
        sounds.is3D(true);
        sounds.pitch(1);

        org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions data = new org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions();
        data.sounds().add(sounds);
        data.maxDistance(64);

        this.soundDefinitions.soundDefinitions().put(id, data);
    }

    /**
     * Add a sound to the resource pack.
     *
     * @param id the id of the sound
     * @param soundDefinition the sound definition
     */
    public void addSoundDefinition(@NotNull String id, @NotNull org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions soundDefinition) {
        if (this.soundDefinitions == null) {
            this.soundDefinitions = new SoundDefinitions();
            this.soundDefinitions.formatVersion("1.14.0");
        }

        this.soundDefinitions.soundDefinitions().put(id, soundDefinition);
    }

    /**
     * Add a language to the resource pack.
     *
     * @param languageCode the language code
     * @param translationStrings the translation strings
     */
    public void addLanguage(@NotNull String languageCode, @NotNull Map<String, String> translationStrings) {
        if (this.languages == null) {
            this.languages = new Languages();
        }

        this.languages.language(languageCode, translationStrings);
    }

    /**
     * Add an extra file to the resource pack.
     *
     * @param bytes the bytes of the file
     * @param location the location of the file
     */
    public void addExtraFile(byte[] bytes, @NotNull String location) {
        if (this.extraFiles == null) {
            this.extraFiles = new HashMap<>();
        }

        this.extraFiles.put(location, bytes);
    }

    /**
     * Exports the resource pack to the specified directory.
     *
     * @throws IOException if an error occurs while exporting the resource pack
     */
    public void export() throws IOException {
        if (this.manifest == null) {
            throw new NullPointerException("Pack manifest cannot be null");
        }

        exportJson(GSON, this.directory.resolve("manifest.json"), this.manifest);
        if (this.icon != null) {
            Files.write(this.directory.resolve("pack_icon.png"), this.icon);
        }

        if (this.itemTexture != null) {
            exportJson(GSON, this.directory.resolve("textures/item_texture.json"), this.itemTexture);
        }

        if (this.terrainTexture != null) {
            exportJson(GSON, this.directory.resolve("textures/terrain_texture.json"), this.terrainTexture);
        }

        if (this.flipbookTextures != null) {
            exportJson(GSON, this.directory.resolve("textures/flipbook_textures.json"), this.flipbookTextures.values());
        }

        if (this.attachables != null) {
            for (Map.Entry<String, Attachables> attachable : this.attachables.entrySet()) {
                exportJson(GSON, this.directory.resolve(attachable.getKey()), attachable.getValue());
            }
        }

        if (this.renderControllers != null) {
            for (Map.Entry<String, RenderControllers> renderController : this.renderControllers.entrySet()) {
                exportJson(GSON, this.directory.resolve(renderController.getKey()), renderController.getValue());
            }
        }

        if (this.blockModels != null) {
            for (Map.Entry<String, ModelEntity> blockModel : this.blockModels.entrySet()) {
                exportJson(GSON, this.directory.resolve(blockModel.getKey()), blockModel.getValue());
            }
        }

        if (this.entityModels != null) {
            for (Map.Entry<String, ModelEntity> entityModel : this.entityModels.entrySet()) {
                exportJson(GSON, this.directory.resolve(entityModel.getKey()), entityModel.getValue());
            }
        }

        if (this.soundDefinitions != null) {
            exportJson(GSON, this.directory.resolve("sounds/sound_definitions.json"), this.soundDefinitions);
        }

        if (this.languages != null) {
            exportJson(GSON, this.directory.resolve("texts/languages.json"), this.languages.languageCodes());

            for (Map.Entry<String, Map<String, String>> language : this.languages.languages().entrySet()) {
                exportProperties(this.directory.resolve("texts/" + language.getKey() + ".lang"), language.getValue());
            }
        }

        if (this.extraFiles != null) {
            for (Map.Entry<String, byte[]> extraFile : this.extraFiles.entrySet()) {
                Path extraFilePath = this.directory.resolve(extraFile.getKey());
                Files.createDirectories(extraFilePath.getParent());
                Files.write(extraFilePath, extraFile.getValue());
            }
        }
    }
}
