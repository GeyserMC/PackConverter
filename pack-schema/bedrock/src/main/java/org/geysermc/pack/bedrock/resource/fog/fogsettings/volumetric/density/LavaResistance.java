package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;

public class LavaResistance {
  @SerializedName("max_density")
  public Float maxDensity;

  @SerializedName("max_density_height")
  public Float maxDensityHeight;

  @SerializedName("zero_density_height")
  public Float zeroDensityHeight;

  public Boolean uniform;

  /**
   * The maximum amount of opaqueness that the ground fog will take on. A value from [0.0, 1.0].
   *
   * @return Maximum Density
   */
  public Float maxDensity() {
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
  public Float maxDensityHeight() {
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
  public Float zeroDensityHeight() {
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
  public Boolean uniform() {
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
