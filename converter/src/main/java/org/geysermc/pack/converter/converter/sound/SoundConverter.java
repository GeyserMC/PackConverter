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

package org.geysermc.pack.converter.converter.sound;

import com.google.auto.service.AutoService;
import org.apache.commons.io.file.PathUtils;
import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions;
import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.Sounds;
import org.geysermc.pack.converter.Constants;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.BaseConverter;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.data.BaseConversionData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import team.unnamed.creative.sound.Sound;
import team.unnamed.creative.sound.SoundEntry;
import team.unnamed.creative.sound.SoundEvent;
import team.unnamed.creative.sound.SoundRegistry;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Map;

@AutoService(Converter.class)
public class SoundConverter extends BaseConverter {
    private static final String JAVA_SOUNDS_LOCATION = Constants.JAVA_PACK_LOCATION + "/sounds";
    private static final String BEDROCK_SOUNDS_LOCATION = "sounds";

    @Override
    public void convert(@NotNull PackConversionContext<BaseConversionData> context) throws Exception {
        Collection<SoundRegistry> registry = context.javaResourcePack().soundRegistries();
        for (SoundRegistry soundRegistry : registry) {
            @Unmodifiable @NotNull Collection<SoundEvent> sounds = soundRegistry.sounds();

            for (SoundEvent value : sounds) {
                String key = value.key().asString();

                SoundDefinitions definition = new SoundDefinitions();
                definition.useLegacyMaxDistance(true); // TODO: Needed?
                definition.maxDistance(64); // ???
                for (SoundEntry sound : value.sounds()) {
                    Sounds bedrockSound = new Sounds();
                    bedrockSound.name(BEDROCK_SOUNDS_LOCATION + "/" + sound.key().value());
                    bedrockSound.stream(sound.stream());
                    bedrockSound.loadOnLowMemory(true);
                    bedrockSound.volume(sound.volume());
                    bedrockSound.pitch(sound.pitch());
                    bedrockSound.weight(sound.weight());

                    definition.sounds().add(bedrockSound);
                }

                context.bedrockResourcePack().addSoundDefinition(key, definition);
            }

            // Relocate sound files
            Path input = context.inputDirectory().resolve(String.format(JAVA_SOUNDS_LOCATION, soundRegistry.namespace()));
            Path output = context.outputDirectory().resolve(BEDROCK_SOUNDS_LOCATION);

            if (Files.notExists(output)) {
                Files.createDirectories(output);
            }

            PathUtils.copyDirectory(input, output, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
