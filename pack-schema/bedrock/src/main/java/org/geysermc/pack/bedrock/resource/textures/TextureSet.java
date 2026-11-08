package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

public class TextureSet {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:texture_set")
  public org.geysermc.pack.bedrock.resource.textures.textureset.TextureSet textureSet;

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * Texture Sets are used to define multiple PBR layers for a texture resource.
   *
   * @return Texture Set
   */
  public org.geysermc.pack.bedrock.resource.textures.textureset.TextureSet textureSet() {
    return this.textureSet;
  }

  /**
   * Texture Sets are used to define multiple PBR layers for a texture resource.
   *
   * @param textureSet Texture Set
   */
  public void textureSet(
      org.geysermc.pack.bedrock.resource.textures.textureset.TextureSet textureSet) {
    this.textureSet = textureSet;
  }
}
