package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Cubemap Identifier
 * <p>
 * Set the identifier used for cubemap in Vibrant Visuals mode. Identifiers must resolve to identifiers in valid Cubemap JSON schemas under the "cubemaps" directory. Biomes without this component will have default cubemap settings.
 */
public class CubemapIdentifier {
  @SerializedName("cubemap_identifier")
  public String cubemapIdentifier;

  /**
   * Identifier of cubemap definition to use
   *
   * @return Cubemap Identifier
   */
  public String cubemapIdentifier() {
    return this.cubemapIdentifier;
  }

  /**
   * Identifier of cubemap definition to use
   *
   * @param cubemapIdentifier Cubemap Identifier
   */
  public void cubemapIdentifier(String cubemapIdentifier) {
    this.cubemapIdentifier = cubemapIdentifier;
  }
}
