package org.geysermc.pack.bedrock.resource.blocks;

import java.lang.Boolean;

public class Isotropic {
  public Boolean down;

  public Boolean up;

  public Boolean side;

  public Boolean south;

  public Boolean north;

  public Boolean west;

  public Boolean east;

  public Boolean down() {
    return this.down;
  }

  public void down(boolean down) {
    this.down = down;
  }

  public Boolean up() {
    return this.up;
  }

  public void up(boolean up) {
    this.up = up;
  }

  public Boolean side() {
    return this.side;
  }

  public void side(boolean side) {
    this.side = side;
  }

  public Boolean south() {
    return this.south;
  }

  public void south(boolean south) {
    this.south = south;
  }

  public Boolean north() {
    return this.north;
  }

  public void north(boolean north) {
    this.north = north;
  }

  public Boolean west() {
    return this.west;
  }

  public void west(boolean west) {
    this.west = west;
  }

  public Boolean east() {
    return this.east;
  }

  public void east(boolean east) {
    this.east = east;
  }
}
