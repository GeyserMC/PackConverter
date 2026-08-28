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

package org.geysermc.pack.converter.type.entity.javarefl;

import org.geysermc.pack.converter.type.entity.EntityModelParser;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;

/**
 * Stub SPI parser for mods whose entity models are produced at
 * runtime in Java code (Fabric-only GeckoLib / Citadel / Tabula
 * mods) instead of being shipped as files. The default implementation
 * returns {@code null}; mods that want to support their own format
 * are expected to register a custom provider via
 * {@code META-INF/services/...EntityModelParser}.
 *
 * <p>This is the entry point third-party mods should register in
 * their own jar if they want Hydraulic to render their entities on
 * Bedrock. An example adapter for Citadel/Tabula would:</p>
 * <ol>
 *   <li>Load the {@code TabulaModelContainer} via the mod's
 *       reflection-friendly accessor.</li>
 *   <li>Walk the cube list and produce a
 *       {@code ModelEntity} via the Bedrock pack-schema classes.</li>
 *   <li>Return a {@code BedrockModel} wrapped in the right key.</li>
 * </ol>
 */
public final class JavaReflectionEntityParser implements EntityModelParser {

    @Override
    public String id() {
        return "java-reflection";
    }

    @Override
    public String[] supportedExtensions() {
        // No file extension - this parser never picks up files; it only
        // answers when an external provider implements the same SPI
        // and registers a higher-priority match.
        return new String[0];
    }

    @Override
    public BedrockModel parse(String path, ResourcePack pack) {
        return null;
    }
}
