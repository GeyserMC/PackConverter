package org.geysermc.pack.bedrock.resource.sounds;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Music File
 * <p>
 * The definition file of music of the resourcepack.
 */
public class MusicDefinitions {
  @JsonProperty("event_name")
  public String eventName;

  @JsonProperty("min_delay")
  public int minDelay;

  @JsonProperty("max_delay")
  public int maxDelay;

  /**
   * The name of the minecraft music event.
   *
   * @return Event Name
   */
  public String eventName() {
    return this.eventName;
  }

  /**
   * The name of the minecraft music event.
   *
   * @param eventName Event Name
   */
  public void eventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * @return Minimum Delay
   */
  public int minDelay() {
    return this.minDelay;
  }

  /**
   * @param minDelay Minimum Delay
   */
  public void minDelay(int minDelay) {
    this.minDelay = minDelay;
  }

  /**
   * @return Maximum Delay
   */
  public int maxDelay() {
    return this.maxDelay;
  }

  /**
   * @param maxDelay Maximum Delay
   */
  public void maxDelay(int maxDelay) {
    this.maxDelay = maxDelay;
  }
}
