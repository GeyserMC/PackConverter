package org.geysermc.pack.bedrock.resource.sounds.entitysounds;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.entitysounds.entities.Events;
import org.geysermc.pack.bedrock.resource.sounds.entitysounds.entities.Variants;

/**
 * Entity Sound
 * <p>
 * Entity sound definitions.
 */
public class Entities {
  public float[] volume;

  public float[] pitch;

  private Map<String, Events> events = new HashMap<>();

  public Variants variants;

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
  public Map<String, Events> events() {
    return this.events;
  }

  /**
   * @param events Events
   */
  public void events(Map<String, Events> events) {
    this.events = events;
  }

  /**
   * Defines sound variants based on the runtime entity
   *
   * @return Variants
   */
  public Variants variants() {
    return this.variants;
  }

  /**
   * Defines sound variants based on the runtime entity
   *
   * @param variants Variants
   */
  public void variants(Variants variants) {
    this.variants = variants;
  }
}
