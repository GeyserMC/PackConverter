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
import org.geysermc.pack.converter.converter.ActionListener;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.data.ConversionData;
import org.geysermc.pack.converter.util.DefaultLogListener;
import org.geysermc.pack.converter.util.LogListener;
import org.geysermc.pack.converter.util.NioDirectoryFileTreeReader;
import org.geysermc.pack.converter.util.ZipUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackReader;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Handles the conversion of a resource pack.
 */
public final class PackConverter {
    private Path input;
    private Path output;

    private String textureSubdirectory;

    private boolean compressed;

    private final Map<Class<?>, List<ActionListener<?>>> actionListeners = new IdentityHashMap<>();

    private BiConsumer<ResourcePack, BedrockResourcePack> postProcessor;

    private final List<Converter<?>> converters = new ArrayList<>();

    private Path tmpDir;

    private PackageHandler packageHandler = PackageHandler.ZIP;
    private LogListener logListener = new DefaultLogListener();

    /**
     * Gets the subdirectory used for textures in the converted
     * resource pack.
     * <p>
     * This option is only necessary for non-vanilla resource packs.
     *
     * @return the texture subdirectory
     */
    @Nullable
    public String textureSubdirectory() {
        return this.textureSubdirectory;
    }

    /**
     * Sets the input (Java Edition) pack location.
     *
     * @param input the input pack location
     * @return this instance
     */
    public PackConverter input(@NotNull Path input) {
        return this.input(input, true);
    }

    /**
     * Sets the input (Java Edition) pack location.
     * <p>
     * Set {@code compressed} to {@code true} if the input pack is
     * compressed, {@code false} otherwise.
     *
     * @param input the input pack location
     * @param compressed whether the input pack is compressed
     * @return this instance
     */
    public PackConverter input(@NotNull Path input, boolean compressed) {
        this.input = input;
        this.compressed = compressed;
        return this;
    }

    /**
     * Sets the output (Bedrock Edition) pack location.
     *
     * @param output the output pack location
     * @return this instance
     */
    public PackConverter output(@NotNull Path output) {
        this.output = output;
        return this;
    }

    /**
     * Sets the texture subdirectory.
     * <p>
     * This option is only necessary for non-vanilla resource packs.
     *
     * @param textureSubdirectory the texture subdirectory
     * @return this instance
     */
    public PackConverter textureSubdirectory(@NotNull String textureSubdirectory) {
        this.textureSubdirectory = textureSubdirectory;
        return this;
    }

    /**
     * Adds a converter to the converter list.
     *
     * @param converter the converter to add
     * @return this instance
     */
    public PackConverter converter(@NotNull Converter<?> converter) {
        this.converters.add(converter);
        return this;
    }

    /**
     * Adds a list of converters to the converter list.
     *
     * @param converters the converters to add
     * @return this instance
     */
    public PackConverter converters(@NotNull List<Converter<?>> converters) {
        this.converters.addAll(converters);
        return this;
    }

    /**
     * Sets the log listener for displaying conversion information.
     *
     * @param logListener the log listener
     * @return this instance
     */
    public PackConverter logListener(@NotNull LogListener logListener) {
        this.logListener = logListener;
        return this;
    }

    /**
     * Sets the handler used to package the resource pack. By default,
     * the resource pack is zipped, but this can be changed to a different
     * handler through this method.
     *
     * @param packageHandler the package handler
     * @return this instance
     */
    public PackConverter packageHandler(@NotNull PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
        return this;
    }

    /**
     * Sets a list of action listeners for a specific conversion data class.
     * <p>
     * This is particularly useful for external programs that may rely on
     * various bits of information from the pack converter at different
     * stages.
     *
     * @param clazz the conversion data class
     * @param actionListeners the action listeners
     * @return this instance
     * @param <T> the conversion data type
     */
    public <T extends ConversionData> PackConverter actionListeners(@NotNull Class<T> clazz, @NotNull ActionListener<T>... actionListeners) {
        this.actionListeners.put(clazz, List.of(actionListeners));
        return this;
    }

    /**
     * Sets the action listeners.
     * <p>
     * This is particularly useful for external programs that may rely on
     * various bits of information from the pack converter at different
     * stages.
     *
     * @param actionListeners the action listeners
     * @return this instance
     * @param <T> the conversion data type
     */
    public <T extends ConversionData> PackConverter actionListeners(@NotNull Map<Class<T>, List<ActionListener<T>>> actionListeners) {
        for (Map.Entry<Class<T>, List<ActionListener<T>>> entry : actionListeners.entrySet()) {
            this.actionListeners.put(entry.getKey(), (List) entry.getValue());
        }

        return this;
    }

    /**
     * Sets the post processor for the converted resource pack.
     * <p>
     * This is called after the pack conversion is complete, but
     * before the pack is packaged.
     *
     * @param postProcessor the post processor
     * @return this instance
     */
    public PackConverter postProcessor(@NotNull BiConsumer<ResourcePack, BedrockResourcePack> postProcessor) {
        this.postProcessor = postProcessor;
        return this;
    }

    /**
     * Convert all resources in the pack using the converters
     *
     * @return this instance
     * @throws IOException if an I/O error occurs
     */
    public PackConverter convert() throws IOException {
        if (this.input == null) {
            throw new NullPointerException("Input cannot be null");
        }

        if (this.output == null) {
            throw new NullPointerException("Output cannot be null");
        }

        // Load any image plugins
        ImageIO.scanForPlugins();

        ZipUtils.openFileSystem(this.input, this.compressed, input -> {
            this.tmpDir = this.output.toAbsolutePath().getParent().resolve(this.output.getFileName() + "_mcpack/");

            if (this.converters.isEmpty()) {
                throw new IllegalStateException("No converters have been added");
            }

            ResourcePack javaResourcePack = this.compressed ? MinecraftResourcePackReader.minecraft().readFromZipFile(this.input) : MinecraftResourcePackReader.minecraft().read(NioDirectoryFileTreeReader.read(this.input));
            BedrockResourcePack bedrockResourcePack = new BedrockResourcePack(this.tmpDir);

            int errors = 0;
            for (Converter converter : this.converters) {
                ConversionData data = converter.createConversionData(this, input, this.tmpDir);
                PackConversionContext<?> context = new PackConversionContext<>(data, this, javaResourcePack, bedrockResourcePack, this.logListener);

                List<ActionListener<?>> actionListeners = this.actionListeners.getOrDefault(data.getClass(), List.of());
                try {
                    actionListeners.forEach(actionListener -> actionListener.preConvert((PackConversionContext) context));
                    converter.convert(context);
                    actionListeners.forEach(actionListener -> actionListener.postConvert((PackConversionContext) context));
                } catch (Throwable t) {
                    this.logListener.error("Error converting pack!", t);
                    errors++;
                }
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

    /**
     * Convert the temporary folder into the output zip
     *
     * @return this instance
     * @throws IOException if an I/O error occurs
     */
    public PackConverter pack() throws IOException {
        this.packageHandler.pack(this, this.tmpDir, this.output, this.logListener);
        this.cleanup();

        return this;
    }

    /**
     * Remove the temporary folder generated by the converter.
     * <p>
     * Silently fails.
     */
    private void cleanup() {
        try {
            PathUtils.delete(tmpDir);
        } catch (IOException ignored) {
        }
    }
}
