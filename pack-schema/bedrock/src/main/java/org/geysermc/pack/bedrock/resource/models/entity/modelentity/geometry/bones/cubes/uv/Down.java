package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv;

import com.google.gson.annotations.SerializedName;
import java.lang.Integer;
import java.lang.String;

public class Down {
  public float[] uv;

  @SerializedName("uv_size")
  public float[] uvSize;

  @SerializedName("material_instance")
  public String materialInstance;

  @SerializedName("uv_rotation")
  public Integer uvRotation;

  public float[] uv() {
    return this.uv;
  }

  public void uv(float[] uv) {
    this.uv = uv;
  }

  public float[] uvSize() {
    return this.uvSize;
  }

  public void uvSize(float[] uvSize) {
    this.uvSize = uvSize;
  }

  /**
   * Specifies the UV's for the face that stretches.
   *
   * @return Material Instance
   */
  public String materialInstance() {
    return this.materialInstance;
  }

  /**
   * Specifies the UV's for the face that stretches.
   *
   * @param materialInstance Material Instance
   */
  public void materialInstance(String materialInstance) {
    this.materialInstance = materialInstance;
  }

  /**
   * Specifies an optional rotation for the specified UV rect in 90-degree clockwise increments before applying it to a geometry cube face. If not specified, no rotation will be applied.
   *
   * @return Uv Rotation
   */
  public Integer uvRotation() {
    return this.uvRotation;
  }

  /**
   * Specifies an optional rotation for the specified UV rect in 90-degree clockwise increments before applying it to a geometry cube face. If not specified, no rotation will be applied.
   *
   * @param uvRotation Uv Rotation
   */
  public void uvRotation(int uvRotation) {
    this.uvRotation = uvRotation;
  }
}
