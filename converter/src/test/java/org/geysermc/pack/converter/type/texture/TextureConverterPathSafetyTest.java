package org.geysermc.pack.converter.type.texture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test for the path-traversal bug found in a live server log on 2026-09-03:
 * {@code viaversion:logo.png} (a texture whose path contains no "/" character) was
 * resolved by {@code Path#resolve(String)} as an absolute path, making
 * {@code Files.createDirectories} attempt to create {@code /viaversion} at the
 * host filesystem root, then silently swallowed by a {@code catch (IOException)}
 * inside the converter. The pack was reported as "successfully converted" but
 * contained no Bedrock assets.
 *
 * <p>The test exercises the static helper {@link TextureConverter#resolveSafeRelative}
 * directly so it does not require any converter scaffolding.</p>
 */
class TextureConverterPathSafetyTest {
    @Test
    void rejectsAbsoluteUnixPath(@TempDir Path tmp) {
        IOException exception = assertThrows(IOException.class,
                () -> TextureConverter.resolveSafeRelative(tmp, "/viaversion/logo.png"));
        assertTrue(exception.getMessage().toLowerCase().contains("absolute"),
                "Error must mention 'absolute', got: " + exception.getMessage());
    }

    @Test
    void rejectsParentTraversalThatEscapesRoot(@TempDir Path tmp) {
        IOException exception = assertThrows(IOException.class,
                () -> TextureConverter.resolveSafeRelative(tmp, "../etc/passwd"));
        assertTrue(exception.getMessage().toLowerCase().contains("escapes"),
                "Error must mention 'escapes', got: " + exception.getMessage());
    }

    @Test
    void rejectsSingleDotDotThatEscapesAfterNormalisation(@TempDir Path tmp) {
        Path textures = tmp.resolve("textures");
        // The "resolved" path after normalisation would land at the test tmp dir
        // (one level up from "textures"), which is outside the texturePath root.
        IOException exception = assertThrows(IOException.class,
                () -> TextureConverter.resolveSafeRelative(textures, ".."));
        assertTrue(exception.getMessage().toLowerCase().contains("escapes"));
    }

    @Test
    void acceptsNormalRelativePath(@TempDir Path tmp) throws IOException {
        Path textures = tmp.resolve("textures");
        Path resolved = TextureConverter.resolveSafeRelative(textures, "blocks/stone.png");
        assertEquals(textures.resolve("blocks/stone.png"), resolved);
    }

    @Test
    void acceptsNestedRelativePath(@TempDir Path tmp) throws IOException {
        Path textures = tmp.resolve("textures");
        Path resolved = TextureConverter.resolveSafeRelative(textures,
                "misc/viabackwards/logo.png");
        assertEquals(textures.resolve("misc/viabackwards/logo.png"), resolved);
    }
}