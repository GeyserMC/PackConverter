package org.geysermc.pack.bedrock.resource.fog;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Fog
 */
public class Fog {
  @JsonProperty("format_version")
  public String formatVersion;

  @JsonProperty("minecraft:fog_settings")
  public FogSettings fogSettings;

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
   * The definition of a single fog.
   *
   * @return Fog Settings
   */
  public FogSettings fogSettings() {
    return this.fogSettings;
  }

  /**
   * The definition of a single fog.
   *
   * @param fogSettings Fog Settings
   */
  public void fogSettings(FogSettings fogSettings) {
    this.fogSettings = fogSettings;
  }
}
