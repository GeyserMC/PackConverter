package org.geysermc.pack.converter.type.entity.javarefl;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
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

        List<Bones> bones = target.entityModels().get("models/entity/example.good.json").geometry().getFirst().bones();
        Bones body = bones.stream().filter(bone -> bone.name().equals("body")).findFirst().orElseThrow();
        Bones tail = bones.stream().filter(bone -> bone.name().equals("tail")).findFirst().orElseThrow();
        assertEquals(2, bones.size());
        assertEquals(null, body.parent());
        assertEquals("body", tail.parent());
        assertEquals(90f, body.rotation()[0], 0.001f, "Java radians must be converted to Bedrock degrees");
        assertEquals(45f, tail.rotation()[1], 0.001f, "child rest rotation must remain on its bone");
        assertEquals(4f, tail.cubes().getFirst().size()[0], 0.001f, "part X scale must be baked into cube size");
        assertEquals(2f, tail.cubes().getFirst().size()[1], 0.001f, "part Y scale must be baked into cube size");
        assertEquals(9f, tail.cubes().getFirst().size()[2], 0.001f, "part Z scale must be baked into cube size");
    }

    private void writeModelJar(Path jar) throws IOException {
        Path source = Files.createDirectories(tempDir.resolve("source/example/client/model"));
        Path classes = Files.createDirectories(tempDir.resolve("classes"));
        Files.writeString(source.resolve("ModelGood.java"), """
                package example.client.model;
                import java.util.ArrayList;
                import java.util.List;
                public class ModelGood {
                    private final Part body = new Part(1, 2, 3, (float) (Math.PI / 2), 0, 0, 1, 1, 1);
                    private final Part tail = new Part(2, 3, 4, 0, (float) (Math.PI / 4), 0, 2, .5f, 1.5f);
                    public ModelGood() { body.childModels.add(tail); }
                    public static final class Part {
                        public final List<Part> childModels = new ArrayList<>();
                        public final List<Box> cubeList = List.of(new Box());
                        public float defaultPositionX, defaultPositionY, defaultPositionZ;
                        public float defaultRotationX, defaultRotationY, defaultRotationZ;
                        public float scaleX, scaleY, scaleZ;
                        public float textureWidth = 64, textureHeight = 32;
                        Part(float x, float y, float z, float rx, float ry, float rz, float sx, float sy, float sz) {
                            defaultPositionX = x; defaultPositionY = y; defaultPositionZ = z;
                            defaultRotationX = rx; defaultRotationY = ry; defaultRotationZ = rz;
                            scaleX = sx; scaleY = sy; scaleZ = sz;
                        }
                    }
                    public static final class Box {
                        public float posX1, posY1, posZ1, posX2 = 2, posY2 = 4, posZ2 = 6;
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
