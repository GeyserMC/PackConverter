package org.geysermc.pack.bedrock.resource.particles.particleeffect.components.particleappearancetinting;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Interpolation based color.
 */
public class Color {
  private Map<String, String> gradient = new HashMap<>();

  public String interpolant;

  /**
   * An object of colors.
   */
  public Map<String, String> gradient() {
    return this.gradient;
  }

  /**
   * An object of colors.
   */
  public void gradient(Map<String, String> gradient) {
    this.gradient = gradient;
  }

  public String interpolant() {
    return this.interpolant;
  }

  public void interpolant(String interpolant) {
    this.interpolant = interpolant;
  }
}
