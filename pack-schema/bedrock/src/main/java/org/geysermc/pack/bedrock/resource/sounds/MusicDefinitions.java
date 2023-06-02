package org.geysermc.pack.bedrock.resource.sounds;

import com.google.gson.annotations.SerializedName;
import java.lang.Integer;
import java.lang.String;

/**
 * Music File
 * <p>
 * The definition file of music of the resourcepack.
 */
public class MusicDefinitions {
  @SerializedName("event_name")
  public String eventName;

  @SerializedName("min_delay")
  public Integer minDelay;

  @SerializedName("max_delay")
  public Integer maxDelay;

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
  public Integer minDelay() {
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
  public Integer maxDelay() {
    return this.maxDelay;
  }

  /**
   * @param maxDelay Maximum Delay
   */
  public void maxDelay(int maxDelay) {
    this.maxDelay = maxDelay;
  }
}
