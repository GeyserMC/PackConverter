package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones;

import java.lang.Boolean;
import java.lang.Float;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;

/**
 * A single cube.
 */
public class Cubes {
  public Float inflate;

  public Boolean mirror;

  public float[] origin;

  public float[] pivot;

  public Boolean reset;

  public float[] rotation;

  public float[] size;

  public Uv uv;

  /**
   * Grow this box by this additive amount in all directions (in model space units), this field overrides the bone's inflate field for this cube only.
   */
  public Float inflate() {
    return this.inflate;
  }

  /**
   * Grow this box by this additive amount in all directions (in model space units), this field overrides the bone's inflate field for this cube only.
   */
  public void inflate(float inflate) {
    this.inflate = inflate;
  }

  /**
   * Mirrors this cube about the unrotated x axis (effectively flipping the east / west faces), overriding the bone's `mirror` setting for this cube.
   */
  public Boolean mirror() {
    return this.mirror;
  }

  /**
   * Mirrors this cube about the unrotated x axis (effectively flipping the east / west faces), overriding the bone's `mirror` setting for this cube.
   */
  public void mirror(boolean mirror) {
    this.mirror = mirror;
  }

  public float[] origin() {
    return this.origin;
  }

  public void origin(float[] origin) {
    this.origin = origin;
  }

  /**
   * If this field is specified, rotation of this cube occurs around this point, otherwise its rotation is around the center of the box. Note that in 1.12 this is flipped upside-down, but is fixed in 1.14.
   *
   * @return Pivot
   */
  public float[] pivot() {
    return this.pivot;
  }

  /**
   * If this field is specified, rotation of this cube occurs around this point, otherwise its rotation is around the center of the box. Note that in 1.12 this is flipped upside-down, but is fixed in 1.14.
   *
   * @param pivot Pivot
   */
  public void pivot(float[] pivot) {
    this.pivot = pivot;
  }

  /**
   * @return Reset
   */
  public Boolean reset() {
    return this.reset;
  }

  /**
   * @param reset Reset
   */
  public void reset(boolean reset) {
    this.reset = reset;
  }

  /**
   * @return Rotation
   */
  public float[] rotation() {
    return this.rotation;
  }

  /**
   * @param rotation Rotation
   */
  public void rotation(float[] rotation) {
    this.rotation = rotation;
  }

  /**
   * The cube extends this amount relative to its origin (in model space units).
   *
   * @return Size
   */
  public float[] size() {
    return this.size;
  }

  /**
   * The cube extends this amount relative to its origin (in model space units).
   *
   * @param size Size
   */
  public void size(float[] size) {
    this.size = size;
  }

  public Uv uv() {
    return this.uv;
  }

  public void uv(Uv uv) {
    this.uv = uv;
  }
}
