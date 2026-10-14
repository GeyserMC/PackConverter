package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.henyeygreensteing;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;

/**
 * Air
 * <p>
 * Used when light is passing through air.
 */
public class Air {
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
