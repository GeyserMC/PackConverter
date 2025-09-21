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

package org.geysermc.pack.converter.type.texture.transformer.type.block;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class TallSeagrassTransformer implements TextureTransformer {
    private static final String JAVA_TOP = "block/tall_seagrass_top.png";
    private static final String JAVA_BOTTOM = "block/tall_seagrass_bottom.png";

    private static final String BEDROCK_TOP = "blocks/seagrass_doubletall_top_%s.png";
    private static final String BEDROCK_BOTTOM = "blocks/seagrass_doubletall_bottom_%s.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture javaTop = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_TOP));

        if (javaTop != null) {
            BufferedImage javaImage = this.readImage(javaTop);

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_TOP.formatted("a")), javaImage, "png");
            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_TOP.formatted("b")), ImageUtil.flip(javaImage, true, false), "png");
        }

        Texture javaBottom = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, JAVA_BOTTOM));

        if (javaBottom != null) {
            BufferedImage javaImage = this.readImage(javaBottom);

            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_BOTTOM.formatted("a")), javaImage, "png");
            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, BEDROCK_BOTTOM.formatted("b")), ImageUtil.flip(javaImage, true, false), "png");
        }
    }
}
