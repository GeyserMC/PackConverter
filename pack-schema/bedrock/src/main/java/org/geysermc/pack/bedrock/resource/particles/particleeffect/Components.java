package org.geysermc.pack.bedrock.resource.particles.particleeffect;

import com.google.gson.annotations.SerializedName;
import java.lang.String;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterInitialization;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterLifetimeEvents;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterLifetimeExpression;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterLifetimeLooping;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterLifetimeOnce;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterLocalSpace;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterRateInstant;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterRateManual;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterRateSteady;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapeBox;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapeCustom;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapeDisc;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapeEntityAabb;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapePoint;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.EmitterShapeSphere;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleAppearanceBillboard;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleAppearanceLighting;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleAppearanceTinting;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleInitialSpin;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleInitialization;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleLifetimeEvents;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleLifetimeExpression;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleMotionCollision;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleMotionDynamic;
import org.geysermc.pack.bedrock.resource.particles.particleeffect.components.ParticleMotionParametric;

/**
 * Components
 * <p>
 * The particle components.
 */
public class Components {
  @SerializedName("minecraft:emitter_initialization")
  public EmitterInitialization emitterInitialization;

  @SerializedName("minecraft:emitter_lifetime_events")
  public EmitterLifetimeEvents emitterLifetimeEvents;

  @SerializedName("minecraft:emitter_lifetime_expression")
  public EmitterLifetimeExpression emitterLifetimeExpression;

  @SerializedName("minecraft:emitter_lifetime_once")
  public EmitterLifetimeOnce emitterLifetimeOnce;

  @SerializedName("minecraft:emitter_lifetime_looping")
  public EmitterLifetimeLooping emitterLifetimeLooping;

  @SerializedName("minecraft:emitter_local_space")
  public EmitterLocalSpace emitterLocalSpace;

  @SerializedName("minecraft:emitter_rate_instant")
  public EmitterRateInstant emitterRateInstant;

  @SerializedName("minecraft:emitter_rate_manual")
  public EmitterRateManual emitterRateManual;

  @SerializedName("minecraft:emitter_rate_steady")
  public EmitterRateSteady emitterRateSteady;

  @SerializedName("minecraft:emitter_shape_box")
  public EmitterShapeBox emitterShapeBox;

  @SerializedName("minecraft:emitter_shape_custom")
  public EmitterShapeCustom emitterShapeCustom;

  @SerializedName("minecraft:emitter_shape_disc")
  public EmitterShapeDisc emitterShapeDisc;

  @SerializedName("minecraft:emitter_shape_entity_aabb")
  public EmitterShapeEntityAabb emitterShapeEntityAabb;

  @SerializedName("minecraft:emitter_shape_point")
  public EmitterShapePoint emitterShapePoint;

  @SerializedName("minecraft:emitter_shape_sphere")
  public EmitterShapeSphere emitterShapeSphere;

  @SerializedName("minecraft:particle_appearance_billboard")
  public ParticleAppearanceBillboard particleAppearanceBillboard;

  @SerializedName("minecraft:particle_appearance_tinting")
  public ParticleAppearanceTinting particleAppearanceTinting;

  @SerializedName("minecraft:particle_appearance_lighting")
  public ParticleAppearanceLighting particleAppearanceLighting;

  @SerializedName("minecraft:particle_initialization")
  public ParticleInitialization particleInitialization;

  @SerializedName("minecraft:particle_initial_speed")
  public String particleInitialSpeed;

  @SerializedName("minecraft:particle_initial_spin")
  public ParticleInitialSpin particleInitialSpin;

  @SerializedName("minecraft:particle_lifetime_expression")
  public ParticleLifetimeExpression particleLifetimeExpression;

  @SerializedName("minecraft:particle_lifetime_events")
  public ParticleLifetimeEvents particleLifetimeEvents;

  @SerializedName("minecraft:particle_kill_plane")
  public String[] particleKillPlane;

  @SerializedName("minecraft:particle_motion_collision")
  public ParticleMotionCollision particleMotionCollision;

  @SerializedName("minecraft:particle_motion_dynamic")
  public ParticleMotionDynamic particleMotionDynamic;

  @SerializedName("minecraft:particle_motion_parametric")
  public ParticleMotionParametric particleMotionParametric;

