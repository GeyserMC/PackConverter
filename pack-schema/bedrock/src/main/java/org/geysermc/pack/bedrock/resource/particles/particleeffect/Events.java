package org.geysermc.pack.bedrock.resource.particles.particleeffect;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.events.ParticleEffect;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.events.SoundEffect;

/**
 * Events
 */
public class Events {
  @JsonProperty("particle_effect")
  public ParticleEffect particleEffect;

  @JsonProperty("sound_effect")
  public SoundEffect soundEffect;

  /**
   * @return Particle Effect
   */
  public ParticleEffect particleEffect() {
    return this.particleEffect;
  }

  /**
   * @param particleEffect Particle Effect
   */
  public void particleEffect(ParticleEffect particleEffect) {
    this.particleEffect = particleEffect;
  }

  /**
   * @return Sound Effect
   */
  public SoundEffect soundEffect() {
    return this.soundEffect;
  }

  /**
   * @param soundEffect Sound Effect
   */
  public void soundEffect(SoundEffect soundEffect) {
    this.soundEffect = soundEffect;
  }
}
