package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.emitterlifetimeevents;

import java.lang.Float;
import java.lang.String;

/**
 * Distance Event
 */
public class LoopingTravelDistanceEvents {
  public Float distance;

  public String[] effects;

  /**
   * @return Distance
   */
  public Float distance() {
    return this.distance;
  }

  /**
   * @param distance Distance
   */
  public void distance(float distance) {
    this.distance = distance;
  }

  public String[] effects() {
    return this.effects;
  }

  public void effects(String[] effects) {
    this.effects = effects;
  }
}
