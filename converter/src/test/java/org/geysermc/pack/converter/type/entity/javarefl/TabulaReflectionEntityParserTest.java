package org.geysermc.pack.converter.type.entity.javarefl;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.type.entity.EntityModelScanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TabulaReflectionEntityParserTest {
    private static final String MODS_DIRECTORY = "hydraulic.mods.dir";
    private static final String MINECRAFT_MERGED = "hydraulic.minecraft.merged";
    private static final String BROKEN_ATTEMPTS = "packconverter.test.broken.attempts";

    @TempDir
    Path tempDir;

    private final String previousModsDirectory = System.getProperty(MODS_DIRECTORY);
    private final String previousMinecraftMerged = System.getProperty(MINECRAFT_MERGED);

    @AfterEach
    void cleanup() {
        restore(MODS_DIRECTORY, previousModsDirectory);
        restore(MINECRAFT_MERGED, previousMinecraftMerged);
        System.clearProperty(BROKEN_ATTEMPTS);
        TabulaReflectionEntityParser.clearCachesForTests();
    }

    @Test
    void reusesOneRuntimeCachesBrokenClassAndContinuesNamespace() throws Exception {
        Path mods = Files.createDirectories(tempDir.resolve("mods"));
        writeModelJar(mods.resolve("example.jar"));
        System.setProperty(MODS_DIRECTORY, mods.toString());
        // Avoid an unrelated Gradle-cache walk while testing this isolated jar.
        System.setProperty(MINECRAFT_MERGED, tempDir.resolve("client.jar").toString());

        EntityModelScanner scanner = EntityModelScanner.discover();
        BedrockResourcePack target = new BedrockResourcePack(tempDir.resolve("output"));
        EntityModelScanner.ScanResult result = scanner.addReflectionEntityModels(null, target,
                List.of("example:good", "example:broken", "example:broken", "example:other"));

        assertEquals(2, result.successCount(), "a broken model must not disable the remaining namespace");
        assertEquals(2, result.diagnostics().size());
        assertTrue(result.diagnostics().stream().allMatch(diagnostic -> diagnostic.path().equals("example:broken")));
        assertTrue(result.diagnostics().getFirst().detail().contains("class=example.client.model.ModelBroken"));
        assertTrue(result.diagnostics().getFirst().detail().contains("reason=IncompatibleClassChangeError: broken runtime link"));
        assertEquals("1", System.getProperty(BROKEN_ATTEMPTS), "the failed constructor must be negative-cached");
        assertEquals(new TabulaReflectionEntityParser.CacheState(1, 1), TabulaReflectionEntityParser.cacheStateForTests());
        assertNotNull(target.entityModels().get("models/entity/example.good.json"));
        assertNotNull(target.entityModels().get("models/entity/example.other.json"));
    }

    private void writeModelJar(Path jar) throws IOException {
        Path source = Files.createDirectories(tempDir.resolve("source/example/client/model"));
        Path classes = Files.createDirectories(tempDir.resolve("classes"));
        Files.writeString(source.resolve("ModelGood.java"), """
                package example.client.model;
                import java.util.List;
                public class ModelGood {
                    private final Part body = new Part();
                    public static final class Part {
                        public final List<Box> cubeList = List.of(new Box());
                        public float defaultPositionX = 1, defaultPositionY = 2, defaultPositionZ = 3;
                        public float defaultRotationX, defaultRotationY, defaultRotationZ;
                        public float textureWidth = 64, textureHeight = 32;
                    }
                    public static final class Box {
                        public float posX1, posY1, posZ1, posX2 = 2, posY2 = 3, posZ2 = 4;
                    }
                }
                """);
        Files.writeString(source.resolve("ModelOther.java"), """
                package example.client.model;
                public final class ModelOther extends ModelGood {}
                """);
        Files.writeString(source.resolve("ModelBroken.java"), """
                package example.client.model;
                public final class ModelBroken {
                    public ModelBroken() {
                        int attempts = Integer.parseInt(System.getProperty("packconverter.test.broken.attempts", "0"));
                        System.setProperty("packconverter.test.broken.attempts", Integer.toString(attempts + 1));
                        throw new IncompatibleClassChangeError("broken runtime link");
                    }
                }
                """);

        assertEquals(0, ToolProvider.getSystemJavaCompiler().run(null, null, null, "-d", classes.toString(),
                source.resolve("ModelGood.java").toString(), source.resolve("ModelOther.java").toString(),
                source.resolve("ModelBroken.java").toString()));

        try (ZipOutputStream output = new ZipOutputStream(Files.newOutputStream(jar));
             var files = Files.walk(classes)) {
            for (Path file : files.filter(Files::isRegularFile).toList()) {
                output.putNextEntry(new ZipEntry(classes.relativize(file).toString().replace('\\', '/')));
                Files.copy(file, output);
                output.closeEntry();
            }
        }
    }

    private static void restore(String name, String value) {
        if (value == null) System.clearProperty(name);
        else System.setProperty(name, value);
    }
}
