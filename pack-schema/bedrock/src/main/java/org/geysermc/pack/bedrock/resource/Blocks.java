package org.geysermc.pack.bedrock.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.blocks.CarriedTextures;
import org.geysermc.pack.bedrock.resource.blocks.Isotropic;
import org.geysermc.pack.bedrock.resource.blocks.Textures;

/**
 * Blocks
 * <p>
 * The minecraft block definition file.
 */
public class Blocks {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("brightness_gamma")
  public float brightnessGamma;

  @SerializedName("carried_textures")
  public CarriedTextures carriedTextures;

  public Isotropic isotropic;

  public String sound;

  public Textures textures;

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
   * Specifies the gamma brightness level to apply to the block texture.
   *
   * @return Brightness Gamma
   */
  public float brightnessGamma() {
    return this.brightnessGamma;
  }

  /**
   * Specifies the gamma brightness level to apply to the block texture.
   *
   * @param brightnessGamma Brightness Gamma
   */
  public void brightnessGamma(float brightnessGamma) {
    this.brightnessGamma = brightnessGamma;
  }

  public CarriedTextures carriedTextures() {
    return this.carriedTextures;
  }

  public void carriedTextures(CarriedTextures carriedTextures) {
    this.carriedTextures = carriedTextures;
  }

  public Isotropic isotropic() {
    return this.isotropic;
  }

  public void isotropic(Isotropic isotropic) {
    this.isotropic = isotropic;
  }

  /**
   * The sound definition of this block.
   *
   * @return Sound
   */
  public String sound() {
    return this.sound;
  }

  /**
   * The sound definition of this block.
   *
   * @param sound Sound
   */
  public void sound(String sound) {
    this.sound = sound;
  }

  public Textures textures() {
    return this.textures;
  }

  public void textures(Textures textures) {
    this.textures = textures;
  }
}
