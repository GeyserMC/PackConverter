package org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome;

import com.google.gson.annotations.SerializedName;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.AmbientSounds;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.AtmosphereIdentifier;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.BiomeMusic;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.ColorGradingIdentifier;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.CubemapIdentifier;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.DryFoliageColor;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.FogAppearance;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.FoliageAppearance;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.GrassAppearance;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.LightingIdentifier;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.Precipitation;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.SkyColor;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.WaterAppearance;
import org.geysermc.pack.bedrock.resource.biomes.format.minecraftclientbiome.components.WaterIdentifier;

/**
 * Components
 * <p>
 * Any components that this Client Biome uses.
 */
public class Components {
  @SerializedName("minecraft:ambient_sounds")
  public AmbientSounds ambientSounds;

  @SerializedName("minecraft:atmosphere_identifier")
  public AtmosphereIdentifier atmosphereIdentifier;

  @SerializedName("minecraft:biome_music")
  public BiomeMusic biomeMusic;

  @SerializedName("minecraft:color_grading_identifier")
  public ColorGradingIdentifier colorGradingIdentifier;

  @SerializedName("minecraft:cubemap_identifier")
  public CubemapIdentifier cubemapIdentifier;

  @SerializedName("minecraft:dry_foliage_color")
  public DryFoliageColor dryFoliageColor;

  @SerializedName("minecraft:fog_appearance")
  public FogAppearance fogAppearance;

  @SerializedName("minecraft:foliage_appearance")
  public FoliageAppearance foliageAppearance;

  @SerializedName("minecraft:grass_appearance")
  public GrassAppearance grassAppearance;

  @SerializedName("minecraft:lighting_identifier")
  public LightingIdentifier lightingIdentifier;

  @SerializedName("minecraft:sky_color")
  public SkyColor skyColor;

  @SerializedName("minecraft:precipitation")
  public Precipitation precipitation;

  @SerializedName("minecraft:water_appearance")
  public WaterAppearance waterAppearance;

  @SerializedName("minecraft:water_identifier")
  public WaterIdentifier waterIdentifier;

  /**
   * Sets the ambient sounds for the biome. These sounds must be in the 'individual_named_sounds' in a 'sounds.json' file.
   *
   * @return Ambient Sounds
   */
  public AmbientSounds ambientSounds() {
    return this.ambientSounds;
  }

  /**
   * Sets the ambient sounds for the biome. These sounds must be in the 'individual_named_sounds' in a 'sounds.json' file.
   *
   * @param ambientSounds Ambient Sounds
   */
  public void ambientSounds(AmbientSounds ambientSounds) {
    this.ambientSounds = ambientSounds;
  }

  /**
   * Set the atmosphere settings used during deferred rendering.
   * Biomes without this component will have default atmosphere settings.
   *
   * @return Atmosphere Identifier
   */
  public AtmosphereIdentifier atmosphereIdentifier() {
    return this.atmosphereIdentifier;
  }

  /**
   * Set the atmosphere settings used during deferred rendering.
   * Biomes without this component will have default atmosphere settings.
   *
   * @param atmosphereIdentifier Atmosphere Identifier
   */
  public void atmosphereIdentifier(AtmosphereIdentifier atmosphereIdentifier) {
    this.atmosphereIdentifier = atmosphereIdentifier;
  }

  /**
   * Affects how music plays within the biome.
   *
   * @return Biome Music
   */
  public BiomeMusic biomeMusic() {
    return this.biomeMusic;
  }

  /**
   * Affects how music plays within the biome.
   *
   * @param biomeMusic Biome Music
   */
  public void biomeMusic(BiomeMusic biomeMusic) {
    this.biomeMusic = biomeMusic;
  }

  /**
   * Set the color_grading settings used during deferred rendering.
   * Biomes without this component will have default color_grading settings.
   *
   * @return Color Grading Identifier
   */
  public ColorGradingIdentifier colorGradingIdentifier() {
    return this.colorGradingIdentifier;
  }

  /**
   * Set the color_grading settings used during deferred rendering.
   * Biomes without this component will have default color_grading settings.
   *
   * @param colorGradingIdentifier Color Grading Identifier
   */
  public void colorGradingIdentifier(ColorGradingIdentifier colorGradingIdentifier) {
    this.colorGradingIdentifier = colorGradingIdentifier;
  }

  /**
   * Set the identifier used for cubemap in Vibrant Visuals mode. Identifiers must resolve to identifiers in valid Cubemap JSON schemas under the "cubemaps" directory. Biomes without this component will have default cubemap settings.
   *
   * @return Cubemap Identifier
   */
  public CubemapIdentifier cubemapIdentifier() {
    return this.cubemapIdentifier;
  }

