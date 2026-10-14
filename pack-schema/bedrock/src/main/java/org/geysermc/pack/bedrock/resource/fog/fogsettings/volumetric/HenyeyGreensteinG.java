package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric;

import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.henyeygreensteing.Air;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.henyeygreensteing.Water;

/**
 * Henyey Greenstein G
 * <p>
 * These are used to determine the distribution of scattered light in different mediums.
 */
public class HenyeyGreensteinG {
  public Air air;

  public Water water;

  /**
   * Used when light is passing through air.
   *
   * @return Air
   */
  public Air air() {
    return this.air;
  }

  /**
   * Used when light is passing through air.
   *
   * @param air Air
   */
  public void air(Air air) {
    this.air = air;
  }

  /**
   * Used when light is passing through water.
   *
   * @return Water
   */
  public Water water() {
    return this.water;
  }

  /**
   * Used when light is passing through water.
   *
   * @param water Water
   */
  public void water(Water water) {
    this.water = water;
  }
}
