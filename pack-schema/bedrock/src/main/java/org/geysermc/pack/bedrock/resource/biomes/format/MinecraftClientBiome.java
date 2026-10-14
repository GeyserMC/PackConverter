package org.geysermc.pack.bedrock.resource.biomes.format;

import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.Components;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.Description;

/**
 * Client Biome Definitions
 * <p>
 * A client biome definition.
 */
public class MinecraftClientBiome {
  public Description description;

  public Components components;

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

  /**
   * Any components that this Client Biome uses.
   *
   * @return Components
   */
  public Components components() {
    return this.components;
  }

  /**
   * Any components that this Client Biome uses.
   *
   * @param components Components
   */
  public void components(Components components) {
    this.components = components;
  }
}
