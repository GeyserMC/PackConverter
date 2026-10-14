package org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Variants
 * <p>
 * Defines sound variants based on the runtime entity
 */
public class Variants {
  public String key;

  private Map<String, org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults.variants.Map> map = new HashMap<>();

  /**
   * Key returning the enum string for variants, can use any Molang expression resulting in a string.
   *
   * @return Key
   */
  public String key() {
    return this.key;
  }

  /**
   * Key returning the enum string for variants, can use any Molang expression resulting in a string.
   *
   * @param key Key
   */
  public void key(String key) {
    this.key = key;
  }

  /**
   * Contains key-value pairs of a variant identifier and the sound events it overrides
   *
   * @return Map
   */
  public Map<String, org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults.variants.Map> map(
      ) {
    return this.map;
  }

  /**
   * Contains key-value pairs of a variant identifier and the sound events it overrides
   *
   * @param map Map
   */
  public void map(
      Map<String, org.geysermc.pack.bedrock.resource.sounds.entitysounds.defaults.variants.Map> map) {
    this.map = map;
  }
}
