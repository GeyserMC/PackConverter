package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Lifetime Expression Component For 1.10.0
 */
public class ParticleLifetimeExpression {
  @SerializedName("expiration_expression")
  public String expirationExpression;

  @SerializedName("max_lifetime")
  public String maxLifetime;

  public String expirationExpression() {
    return this.expirationExpression;
  }

  public void expirationExpression(String expirationExpression) {
    this.expirationExpression = expirationExpression;
  }

  public String maxLifetime() {
    return this.maxLifetime;
  }

  public void maxLifetime(String maxLifetime) {
    this.maxLifetime = maxLifetime;
  }
}
