package org.geysermc.pack.bedrock.resource.manifest;

import com.google.gson.annotations.SerializedName;

/**
 * Capabilities
 * <p>
 * These are the different features that the pack makes use of that aren't necessarily enabled by default.
 */
public class Capabilities {
  @SerializedName("experimental_custom_ui")
  public boolean experimentalCustomUi;

  public boolean chemistry;

  public boolean raytraced;

  /**
   * Allows HTML files in the pack to be used for custom UI, and scripts in the pack to call and manipulate custom UI.
   *
   * @return Experimental Custom Ui
   */
  public boolean experimentalCustomUi() {
    return this.experimentalCustomUi;
  }

  /**
   * Allows HTML files in the pack to be used for custom UI, and scripts in the pack to call and manipulate custom UI.
   *
   * @param experimentalCustomUi Experimental Custom Ui
   */
  public void experimentalCustomUi(boolean experimentalCustomUi) {
    this.experimentalCustomUi = experimentalCustomUi;
  }

  /**
   * Allows the pack to add, change or replace Chemistry functionality.
   *
   * @return Chemistry
   */
  public boolean chemistry() {
    return this.chemistry;
  }

  /**
   * Allows the pack to add, change or replace Chemistry functionality.
   *
   * @param chemistry Chemistry
   */
  public void chemistry(boolean chemistry) {
    this.chemistry = chemistry;
  }

  /**
   * Indicates that this pack contains Raytracing Enhanced or Physical Based Materials for rendering.
   *
   * @return Raytraced
   */
  public boolean raytraced() {
    return this.raytraced;
  }

  /**
   * Indicates that this pack contains Raytracing Enhanced or Physical Based Materials for rendering.
   *
   * @param raytraced Raytraced
   */
  public void raytraced(boolean raytraced) {
    this.raytraced = raytraced;
  }
}
