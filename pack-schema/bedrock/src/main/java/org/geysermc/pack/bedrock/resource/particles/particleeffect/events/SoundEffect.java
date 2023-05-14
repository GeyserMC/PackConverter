package org.geysermc.pack.bedrock.resource.particles.particleeffect.events;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Sound Effect
 */
public class SoundEffect {
  @SerializedName("event_name")
  public String eventName;

  /**
   * @return Event Name
   */
  public String eventName() {
    return this.eventName;
  }

  /**
   * @param eventName Event Name
   */
  public void eventName(String eventName) {
    this.eventName = eventName;
  }
}
