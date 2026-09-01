package org.geysermc.pack.converter.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VanillaPackProviderTest {
    @TempDir Path temporaryDirectory;

    @Test
    void rejectsCorruptAndIncompleteVanillaCaches() throws Exception {
        Path corrupt = temporaryDirectory.resolve("corrupt.zip");
        Files.writeString(corrupt, "not a zip");
        assertFalse(VanillaPackProvider.validArchive(corrupt, "assets/minecraft/models/"));

        Path complete = temporaryDirectory.resolve("complete.zip");
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(complete))) {
            zip.putNextEntry(new ZipEntry("assets/minecraft/models/block/cube.json"));
            zip.write("{}".getBytes(java.nio.charset.StandardCharsets.UTF_8));
            zip.closeEntry();
        }
        assertTrue(VanillaPackProvider.validArchive(complete, "assets/minecraft/models/"));
        assertFalse(VanillaPackProvider.validArchive(complete, "missing/"));
    }
}
