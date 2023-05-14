package org.geysermc.pack.bedrock.resource.sounds.sounddefinitions;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Sounds
 * <p>
 * A collection of sounds to choice from.
 */
public class Sounds {
  public boolean is3D;

  public float pitch;

  public float volume;

  @SerializedName("load_on_low_memory")
  public boolean loadOnLowMemory;

  public boolean stream;

  public String name;

  public int weight;

  /**
   * @return Is 3D
   */
  public boolean is3D() {
    return this.is3D;
  }

  /**
   * @param is3D Is 3D
   */
  public void is3D(boolean is3D) {
    this.is3D = is3D;
  }

  /**
   * The pitch of the audio, 1 is nomial.
   *
   * @return Pitch
   */
  public float pitch() {
    return this.pitch;
  }

  /**
   * The pitch of the audio, 1 is nomial.
   *
   * @param pitch Pitch
   */
  public void pitch(float pitch) {
    this.pitch = pitch;
  }

  /**
   * The volume of the audio, 1 is nomial.
   *
   * @return Volume
   */
  public float volume() {
    return this.volume;
  }

  /**
   * The volume of the audio, 1 is nomial.
   *
   * @param volume Volume
   */
  public void volume(float volume) {
    this.volume = volume;
  }

  /**
   * Marks if this audio should be loaded or not on low memory.
   *
   * @return Load On Low Memory
   */
  public boolean loadOnLowMemory() {
    return this.loadOnLowMemory;
  }

  /**
   * Marks if this audio should be loaded or not on low memory.
   *
   * @param loadOnLowMemory Load On Low Memory
   */
  public void loadOnLowMemory(boolean loadOnLowMemory) {
    this.loadOnLowMemory = loadOnLowMemory;
  }

  /**
   * If marked true then minecraft will stream the audio.
   *
   * @return Stream
   */
  public boolean stream() {
    return this.stream;
  }

  /**
   * If marked true then minecraft will stream the audio.
   *
   * @param stream Stream
   */
  public void stream(boolean stream) {
    this.stream = stream;
  }

  /**
   * @return Name
   */
  public String name() {
    return this.name;
  }

  /**
   * @param name Name
   */
  public void name(String name) {
    this.name = name;
  }

  /**
   * @return Weight
   */
  public int weight() {
    return this.weight;
  }

  /**
   * @param weight Weight
   */
  public void weight(int weight) {
    this.weight = weight;
  }
}
