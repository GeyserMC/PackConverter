package org.geysermc.pack.bedrock.resource.fog.fogsettings;

import java.lang.String;

/**
 * Description
 * <p>
 * The identifying description of this fog settings.
 */
public class Description {
  public String identifier;

  /**
   * The identifier for these fog settings. The identifier must include a namespace.
   *
   * @return Identifier
   */
  public String identifier() {
    return this.identifier;
  }

  /**
   * The identifier for these fog settings. The identifier must include a namespace.
   *
   * @param identifier Identifier
   */
  public void identifier(String identifier) {
    this.identifier = identifier;
  }
}
