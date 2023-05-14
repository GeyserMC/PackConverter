package org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Render Controllers
 * <p>
 * The collection of render controllers, each property is the identifier of a render controller.
 */
public class RenderControllers {
  public Arrays arrays;

  public Color color;

  @SerializedName("filter_lighting")
  public boolean filterLighting;

  public String geometry;

  @SerializedName("ignore_lighting")
  public boolean ignoreLighting;

  @SerializedName("is_hurt_color")
  public IsHurtColor isHurtColor;

  @SerializedName("light_color_multiplier")
  public String lightColorMultiplier;

  private Map<String, String> materials = new HashMap<>();

  @SerializedName("on_fire_color")
  public OnFireColor onFireColor;

  @SerializedName("overlay_color")
  public OverlayColor overlayColor;

  @SerializedName("part_visibility")
  private Map<String, String> partVisibility = new HashMap<>();

  @SerializedName("uv_anim")
  public UvAnim uvAnim;

  /**
   * A collection of definition of arrays.
   *
   * @return Arrays
   */
  public Arrays arrays() {
    return this.arrays;
  }

  /**
   * A collection of definition of arrays.
   *
   * @param arrays Arrays
   */
  public void arrays(Arrays arrays) {
    this.arrays = arrays;
  }

  /**
   * The color to apply.
   *
   * @return Color
   */
  public Color color() {
    return this.color;
  }

  /**
   * The color to apply.
   *
   * @param color Color
   */
  public void color(Color color) {
    this.color = color;
  }

  /**
   * Whenever or not to apply enviroment lighting to this object.
   *
   * @return Filter Lighting
   */
  public boolean filterLighting() {
    return this.filterLighting;
  }

  /**
   * Whenever or not to apply enviroment lighting to this object.
   *
   * @param filterLighting Filter Lighting
   */
  public void filterLighting(boolean filterLighting) {
    this.filterLighting = filterLighting;
  }

  /**
   * Molang definition.
   *
   * @return Molang
   */
  public String geometry() {
    return this.geometry;
  }

  /**
   * Molang definition.
   *
   * @param geometry Molang
   */
  public void geometry(String geometry) {
    this.geometry = geometry;
  }

  /**
   * Whenever or not to apply enviroment lighting to this object.
   *
   * @return Ignore Lighting
   */
  public boolean ignoreLighting() {
    return this.ignoreLighting;
  }

  /**
   * Whenever or not to apply enviroment lighting to this object.
   *
   * @param ignoreLighting Ignore Lighting
   */
  public void ignoreLighting(boolean ignoreLighting) {
    this.ignoreLighting = ignoreLighting;
  }

  /**
   * The color to overlay on the entity when hurt.
   *
   * @return Is Hurt Color
   */
  public IsHurtColor isHurtColor() {
    return this.isHurtColor;
  }

  /**
   * The color to overlay on the entity when hurt.
   *
   * @param isHurtColor Is Hurt Color
   */
  public void isHurtColor(IsHurtColor isHurtColor) {
    this.isHurtColor = isHurtColor;
  }

  public String lightColorMultiplier() {
    return this.lightColorMultiplier;
  }

  public void lightColorMultiplier(String lightColorMultiplier) {
    this.lightColorMultiplier = lightColorMultiplier;
  }

  /**
   * The specification where to apply materials to.
   *
   * @return Materials
   */
  public Map<String, String> materials() {
    return this.materials;
  }

  /**
   * The specification where to apply materials to.
   *
   * @param materials Materials
   */
  public void materials(Map<String, String> materials) {
    this.materials = materials;
  }

  /**
   * The color that will be overlayed when the object is on fire.
   *
   * @return On Fire Color
   */
  public OnFireColor onFireColor() {
    return this.onFireColor;
  }

  /**
   * The color that will be overlayed when the object is on fire.
   *
   * @param onFireColor On Fire Color
   */
  public void onFireColor(OnFireColor onFireColor) {
    this.onFireColor = onFireColor;
  }

  /**
   * The color to put over the object.
   *
   * @return Overlay Color
   */
  public OverlayColor overlayColor() {
    return this.overlayColor;
  }

  /**
   * The color to put over the object.
   *
   * @param overlayColor Overlay Color
   */
  public void overlayColor(OverlayColor overlayColor) {
    this.overlayColor = overlayColor;
  }

  /**
   * Determines what part of the object to show or hide.
   *
   * @return Part Visibility
   */
  public Map<String, String> partVisibility() {
    return this.partVisibility;
  }

  /**
   * Determines what part of the object to show or hide.
   *
   * @param partVisibility Part Visibility
   */
  public void partVisibility(Map<String, String> partVisibility) {
    this.partVisibility = partVisibility;
  }

  /**
   * The UV animation to apply to the render texture.
   *
   * @return Uv Anim
   */
  public UvAnim uvAnim() {
    return this.uvAnim;
  }

  /**
   * The UV animation to apply to the render texture.
   *
   * @param uvAnim Uv Anim
   */
  public void uvAnim(UvAnim uvAnim) {
    this.uvAnim = uvAnim;
  }
}
