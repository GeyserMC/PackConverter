package org.geysermc.pack.bedrock.resource.textures.itemtexture;

import java.lang.String;

/**
 * Texture Data
 */
public class TextureData {
  public String textures;

  /**
   * A texture file.
   *
   * @return Texture
   */
  public String textures() {
    return this.textures;
  }

  /**
   * A texture file.
   *
   * @param textures Texture
   */
  public void textures(String textures) {
    this.textures = textures;
  }
}
