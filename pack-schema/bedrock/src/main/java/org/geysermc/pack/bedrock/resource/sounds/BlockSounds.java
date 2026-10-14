package org.geysermc.pack.bedrock.resource.sounds;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.blocksounds.Events;

/**
 * Block Sounds
 * <p>
 * Block sound definitions.
 */
public class BlockSounds {
  public String base;

  public float[] volume;

  public float[] pitch;

  private Map<String, Events> events = new HashMap<>();

  /**
   * Specifying another block sound they should use sounds for if they do not define a sound themselves
   *
   * @return Base
   */
  public String base() {
    return this.base;
  }

  /**
   * Specifying another block sound they should use sounds for if they do not define a sound themselves
   *
   * @param base Base
   */
  public void base(String base) {
    this.base = base;
  }

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
}
