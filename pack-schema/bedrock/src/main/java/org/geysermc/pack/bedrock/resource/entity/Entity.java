package org.geysermc.pack.bedrock.resource.entity;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Actor Entity 1.10.0
 * <p>
 * A client side entity definition.
 */
public class Entity {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:client_entity")
  public ClientEntity clientEntity;

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * The entity description for clientside rendering, animations and models.
   *
   * @return Client Entity
   */
  public ClientEntity clientEntity() {
    return this.clientEntity;
  }

  /**
   * The entity description for clientside rendering, animations and models.
   *
   * @param clientEntity Client Entity
   */
  public void clientEntity(ClientEntity clientEntity) {
    this.clientEntity = clientEntity;
  }
}
