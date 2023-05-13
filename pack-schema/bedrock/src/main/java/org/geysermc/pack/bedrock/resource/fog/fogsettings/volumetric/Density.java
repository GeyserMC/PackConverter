package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density.Air;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density.Lava;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density.LavaResistance;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.density.Water;

/**
 * Density
 * <p>
 * The density settings for different camera locations.
 */
public class Density {
  public Air air;

  public Water water;

  public Lava lava;

  @JsonProperty("lava_resistance")
  public LavaResistance lavaResistance;

  public Air air() {
    return this.air;
  }

  public void air(Air air) {
    this.air = air;
  }

  public Water water() {
    return this.water;
  }

  public void water(Water water) {
    this.water = water;
  }

  public Lava lava() {
    return this.lava;
  }

  public void lava(Lava lava) {
    this.lava = lava;
  }

  public LavaResistance lavaResistance() {
    return this.lavaResistance;
  }

  public void lavaResistance(LavaResistance lavaResistance) {
    this.lavaResistance = lavaResistance;
  }
}
