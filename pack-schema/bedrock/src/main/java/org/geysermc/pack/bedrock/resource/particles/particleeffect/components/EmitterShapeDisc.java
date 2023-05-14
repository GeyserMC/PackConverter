package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Emitter Shape Disc Component For 1.10.0
 */
public class EmitterShapeDisc {
  public String direction;

  public String radius;

  public String[] offset;

  @JsonProperty("plane_normal")
  public String[] planeNormal;

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
   * @return Offset
   */
  public String[] offset() {
    return this.offset;
  }

  /**
   * @param offset Offset
   */
  public void offset(String[] offset) {
    this.offset = offset;
  }

  public String[] planeNormal() {
    return this.planeNormal;
  }

  public void planeNormal(String[] planeNormal) {
    this.planeNormal = planeNormal;
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
