package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Rate Manual Component For 1.10.0
 */
public class EmitterRateManual {
  @SerializedName("max_particles")
  public String maxParticles;

  public String maxParticles() {
    return this.maxParticles;
  }

  public void maxParticles(String maxParticles) {
    this.maxParticles = maxParticles;
  }
}
