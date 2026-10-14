package org.geysermc.pack.bedrock.resource.particles.particleeffect.events;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Sound Effect
 * <p>
 * Sound effect action.
 */
public class SoundEffect {
  @SerializedName("event_name")
  public String eventName;

  /**
   * Name of the level sound event.
   *
   * @return Event Name
   */
  public String eventName() {
    return this.eventName;
  }

  /**
   * Name of the level sound event.
   *
   * @param eventName Event Name
   */
  public void eventName(String eventName) {
    this.eventName = eventName;
  }
}
