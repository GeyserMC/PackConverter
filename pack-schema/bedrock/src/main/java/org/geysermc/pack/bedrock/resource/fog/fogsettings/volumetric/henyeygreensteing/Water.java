package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.henyeygreensteing;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;

/**
 * Water
 * <p>
 * Used when light is passing through water.
 */
public class Water {
  @SerializedName("henyey_greenstein_g")
  public Float henyeyGreensteinG;

  /**
   * Controls the distribution of scattered light.
   *
   * @return Heyney GreenStein G
   */
  public Float henyeyGreensteinG() {
    return this.henyeyGreensteinG;
  }

  /**
   * Controls the distribution of scattered light.
   *
   * @param henyeyGreensteinG Heyney GreenStein G
   */
  public void henyeyGreensteinG(float henyeyGreensteinG) {
    this.henyeyGreensteinG = henyeyGreensteinG;
  }
}
