package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Lifetime Once Component For 1.10.0
 */
public class EmitterLifetimeOnce {
  @SerializedName("active_time")
  public String activeTime;

  public String activeTime() {
    return this.activeTime;
  }

  public void activeTime(String activeTime) {
    this.activeTime = activeTime;
  }
}
