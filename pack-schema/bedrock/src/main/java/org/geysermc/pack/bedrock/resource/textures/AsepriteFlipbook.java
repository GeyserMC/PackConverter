package org.geysermc.pack.bedrock.resource.textures;

import java.util.ArrayList;
import java.util.List;
import org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.Frames;
import org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.Meta;

/**
 * Aseprite Flipbook File
 * <p>
 * The file that specifies Aseprite/libresprite flipbook animation.
 */
public class AsepriteFlipbook {
  public Meta meta;

  public List<Frames> frames = new ArrayList<>();

  /**
   * The meta data of the flipbook.
   *
   * @return Meta
   */
  public Meta meta() {
    return this.meta;
  }

  /**
   * The meta data of the flipbook.
   *
   * @param meta Meta
   */
  public void meta(Meta meta) {
    this.meta = meta;
  }

  /**
   * The frames of the flipbook.
   *
   * @return Frames
   */
  public List<Frames> frames() {
    return this.frames;
  }

  /**
   * The frames of the flipbook.
   *
   * @param frames Frames
   */
  public void frames(List<Frames> frames) {
    this.frames = frames;
  }
}
