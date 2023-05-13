package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.emitterlifetimeevents;

import java.lang.String;

/**
 * Distance Event
 */
public class LoopingTravelDistanceEvents {
  public float distance;

  public String effects;

  /**
   * @return Distance
   */
  public float distance() {
    return this.distance;
  }

  /**
   * @param distance Distance
   */
  public void distance(float distance) {
    this.distance = distance;
  }

  public String effects() {
    return this.effects;
  }

  public void effects(String effects) {
    this.effects = effects;
  }
}
