package org.geysermc.pack.bedrock.resource.fog.fogsettings;

import com.google.gson.annotations.SerializedName;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.Air;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.Lava;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.LavaResistance;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.PowderSnow;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.Water;
import org.geysermc.pack.bedrock.resource.fog.fogsettings.distance.Weather;

/**
 * Distance
 * <p>
 * The distance fog settings for different camera locations.
 */
public class Distance {
  public Air air;

  public Weather weather;

  public Water water;

  public Lava lava;

  @SerializedName("lava_resistance")
  public LavaResistance lavaResistance;

  @SerializedName("powder_snow")
  public PowderSnow powderSnow;

  public Air air() {
    return this.air;
  }

  public void air(Air air) {
    this.air = air;
  }

  public Weather weather() {
    return this.weather;
  }

  public void weather(Weather weather) {
    this.weather = weather;
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

  public PowderSnow powderSnow() {
    return this.powderSnow;
  }

  public void powderSnow(PowderSnow powderSnow) {
    this.powderSnow = powderSnow;
  }
}
