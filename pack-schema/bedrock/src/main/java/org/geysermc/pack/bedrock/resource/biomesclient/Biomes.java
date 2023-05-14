package org.geysermc.pack.bedrock.resource.biomesclient;

import com.google.gson.annotations.SerializedName;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.BambooJungle;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.BambooJungleHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.BasaltDeltas;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Beach;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.BirchForest;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.BirchForestHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ColdBeach;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ColdOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ColdTaiga;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ColdTaigaHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ColdTaigaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.CrimsonForest;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DeepColdOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DeepFrozenOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DeepLukewarmOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DeepOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DeepWarmOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DefaultValue;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Desert;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.DesertHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ExtremeHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ExtremeHillsEdge;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ExtremeHillsMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ExtremeHillsPlusTrees;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ExtremeHillsPlusTreesMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.FlowerForest;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Forest;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.ForestHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.FrozenOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.FrozenRiver;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Hell;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.IceMountains;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.IcePlains;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.IcePlainsSpikes;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Jungle;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.JungleEdge;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.JungleHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.JungleMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.LukewarmOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MangroveSwamp;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MegaSpruceTaiga;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MegaSpruceTaigaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MegaTaiga;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MegaTaigaHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MegaTaigaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Mesa;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MesaBryce;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MesaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MesaPlateau;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MesaPlateauStone;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MushroomIsland;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.MushroomIslandShore;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Ocean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Plains;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.River;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.RoofedForest;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Savanna;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.SavannaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.SavannaPlateau;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.SoulsandValley;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.StoneBeach;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.SunflowerPlains;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Swampland;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.SwamplandMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.Taiga;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.TaigaHills;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.TaigaMutated;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.TheEnd;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.WarmOcean;
import org.geysermc.pack.bedrock.resource.biomesclient.biomes.WarpedForest;

/**
 * Biomes
 * <p>
 * A collection of predefined biomes.
 */
public class Biomes {
  @SerializedName("bamboo_jungle_hills")
  public BambooJungleHills bambooJungleHills;

  @SerializedName("bamboo_jungle")
  public BambooJungle bambooJungle;

  @SerializedName("basalt_deltas")
  public BasaltDeltas basaltDeltas;

  public Beach beach;

  @SerializedName("birch_forest_hills")
  public BirchForestHills birchForestHills;

  @SerializedName("birch_forest")
  public BirchForest birchForest;

  @SerializedName("cold_beach")
  public ColdBeach coldBeach;

  @SerializedName("cold_ocean")
  public ColdOcean coldOcean;

  @SerializedName("cold_taiga_hills")
  public ColdTaigaHills coldTaigaHills;

  @SerializedName("cold_taiga_mutated")
  public ColdTaigaMutated coldTaigaMutated;

  @SerializedName("cold_taiga")
  public ColdTaiga coldTaiga;

  @SerializedName("crimson_forest")
  public CrimsonForest crimsonForest;

  @SerializedName("deep_cold_ocean")
  public DeepColdOcean deepColdOcean;

  @SerializedName("deep_frozen_ocean")
  public DeepFrozenOcean deepFrozenOcean;

  @SerializedName("deep_lukewarm_ocean")
  public DeepLukewarmOcean deepLukewarmOcean;

  @SerializedName("deep_ocean")
  public DeepOcean deepOcean;

  @SerializedName("deep_warm_ocean")
  public DeepWarmOcean deepWarmOcean;

  @SerializedName("default")
  public DefaultValue defaultValue;

  @SerializedName("desert_hills")
  public DesertHills desertHills;

  public Desert desert;

  @SerializedName("extreme_hills_edge")
  public ExtremeHillsEdge extremeHillsEdge;

  @SerializedName("extreme_hills_mutated")
  public ExtremeHillsMutated extremeHillsMutated;

  @SerializedName("extreme_hills_plus_trees_mutated")
  public ExtremeHillsPlusTreesMutated extremeHillsPlusTreesMutated;

  @SerializedName("extreme_hills_plus_trees")
  public ExtremeHillsPlusTrees extremeHillsPlusTrees;

  @SerializedName("extreme_hills")
  public ExtremeHills extremeHills;

  @SerializedName("flower_forest")
  public FlowerForest flowerForest;

  @SerializedName("forest_hills")
  public ForestHills forestHills;

  public Forest forest;

  @SerializedName("frozen_ocean")
  public FrozenOcean frozenOcean;

  @SerializedName("frozen_river")
  public FrozenRiver frozenRiver;

