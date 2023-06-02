package org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.lava;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.lava.transitionfog.InitFog;

/**
 * Transition Fog
 * <p>
 * Additional fog data which will slowly transition to the distance fog of current biome.
 */
public class TransitionFog {
  @SerializedName("init_fog")
  public InitFog initFog;

  @SerializedName("min_percent")
  public Float minPercent;

  @SerializedName("mid_seconds")
  public Float midSeconds;

  @SerializedName("mid_percent")
  public Float midPercent;

  @SerializedName("max_seconds")
  public Float maxSeconds;

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
  public Float minPercent() {
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
  public Float midSeconds() {
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
  public Float midPercent() {
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
  public Float maxSeconds() {
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
