package org.geysermc.pack.bedrock.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.sounds.BlockSounds;
import org.geysermc.pack.bedrock.resource.sounds.EntitySounds;
import org.geysermc.pack.bedrock.resource.sounds.IndividualEventSounds;
import org.geysermc.pack.bedrock.resource.sounds.InteractiveSounds;

/**
 * Sounds.json
 * <p>
 * Sound definitions.
 */
public class Sounds {
  @SerializedName("block_sounds")
  private Map<String, BlockSounds> blockSounds = new HashMap<>();

  @SerializedName("entity_sounds")
  public EntitySounds entitySounds;

  @SerializedName("individual_event_sounds")
  public IndividualEventSounds individualEventSounds;

  @SerializedName("interactive_sounds")
  public InteractiveSounds interactiveSounds;

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
   * Entity sounds definitions.
   *
   * @return Entity Sounds
   */
  public EntitySounds entitySounds() {
    return this.entitySounds;
  }

  /**
   * Entity sounds definitions.
   *
   * @param entitySounds Entity Sounds
   */
  public void entitySounds(EntitySounds entitySounds) {
    this.entitySounds = entitySounds;
  }

  /**
   * Individual event sounds definitions.
   *
   * @return Individual Event Sounds
   */
  public IndividualEventSounds individualEventSounds() {
    return this.individualEventSounds;
  }

  /**
   * Individual event sounds definitions.
   *
   * @param individualEventSounds Individual Event Sounds
   */
  public void individualEventSounds(IndividualEventSounds individualEventSounds) {
    this.individualEventSounds = individualEventSounds;
  }

  /**
   * Interactive sounds definitions.
   *
   * @return Interactive Sounds
   */
  public InteractiveSounds interactiveSounds() {
    return this.interactiveSounds;
  }

  /**
   * Interactive sounds definitions.
   *
   * @param interactiveSounds Interactive Sounds
   */
  public void interactiveSounds(InteractiveSounds interactiveSounds) {
    this.interactiveSounds = interactiveSounds;
  }
}
