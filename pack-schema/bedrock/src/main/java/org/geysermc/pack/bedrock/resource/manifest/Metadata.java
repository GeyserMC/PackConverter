package org.geysermc.pack.bedrock.resource.manifest;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * Metadata
 * <p>
 * Section containing the metadata about the file such as authors and licensing information.
 */
public class Metadata {
  public String[] authors;

  @SerializedName("generated_with")
  private Map<String, String[]> generatedWith = new HashMap<>();

  public String license;

  @SerializedName("product_type")
  public String productType;

  public String url;

  /**
   * Name of the author(s) of the pack.
   *
   * @return Authors
   */
  public String[] authors() {
    return this.authors;
  }

  /**
   * Name of the author(s) of the pack.
   *
   * @param authors Authors
   */
  public void authors(String[] authors) {
    this.authors = authors;
  }

  /**
   * A list of tools and their version that have modified this pack.
   *
   * @return Generated With
   */
  public Map<String, String[]> generatedWith() {
    return this.generatedWith;
  }

  /**
   * A list of tools and their version that have modified this pack.
   *
   * @param generatedWith Generated With
   */
  public void generatedWith(Map<String, String[]> generatedWith) {
    this.generatedWith = generatedWith;
  }

  /**
   * The license of the pack.
   *
   * @return License
   */
  public String license() {
    return this.license;
  }

  /**
   * The license of the pack.
   *
   * @param license License
   */
  public void license(String license) {
    this.license = license;
  }

  /**
   * The type of product this pack is. This is used to determine how the pack is displayed in the store.
   *
   * @return Product Type
   */
  public String productType() {
    return this.productType;
  }

  /**
   * The type of product this pack is. This is used to determine how the pack is displayed in the store.
   *
   * @param productType Product Type
   */
  public void productType(String productType) {
    this.productType = productType;
  }

  /**
   * The home website of your pack.
   *
   * @return Url
   */
  public String url() {
    return this.url;
  }

  /**
   * The home website of your pack.
   *
   * @param url Url
   */
  public void url(String url) {
    this.url = url;
  }
}
