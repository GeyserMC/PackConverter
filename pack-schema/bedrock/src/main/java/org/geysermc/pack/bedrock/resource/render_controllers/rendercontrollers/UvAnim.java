package org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers;

import java.lang.String;

/**
 * Uv Anim
 * <p>
 * The UV animation to apply to the render texture.
 */
public class UvAnim {
  public String[] offset;

  public String[] scale;

  /**
   * The offset to apply the UV, this will cause the texture on the object to shift by said amount, can be molang. The value for how much to offset is usually specified between 0 and 1
   *
   * @return Offset
   */
  public String[] offset() {
    return this.offset;
  }

  /**
   * The offset to apply the UV, this will cause the texture on the object to shift by said amount, can be molang. The value for how much to offset is usually specified between 0 and 1
   *
   * @param offset Offset
   */
  public void offset(String[] offset) {
    this.offset = offset;
  }

  /**
   * The scale to apply to the texture, this will cause texture to seem to grow and shrink if done per frame.
   *
   * @return Scale
   */
  public String[] scale() {
    return this.scale;
  }

  /**
   * The scale to apply to the texture, this will cause texture to seem to grow and shrink if done per frame.
   *
   * @param scale Scale
   */
  public void scale(String[] scale) {
    this.scale = scale;
  }
}
