package org.geysermc.pack.bedrock.resource.block_culling.blockculling.blockcullingrules;

import java.lang.Integer;
import java.lang.String;

/**
 * Geometry Part
 * <p>
 * Specifies the bone, cube, and face that the block will be culled. The cube and face fields are optional to allow culling a specific face. Omitting these fields will cull the whole bone.
 */
public class GeometryPart {
  public String bone;

  public Integer cube;

  public String face;

  /**
   * The bone within the geometry part
   *
   * @return Bone
   */
  public String bone() {
    return this.bone;
  }

  /**
   * The bone within the geometry part
   *
   * @param bone Bone
   */
  public void bone(String bone) {
    this.bone = bone;
  }

  /**
   * The cube within the geometry part
   *
   * @return Cube
   */
  public Integer cube() {
    return this.cube;
  }

  /**
   * The cube within the geometry part
   *
   * @param cube Cube
   */
  public void cube(int cube) {
    this.cube = cube;
  }

  /**
   * The face within the geometry part
   *
   * @return Face
   */
  public String face() {
    return this.face;
  }

  /**
   * The face within the geometry part
   *
   * @param face Face
   */
  public void face(String face) {
    this.face = face;
  }
}
