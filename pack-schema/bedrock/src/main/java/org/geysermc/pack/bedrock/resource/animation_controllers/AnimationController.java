package org.geysermc.pack.bedrock.resource.animation_controllers;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.AnimationControllers;

/**
 * Animation Controller
 */
public class AnimationController {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("animation_controllers")
  private Map<String, AnimationControllers> animationControllers = new HashMap<>();

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
   * The animation controllers schema for.
   *
   * @return Animation Controllers Schema
   */
  public Map<String, AnimationControllers> animationControllers() {
    return this.animationControllers;
  }

  /**
   * The animation controllers schema for.
   *
   * @param animationControllers Animation Controllers Schema
   */
  public void animationControllers(Map<String, AnimationControllers> animationControllers) {
    this.animationControllers = animationControllers;
  }
}
