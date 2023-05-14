package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones;

import com.google.gson.annotations.SerializedName;

public class Locators {
  public float[] offset;

  public float[] rotation;

  @SerializedName("ignore_inherited_scale")
  public boolean ignoreInheritedScale;

  /**
   * Position of the locator in model space.
   */
  public float[] offset() {
    return this.offset;
  }

  /**
   * Position of the locator in model space.
   */
  public void offset(float[] offset) {
    this.offset = offset;
  }

  /**
   * Rotation of the locator in model space.
   */
  public float[] rotation() {
    return this.rotation;
  }

  /**
   * Rotation of the locator in model space.
   */
  public void rotation(float[] rotation) {
    this.rotation = rotation;
  }

  /**
   * Discard scale inherited from parent bone.
   */
  public boolean ignoreInheritedScale() {
    return this.ignoreInheritedScale;
  }

  /**
   * Discard scale inherited from parent bone.
   */
  public void ignoreInheritedScale(boolean ignoreInheritedScale) {
    this.ignoreInheritedScale = ignoreInheritedScale;
  }
}