  /**
   * Set the identifier used for cubemap in Vibrant Visuals mode. Identifiers must resolve to identifiers in valid Cubemap JSON schemas under the "cubemaps" directory. Biomes without this component will have default cubemap settings.
   *
   * @param cubemapIdentifier Cubemap Identifier
   */
  public void cubemapIdentifier(CubemapIdentifier cubemapIdentifier) {
    this.cubemapIdentifier = cubemapIdentifier;
  }

  /**
   * Set the dry foliage color used during rendering. Biomes without this component will have default dry foliage color behavior.
   *
   * @return Dry Foliage Color
   */
  public DryFoliageColor dryFoliageColor() {
    return this.dryFoliageColor;
  }

  /**
   * Set the dry foliage color used during rendering. Biomes without this component will have default dry foliage color behavior.
   *
   * @param dryFoliageColor Dry Foliage Color
   */
  public void dryFoliageColor(DryFoliageColor dryFoliageColor) {
    this.dryFoliageColor = dryFoliageColor;
  }

  /**
   * Set the fog settings used during rendering. Biomes without this component will have default fog settings.
   *
   * @return Fog Appearance
   */
  public FogAppearance fogAppearance() {
    return this.fogAppearance;
  }

  /**
   * Set the fog settings used during rendering. Biomes without this component will have default fog settings.
   *
   * @param fogAppearance Fog Appearance
   */
  public void fogAppearance(FogAppearance fogAppearance) {
    this.fogAppearance = fogAppearance;
  }

  /**
   * Set the foliage color or color map used during rendering. Biomes without this component will have default foliage appearance.
   *
   * @return Foliage Appearance
   */
  public FoliageAppearance foliageAppearance() {
    return this.foliageAppearance;
  }

  /**
   * Set the foliage color or color map used during rendering. Biomes without this component will have default foliage appearance.
   *
   * @param foliageAppearance Foliage Appearance
   */
  public void foliageAppearance(FoliageAppearance foliageAppearance) {
    this.foliageAppearance = foliageAppearance;
  }

  /**
   * Set the grass color or color map used during rendering. Biomes without this component will have default grass appearance.
   *
   * @return Grass Appearance
   */
  public GrassAppearance grassAppearance() {
    return this.grassAppearance;
  }

  /**
   * Set the grass color or color map used during rendering. Biomes without this component will have default grass appearance.
   *
   * @param grassAppearance Grass Appearance
   */
  public void grassAppearance(GrassAppearance grassAppearance) {
    this.grassAppearance = grassAppearance;
  }

  /**
   * Set the lighting settings used during deferred rendering.
   * Biomes without this component will have default lighting settings.
   *
   * @return Lighting Identifier
   */
  public LightingIdentifier lightingIdentifier() {
    return this.lightingIdentifier;
  }

  /**
   * Set the lighting settings used during deferred rendering.
   * Biomes without this component will have default lighting settings.
   *
   * @param lightingIdentifier Lighting Identifier
   */
  public void lightingIdentifier(LightingIdentifier lightingIdentifier) {
    this.lightingIdentifier = lightingIdentifier;
  }

  /**
   * Set the sky color used during rendering. Biomes without this component will have default sky color behavior.
   *
   * @return Sky Color
   */
  public SkyColor skyColor() {
    return this.skyColor;
  }

  /**
   * Set the sky color used during rendering. Biomes without this component will have default sky color behavior.
   *
   * @param skyColor Sky Color
   */
  public void skyColor(SkyColor skyColor) {
    this.skyColor = skyColor;
  }

  /**
   * Describes the visuals for a biome's precipitation. Biomes without this component will have default values. At most one precipitation type can be set for a biome.
   *
   * @return Precipitation
   */
  public Precipitation precipitation() {
    return this.precipitation;
  }

  /**
   * Describes the visuals for a biome's precipitation. Biomes without this component will have default values. At most one precipitation type can be set for a biome.
   *
   * @param precipitation Precipitation
   */
  public void precipitation(Precipitation precipitation) {
    this.precipitation = precipitation;
  }

  /**
   * Set the water surface color used during rendering. Biomes without this component will have default water surface color behavior.
   *
   * @return Water Appearance
   */
  public WaterAppearance waterAppearance() {
    return this.waterAppearance;
  }

  /**
   * Set the water surface color used during rendering. Biomes without this component will have default water surface color behavior.
   *
   * @param waterAppearance Water Appearance
   */
  public void waterAppearance(WaterAppearance waterAppearance) {
    this.waterAppearance = waterAppearance;
  }

  /**
   * Set the water settings used during deferred rendering.
   * Biomes without this component will have default water settings.
   *
   * @return Water Identifier
   */
  public WaterIdentifier waterIdentifier() {
    return this.waterIdentifier;
  }

  /**
   * Set the water settings used during deferred rendering.
   * Biomes without this component will have default water settings.
   *
   * @param waterIdentifier Water Identifier
   */
  public void waterIdentifier(WaterIdentifier waterIdentifier) {
    this.waterIdentifier = waterIdentifier;
  }
}
