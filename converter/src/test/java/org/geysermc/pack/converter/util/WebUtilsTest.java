package org.geysermc.pack.converter.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WebUtilsTest {
    @Test
    void rejectsUntrustedDownloadSchemesBeforeConnecting() {
        assertThrows(IOException.class, () -> WebUtils.getBody("http://example.invalid/manifest.json"));
        assertThrows(IOException.class, () -> WebUtils.downloadToFile("file:///tmp/client.jar", Path.of("client.jar")));
    }
}
