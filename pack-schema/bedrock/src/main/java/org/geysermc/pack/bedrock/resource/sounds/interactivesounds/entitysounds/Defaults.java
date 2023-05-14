package org.geysermc.pack.bedrock.resource.sounds.interactivesounds.entitysounds;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.interactivesounds.entitysounds.defaults.Events;

/**
 * Defaults
 * <p>
 * Default sound definitions.
 */
public class Defaults {
  public float[] volume;

  public float[] pitch;

  private Map<String, Events> events = new HashMap<>();

  /**
   * A random selection between a minimum and maximum.
   */
  public float[] volume() {
    return this.volume;
  }

  /**
   * A random selection between a minimum and maximum.
   */
  public void volume(float[] volume) {
    this.volume = volume;
  }

  /**
   * A random selection between a minimum and maximum.
   */
  public float[] pitch() {
    return this.pitch;
  }

  /**
   * A random selection between a minimum and maximum.
   */
  public void pitch(float[] pitch) {
    this.pitch = pitch;
  }

  /**
   * @return Entity Events
   */
  public Map<String, Events> events() {
    return this.events;
  }

  /**
   * @param events Entity Events
   */
  public void events(Map<String, Events> events) {
    this.events = events;
  }
}
