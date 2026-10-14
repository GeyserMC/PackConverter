package org.geysermc.pack.bedrock.resource.biomes;

import org.geysermc.pack.bedrock.resource.biomes.clientbiome.Description;

/**
 * Client Biome Definitions
 * <p>
 * A client biome definition.
 */
public class ClientBiome {
  public Description description;

  /**
   * Contains non-component settings for a Client Biome.
   *
   * @return Client Biome Description
   */
  public Description description() {
    return this.description;
  }

  /**
   * Contains non-component settings for a Client Biome.
   *
   * @param description Client Biome Description
   */
  public void description(Description description) {
    this.description = description;
  }
}
