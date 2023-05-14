package org.geysermc.pack.bedrock.resource.attachables.attachable.description;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Scripts
 */
public class Scripts {
  private Map<String, String> animate = new HashMap<>();

  @SerializedName("parent_setup")
  public String parentSetup;

  public String scale;

  /**
   * @return Animate
   */
  public Map<String, String> animate() {
    return this.animate;
  }

  /**
   * @param animate Animate
   */
  public void animate(Map<String, String> animate) {
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
