package org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.air;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.air.transitionfog.InitFog;

/**
 * Transition Fog
 * <p>
 * Additional fog data which will slowly transition to the distance fog of current biome.
 */
public class TransitionFog {
  @JsonProperty("init_fog")
  public InitFog initFog;

  @JsonProperty("min_percent")
  public float minPercent;

  @JsonProperty("mid_seconds")
  public float midSeconds;

  @JsonProperty("mid_percent")
  public float midPercent;

  @JsonProperty("max_seconds")
  public float maxSeconds;

  /**
   * Initial fog that will slowly transition into water distance fog of the biome when player goes into water.
   *
   * @return Initial Fog
   */
  public InitFog initFog() {
    return this.initFog;
  }

  /**
   * Initial fog that will slowly transition into water distance fog of the biome when player goes into water.
   *
   * @param initFog Initial Fog
   */
  public void initFog(InitFog initFog) {
    this.initFog = initFog;
  }

  /**
   * The minimum progress of fog transition.
   *
   * @return Minimum Percent
   */
  public float minPercent() {
    return this.minPercent;
  }

  /**
   * The minimum progress of fog transition.
   *
   * @param minPercent Minimum Percent
   */
  public void minPercent(float minPercent) {
    this.minPercent = minPercent;
  }

  /**
   * The time takes to reach certain progress('mid_percent') of fog transition.
   *
   * @return Midpoint Seconds
   */
  public float midSeconds() {
    return this.midSeconds;
  }

  /**
   * The time takes to reach certain progress('mid_percent') of fog transition.
   *
   * @param midSeconds Midpoint Seconds
   */
  public void midSeconds(float midSeconds) {
    this.midSeconds = midSeconds;
  }

  /**
   * The progress of fog transition after 'mid_seconds' seconds.
   *
   * @return Midpoint Percent
   */
  public float midPercent() {
    return this.midPercent;
  }

  /**
   * The progress of fog transition after 'mid_seconds' seconds.
   *
   * @param midPercent Midpoint Percent
   */
  public void midPercent(float midPercent) {
    this.midPercent = midPercent;
  }

  /**
   * Total amount of time takes to complete fog transition.
   *
   * @return Maximum Seconds
   */
  public float maxSeconds() {
    return this.maxSeconds;
  }

  /**
   * Total amount of time takes to complete fog transition.
   *
   * @param maxSeconds Maximum Seconds
   */
  public void maxSeconds(float maxSeconds) {
    this.maxSeconds = maxSeconds;
  }
}
