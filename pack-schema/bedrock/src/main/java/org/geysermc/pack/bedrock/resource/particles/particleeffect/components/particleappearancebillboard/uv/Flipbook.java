package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.uv;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

/**
 * Flipbook
 */
public class Flipbook {
  @SerializedName("frames_per_second")
  public String framesPerSecond;

  @SerializedName("max_frame")
  public String maxFrame;

  @SerializedName("stretch_to_lifetime")
  public Boolean stretchToLifetime;

  public Boolean loop;

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
  public Boolean stretchToLifetime() {
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
  public Boolean loop() {
    return this.loop;
  }

  /**
   * @param loop Loop
   */
  public void loop(boolean loop) {
    this.loop = loop;
  }
}
