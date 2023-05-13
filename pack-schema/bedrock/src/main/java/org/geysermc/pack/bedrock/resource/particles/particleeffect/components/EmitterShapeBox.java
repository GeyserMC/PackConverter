package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Emitter Shape Box Component For 1.10.0
 */
public class EmitterShapeBox {
  public String direction;

  public String radius;

  @JsonProperty("surface_only")
  public boolean surfaceOnly;

  public String direction() {
    return this.direction;
  }

  public void direction(String direction) {
    this.direction = direction;
  }

  public String radius() {
    return this.radius;
  }

  public void radius(String radius) {
    this.radius = radius;
  }

  /**
   * @return Surface Only
   */
  public boolean surfaceOnly() {
    return this.surfaceOnly;
  }

  /**
   * @param surfaceOnly Surface Only
   */
  public void surfaceOnly(boolean surfaceOnly) {
    this.surfaceOnly = surfaceOnly;
  }
}
