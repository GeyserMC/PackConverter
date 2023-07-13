package org.geysermc.pack.bedrock.resource.attachables;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Actor Animation 1.10.0
 */
public class Attachables {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:attachable")
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
