package org.geysermc.pack.bedrock.resource.entity.cliententity.description;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Spawn Egg
 * <p>
 * The definition of how the spawn_egg icon looks like.
 */
public class SpawnEgg {
  @SerializedName("base_color")
  public String baseColor;

  @SerializedName("overlay_color")
  public String overlayColor;

  public String texture;

  @SerializedName("texture_index")
  public int textureIndex;

  /**
   * The basic color of the egg.
   *
   * @return Base Color
   */
  public String baseColor() {
    return this.baseColor;
  }

  /**
   * The basic color of the egg.
   *
   * @param baseColor Base Color
   */
  public void baseColor(String baseColor) {
    this.baseColor = baseColor;
  }

  /**
   * The colors of the dots on the egg.
   *
   * @return Overlay Color
   */
  public String overlayColor() {
    return this.overlayColor;
  }

  /**
   * The colors of the dots on the egg.
   *
   * @param overlayColor Overlay Color
   */
  public void overlayColor(String overlayColor) {
    this.overlayColor = overlayColor;
  }

  /**
   * The texture reference in item_texture.json
   *
   * @return Texture
   */
  public String texture() {
    return this.texture;
  }

  /**
   * The texture reference in item_texture.json
   *
   * @param texture Texture
   */
  public void texture(String texture) {
    this.texture = texture;
  }

  /**
   * The index of the texture.
   *
   * @return Texture Index
   */
  public int textureIndex() {
    return this.textureIndex;
  }

  /**
   * The index of the texture.
   *
   * @param textureIndex Texture Index
   */
  public void textureIndex(int textureIndex) {
    this.textureIndex = textureIndex;
  }
}
