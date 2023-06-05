/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.converter.texture.transformer.type.particle;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.Spritesheet;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AutoService(TextureTransformer.class)
public class BaseParticleTransformer implements TextureTransformer {
    private static final String PATH = "particle";
    private static final String OUTPUT = "particles.png";

    private static final List<TextureData> TEXTURES = List.of(
            new AtlasTextureData("generic", 8),
            new AtlasTextureData("splash", 8),
            new MultiTextureData("particle/bubble", "entity/fishing_hook", "particle/bubble_gray", null, "particle/flash"),
            new MultiTextureData("particle/flame", "particle/lava", "particle/soul_fire_flame", null), // TODO: Soul lava?
            new MultiTextureData("particle/note", "particle/critical_hit", "particle/enchanted_hit"),
            new MultiTextureData("particle/heart", "particle/angry", "particle/glint", null), // TODO: Villager?
            new MultiTextureData(null, null, "particle/glow"),
            new MultiTextureData("particle/drip_hang", "particle/drip_fall", "particle/drip_land"),
            new AtlasTextureData("effect", 8),
            new AtlasTextureData("spell", 8),
            new AtlasTextureData("explosion", 16),
            new AtlasTextureData("glitter", 8),
            new AtlasTextureData("spark", 8),
            new MultiTextureData(new String[] { null }), // TODO: What are these textures?
            new MultiTextureData(null, "particle/sga_a", "particle/sga_b", "particle/sga_c", "particle/sga_d", "particle/sga_e", "particle/sga_f", "particle/sga_g", "particle/sga_h", "particle/sga_i", "particle/sga_j", "particle/sga_k", "particle/sga_l", "particle/sga_m", "particle/sga_n", "particle/sga_o"),
            new MultiTextureData("particle/sga_p", "particle/sga_q", "particle/sga_r", "particle/sga_s", "particle/sga_t", "particle/sga_u", "particle/sga_v", "particle/sga_w", "particle/sga_x", "particle/sga_y", "particle/sga_z")
    );

    private static final Map<String, Integer> PARTICLE_SCALES = Map.of(
            "particle/flash.png", 4
    );

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        // Create a grayscale bubble image
        Texture bubbleTexture = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, PATH + "/bubble.png"));
        if (bubbleTexture != null) {
            BufferedImage bubble = this.readImage(bubbleTexture);
            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, PATH + "/bubble_gray.png"), ImageUtil.grayscale(bubble), "png");
        }

        this.createSpritesheet(context);
    }

    private void createSpritesheet(@NotNull TransformContext context) throws IOException {
        Spritesheet spritesheet = new Spritesheet();
        int spriteSize = -1;

        BufferedImage vanillaSprite = ImageUtil.loadImage("/particle_spritesheet.png");
        
        int[][] occupiedSectors = null;
        for (int i = 0; i < TEXTURES.size(); i++) {
            TextureData textureData = TEXTURES.get(i);
            Texture[] textures = textureData.textures(context);
            Image[] images = new Image[textures.length];
            for (Texture texture : textures) {
                if (texture == null) {
                    continue;
                }

                BufferedImage image = this.readImage(texture);
                if (spriteSize == -1) {
                    spriteSize = image.getWidth(null);
                    
                    vanillaSprite = ImageUtil.resize(vanillaSprite, spriteSize * spriteSize, spriteSize * spriteSize);
                    occupiedSectors = new int[spriteSize * spriteSize][spriteSize * spriteSize];
                    break;
                }
            }

            // This is a bit of a hack, but ensures that all elements in the sprite
            // are the same size
            if (spriteSize == -1) {
                context.debug(String.format("No textures found for sprite with textures %s", Arrays.toString(textures)));
                return;
            }

            for (int j = 0; j < textures.length; j++) {
                Texture texture = textures[j];
                if (texture == null) {
                    images[j] = com.twelvemonkeys.image.ImageUtil.createTransparent(spriteSize, spriteSize);
                    continue;
                }

                int textureSize = spriteSize * PARTICLE_SCALES.getOrDefault(texture.key().value(), 1);

                BufferedImage image = ImageUtil.resize(this.readImage(texture), textureSize, textureSize);
                images[j] = image;
                
                occupiedSectors[j][i] = textureSize;
            }

            spritesheet.addRow(images);
        }

        if (!spritesheet.hasSprites()) {
            return;
        }
        
        BufferedImage spriteImage = spritesheet.compile();
        int expectedSize = spriteSize * spriteSize;
        if (spriteImage.getWidth() != expectedSize || spriteImage.getHeight() != expectedSize) {
            context.warn(String.format("Expected sprite image to be %dx%d, but was %dx%d. Automatically expanding canvas (this may cause issues).", expectedSize, expectedSize, spriteImage.getWidth(), spriteImage.getHeight()));
            spriteImage = ImageUtil.expandCanvas(spriteImage, expectedSize, expectedSize);
        }

        vanillaSprite = ImageUtil.resize(vanillaSprite, spriteImage.getWidth(), spriteImage.getHeight());

        Graphics2D graphics = vanillaSprite.createGraphics();
        graphics.setBackground(new Color(0, 0, 0, 0));

        for (int x = 0; x < occupiedSectors.length; x++) {
            for (int y = 0; y < occupiedSectors[x].length; y++) {
                int size = occupiedSectors[x][y];
                if (size == 0) {
                    continue;
                }

                int spriteX = x * spriteSize;
                int spriteY = y * spriteSize;
                graphics.clearRect(spriteX, spriteY, size, size);
            }
        }

        graphics.drawImage(spriteImage, 0, 0, null);
        graphics.dispose();

        context.debug(String.format("Creating particle spritesheet %s", OUTPUT));

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, PATH + "/" + OUTPUT), vanillaSprite, "png");
    }

    interface TextureData {

        Texture[] textures(@NotNull TransformContext context);
    }

    record AtlasTextureData(@NotNull String javaName, int atlasCount) implements TextureData {

        @NotNull
        public Key textureKey(int atlas) {
            return Key.key(Key.MINECRAFT_NAMESPACE, PATH + "/" + javaName + "_" + atlas + ".png");
        }

        @Override
        public Texture[] textures(@NotNull TransformContext context) {
            Texture[] textures = new Texture[this.atlasCount];
            for (int atlas = 0; atlas < this.atlasCount; atlas++) {
                Texture texture = context.poll(this.textureKey(atlas));
                textures[atlas] = texture;
            }

            return textures;
        }
    }

    record MultiTextureData(String @NotNull... textureNames) implements TextureData {

        @Override
        public Texture[] textures(@NotNull TransformContext context) {
            Texture[] textures = new Texture[this.textureNames.length];
            for (int i = 0; i < this.textureNames.length; i++) {
                String textureName = this.textureNames[i];
                if (textureName == null) {
                    continue;
                }

                Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, textureName + ".png"));
                if (texture == null) {
                    continue;
                }

                textures[i] = texture;
            }

            return textures;
        }
    }
}