  /**
   * This component allows the emitter to run some Molang at creation, primarily to populate any Molang variables that get used later.
   *
   * @return Emitter Initialization Component For 1.10.0
   */
  public EmitterInitialization emitterInitialization() {
    return this.emitterInitialization;
  }

  /**
   * This component allows the emitter to run some Molang at creation, primarily to populate any Molang variables that get used later.
   *
   * @param emitterInitialization Emitter Initialization Component For 1.10.0
   */
  public void emitterInitialization(EmitterInitialization emitterInitialization) {
    this.emitterInitialization = emitterInitialization;
  }

  /**
   * @return Emitter Lifetime Events Component For 1.10.0
   */
  public EmitterLifetimeEvents emitterLifetimeEvents() {
    return this.emitterLifetimeEvents;
  }

  /**
   * @param emitterLifetimeEvents Emitter Lifetime Events Component For 1.10.0
   */
  public void emitterLifetimeEvents(EmitterLifetimeEvents emitterLifetimeEvents) {
    this.emitterLifetimeEvents = emitterLifetimeEvents;
  }

  /**
   * @return Emitter Rate Manual Component 1.10.0
   */
  public EmitterLifetimeExpression emitterLifetimeExpression() {
    return this.emitterLifetimeExpression;
  }

  /**
   * @param emitterLifetimeExpression Emitter Rate Manual Component 1.10.0
   */
  public void emitterLifetimeExpression(EmitterLifetimeExpression emitterLifetimeExpression) {
    this.emitterLifetimeExpression = emitterLifetimeExpression;
  }

  /**
   * @return Emitter Lifetime Once Component For 1.10.0
   */
  public EmitterLifetimeOnce emitterLifetimeOnce() {
    return this.emitterLifetimeOnce;
  }

  /**
   * @param emitterLifetimeOnce Emitter Lifetime Once Component For 1.10.0
   */
  public void emitterLifetimeOnce(EmitterLifetimeOnce emitterLifetimeOnce) {
    this.emitterLifetimeOnce = emitterLifetimeOnce;
  }

  /**
   * @return Emitter Lifetime Looping Component For 1.10.0
   */
  public EmitterLifetimeLooping emitterLifetimeLooping() {
    return this.emitterLifetimeLooping;
  }

  /**
   * @param emitterLifetimeLooping Emitter Lifetime Looping Component For 1.10.0
   */
  public void emitterLifetimeLooping(EmitterLifetimeLooping emitterLifetimeLooping) {
    this.emitterLifetimeLooping = emitterLifetimeLooping;
  }

  /**
   * @return Emitter Local Space Component For 1.10.0
   */
  public EmitterLocalSpace emitterLocalSpace() {
    return this.emitterLocalSpace;
  }

  /**
   * @param emitterLocalSpace Emitter Local Space Component For 1.10.0
   */
  public void emitterLocalSpace(EmitterLocalSpace emitterLocalSpace) {
    this.emitterLocalSpace = emitterLocalSpace;
  }

  /**
   * @return Emitter Rate Instant Component For 1.10.0
   */
  public EmitterRateInstant emitterRateInstant() {
    return this.emitterRateInstant;
  }

  /**
   * @param emitterRateInstant Emitter Rate Instant Component For 1.10.0
   */
  public void emitterRateInstant(EmitterRateInstant emitterRateInstant) {
    this.emitterRateInstant = emitterRateInstant;
  }

  /**
   * @return Emitter Rate Manual Component For 1.10.0
   */
  public EmitterRateManual emitterRateManual() {
    return this.emitterRateManual;
  }

  /**
   * @param emitterRateManual Emitter Rate Manual Component For 1.10.0
   */
  public void emitterRateManual(EmitterRateManual emitterRateManual) {
    this.emitterRateManual = emitterRateManual;
  }

  /**
   * @return Emitter Rate Steady Component For 1.10.0
   */
  public EmitterRateSteady emitterRateSteady() {
    return this.emitterRateSteady;
  }

  /**
   * @param emitterRateSteady Emitter Rate Steady Component For 1.10.0
   */
  public void emitterRateSteady(EmitterRateSteady emitterRateSteady) {
    this.emitterRateSteady = emitterRateSteady;
  }

  /**
   * @return Emitter Shape Box Component For 1.10.0
   */
  public EmitterShapeBox emitterShapeBox() {
    return this.emitterShapeBox;
  }

  /**
   * @param emitterShapeBox Emitter Shape Box Component For 1.10.0
   */
  public void emitterShapeBox(EmitterShapeBox emitterShapeBox) {
    this.emitterShapeBox = emitterShapeBox;
  }

