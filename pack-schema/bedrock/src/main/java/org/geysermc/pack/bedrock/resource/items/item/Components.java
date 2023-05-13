package org.geysermc.pack.bedrock.resource.items.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Components
 * <p>
 * The components that describe this item.
 */
public class Components {
  @JsonProperty("minecraft:icon")
  public String icon;

  @JsonProperty("minecraft:render_offsets")
  public String renderOffsets;

  /**
   * The texture defined in `textures/item_texture.json`
   *
   * @return Icon
   */
  public String icon() {
    return this.icon;
  }

  /**
   * The texture defined in `textures/item_texture.json`
   *
   * @param icon Icon
   */
  public void icon(String icon) {
    this.icon = icon;
  }

  /**
   * The render offset used for the item.
   *
   * @return Render Offsets
   */
  public String renderOffsets() {
    return this.renderOffsets;
  }

  /**
   * The render offset used for the item.
   *
   * @param renderOffsets Render Offsets
   */
  public void renderOffsets(String renderOffsets) {
    this.renderOffsets = renderOffsets;
  }
}
