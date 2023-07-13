package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import java.lang.String;

public class Direction {
  public String mode;

  @SerializedName("custom_direction")
  public String[] customDirection;

  @SerializedName("min_speed_threshold")
  public Float minSpeedThreshold;

  /**
   * Specified how to calculate the billboard direction of a particle.
   */
  public String mode() {
    return this.mode;
  }

  /**
   * Specified how to calculate the billboard direction of a particle.
   */
  public void mode(String mode) {
    this.mode = mode;
  }

  /**
   * The facing direction of emitted particles.
   */
  public String[] customDirection() {
    return this.customDirection;
  }

  /**
   * The facing direction of emitted particles.
   */
  public void customDirection(String[] customDirection) {
    this.customDirection = customDirection;
  }

  /**
   * The direction is set if the speed of the particle is above the threshold.
   */
  public Float minSpeedThreshold() {
    return this.minSpeedThreshold;
  }

  /**
   * The direction is set if the speed of the particle is above the threshold.
   */
  public void minSpeedThreshold(float minSpeedThreshold) {
    this.minSpeedThreshold = minSpeedThreshold;
  }
}
