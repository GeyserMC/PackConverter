package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.uv;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

/**
 * Flipbook
 */
public class Flipbook {
  @SerializedName("base_UV")
  public String[] baseUv;

  @SerializedName("size_UV")
  public String[] sizeUv;

  @SerializedName("step_UV")
  public String[] stepUv;

  @SerializedName("frames_per_second")
  public String framesPerSecond;

  @SerializedName("max_frame")
  public String maxFrame;

  @SerializedName("stretch_to_lifetime")
  public Boolean stretchToLifetime;

  public Boolean loop;

  /**
   * @return Base U V
   */
  public String[] baseUv() {
    return this.baseUv;
  }

  /**
   * @param baseUv Base U V
   */
  public void baseUv(String[] baseUv) {
    this.baseUv = baseUv;
  }

  /**
   * @return Size U V
   */
  public String[] sizeUv() {
    return this.sizeUv;
  }

  /**
   * @param sizeUv Size U V
   */
  public void sizeUv(String[] sizeUv) {
    this.sizeUv = sizeUv;
  }

  /**
   * @return Step U V
   */
  public String[] stepUv() {
    return this.stepUv;
  }

  /**
   * @param stepUv Step U V
   */
  public void stepUv(String[] stepUv) {
    this.stepUv = stepUv;
  }

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
