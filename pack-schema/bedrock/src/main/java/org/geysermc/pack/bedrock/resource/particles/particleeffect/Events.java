package org.geysermc.pack.bedrock.resource.particles.particleeffect;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.events.ParticleEffect;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.events.SoundEffect;

/**
 * Particle event
 * <p>
 * The particle event.
 */
public class Events {
  @SerializedName("particle_effect")
  public ParticleEffect particleEffect;

  @SerializedName("sound_effect")
  public SoundEffect soundEffect;

  public String expression;

  public String log;

  public Float weight;

  /**
   * Particle effect action.
   *
   * @return Particle Effect
   */
  public ParticleEffect particleEffect() {
    return this.particleEffect;
  }

  /**
   * Particle effect action.
   *
   * @param particleEffect Particle Effect
   */
  public void particleEffect(ParticleEffect particleEffect) {
    this.particleEffect = particleEffect;
  }

  /**
   * Sound effect action.
   *
   * @return Sound Effect
   */
  public SoundEffect soundEffect() {
    return this.soundEffect;
  }

  /**
   * Sound effect action.
   *
   * @param soundEffect Sound Effect
   */
  public void soundEffect(SoundEffect soundEffect) {
    this.soundEffect = soundEffect;
  }

  /**
   * Molang expression to run on the event-firing emitter.
   *
   * @return Expression
   */
  public String expression() {
    return this.expression;
  }

  /**
   * Molang expression to run on the event-firing emitter.
   *
   * @param expression Expression
   */
  public void expression(String expression) {
    this.expression = expression;
  }

  /**
   * Message to log, along with the firing effect's name and event position.
   *
   * @return Log
   */
  public String log() {
    return this.log;
  }

  /**
   * Message to log, along with the firing effect's name and event position.
   *
   * @param log Log
   */
  public void log(String log) {
    this.log = log;
  }

  /**
   * The weight of the element.
   *
   * @return Weight
   */
  public Float weight() {
    return this.weight;
  }

  /**
   * The weight of the element.
   *
   * @param weight Weight
   */
  public void weight(float weight) {
    this.weight = weight;
  }
}
