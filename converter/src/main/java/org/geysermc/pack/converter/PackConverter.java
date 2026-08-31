/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter;

import org.apache.commons.io.file.PathUtils;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.ConverterPipeline;
import org.geysermc.pack.converter.type.entity.EntityModelScanner;
import org.geysermc.pack.converter.type.entity.ReflectionInput;
import org.geysermc.pack.converter.util.DefaultLogListener;
import org.geysermc.pack.converter.util.LogListener;
import org.geysermc.pack.converter.util.ModJarExtractor;
import org.geysermc.pack.converter.util.NioDirectoryFileTreeReader;
import org.geysermc.pack.converter.util.VanillaPackProvider;
import org.geysermc.pack.converter.util.ZipUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackReader;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/** Handles the conversion of a resource pack. */
public final class PackConverter {
    private Path input;
    private final List<Path> inputs = new ArrayList<>();
    private Path output;
    private String packName;
    private Path vanillaPackPath = Paths.get("vanilla-pack.zip");
    private String textureSubdirectory;
    private boolean compressed;
    private boolean enforcePackCheck = false;
    private boolean autoExtractModResources;
    private Iterable<String> reflectionEntityIds = List.of();
    private ReflectionInput reflectionInput;
    private BiConsumer<ResourcePack, BedrockResourcePack> postProcessor;
    private final List<ConverterPipeline<?, ?>> converters = new ArrayList<>();
    private final List<EntityModelScanner.Diagnostic> entityModelDiagnostics = new ArrayList<>();
    private Path tmpDir;
    private Path modResourceDir;
    private PackageHandler packageHandler = PackageHandler.ZIP;
    private LogListener logListener = new DefaultLogListener();

    @Nullable
    public String textureSubdirectory() {
        return this.textureSubdirectory;
    }

    public PackConverter input(@NotNull Path input) {
        return this.input(input, true);
    }

    public PackConverter input(@NotNull Path input, boolean compressed) {
        this.input = input;
        this.inputs.clear();
        this.inputs.add(input);
        this.compressed = compressed;
        this.autoExtractModResources = false;
        return this;
    }

    /** Uses layered directory inputs in order; the first occurrence of a resource path wins. */
    public PackConverter inputs(@NotNull Collection<Path> inputs) {
        if (inputs.isEmpty()) throw new IllegalArgumentException("Inputs cannot be empty");
        this.inputs.clear();
        this.inputs.addAll(inputs);
        this.input = this.inputs.getFirst();
        this.compressed = false;
        this.autoExtractModResources = false;
        return this;
    }

    /** Sets a directory of mod JARs as the input and enables automatic extraction. */
    public PackConverter modDirectory(@NotNull Path modDirectory) {
        this.input(modDirectory, false);
        this.autoExtractModResources = true;
        return this;
    }

    /** Enables or disables automatic extraction from an uncompressed mod directory. */
    public PackConverter autoExtractModResources(boolean enabled) {
        this.autoExtractModResources = enabled;
        return this;
    }

    public PackConverter output(@NotNull Path output) {
        this.output = output;
        return this;
    }

    public PackConverter packName(@NotNull String packName) {
        this.packName = packName;
        return this;
    }

    public @NotNull String packName() {
        if (packName == null || packName.isBlank()) {
            return input.getFileName().toString().replaceFirst("[.][^.]+$", "");
        }
        return packName;
    }

    public PackConverter vanillaPackPath(@NotNull Path vanillaPackPath) {
        this.vanillaPackPath = vanillaPackPath;
        return this;
    }

    public PackConverter textureSubdirectory(@NotNull String textureSubdirectory) {
        this.textureSubdirectory = textureSubdirectory;
        return this;
    }

    /** Supplies trusted entity identifiers for the opt-in runtime-model bridge. */
    public PackConverter reflectionEntityIds(@NotNull Iterable<String> entityIds) {
        this.reflectionEntityIds = entityIds;
        return this;
    }

    /** Supplies the exact runtime-model source and classpath for this conversion. */
    public PackConverter reflectionInput(@Nullable ReflectionInput input) {
        this.reflectionInput = input;
        return this;
    }

