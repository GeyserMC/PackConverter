package org.geysermc.pack.bedrock.resource.fog.fogsettings.volumetric.mediacoefficients;

public class Air {
  public float[] absorption;

  public float[] scattering;

  public float[] absorption() {
    return this.absorption;
  }

  public void absorption(float[] absorption) {
    this.absorption = absorption;
  }

  public float[] scattering() {
    return this.scattering;
  }

  public void scattering(float[] scattering) {
    this.scattering = scattering;
  }
}
