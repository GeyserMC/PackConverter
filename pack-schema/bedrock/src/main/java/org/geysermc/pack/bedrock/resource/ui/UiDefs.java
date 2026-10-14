package org.geysermc.pack.bedrock.resource.ui;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * UI definitions
 */
public class UiDefs {
  @SerializedName("ui_defs")
  public String[] uiDefs;

  /**
   * @return UI definitions
   */
  public String[] uiDefs() {
    return this.uiDefs;
  }

  /**
   * @param uiDefs UI definitions
   */
  public void uiDefs(String[] uiDefs) {
    this.uiDefs = uiDefs;
  }
}
