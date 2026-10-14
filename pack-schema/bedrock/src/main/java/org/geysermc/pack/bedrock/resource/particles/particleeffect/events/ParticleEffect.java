package org.geysermc.pack.bedrock.resource.particles.particleeffect.events;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Effect
 * <p>
 * Particle effect action.
 */
public class ParticleEffect {
  public String effect;

  public String type;

  @SerializedName("pre_effect_expression")
  public String preEffectExpression;

  /**
   * Identifier of the effect.
   *
   * @return Effect
   */
  public String effect() {
    return this.effect;
  }

  /**
   * Identifier of the effect.
   *
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

  /**
   * Molang expression to run on the new emitter.
   *
   * @return Pre Effect Expression
   */
  public String preEffectExpression() {
    return this.preEffectExpression;
  }

  /**
   * Molang expression to run on the new emitter.
   *
   * @param preEffectExpression Pre Effect Expression
   */
  public void preEffectExpression(String preEffectExpression) {
    this.preEffectExpression = preEffectExpression;
  }
}
