package org.geysermc.pack.bedrock.resource.items;

import com.google.gson.annotations.SerializedName;
import java.lang.String;

/**
 * Item
 * <p>
 * Minecraft items 1.10.0
 */
public class Items {
  @SerializedName("format_version")
  public String formatVersion;

  @SerializedName("minecraft:item")
  public Item item;

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @return Format Version
   */
  public String formatVersion() {
    return this.formatVersion;
  }

  /**
   * A version that tells minecraft what type of data format can be expected when reading this file.
   *
   * @param formatVersion Format Version
   */
  public void formatVersion(String formatVersion) {
    this.formatVersion = formatVersion;
  }

  /**
   * A resource pack definition of an item.
   *
   * @return Item
   */
  public Item item() {
    return this.item;
  }

  /**
   * A resource pack definition of an item.
   *
   * @param item Item
   */
  public void item(Item item) {
    this.item = item;
  }
}
