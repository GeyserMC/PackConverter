package org.geysermc.pack.bedrock.resource.attachables.attachable.description;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Scripts
 */
public class Scripts {
  public List<Map<String, String>> animate = new ArrayList<>();

  @SerializedName("parent_setup")
  public String parentSetup;

  public String scale;

  /**
   * @return Animate
   */
  public List<Map<String, String>> animate() {
    return this.animate;
  }

  /**
   * @param animate Animate
   */
  public void animate(List<Map<String, String>> animate) {
    this.animate = animate;
  }

  /**
   * @return Parent Setup
   */
  public String parentSetup() {
    return this.parentSetup;
  }

  /**
   * @param parentSetup Parent Setup
   */
  public void parentSetup(String parentSetup) {
    this.parentSetup = parentSetup;
  }

  /**
   * @return Scale
   */
  public String scale() {
    return this.scale;
  }

  /**
   * @param scale Scale
   */
  public void scale(String scale) {
    this.scale = scale;
  }
}
