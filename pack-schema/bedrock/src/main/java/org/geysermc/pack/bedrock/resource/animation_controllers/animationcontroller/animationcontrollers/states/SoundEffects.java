package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states;

import java.lang.String;

public class SoundEffects {
  public String effect;

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
}
