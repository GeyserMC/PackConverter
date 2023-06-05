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

  public String[] initialize;

  @SerializedName("pre_animation")
  public String[] preAnimation;

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
   * @return Initialize
   */
  public String[] initialize() {
    return this.initialize;
  }

  /**
   * @param initialize Initialize
   */
  public void initialize(String[] initialize) {
    this.initialize = initialize;
  }

  /**
   * @return Pre Animation
   */
  public String[] preAnimation() {
    return this.preAnimation;
  }

  /**
   * @param preAnimation Pre Animation
   */
  public void preAnimation(String[] preAnimation) {
    this.preAnimation = preAnimation;
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
