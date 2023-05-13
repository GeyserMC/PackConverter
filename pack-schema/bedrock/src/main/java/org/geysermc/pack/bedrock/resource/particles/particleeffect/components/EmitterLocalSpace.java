package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

/**
 * Emitter Local Space Component For 1.10.0
 */
public class EmitterLocalSpace {
  public boolean position;

  public boolean rotation;

  public boolean velocity;

  /**
   * @return Position
   */
  public boolean position() {
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
  public boolean rotation() {
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
  public boolean velocity() {
    return this.velocity;
  }

  /**
   * @param velocity Rotation
   */
  public void velocity(boolean velocity) {
    this.velocity = velocity;
  }
}
