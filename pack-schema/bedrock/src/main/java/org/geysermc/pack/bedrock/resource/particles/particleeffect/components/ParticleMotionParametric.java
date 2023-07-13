package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Motion Parametric Component For 1.10.0
 */
public class ParticleMotionParametric {
  @SerializedName("relative_position")
  public String[] relativePosition;

  public String rotation;

  public String[] direction;

  /**
   * @return Relative Position
   */
  public String[] relativePosition() {
    return this.relativePosition;
  }

  /**
   * @param relativePosition Relative Position
   */
  public void relativePosition(String[] relativePosition) {
    this.relativePosition = relativePosition;
  }

  public String rotation() {
    return this.rotation;
  }

  public void rotation(String rotation) {
    this.rotation = rotation;
  }

  /**
   * @return Direction
   */
  public String[] direction() {
    return this.direction;
  }

  /**
   * @param direction Direction
   */
  public void direction(String[] direction) {
    this.direction = direction;
  }
}
