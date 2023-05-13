package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states.SoundEffects;
import org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states.Variables;

/**
 * States
 * <p>
 * The states of this animation controller.
 */
public class States {
  @JsonProperty("blend_transition")
  public float blendTransition;

  @JsonProperty("blend_via_shortest_path")
  public boolean blendViaShortestPath;

  @JsonProperty("sound_effects")
  public List<SoundEffects> soundEffects = new ArrayList<>();

  private Map<String, Variables> variables = new HashMap<>();

  @JsonProperty("on_entry")
  public String[] onEntry;

  @JsonProperty("on_exit")
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
