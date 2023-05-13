package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Particle Initialization Component For 1.10.0
 */
public class ParticleInitialization {
  @JsonProperty("per_update_expression")
  public String perUpdateExpression;

  @JsonProperty("per_render_expression")
  public String perRenderExpression;

  public String perUpdateExpression() {
    return this.perUpdateExpression;
  }

  public void perUpdateExpression(String perUpdateExpression) {
    this.perUpdateExpression = perUpdateExpression;
  }

  public String perRenderExpression() {
    return this.perRenderExpression;
  }

  public void perRenderExpression(String perRenderExpression) {
    this.perRenderExpression = perRenderExpression;
  }
}
