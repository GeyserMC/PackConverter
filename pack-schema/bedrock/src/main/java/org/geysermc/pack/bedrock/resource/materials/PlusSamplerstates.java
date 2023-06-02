package org.geysermc.pack.bedrock.resource.materials;

import java.lang.Integer;
import java.lang.String;

/**
 * Sample State
 */
public class PlusSamplerstates {
  public Integer samplerIndex;

  public String textureFilter;

  public String textureWrap;

  /**
   * @return Sample State
   */
  public Integer samplerIndex() {
    return this.samplerIndex;
  }

  /**
   * @param samplerIndex Sample State
   */
  public void samplerIndex(int samplerIndex) {
    this.samplerIndex = samplerIndex;
  }

  /**
   * @return Texture Filter
   */
  public String textureFilter() {
    return this.textureFilter;
  }

  /**
   * @param textureFilter Texture Filter
   */
  public void textureFilter(String textureFilter) {
    this.textureFilter = textureFilter;
  }

  /**
   * @return Texture Wrap
   */
  public String textureWrap() {
    return this.textureWrap;
  }

  /**
   * @param textureWrap Texture Wrap
   */
  public void textureWrap(String textureWrap) {
    this.textureWrap = textureWrap;
  }
}
