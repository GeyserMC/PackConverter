package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes;

import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Down;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.East;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.North;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.South;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Up;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.West;

public class Uv {
  public North north;

  public South south;

  public East east;

  public West west;

  public Up up;

  public Down down;

  public North north() {
    return this.north;
  }

  public void north(North north) {
    this.north = north;
  }

  public South south() {
    return this.south;
  }

  public void south(South south) {
    this.south = south;
  }

  public East east() {
    return this.east;
  }

  public void east(East east) {
    this.east = east;
  }

  public West west() {
    return this.west;
  }

  public void west(West west) {
    this.west = west;
  }

  public Up up() {
    return this.up;
  }

  public void up(Up up) {
    this.up = up;
  }

  public Down down() {
    return this.down;
  }

  public void down(Down down) {
    this.down = down;
  }
}
