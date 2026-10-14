package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Color Grading Identifier
 * <p>
 * Set the color_grading settings used during deferred rendering.
 * Biomes without this component will have default color_grading settings.
 */
public class ColorGradingIdentifier {
  @SerializedName("color_grading_identifier")
  public String colorGradingIdentifier;

  /**
   * Identifier of color_grading definition to use
   *
   * @return Color Grading Identifier
   */
  public String colorGradingIdentifier() {
    return this.colorGradingIdentifier;
  }

  /**
   * Identifier of color_grading definition to use
   *
   * @param colorGradingIdentifier Color Grading Identifier
   */
  public void colorGradingIdentifier(String colorGradingIdentifier) {
    this.colorGradingIdentifier = colorGradingIdentifier;
  }
}
