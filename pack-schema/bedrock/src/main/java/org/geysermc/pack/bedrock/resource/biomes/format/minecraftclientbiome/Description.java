package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome;

import java.lang.String;

/**
 * Client Biome Description
 * <p>
 * Contains non-component settings for a Client Biome.
 */
public class Description {
  public String identifier;

  /**
   * The name of the Client Biome, used by other features like the '/locate biome' command. Must match the name of a Biome defined by the game or a behavior pack.
   *
   * @return Identifier
   */
  public String identifier() {
    return this.identifier;
  }

  /**
   * The name of the Client Biome, used by other features like the '/locate biome' command. Must match the name of a Biome defined by the game or a behavior pack.
   *
   * @param identifier Identifier
   */
  public void identifier(String identifier) {
    this.identifier = identifier;
  }
}
