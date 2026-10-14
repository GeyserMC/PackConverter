package org.geysermc.pack.bedrock.resource.block_culling.blockculling.blockcullingrules;

import java.lang.String;

/**
 * Description
 * <p>
 * Contains the identifier used by minecraft:geometry block components to refer to this culling data
 */
public class Description {
  public String identifier;

  /**
   * The minecraft block culling rule identifier.
   *
   * @return Block Culling Rule Identifier
   */
  public String identifier() {
    return this.identifier;
  }

  /**
   * The minecraft block culling rule identifier.
   *
   * @param identifier Block Culling Rule Identifier
   */
  public void identifier(String identifier) {
    this.identifier = identifier;
  }
}
