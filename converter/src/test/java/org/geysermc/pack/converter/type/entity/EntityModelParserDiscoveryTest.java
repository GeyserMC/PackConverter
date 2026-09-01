package org.geysermc.pack.converter.type.entity;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EntityModelParserDiscoveryTest {
    @Test
    void doesNotTreatClientEntityDefinitionsAsGeometryModels() {
        assertFalse(ServiceLoader.load(EntityModelParser.class).stream()
                .anyMatch(provider -> provider.type().getSimpleName().equals("VanillaBedrockEntityParser")));
    }
}
