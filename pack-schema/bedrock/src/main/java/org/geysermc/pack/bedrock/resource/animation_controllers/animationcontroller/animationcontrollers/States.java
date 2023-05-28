package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states.ParticleEffects;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states.SoundEffects;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states.Variables;

/**
 * States
 * <p>
 * The states of this animation controller.
 */
public class States {
  @SerializedName("blend_transition")
  public float blendTransition;

  @SerializedName("blend_via_shortest_path")
  public boolean blendViaShortestPath;

  @SerializedName("particle_effects")
  public List<ParticleEffects> particleEffects = new ArrayList<>();

  @SerializedName("sound_effects")
  public List<SoundEffects> soundEffects = new ArrayList<>();

  private Map<String, Variables> variables = new HashMap<>();

  @SerializedName("on_entry")
  public String[] onEntry;

  @SerializedName("on_exit")
  public String[] onExit;

  /**
   * A short-hand version of blend_out that simply sets the amount of time to fade out if the animation is interrupted.
   */
  public float blendTransition() {
    return this.blendTransition;
  }

  /**
   * A short-hand version of blend_out that simply sets the amount of time to fade out if the animation is interrupted.
   */
  public void blendTransition(float blendTransition) {
    this.blendTransition = blendTransition;
  }

  /**
   * When blending a transition to another state, animate each euler axis through the shortest rotation, instead of by value.
   *
   * @return Blend Via Shortest Path
   */
  public boolean blendViaShortestPath() {
    return this.blendViaShortestPath;
  }

  /**
   * When blending a transition to another state, animate each euler axis through the shortest rotation, instead of by value.
   *
   * @param blendViaShortestPath Blend Via Shortest Path
   */
  public void blendViaShortestPath(boolean blendViaShortestPath) {
    this.blendViaShortestPath = blendViaShortestPath;
  }

  /**
   * The effects to be emitted.
   *
   * @return Particle Effects
   */
  public List<ParticleEffects> particleEffects() {
    return this.particleEffects;
  }

  /**
   * The effects to be emitted.
   *
   * @param particleEffects Particle Effects
   */
  public void particleEffects(List<ParticleEffects> particleEffects) {
    this.particleEffects = particleEffects;
  }

  /**
   * Collection of sounds to trigger on entry to this animation state.
   */
  public List<SoundEffects> soundEffects() {
    return this.soundEffects;
  }

  /**
   * Collection of sounds to trigger on entry to this animation state.
   */
  public void soundEffects(List<SoundEffects> soundEffects) {
    this.soundEffects = soundEffects;
  }

  public Map<String, Variables> variables() {
    return this.variables;
  }

  public void variables(Map<String, Variables> variables) {
    this.variables = variables;
  }

  /**
   * Sets molang on data on entry.
   *
   * @return On Entry
   */
  public String[] onEntry() {
    return this.onEntry;
  }

  /**
   * Sets molang on data on entry.
   *
   * @param onEntry On Entry
   */
  public void onEntry(String[] onEntry) {
    this.onEntry = onEntry;
  }

  /**
   * Sets molang on data on exit.
   *
   * @return On Exit
   */
  public String[] onExit() {
    return this.onExit;
  }

  /**
   * Sets molang on data on exit.
   *
   * @param onExit On Exit
   */
  public void onExit(String[] onExit) {
    this.onExit = onExit;
  }
}
