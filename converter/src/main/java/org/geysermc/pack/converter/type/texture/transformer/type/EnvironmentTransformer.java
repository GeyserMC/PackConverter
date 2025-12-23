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

package org.geysermc.pack.converter.type.texture.transformer.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@AutoService(TextureTransformer.class)
public class EnvironmentTransformer implements TextureTransformer {
    private static final Key NEW_MOON = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/new_moon.png");
    private static final Key WAXING_CRESCENT = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/waxing_crescent.png");
    private static final Key FIRST_QUARTER = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/first_quarter.png");
    private static final Key WAXING_GIBBOUS = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/waxing_gibbous.png");
    private static final Key FULL_MOON = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/full_moon.png");
    private static final Key WANING_GIBBOUS = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/waning_gibbous.png");
    private static final Key THIRD_QUARTER = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/third_quarter.png");
    private static final Key WANING_CRESCENT = Key.key(Key.MINECRAFT_NAMESPACE, "environment/celestial/moon/waning_crescent.png");
    private static final Key MOON_PHASES = Key.key(Key.MINECRAFT_NAMESPACE, "environment/moon_phases.png");

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        gridTransform(
                context, true, 2, 4, MOON_PHASES,
                FULL_MOON, WANING_GIBBOUS, THIRD_QUARTER, WANING_CRESCENT,
                NEW_MOON, WAXING_CRESCENT, FIRST_QUARTER, WAXING_GIBBOUS
        );
    }
}
