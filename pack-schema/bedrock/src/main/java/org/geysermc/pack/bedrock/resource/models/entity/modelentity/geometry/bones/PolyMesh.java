package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ***EXPERIMENTAL*** A triangle or quad mesh object. Can be used in conjunction with cubes and texture geometry.
 */
public class PolyMesh {
  @JsonProperty("normalized_uvs")
  public boolean normalizedUvs;

  public float[][] normals;

  public float[][][] polys;

  public float[][] positions;

  public float[][] uvs;

  /**
   * If true, UVs are assumed to be [0-1]. If false, UVs are assumed to be [0-texture_width] and [0-texture_height] respectively.
   */
  public boolean normalizedUvs() {
    return this.normalizedUvs;
  }

  /**
   * If true, UVs are assumed to be [0-1]. If false, UVs are assumed to be [0-texture_width] and [0-texture_height] respectively.
   */
  public void normalizedUvs(boolean normalizedUvs) {
    this.normalizedUvs = normalizedUvs;
  }

  /**
   * Vertex normals. Can be either indexed via the `polys` section, or be a quad-list if mapped 1-to-1 to the positions and UVs sections.
   */
  public float[][] normals() {
    return this.normals;
  }

  /**
   * Vertex normals. Can be either indexed via the `polys` section, or be a quad-list if mapped 1-to-1 to the positions and UVs sections.
   */
  public void normals(float[][] normals) {
    this.normals = normals;
  }

  /**
   * Poly element indices, as an array of polygons, each an array of either three or four vertices, each an array of indices into positions, normals, and UVs (in that order).
   */
  public float[][][] polys() {
    return this.polys;
  }

  /**
   * Poly element indices, as an array of polygons, each an array of either three or four vertices, each an array of indices into positions, normals, and UVs (in that order).
   */
  public void polys(float[][][] polys) {
    this.polys = polys;
  }

  public float[][] positions() {
    return this.positions;
  }

  public void positions(float[][] positions) {
    this.positions = positions;
  }

  /**
   * Vertex UVs. Can be either indexed via the `polys` section, or be a quad-list if mapped 1-to-1 to the positions and normals sections.
   */
  public float[][] uvs() {
    return this.uvs;
  }

  /**
   * Vertex UVs. Can be either indexed via the `polys` section, or be a quad-list if mapped 1-to-1 to the positions and normals sections.
   */
  public void uvs(float[][] uvs) {
    this.uvs = uvs;
  }
}
