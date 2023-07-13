package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard;

import com.google.gson.annotations.SerializedName;
import java.lang.Integer;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.uv.Flipbook;

/**
 * Uv
 */
public class Uv {
  @SerializedName("texture_width")
  public Integer textureWidth;

  @SerializedName("texture_height")
  public Integer textureHeight;

  public Flipbook flipbook;

  public String[] uv;

  @SerializedName("uv_size")
  public String[] uvSize;

  /**
   * @return Texture Width
   */
  public Integer textureWidth() {
    return this.textureWidth;
  }

  /**
   * @param textureWidth Texture Width
   */
  public void textureWidth(int textureWidth) {
    this.textureWidth = textureWidth;
  }

  /**
   * @return Texture Height
   */
  public Integer textureHeight() {
    return this.textureHeight;
  }

  /**
   * @param textureHeight Texture Height
   */
  public void textureHeight(int textureHeight) {
    this.textureHeight = textureHeight;
  }

  /**
   * @return Flipbook
   */
  public Flipbook flipbook() {
    return this.flipbook;
  }

  /**
   * @param flipbook Flipbook
   */
  public void flipbook(Flipbook flipbook) {
    this.flipbook = flipbook;
  }

  /**
   * @return Uv
   */
  public String[] uv() {
    return this.uv;
  }

  /**
   * @param uv Uv
   */
  public void uv(String[] uv) {
    this.uv = uv;
  }

  /**
   * @return Uv Size
   */
  public String[] uvSize() {
    return this.uvSize;
  }

  /**
   * @param uvSize Uv Size
   */
  public void uvSize(String[] uvSize) {
    this.uvSize = uvSize;
  }
}
