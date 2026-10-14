package org.geysermc.pack.bedrock.resource.textures.asepriteflipbook;

import java.lang.Integer;
import org.geysermc.pack.bedrock.resource.textures.asepriteflipbook.frames.Frame;

/**
 * Frame
 * <p>
 * A frame of the flipbook.
 */
public class Frames {
  public Integer duration;

  public Frame frame;

  /**
   * The duration of the frame in milliseconds.
   *
   * @return Duration
   */
  public Integer duration() {
    return this.duration;
  }

  /**
   * The duration of the frame in milliseconds.
   *
   * @param duration Duration
   */
  public void duration(int duration) {
    this.duration = duration;
  }

  /**
   * The position of the frame in the sprite sheet (UV).
   *
   * @return Frame
   */
  public Frame frame() {
    return this.frame;
  }

  /**
   * The position of the frame in the sprite sheet (UV).
   *
   * @param frame Frame
   */
  public void frame(Frame frame) {
    this.frame = frame;
  }
}
