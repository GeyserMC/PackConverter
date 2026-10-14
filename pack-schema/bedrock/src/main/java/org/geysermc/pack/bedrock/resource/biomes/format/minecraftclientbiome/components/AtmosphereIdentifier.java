package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Atmosphere Identifier
 * <p>
 * Set the atmosphere settings used during deferred rendering.
 * Biomes without this component will have default atmosphere settings.
 */
public class AtmosphereIdentifier {
  @SerializedName("atmosphere_identifier")
  public String atmosphereIdentifier;

  /**
   * Identifier of atmosphere definition to use
   *
   * @return Atmosphere Identifier
   */
  public String atmosphereIdentifier() {
    return this.atmosphereIdentifier;
  }

  /**
   * Identifier of atmosphere definition to use
   *
   * @param atmosphereIdentifier Atmosphere Identifier
   */
  public void atmosphereIdentifier(String atmosphereIdentifier) {
    this.atmosphereIdentifier = atmosphereIdentifier;
  }
}
