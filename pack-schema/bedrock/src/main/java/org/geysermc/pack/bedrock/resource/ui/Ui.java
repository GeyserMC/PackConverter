package org.geysermc.pack.bedrock.resource.ui;

import java.lang.String;

/**
 * Minecraft JSON UI
 */
public class Ui {
  public String namespace;

  /**
   * @return Namespace
   */
  public String namespace() {
    return this.namespace;
  }

  /**
   * @param namespace Namespace
   */
  public void namespace(String namespace) {
    this.namespace = namespace;
  }
}
