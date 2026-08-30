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

import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.converter.type.entity.gecko.BoxUvMapper;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.converter.type.entity.EntityModelParser;
import org.geysermc.pack.converter.type.model.BedrockModel;
import team.unnamed.creative.ResourcePack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reads entity geometry from a Fabric mod jar at build time, using
 * reflection. MOD-AGNOSTIC — the parser is a generic mod-to-Bedrock
 * bridge, not a feature for any specific mod.
 *
 * <p>It targets the Citadel/Tabula model framework, which is
 * popular in many Fabric mods and exposes each model as a
 * {@code com.<author>.<namespace>.client.model.Model<EntityName>}
 * class. The parser scans the mod jar's index for any class whose
 * simple name matches {@code Model<Pascal>} or {@code <Pascal>Model}
 * and walks its declared {@code AdvancedModelBox} fields. No
 * namespace, mod name, or framework tag is hardcoded — any mod
 * that follows the same convention works.</p>
 *
 * <p>For mods that ship no model files on disk (Citadel, GeckoLib
 * runtime-generated, etc.) the file-based parsers can never
 * produce output. This parser loads the mod jar with a dedicated
 * {@link URLClassLoader}, instantiates the entity's model class by
 * name, and walks the resulting cube data structure to produce a
 * Bedrock geometry JSON.</p>
 *
 * <p>Activation is file-path based: the scanner passes a synthetic
 * path of the form {@code <entity-id>.reflection} when it wants
 * this parser to try generating a model for an entity that has no
 * static file. Conventional {@code .tbl} paths are also accepted
 * for completeness.</p>
 *
 * <p><b>Note:</b> Citadel uses the classes
 * {@code BasicModelPart} (with final fields {@code posX1/posY1/posZ1}
 * /{@code posX2/posY2/posZ2} for the AABB of each render box) and
 * {@code AdvancedEntityModel} (which holds a per-bone texture size).
 * Reflection here reads those fields directly. Any mod that
 * subclasses this hierarchy with the same field shape will work
 * without changes.</p>
 */
public final class TabulaReflectionEntityParser implements EntityModelParser {

    private static final String[] EXTS = {".tbl", ".reflection"};
    private static final Set<String> REPORTED_CLASSPATHS = ConcurrentHashMap.newKeySet();
    private static final Map<Path, Map<String, String>> MODEL_CLASS_INDEX = new ConcurrentHashMap<>();
    private static final Map<Path, ReflectionRuntime> RUNTIMES = new ConcurrentHashMap<>();
    private static final Map<Path, Map<String, String>> FAILED_MODEL_CLASSES = new ConcurrentHashMap<>();

