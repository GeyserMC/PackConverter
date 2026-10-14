package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Ambient Sounds
 * <p>
 * Sets the ambient sounds for the biome. These sounds must be in the 'individual_named_sounds' in a 'sounds.json' file.
 */
public class AmbientSounds {
  public String loop;

  public String mood;

  @SerializedName("underwater_loop")
  public String underwaterLoop;

  @SerializedName("underwater_mood")
  public String underwaterMood;

  /**
   * Named sound that loops while the listener position is inside the biome.
   *
   * @return Loop
   */
  public String loop() {
    return this.loop;
  }

  /**
   * Named sound that loops while the listener position is inside the biome.
   *
   * @param loop Loop
   */
  public void loop(String loop) {
    this.loop = loop;
  }

  /**
   * Named sound that rarely plays at a nearby air block position when the light level is low.
   *
   * @return Mood
   */
  public String mood() {
    return this.mood;
  }

  /**
   * Named sound that rarely plays at a nearby air block position when the light level is low.
   *
   * @param mood Mood
   */
  public void mood(String mood) {
    this.mood = mood;
  }

  /**
   * Named sound that loops while the listener position is inside the biome and underwater.
   *
   * @return Underwater Loop
   */
  public String underwaterLoop() {
    return this.underwaterLoop;
  }

  /**
   * Named sound that loops while the listener position is inside the biome and underwater.
   *
   * @param underwaterLoop Underwater Loop
   */
  public void underwaterLoop(String underwaterLoop) {
    this.underwaterLoop = underwaterLoop;
  }

  /**
   * Named sound that rarely plays at a nearby water block position when the light level is low.
   *
   * @return Underwater Mood
   */
  public String underwaterMood() {
    return this.underwaterMood;
  }

  /**
   * Named sound that rarely plays at a nearby water block position when the light level is low.
   *
   * @param underwaterMood Underwater Mood
   */
  public void underwaterMood(String underwaterMood) {
    this.underwaterMood = underwaterMood;
  }
}
