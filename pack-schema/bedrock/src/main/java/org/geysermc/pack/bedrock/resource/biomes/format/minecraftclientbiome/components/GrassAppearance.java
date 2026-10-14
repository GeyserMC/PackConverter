package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;

/**
 * Grass Appearance
 * <p>
 * Set the grass color or color map used during rendering. Biomes without this component will have default grass appearance.
 */
public class GrassAppearance {
  @SerializedName("grass_is_shaded")
  public Boolean grassIsShaded;

  /**
   * Adds a shading effect to the grass as if there was a roof.
   *
   * @return Grass Is Shaded
   */
  public Boolean grassIsShaded() {
    return this.grassIsShaded;
  }

  /**
   * Adds a shading effect to the grass as if there was a roof.
   *
   * @param grassIsShaded Grass Is Shaded
   */
  public void grassIsShaded(boolean grassIsShaded) {
    this.grassIsShaded = grassIsShaded;
  }
}
