package org.geysermc.pack.bedrock.resource.attachables.attachable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.pack.bedrock.resource.attachables.attachable.description.Scripts;
import org.geysermc.pack.bedrock.resource.attachables.attachable.description.SpawnEgg;

/**
 * Description
 */
public class Description {
  private Map<String, String> animations = new HashMap<>();

  @JsonProperty("animation_controllers")
  public String[] animationControllers;

  @JsonProperty("enable_attachables")
  public boolean enableAttachables;

  private Map<String, String> geometry = new HashMap<>();

  public String identifier;

  private Map<String, String> item = new HashMap<>();

  private Map<String, String> materials = new HashMap<>();

  @JsonProperty("min_engine_version")
  public String minEngineVersion;

  @JsonProperty("particle_effects")
  private Map<String, String> particleEffects = new HashMap<>();

  @JsonProperty("particle_emitters")
  private Map<String, String> particleEmitters = new HashMap<>();

  @JsonProperty("render_controllers")
  public String[] renderControllers;

  public Scripts scripts;

  @JsonProperty("sound_effects")
  public String[] soundEffects;

  @JsonProperty("spawn_egg")
  public SpawnEgg spawnEgg;

  private Map<String, String> textures = new HashMap<>();

  /**
   * The collection of animations references.
   *
   * @return Animations
   */
  public Map<String, String> animations() {
    return this.animations;
  }

  /**
   * The collection of animations references.
   *
   * @param animations Animations
   */
  public void animations(Map<String, String> animations) {
    this.animations = animations;
  }

  /**
   * The specification of animation controllers.
   *
   * @return Animation Controllers
   */
  public String[] animationControllers() {
    return this.animationControllers;
  }

  /**
   * The specification of animation controllers.
   *
   * @param animationControllers Animation Controllers
   */
  public void animationControllers(String[] animationControllers) {
    this.animationControllers = animationControllers;
  }

  /**
   * @return Enable Attachables
   */
  public boolean enableAttachables() {
    return this.enableAttachables;
  }

  /**
   * @param enableAttachables Enable Attachables
   */
  public void enableAttachables(boolean enableAttachables) {
    this.enableAttachables = enableAttachables;
  }

  /**
   * The geometry specification.
   *
   * @return Geometry
   */
  public Map<String, String> geometry() {
    return this.geometry;
  }

  /**
   * The geometry specification.
   *
   * @param geometry Geometry
   */
  public void geometry(Map<String, String> geometry) {
    this.geometry = geometry;
  }

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
   * @return Item
   */
  public Map<String, String> item() {
    return this.item;
  }

  /**
   * @param item Item
   */
  public void item(Map<String, String> item) {
    this.item = item;
  }

  /**
   * A collection of material references.
   *
   * @return Materials
   */
  public Map<String, String> materials() {
    return this.materials;
  }

  /**
   * A collection of material references.
   *
   * @param materials Materials
   */
  public void materials(Map<String, String> materials) {
    this.materials = materials;
  }

  /**
   * The minimum engine needed to use this.
   *
   * @return Minimum Engine Version
   */
  public String minEngineVersion() {
    return this.minEngineVersion;
  }

  /**
   * The minimum engine needed to use this.
   *
   * @param minEngineVersion Minimum Engine Version
   */
  public void minEngineVersion(String minEngineVersion) {
    this.minEngineVersion = minEngineVersion;
  }

  /**
   * A collection of particle effect references.
   *
   * @return Particle Effects
   */
  public Map<String, String> particleEffects() {
    return this.particleEffects;
  }

  /**
   * A collection of particle effect references.
   *
   * @param particleEffects Particle Effects
   */
  public void particleEffects(Map<String, String> particleEffects) {
    this.particleEffects = particleEffects;
  }

  /**
   * @return Particle Emitters
   */
  public Map<String, String> particleEmitters() {
    return this.particleEmitters;
  }

  /**
   * @param particleEmitters Particle Emitters
   */
  public void particleEmitters(Map<String, String> particleEmitters) {
    this.particleEmitters = particleEmitters;
  }

  /**
   * @return Render Controllers
   */
  public String[] renderControllers() {
    return this.renderControllers;
  }

  /**
   * @param renderControllers Render Controllers
   */
  public void renderControllers(String[] renderControllers) {
    this.renderControllers = renderControllers;
  }

  /**
   * @return Scripts
   */
  public Scripts scripts() {
    return this.scripts;
  }

  /**
   * @param scripts Scripts
   */
  public void scripts(Scripts scripts) {
    this.scripts = scripts;
  }

  /**
   * @return Sound Effects
   */
  public String[] soundEffects() {
    return this.soundEffects;
  }

  /**
   * @param soundEffects Sound Effects
   */
  public void soundEffects(String[] soundEffects) {
    this.soundEffects = soundEffects;
  }

  /**
   * @return Spawn Egg
   */
  public SpawnEgg spawnEgg() {
    return this.spawnEgg;
  }

  /**
   * @param spawnEgg Spawn Egg
   */
  public void spawnEgg(SpawnEgg spawnEgg) {
    this.spawnEgg = spawnEgg;
  }

  /**
   * @return Textures
   */
  public Map<String, String> textures() {
    return this.textures;
  }

  /**
   * @param textures Textures
   */
  public void textures(Map<String, String> textures) {
    this.textures = textures;
  }
}
