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

package org.geysermc.pack.converter.converter.texture.transformer;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.texture.TextureMappings;
import org.geysermc.pack.converter.data.TextureConversionData;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransformContext {
    private final PackConversionContext<TextureConversionData> conversionContext;
    private final TextureMappings mappings;
    private final Collection<Texture> textures;
    private final Map<Key, Texture> byKey = new HashMap<>();

    public TransformContext(PackConversionContext<TextureConversionData> conversionContext, TextureMappings mappings, Collection<Texture> textures) {
        this.conversionContext = conversionContext;
        this.mappings = mappings;
        this.textures = textures;

        for (Texture texture : textures) {
            this.byKey.put(texture.key(), texture);
        }
    }

    public TextureMappings mappings() {
        return this.mappings;
    }

    /**
     * Removes the texture from the list of textures and returns it.
     *
     * @param key the key of the texture to remove
     * @return the texture that was removed, or null if it didn't exist
     */
    @Nullable
    public Texture poll(@NotNull Key key) {
        Texture remove = this.byKey.remove(key);
        if (remove == null) {
            return null;
        }

        this.textures.remove(remove);
        return remove;
    }

    /**
     * Returns the texture with the given key, but does not remove it from the list of textures.
     *
     * @param key the key of the texture to get
     * @return the texture with the given key, or null if it doesn't exist
     */
    @Nullable
    public Texture peek(@NotNull Key key) {
        return this.byKey.get(key);
    }

    /**
     * Adds the given texture to the list of textures.
     *
     * @param key the key of the texture to add
     * @param image the image of the texture to add
     * @param format the format of the image
     * @throws IOException if an error occurs while converting the image to bytes
     */
    public void offer(@NotNull Key key, @NotNull BufferedImage image, @NotNull String format) throws IOException {
        this.offer(Texture.texture(key, Writable.bytes(ImageUtil.toByteArray(image, format))));
    }

    /**
     * Adds the given texture to the list of textures.
     *
     * @param texture the texture to add
     */
    public void offer(@NotNull Texture texture) {
        this.textures.add(texture);
        this.byKey.put(texture.key(), texture);
    }

    /**
     * Returns whether or not the list of textures contains a texture with the given key.
     *
     * @param key the key of the texture to check for
     * @return whether or not the list of textures contains a texture with the given key
     */
    public boolean containsKey(@NotNull Key key) {
        return this.byKey.containsKey(key);
    }

    public void debug(@NotNull String message) {
        this.conversionContext.debug(message);
    }

    public void info(@NotNull String message) {
        this.conversionContext.info(message);
    }

    public void warn(@NotNull String message) {
        this.conversionContext.warn(message);
    }

    public void error(@NotNull String message) {
        this.conversionContext.error(message);
    }

    public void error(@NotNull String message, @NotNull Throwable throwable) {
        this.conversionContext.error(message, throwable);
    }
}
