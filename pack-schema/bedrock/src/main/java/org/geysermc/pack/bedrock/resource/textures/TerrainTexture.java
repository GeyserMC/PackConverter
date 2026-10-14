package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;
import java.lang.Integer;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.textures.terraintexture.TextureData;

/**
 * Terrain Texture File
 * <p>
 * An collection of texture definitions.
 */
public class TerrainTexture {
  @SerializedName("num_mip_levels")
  public Integer numMipLevels;

  public Integer padding;

  @SerializedName("resource_pack_name")
  public String resourcePackName;

  @SerializedName("texture_data")
  private Map<String, TextureData> textureData = new HashMap<>();

  @SerializedName("texture_name")
  public String textureName;

  /**
   * Sets the number of mipmap levels for reducing texture quality at varying distances. More mipmap levels means reduced texture quality at farther distances.
   *
   * @return Num Mip Levels
   */
  public Integer numMipLevels() {
    return this.numMipLevels;
  }

  /**
   * Sets the number of mipmap levels for reducing texture quality at varying distances. More mipmap levels means reduced texture quality at farther distances.
   *
   * @param numMipLevels Num Mip Levels
   */
  public void numMipLevels(int numMipLevels) {
    this.numMipLevels = numMipLevels;
  }

  /**
   * Adds buffer space to prevent textures from bleeding into each other.
   *
   * @return Padding
   */
  public Integer padding() {
    return this.padding;
  }

  /**
   * Adds buffer space to prevent textures from bleeding into each other.
   *
   * @param padding Padding
   */
  public void padding(int padding) {
    this.padding = padding;
  }

  /**
   * The name of your resource pack, which should match the name in this pack's manifest.
   *
   * @return Resource Pack Name
   */
  public String resourcePackName() {
    return this.resourcePackName;
  }

  /**
   * The name of your resource pack, which should match the name in this pack's manifest.
   *
   * @param resourcePackName Resource Pack Name
   */
  public void resourcePackName(String resourcePackName) {
    this.resourcePackName = resourcePackName;
  }

  /**
   * A JSON object with shortnames and texture data.
   *
   * @return Texture Data
   */
  public Map<String, TextureData> textureData() {
    return this.textureData;
  }

  /**
   * A JSON object with shortnames and texture data.
   *
   * @param textureData Texture Data
   */
  public void textureData(Map<String, TextureData> textureData) {
    this.textureData = textureData;
  }

  public String textureName() {
    return this.textureName;
  }

  public void textureName(String textureName) {
    this.textureName = textureName;
  }
}