    private final Map<String, String> failureDetails = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "tabula-reflection";
    }

    @Override
    public String[] supportedExtensions() {
        return EXTS;
    }

    @Override
    public BedrockModel parse(String path, ResourcePack pack) {
        ParsedEntityRef ref = ParsedEntityRef.from(path);
        if (ref == null) return null;
        failureDetails.remove(path);
        Path modJar = locateModJar(ref.namespace, pack);
        if (modJar == null) {
            // No mod jar available - the scanner will fall back to the
            // next parser or vanilla Bedrock geometry.
            return null;
        }

        try {
            Path cacheKey = modJar.toAbsolutePath().normalize();
            // Do not inherit Fabric's transforming classloader: on a dedicated
            // server it deliberately rejects client-only classes before we can
            // inspect them. One runtime is retained per immutable mod JAR for
            // the server lifetime, so every entity does not rescan disk or link
            // a new copy of the same dependency graph.
            ReflectionRuntime runtime = RUNTIMES.computeIfAbsent(cacheKey, TabulaReflectionEntityParser::openRuntime);
            ModelLoadResult loaded = loadModelFromMod(runtime.loader(), cacheKey, ref.entityName);
            if (loaded.failure() != null) {
                recordFailure(path, ref, loaded.failure());
            }
            if (loaded.data() == null) return null;
            return buildBedrockModel(ref.namespace, ref.entityName, loaded.data());
        } catch (Throwable t) {
            recordFailure(path, ref, new ModelLoadFailure("<classpath>", reason(t)));
            return null;
        }
    }

    @Override
    public String failureDetail(String path) {
        return failureDetails.get(path);
    }

    private static ReflectionRuntime openRuntime(Path modJar) {
        try {
            URL[] urls = collectClasspathUrls(modJar);
            return new ReflectionRuntime(new URLClassLoader("tabula-reflect-" + modJar.getFileName(), urls, null));
        } catch (java.net.MalformedURLException exception) {
            throw new IllegalStateException("Could not build reflection classpath for " + modJar, exception);
        }
    }

    private void recordFailure(String path, ParsedEntityRef ref, ModelLoadFailure failure) {
        String detail = "namespace=" + ref.namespace + ", entity=" + ref.entityName
                + ", class=" + failure.modelClass + ", reason=" + failure.reason;
        failureDetails.put(path, detail);
        System.err.println("TabulaReflection fallback: " + detail);
    }

    /**
     * Compose the classpath the mod's model class needs to link.
     * The mod jar is always present; we layer any Mojang client jar
     * we can find on top of it so the Citadel/AdvancedEntityModel
     * types resolve. The launcher (Hydraulic) sets a system property
     * pointing at the merged client jar; failing that we walk the
     * local Gradle cache (convenient for tests and CLI runs).
     */
    private static URL[] collectClasspathUrls(Path modJar) throws java.net.MalformedURLException {
        java.util.List<URL> urls = new java.util.ArrayList<>();

        // Mojang mapped client: required for the mod to link against
        // vanilla classes. Resolve in this order:
        //   1. System property (Hydraulic sets this for builds)
        //   2. Project-local libs/mojang-client-<ver>.jar (test fixture)
        //   3. Walk Gradle cache for fabric-loom's mapped jar
        // The deobf jar in minecraftMaven is intentionally excluded
        // because it duplicates classes with mismatched method
        // signatures (Mob.getLootTable is final in 26.2 mapped but
        // not in obfuscated builds).
        String explicit = System.getProperty("hydraulic.minecraft.merged");
        if (explicit != null && !explicit.isEmpty()) {
            urls.add(Path.of(explicit).toUri().toURL());
        } else {
            // Local fixture (added by tests/CLI to keep classpath
            // self-contained).
            Path local = Path.of("libs", "mojang-client-26.2.jar");
            if (Files.isRegularFile(local)) {
                urls.add(local.toAbsolutePath().toUri().toURL());
            }
            // Gradle cache fall-through.
            String userHome = System.getProperty("user.home");
            if (userHome != null) {
                Path[] roots = new Path[]{
                        Path.of(userHome, ".gradle", "caches", "modules-2", "files-2.1"),
                        Path.of(userHome, ".gradle", "caches", "fabric-loom"),
                };
                for (Path root : roots) {
                    if (!Files.isDirectory(root)) continue;
                    try (java.util.stream.Stream<Path> walk = Files.walk(root, 8)) {
                        walk.filter(p -> p.toString().endsWith(".jar"))
                                .filter(p -> !p.toString().contains("minecraftMaven"))
                                .forEach(p -> {
                                    try {
                                        urls.add(p.toUri().toURL());
                                    } catch (java.net.MalformedURLException e) {
                                        // ignore
                                    }
                                });
                    } catch (java.io.IOException e) {
                        // best-effort
                    }
                }
            }
        }
        // Load installed dependencies too; a model framework may be shipped
        // as a separate mod rather than shaded into the target jar.
        String modsDirectory = System.getProperty("hydraulic.mods.dir", "mods");
        File[] dependencies = new File(modsDirectory).listFiles((dir, name) -> name.endsWith(".jar"));
        if (dependencies != null) {
            for (File dependency : dependencies) {
                if (!dependency.toPath().equals(modJar)) {
                    urls.add(dependency.toPath().toUri().toURL());
                }
            }
        }

        // Fabric/NeoForge launch libraries (JOML, Guava, loader APIs, ...)
        // live outside mods/. Include their jars without knowing a loader or
        // framework-specific layout.
        for (String directory : List.of("libraries", "lib", "libs")) {
            Path root = Path.of(directory);
            if (!Files.isDirectory(root)) continue;
            try (java.util.stream.Stream<Path> files = Files.walk(root, 8)) {
                files.filter(file -> file.toString().endsWith(".jar"))
                        .forEach(file -> {
                            try {
                                urls.add(file.toUri().toURL());
                            } catch (java.net.MalformedURLException ignored) {
                            }
                        });
            } catch (IOException ignored) {
                // A missing optional library directory is normal.
            }
        }

        // Mod jar is added LAST so its class definitions win when
        // there is a duplicate name across the mod and the libraries.
        urls.add(modJar.toUri().toURL());
        if (REPORTED_CLASSPATHS.add(modJar.toString())) {
            System.err.println("TabulaReflection classpath for " + modJar.getFileName() + ": " + urls.size() + " URLs");
        }
        return urls.toArray(new URL[0]);
    }

    /**
     * Best-effort lookup for the mod jar that ships the given
     * namespace. Walks up from the resource pack's directory looking
     * for a sibling {@code mods/} folder; this matches the layout
     * Hydraulic passes to the converter at build time.
     */
    private static Path locateModJar(String namespace, ResourcePack pack) {
        // creative-api's ResourcePack doesn't expose an absolute path
        // by default, so fall back to a system-property hint that
        // Hydraulic sets before invoking the converter. The default
        // convention is "mods/<namespace>.jar" relative to the
        // server working directory.
        String hint = System.getProperty("hydraulic.mods.dir", "mods");
        File dir = new File(hint);
        if (!dir.isDirectory()) return null;
        for (File f : dir.listFiles((d, n) -> n.toLowerCase(Locale.ROOT).startsWith(namespace.toLowerCase(Locale.ROOT)) && n.endsWith(".jar"))) {
            return f.toPath();
        }
        return null;
    }

    /**
     * Load the model class from the mod jar via reflection and
     * return its cube data. Returns null if the class can't be
     * found or doesn't expose the expected shape.
     *
     * <p>Alex's Mobs and similar Citadel-based mods don't expose a
     * "cubes" map; instead each {@code AdvancedModelBox} field is
     * a separate private field on the model class. We iterate every
     * declared field whose type is (or is assignable to) the
     * {@code AdvancedModelBox} / {@code BasicModelPart} hierarchy
     * and dump each one as a Bedrock cube.</p>
     *
     * <p>For mod-agnostic discovery, we index the mod jar by entry
     * name. Every class whose simple name matches {@code Model<Pascal>*}
     * is recorded, then looked up by entity name without committing
     * to a specific namespace. Mods that follow the {@code
     * com.<author>.<ns>.client.model.Model<EntityName>} convention
     * (Alex's Mobs, Citadel) or {@code com.<author>.<ns>.client.model.<EntityName>Model}
     * are both supported.</p>
     */
    private static ModelLoadResult loadModelFromMod(URLClassLoader loader, Path modJar, String entityName) {
        String pascal = toPascalCase(entityName);

        Map<String, String> nameToFqn = MODEL_CLASS_INDEX.computeIfAbsent(modJar, TabulaReflectionEntityParser::indexModelClasses);

        if (nameToFqn.isEmpty()) return new ModelLoadResult(null, null);

        // Try the most likely candidates first: exact Model<Pascal> or
        // <Pascal>Model, then prefix matches.
        String[] order = {
                "Model" + pascal,
                pascal + "Model",
        };
        LinkedHashSet<String> candidates = new LinkedHashSet<>();
        for (String key : order) {
            String fqn = nameToFqn.get(key);
            if (fqn != null) candidates.add(fqn);
        }
        // Fallback: any class whose simple name starts with Model and is
        // associated with this entity (handles Baby variants and similar).
        for (Map.Entry<String, String> entry : nameToFqn.entrySet()) {
            if (entry.getKey().toLowerCase(Locale.ROOT).contains(pascal.toLowerCase(Locale.ROOT))) {
                candidates.add(entry.getValue());
            }
        }

        ModelLoadFailure firstFailure = null;
        Map<String, String> failedClasses = FAILED_MODEL_CLASSES.computeIfAbsent(modJar, ignored -> new ConcurrentHashMap<>());
        for (String candidate : candidates) {
            String cachedFailure = failedClasses.get(candidate);
            if (cachedFailure != null) {
                if (firstFailure == null) firstFailure = new ModelLoadFailure(candidate, cachedFailure);
                continue;
            }

            Class<?> modelClass;
            Object modelInstance;
            try {
                modelClass = loader.loadClass(candidate);
                modelInstance = modelClass.getDeclaredConstructor().newInstance();
            } catch (LinkageError | ReflectiveOperationException | SecurityException exception) {
                String failure = reason(exception);
                failedClasses.putIfAbsent(candidate, failure);
                if (firstFailure == null) firstFailure = new ModelLoadFailure(candidate, failure);
                continue;
            }

            ModelCubeData data;
            try {
                data = extractModelData(loader, modelClass, modelInstance);
            } catch (LinkageError | RuntimeException exception) {
                String failure = reason(exception);
                failedClasses.putIfAbsent(candidate, failure);
                if (firstFailure == null) firstFailure = new ModelLoadFailure(candidate, failure);
                continue;
            }
            if (data != null) return new ModelLoadResult(data, null);
        }
        return new ModelLoadResult(null, firstFailure);
    }

    private static ModelCubeData extractModelData(URLClassLoader loader, Class<?> modelClass, Object modelInstance) {
        // 1. Try the well-known TabulaModel field "cubes" first - it
        // works for mods that use vanilla GeckoLib/Tabula runtime
        // dumped as a single field.
        Map<String, Object> cubes = readCubesMap(modelInstance, modelClass);
        if (cubes == null) {
            cubes = readCubesFromStaticMethod(loader, modelClass);
        }
        if (cubes != null && !cubes.isEmpty()) {
            return buildCubeData(cubes);
        }

        // 2. Framework path: model libraries commonly expose one model-part
        // field per bone. Instead of naming a framework/package, recognize a
        // part structurally by its inherited cubeList field.
        List<BoneSpec> bones = new ArrayList<>();
        Map<Object, String> names = new IdentityHashMap<>();
        List<PartRef> parts = new ArrayList<>();
        int texW = 64;
        int texH = 64;
        for (Field f : collectAllFields(modelClass)) {
            f.setAccessible(true);
            try {
                Object part = f.get(modelInstance);
                if (part == null || !hasField(part.getClass(), "cubeList")) continue;
                if (names.putIfAbsent(part, f.getName()) == null) {
                    parts.add(new PartRef(f.getName(), part));
                }
                texW = Math.max(texW, (int) readFloatField(part.getClass(), part, "textureWidth", 64f));
                texH = Math.max(texH, (int) readFloatField(part.getClass(), part, "textureHeight", 64f));
            } catch (IllegalAccessException ignored) {
            }
        }
        for (PartRef part : parts) {
            List<CubeSpec> partCubes = readAdvancedModelBoxes(part.value());
            if (partCubes.isEmpty()) continue;
            bones.add(new BoneSpec(part.name(), parentName(part.value(), parts, names),
                    pivot(part.value()), rotation(part.value()), partCubes));
        }
        return bones.isEmpty() ? null : new ModelCubeData(bones, texW, texH);
    }

    private static Map<String, String> indexModelClasses(Path modJar) {
        Map<String, String> index = new HashMap<>();
        try (java.util.jar.JarFile jar = new java.util.jar.JarFile(modJar.toFile())) {
            java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String path = entries.nextElement().getName();
                if (!path.endsWith(".class") || path.contains("$") || !path.contains("/client/model/")) continue;
                String simpleName = path.substring(path.lastIndexOf('/') + 1, path.length() - 6);
                if (simpleName.startsWith("Model") || simpleName.endsWith("Model")) {
                    index.putIfAbsent(simpleName, path.replace('/', '.').replace(".class", ""));
                }
            }
        } catch (IOException ignored) {
        }
        return Map.copyOf(index);
    }

    private static List<Field> collectAllFields(Class<?> type) {
        List<Field> out = new ArrayList<>();
        Class<?> c = type;
        while (c != null && c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) continue;
                out.add(f);
            }
            c = c.getSuperclass();
        }
        return out;
    }

    private static String toPascalCase(String snake) {
        StringBuilder out = new StringBuilder();
        for (String part : snake.split("_")) {
            if (part.isEmpty()) continue;
            out.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) out.append(part.substring(1));
        }
        return out.toString();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> readCubesMap(Object instance, Class<?> modelClass) {
        Class<?> c = modelClass;
        while (c != null) {
            try {
                Field f = c.getDeclaredField("cubes");
                f.setAccessible(true);
                Object value = f.get(instance);
                if (value instanceof Map<?, ?> m) {
                    return (Map<String, Object>) m;
                }
            } catch (NoSuchFieldException ignored) {
                // try parent
            } catch (IllegalAccessException ignored) {
                // try parent
            }
            c = c.getSuperclass();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> readCubesFromStaticMethod(URLClassLoader loader, Class<?> modelClass) {
        for (Method m : modelClass.getMethods()) {
            if (!java.lang.reflect.Modifier.isStatic(m.getModifiers())) continue;
            if (m.getParameterCount() != 0) continue;
            if (!Map.class.isAssignableFrom(m.getReturnType())) continue;
            m.setAccessible(true);
            try {
                Object value = m.invoke(null);
                if (value instanceof Map<?, ?> map) {
                    return (Map<String, Object>) map;
                }
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        }
        return null;
    }

    /**
     * Walk the cube map and pull position/size/rotation off each
     * AdvancedModelBox via reflection on its public fields. Returns
     * a flat list of Bedrock-ready cubes plus texture size.
     */
    private static ModelCubeData buildCubeData(Map<String, Object> cubes) {
        List<CubeSpec> specs = new ArrayList<>();
        int texW = 64;
        int texH = 64;

        for (Map.Entry<String, Object> entry : cubes.entrySet()) {
            Object box = entry.getValue();
            if (box == null) continue;
            specs.addAll(readAdvancedModelBoxes(box));

            // Pick the largest textureWidth/Height declared on any
            // box; the Citadel model has one per-instance and they're
            // uniform.
            try {
                Field tw = box.getClass().getDeclaredField("textureWidth");
                tw.setAccessible(true);
                float v = tw.getFloat(box);
                if (v > texW) texW = (int) v;
            } catch (Exception ignored) {
            }
            try {
                Field th = box.getClass().getDeclaredField("textureHeight");
                th.setAccessible(true);
                float v = th.getFloat(box);
                if (v > texH) texH = (int) v;
            } catch (Exception ignored) {
            }
        }

        return specs.isEmpty() ? null : new ModelCubeData(List.of(
                new BoneSpec("root", null, new float[]{0, 0, 0}, new float[]{0, 0, 0}, specs)), texW, texH);
    }

    private static List<CubeSpec> readAdvancedModelBoxes(Object box) {
        try {
            Class<?> c = box.getClass();
            // AdvancedEntityModel/AdvancedModelBox has the
            // render box geometry stored as six floats in cubeList,
            // but the *default* (rest) pose is exposed as direct
            // public fields. We use the rest pose here.
            float posX = readFloatField(c, box, "defaultPositionX");
            float posY = readFloatField(c, box, "defaultPositionY");
            float posZ = readFloatField(c, box, "defaultPositionZ");
            float rotX = readFloatField(c, box, "defaultRotationX");
            float rotY = readFloatField(c, box, "defaultRotationY");
            float rotZ = readFloatField(c, box, "defaultRotationZ");
            float scaleX = readFloatField(c, box, "scaleX", 1f);
            float scaleY = readFloatField(c, box, "scaleY", 1f);
            float scaleZ = readFloatField(c, box, "scaleZ", 1f);

            // Citadel models expose geometry as a single "render box"
            // (xMin, yMin, zMin, xSize, ySize, zSize) inside
            // `cubeList[0]`. Width/height come from the render box
            // rather than the rotation pivots so we don't have to
            // walk the GL cube list ourselves.
            int texX = (int) readFloatField(c, box, "textureOffsetX", 0f);
            int texY = (int) readFloatField(c, box, "textureOffsetY", 0f);
            if (scaleX == 0f || scaleY == 0f || scaleZ == 0f) return List.of();
            List<CubeSpec> cubes = new ArrayList<>();
            for (float[] dims : readRenderBoxDims(box)) {
                float w = dims[3], h = dims[4], d = dims[5];
                if (w <= 0f || h <= 0f || d <= 0f) continue;
                cubes.add(new CubeSpec(box.toString(),
                        new float[]{dims[0] + w / 2f, dims[1] + h / 2f, dims[2] + d / 2f},
                        new float[]{w, h, d}, new float[]{rotX, rotY, rotZ},
                        new float[]{posX, posY, posZ}, texX, texY));
            }
            return cubes;
        } catch (Exception e) {
            return List.of();
        }
    }

    private static float readFloatField(Class<?> c, Object inst, String name) {
        return readFloatField(c, inst, name, 0f);
    }

    private static float readFloatField(Class<?> c, Object inst, String name, float fallback) {
        try {
            Field f = findField(c, name);
            if (f == null) return fallback;
            f.setAccessible(true);
            return f.getFloat(inst);
        } catch (Exception e) {
            return fallback;
        }
    }

    /**
     * Pulls the rendered AABB from {@code cubeList[0]} on
     * AdvancedModelBox. Returns {@code null} when the cube list
     * is empty (e.g. collapsed bone).
     */
    private static List<float[]> readRenderBoxDims(Object box) {
        try {
            Field cubeListField = findField(box.getClass(), "cubeList");
            if (cubeListField == null) return List.of();
            cubeListField.setAccessible(true);
            Object cubeList = cubeListField.get(box);
            if (!(cubeList instanceof java.util.List<?> list) || list.isEmpty()) return List.of();
            List<float[]> dimensions = new ArrayList<>();
            for (Object modelBox : list) {
                // Both BasicModelPart.ModelBox and TabulaModelRenderUtils.ModelBox
                // expose the AABB via final fields posX1/posY1/posZ1 and
                // posX2/posY2/posZ2. The first set is the min-corner and
                // the second is the max-corner.
                Field posX1 = modelBox.getClass().getField("posX1");
                Field posY1 = modelBox.getClass().getField("posY1");
                Field posZ1 = modelBox.getClass().getField("posZ1");
                Field posX2 = modelBox.getClass().getField("posX2");
                Field posY2 = modelBox.getClass().getField("posY2");
                Field posZ2 = modelBox.getClass().getField("posZ2");
                float xMin = posX1.getFloat(modelBox);
                float yMin = posY1.getFloat(modelBox);
                float zMin = posZ1.getFloat(modelBox);
                float xMax = posX2.getFloat(modelBox);
                float yMax = posY2.getFloat(modelBox);
                float zMax = posZ2.getFloat(modelBox);
                dimensions.add(new float[]{xMin, yMin, zMin, xMax - xMin, yMax - yMin, zMax - zMin});
            }
            return dimensions;
        } catch (Exception e) {
            return List.of();
        }
    }

    private static boolean hasField(Class<?> type, String name) {
        return findField(type, name) != null;
    }

    private static Field findField(Class<?> type, String name) {
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
                // Continue into the parent model-part type.
            }
        }
        return null;
    }

    private static String parentName(Object part, List<PartRef> parts, Map<Object, String> names) {
        for (PartRef candidate : parts) {
            for (Object child : childParts(candidate.value())) {
                if (child == part) {
                    return names.get(candidate.value());
                }
            }
        }
        return null;
    }

    private static List<?> childParts(Object part) {
        try {
            Field children = findField(part.getClass(), "childModels");
            if (children == null) return List.of();
            children.setAccessible(true);
            Object value = children.get(part);
            return value instanceof List<?> list ? list : List.of();
        } catch (IllegalAccessException ignored) {
            return List.of();
        }
    }

    private static float[] pivot(Object part) {
        Class<?> type = part.getClass();
        return new float[]{
                readFloatField(type, part, "defaultPositionX", readFloatField(type, part, "rotationPointX", 0f)),
                readFloatField(type, part, "defaultPositionY", readFloatField(type, part, "rotationPointY", 0f)),
                readFloatField(type, part, "defaultPositionZ", readFloatField(type, part, "rotationPointZ", 0f))};
    }

    private static float[] rotation(Object part) {
        Class<?> type = part.getClass();
        return new float[]{
                readFloatField(type, part, "defaultRotationX", readFloatField(type, part, "rotateAngleX", 0f)),
                readFloatField(type, part, "defaultRotationY", readFloatField(type, part, "rotateAngleY", 0f)),
                readFloatField(type, part, "defaultRotationZ", readFloatField(type, part, "rotateAngleZ", 0f))};
    }

    private static BedrockModel buildBedrockModel(String namespace, String entityName, ModelCubeData data) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.formatVersion("1.16.0");

        Geometry geometry = new Geometry();
        Description description = new Description();
        description.identifier("geometry." + namespace + "." + entityName);
        description.textureWidth(data.textureWidth);
        description.textureHeight(data.textureHeight);
        geometry.description(description);

        List<Bones> bones = new ArrayList<>();
        for (BoneSpec spec : data.bones) {
            Bones bone = new Bones();
            bone.name(spec.name);
            if (spec.parent != null) bone.parent(spec.parent);
            bone.pivot(spec.pivot);
            if (spec.rotation[0] != 0 || spec.rotation[1] != 0 || spec.rotation[2] != 0) {
                bone.rotation(spec.rotation);
            }
            List<Cubes> cubes = new ArrayList<>();
            for (CubeSpec cubeSpec : spec.cubes) {
                Cubes cube = new Cubes();
                cube.origin(cubeSpec.origin);
                cube.size(cubeSpec.size);
                cube.uv(BoxUvMapper.expand(cubeSpec.textureX, cubeSpec.textureY, cubeSpec.size));
                cubes.add(cube);
            }
            bone.cubes(cubes);
            bones.add(bone);
        }
        geometry.bones(bones);
        modelEntity.geometry(List.of(geometry));

        return new BedrockModel(BedrockModel.ModelType.ENTITY,
                namespace + "." + entityName + ".json", 
                modelEntity);
    }

    private static String reason(Throwable throwable) {
        Throwable cause = throwable instanceof InvocationTargetException && throwable.getCause() != null
                ? throwable.getCause() : throwable;
        String message = cause.getMessage();
        return cause.getClass().getSimpleName() + (message == null || message.isBlank()
                ? "" : ": " + message.replace('\n', ' ').replace('\r', ' '));
    }

    // Package-private test hooks keep the runtime cache observable without
    // making cache lifecycle part of PackConverter's public API.
    static CacheState cacheStateForTests() {
        return new CacheState(RUNTIMES.size(), FAILED_MODEL_CLASSES.values().stream().mapToInt(Map::size).sum());
    }

    static void clearCachesForTests() {
        for (ReflectionRuntime runtime : RUNTIMES.values()) {
            try {
                runtime.loader.close();
            } catch (IOException ignored) {
                // Test cleanup only; the process releases retained loaders in production.
            }
        }
        RUNTIMES.clear();
        MODEL_CLASS_INDEX.clear();
        FAILED_MODEL_CLASSES.clear();
        REPORTED_CLASSPATHS.clear();
    }

    private record ParsedEntityRef(String namespace, String entityName) {
        static ParsedEntityRef from(String path) {
            // Accept both <ns>:<entity>.reflection and
            // assets/<ns>/.../<entity>.tbl
            if (path.endsWith(".reflection")) {
                String base = path.substring(0, path.length() - ".reflection".length());
                int colon = base.lastIndexOf(':');
                if (colon < 0) {
                    int slash = base.lastIndexOf('/');
                    if (slash < 0) return null;
                    base = base.substring(slash + 1);
                    colon = base.indexOf('_');
                    if (colon < 0) return null;
                }
                String ns = base.substring(0, colon);
                String ent = base.substring(colon + 1);
                return new ParsedEntityRef(ns, ent);
            }
            if (path.startsWith("assets/")) {
                String[] parts = path.split("/", 3);
                if (parts.length < 3) return null;
                String ns = parts[1];
                int lastSlash = path.lastIndexOf('/');
                String fileName = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
                if (!fileName.endsWith(".tbl")) return null;
                String ent = fileName.substring(0, fileName.length() - 4);
                return new ParsedEntityRef(ns, ent);
            }
            return null;
        }
    }

    private record ModelCubeData(List<BoneSpec> bones, int textureWidth, int textureHeight) {}

    private record ModelLoadResult(ModelCubeData data, ModelLoadFailure failure) {}

    private record ModelLoadFailure(String modelClass, String reason) {}

    private record ReflectionRuntime(URLClassLoader loader) {}

    record CacheState(int runtimes, int failedModelClasses) {}

    private record PartRef(String name, Object value) {}

    private record BoneSpec(String name, String parent, float[] pivot, float[] rotation, List<CubeSpec> cubes) {}

    private record CubeSpec(
            String identifier,
            float[] origin,
            float[] size,
            float[] rotation,
            float[] pivot,
            int textureX,
            int textureY) {
    }
}
