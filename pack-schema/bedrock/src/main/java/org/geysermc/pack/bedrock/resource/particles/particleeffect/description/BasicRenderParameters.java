package org.geysermc.pack.bedrock.resource.particles.particleeffect.description;

import java.lang.String;

/**
 * Basic Render Parameters
 */
public class BasicRenderParameters {
  public String material;

  public String texture;

  /**
   *  Minecraft material to use for emitter.
   *
   * @return Material
   */
  public String material() {
    return this.material;
  }

  /**
   *  Minecraft material to use for emitter.
   *
   * @param material Material
   */
  public void material(String material) {
    this.material = material;
  }

  /**
   * Minecraft texture to use for emitter.
   *
   * @return Texture
   */
  public String texture() {
    return this.texture;
  }

  /**
   * Minecraft texture to use for emitter.
   *
   * @param texture Texture
   */
  public void texture(String texture) {
    this.texture = texture;
  }
}
