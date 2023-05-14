package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import java.lang.String;

/**
 * Emitter Shape Point Component For 1.10.0
 */
public class EmitterShapePoint {
  public String[] direction;

  public String[] offset;

  /**
   * @return Direction
   */
  public String[] direction() {
    return this.direction;
  }

  /**
   * @param direction Direction
   */
  public void direction(String[] direction) {
    this.direction = direction;
  }

  /**
   * @return Offset
   */
  public String[] offset() {
    return this.offset;
  }

  /**
   * @param offset Offset
   */
  public void offset(String[] offset) {
    this.offset = offset;
  }
}
