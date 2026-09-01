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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.geysermc.pack.converter.type.model.BedrockModel;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke test: extract geometry from a real alexsmobs jar to verify
 * the reflection path works end-to-end. Reads the mod jar path from
 * {@code -Dtabula.modjar=...} or {@code TABULA_MODJAR} env var.
 * Skipped if neither is set.
 */
class TabulaReflectionSmoke {

    @Test
    void extractLaviathanFromAlexsmobs() throws Exception {
        String prop = System.getProperty("tabula.modjar", System.getenv("TABULA_MODJAR"));
        Assumptions.assumeTrue(prop != null && !prop.isEmpty(),
                "Skipped: set -Dtabula.modjar=/path/to/alexsmobs.jar to enable");
        Path source = Path.of(prop);
        Assumptions.assumeTrue(Files.isRegularFile(source), "mod jar not found: " + source);

        // Stage the jar into a fresh temp directory so the parser's
        // locateModJar() can find it via the hydraulic.mods.dir hint.
        Path tmpMods = Files.createTempDirectory("tabula-smoke-mods-");
        Path staged = tmpMods.resolve("alexsmobs-2.1.6-fabric_26.2.jar");
        Files.copy(source, staged, StandardCopyOption.REPLACE_EXISTING);
        System.setProperty("hydraulic.mods.dir", tmpMods.toString());

        TabulaReflectionEntityParser parser = new TabulaReflectionEntityParser();
        BedrockModel model = parser.parse("alexsmobs:laviathan.reflection", null);
        assertNotNull(model, "parser returned null; reflection path failed");
        assertTrue(model.fileName().endsWith(".json"),
                "expected .json output, got " + model.fileName());

        // Inspect the dumped JSON to confirm cube data survived the
        // reflection + transform pipeline.
        String json = new Gson().toJson(model.model());
        JsonObject root = new Gson().fromJson(json, JsonObject.class);
        JsonObject description = root.getAsJsonObject("minecraft:client_entity")
                .getAsJsonObject("description");
        assertNotNull(description, "client_entity description missing");
        assertTrue(description.get("identifier").getAsString().startsWith("geometry.alexsmobs.laviathan"),
                "identifier malformed: " + description);
        var geometryArr = description.has("geometry")
                ? root.getAsJsonArray("minecraft:geometry") : null;
        assertNotNull(geometryArr);
        assertTrue(!geometryArr.isEmpty(), "geometry array empty");
        var cubes = geometryArr.get(0).getAsJsonObject()
                .getAsJsonArray("bones").get(0).getAsJsonObject()
                .getAsJsonArray("cubes");
        System.out.println("Extracted " + cubes.size() + " cubes for laviathan");
        assertTrue(cubes.size() > 0, "no cubes dumped");
    }
}
