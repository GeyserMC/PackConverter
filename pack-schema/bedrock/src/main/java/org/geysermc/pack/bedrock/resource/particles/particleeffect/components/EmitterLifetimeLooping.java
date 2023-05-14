package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Lifetime Looping Component For 1.10.0
 */
public class EmitterLifetimeLooping {
  @SerializedName("active_time")
  public String activeTime;

  @SerializedName("sleep_time")
  public String sleepTime;

  public String activeTime() {
    return this.activeTime;
  }

  public void activeTime(String activeTime) {
    this.activeTime = activeTime;
  }

  public String sleepTime() {
    return this.sleepTime;
  }

  public void sleepTime(String sleepTime) {
    this.sleepTime = sleepTime;
  }
}
