package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Lighting Identifier
 * <p>
 * Set the lighting settings used during deferred rendering.
 * Biomes without this component will have default lighting settings.
 */
public class LightingIdentifier {
  @SerializedName("lighting_identifier")
  public String lightingIdentifier;

  /**
   * Identifier of lighting definition to use
   *
   * @return Lighting Identifier
   */
  public String lightingIdentifier() {
    return this.lightingIdentifier;
  }

  /**
   * Identifier of lighting definition to use
   *
   * @param lightingIdentifier Lighting Identifier
   */
  public void lightingIdentifier(String lightingIdentifier) {
    this.lightingIdentifier = lightingIdentifier;
  }
}