    /** Non-fatal entity parser fallbacks accumulated across conversion inputs. */
    public List<EntityModelScanner.Diagnostic> entityModelDiagnostics() {
        return List.copyOf(entityModelDiagnostics);
    }

    public PackConverter enforcePackCheck(boolean enforcePackCheck) {
        this.enforcePackCheck = enforcePackCheck;
        return this;
    }

    public PackConverter converter(@NotNull ConverterPipeline<?, ?> converter) {
        this.converters.add(converter);
        return this;
    }

    public PackConverter converters(@NotNull List<? extends ConverterPipeline<?, ?>> converters) {
        this.converters.addAll(converters);
        return this;
    }

    public PackConverter logListener(@NotNull LogListener logListener) {
        this.logListener = logListener;
        return this;
    }

    public PackConverter packageHandler(@NotNull PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
        return this;
    }

    public PackConverter postProcessor(@NotNull BiConsumer<ResourcePack, BedrockResourcePack> postProcessor) {
        this.postProcessor = postProcessor;
        return this;
    }

    public PackConverter convert() throws IOException {
        validateConfiguration();
        cleanupTemporaryState();
        entityModelDiagnostics.clear();
        ImageIO.scanForPlugins();
        Path absoluteOutput = this.output.toAbsolutePath().normalize();
        Path outputParent = absoluteOutput.getParent();
        if (outputParent == null) {
            throw new IllegalArgumentException("Output must resolve to a filesystem path with a parent directory");
        }
        Files.createDirectories(outputParent);
        VanillaPackProvider.create(this.vanillaPackPath, this.logListener);

        Path conversionInput = this.input;
        if (!this.compressed && this.autoExtractModResources && ModJarExtractor.isModDirectory(this.input)) {
            this.modResourceDir = outputParent.resolve(absoluteOutput.getFileName() + "_modresources");
            if (Files.exists(this.modResourceDir)) {
                PathUtils.delete(this.modResourceDir);
            }

            ModJarExtractor.ExtractionReport report = ModJarExtractor.extractAll(this.input, this.modResourceDir);
            this.logListener.info("Extracted " + report.filesExtracted() + " resources from "
                    + report.mods().size() + " mod JARs in deterministic order.");
            if (!report.collisions().isEmpty()) {
                this.logListener.warn("Detected " + report.collisions().size()
                        + " duplicate resource paths; later sorted mods override earlier ones.");
                for (String collision : report.collisions()) {
                    this.logListener.warn("Resource override: " + collision);
                }
            }
            conversionInput = this.modResourceDir;
        }

        List<Path> conversionInputs = conversionInput.equals(this.input) ? List.copyOf(this.inputs) : List.of(conversionInput);
        Path sourceInput = conversionInput;
        ZipUtils.openFileSystem(sourceInput, this.compressed && sourceInput.equals(this.input), input -> {
            List<Path> sourceRoots = conversionInputs.size() == 1 ? List.of(input) : conversionInputs;
            if (this.enforcePackCheck && sourceRoots.stream().noneMatch(root -> Files.exists(root.resolve("pack.mcmeta")))) {
                throw new IllegalArgumentException("Invalid Java Edition resource pack. No pack.mcmeta found.");
            }

            this.tmpDir = outputParent.resolve(absoluteOutput.getFileName() + "_mcpack");
            if (Files.exists(this.tmpDir)) {
                PathUtils.delete(this.tmpDir);
            }

            ResourcePack javaResourcePack = (this.compressed && sourceInput.equals(this.input))
                    ? MinecraftResourcePackReader.minecraft().readFromZipFile(sourceInput)
                    : MinecraftResourcePackReader.minecraft().read(NioDirectoryFileTreeReader.read(sourceRoots));
            ResourcePack vanillaResourcePack = MinecraftResourcePackReader.minecraft().readFromZipFile(vanillaPackPath);
            BedrockResourcePack bedrockResourcePack = new BedrockResourcePack(this.tmpDir);

            int errors = converters.stream()
                    .mapToInt(converter -> converter.convert(javaResourcePack, Optional.of(vanillaResourcePack),
                            bedrockResourcePack, packName(), textureSubdirectory, logListener))
                    .sum();

            // Run all registered entity model parsers (vanilla, GeckoLib,
            // Blockbench, Tabula, OBJ, ...) over the Java resource pack
            // and merge the results into the Bedrock pack. The first
            // parser to successfully convert a file wins.
            EntityModelScanner entityModelScanner = EntityModelScanner.discover();
            EntityModelScanner.ScanResult scan = entityModelScanner.addEntityModels(javaResourcePack, bedrockResourcePack);
            EntityModelScanner.ScanResult reflected = reflectionInput == null ? new EntityModelScanner.ScanResult()
                    : entityModelScanner.addReflectionEntityModels(javaResourcePack, bedrockResourcePack, reflectionEntityIds, reflectionInput);
            this.entityModelDiagnostics.addAll(reflected.diagnostics());
            if (scan.successCount() > 0) {
                this.logListener.info("Entity model scanner: " + scan.successCount() + " model(s) converted via " + entityModelScanner.parsers().size() + " parser(s)");
            }
            for (String dup : scan.duplicates()) {
                this.logListener.warn("Duplicate entity model skipped: " + dup);
            }
            for (String fail : scan.failures()) {
                this.logListener.warn("Entity model parse failed: " + fail);
            }
            for (String fail : reflected.failures()) {
                this.logListener.warn("Reflection entity model parse failed: " + fail);
            }
            for (EntityModelScanner.Diagnostic diagnostic : reflected.diagnostics()) {
                this.logListener.warn("Reflection entity model fallback: " + diagnostic.detail());
            }

            if (this.postProcessor != null) {
                this.postProcessor.accept(javaResourcePack, bedrockResourcePack);
            }

            bedrockResourcePack.export();

            if (errors > 0) {
                this.logListener.warn("Pack conversion completed with " + errors + " errors!");
            } else {
                this.logListener.info("Pack conversion completed successfully!");
            }
        });
        return this;
    }

