package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Emitter Rate Steady Component For 1.10.0
 */
public class EmitterRateSteady {
  @JsonProperty("max_particles")
  public String maxParticles;

  @JsonProperty("spawn_rate")
  public String spawnRate;

  public String maxParticles() {
    return this.maxParticles;
  }

  public void maxParticles(String maxParticles) {
    this.maxParticles = maxParticles;
  }

  public String spawnRate() {
    return this.spawnRate;
  }

  public void spawnRate(String spawnRate) {
    this.spawnRate = spawnRate;
  }
}
