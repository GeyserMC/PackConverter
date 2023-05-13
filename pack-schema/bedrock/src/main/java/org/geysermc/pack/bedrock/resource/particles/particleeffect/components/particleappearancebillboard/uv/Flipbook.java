package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.uv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Flipbook
 */
public class Flipbook {
  @JsonProperty("frames_per_second")
  public String framesPerSecond;

  @JsonProperty("max_frame")
  public String maxFrame;

  @JsonProperty("stretch_to_lifetime")
  public boolean stretchToLifetime;

  public boolean loop;

  public String framesPerSecond() {
    return this.framesPerSecond;
  }

  public void framesPerSecond(String framesPerSecond) {
    this.framesPerSecond = framesPerSecond;
  }

  public String maxFrame() {
    return this.maxFrame;
  }

  public void maxFrame(String maxFrame) {
    this.maxFrame = maxFrame;
  }

  /**
   * @return Stretch To Lifetime
   */
  public boolean stretchToLifetime() {
    return this.stretchToLifetime;
  }

  /**
   * @param stretchToLifetime Stretch To Lifetime
   */
  public void stretchToLifetime(boolean stretchToLifetime) {
    this.stretchToLifetime = stretchToLifetime;
  }

  /**
   * @return Loop
   */
  public boolean loop() {
    return this.loop;
  }

  /**
   * @param loop Loop
   */
  public void loop(boolean loop) {
    this.loop = loop;
  }
}
