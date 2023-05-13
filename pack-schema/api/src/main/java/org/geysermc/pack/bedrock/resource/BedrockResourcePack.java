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

package org.geysermc.pack.bedrock.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.geysermc.pack.bedrock.resource.attachables.Attachables;
import org.geysermc.pack.bedrock.resource.textures.ItemTexture;
import org.geysermc.pack.bedrock.resource.textures.TerrainTexture;
import org.geysermc.pack.bedrock.resource.textures.itemtexture.TextureData;
import org.geysermc.pack.bedrock.resource.textures.terraintexture.texturedata.Textures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.geysermc.pack.bedrock.resource.util.FileUtil.exportJson;

/**
 * Represents a Bedrock resource pack.
 */
public class BedrockResourcePack {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    private final Path directory;
    private Manifest manifest;

    private ItemTexture itemTexture;
    private TerrainTexture terrainTexture;
    private Map<String, Attachables> attachables;

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
    @NotNull
    public Manifest manifest() {
        return this.manifest;
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
     * Exports the resource pack to the specified directory.
     *
     * @throws IOException if an error occurs while exporting the resource pack
     */
    public void export() throws IOException {
        if (this.manifest == null) {
            throw new NullPointerException("Pack manifest cannot be null");
        }

        exportJson(MAPPER, this.directory.resolve("manifest.json"), this.manifest);

        if (this.itemTexture != null) {
            exportJson(MAPPER, this.directory.resolve("textures/item_texture.json"), this.itemTexture);
        }

        if (this.terrainTexture != null) {
            exportJson(MAPPER, this.directory.resolve("textures/terrain_texture.json"), this.terrainTexture);
        }
    }
}
