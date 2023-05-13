package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.uv.Flipbook;

/**
 * Uv
 */
public class Uv {
  @JsonProperty("texture_width")
  public int textureWidth;

  @JsonProperty("texture_height")
  public int textureHeight;

  public Flipbook flipbook;

  /**
   * @return Texture Width
   */
  public int textureWidth() {
    return this.textureWidth;
  }

  /**
   * @param textureWidth Texture Width
   */
  public void textureWidth(int textureWidth) {
    this.textureWidth = textureWidth;
  }

  /**
   * @return Texture Height
   */
  public int textureHeight() {
    return this.textureHeight;
  }

  /**
   * @param textureHeight Texture Height
   */
  public void textureHeight(int textureHeight) {
    this.textureHeight = textureHeight;
  }

  /**
   * @return Flipbook
   */
  public Flipbook flipbook() {
    return this.flipbook;
  }

  /**
   * @param flipbook Flipbook
   */
  public void flipbook(Flipbook flipbook) {
    this.flipbook = flipbook;
  }
}
