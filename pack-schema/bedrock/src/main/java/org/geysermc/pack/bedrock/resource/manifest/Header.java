package org.geysermc.pack.bedrock.resource.manifest;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.String;

/**
 * Header
 * <p>
 * Section containing information regarding the name of the pack, description, and other features that are public facing.
 */
public class Header {
  @SerializedName("allow_random_seed")
  public Boolean allowRandomSeed;

  @SerializedName("base_game_version")
  public float[] baseGameVersion;

  public String description;

  @SerializedName("lock_template_options")
  public Boolean lockTemplateOptions;

  @SerializedName("min_engine_version")
  public float[] minEngineVersion;

  public String name;

  @SerializedName("pack_scope")
  public String packScope;

  public String uuid;

  public float[] version;

  /**
   * This option is required for any world templates. This will allow the player to use a random seed when creating a new world from your template.
   *
   * @return Allow Random Seed
   */
  public Boolean allowRandomSeed() {
    return this.allowRandomSeed;
  }

  /**
   * This option is required for any world templates. This will allow the player to use a random seed when creating a new world from your template.
   *
   * @param allowRandomSeed Allow Random Seed
   */
  public void allowRandomSeed(boolean allowRandomSeed) {
    this.allowRandomSeed = allowRandomSeed;
  }

  /**
   * A version made of 3 numbers.
   *
   * @return Version Numbering
   */
  public float[] baseGameVersion() {
    return this.baseGameVersion;
  }

  /**
   * A version made of 3 numbers.
   *
   * @param baseGameVersion Version Numbering
   */
  public void baseGameVersion(float[] baseGameVersion) {
    this.baseGameVersion = baseGameVersion;
  }

  /**
   * This is a short description of the pack. It will appear in the game below the name of the pack. We recommend keeping it to 1-2 lines.
   *
   * @return Description
   */
  public String description() {
    return this.description;
  }

  /**
   * This is a short description of the pack. It will appear in the game below the name of the pack. We recommend keeping it to 1-2 lines.
   *
   * @param description Description
   */
  public void description(String description) {
    this.description = description;
  }

  /**
   * This option is required for any world templates. This will lock the player from modifying the options of the world.
   *
   * @return Lock Template Options
   */
  public Boolean lockTemplateOptions() {
    return this.lockTemplateOptions;
  }

  /**
   * This option is required for any world templates. This will lock the player from modifying the options of the world.
   *
   * @param lockTemplateOptions Lock Template Options
   */
  public void lockTemplateOptions(boolean lockTemplateOptions) {
    this.lockTemplateOptions = lockTemplateOptions;
  }

  /**
   * A version made of 3 numbers.
   *
   * @return Version Numbering
   */
  public float[] minEngineVersion() {
    return this.minEngineVersion;
  }

  /**
   * A version made of 3 numbers.
   *
   * @param minEngineVersion Version Numbering
   */
  public void minEngineVersion(float[] minEngineVersion) {
    this.minEngineVersion = minEngineVersion;
  }

  /**
   * This is the name of the pack as it appears within Minecraft. This is a required field.
   *
   * @return Name
   */
  public String name() {
    return this.name;
  }

  /**
   * This is the name of the pack as it appears within Minecraft. This is a required field.
   *
   * @param name Name
   */
  public void name(String name) {
    this.name = name;
  }

  /**
   * This is the scope of the pack. This is only for resource packs
   *
   * @return Pack Scope
   */
  public String packScope() {
    return this.packScope;
  }

  /**
   * This is the scope of the pack. This is only for resource packs
   *
   * @param packScope Pack Scope
   */
  public void packScope(String packScope) {
    this.packScope = packScope;
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
}
