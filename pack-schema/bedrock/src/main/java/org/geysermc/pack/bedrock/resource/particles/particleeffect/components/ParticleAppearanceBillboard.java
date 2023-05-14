package org.geysermc.pack.bedrock.resource.particles.particleeffect.components;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.Direction;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancebillboard.Uv;

/**
 * Particle Appearance Billboard Component For 1.10.0
 */
public class ParticleAppearanceBillboard {
  @SerializedName("facing_camera_mode")
  public String facingCameraMode;

  public Direction direction;

  public Uv uv;

  /**
   * Used to orient the billboard.
   *
   * @return Facing Camera Mode
   */
  public String facingCameraMode() {
    return this.facingCameraMode;
  }

  /**
   * Used to orient the billboard.
   *
   * @param facingCameraMode Facing Camera Mode
   */
  public void facingCameraMode(String facingCameraMode) {
    this.facingCameraMode = facingCameraMode;
  }

  public Direction direction() {
    return this.direction;
  }

  public void direction(Direction direction) {
    this.direction = direction;
  }

  /**
   * @return Uv
   */
  public Uv uv() {
    return this.uv;
  }

  /**
   * @param uv Uv
   */
  public void uv(Uv uv) {
    this.uv = uv;
  }
}
