package org.geysermc.pack.converter.type.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityModelScannerTest {
    @Test
    void reflectionParserRequiresExplicitOptIn() {
        String property = EntityModelScanner.ENABLE_REFLECTION_PARSER_PROPERTY;
        String previous = System.getProperty(property);
        try {
            System.clearProperty(property);
            assertFalse(EntityModelScanner.discover().parsers().stream()
                    .anyMatch(parser -> parser.id().equals("tabula-reflection")));

            System.setProperty(property, "true");
            assertTrue(EntityModelScanner.discover().parsers().stream()
                    .anyMatch(parser -> parser.id().equals("tabula-reflection")));
        } finally {
            if (previous == null) {
                System.clearProperty(property);
            } else {
                System.setProperty(property, previous);
            }
        }
    }
}
