package org.geysermc.pack.bedrock.resource;

import org.geysermc.pack.bedrock.resource.biomesclient.Biomes;

/**
 * Biomes Client
 * <p>
 * The minecraft biomes definition file.
 */
public class BiomesClient {
  public Biomes biomes;

  /**
   * A collection of predefined biomes.
   *
   * @return Biomes
   */
  public Biomes biomes() {
    return this.biomes;
  }

  /**
   * A collection of predefined biomes.
   *
   * @param biomes Biomes
   */
  public void biomes(Biomes biomes) {
    this.biomes = biomes;
  }
}
