package org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ***EXPERIMENTAL*** A triangle or quad mesh object. Can be used in conjunction with cubes and texture geometry.
 */
public class PolyMesh {
  @JsonProperty("normalized_uvs")
  public boolean normalizedUvs;

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
}
