package org.geysermc.pack.bedrock.resource.sounds.interactivesounds.blocksounds;

import java.lang.String;

/**
 * Events
 */
public class Events {
  public String sound;

  public float[] volume;

  public float[] pitch;

  /**
   * The sound event described in sound_definitions.json
   *
   * @return Sound Event
   */
  public String sound() {
    return this.sound;
  }

  /**
   * The sound event described in sound_definitions.json
   *
   * @param sound Sound Event
   */
  public void sound(String sound) {
    this.sound = sound;
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
}
