package org.geysermc.pack.bedrock.resource.manifest;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;

public class Capabilities {
  public Boolean chemistry;

  public Boolean editorExtension;

  @SerializedName("experimental_custom_ui")
  public Boolean experimentalCustomUi;

  public Boolean raytraced;

  /**
   * Allows the pack to add, change or replace Chemistry functionality.
   *
   * @return Chemistry
   */
  public Boolean chemistry() {
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
   * Indicates that this pack contains extensions for editing.
   *
   * @return Editor Extension
   */
  public Boolean editorExtension() {
    return this.editorExtension;
  }

  /**
   * Indicates that this pack contains extensions for editing.
   *
   * @param editorExtension Editor Extension
   */
  public void editorExtension(boolean editorExtension) {
    this.editorExtension = editorExtension;
  }

  /**
   * Allows HTML files in the pack to be used for custom UI, and scripts in the pack to call and manipulate custom UI.
   *
   * @return Experimental Custom Ui
   */
  public Boolean experimentalCustomUi() {
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
   * Indicates that this pack contains Raytracing Enhanced or Physical Based Materials for rendering.
   *
   * @return Raytraced
   */
  public Boolean raytraced() {
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
