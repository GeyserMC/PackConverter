package org.geysermc.pack.bedrock.resource.animations;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.Animations;

/**
 * Actor Animation
 * <p>
 * The RP animation that changes an actors models, or molang data.
 */
public class ActorAnimation {
  @SerializedName("format_version")
  public String formatVersion;

  private Map<String, Animations> animations = new HashMap<>();

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * The animation specification.
   *
   * @return Animations Schema
   */
  public Map<String, Animations> animations() {
    return this.animations;
  }

  /**
   * The animation specification.
   *
   * @param animations Animations Schema
   */
  public void animations(Map<String, Animations> animations) {
    this.animations = animations;
  }
}
