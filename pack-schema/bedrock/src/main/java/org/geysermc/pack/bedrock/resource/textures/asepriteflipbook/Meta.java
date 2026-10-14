package org.geysermc.pack.bedrock.resource.textures.asepriteflipbook;

import java.lang.String;
import org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.meta.Size;

/**
 * Meta
 * <p>
 * The meta data of the flipbook.
 */
public class Meta {
  public Size size;

  public String image;

  /**
   * The size of the flipbook sprite sheet.
   *
   * @return Size
   */
  public Size size() {
    return this.size;
  }

  /**
   * The size of the flipbook sprite sheet.
   *
   * @param size Size
   */
  public void size(Size size) {
    this.size = size;
  }

  /**
   * The path to the sprite sheet. The value is required, but not used.
   *
   * @return Image
   */
  public String image() {
    return this.image;
  }

  /**
   * The path to the sprite sheet. The value is required, but not used.
   *
   * @param image Image
   */
  public void image(String image) {
    this.image = image;
  }
}
