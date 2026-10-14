package org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.frames;

import java.lang.Integer;

/**
 * Frame
 * <p>
 * The position of the frame in the sprite sheet (UV).
 */
public class Frame {
  public Integer x;

  public Integer y;

  /**
   * The x position of the frame in the sprite sheet.
   *
   * @return X
   */
  public Integer x() {
    return this.x;
  }

  /**
   * The x position of the frame in the sprite sheet.
   *
   * @param x X
   */
  public void x(int x) {
    this.x = x;
  }

  /**
   * The y position of the frame in the sprite sheet.
   *
   * @return Y
   */
  public Integer y() {
    return this.y;
  }

  /**
   * The y position of the frame in the sprite sheet.
   *
   * @param y Y
   */
  public void y(int y) {
    this.y = y;
  }
}
