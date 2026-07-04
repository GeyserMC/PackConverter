/*
 * Copyright (c) 2019-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.type.texture.transformer.TransformedTexture;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextureConverterTest {
    private static final Path TEXTURES = Path.of("pack").resolve("textures");

    // ---- Unit tests for the path computation (GeyserMC/PackConverter#68) --------------------------

    /**
     * Regression test for #68: a top-level texture such as {@code textures/icons.png} (empty root) must not
     * resolve to an absolute filesystem path. Previously the output was {@code "/appleskin/icons.png"}, which
     * {@link Path#resolve} treated as absolute, causing {@code Files.createDirectories("/appleskin")} to throw
     * {@code AccessDeniedException}.
     */
    @Test
    void topLevelTextureWithSubDirectoryStaysInsidePack() {
        Path result = TextureConverter.resolveOutputPath(TEXTURES, "/icons.png", "appleskin");
        assertTrue(result.startsWith(TEXTURES), "must stay under the pack textures dir, was: " + result);
        assertEquals(TEXTURES.resolve(Path.of("appleskin", "icons.png")), result);
    }

    @Test
    void topLevelTextureWithoutSubDirectoryStaysInsidePack() {
        Path result = TextureConverter.resolveOutputPath(TEXTURES, "/icons.png", null);
        assertTrue(result.startsWith(TEXTURES), "must stay under the pack textures dir, was: " + result);
        assertEquals(TEXTURES.resolve("icons.png"), result);
    }

    @Test
    void outputPathWithNoSlashStaysInsidePack() {
        Path result = TextureConverter.resolveOutputPath(TEXTURES, "icons.png", "appleskin");
        assertTrue(result.startsWith(TEXTURES), "must stay under the pack textures dir, was: " + result);
        assertEquals(TEXTURES.resolve(Path.of("appleskin", "icons.png")), result);
    }

    @Test
    void normalBlockTextureIsUnchanged() {
        Path result = TextureConverter.resolveOutputPath(TEXTURES, "blocks/foo.png", "mcwwindows");
        assertEquals(TEXTURES.resolve(Path.of("blocks", "mcwwindows", "foo.png")), result);
    }

    @Test
    void normalBlockTextureWithoutSubDirectoryIsUnchanged() {
        Path result = TextureConverter.resolveOutputPath(TEXTURES, "blocks/foo.png", null);
        assertEquals(TEXTURES.resolve(Path.of("blocks", "foo.png")), result);
    }

    // ---- End-to-end: run the full include() flow for the #68 scenario ----------------------------

    /**
     * Drives the real {@link TextureConverter#include} path for a top-level texture (the AppleSkin
     * {@code icons.png} case). Before the fix this threw {@code AccessDeniedException: /appleskin}; now the
     * PNG must be written inside the pack and no error must be reported.
     */
    @Test
    void includeWritesTopLevelTextureInsidePack(@TempDir Path packDir) throws Exception {
        BedrockResourcePack pack = new BedrockResourcePack(packDir);

        byte[] png = ImageUtil.toByteArray(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), "png");
        Texture texture = Texture.texture(Key.key("appleskin", "icons.png"), Writable.bytes(png));
        TransformedTexture transformed = new TransformedTexture(texture);
        transformed.output(List.of("/icons.png")); // top-level texture, empty root — the #68 trigger

        List<String> errors = new ArrayList<>();
        CombineContext context = new CombineContext("appleskin", new LogListener() {
            @Override public void debugUnchecked(@NotNull String m) { }
            @Override public void info(@NotNull String m) { }
            @Override public void warn(@NotNull String m) { }
            @Override public void error(@NotNull String m) { errors.add(m); }
            @Override public void error(@NotNull String m, @Nullable Throwable t) { errors.add(m); }
        });

        TextureConverter.INSTANCE.include(pack, List.of(transformed), context);

        Path written = packDir.resolve("textures").resolve("appleskin").resolve("icons.png");
        assertTrue(Files.exists(written), "texture should be written inside the pack at " + written);
        assertTrue(errors.isEmpty(), "no errors expected, got: " + errors);
    }
}
