package org.geysermc.pack.bedrock.resource.sounds;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.interactivesounds.BlockSounds;
import org.geysermc.pack.bedrock.resource.sounds.interactivesounds.EntitySounds;

/**
 * Interactive Sounds
 * <p>
 * Interactive sounds definitions.
 */
public class InteractiveSounds {
  @JsonProperty("block_sounds")
  private Map<String, BlockSounds> blockSounds = new HashMap<>();

  @JsonProperty("entity_sounds")
  public EntitySounds entitySounds;

  /**
   * Block sound definitions.
   *
   * @return Block Sounds
   */
  public Map<String, BlockSounds> blockSounds() {
    return this.blockSounds;
  }

  /**
   * Block sound definitions.
   *
   * @param blockSounds Block Sounds
   */
  public void blockSounds(Map<String, BlockSounds> blockSounds) {
    this.blockSounds = blockSounds;
  }

  /**
   * Entity sound definitions.
   *
   * @return Entity Sounds
   */
  public EntitySounds entitySounds() {
    return this.entitySounds;
  }

  /**
   * Entity sound definitions.
   *
   * @param entitySounds Entity Sounds
   */
  public void entitySounds(EntitySounds entitySounds) {
    this.entitySounds = entitySounds;
  }
}
