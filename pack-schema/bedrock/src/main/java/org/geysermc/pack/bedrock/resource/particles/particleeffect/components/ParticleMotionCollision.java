package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Particle Motion Collision Component For 1.10.0
 */
public class ParticleMotionCollision {
  @SerializedName("collision_drag")
  public float collisionDrag;

  @SerializedName("coefficient_of_restitution")
  public float coefficientOfRestitution;

  @SerializedName("collision_radius")
  public float collisionRadius;

  public String enabled;

  @SerializedName("expire_on_contact")
  public boolean expireOnContact;

  /**
   * @return Collision Drag
   */
  public float collisionDrag() {
    return this.collisionDrag;
  }

  /**
   * @param collisionDrag Collision Drag
   */
  public void collisionDrag(float collisionDrag) {
    this.collisionDrag = collisionDrag;
  }

  /**
   * @return Coefficient Of Restitution
   */
  public float coefficientOfRestitution() {
    return this.coefficientOfRestitution;
  }

  /**
   * @param coefficientOfRestitution Coefficient Of Restitution
   */
  public void coefficientOfRestitution(float coefficientOfRestitution) {
    this.coefficientOfRestitution = coefficientOfRestitution;
  }

  /**
   * @return Collision Radius
   */
  public float collisionRadius() {
    return this.collisionRadius;
  }

  /**
   * @param collisionRadius Collision Radius
   */
  public void collisionRadius(float collisionRadius) {
    this.collisionRadius = collisionRadius;
  }

  public String enabled() {
    return this.enabled;
  }

  public void enabled(String enabled) {
    this.enabled = enabled;
  }

  /**
   * @return Expire On Contact
   */
  public boolean expireOnContact() {
    return this.expireOnContact;
  }

  /**
   * @param expireOnContact Expire On Contact
   */
  public void expireOnContact(boolean expireOnContact) {
    this.expireOnContact = expireOnContact;
  }
}
