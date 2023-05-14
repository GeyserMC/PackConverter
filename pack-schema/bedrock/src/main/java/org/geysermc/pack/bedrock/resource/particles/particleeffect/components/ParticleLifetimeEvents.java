package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Lifetime Events Component For 1.10.0
 */
public class ParticleLifetimeEvents {
  @SerializedName("creation_event")
  public String[] creationEvent;

  @SerializedName("expiration_event")
  public String[] expirationEvent;

  public String[] creationEvent() {
    return this.creationEvent;
  }

  public void creationEvent(String[] creationEvent) {
    this.creationEvent = creationEvent;
  }

  public String[] expirationEvent() {
    return this.expirationEvent;
  }

  public void expirationEvent(String[] expirationEvent) {
    this.expirationEvent = expirationEvent;
  }
}
