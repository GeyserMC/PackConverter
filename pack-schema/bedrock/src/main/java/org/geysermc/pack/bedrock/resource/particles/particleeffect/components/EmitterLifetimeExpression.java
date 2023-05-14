package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Emitter Rate Manual Component 1.10.0
 */
public class EmitterLifetimeExpression {
  @SerializedName("activation_expression")
  public String activationExpression;

  @SerializedName("expiration_expression")
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
