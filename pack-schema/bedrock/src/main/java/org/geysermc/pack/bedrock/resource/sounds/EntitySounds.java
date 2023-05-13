package org.geysermc.pack.bedrock.resource.sounds;

import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.entitysounds.Defaults;

/**
 * Entity Sounds
 * <p>
 * Entity sounds definitions.
 */
public class EntitySounds {
  public Defaults defaults;

  private Map<String, Object> entities = new HashMap<>();

  /**
   * Entity sound definitions.
   *
   * @return Entity Sound
   */
  public Defaults defaults() {
    return this.defaults;
  }

  /**
   * Entity sound definitions.
   *
   * @param defaults Entity Sound
   */
  public void defaults(Defaults defaults) {
    this.defaults = defaults;
  }

  /**
   * Entities definitions.
   *
   * @return Entities
   */
  public Map<String, Object> entities() {
    return this.entities;
  }

  /**
   * Entities definitions.
   *
   * @param entities Entities
   */
  public void entities(Map<String, Object> entities) {
    this.entities = entities;
  }
}
