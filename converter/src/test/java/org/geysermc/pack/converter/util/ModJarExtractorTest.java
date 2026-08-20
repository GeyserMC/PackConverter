package org.geysermc.pack.converter.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModJarExtractorTest {
    @Test
    void extractsOnlyResourcePackEntries() throws Exception {
        Path root = Files.createTempDirectory("packconverter-extractor-");
        try {
            Path jar = root.resolve("example.jar");
            Path output = root.resolve("out");
            writeJar(jar,
                    "assets/example/models/item/test.json", "{\"parent\":\"item/generated\"}",
                    "assets/example/textures/item/test.png", "not-a-real-png",
                    "META-INF/mods.toml", "metadata");

            Path extracted = ModJarExtractor.extract(jar, output);

            assertEquals(output.toAbsolutePath().normalize(), extracted);
            assertTrue(Files.exists(output.resolve("assets/example/models/item/test.json")));
            assertTrue(Files.exists(output.resolve("assets/example/textures/item/test.png")));
            assertTrue(Files.notExists(output.resolve("META-INF/mods.toml")));
        } finally {
            deleteTree(root);
        }
    }

    @Test
    void reportsCollisionsAndUsesDeterministicLaterOverride() throws Exception {
        Path root = Files.createTempDirectory("packconverter-collision-");
        try {
            Path mods = Files.createDirectory(root.resolve("mods"));
            Path output = root.resolve("out");
            writeJar(mods.resolve("01-first.jar"), "assets/test/models/item/shared.json", "first");
            writeJar(mods.resolve("02-second.jar"), "assets/test/models/item/shared.json", "second");

            ModJarExtractor.ExtractionReport report = ModJarExtractor.extractAll(mods, output);

            assertEquals(2, report.filesExtracted());
            assertEquals(2, report.mods().size());
            assertEquals(1, report.collisions().size());
            assertEquals("second", Files.readString(output.resolve("assets/test/models/item/shared.json")));
        } finally {
            deleteTree(root);
        }
    }

    @Test
    void rejectsZipSlipEntries() throws Exception {
        Path root = Files.createTempDirectory("packconverter-zipslip-");
        try {
            Path jar = root.resolve("malicious.jar");
            Path output = root.resolve("out");
            writeJar(jar, "assets/../../outside.txt", "blocked");

            assertThrows(IOException.class, () -> ModJarExtractor.extract(jar, output));
            assertTrue(Files.notExists(root.resolve("outside.txt")));
        } finally {
            deleteTree(root);
        }
    }

    private static void writeJar(Path jar, String... entries) throws IOException {
        try (OutputStream output = Files.newOutputStream(jar); ZipOutputStream zip = new ZipOutputStream(output)) {
            for (int i = 0; i < entries.length; i += 2) {
                zip.putNextEntry(new ZipEntry(entries[i]));
                zip.write(entries[i + 1].getBytes());
                zip.closeEntry();
            }
        }
    }

    private static void deleteTree(Path root) throws IOException {
        if (!Files.exists(root)) return;
        try (var paths = Files.walk(root)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            });
        }
    }
}
