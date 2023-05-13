package org.geysermc.pack.bedrock.resource.attachables;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Actor Animation 1.10.0
 */
public class Attachables {
  @JsonProperty("format_version")
  public String formatVersion;

  @JsonProperty("minecraft:attachable")
  public Attachable attachable;

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return 1.10.0 Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion 1.10.0 Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * The attachables definition.
   *
   * @return Attachables
   */
  public Attachable attachable() {
    return this.attachable;
  }

  /**
   * The attachables definition.
   *
   * @param attachable Attachables
   */
  public void attachable(Attachable attachable) {
    this.attachable = attachable;
  }
}
