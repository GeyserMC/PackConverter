/*
 * Copyright (c) 2019-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.base;

import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.pipeline.ExtractionContext;
import org.geysermc.pack.converter.util.KeyUtil;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

import java.util.Optional;

public class PackIconConverter implements AssetConverter<Writable, byte[]> {
    public static final PackIconConverter INSTANCE = new PackIconConverter();
    private static final Key UNKNOWN_PACK = KeyUtil.key(Key.MINECRAFT_NAMESPACE, "misc/unknown_pack.png");

    public static Writable extractIcon(ResourcePack pack, ExtractionContext context) {
        Writable packIcon = pack.icon();
        if (packIcon == null) {
            Texture unknownPackOverride = pack.texture(UNKNOWN_PACK);
            if (unknownPackOverride != null) {
                packIcon = unknownPackOverride.data();
            } else {
                packIcon = context.vanillaPack()
                        .flatMap(vanilla -> Optional.ofNullable(vanilla.texture(UNKNOWN_PACK)))
                        .map(Texture::data)
                        .orElse(null);
            }
        }
        return packIcon;
    }

    @Override
    public byte[] convert(Writable writable, ConversionContext context) throws Exception {
        return writable.toByteArray();
    }
}
