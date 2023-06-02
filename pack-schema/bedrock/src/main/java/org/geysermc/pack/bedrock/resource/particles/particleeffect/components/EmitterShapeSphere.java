package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

/**
 * Emitter Shape Sphere Component For 1.10.0
 */
public class EmitterShapeSphere {
  public String direction;

  public String[] offset;

  public String radius;

  @SerializedName("surface_only")
  public Boolean surfaceOnly;

  public String direction() {
    return this.direction;
  }

  public void direction(String direction) {
    this.direction = direction;
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

  public String radius() {
    return this.radius;
  }

  public void radius(String radius) {
    this.radius = radius;
  }

  /**
   * @return Surface Only
   */
  public Boolean surfaceOnly() {
    return this.surfaceOnly;
  }

  /**
   * @param surfaceOnly Surface Only
   */
  public void surfaceOnly(boolean surfaceOnly) {
    this.surfaceOnly = surfaceOnly;
  }
}
