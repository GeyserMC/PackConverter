package org.geysermc.pack.bedrock.resource.blocks;

public class Isotropic {
  public boolean down;

  public boolean up;

  public boolean side;

  public boolean south;

  public boolean north;

  public boolean west;

  public boolean east;

  public boolean down() {
    return this.down;
  }

  public void down(boolean down) {
    this.down = down;
  }

  public boolean up() {
    return this.up;
  }

  public void up(boolean up) {
    this.up = up;
  }

  public boolean side() {
    return this.side;
  }

  public void side(boolean side) {
    this.side = side;
  }

  public boolean south() {
    return this.south;
  }

  public void south(boolean south) {
    this.south = south;
  }

  public boolean north() {
    return this.north;
  }

  public void north(boolean north) {
    this.north = north;
  }

  public boolean west() {
    return this.west;
  }

  public void west(boolean west) {
    this.west = west;
  }

  public boolean east() {
    return this.east;
  }

  public void east(boolean east) {
    this.east = east;
  }
}
