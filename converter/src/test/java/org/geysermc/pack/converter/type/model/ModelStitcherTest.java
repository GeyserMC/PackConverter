package org.geysermc.pack.converter.type.model;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.util.LogListener;
import org.junit.jupiter.api.Test;
import team.unnamed.creative.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelStitcherTest {
    @Test
    void stopsParentCycles() {
        Key firstKey = Key.key("example:first");
        Key secondKey = Key.key("example:second");
        Model first = Model.model().key(firstKey).parent(secondKey).build();
        Model second = Model.model().key(secondKey).parent(firstKey).build();
        RecordingLog log = new RecordingLog();

        assertDoesNotThrow(() -> new ModelStitcher(Map.of(firstKey, first, secondKey, second)::get, first, log).stitch());
        assertTrue(log.errors.stream().anyMatch(message -> message.contains("cycle")));
    }

    private static final class RecordingLog implements LogListener {
        private final List<String> errors = new ArrayList<>();

        @Override public void debugUnchecked(String message) { }
        @Override public void info(String message) { }
        @Override public void warn(String message) { }
        @Override public void error(String message) { errors.add(message); }
        @Override public void error(String message, Throwable exception) { errors.add(message); }
    }
}
