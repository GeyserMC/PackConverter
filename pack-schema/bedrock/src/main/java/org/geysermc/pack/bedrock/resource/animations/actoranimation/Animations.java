package org.geysermc.pack.bedrock.resource.animations.actoranimation;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.Bones;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.ParticleEffects;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.SoundEffects;

/**
 * Animations Schema
 * <p>
 * The animation specification.
 */
public class Animations {
  @SerializedName("anim_time_update")
  public String animTimeUpdate;

  @SerializedName("animation_length")
  public Float animationLength;

  @SerializedName("blend_weight")
  public String blendWeight;

  private Map<String, Bones> bones = new HashMap<>();

  public String loop;

  @SerializedName("loop_delay")
  public String loopDelay;

  @SerializedName("override_previous_animation")
  public Boolean overridePreviousAnimation;

  @SerializedName("particle_effects")
  private Map<String, ParticleEffects> particleEffects = new HashMap<>();

  @SerializedName("start_delay")
  public String startDelay;

  @SerializedName("sound_effects")
  private Map<String, SoundEffects> soundEffects = new HashMap<>();

  private Map<String, String> timeline = new HashMap<>();

  public String animTimeUpdate() {
    return this.animTimeUpdate;
  }

  public void animTimeUpdate(String animTimeUpdate) {
    this.animTimeUpdate = animTimeUpdate;
  }

  /**
   * Override calculated value (set as the last keyframe time) and set animation length in seconds.
   *
   * @return Animation Length
   */
  public Float animationLength() {
    return this.animationLength;
  }

  /**
   * Override calculated value (set as the last keyframe time) and set animation length in seconds.
   *
   * @param animationLength Animation Length
   */
  public void animationLength(float animationLength) {
    this.animationLength = animationLength;
  }

  public String blendWeight() {
    return this.blendWeight;
  }

  public void blendWeight(String blendWeight) {
    this.blendWeight = blendWeight;
  }

  /**
   * Defines how the bones in an animation move or transform.
   *
   * @return Bones
   */
  public Map<String, Bones> bones() {
    return this.bones;
  }

  /**
   * Defines how the bones in an animation move or transform.
   *
   * @param bones Bones
   */
  public void bones(Map<String, Bones> bones) {
    this.bones = bones;
  }

  public String loop() {
    return this.loop;
  }

  public void loop(String loop) {
    this.loop = loop;
  }

  public String loopDelay() {
    return this.loopDelay;
  }

  public void loopDelay(String loopDelay) {
    this.loopDelay = loopDelay;
  }

  /**
   * Reset bones in this animation to the default pose before applying this animation.
   *
   * @return Override Previous Animation
   */
  public Boolean overridePreviousAnimation() {
    return this.overridePreviousAnimation;
  }

  /**
   * Reset bones in this animation to the default pose before applying this animation.
   *
   * @param overridePreviousAnimation Override Previous Animation
   */
  public void overridePreviousAnimation(boolean overridePreviousAnimation) {
    this.overridePreviousAnimation = overridePreviousAnimation;
  }

  /**
   * @return Particle Effects
   */
  public Map<String, ParticleEffects> particleEffects() {
    return this.particleEffects;
  }

  /**
   * @param particleEffects Particle Effects
   */
  public void particleEffects(Map<String, ParticleEffects> particleEffects) {
    this.particleEffects = particleEffects;
  }

  public String startDelay() {
    return this.startDelay;
  }

  public void startDelay(String startDelay) {
    this.startDelay = startDelay;
  }

  /**
   * @return Sound Effect
   */
  public Map<String, SoundEffects> soundEffects() {
    return this.soundEffects;
  }

  /**
   * @param soundEffects Sound Effect
   */
  public void soundEffects(Map<String, SoundEffects> soundEffects) {
    this.soundEffects = soundEffects;
  }

  /**
   * The time line.
   *
   * @return Timeline
   */
  public Map<String, String> timeline() {
    return this.timeline;
  }

  /**
   * The time line.
   *
   * @param timeline Timeline
   */
  public void timeline(Map<String, String> timeline) {
    this.timeline = timeline;
  }
}
