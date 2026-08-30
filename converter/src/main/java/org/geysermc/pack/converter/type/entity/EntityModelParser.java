/*
 * Copyright (c) 2019-2026 GeyserMC. http://geysermc.org
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
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR THE OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 *  @author GeyserMC
 *  @link https://github.com/GeyserMC/PackConverter
 *
 */

package org.geysermc.pack.converter.type.entity;

import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;

/**
 * Single entry point every entity model parser must satisfy. One
 * concrete implementation per file format: GeckoLib, Blockbench,
 * Tabula, OBJ/Wavefront, vanilla Bedrock entity JSON pass-through, or
 * Java-reflection based providers (for mods whose models are generated
 * at runtime and cannot live on disk).
 *
 * <p>Implementations are discovered via {@link java.util.ServiceLoader}
 * using the fully-qualified class name
 * {@code org.geysermc.pack.converter.type.entity.EntityModelParser}
 * in {@code META-INF/services/...} inside the converter jar. Built-in
 * parsers ship in that file; third-party parsers are added the same
 * way by anyone (typically a mod whose models need custom handling).</p>
 *
 * <p>All parsers share the same output shape: a
 * {@link BedrockModel} with {@code modelType == ENTITY}. The
 * downstream pipeline then writes the model file and registers it
 * with Geyser / packs the resource the same way regardless of the
 * source format.</p>
 */
public interface EntityModelParser {

    /**
     * Stable identifier used in logs and to deduplicate registrations.
     * Should match the parser's format - e.g. {@code "geckolib"},
     * {@code "blockbench"}, {@code "tabula"}, {@code "obj"},
     * {@code "vanilla-bedrock"}, {@code "java-reflection"}.
     */
    String id();

    /**
     * File extensions the parser can read, lowercased and including the
     * leading dot, e.g. {@code ".geo.json"}, {@code ".bbmodel"},
     * {@code ".tbl"}, {@code ".obj"}, {@code ".entity.json"}.
     */
    String[] supportedExtensions();

    /**
     * Whether this parser can produce a geometry for a given raw file
     * path - typically a simple suffix check against
     * {@link #supportedExtensions()}, but specific parsers can
     * inspect the path further (e.g. only under {@code assets/<ns>/models/}).
     */
    default boolean acceptsPath(String path) {
        String lower = path.toLowerCase();
        for (String ext : supportedExtensions()) {
            if (lower.endsWith(ext)) return true;
        }
        return false;
    }

    /**
     * Parse the file and produce a Bedrock entity model. Returning
     * {@code null} signals the parser rejected this file (e.g. it
     * matched the extension but the content was not a model in this
     * format); other parsers will still be tried.
     */
    BedrockModel parse(String path, ResourcePack pack);

    /** Parses a runtime model using the conversion host's exact classpath. */
    default BedrockModel parse(String path, ResourcePack pack, ReflectionInput input) {
        return parse(path, pack);
    }

    /**
     * Explains why the most recent {@link #parse(String, ResourcePack)} call
     * declined a path after reaching a supported source. Returning {@code null}
     * keeps the normal silent-rejection behavior for formats that simply do not
     * match the input.
     */
    default String failureDetail(String path) {
        return null;
    }
}
