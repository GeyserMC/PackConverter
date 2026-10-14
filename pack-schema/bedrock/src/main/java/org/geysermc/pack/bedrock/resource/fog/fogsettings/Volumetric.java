package org.geysermc.pack.bedrock.resource.fog.fogsettings;

import com.google.gson.annotations.SerializedName;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.Density;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.HenyeyGreensteinG;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.MediaCoefficients;

/**
 * Volumetric
 * <p>
 * The volumetric fog settings.
 */
public class Volumetric {
  public Density density;

  @SerializedName("media_coefficients")
  public MediaCoefficients mediaCoefficients;

  @SerializedName("henyey_greenstein_g")
  public HenyeyGreensteinG henyeyGreensteinG;

  /**
   * The density settings for different camera locations.
   *
   * @return Density
   */
  public Density density() {
    return this.density;
  }

  /**
   * The density settings for different camera locations.
   *
   * @param density Density
   */
  public void density(Density density) {
    this.density = density;
  }

  /**
   * The coefficient settings for the volumetric fog in different blocks.
   *
   * @return Media Coefficients
   */
  public MediaCoefficients mediaCoefficients() {
    return this.mediaCoefficients;
  }

  /**
   * The coefficient settings for the volumetric fog in different blocks.
   *
   * @param mediaCoefficients Media Coefficients
   */
  public void mediaCoefficients(MediaCoefficients mediaCoefficients) {
    this.mediaCoefficients = mediaCoefficients;
  }

  /**
   * These are used to determine the distribution of scattered light in different mediums.
   *
   * @return Henyey Greenstein G
   */
  public HenyeyGreensteinG henyeyGreensteinG() {
    return this.henyeyGreensteinG;
  }

  /**
   * These are used to determine the distribution of scattered light in different mediums.
   *
   * @param henyeyGreensteinG Henyey Greenstein G
   */
  public void henyeyGreensteinG(HenyeyGreensteinG henyeyGreensteinG) {
    this.henyeyGreensteinG = henyeyGreensteinG;
  }
}
