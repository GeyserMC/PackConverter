package org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults.variants;

import java.lang.String;
import java.util.HashMap;
import org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults.variants.map.Events;

/**
 * Map
 * <p>
 * Contains key-value pairs of a variant identifier and the sound events it overrides
 */
public class Map {
  private java.util.Map<String, Events> events = new HashMap<>();

  /**
   * @return Events
   */
  public java.util.Map<String, Events> events() {
    return this.events;
  }

  /**
   * @param events Events
   */
  public void events(java.util.Map<String, Events> events) {
    this.events = events;
  }
}
