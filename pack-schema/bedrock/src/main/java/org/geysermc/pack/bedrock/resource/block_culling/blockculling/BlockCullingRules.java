package org.geysermc.pack.bedrock.resource.block_culling.blockculling;

import java.util.ArrayList;
import java.util.List;
import org.geysermc.pack.bedrock.resource.block_culling.blockculling.blockcullingrules.Description;
import org.geysermc.pack.bedrock.resource.block_culling.blockculling.blockcullingrules.Rules;

/**
 * Block Culling Rules
 * <p>
 * JSON container used for descriptions, especially the identifier for the name of the culled version of the block.
 */
public class BlockCullingRules {
  public Description description;

  public List<Rules> rules = new ArrayList<>();

  /**
   * Contains the identifier used by minecraft:geometry block components to refer to this culling data
   *
   * @return Description
   */
  public Description description() {
    return this.description;
  }

  /**
   * Contains the identifier used by minecraft:geometry block components to refer to this culling data
   *
   * @param description Description
   */
  public void description(Description description) {
    this.description = description;
  }

  /**
   * List of all components used to identify geometry parts used in culling.
   *
   * @return Rules
   */
  public List<Rules> rules() {
    return this.rules;
  }

  /**
   * List of all components used to identify geometry parts used in culling.
   *
   * @param rules Rules
   */
  public void rules(List<Rules> rules) {
    this.rules = rules;
  }
}
