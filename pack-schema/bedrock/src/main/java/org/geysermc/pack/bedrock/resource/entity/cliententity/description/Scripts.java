package org.geysermc.pack.bedrock.resource.entity.cliententity.description;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Scripts
 * <p>
 * The place where variables, and animations / controller to be run is specified.
 */
public class Scripts {
  public String[] animate;

  public String[] initialize;

  @SerializedName("pre_animation")
  public String[] preAnimation;

  @SerializedName("parent_setup")
  public String parentSetup;

  public String scale;

  public String scalex;

  public String scaley;

  public String scalez;

  @SerializedName("should_update_bones_and_effects_offscreen")
  public String shouldUpdateBonesAndEffectsOffscreen;

  @SerializedName("should_update_effects_offscreen")
  public String shouldUpdateEffectsOffscreen;

  private Map<String, String> variables = new HashMap<>();

  /**
   * The array of items to animate.
   *
   * @return Animate
   */
  public String[] animate() {
    return this.animate;
  }

  /**
   * The array of items to animate.
   *
   * @param animate Animate
   */
  public void animate(String[] animate) {
    this.animate = animate;
  }

  /**
   * Clientside molang variables that are to be evaluated during the creation of the entity.
   *
   * @return Initialize
   */
  public String[] initialize() {
    return this.initialize;
  }

  /**
   * Clientside molang variables that are to be evaluated during the creation of the entity.
   *
   * @param initialize Initialize
   */
  public void initialize(String[] initialize) {
    this.initialize = initialize;
  }

  /**
   * Clientside molang variables that are to be evaluated during the animation.
   *
   * @return Pre Animation
   */
  public String[] preAnimation() {
    return this.preAnimation;
  }

  /**
   * Clientside molang variables that are to be evaluated during the animation.
   *
   * @param preAnimation Pre Animation
   */
  public void preAnimation(String[] preAnimation) {
    this.preAnimation = preAnimation;
  }

  /**
   * @return Parent Setup
   */
  public String parentSetup() {
    return this.parentSetup;
  }

  /**
   * @param parentSetup Parent Setup
   */
  public void parentSetup(String parentSetup) {
    this.parentSetup = parentSetup;
  }

  /**
   * Scale sets the scale of the mob's geometry.
   *
   * @return Scale
   */
  public String scale() {
    return this.scale;
  }

  /**
   * Scale sets the scale of the mob's geometry.
   *
   * @param scale Scale
   */
  public void scale(String scale) {
    this.scale = scale;
  }

  public String scalex() {
    return this.scalex;
  }

  public void scalex(String scalex) {
    this.scalex = scalex;
  }

  public String scaley() {
    return this.scaley;
  }

  public void scaley(String scaley) {
    this.scaley = scaley;
  }

  public String scalez() {
    return this.scalez;
  }

  public void scalez(String scalez) {
    this.scalez = scalez;
  }

  public String shouldUpdateBonesAndEffectsOffscreen() {
    return this.shouldUpdateBonesAndEffectsOffscreen;
  }

  public void shouldUpdateBonesAndEffectsOffscreen(String shouldUpdateBonesAndEffectsOffscreen) {
    this.shouldUpdateBonesAndEffectsOffscreen = shouldUpdateBonesAndEffectsOffscreen;
  }

  public String shouldUpdateEffectsOffscreen() {
    return this.shouldUpdateEffectsOffscreen;
  }

  public void shouldUpdateEffectsOffscreen(String shouldUpdateEffectsOffscreen) {
    this.shouldUpdateEffectsOffscreen = shouldUpdateEffectsOffscreen;
  }

  /**
   *  A list of variables that need certain settings applied to them. Currently, for the client, only `public` is supported.
   *
   * @return Variables
   */
  public Map<String, String> variables() {
    return this.variables;
  }

  /**
   *  A list of variables that need certain settings applied to them. Currently, for the client, only `public` is supported.
   *
   * @param variables Variables
   */
  public void variables(Map<String, String> variables) {
    this.variables = variables;
  }
}
