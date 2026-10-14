package org.geysermc.pack.bedrock.resource.manifest;

import java.lang.String;

/**
 * Module
 * <p>
 * Section containing information regarding the type of content that is being brought in.
 */
public class Modules {
  public String description;

  public String type;

  public String language;

  public String uuid;

  public float[] version;

  public String entry;

  /**
   * This is a short description of the module. This is not user-facing at the moment but is a good place to remind yourself why the module is defined
   *
   * @return Description
   */
  public String description() {
    return this.description;
  }

  /**
   * This is a short description of the module. This is not user-facing at the moment but is a good place to remind yourself why the module is defined
   *
   * @param description Description
   */
  public void description(String description) {
    this.description = description;
  }

  /**
   * This is the type of the module.
   *
   * @return Type
   */
  public String type() {
    return this.type;
  }

  /**
   * This is the type of the module.
   *
   * @param type Type
   */
  public void type(String type) {
    this.type = type;
  }

  /**
   * Only present if `type` is `script`. This indicates the language in which scripts are written in the pack. The only supported value is `javascript`.
   *
   * @return Language
   */
  public String language() {
    return this.language;
  }

  /**
   * Only present if `type` is `script`. This indicates the language in which scripts are written in the pack. The only supported value is `javascript`.
   *
   * @param language Language
   */
  public void language(String language) {
    this.language = language;
  }

  /**
   * A valid UUID v4.
   *
   * @return A UUID Format V4
   */
  public String uuid() {
    return this.uuid;
  }

  /**
   * A valid UUID v4.
   *
   * @param uuid A UUID Format V4
   */
  public void uuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * A version made of 3 numbers.
   *
   * @return Version Numbering
   */
  public float[] version() {
    return this.version;
  }

  /**
   * A version made of 3 numbers.
   *
   * @param version Version Numbering
   */
  public void version(float[] version) {
    this.version = version;
  }

  /**
   * The entry file for the pack's scripts. Requires `type` to be set to `script`.
   *
   * @return Entry
   */
  public String entry() {
    return this.entry;
  }

  /**
   * The entry file for the pack's scripts. Requires `type` to be set to `script`.
   *
   * @param entry Entry
   */
  public void entry(String entry) {
    this.entry = entry;
  }
}
