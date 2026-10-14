package org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.meta;

import java.lang.Integer;

/**
 * Size
 * <p>
 * The size of the flipbook sprite sheet.
 */
public class Size {
  public Integer w;

  public Integer h;

  /**
   * The width of the sprite sheet.
   *
   * @return Width
   */
  public Integer w() {
    return this.w;
  }

  /**
   * The width of the sprite sheet.
   *
   * @param w Width
   */
  public void w(int w) {
    this.w = w;
  }

  /**
   * The height of the sprite sheet.
   *
   * @return Height
   */
  public Integer h() {
    return this.h;
  }

  /**
   * The height of the sprite sheet.
   *
   * @param h Height
   */
  public void h(int h) {
    this.h = h;
  }
}
