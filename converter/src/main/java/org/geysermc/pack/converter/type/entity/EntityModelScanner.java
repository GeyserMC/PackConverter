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

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Scans every {@link EntityModelParser} via {@link ServiceLoader} and
 * runs them over a single {@link ResourcePack}. The first parser to
 * successfully convert a file wins; duplicates are warned.
 *
     * <p>Use {@link #addEntityModels(ResourcePack, BedrockResourcePack)} from
     * a Bedrock pack module to apply all discovered parsers to a
     * single pack.</p>
 */
public final class EntityModelScanner {

    private final List<EntityModelParser> parsers;

    private EntityModelScanner(List<EntityModelParser> parsers) {
        this.parsers = parsers;
    }

    public static EntityModelScanner discover() {
        List<EntityModelParser> found = new ArrayList<>();
        ServiceLoader<EntityModelParser> loader = ServiceLoader.load(EntityModelParser.class);
        for (EntityModelParser p : loader) {
            try {
                found.add(p);
            } catch (ServiceConfigurationError e) {
                // A misbehaving provider - skip but don't fail the whole scan.
            }
        }
        return new EntityModelScanner(found);
    }

    /**
     * Iterate every unknown file in the pack, try each parser in
     * registration order, and write the first successful conversion
     * into {@code pack.entityModels()}. Files not consumed by any
     * parser are silently ignored - other pipelines (textures,
     * sounds) may still handle them.
     */
    public ScanResult addEntityModels(ResourcePack source, BedrockResourcePack target) {
        ScanResult result = new ScanResult();
        if (parsers.isEmpty()) {
            return result;
        }
        for (Map.Entry<String, Writable> entry : source.unknownFiles().entrySet()) {
            String path = entry.getKey();
            for (EntityModelParser parser : parsers) {
                if (!parser.acceptsPath(path)) continue;
                BedrockModel model;
                try {
                    model = parser.parse(path, source);
                } catch (RuntimeException e) {
                    result.recordFailure(parser.id(), path, e);
                    continue;
                }
                if (model == null) continue;
                String packKey = "models/entity/" + model.fileName();
                if (target.entityModels() != null && target.entityModels().containsKey(packKey)) {
                    result.recordDuplicate(parser.id(), model.fileName());
                    continue;
                }
                target.addEntityModel(model.model(), model.fileName());
                result.recordSuccess(parser.id(), model.fileName());
                break;
            }
        }
        return result;
    }

    public List<EntityModelParser> parsers() {
        return parsers;
    }

    /** Aggregated outcome of one scan, exposed for logging and tests. */
    public static final class ScanResult {
        private final Map<String, Integer> successByParser = new HashMap<>();
        private final Map<String, Integer> failureByParser = new HashMap<>();
        private final List<String> duplicates = new ArrayList<>();
        private final List<String> failures = new ArrayList<>();

        void recordSuccess(String parserId, String key) {
            successByParser.merge(parserId, 1, Integer::sum);
        }

        void recordDuplicate(String parserId, String key) {
            duplicates.add(parserId + " -> " + key);
        }

        void recordFailure(String parserId, String path, Throwable e) {
            failureByParser.merge(parserId, 1, Integer::sum);
            failures.add(parserId + " -> " + path + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
        }

        public int successCount() {
            return successByParser.values().stream().mapToInt(Integer::intValue).sum();
        }

        public int failureCount() {
            return failureByParser.values().stream().mapToInt(Integer::intValue).sum();
        }

        public List<String> duplicates() {
            return duplicates;
        }

        public List<String> failures() {
            return failures;
        }
    }
}
