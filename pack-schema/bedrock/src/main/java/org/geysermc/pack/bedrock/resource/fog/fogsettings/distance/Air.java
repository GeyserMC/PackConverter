package org.geysermc.pack.bedrock.resource.fog.fogsettings.distance;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.air.TransitionFog;

public class Air {
  @SerializedName("fog_start")
  public Float fogStart;

  @SerializedName("fog_end")
  public Float fogEnd;

  @SerializedName("fog_color")
  public String fogColor;

  @SerializedName("render_distance_type")
  public String renderDistanceType;

  @SerializedName("transition_fog")
  public TransitionFog transitionFog;

  /**
   * The distance from the player that the fog will begin to appear. 'fog_start' must be less than or equal to 'fog_end'.
   *
   * @return Fog Start
   */
  public Float fogStart() {
    return this.fogStart;
  }

  /**
   * The distance from the player that the fog will begin to appear. 'fog_start' must be less than or equal to 'fog_end'.
   *
   * @param fogStart Fog Start
   */
  public void fogStart(float fogStart) {
    this.fogStart = fogStart;
  }

  /**
   * The distance from the player that the fog will become fully opaque. 'fog_end' must be greater than or equal to 'fog_start'.
   *
   * @return Fog End
   */
  public Float fogEnd() {
    return this.fogEnd;
  }

  /**
   * The distance from the player that the fog will become fully opaque. 'fog_end' must be greater than or equal to 'fog_start'.
   *
   * @param fogEnd Fog End
   */
  public void fogEnd(float fogEnd) {
    this.fogEnd = fogEnd;
  }

  /**
   * The color that the fog will take on.
   *
   * @return Fog Color
   */
  public String fogColor() {
    return this.fogColor;
  }

  /**
   * The color that the fog will take on.
   *
   * @param fogColor Fog Color
   */
  public void fogColor(String fogColor) {
    this.fogColor = fogColor;
  }

  /**
   * Determines how distance value is used. Fixed distance is measured in blocks. Dynamic distance is multiplied by the current render distance.
   *
   * @return Render Distance Type
   */
  public String renderDistanceType() {
    return this.renderDistanceType;
  }

  /**
   * Determines how distance value is used. Fixed distance is measured in blocks. Dynamic distance is multiplied by the current render distance.
   *
   * @param renderDistanceType Render Distance Type
   */
  public void renderDistanceType(String renderDistanceType) {
    this.renderDistanceType = renderDistanceType;
  }

  /**
   * Additional fog data which will slowly transition to the distance fog of current biome.
   *
   * @return Transition Fog
   */
  public TransitionFog transitionFog() {
    return this.transitionFog;
  }

  /**
   * Additional fog data which will slowly transition to the distance fog of current biome.
   *
   * @param transitionFog Transition Fog
   */
  public void transitionFog(TransitionFog transitionFog) {
    this.transitionFog = transitionFog;
  }
}
