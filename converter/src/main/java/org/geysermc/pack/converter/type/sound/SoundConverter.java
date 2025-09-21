/*
 * Copyright (c) 2025-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.sound;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.AssetCombiner;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.sound.Sound;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SoundConverter implements AssetConverter<Sound, Sound>, AssetCombiner<Sound> {
    public static final SoundConverter INSTANCE = new SoundConverter();

    @Override
    public @Nullable Sound convert(Sound sound, ConversionContext context) throws Exception {
        return sound;
    }

    @Override
    public void include(BedrockResourcePack pack, List<Sound> sounds, CombineContext context) {
        List<String> exported = new ArrayList<>();
        Path output = pack.directory().resolve(SoundRegistryConverter.BEDROCK_SOUNDS_LOCATION);

        for (Sound sound : sounds) {
            String path = sound.key().value();
            if (exported.contains(path)) {
                context.warn("Conflicting sound file " + sound.key() + "!");
                continue;
            }
            Path file = output.resolve(path + ".ogg");
            Path directory = file.getParent();
            try {
                Files.createDirectories(directory);
                try (OutputStream outputStream = new FileOutputStream(file.toFile())) {
                    sound.data().write(outputStream);
                }
            } catch (IOException exception) {
                context.error("Failed to write sound file " + sound.key() + "!", exception);
                continue;
            }
            exported.add(path);
        }
    }
}
