package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

public class Direction {
  public String mode;

  @JsonProperty("min_speed_threshold")
  public float minSpeedThreshold;

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
   * The direction is set if the speed of the particle is above the threshold.
   */
  public float minSpeedThreshold() {
    return this.minSpeedThreshold;
  }

  /**
   * The direction is set if the speed of the particle is above the threshold.
   */
  public void minSpeedThreshold(float minSpeedThreshold) {
    this.minSpeedThreshold = minSpeedThreshold;
  }
}
