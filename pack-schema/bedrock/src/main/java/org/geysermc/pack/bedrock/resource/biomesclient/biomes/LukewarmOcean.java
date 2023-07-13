package org.geysermc.pack.bedrock.resource.biomesclient.biomes;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.Integer;
import java.lang.String;

/**
 * Biome
 * <p>
 * The specification of colors in a given biome.
 */
public class LukewarmOcean {
  @SerializedName("fog_identifier")
  public String fogIdentifier;

  @SerializedName("fog_ids_to_merge")
  public String[] fogIdsToMerge;

  @SerializedName("inherit_from_prior_fog")
  public Boolean inheritFromPriorFog;

  @SerializedName("remove_all_prior_fog")
  public Boolean removeAllPriorFog;

  @SerializedName("water_fog_distance")
  public Integer waterFogDistance;

  @SerializedName("water_surface_transparency")
  public Float waterSurfaceTransparency;

  /**
   * A minecraft fog identifier.
   *
   * @return Fog Identifier
   */
  public String fogIdentifier() {
    return this.fogIdentifier;
  }

  /**
   * A minecraft fog identifier.
   *
   * @param fogIdentifier Fog Identifier
   */
  public void fogIdentifier(String fogIdentifier) {
    this.fogIdentifier = fogIdentifier;
  }

  /**
   * @return Fog Ids To Merge
   */
  public String[] fogIdsToMerge() {
    return this.fogIdsToMerge;
  }

  /**
   * @param fogIdsToMerge Fog Ids To Merge
   */
  public void fogIdsToMerge(String[] fogIdsToMerge) {
    this.fogIdsToMerge = fogIdsToMerge;
  }

  /**
   * @return Inherit From Prior Fog
   */
  public Boolean inheritFromPriorFog() {
    return this.inheritFromPriorFog;
  }

  /**
   * @param inheritFromPriorFog Inherit From Prior Fog
   */
  public void inheritFromPriorFog(boolean inheritFromPriorFog) {
    this.inheritFromPriorFog = inheritFromPriorFog;
  }

  /**
   * @return Remove All Prior Fog
   */
  public Boolean removeAllPriorFog() {
    return this.removeAllPriorFog;
  }

  /**
   * @param removeAllPriorFog Remove All Prior Fog
   */
  public void removeAllPriorFog(boolean removeAllPriorFog) {
    this.removeAllPriorFog = removeAllPriorFog;
  }

  /**
   * The distance the water fog start at.
   *
   * @return Water Fog Distance
   */
  public Integer waterFogDistance() {
    return this.waterFogDistance;
  }

  /**
   * The distance the water fog start at.
   *
   * @param waterFogDistance Water Fog Distance
   */
  public void waterFogDistance(int waterFogDistance) {
    this.waterFogDistance = waterFogDistance;
  }

  /**
   * The amount of transpareny the surface of the water has.
   *
   * @return Water Surface Transparency
   */
  public Float waterSurfaceTransparency() {
    return this.waterSurfaceTransparency;
  }

  /**
   * The amount of transpareny the surface of the water has.
   *
   * @param waterSurfaceTransparency Water Surface Transparency
   */
  public void waterSurfaceTransparency(float waterSurfaceTransparency) {
    this.waterSurfaceTransparency = waterSurfaceTransparency;
  }
}