  /**
   * @return Emitter Shape Custom Component For 1.10.0
   */
  public EmitterShapeCustom emitterShapeCustom() {
    return this.emitterShapeCustom;
  }

  /**
   * @param emitterShapeCustom Emitter Shape Custom Component For 1.10.0
   */
  public void emitterShapeCustom(EmitterShapeCustom emitterShapeCustom) {
    this.emitterShapeCustom = emitterShapeCustom;
  }

  /**
   * @return Emitter Shape Disc Component For 1.10.0
   */
  public EmitterShapeDisc emitterShapeDisc() {
    return this.emitterShapeDisc;
  }

  /**
   * @param emitterShapeDisc Emitter Shape Disc Component For 1.10.0
   */
  public void emitterShapeDisc(EmitterShapeDisc emitterShapeDisc) {
    this.emitterShapeDisc = emitterShapeDisc;
  }

  /**
   * @return Emitter Shape Entity Aabb Component For 1.10.0
   */
  public EmitterShapeEntityAabb emitterShapeEntityAabb() {
    return this.emitterShapeEntityAabb;
  }

  /**
   * @param emitterShapeEntityAabb Emitter Shape Entity Aabb Component For 1.10.0
   */
  public void emitterShapeEntityAabb(EmitterShapeEntityAabb emitterShapeEntityAabb) {
    this.emitterShapeEntityAabb = emitterShapeEntityAabb;
  }

  /**
   * @return Emitter Shape Point Component For 1.10.0
   */
  public EmitterShapePoint emitterShapePoint() {
    return this.emitterShapePoint;
  }

  /**
   * @param emitterShapePoint Emitter Shape Point Component For 1.10.0
   */
  public void emitterShapePoint(EmitterShapePoint emitterShapePoint) {
    this.emitterShapePoint = emitterShapePoint;
  }

  /**
   * @return Emitter Shape Sphere Component For 1.10.0
   */
  public EmitterShapeSphere emitterShapeSphere() {
    return this.emitterShapeSphere;
  }

  /**
   * @param emitterShapeSphere Emitter Shape Sphere Component For 1.10.0
   */
  public void emitterShapeSphere(EmitterShapeSphere emitterShapeSphere) {
    this.emitterShapeSphere = emitterShapeSphere;
  }

  /**
   * @return Particle Appearance Billboard Component For 1.10.0
   */
  public ParticleAppearanceBillboard particleAppearanceBillboard() {
    return this.particleAppearanceBillboard;
  }

  /**
   * @param particleAppearanceBillboard Particle Appearance Billboard Component For 1.10.0
   */
  public void particleAppearanceBillboard(ParticleAppearanceBillboard particleAppearanceBillboard) {
    this.particleAppearanceBillboard = particleAppearanceBillboard;
  }

  /**
   * Color fields are special, they can be either an RGB, or a `#RRGGBB` field (or RGBA or `AARRGGBB`).  If RGB(A), the channels are from 0 to 1.  If the string `#AARRGGBB`, then the values are hex from 00 to ff.
   *
   * @return Particle Appearance Tinting Component For 1.10.0
   */
  public ParticleAppearanceTinting particleAppearanceTinting() {
    return this.particleAppearanceTinting;
  }

  /**
   * Color fields are special, they can be either an RGB, or a `#RRGGBB` field (or RGBA or `AARRGGBB`).  If RGB(A), the channels are from 0 to 1.  If the string `#AARRGGBB`, then the values are hex from 00 to ff.
   *
   * @param particleAppearanceTinting Particle Appearance Tinting Component For 1.10.0
   */
  public void particleAppearanceTinting(ParticleAppearanceTinting particleAppearanceTinting) {
    this.particleAppearanceTinting = particleAppearanceTinting;
  }

  /**
   * @return Particle Appearance Lighting Component For 1.10.0
   */
  public ParticleAppearanceLighting particleAppearanceLighting() {
    return this.particleAppearanceLighting;
  }

  /**
   * @param particleAppearanceLighting Particle Appearance Lighting Component For 1.10.0
   */
  public void particleAppearanceLighting(ParticleAppearanceLighting particleAppearanceLighting) {
    this.particleAppearanceLighting = particleAppearanceLighting;
  }

