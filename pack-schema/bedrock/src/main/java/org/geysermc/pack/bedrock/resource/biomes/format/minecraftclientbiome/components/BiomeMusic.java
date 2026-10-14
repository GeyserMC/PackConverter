package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Float;
import java.lang.String;

/**
 * Biome Music
 * <p>
 * Affects how music plays within the biome.
 */
public class BiomeMusic {
  @SerializedName("music_definition")
  public String musicDefinition;

  @SerializedName("underwater_music")
  public Boolean underwaterMusic;

  @SerializedName("volume_multiplier")
  public Float volumeMultiplier;

  /**
   * Music to be played when inside this biome. If left off or not found the default music will be determined by the dimension. Empty string will result in no music.
   *
   * @return Music Definition
   */
  public String musicDefinition() {
    return this.musicDefinition;
  }

  /**
   * Music to be played when inside this biome. If left off or not found the default music will be determined by the dimension. Empty string will result in no music.
   *
   * @param musicDefinition Music Definition
   */
  public void musicDefinition(String musicDefinition) {
    this.musicDefinition = musicDefinition;
  }

  /**
   * Enables underwater music
   *
   * @return Underwater Music
   */
  public Boolean underwaterMusic() {
    return this.underwaterMusic;
  }

  /**
   * Enables underwater music
   *
   * @param underwaterMusic Underwater Music
   */
  public void underwaterMusic(boolean underwaterMusic) {
    this.underwaterMusic = underwaterMusic;
  }

  /**
   * Multiplier temporarily and gradually applied to music volume when within this biome.
   *
   * @return Volume Multiplier
   */
  public Float volumeMultiplier() {
    return this.volumeMultiplier;
  }

  /**
   * Multiplier temporarily and gradually applied to music volume when within this biome.
   *
   * @param volumeMultiplier Volume Multiplier
   */
  public void volumeMultiplier(float volumeMultiplier) {
    this.volumeMultiplier = volumeMultiplier;
  }
}
