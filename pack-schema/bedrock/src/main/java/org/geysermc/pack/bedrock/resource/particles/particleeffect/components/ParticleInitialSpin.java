package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Initial Spin Component For 1.10.0
 * <p>
 * Starts the particle with a specified orientation and rotation rate.
 */
public class ParticleInitialSpin {
  public String rotation;

  @SerializedName("rotation_rate")
  public String rotationRate;

  public String rotation() {
    return this.rotation;
  }

  public void rotation(String rotation) {
    this.rotation = rotation;
  }

  public String rotationRate() {
    return this.rotationRate;
  }

  public void rotationRate(String rotationRate) {
    this.rotationRate = rotationRate;
  }
}