  /**
   * @return Particle Initialization Component For 1.10.0
   */
  public ParticleInitialization particleInitialization() {
    return this.particleInitialization;
  }

  /**
   * @param particleInitialization Particle Initialization Component For 1.10.0
   */
  public void particleInitialization(ParticleInitialization particleInitialization) {
    this.particleInitialization = particleInitialization;
  }

  public String particleInitialSpeed() {
    return this.particleInitialSpeed;
  }

  public void particleInitialSpeed(String particleInitialSpeed) {
    this.particleInitialSpeed = particleInitialSpeed;
  }

  /**
   * Starts the particle with a specified orientation and rotation rate.
   *
   * @return Particle Initial Spin Component For 1.10.0
   */
  public ParticleInitialSpin particleInitialSpin() {
    return this.particleInitialSpin;
  }

  /**
   * Starts the particle with a specified orientation and rotation rate.
   *
   * @param particleInitialSpin Particle Initial Spin Component For 1.10.0
   */
  public void particleInitialSpin(ParticleInitialSpin particleInitialSpin) {
    this.particleInitialSpin = particleInitialSpin;
  }

  /**
   * @return Particle Lifetime Expression Component For 1.10.0
   */
  public ParticleLifetimeExpression particleLifetimeExpression() {
    return this.particleLifetimeExpression;
  }

  /**
   * @param particleLifetimeExpression Particle Lifetime Expression Component For 1.10.0
   */
  public void particleLifetimeExpression(ParticleLifetimeExpression particleLifetimeExpression) {
    this.particleLifetimeExpression = particleLifetimeExpression;
  }

  /**
   * @return Particle Lifetime Events Component For 1.10.0
   */
  public ParticleLifetimeEvents particleLifetimeEvents() {
    return this.particleLifetimeEvents;
  }

  /**
   * @param particleLifetimeEvents Particle Lifetime Events Component For 1.10.0
   */
  public void particleLifetimeEvents(ParticleLifetimeEvents particleLifetimeEvents) {
    this.particleLifetimeEvents = particleLifetimeEvents;
  }

  /**
   * A*x + B*y + C*z + D = 0
   * with the parameters being [ A, B, C, D ].
   *
   * @return Particle Kill Plane Component For 1.10.0
   */
  public String[] particleKillPlane() {
    return this.particleKillPlane;
  }

  /**
   * A*x + B*y + C*z + D = 0
   * with the parameters being [ A, B, C, D ].
   *
   * @param particleKillPlane Particle Kill Plane Component For 1.10.0
   */
  public void particleKillPlane(String[] particleKillPlane) {
    this.particleKillPlane = particleKillPlane;
  }

  /**
   * @return Particle Motion Collision Component For 1.10.0
   */
  public ParticleMotionCollision particleMotionCollision() {
    return this.particleMotionCollision;
  }

  /**
   * @param particleMotionCollision Particle Motion Collision Component For 1.10.0
   */
  public void particleMotionCollision(ParticleMotionCollision particleMotionCollision) {
    this.particleMotionCollision = particleMotionCollision;
  }

  /**
   * This component specifies the dynamic properties of the particle, from a simulation standpoint what forces act upon the particle? These dynamics alter the velocity of the particle, which is a combination of the direction of the particle and the speed. Particle direction will always be in the direction of the velocity of the particle.
   *
   * @return Particle Motion Dynamic Component For 1.10.0
   */
  public ParticleMotionDynamic particleMotionDynamic() {
    return this.particleMotionDynamic;
  }

  /**
   * This component specifies the dynamic properties of the particle, from a simulation standpoint what forces act upon the particle? These dynamics alter the velocity of the particle, which is a combination of the direction of the particle and the speed. Particle direction will always be in the direction of the velocity of the particle.
   *
   * @param particleMotionDynamic Particle Motion Dynamic Component For 1.10.0
   */
  public void particleMotionDynamic(ParticleMotionDynamic particleMotionDynamic) {
    this.particleMotionDynamic = particleMotionDynamic;
  }

  /**
   * @return Particle Motion Parametric Component For 1.10.0
   */
  public ParticleMotionParametric particleMotionParametric() {
    return this.particleMotionParametric;
  }

  /**
   * @param particleMotionParametric Particle Motion Parametric Component For 1.10.0
   */
  public void particleMotionParametric(ParticleMotionParametric particleMotionParametric) {
    this.particleMotionParametric = particleMotionParametric;
  }
}
