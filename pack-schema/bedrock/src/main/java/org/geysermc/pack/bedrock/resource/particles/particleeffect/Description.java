package org.geysermc.pack.bedrock.resource.particles.particleeffect;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.description.BasicRenderParameters;

/**
 * Description
 */
public class Description {
  public String identifier;

  @SerializedName("basic_render_parameters")
  public BasicRenderParameters basicRenderParameters;

  /**
   * @return Identifier
   */
  public String identifier() {
    return this.identifier;
  }

  /**
   * @param identifier Identifier
   */
  public void identifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * @return Basic Render Parameters
   */
  public BasicRenderParameters basicRenderParameters() {
    return this.basicRenderParameters;
  }

  /**
   * @param basicRenderParameters Basic Render Parameters
   */
  public void basicRenderParameters(BasicRenderParameters basicRenderParameters) {
    this.basicRenderParameters = basicRenderParameters;
  }
}
