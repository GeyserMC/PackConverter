package org.geysermc.pack.bedrock.resource.particles.particleeffect;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.curves.Nodes;

/**
 * Curves
 * <p>
 * Curves are interpolation values, with inputs from 0 to 1, and outputs based on the curve. The result of the curve is a Molang variable of the same name that can be referenced in Molang in components. For each rendering frame for each particle, the curves are evaluated and the result is placed in a Molang variable of the name of the curve.
 */
public class Curves {
  public String input;

  private Map<String, Nodes> nodes = new HashMap<>();

  public String type;

  @SerializedName("horizontal_range")
  public String horizontalRange;

  public String input() {
    return this.input;
  }

  public void input(String input) {
    this.input = input;
  }

  public Map<String, Nodes> nodes() {
    return this.nodes;
  }

  public void nodes(Map<String, Nodes> nodes) {
    this.nodes = nodes;
  }

  /**
   * The type of curve.
   *
   * @return Type
   */
  public String type() {
    return this.type;
  }

  /**
   * The type of curve.
   *
   * @param type Type
   */
  public void type(String type) {
    this.type = type;
  }

  public String horizontalRange() {
    return this.horizontalRange;
  }

  public void horizontalRange(String horizontalRange) {
    this.horizontalRange = horizontalRange;
  }
}
