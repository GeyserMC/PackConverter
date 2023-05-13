package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric;

import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.mediacoefficients.Air;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.mediacoefficients.Cloud;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.mediacoefficients.Water;

/**
 * Media Coefficients
 * <p>
 * The coefficient settings for the volumetric fog in different blocks.
 */
public class MediaCoefficients {
  public Air air;

  public Water water;

  public Cloud cloud;

  public Air air() {
    return this.air;
  }

  public void air(Air air) {
    this.air = air;
  }

  public Water water() {
    return this.water;
  }

  public void water(Water water) {
    this.water = water;
  }

  public Cloud cloud() {
    return this.cloud;
  }

  public void cloud(Cloud cloud) {
    this.cloud = cloud;
  }
}
