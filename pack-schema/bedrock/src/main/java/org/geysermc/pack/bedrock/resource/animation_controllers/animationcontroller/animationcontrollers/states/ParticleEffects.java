package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

public class ParticleEffects {
  @SerializedName("bind_to_actor")
  public Boolean bindToActor;

  public String effect;

  public String locator;

  @SerializedName("pre_effect_script")
  public String preEffectScript;

  /**
   * Set to false to have the effect spawned in the world without being bound to an actor (by default an effect is bound to the actor).
   *
   * @return Bind To Actor
   */
  public Boolean bindToActor() {
    return this.bindToActor;
  }

  /**
   * Set to false to have the effect spawned in the world without being bound to an actor (by default an effect is bound to the actor).
   *
   * @param bindToActor Bind To Actor
   */
  public void bindToActor(boolean bindToActor) {
    this.bindToActor = bindToActor;
  }

  /**
   * The name of a particle effect that should be played.
   *
   * @return Effect
   */
  public String effect() {
    return this.effect;
  }

  /**
   * The name of a particle effect that should be played.
   *
   * @param effect Effect
   */
  public void effect(String effect) {
    this.effect = effect;
  }

  /**
   * The name of a locator on the actor where the effect should be located.
   *
   * @return Locator
   */
  public String locator() {
    return this.locator;
  }

  /**
   * The name of a locator on the actor where the effect should be located.
   *
   * @param locator Locator
   */
  public void locator(String locator) {
    this.locator = locator;
  }

  /**
   * A molang script that will be run when the particle emitter is initialized.
   *
   * @return Pre Effect Script
   */
  public String preEffectScript() {
    return this.preEffectScript;
  }

  /**
   * A molang script that will be run when the particle emitter is initialized.
   *
   * @param preEffectScript Pre Effect Script
   */
  public void preEffectScript(String preEffectScript) {
    this.preEffectScript = preEffectScript;
  }
}
