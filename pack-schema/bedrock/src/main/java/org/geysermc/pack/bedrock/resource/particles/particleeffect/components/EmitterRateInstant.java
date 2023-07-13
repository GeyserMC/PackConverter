package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Rate Instant Component For 1.10.0
 */
public class EmitterRateInstant {
  @SerializedName("num_particles")
  public String numParticles;

  public String numParticles() {
    return this.numParticles;
  }

  public void numParticles(String numParticles) {
    this.numParticles = numParticles;
  }
}
