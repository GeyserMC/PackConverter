package org.geysermc.pack.converter.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NioDirectoryFileTreeReaderTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void layersRootsWithoutDroppingEarlierAssets() throws Exception {
        Path first = Files.createDirectory(temporaryDirectory.resolve("first"));
        Path second = Files.createDirectory(temporaryDirectory.resolve("second"));
        write(first, "assets/example/shared.txt", "first");
        write(first, "assets/example/first.txt", "one");
        write(second, "assets/example/shared.txt", "second");
        write(second, "assets/example/second.txt", "two");

        Map<String, String> files = new LinkedHashMap<>();
        try (var reader = NioDirectoryFileTreeReader.read(List.of(first, second))) {
            while (reader.hasNext()) {
                String name = reader.next();
                files.put(name, new String(reader.stream().readAllBytes(), StandardCharsets.UTF_8));
            }
        }

        assertEquals("first", files.get("assets/example/shared.txt"));
        assertEquals("one", files.get("assets/example/first.txt"));
        assertEquals("two", files.get("assets/example/second.txt"));
        assertEquals(3, files.size());
    }

    private static void write(Path root, String name, String contents) throws Exception {
        Path file = root.resolve(name);
        Files.createDirectories(file.getParent());
        Files.writeString(file, contents);
    }
}
