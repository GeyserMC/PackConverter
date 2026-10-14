package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Sky Color
 * <p>
 * Set the sky color used during rendering. Biomes without this component will have default sky color behavior.
 */
public class SkyColor {
  @SerializedName("sky_color")
  public String skyColor;

  /**
   * RGB color of the sky.
   *
   * @return Sky Color
   */
  public String skyColor() {
    return this.skyColor;
  }

  /**
   * RGB color of the sky.
   *
   * @param skyColor Sky Color
   */
  public void skyColor(String skyColor) {
    this.skyColor = skyColor;
  }
}
