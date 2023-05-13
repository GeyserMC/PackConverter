package org.geysermc.pack.bedrock.resource.textures;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.textures.itemtexture.TextureData;

/**
 * Item Texture File
 */
public class ItemTexture {
  @JsonProperty("resource_pack_name")
  public String resourcePackName;

  @JsonProperty("texture_data")
  private Map<String, TextureData> textureData = new HashMap<>();

  @JsonProperty("texture_name")
  public String textureName;

  /**
   * @return Resource Pack Name
   */
  public String resourcePackName() {
    return this.resourcePackName;
  }

  /**
   * @param resourcePackName Resource Pack Name
   */
  public void resourcePackName(String resourcePackName) {
    this.resourcePackName = resourcePackName;
  }

  /**
   * @return Texture Data
   */
  public Map<String, TextureData> textureData() {
    return this.textureData;
  }

  /**
   * @param textureData Texture Data
   */
  public void textureData(Map<String, TextureData> textureData) {
    this.textureData = textureData;
  }

  /**
   * @return Texture Name
   */
  public String textureName() {
    return this.textureName;
  }

  /**
   * @param textureName Texture Name
   */
  public void textureName(String textureName) {
    this.textureName = textureName;
  }
}
