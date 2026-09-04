package org.geysermc.pack.converter.pipeline;

import org.geysermc.pack.converter.util.LogListener;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that the {@link CombineContext} error override actually increments
 * the shared counter. Without this contract, a buggy combiner (e.g. one that
 * only logs and never throws) would let the pipeline report "successfully
 * converted" even though assets silently went missing — a regression
 * observed in a live server log on 2026-09-03.
 */
class CombineContextErrorCounterTest {
    @Test
    void errorWithoutThrowableIncrementsCounter() {
        AtomicInteger counter = new AtomicInteger();
        RecordingListener listener = new RecordingListener();
        CombineContext context = new CombineContext("test", listener, counter);

        context.error("first failure");
        context.error("second failure");

        assertEquals(2, counter.get(),
                "Every context.error(String) must increment the shared counter");
        assertEquals(List.of("first failure", "second failure"), listener.errors);
    }

    @Test
    void errorWithThrowableAlsoIncrementsCounter() {
        AtomicInteger counter = new AtomicInteger();
        RecordingListener listener = new RecordingListener();
        CombineContext context = new CombineContext("test", listener, counter);

        context.error("with throwable", new RuntimeException("boom"));

        assertEquals(1, counter.get(),
                "Every context.error(String, Throwable) must increment the shared counter");
        assertEquals(1, listener.errorsWithThrowable.size());
        assertEquals("with throwable", listener.errorsWithThrowable.get(0).message);
    }

    @Test
    void backwardsCompatibleConstructorIsolatesCounter() {
        RecordingListener listener = new RecordingListener();
        CombineContext context = new CombineContext("test", listener);

        context.error("isolated failure");

        // The default counter must exist and be incrementable, but the caller
        // has no way to read it — that is the entire point of the legacy form.
        // We only assert that no NPE escapes and the listener is invoked.
        assertEquals(List.of("isolated failure"), listener.errors);
    }

    @Test
    void warnAndInfoDoNotIncrementErrorCounter() {
        AtomicInteger counter = new AtomicInteger();
        RecordingListener listener = new RecordingListener();
        CombineContext context = new CombineContext("test", listener, counter);

        context.warn("warning only");
        context.info("informational");

        assertEquals(0, counter.get(),
                "Only context.error must increment; warn/info are not failures");
    }

    /** Minimal in-memory LogListener so the test does not need Mockito. */
    private static final class RecordingListener implements LogListener {
        final List<String> errors = new ArrayList<>();
        final List<String> warns = new ArrayList<>();
        final List<String> infos = new ArrayList<>();
        final List<String> debugs = new ArrayList<>();
        final List<ThrowableRecord> errorsWithThrowable = new ArrayList<>();

        @Override public void debugUnchecked(String message) { debugs.add(message); }
        @Override public void error(String message) { errors.add(message); }
        @Override public void error(String message, Throwable exception) {
            errors.add(message);
            errorsWithThrowable.add(new ThrowableRecord(message, exception));
        }
        @Override public void warn(String message) { warns.add(message); }
        @Override public void info(String message) { infos.add(message); }

        record ThrowableRecord(String message, Throwable exception) {}
    }
}