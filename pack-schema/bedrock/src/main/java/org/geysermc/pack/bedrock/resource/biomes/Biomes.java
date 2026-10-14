package org.geysermc.pack.bedrock.resource.biomes;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Client Biome
 * <p>
 * Minecraft client_biome files define client-side settings for biomes in resource packs. This is the new preferred location for per-biome settings that used to be in biomes_client.json.
 */
public class Biomes {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:client_biome")
  public ClientBiome clientBiome;

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * A client biome definition.
   *
   * @return Client Biome Definitions
   */
  public ClientBiome clientBiome() {
    return this.clientBiome;
  }

  /**
   * A client biome definition.
   *
   * @param clientBiome Client Biome Definitions
   */
  public void clientBiome(ClientBiome clientBiome) {
    this.clientBiome = clientBiome;
  }
}
