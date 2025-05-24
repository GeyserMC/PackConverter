package org.geysermc.pack.bedrock.resource.textures;

import com.google.gson.annotations.SerializedName;

/**
 * Flipbook Texture Entry
 * <p>
 * An entry in the flipbook texture file that specifies an animated texture.
 */
public class FlipbookTexture {
    @SerializedName("atlas_tile")
    public String atlasTile;

    @SerializedName("flipbook_texture")
    public String flipbookTexture;

    @SerializedName("ticks_per_frame")
    public int ticksPerFrame;

    public String atlasTile() {
        return this.atlasTile;
    }

    public void atlasTile(String atlasTile) {
        this.atlasTile = atlasTile;
    }

    public String flipbookTexture() {
        return this.flipbookTexture;
    }

    public void flipbookTexture(String flipbookTexture) {
        this.flipbookTexture = flipbookTexture;
    }

    public int ticksPerFrame() {
        return this.ticksPerFrame;
    }

    public void ticksPerFrame(int ticksPerFrame) {
        this.ticksPerFrame = ticksPerFrame;
    }
}
