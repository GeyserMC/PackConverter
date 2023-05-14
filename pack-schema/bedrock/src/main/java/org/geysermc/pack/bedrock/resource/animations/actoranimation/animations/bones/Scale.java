package org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.bones;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

public class Scale {
  @JsonProperty("lerp_mode")
  public String lerpMode;

  public String[] pre;

  public String[] post;

  /**
   * @return Lerp Mode
   */
  public String lerpMode() {
    return this.lerpMode;
  }

  /**
   * @param lerpMode Lerp Mode
   */
  public void lerpMode(String lerpMode) {
    this.lerpMode = lerpMode;
  }

  /**
   * An array of 3 items that describe the bones scale.
   *
   * @return Scale Array
   */
  public String[] pre() {
    return this.pre;
  }

  /**
   * An array of 3 items that describe the bones scale.
   *
   * @param pre Scale Array
   */
  public void pre(String[] pre) {
    this.pre = pre;
  }

  /**
   * An array of 3 items that describe the bones scale.
   *
   * @return Scale Array
   */
  public String[] post() {
    return this.post;
  }

  /**
   * An array of 3 items that describe the bones scale.
   *
   * @param post Scale Array
   */
  public void post(String[] post) {
    this.post = post;
  }
}
