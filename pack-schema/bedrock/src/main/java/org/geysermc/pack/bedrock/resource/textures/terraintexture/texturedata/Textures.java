package org.geysermc.pack.bedrock.resource.textures.terraintexture.texturedata;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import org.geysermc.pack.bedrock.resource.textures.terraintexture.texturedata.textures.Variations;

/**
 * Texture
 * <p>
 * A collection of texture files.
 */
public class Textures {
  public String path;

  @SerializedName("tint_color")
  public String tintColor;

  public List<Variations> variations = new ArrayList<>();

  /**
   * A texture file.
   *
   * @return Path
   */
  public String path() {
    return this.path;
  }

  /**
   * A texture file.
   *
   * @param path Path
   */
  public void path(String path) {
    this.path = path;
  }

  /**
   * The tint color to be applied to the texture.
   *
   * @return Tint Color
   */
  public String tintColor() {
    return this.tintColor;
  }

  /**
   * The tint color to be applied to the texture.
   *
   * @param tintColor Tint Color
   */
  public void tintColor(String tintColor) {
    this.tintColor = tintColor;
  }

  /**
   * The possible variations to use for this texture.
   *
   * @return Variantions
   */
  public List<Variations> variations() {
    return this.variations;
  }

  /**
   * The possible variations to use for this texture.
   *
   * @param variations Variantions
   */
  public void variations(List<Variations> variations) {
    this.variations = variations;
  }
}
