package org.geysermc.pack.converter.type.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityModelScannerTest {
    @Test
    void runtimeModelParserIsAvailableWithoutProcessConfiguration() {
        assertTrue(EntityModelScanner.discover().parsers().stream()
                .anyMatch(parser -> parser.id().equals("tabula-reflection")));
    }
}
