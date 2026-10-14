package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Water Identifier
 * <p>
 * Set the water settings used during deferred rendering.
 * Biomes without this component will have default water settings.
 */
public class WaterIdentifier {
  @SerializedName("water_identifier")
  public String waterIdentifier;

  /**
   * Identifier of water definition to use
   *
   * @return Water Identifier
   */
  public String waterIdentifier() {
    return this.waterIdentifier;
  }

  /**
   * Identifier of water definition to use
   *
   * @param waterIdentifier Water Identifier
   */
  public void waterIdentifier(String waterIdentifier) {
    this.waterIdentifier = waterIdentifier;
  }
}
