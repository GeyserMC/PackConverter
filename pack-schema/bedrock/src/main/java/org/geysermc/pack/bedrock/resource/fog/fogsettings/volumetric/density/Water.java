package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Water {
  @JsonProperty("max_density")
  public float maxDensity;

  @JsonProperty("max_density_height")
  public float maxDensityHeight;

  @JsonProperty("zero_density_height")
  public float zeroDensityHeight;

  public boolean uniform;

  /**
   * The maximum amount of opaqueness that the ground fog will take on. A value from [0.0, 1.0].
   *
   * @return Maximum Density
   */
  public float maxDensity() {
    return this.maxDensity;
  }

  /**
   * The maximum amount of opaqueness that the ground fog will take on. A value from [0.0, 1.0].
   *
   * @param maxDensity Maximum Density
   */
  public void maxDensity(float maxDensity) {
    this.maxDensity = maxDensity;
  }

  /**
   * The height in blocks that the ground fog will become it's maximum density.
   *
   * @return Maximum Density Height
   */
  public float maxDensityHeight() {
    return this.maxDensityHeight;
  }

  /**
   * The height in blocks that the ground fog will become it's maximum density.
   *
   * @param maxDensityHeight Maximum Density Height
   */
  public void maxDensityHeight(float maxDensityHeight) {
    this.maxDensityHeight = maxDensityHeight;
  }

  /**
   * The height in blocks that the ground fog will be completely transparent and begin to appear. This value needs to be at least 1 higher than `max_density_height`.
   *
   * @return Zero Density Height
   */
  public float zeroDensityHeight() {
    return this.zeroDensityHeight;
  }

  /**
   * The height in blocks that the ground fog will be completely transparent and begin to appear. This value needs to be at least 1 higher than `max_density_height`.
   *
   * @param zeroDensityHeight Zero Density Height
   */
  public void zeroDensityHeight(float zeroDensityHeight) {
    this.zeroDensityHeight = zeroDensityHeight;
  }

  /**
   * When set to true, the density will be uniform across all heights.
   *
   * @return Uniform
   */
  public boolean uniform() {
    return this.uniform;
  }

  /**
   * When set to true, the density will be uniform across all heights.
   *
   * @param uniform Uniform
   */
  public void uniform(boolean uniform) {
    this.uniform = uniform;
  }
}
