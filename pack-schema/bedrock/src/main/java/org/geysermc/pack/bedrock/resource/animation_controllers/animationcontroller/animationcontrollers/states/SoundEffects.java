package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states;

import java.lang.String;

public class SoundEffects {
  public String effect;

  public String locator;

  /**
   * Valid sound effect names should be listed in the entity's resource_definition json file.
   */
  public String effect() {
    return this.effect;
  }

  /**
   * Valid sound effect names should be listed in the entity's resource_definition json file.
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
}
