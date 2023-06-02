package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import java.lang.Boolean;

/**
 * Emitter Local Space Component For 1.10.0
 */
public class EmitterLocalSpace {
  public Boolean position;

  public Boolean rotation;

  public Boolean velocity;

  /**
   * @return Position
   */
  public Boolean position() {
    return this.position;
  }

  /**
   * @param position Position
   */
  public void position(boolean position) {
    this.position = position;
  }

  /**
   * @return Rotation
   */
  public Boolean rotation() {
    return this.rotation;
  }

  /**
   * @param rotation Rotation
   */
  public void rotation(boolean rotation) {
    this.rotation = rotation;
  }

  /**
   * @return Rotation
   */
  public Boolean velocity() {
    return this.velocity;
  }

  /**
   * @param velocity Rotation
   */
  public void velocity(boolean velocity) {
    this.velocity = velocity;
  }
}
