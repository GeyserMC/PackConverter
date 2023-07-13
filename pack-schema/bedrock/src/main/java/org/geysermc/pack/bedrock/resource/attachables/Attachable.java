package org.geysermc.pack.bedrock.resource.attachables;

import org.geysermc.pack.bedrock.resource.attachables.attachable.Description;

/**
 * Attachables
 * <p>
 * The attachables definition.
 */
public class Attachable {
  public Description description;

  /**
   * @return Description
   */
  public Description description() {
    return this.description;
  }

  /**
   * @param description Description
   */
  public void description(Description description) {
    this.description = description;
  }
}
