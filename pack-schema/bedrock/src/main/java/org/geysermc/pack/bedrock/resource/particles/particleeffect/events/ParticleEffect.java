package org.geysermc.pack.bedrock.resource.particles.particleeffect.events;

import java.lang.String;

/**
 * Particle Effect
 */
public class ParticleEffect {
  public String effect;

  public String type;

  /**
   * @return Effect
   */
  public String effect() {
    return this.effect;
  }

  /**
   * @param effect Effect
   */
  public void effect(String effect) {
    this.effect = effect;
  }

  /**
   * @return Type
   */
  public String type() {
    return this.type;
  }

  /**
   * @param type Type
   */
  public void type(String type) {
    this.type = type;
  }
}
