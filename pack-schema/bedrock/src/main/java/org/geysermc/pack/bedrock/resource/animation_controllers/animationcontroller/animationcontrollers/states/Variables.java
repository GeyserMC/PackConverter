package org.geysermc.pack.bedrock.resource.animation_controllers.animationcontroller.animationcontrollers.states;

import com.google.gson.annotations.SerializedName;
import java.lang.Float;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class Variables {
  public String input;

  @SerializedName("remap_curve")
  private Map<String, Float> remapCurve = new HashMap<>();

  public String input() {
    return this.input;
  }

  public void input(String input) {
    this.input = input;
  }

  /**
   * @return Remap Curve
   */
  public Map<String, Float> remapCurve() {
    return this.remapCurve;
  }

  /**
   * @param remapCurve Remap Curve
   */
  public void remapCurve(Map<String, Float> remapCurve) {
    this.remapCurve = remapCurve;
  }
}
