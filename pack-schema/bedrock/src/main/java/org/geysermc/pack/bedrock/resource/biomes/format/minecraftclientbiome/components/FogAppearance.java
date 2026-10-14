package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Fog Appearance
 * <p>
 * Set the fog settings used during rendering. Biomes without this component will have default fog settings.
 */
public class FogAppearance {
  @SerializedName("fog_identifier")
  public String fogIdentifier;

  /**
   * Identifier of fog definition to use.
   *
   * @return Fog Identifier
   */
  public String fogIdentifier() {
    return this.fogIdentifier;
  }

  /**
   * Identifier of fog definition to use.
   *
   * @param fogIdentifier Fog Identifier
   */
  public void fogIdentifier(String fogIdentifier) {
    this.fogIdentifier = fogIdentifier;
  }
}
