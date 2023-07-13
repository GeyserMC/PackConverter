package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Motion Dynamic Component For 1.10.0
 * <p>
 * This component specifies the dynamic properties of the particle, from a simulation standpoint what forces act upon the particle? These dynamics alter the velocity of the particle, which is a combination of the direction of the particle and the speed. Particle direction will always be in the direction of the velocity of the particle.
 */
public class ParticleMotionDynamic {
  @SerializedName("linear_acceleration")
  public String[] linearAcceleration;

  @SerializedName("linear_drag_coefficient")
  public String linearDragCoefficient;

  @SerializedName("rotation_acceleration")
  public String rotationAcceleration;

  @SerializedName("rotation_drag_coefficient")
  public String rotationDragCoefficient;

  /**
   * The linear acceleration applied to the particle. Units are blocks/sec/sec
   *
   * @return Linear Acceleration
   */
  public String[] linearAcceleration() {
    return this.linearAcceleration;
  }

  /**
   * The linear acceleration applied to the particle. Units are blocks/sec/sec
   *
   * @param linearAcceleration Linear Acceleration
   */
  public void linearAcceleration(String[] linearAcceleration) {
    this.linearAcceleration = linearAcceleration;
  }

  public String linearDragCoefficient() {
    return this.linearDragCoefficient;
  }

  public void linearDragCoefficient(String linearDragCoefficient) {
    this.linearDragCoefficient = linearDragCoefficient;
  }

  public String rotationAcceleration() {
    return this.rotationAcceleration;
  }

  public void rotationAcceleration(String rotationAcceleration) {
    this.rotationAcceleration = rotationAcceleration;
  }

  public String rotationDragCoefficient() {
    return this.rotationDragCoefficient;
  }

  public void rotationDragCoefficient(String rotationDragCoefficient) {
    this.rotationDragCoefficient = rotationDragCoefficient;
  }
}
