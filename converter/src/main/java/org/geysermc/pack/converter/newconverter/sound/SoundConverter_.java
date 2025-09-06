/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.newconverter.sound;

import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.SoundDefinitions;
import org.geysermc.pack.bedrock.resource.sounds.sounddefinitions.Sounds;
import org.geysermc.pack.converter.newconverter.ConversionContext;
import org.geysermc.pack.converter.newconverter.NamespacedAssetConverter;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.sound.SoundEntry;
import team.unnamed.creative.sound.SoundEvent;
import team.unnamed.creative.sound.SoundRegistry;

import java.util.HashMap;
import java.util.Map;

public class SoundConverter_ implements NamespacedAssetConverter<SoundRegistry, Map<String, SoundDefinitions>> {
    private static final String BEDROCK_SOUNDS_LOCATION = "sounds";

    @Override
    public @Nullable Map<String, SoundDefinitions> convert(SoundRegistry soundRegistry, ConversionContext context) throws Exception {
        Map<String, SoundDefinitions> definitions = new HashMap<>();

        for (SoundEvent value : soundRegistry.sounds()) {
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

            definitions.put(key, definition);
        }

        return definitions;
    }
}
