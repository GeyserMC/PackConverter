package org.geysermc.pack.bedrock.resource.render_controllers;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Render Controllers
 * <p>
 * A collection of render controllers to apply.
 */
public class RenderControllers {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("render_controllers")
  private Map<String, org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers.RenderControllers> renderControllers = new HashMap<>();

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * The collection of render controllers, each property is the identifier of a render controller.
   *
   * @return Render Controllers
   */
  public Map<String, org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers.RenderControllers> renderControllers(
      ) {
    return this.renderControllers;
  }

  /**
   * The collection of render controllers, each property is the identifier of a render controller.
   *
   * @param renderControllers Render Controllers
   */
  public void renderControllers(
      Map<String, org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers.RenderControllers> renderControllers) {
    this.renderControllers = renderControllers;
  }
}
