package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * Flipbook Texture
 * <p>
 * A single flipbook texture.
 */
public class FlipbookTexture {
  @SerializedName("atlas_index")
  public Integer atlasIndex;

  @SerializedName("atlas_tile")
  public String atlasTile;

  @SerializedName("atlas_tile_variant")
  public Integer atlasTileVariant;

  @SerializedName("blend_frames")
  public Boolean blendFrames;

  @SerializedName("flipbook_texture")
  public String flipbookTexture;

  public int[] frames;

  public Integer replicate;

  @SerializedName("ticks_per_frame")
  public Integer ticksPerFrame;

  /**
   * The index of the texture array inside the definition of that shortname.
   *
   * @return Atlas Index
   */
  public Integer atlasIndex() {
    return this.atlasIndex;
  }

  /**
   * The index of the texture array inside the definition of that shortname.
   *
   * @param atlasIndex Atlas Index
   */
  public void atlasIndex(int atlasIndex) {
    this.atlasIndex = atlasIndex;
  }

  /**
   * The shortname defined in the terrain_texture JSON file.
   *
   * @return Atlas Title
   */
  public String atlasTile() {
    return this.atlasTile;
  }

  /**
   * The shortname defined in the terrain_texture JSON file.
   *
   * @param atlasTile Atlas Title
   */
  public void atlasTile(String atlasTile) {
    this.atlasTile = atlasTile;
  }

  /**
   * The variant of the block's texture array inside the shortname's block variation.
   *
   * @return Atlas Tile Variant
   */
  public Integer atlasTileVariant() {
    return this.atlasTileVariant;
  }

  /**
   * The variant of the block's texture array inside the shortname's block variation.
   *
   * @param atlasTileVariant Atlas Tile Variant
   */
  public void atlasTileVariant(int atlasTileVariant) {
    this.atlasTileVariant = atlasTileVariant;
  }

  /**
   * Whether or not Minecraft should interpolate between frames.
   *
   * @return Blend Frames
   */
  public Boolean blendFrames() {
    return this.blendFrames;
  }

  /**
   * Whether or not Minecraft should interpolate between frames.
   *
   * @param blendFrames Blend Frames
   */
  public void blendFrames(boolean blendFrames) {
    this.blendFrames = blendFrames;
  }

  /**
   * A texture file.
   *
   * @return Flipbook Texture
   */
  public String flipbookTexture() {
    return this.flipbookTexture;
  }

  /**
   * A texture file.
   *
   * @param flipbookTexture Flipbook Texture
   */
  public void flipbookTexture(String flipbookTexture) {
    this.flipbookTexture = flipbookTexture;
  }

  /**
   * The order in which frame indices to display. Duplicate frames are allowed.
   *
   * @return Frames
   */
  public int[] frames() {
    return this.frames;
  }

  /**
   * The order in which frame indices to display. Duplicate frames are allowed.
   *
   * @param frames Frames
   */
  public void frames(int[] frames) {
    this.frames = frames;
  }

  /**
   * Sets the size of pixels.
   *
   * @return Replicate
   */
  public Integer replicate() {
    return this.replicate;
  }

  /**
   * Sets the size of pixels.
   *
   * @param replicate Replicate
   */
  public void replicate(int replicate) {
    this.replicate = replicate;
  }

  /**
   * The amount of ticks to wait between frames.
   *
   * @return Ticks Per Frame
   */
  public Integer ticksPerFrame() {
    return this.ticksPerFrame;
  }

  /**
   * The amount of ticks to wait between frames.
   *
   * @param ticksPerFrame Ticks Per Frame
   */
  public void ticksPerFrame(int ticksPerFrame) {
    this.ticksPerFrame = ticksPerFrame;
  }
}
