package org.geysermc.pack.bedrock.resource.sounds.sounddefinitions;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * Sound
 */
public class SoundDefinitions {
  @SerializedName("__use_legacy_max_distance")
  public Boolean useLegacyMaxDistance;

  public String category;

  private List<Sounds> sounds = new ArrayList<>();

  @SerializedName("max_distance")
  public Float maxDistance;

  /**
   * Whenever or not use legacy distance checking.
   *
   * @return Use Legacy Max Distance
   */
  public Boolean useLegacyMaxDistance() {
    return this.useLegacyMaxDistance;
  }

  /**
   * Whenever or not use legacy distance checking.
   *
   * @param useLegacyMaxDistance Use Legacy Max Distance
   */
  public void useLegacyMaxDistance(boolean useLegacyMaxDistance) {
    this.useLegacyMaxDistance = useLegacyMaxDistance;
  }

  /**
   * The category this sound belongs to, for the user to control the volume on.
   *
   * @return Sound Category
   */
  public String category() {
    return this.category;
  }

  /**
   * The category this sound belongs to, for the user to control the volume on.
   *
   * @param category Sound Category
   */
  public void category(String category) {
    this.category = category;
  }

  /**
   * The collection of sounds minecraft can choice from.
   *
   * @return Sounds
   */
  public List<Sounds> sounds() {
    return this.sounds;
  }

  /**
   * The collection of sounds minecraft can choice from.
   *
   * @param sounds Sounds
   */
  public void sounds(List<Sounds> sounds) {
    this.sounds = sounds;
  }

  /**
   * @return Max Distance
   */
  public Float maxDistance() {
    return this.maxDistance;
  }

  /**
   * @param maxDistance Max Distance
   */
  public void maxDistance(float maxDistance) {
    this.maxDistance = maxDistance;
  }
}
