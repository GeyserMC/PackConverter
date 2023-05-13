package org.geysermc.pack.bedrock.resource.sounds.entitysounds;

import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity Sound
 * <p>
 * Entity sound definitions.
 */
public class Defaults {
  public float[] volume;

  public float[] pitch;

  private Map<String, Object> events = new HashMap<>();

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
   * @return Events
   */
  public Map<String, Object> events() {
    return this.events;
  }

  /**
   * @param events Events
   */
  public void events(Map<String, Object> events) {
    this.events = events;
  }
}
