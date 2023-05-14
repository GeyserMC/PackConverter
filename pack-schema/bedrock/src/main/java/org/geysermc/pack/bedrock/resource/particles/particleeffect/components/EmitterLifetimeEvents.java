package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.emitterlifetimeevents.LoopingTravelDistanceEvents;

/**
 * Emitter Lifetime Events Component For 1.10.0
 */
public class EmitterLifetimeEvents {
  @SerializedName("creation_event")
  public String[] creationEvent;

  @SerializedName("expiration_event")
  public String[] expirationEvent;

  private Map<String, String[]> timeline = new HashMap<>();

  @SerializedName("looping_travel_distance_events")
  public List<LoopingTravelDistanceEvents> loopingTravelDistanceEvents = new ArrayList<>();

  public String[] creationEvent() {
    return this.creationEvent;
  }

  public void creationEvent(String[] creationEvent) {
    this.creationEvent = creationEvent;
  }

  public String[] expirationEvent() {
    return this.expirationEvent;
  }

  public void expirationEvent(String[] expirationEvent) {
    this.expirationEvent = expirationEvent;
  }

  /**
   * A series of times, e.g. 0.0 or 1.0, that trigger the event, these get fired on every loop the emitter goes through, `time` is the time, e.g. one line might be: `0.4`: `event`
   *
   * @return Timeline
   */
  public Map<String, String[]> timeline() {
    return this.timeline;
  }

  /**
   * A series of times, e.g. 0.0 or 1.0, that trigger the event, these get fired on every loop the emitter goes through, `time` is the time, e.g. one line might be: `0.4`: `event`
   *
   * @param timeline Timeline
   */
  public void timeline(Map<String, String[]> timeline) {
    this.timeline = timeline;
  }

  /**
   * A series of events that occur at set intervals these get fired every time the emitter has moved the specified input distance from the last time it was fired.
   *
   * @return Looping Travel Distance Events
   */
  public List<LoopingTravelDistanceEvents> loopingTravelDistanceEvents() {
    return this.loopingTravelDistanceEvents;
  }

  /**
   * A series of events that occur at set intervals these get fired every time the emitter has moved the specified input distance from the last time it was fired.
   *
   * @param loopingTravelDistanceEvents Looping Travel Distance Events
   */
  public void loopingTravelDistanceEvents(
      List<LoopingTravelDistanceEvents> loopingTravelDistanceEvents) {
    this.loopingTravelDistanceEvents = loopingTravelDistanceEvents;
  }
}
