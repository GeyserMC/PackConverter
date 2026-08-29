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
import org.geysermc.pack.bedrock.resource.models.entity.ModelEntity;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.Geometry;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Description;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.Bones;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.Cubes;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Down;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.East;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.North;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.South;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.Up;
import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.uv.West;
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
    private static final Gson GSON = new Gson();
    private static final Set<String> UNAVAILABLE_NAMESPACES = ConcurrentHashMap.newKeySet();
    private static final Set<String> REPORTED_CLASSPATHS = ConcurrentHashMap.newKeySet();
    // 

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
        if (UNAVAILABLE_NAMESPACES.contains(ref.namespace)) return null;
        Path modJar = locateModJar(ref.namespace, pack);
        if (modJar == null) {
            // No mod jar available - the scanner will fall back to the
            // next parser or vanilla Bedrock geometry.
            return null;
        }

        try {
            URL[] urls = collectClasspathUrls(modJar);
            // parent = null so the URLClassLoader searches only its
            // own URLs. We layered every Gradle-cache jar above, which
            // gives the loader full visibility of the project's runtime
            // classpath (Mojang, Guava, JOML, datafixerupper, etc.)
            // without depending on a parent that may not expose them.
            try (URLClassLoader loader = new URLClassLoader(
                    "tabula-reflect", 
                    urls,
                    null)) {

                ModelCubeData data = loadModelFromMod(loader, modJar, ref.namespace, ref.entityName);
                if (data == null) {
                    return null;
                }
                return buildBedrockModel(ref.namespace, ref.entityName, data);
            }
        } catch (NoClassDefFoundError error) {
            UNAVAILABLE_NAMESPACES.add(ref.namespace);
            System.err.println("TabulaReflection disabled for " + ref.namespace
                    + ": missing runtime class " + error.getMessage());
            return null;
        } catch (Throwable t) {
            System.err.println("TabulaReflection failed for " + ref.namespace + ": " + t.getClass().getSimpleName()
                    + (t.getMessage() == null ? "" : " (" + t.getMessage() + ")"));
            return null;
        }
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
    private static ModelCubeData loadModelFromMod(URLClassLoader loader, Path modJar, String namespace, String entityName) {
        String pascal = toPascalCase(entityName);

        // Build a name -> fqn index from the mod jar entries once.
        // This avoids hardcoding a single namespace and works for
        // any mod that ships Model<Pascal> classes.
        Map<String, String> nameToFqn = new HashMap<>();
        try {
            java.util.jar.JarFile jar = new java.util.jar.JarFile(modJar.toFile());
            try {
                java.util.Enumeration<java.util.jar.JarEntry> en = jar.entries();
                while (en.hasMoreElements()) {
                    java.util.jar.JarEntry e = en.nextElement();
                    String n = e.getName();
                    if (!n.endsWith(".class") || !n.contains("/client/model/")) continue;
                    int slash = n.lastIndexOf('/');
                    String simpleName = n.substring(slash + 1, n.length() - 6);
                    // Match Model<Pascal>, Model<Pascal>Baby,
                    // <Pascal>Model, and similar variants.
                    if (simpleName.equals("Model" + pascal)
                            || simpleName.startsWith("Model" + pascal)
                            || simpleName.equals(pascal + "Model")
                            || simpleName.startsWith(pascal + "Model")
                            || simpleName.startsWith("Model")
                            || simpleName.endsWith("Model")) {
                        String fqn = n.replace('/', '.').replace(".class", "");
                        nameToFqn.putIfAbsent(simpleName, fqn);
                    }
                }
            } finally {
                jar.close();
            }
        } catch (Exception e) {
            return null;
        }

        if (nameToFqn.isEmpty()) return null;

        // Try the most likely candidates first: exact Model<Pascal> or
        // <Pascal>Model, then prefix matches.
        String[] order = new String[]{
                "Model" + pascal,
                pascal + "Model", 
        };
        Class<?> modelClass = null;
        for (String key : order) {
            String fqn = nameToFqn.get(key);
            if (fqn != null) {
                try {
                    modelClass = loader.loadClass(fqn);
                    break;
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        if (modelClass == null) {
            // Fallback: any class whose simple name starts with Model
            // and is associated with this entity (handles Baby variants
            // and similar).
            for (Map.Entry<String, String> e : nameToFqn.entrySet()) {
                if (e.getKey().toLowerCase(Locale.ROOT).contains(pascal.toLowerCase(Locale.ROOT))) {
                    try {
                        modelClass = loader.loadClass(e.getValue());
                        break;
                    } catch (ClassNotFoundException ignored) {
                    }
                }
            }
        }
        if (modelClass == null) {
            return null;
        }
        System.err.println("using model class " + modelClass.getName());

        Object modelInstance;
        try {
            modelInstance = modelClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            System.err.println("newInstance failed: " + cause);
            cause.printStackTrace(System.err);
            return null;
        }

        // 1. Try the well-known TabulaModel field "cubes" first - it
        // works for mods that use vanilla GeckoLib/Tabula runtime
        // dumped as a single field.
        System.err.println("TabulaReflection: probing cubes map / static method");
        Map<String, Object> cubes = readCubesMap(modelInstance, modelClass);
        if (cubes == null) {
            cubes = readCubesFromStaticMethod(loader, modelClass);
        }
        if (cubes != null) {
            System.err.println("cubes map has " + cubes.size() + " entries (first 3 keys: "
                    + cubes.keySet().stream().limit(3).reduce((a, b) -> a + "," + b).orElse("") + ")");
        }
        if (cubes != null && !cubes.isEmpty()) {
            return buildCubeData(cubes);
        }

        // 2. Citadel path: each AdvancedModelBox is a private field
        // on the model. Walk every field whose type is an instance
        // of BasicModelPart (parent class of AdvancedModelBox) and
        // collect cubes from each.
        System.err.println("TabulaReflection: trying Citadel field-walk path");
        Class<?> partClass = lookupBasicModelPart(modelClass.getClassLoader());
        if (partClass == null) {
            partClass = lookupBasicModelPart(loader);
        }
        if (partClass == null) {
            System.err.println("TabulaReflection: BasicModelPart not found in mod classpath");
            return null;
        }
        System.err.println("BasicModelPart = " + partClass.getName());
        List<CubeSpec> specs = new ArrayList<>();
        int texW = 64;
        int texH = 64;
        int fieldCount = 0;
        for (Field f : collectAllFields(modelClass)) {
            if (f.getType().isAssignableFrom(partClass)) {
                fieldCount++;
                f.setAccessible(true);
                try {
                    Object part = f.get(modelInstance);
                    if (part == null) continue;
                    CubeSpec spec = readAdvancedModelBox(part);
                    if (spec == null) continue;
                    specs.add(spec);
                    // Sample texture size from each part - they all
                    // share the same value in practice.
                    texW = Math.max(texW, (int) readFloatField(part.getClass(), part, "textureWidth", 64f));
                    texH = Math.max(texH, (int) readFloatField(part.getClass(), part, "textureHeight", 64f));
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        if (specs.isEmpty()) {
            System.err.println("0 cubes collected (scanned " + fieldCount + " matching fields)");
            return null;
        }
        System.err.println("collected " + specs.size() + " cubes from " + fieldCount + " fields");
        return new ModelCubeData(specs, texW, texH);
    }

    private static Class<?> lookupBasicModelPart(ClassLoader cl) {
        String[] candidates = {
                "com.github.alexthe666.alexsmobs.citadel.client.model.basic.BasicModelPart", 
                "com.github.alexthe666.alexsmobs.citadel.client.model.AdvancedModelBox", 
        };
        for (String fqn : candidates) {
            try {
                Class<?> c = Class.forName(fqn, true, cl);
                if (c != null) {
                    System.err.println("found class " + fqn);
                    return c;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("NOT FOUND " + fqn);
            }
        }
        return null;
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

    private static String[] buildModelClassCandidates(String namespace, String entityName) {
        // Alex's Mobs convention: com.github.alexthe666.<ns>.client.model.Model<EntityName>
        // but other mods may use any of:
        //   com.<author>.<ns>.client.model.Model<EntityName>
        //   <ns>.client.model.Model<EntityName>
        //   com.<author>.<ns>.client.model.<EntityName>Model
        // The scanner tries each, so we accept the first that loads.
        String pascal = toPascalCase(entityName);
        return new String[]{
                "com.github.alexthe666." + namespace + ".client.model.Model" + pascal,
                "com.github.alexthe666." + namespace + ".client.model." + pascal + "Model", 
                "com." + namespace + ".client.model.Model" + pascal,
                "com." + namespace + ".client.model." + pascal + "Model", 
                namespace + ".client.model.Model" + pascal,
                namespace + ".client.model." + pascal + "Model", 
        };
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
            CubeSpec spec = readAdvancedModelBox(box);
            if (spec == null) continue;
            specs.add(spec);

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

        return new ModelCubeData(specs, texW, texH);
    }

    private static CubeSpec readAdvancedModelBox(Object box) {
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
            float[] dims = readRenderBoxDims(box);
            if (dims == null) {
                return null;
            }

            float xMin = dims[0], yMin = dims[1], zMin = dims[2];
            float w = dims[3], h = dims[4], d = dims[5];
            // Render box is axis-aligned at rest; rotation pivots
            // around posX/posY/posZ.
            int texX = (int) readFloatField(c, box, "textureOffsetX", 0f);
            int texY = (int) readFloatField(c, box, "textureOffsetY", 0f);

            // Convert to Bedrock origin (cube center) from min-corner
            // + size.
            float ox = xMin + w / 2f;
            float oy = yMin + h / 2f;
            float oz = zMin + d / 2f;

            // Use scale as a rough proxy for "is this a child bone".
            // Citadel models expose scale 0 on invisible bones; skip
            // those so we don't emit garbage cubes.
            if (scaleX == 0f || scaleY == 0f || scaleZ == 0f) return null;
            if (w <= 0f || h <= 0f || d <= 0f) return null;

            return new CubeSpec(
                    box == null ? "" : box.toString(),
                    new float[]{ox, oy, oz},
                    new float[]{w, h, d},
                    new float[]{rotX, rotY, rotZ},
                    new float[]{posX, posY, posZ},
                    texX, texY);
        } catch (Exception e) {
            return null;
        }
    }

    private static float readFloatField(Class<?> c, Object inst, String name) {
        return readFloatField(c, inst, name, 0f);
    }

    private static float readFloatField(Class<?> c, Object inst, String name, float fallback) {
        try {
            Field f = c.getDeclaredField(name);
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
    private static float[] readRenderBoxDims(Object box) {
        try {
            Field cubeListField = box.getClass().getDeclaredField("cubeList");
            cubeListField.setAccessible(true);
            Object cubeList = cubeListField.get(box);
            if (!(cubeList instanceof java.util.List<?> list) || list.isEmpty()) return null;
            Object modelBox = list.get(0);

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
            return new float[]{xMin, yMin, zMin, xMax - xMin, yMax - yMin, zMax - zMin};
        } catch (Exception e) {
            return null;
        }
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

        Bones root = new Bones();
        root.name("root");
        root.pivot(new float[]{0, 0, 0});
        List<Cubes> cubes = new ArrayList<>();
        for (CubeSpec spec : data.cubes) {
            Cubes cube = new Cubes();
            cube.origin(spec.origin);
            cube.size(spec.size);
            if (spec.pivot[0] != 0 || spec.pivot[1] != 0 || spec.pivot[2] != 0) {
                cube.pivot(spec.pivot);
            }
            if (spec.rotation[0] != 0 || spec.rotation[1] != 0 || spec.rotation[2] != 0) {
                cube.rotation(spec.rotation);
            }
            // UV: simple per-face box mapping. For a 16x16 atlas
            // tile this produces reasonable results; the rendering
            // bed-side UV layout may differ and a real mod pipeline
            // would derive UVs from the render box's per-face
            // texture coordinates. Keeping it simple for r11.
            cube.uv(boxUv(spec.size, spec.textureX, spec.textureY, data.textureWidth, data.textureHeight));
            cubes.add(cube);
        }
        root.cubes(cubes);
        geometry.bones(List.of(root));
        modelEntity.geometry(List.of(geometry));

        return new BedrockModel(BedrockModel.ModelType.ENTITY,
                namespace + "." + entityName + ".json", 
                modelEntity);
    }

    /**
     * Build a per-face box UV layout. Returns a 6-entry float
     * array (uv coords per face) — the Bedrock
     * {@link Uv} schema is one UV per face (north, south, east,
     * west, up, down) for vanilla block model conventions; entity
     * model UV is simpler (single {@code uv} pair, all faces
     * share). This stub uses the size-derived per-face mapping
     * that Blockbench's auto-UV does, scaled by textureWidth/
     * textureHeight.
     */
    private static Uv boxUv(float[] size, int texX, int texY, int texW, int texH) {
        // Single UV pair covering the whole box; Bedrock entity
        // model UV is a single [u, v] in the default schema.
        // We map (0,0)-(w,h) into the texture atlas.
        float u0 = texX / (float) texW;
        float v0 = texY / (float) texH;
        Uv uv = new Uv();
        uv.north = northFace(u0, v0);
        uv.south = southFace(u0, v0);
        uv.east = eastFace(u0, v0);
        uv.west = westFace(u0, v0);
        uv.up = upFace(u0, v0);
        uv.down = downFace(u0, v0);
        return uv;
    }

    private static North northFace(float u, float v) {
        North f = new North();
        f.uv = new float[]{u, v};
        return f;
    }

    private static South southFace(float u, float v) {
        South f = new South();
        f.uv = new float[]{u, v};
        return f;
    }

    private static East eastFace(float u, float v) {
        East f = new East();
        f.uv = new float[]{u, v};
        return f;
    }

    private static West westFace(float u, float v) {
        West f = new West();
        f.uv = new float[]{u, v};
        return f;
    }

    private static Up upFace(float u, float v) {
        Up f = new Up();
        f.uv = new float[]{u, v};
        return f;
    }

    private static Down downFace(float u, float v) {
        Down f = new Down();
        f.uv = new float[]{u, v};
        return f;
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

    private record ModelCubeData(List<CubeSpec> cubes, int textureWidth, int textureHeight) {}

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
