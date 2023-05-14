package org.geysermc.pack.bedrock.resource.animations.actoranimation.animations;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.bones.Position;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.bones.RelativeTo;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.bones.Rotation;
import org.geysermc.pack.bedrock.resource.animations.actoranimation.animations.bones.Scale;

/**
 * Bones
 * <p>
 * Defines how the bones in an animation move or transform.
 */
public class Bones {
  private Map<String, Position> position = new HashMap<>();

  private Map<String, Rotation> rotation = new HashMap<>();

  @SerializedName("relative_to")
  public RelativeTo relativeTo;

  private Map<String, Scale> scale = new HashMap<>();

  public Map<String, Position> position() {
    return this.position;
  }

  public void position(Map<String, Position> position) {
    this.position = position;
  }

  public Map<String, Rotation> rotation() {
    return this.rotation;
  }

  public void rotation(Map<String, Rotation> rotation) {
    this.rotation = rotation;
  }

  /**
   * If set, makes the bone rotation relative to the entity instead of the bone's parent.
   *
   * @return Relative To
   */
  public RelativeTo relativeTo() {
    return this.relativeTo;
  }

  /**
   * If set, makes the bone rotation relative to the entity instead of the bone's parent.
   *
   * @param relativeTo Relative To
   */
  public void relativeTo(RelativeTo relativeTo) {
    this.relativeTo = relativeTo;
  }

  public Map<String, Scale> scale() {
    return this.scale;
  }

  public void scale(Map<String, Scale> scale) {
    this.scale = scale;
  }
}
