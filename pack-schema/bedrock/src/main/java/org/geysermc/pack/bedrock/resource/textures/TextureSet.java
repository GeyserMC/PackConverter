package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;

public class TextureSet {
  @SerializedName("minecraft:texture_set")
  public org.geysermc.pack.bedrock.resource.textures.textureset.TextureSet textureSet;

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
