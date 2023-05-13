package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Emitter Rate Manual Component 1.10.0
 */
public class EmitterLifetimeExpression {
  @JsonProperty("activation_expression")
  public String activationExpression;

  @JsonProperty("expiration_expression")
  public String expirationExpression;

  public String activationExpression() {
    return this.activationExpression;
  }

  public void activationExpression(String activationExpression) {
    this.activationExpression = activationExpression;
  }

  public String expirationExpression() {
    return this.expirationExpression;
  }

  public void expirationExpression(String expirationExpression) {
    this.expirationExpression = expirationExpression;
  }
}