  public Hell hell;

  @SerializedName("ice_mountains")
  public IceMountains iceMountains;

  @SerializedName("ice_plains_spikes")
  public IcePlainsSpikes icePlainsSpikes;

  @SerializedName("ice_plains")
  public IcePlains icePlains;

  @SerializedName("jungle_edge")
  public JungleEdge jungleEdge;

  @SerializedName("jungle_hills")
  public JungleHills jungleHills;

  @SerializedName("jungle_mutated")
  public JungleMutated jungleMutated;

  public Jungle jungle;

  @SerializedName("lukewarm_ocean")
  public LukewarmOcean lukewarmOcean;

  @SerializedName("mangrove_swamp")
  public MangroveSwamp mangroveSwamp;

  @SerializedName("mega_spruce_taiga_mutated")
  public MegaSpruceTaigaMutated megaSpruceTaigaMutated;

  @SerializedName("mega_spruce_taiga")
  public MegaSpruceTaiga megaSpruceTaiga;

  @SerializedName("mega_taiga_hills")
  public MegaTaigaHills megaTaigaHills;

  @SerializedName("mega_taiga_mutated")
  public MegaTaigaMutated megaTaigaMutated;

  @SerializedName("mega_taiga")
  public MegaTaiga megaTaiga;

  @SerializedName("mesa_bryce")
  public MesaBryce mesaBryce;

  @SerializedName("mesa_mutated")
  public MesaMutated mesaMutated;

  @SerializedName("mesa_plateau_stone")
  public MesaPlateauStone mesaPlateauStone;

  @SerializedName("mesa_plateau")
  public MesaPlateau mesaPlateau;

  public Mesa mesa;

  @SerializedName("mushroom_island_shore")
  public MushroomIslandShore mushroomIslandShore;

  @SerializedName("mushroom_island")
  public MushroomIsland mushroomIsland;

  public Ocean ocean;

  public Plains plains;

  public River river;

  @SerializedName("roofed_forest")
  public RoofedForest roofedForest;

  @SerializedName("savanna_mutated")
  public SavannaMutated savannaMutated;

  @SerializedName("savanna_plateau")
  public SavannaPlateau savannaPlateau;

  public Savanna savanna;

  @SerializedName("soulsand_valley")
  public SoulsandValley soulsandValley;

  @SerializedName("stone_beach")
  public StoneBeach stoneBeach;

  @SerializedName("sunflower_plains")
  public SunflowerPlains sunflowerPlains;

  @SerializedName("swampland_mutated")
  public SwamplandMutated swamplandMutated;

  public Swampland swampland;

  @SerializedName("taiga_hills")
  public TaigaHills taigaHills;

  @SerializedName("taiga_mutated")
  public TaigaMutated taigaMutated;

  public Taiga taiga;

  @SerializedName("the_end")
  public TheEnd theEnd;

  @SerializedName("warm_ocean")
  public WarmOcean warmOcean;

