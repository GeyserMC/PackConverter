package org.geysermc.pack.bedrock.resource.textures.terraintexture;

import org.geysermc.pack.bedrock.resource.textures.terraintexture.texturedata.Textures;

/**
 * Texture Data
 * <p>
 * A JSON object with shortnames and texture data.
 */
public class TextureData {
  public Textures textures;

  /**
   * A collection of texture files.
   *
   * @return Texture
   */
  public Textures textures() {
    return this.textures;
  }

  /**
   * A collection of texture files.
   *
   * @param textures Texture
   */
  public void textures(Textures textures) {
    this.textures = textures;
  }
}
