package org.geysermc.pack.bedrock.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * World X Pack
 * <p>
 * A reference to a pack.
 */
public class WorldXPack {
  @SerializedName("pack_id")
  public String packId;

  public float[] version;

  /**
   * A valid UUID v4.
   *
   * @return A UUID Format V4
   */
  public String packId() {
    return this.packId;
  }

  /**
   * A valid UUID v4.
   *
   * @param packId A UUID Format V4
   */
  public void packId(String packId) {
    this.packId = packId;
  }

  /**
   * A version made of 3 numbers.
   *
   * @return Version Numbering
   */
  public float[] version() {
    return this.version;
  }

  /**
   * A version made of 3 numbers.
   *
   * @param version Version Numbering
   */
  public void version(float[] version) {
    this.version = version;
  }
}
