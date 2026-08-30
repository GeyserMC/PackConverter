package org.geysermc.pack.converter.util;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PackOutputSecurityTest {
    @Test
    void rejectsExtraFilesOutsidePackDirectory() {
        BedrockResourcePack pack = new BedrockResourcePack(Path.of("build/test-pack"));
        assertThrows(IllegalArgumentException.class,
                () -> pack.addExtraFile(new byte[0], "../outside.json"));
    }
}
