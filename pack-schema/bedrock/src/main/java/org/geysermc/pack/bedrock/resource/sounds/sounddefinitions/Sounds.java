package org.geysermc.pack.bedrock.resource.sounds.sounddefinitions;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

/**
 * Sounds
 * <p>
 * A collection of sounds to choice from.
 */
public class Sounds {
  public Boolean is3D;

  public Float pitch;

  public Float volume;

  @SerializedName("load_on_low_memory")
  public Boolean loadOnLowMemory;

  public Boolean stream;

  public String name;

  public Integer weight;

  /**
   * @return Is 3D
   */
  public Boolean is3D() {
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
  public Float pitch() {
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
  public Float volume() {
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
  public Boolean loadOnLowMemory() {
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
  public Boolean stream() {
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
  public Integer weight() {
    return this.weight;
  }

  /**
   * @param weight Weight
   */
  public void weight(int weight) {
    this.weight = weight;
  }
}