  @SerializedName("warped_forest")
  public WarpedForest warpedForest;

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public BambooJungleHills bambooJungleHills() {
    return this.bambooJungleHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param bambooJungleHills Biome
   */
  public void bambooJungleHills(BambooJungleHills bambooJungleHills) {
    this.bambooJungleHills = bambooJungleHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public BambooJungle bambooJungle() {
    return this.bambooJungle;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param bambooJungle Biome
   */
  public void bambooJungle(BambooJungle bambooJungle) {
    this.bambooJungle = bambooJungle;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public BasaltDeltas basaltDeltas() {
    return this.basaltDeltas;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param basaltDeltas Biome
   */
  public void basaltDeltas(BasaltDeltas basaltDeltas) {
    this.basaltDeltas = basaltDeltas;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Beach beach() {
    return this.beach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param beach Biome
   */
  public void beach(Beach beach) {
    this.beach = beach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public BirchForestHills birchForestHills() {
    return this.birchForestHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param birchForestHills Biome
   */
  public void birchForestHills(BirchForestHills birchForestHills) {
    this.birchForestHills = birchForestHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public BirchForest birchForest() {
    return this.birchForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param birchForest Biome
   */
  public void birchForest(BirchForest birchForest) {
    this.birchForest = birchForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ColdBeach coldBeach() {
    return this.coldBeach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param coldBeach Biome
   */
  public void coldBeach(ColdBeach coldBeach) {
    this.coldBeach = coldBeach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ColdOcean coldOcean() {
    return this.coldOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param coldOcean Biome
   */
  public void coldOcean(ColdOcean coldOcean) {
    this.coldOcean = coldOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ColdTaigaHills coldTaigaHills() {
    return this.coldTaigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param coldTaigaHills Biome
   */
  public void coldTaigaHills(ColdTaigaHills coldTaigaHills) {
    this.coldTaigaHills = coldTaigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ColdTaigaMutated coldTaigaMutated() {
    return this.coldTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param coldTaigaMutated Biome
   */
  public void coldTaigaMutated(ColdTaigaMutated coldTaigaMutated) {
    this.coldTaigaMutated = coldTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ColdTaiga coldTaiga() {
    return this.coldTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param coldTaiga Biome
   */
  public void coldTaiga(ColdTaiga coldTaiga) {
    this.coldTaiga = coldTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public CrimsonForest crimsonForest() {
    return this.crimsonForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param crimsonForest Biome
   */
  public void crimsonForest(CrimsonForest crimsonForest) {
    this.crimsonForest = crimsonForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DeepColdOcean deepColdOcean() {
    return this.deepColdOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param deepColdOcean Biome
   */
  public void deepColdOcean(DeepColdOcean deepColdOcean) {
    this.deepColdOcean = deepColdOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DeepFrozenOcean deepFrozenOcean() {
    return this.deepFrozenOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param deepFrozenOcean Biome
   */
  public void deepFrozenOcean(DeepFrozenOcean deepFrozenOcean) {
    this.deepFrozenOcean = deepFrozenOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DeepLukewarmOcean deepLukewarmOcean() {
    return this.deepLukewarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param deepLukewarmOcean Biome
   */
  public void deepLukewarmOcean(DeepLukewarmOcean deepLukewarmOcean) {
    this.deepLukewarmOcean = deepLukewarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DeepOcean deepOcean() {
    return this.deepOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param deepOcean Biome
   */
  public void deepOcean(DeepOcean deepOcean) {
    this.deepOcean = deepOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DeepWarmOcean deepWarmOcean() {
    return this.deepWarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param deepWarmOcean Biome
   */
  public void deepWarmOcean(DeepWarmOcean deepWarmOcean) {
    this.deepWarmOcean = deepWarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DefaultValue defaultValue() {
    return this.defaultValue;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param defaultValue Biome
   */
  public void defaultValue(DefaultValue defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public DesertHills desertHills() {
    return this.desertHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param desertHills Biome
   */
  public void desertHills(DesertHills desertHills) {
    this.desertHills = desertHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Desert desert() {
    return this.desert;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param desert Biome
   */
  public void desert(Desert desert) {
    this.desert = desert;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ExtremeHillsEdge extremeHillsEdge() {
    return this.extremeHillsEdge;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param extremeHillsEdge Biome
   */
  public void extremeHillsEdge(ExtremeHillsEdge extremeHillsEdge) {
    this.extremeHillsEdge = extremeHillsEdge;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ExtremeHillsMutated extremeHillsMutated() {
    return this.extremeHillsMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param extremeHillsMutated Biome
   */
  public void extremeHillsMutated(ExtremeHillsMutated extremeHillsMutated) {
    this.extremeHillsMutated = extremeHillsMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ExtremeHillsPlusTreesMutated extremeHillsPlusTreesMutated() {
    return this.extremeHillsPlusTreesMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param extremeHillsPlusTreesMutated Biome
   */
  public void extremeHillsPlusTreesMutated(
      ExtremeHillsPlusTreesMutated extremeHillsPlusTreesMutated) {
    this.extremeHillsPlusTreesMutated = extremeHillsPlusTreesMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ExtremeHillsPlusTrees extremeHillsPlusTrees() {
    return this.extremeHillsPlusTrees;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param extremeHillsPlusTrees Biome
   */
  public void extremeHillsPlusTrees(ExtremeHillsPlusTrees extremeHillsPlusTrees) {
    this.extremeHillsPlusTrees = extremeHillsPlusTrees;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ExtremeHills extremeHills() {
    return this.extremeHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param extremeHills Biome
   */
  public void extremeHills(ExtremeHills extremeHills) {
    this.extremeHills = extremeHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public FlowerForest flowerForest() {
    return this.flowerForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param flowerForest Biome
   */
  public void flowerForest(FlowerForest flowerForest) {
    this.flowerForest = flowerForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public ForestHills forestHills() {
    return this.forestHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param forestHills Biome
   */
  public void forestHills(ForestHills forestHills) {
    this.forestHills = forestHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Forest forest() {
    return this.forest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param forest Biome
   */
  public void forest(Forest forest) {
    this.forest = forest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public FrozenOcean frozenOcean() {
    return this.frozenOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param frozenOcean Biome
   */
  public void frozenOcean(FrozenOcean frozenOcean) {
    this.frozenOcean = frozenOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public FrozenRiver frozenRiver() {
    return this.frozenRiver;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param frozenRiver Biome
   */
  public void frozenRiver(FrozenRiver frozenRiver) {
    this.frozenRiver = frozenRiver;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Hell hell() {
    return this.hell;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param hell Biome
   */
  public void hell(Hell hell) {
    this.hell = hell;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public IceMountains iceMountains() {
    return this.iceMountains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param iceMountains Biome
   */
  public void iceMountains(IceMountains iceMountains) {
    this.iceMountains = iceMountains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public IcePlainsSpikes icePlainsSpikes() {
    return this.icePlainsSpikes;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param icePlainsSpikes Biome
   */
  public void icePlainsSpikes(IcePlainsSpikes icePlainsSpikes) {
    this.icePlainsSpikes = icePlainsSpikes;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public IcePlains icePlains() {
    return this.icePlains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param icePlains Biome
   */
  public void icePlains(IcePlains icePlains) {
    this.icePlains = icePlains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public JungleEdge jungleEdge() {
    return this.jungleEdge;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param jungleEdge Biome
   */
  public void jungleEdge(JungleEdge jungleEdge) {
    this.jungleEdge = jungleEdge;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public JungleHills jungleHills() {
    return this.jungleHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param jungleHills Biome
   */
  public void jungleHills(JungleHills jungleHills) {
    this.jungleHills = jungleHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public JungleMutated jungleMutated() {
    return this.jungleMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param jungleMutated Biome
   */
  public void jungleMutated(JungleMutated jungleMutated) {
    this.jungleMutated = jungleMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Jungle jungle() {
    return this.jungle;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param jungle Biome
   */
  public void jungle(Jungle jungle) {
    this.jungle = jungle;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public LukewarmOcean lukewarmOcean() {
    return this.lukewarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param lukewarmOcean Biome
   */
  public void lukewarmOcean(LukewarmOcean lukewarmOcean) {
    this.lukewarmOcean = lukewarmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MangroveSwamp mangroveSwamp() {
    return this.mangroveSwamp;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mangroveSwamp Biome
   */
  public void mangroveSwamp(MangroveSwamp mangroveSwamp) {
    this.mangroveSwamp = mangroveSwamp;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MegaSpruceTaigaMutated megaSpruceTaigaMutated() {
    return this.megaSpruceTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param megaSpruceTaigaMutated Biome
   */
  public void megaSpruceTaigaMutated(MegaSpruceTaigaMutated megaSpruceTaigaMutated) {
    this.megaSpruceTaigaMutated = megaSpruceTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MegaSpruceTaiga megaSpruceTaiga() {
    return this.megaSpruceTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param megaSpruceTaiga Biome
   */
  public void megaSpruceTaiga(MegaSpruceTaiga megaSpruceTaiga) {
    this.megaSpruceTaiga = megaSpruceTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MegaTaigaHills megaTaigaHills() {
    return this.megaTaigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param megaTaigaHills Biome
   */
  public void megaTaigaHills(MegaTaigaHills megaTaigaHills) {
    this.megaTaigaHills = megaTaigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MegaTaigaMutated megaTaigaMutated() {
    return this.megaTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param megaTaigaMutated Biome
   */
  public void megaTaigaMutated(MegaTaigaMutated megaTaigaMutated) {
    this.megaTaigaMutated = megaTaigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MegaTaiga megaTaiga() {
    return this.megaTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param megaTaiga Biome
   */
  public void megaTaiga(MegaTaiga megaTaiga) {
    this.megaTaiga = megaTaiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MesaBryce mesaBryce() {
    return this.mesaBryce;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mesaBryce Biome
   */
  public void mesaBryce(MesaBryce mesaBryce) {
    this.mesaBryce = mesaBryce;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MesaMutated mesaMutated() {
    return this.mesaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mesaMutated Biome
   */
  public void mesaMutated(MesaMutated mesaMutated) {
    this.mesaMutated = mesaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MesaPlateauStone mesaPlateauStone() {
    return this.mesaPlateauStone;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mesaPlateauStone Biome
   */
  public void mesaPlateauStone(MesaPlateauStone mesaPlateauStone) {
    this.mesaPlateauStone = mesaPlateauStone;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MesaPlateau mesaPlateau() {
    return this.mesaPlateau;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mesaPlateau Biome
   */
  public void mesaPlateau(MesaPlateau mesaPlateau) {
    this.mesaPlateau = mesaPlateau;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Mesa mesa() {
    return this.mesa;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mesa Biome
   */
  public void mesa(Mesa mesa) {
    this.mesa = mesa;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MushroomIslandShore mushroomIslandShore() {
    return this.mushroomIslandShore;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mushroomIslandShore Biome
   */
  public void mushroomIslandShore(MushroomIslandShore mushroomIslandShore) {
    this.mushroomIslandShore = mushroomIslandShore;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public MushroomIsland mushroomIsland() {
    return this.mushroomIsland;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param mushroomIsland Biome
   */
  public void mushroomIsland(MushroomIsland mushroomIsland) {
    this.mushroomIsland = mushroomIsland;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Ocean ocean() {
    return this.ocean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param ocean Biome
   */
  public void ocean(Ocean ocean) {
    this.ocean = ocean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Plains plains() {
    return this.plains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param plains Biome
   */
  public void plains(Plains plains) {
    this.plains = plains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public River river() {
    return this.river;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param river Biome
   */
  public void river(River river) {
    this.river = river;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public RoofedForest roofedForest() {
    return this.roofedForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param roofedForest Biome
   */
  public void roofedForest(RoofedForest roofedForest) {
    this.roofedForest = roofedForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public SavannaMutated savannaMutated() {
    return this.savannaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param savannaMutated Biome
   */
  public void savannaMutated(SavannaMutated savannaMutated) {
    this.savannaMutated = savannaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public SavannaPlateau savannaPlateau() {
    return this.savannaPlateau;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param savannaPlateau Biome
   */
  public void savannaPlateau(SavannaPlateau savannaPlateau) {
    this.savannaPlateau = savannaPlateau;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Savanna savanna() {
    return this.savanna;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param savanna Biome
   */
  public void savanna(Savanna savanna) {
    this.savanna = savanna;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public SoulsandValley soulsandValley() {
    return this.soulsandValley;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param soulsandValley Biome
   */
  public void soulsandValley(SoulsandValley soulsandValley) {
    this.soulsandValley = soulsandValley;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public StoneBeach stoneBeach() {
    return this.stoneBeach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param stoneBeach Biome
   */
  public void stoneBeach(StoneBeach stoneBeach) {
    this.stoneBeach = stoneBeach;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public SunflowerPlains sunflowerPlains() {
    return this.sunflowerPlains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param sunflowerPlains Biome
   */
  public void sunflowerPlains(SunflowerPlains sunflowerPlains) {
    this.sunflowerPlains = sunflowerPlains;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public SwamplandMutated swamplandMutated() {
    return this.swamplandMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param swamplandMutated Biome
   */
  public void swamplandMutated(SwamplandMutated swamplandMutated) {
    this.swamplandMutated = swamplandMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Swampland swampland() {
    return this.swampland;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param swampland Biome
   */
  public void swampland(Swampland swampland) {
    this.swampland = swampland;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public TaigaHills taigaHills() {
    return this.taigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param taigaHills Biome
   */
  public void taigaHills(TaigaHills taigaHills) {
    this.taigaHills = taigaHills;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public TaigaMutated taigaMutated() {
    return this.taigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param taigaMutated Biome
   */
  public void taigaMutated(TaigaMutated taigaMutated) {
    this.taigaMutated = taigaMutated;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public Taiga taiga() {
    return this.taiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param taiga Biome
   */
  public void taiga(Taiga taiga) {
    this.taiga = taiga;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public TheEnd theEnd() {
    return this.theEnd;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param theEnd Biome
   */
  public void theEnd(TheEnd theEnd) {
    this.theEnd = theEnd;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public WarmOcean warmOcean() {
    return this.warmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param warmOcean Biome
   */
  public void warmOcean(WarmOcean warmOcean) {
    this.warmOcean = warmOcean;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @return Biome
   */
  public WarpedForest warpedForest() {
    return this.warpedForest;
  }

  /**
   * The specification of colors in a given biome.
   *
   * @param warpedForest Biome
   */
  public void warpedForest(WarpedForest warpedForest) {
    this.warpedForest = warpedForest;
  }
}
