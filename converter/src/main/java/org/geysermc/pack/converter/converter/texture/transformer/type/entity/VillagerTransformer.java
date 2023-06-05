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

package org.geysermc.pack.converter.converter.texture.transformer.type.entity;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@AutoService(TextureTransformer.class)
public class VillagerTransformer implements TextureTransformer {
    private static final List<String> ENTITIES = List.of(
            "villager",
            "zombie_villager"
    );

    private static final List<String> VILLAGER_PROFESSIONS = List.of(
            "armorer",
            "butcher",
            "cartographer",
            "cleric",
            "farmer",
            "fisherman",
            "fletcher",
            "leatherworker",
            "librarian",
            "mason",
            "nitwit",
            "shepherd",
            "toolsmith",
            "weaponsmith"
    );

    private static final String TEXTURE_PATH = "entity/%s/profession/%s.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        for (String entity : ENTITIES) {
            for (String profession : VILLAGER_PROFESSIONS) {
                String texturePath = String.format(TEXTURE_PATH, entity, profession);
                Texture texture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, texturePath));
                if (texture == null) {
                    continue;
                }

                context.debug(String.format("Converting %s texture for profession %s", entity, profession));

                BufferedImage fromImage = this.readImage(texture);
                BufferedImage newImage = new BufferedImage(fromImage.getWidth(), fromImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Color blank = new Color(255, 255, 255, 0);
                for (int x = 0; x < newImage.getWidth(); x++) {
                    for (int y = 0; y < newImage.getHeight(); y++) {
                        Color c = new Color(fromImage.getRGB(x, y), true);
                        if (c.getAlpha() == 0) {
                            newImage.setRGB(x, y, blank.getRGB());
                        } else {
                            newImage.setRGB(x, y, c.getRGB());
                        }
                    }
                }

                context.offer(Key.key(Key.MINECRAFT_NAMESPACE, texturePath), newImage, "png");
            }
        }
    }
}
