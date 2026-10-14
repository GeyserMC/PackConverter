package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import java.lang.String;

/**
 * Water Appearance
 * <p>
 * Set the water surface color used during rendering. Biomes without this component will have default water surface color behavior.
 */
public class WaterAppearance {
  @SerializedName("surface_color")
  public String surfaceColor;

  @SerializedName("surface_opacity")
  public Float surfaceOpacity;

  /**
   * RGB color of the water surface.
   *
   * @return Surface Color
   */
  public String surfaceColor() {
    return this.surfaceColor;
  }

  /**
   * RGB color of the water surface.
   *
   * @param surfaceColor Surface Color
   */
  public void surfaceColor(String surfaceColor) {
    this.surfaceColor = surfaceColor;
  }

  /**
   * Opacity of the water surface (0 for invisible and 1 for opaque)
   *
   * @return Surface Opacity
   */
  public Float surfaceOpacity() {
    return this.surfaceOpacity;
  }

  /**
   * Opacity of the water surface (0 for invisible and 1 for opaque)
   *
   * @param surfaceOpacity Surface Opacity
   */
  public void surfaceOpacity(float surfaceOpacity) {
    this.surfaceOpacity = surfaceOpacity;
  }
}
