package org.geysermc.pack.bedrock.resource.biomesclient.biomes;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Biome
 * <p>
 * The specification of colors in a given biome.
 */
public class IcePlainsSpikes {
  @JsonProperty("fog_identifier")
  public String fogIdentifier;

  @JsonProperty("fog_ids_to_merge")
  public String[] fogIdsToMerge;

  @JsonProperty("inherit_from_prior_fog")
  public boolean inheritFromPriorFog;

  @JsonProperty("remove_all_prior_fog")
  public boolean removeAllPriorFog;

  @JsonProperty("water_fog_distance")
  public int waterFogDistance;

  @JsonProperty("water_surface_transparency")
  public float waterSurfaceTransparency;

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
  public boolean inheritFromPriorFog() {
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
  public boolean removeAllPriorFog() {
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
  public int waterFogDistance() {
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
  public float waterSurfaceTransparency() {
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
