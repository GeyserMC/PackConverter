package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;

/**
 * 9Slice Texture File
 * <p>
 * The file that specifies 9Slice textures.
 */
public class NineSlice {
  @SerializedName("nineslice_size")
  public float[] ninesliceSize;

  @SerializedName("base_size")
  public float[] baseSize;

  /**
   * Offsets from left, top, right and bottom of the texture file (in this exact order).
   */
  public float[] ninesliceSize() {
    return this.ninesliceSize;
  }

  /**
   * Offsets from left, top, right and bottom of the texture file (in this exact order).
   */
  public void ninesliceSize(float[] ninesliceSize) {
    this.ninesliceSize = ninesliceSize;
  }

  /**
   * Width and height of the texture (in this exact order).
   *
   * @return Size of the texture
   */
  public float[] baseSize() {
    return this.baseSize;
  }

  /**
   * Width and height of the texture (in this exact order).
   *
   * @param baseSize Size of the texture
   */
  public void baseSize(float[] baseSize) {
    this.baseSize = baseSize;
  }
}