    private void validateConfiguration() {
        if (this.input == null) throw new NullPointerException("Input cannot be null");
        if (this.output == null) throw new NullPointerException("Output cannot be null");
        if (this.vanillaPackPath == null) throw new NullPointerException("Vanilla Pack Path cannot be null");
        if (this.converters.isEmpty()) throw new IllegalStateException("No converters have been added");
        for (Path input : this.inputs) {
            if (!Files.exists(input)) throw new IllegalArgumentException("Input does not exist: " + input);
        }
        if (!Files.isRegularFile(this.vanillaPackPath)) {
            throw new IllegalArgumentException("Vanilla pack must be a regular file: " + this.vanillaPackPath);
        }
        if (this.compressed && !Files.isRegularFile(this.input)) {
            throw new IllegalArgumentException("Compressed input must be a regular file: " + this.input);
        }
        if (!this.compressed) {
            for (Path input : this.inputs) {
                if (!Files.isDirectory(input)) {
                    throw new IllegalArgumentException("Uncompressed input must be a directory: " + input);
                }
            }
        }
    }

    public PackConverter pack() throws IOException {
        if (tmpDir == null || !Files.exists(tmpDir)) return this;
        logListener.info("Packaging pack...");
        packageHandler.pack(this, tmpDir, output, logListener);
        logListener.info("Packaged pack! Cleaning up...");
        cleanup();
        logListener.info("Pack converted.");
        return this;
    }

    private void cleanupTemporaryState() throws IOException {
        if (tmpDir != null && Files.exists(tmpDir)) {
            PathUtils.delete(tmpDir);
        }
        if (modResourceDir != null && Files.exists(modResourceDir)) {
            PathUtils.delete(modResourceDir);
        }
        tmpDir = null;
        modResourceDir = null;
    }

    private void cleanup() {
        if (tmpDir != null) {
            try {
                PathUtils.delete(tmpDir);
            } catch (IOException ignored) {
            }
            tmpDir = null;
        }
        if (modResourceDir != null) {
            try {
                PathUtils.delete(modResourceDir);
            } catch (IOException ignored) {
            }
            modResourceDir = null;
        }
    }
}
