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

package org.geysermc.pack.converter.converter.texture.transformer.bulk.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.bulk.BulkTransformContext;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@AutoService(BulkTextureTransformer.class)
public class AtlasTransformer implements BulkTextureTransformer {
    private static final List<AtlasData> ATLASES = List.of(
            new AtlasData("item/clock_%s.png", "items/watch_atlas.png", 63),
            new AtlasData("item/compass_%s.png", "items/compass_atlas.png", 31),
            new AtlasData("item/recovery_compass_%s.png", "items/recovery_compass_atlas.png", 31)
    );

    @Override
    public void transform(@NotNull BulkTransformContext context) throws IOException {
        for (AtlasData atlas : ATLASES) {
            String javaName = atlas.javaName();
            String bedrockName = atlas.bedrockName();
            int atlasCount = atlas.altasCount();

            BufferedImage atlasImage = null;
            for (int i = 0; i <= atlasCount; i++) {
                Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, String.format(javaName, String.format("%1$2s", i).replace(" ", "0"))));
                if (texture == null) {
                    continue;
                }

                BufferedImage stepImage = ImageIO.read(new ByteArrayInputStream(texture.data().toByteArray()));
                if (atlasImage == null) {
                    context.info(String.format("Creating atlas %s", bedrockName));
                    atlasImage = new BufferedImage(stepImage.getWidth(), stepImage.getHeight() * (atlasCount + 1), BufferedImage.TYPE_INT_ARGB);
                }

                atlasImage.getGraphics().drawImage(stepImage, 0, (stepImage.getHeight() * i), null);
            }

            if (atlasImage != null) {
                context.offer(Key.key(Key.MINECRAFT_NAMESPACE, bedrockName), atlasImage, "png");
                context.info(String.format("Created atlas %s", bedrockName));
            }
        }
    }

    record AtlasData(String javaName, String bedrockName, int altasCount) {
    }
}
