package org.geysermc.pack.bedrock.resource.block_culling;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.block_culling.blockculling.BlockCullingRules;

/**
 * Block Culling
 * <p>
 * A resource pack file that helps the system determine how to change the appearance of this block.
 */
public class BlockCulling {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:block_culling_rules")
  public BlockCullingRules blockCullingRules;

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
   * JSON container used for descriptions, especially the identifier for the name of the culled version of the block.
   *
   * @return Block Culling Rules
   */
  public BlockCullingRules blockCullingRules() {
    return this.blockCullingRules;
  }

  /**
   * JSON container used for descriptions, especially the identifier for the name of the culled version of the block.
   *
   * @param blockCullingRules Block Culling Rules
   */
  public void blockCullingRules(BlockCullingRules blockCullingRules) {
    this.blockCullingRules = blockCullingRules;
  }
}
