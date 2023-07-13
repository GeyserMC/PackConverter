package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

public class TextureMeshes {
  @SerializedName("local_pivot")
  public float[] localPivot;

  public float[] position;

  public float[] rotation;

  public float[] scale;

  public String texture;

  /**
   * The pivot point on the texture (in *texture space* not entity or bone space) of the texture geometry.
   */
  public float[] localPivot() {
    return this.localPivot;
  }

  /**
   * The pivot point on the texture (in *texture space* not entity or bone space) of the texture geometry.
   */
  public void localPivot(float[] localPivot) {
    this.localPivot = localPivot;
  }

  /**
   * The position of the pivot point after rotation (in *entity space* not texture or bone space) of the texture geometry.
   */
  public float[] position() {
    return this.position;
  }

  /**
   * The position of the pivot point after rotation (in *entity space* not texture or bone space) of the texture geometry.
   */
  public void position(float[] position) {
    this.position = position;
  }

  /**
   * The rotation (in degrees) of the texture geometry relative to the offset.
   */
  public float[] rotation() {
    return this.rotation;
  }

  /**
   * The rotation (in degrees) of the texture geometry relative to the offset.
   */
  public void rotation(float[] rotation) {
    this.rotation = rotation;
  }

  /**
   * The scale (in degrees) of the texture geometry relative to the offset.
   */
  public float[] scale() {
    return this.scale;
  }

  /**
   * The scale (in degrees) of the texture geometry relative to the offset.
   */
  public void scale(float[] scale) {
    this.scale = scale;
  }

  /**
   * The friendly-named texture to use.
   */
  public String texture() {
    return this.texture;
  }

  /**
   * The friendly-named texture to use.
   */
  public void texture(String texture) {
    this.texture = texture;
  }
}
