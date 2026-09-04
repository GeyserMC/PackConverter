/*
 * Copyright (c) 2025-2025 GeyserMC. http://geysermc.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.pipeline;

import org.geysermc.pack.converter.util.LogListener;
import org.geysermc.pack.converter.util.LogListenerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * State shared with an asset combiner while it is materialising Bedrock assets.
 *
 * <p>The {@code errorCounter} lets the combiner report failures (including
 * {@code context.error(...)} calls inside the combine loop) so the
 * surrounding {@link ConverterPipeline#convert} can return a count
 * that the caller treats as the success/failure signal. Without this
 * counter, exceptions thrown by the combine side are silently swallowed
 * and the pipeline reports "successfully converted" even though some
 * assets did not make it onto disk — a bug observed in the wild when
 * 7 texture-IO errors all turned into 0 increment in {@code errors}
 * because the catching site was inside {@code include()} itself.</p>
 *
 * <p>The {@link #error(String)} override is the entire point of the counter
 * being on the context: every {@code context.error(...)} call inside the
 * combine loop automatically increments it, so the pipeline no longer has
 * to refactor each AssetCombiner to thread an error count through its
 * return value.</p>
 */
public record CombineContext(String textureSubDirectory, LogListener logListener, AtomicInteger errorCounter) implements LogListenerHelper {

    /**
     * Backwards-compatible constructor for callers that do not care about
     * the error counter. The counter is a fresh {@link AtomicInteger} so the
     * resulting context is still safe to share with this implementation —
     * it just cannot be observed from the outside.
     */
    public CombineContext(String textureSubDirectory, LogListener logListener) {
        this(textureSubDirectory, logListener, new AtomicInteger());
    }

    @Override
    public void error(@NotNull String message) {
        errorCounter.incrementAndGet();
        logListener.error(message);
    }

    @Override
    public void error(@NotNull String message, @NotNull Throwable exception) {
        errorCounter.incrementAndGet();
        logListener.error(message, exception);
    }
}
