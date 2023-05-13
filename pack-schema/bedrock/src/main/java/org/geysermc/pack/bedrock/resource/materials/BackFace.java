package org.geysermc.pack.bedrock.resource.materials;

import java.lang.String;

/**
 * Face
 */
public class BackFace {
  public String stencilDepthFailOp;

  public String stencilFailOp;

  public String stencilFunc;

  public String stencilPass;

  public String stencilPassOp;

  /**
   * @return Stencil Depth Fail Operation
   */
  public String stencilDepthFailOp() {
    return this.stencilDepthFailOp;
  }

  /**
   * @param stencilDepthFailOp Stencil Depth Fail Operation
   */
  public void stencilDepthFailOp(String stencilDepthFailOp) {
    this.stencilDepthFailOp = stencilDepthFailOp;
  }

  /**
   * @return Stencil Fail Operation
   */
  public String stencilFailOp() {
    return this.stencilFailOp;
  }

  /**
   * @param stencilFailOp Stencil Fail Operation
   */
  public void stencilFailOp(String stencilFailOp) {
    this.stencilFailOp = stencilFailOp;
  }

  /**
   * @return Stencil Function
   */
  public String stencilFunc() {
    return this.stencilFunc;
  }

  /**
   * @param stencilFunc Stencil Function
   */
  public void stencilFunc(String stencilFunc) {
    this.stencilFunc = stencilFunc;
  }

  /**
   * @return Stencil Pass
   */
  public String stencilPass() {
    return this.stencilPass;
  }

  /**
   * @param stencilPass Stencil Pass
   */
  public void stencilPass(String stencilPass) {
    this.stencilPass = stencilPass;
  }

  /**
   * @return Stencil Depth Fail Operation
   */
  public String stencilPassOp() {
    return this.stencilPassOp;
  }

  /**
   * @param stencilPassOp Stencil Depth Fail Operation
   */
  public void stencilPassOp(String stencilPassOp) {
    this.stencilPassOp = stencilPassOp;
  }
}
