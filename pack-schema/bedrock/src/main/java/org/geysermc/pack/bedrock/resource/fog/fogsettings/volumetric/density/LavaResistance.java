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

  public Float maxDensity() {
    return this.maxDensity;
  }

  public void maxDensity(float maxDensity) {
    this.maxDensity = maxDensity;
  }

  public Float maxDensityHeight() {
    return this.maxDensityHeight;
  }

  public void maxDensityHeight(float maxDensityHeight) {
    this.maxDensityHeight = maxDensityHeight;
  }

  public Float zeroDensityHeight() {
    return this.zeroDensityHeight;
  }

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
