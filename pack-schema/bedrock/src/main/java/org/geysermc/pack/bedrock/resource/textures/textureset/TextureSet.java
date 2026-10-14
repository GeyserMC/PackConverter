package org.geysermc.pack.bedrock.resource.textures.textureset;

import java.lang.String;

/**
 * Texture Set
 * <p>
 * Texture Sets are used to define multiple PBR layers for a texture resource.
 */
public class TextureSet {
  public String heightmap;

  public String normal;

  public String heightmap() {
    return this.heightmap;
  }

  public void heightmap(String heightmap) {
    this.heightmap = heightmap;
  }

  public String normal() {
    return this.normal;
  }

  public void normal(String normal) {
    this.normal = normal;
  }
}
