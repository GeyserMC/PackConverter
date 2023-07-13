package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Initialization Component For 1.10.0
 * <p>
 * This component allows the emitter to run some Molang at creation, primarily to populate any Molang variables that get used later.
 */
public class EmitterInitialization {
  @SerializedName("creation_expression")
  public String creationExpression;

  @SerializedName("per_update_expression")
  public String perUpdateExpression;

  /**
   * Molang definition.
   *
   * @return Molang
   */
  public String creationExpression() {
    return this.creationExpression;
  }

  /**
   * Molang definition.
   *
   * @param creationExpression Molang
   */
  public void creationExpression(String creationExpression) {
    this.creationExpression = creationExpression;
  }

  /**
   * Molang definition.
   *
   * @return Molang
   */
  public String perUpdateExpression() {
    return this.perUpdateExpression;
  }

  /**
   * Molang definition.
   *
   * @param perUpdateExpression Molang
   */
  public void perUpdateExpression(String perUpdateExpression) {
    this.perUpdateExpression = perUpdateExpression;
  }
}
