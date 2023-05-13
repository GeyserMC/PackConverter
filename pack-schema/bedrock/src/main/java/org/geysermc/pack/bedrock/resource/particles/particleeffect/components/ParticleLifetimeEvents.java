package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

/**
 * Particle Lifetime Events Component For 1.10.0
 */
public class ParticleLifetimeEvents {
  @JsonProperty("creation_event")
  public String creationEvent;

  @JsonProperty("expiration_event")
  public String expirationEvent;

  public String creationEvent() {
    return this.creationEvent;
  }

  public void creationEvent(String creationEvent) {
    this.creationEvent = creationEvent;
  }

  public String expirationEvent() {
    return this.expirationEvent;
  }

  public void expirationEvent(String expirationEvent) {
    this.expirationEvent = expirationEvent;
  }
}
