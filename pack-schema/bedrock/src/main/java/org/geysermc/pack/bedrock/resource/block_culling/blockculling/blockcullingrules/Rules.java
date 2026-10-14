package org.geysermc.pack.bedrock.resource.block_culling.blockculling.blockcullingrules;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

/**
 * Rule
 * <p>
 * The rules that specifies a "geometry_part" and "direction"
 */
public class Rules {
  @SerializedName("geometry_part")
  public GeometryPart geometryPart;

  @SerializedName("cull_against_full_and_opaque")
  public Boolean cullAgainstFullAndOpaque;

  public String condition;

  public String direction;

  /**
   * Specifies the bone, cube, and face that the block will be culled. The cube and face fields are optional to allow culling a specific face. Omitting these fields will cull the whole bone.
   *
   * @return Geometry Part
   */
  public GeometryPart geometryPart() {
    return this.geometryPart;
  }

  /**
   * Specifies the bone, cube, and face that the block will be culled. The cube and face fields are optional to allow culling a specific face. Omitting these fields will cull the whole bone.
   *
   * @param geometryPart Geometry Part
   */
  public void geometryPart(GeometryPart geometryPart) {
    this.geometryPart = geometryPart;
  }

  /**
   * Whether to cull against a full, opaque block.
   *
   * @return Cull Against Full And Opaque
   */
  public Boolean cullAgainstFullAndOpaque() {
    return this.cullAgainstFullAndOpaque;
  }

  /**
   * Whether to cull against a full, opaque block.
   *
   * @param cullAgainstFullAndOpaque Cull Against Full And Opaque
   */
  public void cullAgainstFullAndOpaque(boolean cullAgainstFullAndOpaque) {
    this.cullAgainstFullAndOpaque = cullAgainstFullAndOpaque;
  }

  /**
   * It provides more control over which neighbor blocks will trigger a face or part to be culled.
   * - "default" will pass the condition if the neighbor block is full and opaque. All conditions are an extension of "default", meaning a full and opaque neighbor will always be a passing condition, before the extended "same_*" condition is checked.
   * - "same_culling_layer" will pass the condition if the neighbor block shares the same "culling_layer" as the block being occluded, defined in the geometry component. If either of the culling layers being compared is "minecraft:culling_layer.undefined", the condition will never pass.
   * - "same_block" will pass the condition if the neighbor block has the same identifier.
   * - "same_block_permutation" will pass the condition if the neighbor block has the same identifier and is the exact same permutation.
   *
   * @return Condition
   */
  public String condition() {
    return this.condition;
  }

  /**
   * It provides more control over which neighbor blocks will trigger a face or part to be culled.
   * - "default" will pass the condition if the neighbor block is full and opaque. All conditions are an extension of "default", meaning a full and opaque neighbor will always be a passing condition, before the extended "same_*" condition is checked.
   * - "same_culling_layer" will pass the condition if the neighbor block shares the same "culling_layer" as the block being occluded, defined in the geometry component. If either of the culling layers being compared is "minecraft:culling_layer.undefined", the condition will never pass.
   * - "same_block" will pass the condition if the neighbor block has the same identifier.
   * - "same_block_permutation" will pass the condition if the neighbor block has the same identifier and is the exact same permutation.
   *
   * @param condition Condition
   */
  public void condition(String condition) {
    this.condition = condition;
  }

  /**
   * Specifies the direction of the neighbor block to check for culling. This direction rotates with a block's Transform component.
   *
   * @return Direction
   */
  public String direction() {
    return this.direction;
  }

  /**
   * Specifies the direction of the neighbor block to check for culling. This direction rotates with a block's Transform component.
   *
   * @param direction Direction
   */
  public void direction(String direction) {
    this.direction = direction;
  }
}
