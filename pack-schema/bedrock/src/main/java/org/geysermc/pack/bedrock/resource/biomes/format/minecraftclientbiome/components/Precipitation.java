package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;

/**
 * Precipitation
 * <p>
 * Describes the visuals for a biome's precipitation. Biomes without this component will have default values. At most one precipitation type can be set for a biome.
 */
public class Precipitation {
  public Float ash;

  @SerializedName("blue_spores")
  public Float blueSpores;

  @SerializedName("red_spores")
  public Float redSpores;

  @SerializedName("white_ash")
  public Float whiteAsh;

  /**
   * Density of ash precipitation visuals.
   *
   * @return Ash
   */
  public Float ash() {
    return this.ash;
  }

  /**
   * Density of ash precipitation visuals.
   *
   * @param ash Ash
   */
  public void ash(float ash) {
    this.ash = ash;
  }

  /**
   * Density of blue spore precipitation visuals.
   *
   * @return Blue Spores
   */
  public Float blueSpores() {
    return this.blueSpores;
  }

  /**
   * Density of blue spore precipitation visuals.
   *
   * @param blueSpores Blue Spores
   */
  public void blueSpores(float blueSpores) {
    this.blueSpores = blueSpores;
  }

  /**
   * Density of blue spore precipitation visuals.
   *
   * @return Red Spores
   */
  public Float redSpores() {
    return this.redSpores;
  }

  /**
   * Density of blue spore precipitation visuals.
   *
   * @param redSpores Red Spores
   */
  public void redSpores(float redSpores) {
    this.redSpores = redSpores;
  }

  /**
   * Density of white ash precipitation visuals.
   *
   * @return White Ash
   */
  public Float whiteAsh() {
    return this.whiteAsh;
  }

  /**
   * Density of white ash precipitation visuals.
   *
   * @param whiteAsh White Ash
   */
  public void whiteAsh(float whiteAsh) {
    this.whiteAsh = whiteAsh;
  }
}
