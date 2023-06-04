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

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.Getter;
import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.converter.ActionListener;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.data.ConversionData;
import org.geysermc.pack.converter.util.DefaultLogListener;
import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackReader;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class PackConverter {
    private Path input;
    private Path output;

    private final Map<Class<?>, List<ActionListener<?>>> actionListeners = new IdentityHashMap<>();

    @Getter
    private final Map<String, Int2ObjectMap<String>> customModelData = new HashMap<>();

    private final List<Converter<?>> converters = new ArrayList<>();

    private Path tmpDir;

    private PackageHandler packageHandler = PackageHandler.ZIP;
    private LogListener logListener = new DefaultLogListener();

    public PackConverter input(@NotNull Path input) {
        this.input = input;
        return this;
    }

    public PackConverter output(@NotNull Path output) {
        this.output = output;
        return this;
    }

    public PackConverter converter(@NotNull Converter<?> converter) {
        this.converters.add(converter);
        return this;
    }

    public PackConverter converters(@NotNull List<Converter<?>> converters) {
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

    public <T extends ConversionData> PackConverter actionListeners(@NotNull Class<T> clazz, @NotNull ActionListener<T>... actionListeners) {
        this.actionListeners.put(clazz, List.of(actionListeners));
        return this;
    }

    public <T extends ConversionData> PackConverter actionListeners(@NotNull Map<Class<T>, List<ActionListener<T>>> actionListeners) {
        for (Map.Entry<Class<T>, List<ActionListener<T>>> entry : actionListeners.entrySet()) {
            this.actionListeners.put(entry.getKey(), (List) entry.getValue());
        }

        return this;
    }

    /**
     * Convert all resources in the pack using the converters
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

        try (FileSystem compressedFileSystem = FileSystems.newFileSystem(this.input, Collections.emptyMap())) {
            Path input = compressedFileSystem.getPath("/");

            this.tmpDir = this.input.toAbsolutePath().getParent().resolve(this.output.getFileName() + "_mcpack/");

            if (this.converters.isEmpty()) {
                throw new IllegalStateException("No converters have been added");
            }

            ResourcePack javaResourcePack = MinecraftResourcePackReader.minecraft().readFromZipFile(this.input);
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

            bedrockResourcePack.export();

            if (errors > 0) {
                this.logListener.warn("Pack conversion completed with " + errors + " errors!");
            } else {
                this.logListener.info("Pack conversion completed successfully!");
            }
        }

        return this;
    }

    /**
     * Convert the temporary folder into the output zip
     */
    public PackConverter pack() throws IOException {
        this.packageHandler.pack(this, this.tmpDir, this.output, this.logListener);
        this.cleanup();

        return this;
    }

    /**
     * Remove the temporary folder generated by the converter.
     * Silently fails.
     */
    private void cleanup() {
        try {
            Files.delete(tmpDir);
        } catch (IOException ignored) {
        }
    }
}
