package org.geysermc.pack.bedrock.resource.sounds.interactivesounds;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.interactivesounds.entitysounds.Defaults;
import org.geysermc.pack.bedrock.resource.sounds.interactivesounds.entitysounds.Entities;

/**
 * Entity Sounds
 * <p>
 * Entity sound definitions.
 */
public class EntitySounds {
  private Map<String, Defaults> defaults = new HashMap<>();

  private Map<String, Entities> entities = new HashMap<>();

  /**
   * Default sound definitions.
   *
   * @return Defaults
   */
  public Map<String, Defaults> defaults() {
    return this.defaults;
  }

  /**
   * Default sound definitions.
   *
   * @param defaults Defaults
   */
  public void defaults(Map<String, Defaults> defaults) {
    this.defaults = defaults;
  }

  /**
   * Entities sound definitions.
   *
   * @return Entites Sounds
   */
  public Map<String, Entities> entities() {
    return this.entities;
  }

  /**
   * Entities sound definitions.
   *
   * @param entities Entites Sounds
   */
  public void entities(Map<String, Entities> entities) {
    this.entities = entities;
  }
}
