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

package org.geysermc.pack.converter.type.texture.transformer.type.ui;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

@AutoService(TextureTransformer.class)
public class IconTransformer implements TextureTransformer {
    private static final Key HELMET = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/helmet.png");
    private static final Key CHESTPLATE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/chestplate.png");
    private static final Key LEGGINGS = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/leggings.png");
    private static final Key BOOTS = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/boots.png");
    private static final Key BEDROCK_ARMOR = Key.key(Key.MINECRAFT_NAMESPACE, "ui/armors_slot_overlay.png");

    private static final Key SWORD = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/sword.png");
    private static final Key SPEAR = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/spear.png");
    private static final Key PICKAXE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/pickaxe.png");
    private static final Key AXE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/axe.png");
    private static final Key SHOVEL = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/shovel.png");
    private static final Key HOE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/hoe.png");
    private static final Key HORSE_ARMOR = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/horse_armor.png");
    private static final Key NAUTILUS_ARMOR = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/nautilus_armor.png");
    private static final Key BEDROCK_ARMOR_TOOLS = Key.key(Key.MINECRAFT_NAMESPACE, "ui/armors_and_tools_slot_overlay.png");

    private static final Key AMETHYST_SHARD = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/amethyst_shard.png");
    private static final Key DIAMOND = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/diamond.png");
    private static final Key EMERALD = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/emerald.png");
    private static final Key INGOT = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/ingot.png");
    private static final Key LAPIS_LAZULI = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/lapis_lazuli.png");
    private static final Key QUARTZ = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/quartz.png");
    private static final Key REDSTONE_DUST = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/redstone_dust.png");
    private static final Key BEDROCK_LAPIS_LAZULI = Key.key(Key.MINECRAFT_NAMESPACE, "ui/lapis_lazuli.png");
    private static final Key BEDROCK_IMAGE_LAPIS_LAZULI = Key.key(Key.MINECRAFT_NAMESPACE, "ui/lapis_lazuli_image.png"); // ???
    private static final Key BEDROCK_MATERIALS = Key.key(Key.MINECRAFT_NAMESPACE, "ui/smithing_material_slot_overlay.png");

    private static final Key BANNER = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/banner.png");
    private static final Key DYE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/dye.png");
    private static final Key BANNER_PATTERN = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/banner_pattern.png");
    private static final Key BEDROCK_BANNER = Key.key(Key.MINECRAFT_NAMESPACE, "ui/loom_banner_empty.png");
    private static final Key BEDROCK_DYE = Key.key(Key.MINECRAFT_NAMESPACE, "ui/loom_dye_empty.png");
    private static final Key BEDROCK_BANNER_PATTERN = Key.key(Key.MINECRAFT_NAMESPACE, "ui/loom_pattern_item_empty.png");

    private static final Key ARMOR_TRIM = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/smithing_template_armor_trim.png");
    private static final Key NETHERITE_UPGRADE = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/smithing_template_netherite_upgrade.png");
    private static final Key BEDROCK_TEMPLATES = Key.key(Key.MINECRAFT_NAMESPACE, "ui/templates_slot_overlay.png");

    private static final Key BREWING_FUEL = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/brewing_fuel.png");
    private static final Key BEDROCK_BREWING_FUEL = Key.key(Key.MINECRAFT_NAMESPACE, "ui/brewing_fuel_empty.png");

    private static final Key LLAMA_ARMOR = Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/container/slot/llama_armor.png");
    private static final Key BEDROCK_LLAMA_ARMOR = Key.key(Key.MINECRAFT_NAMESPACE, "ui/empty_llama_slot_carpet.png");

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        horizontalTransform(
                context, false, BEDROCK_ARMOR,
                HELMET,  CHESTPLATE, LEGGINGS, BOOTS
        );
        horizontalTransform(
                context, true, BEDROCK_ARMOR_TOOLS,
                HELMET, SWORD, CHESTPLATE, PICKAXE, LEGGINGS, AXE,
                BOOTS, HOE, SPEAR, SHOVEL, HORSE_ARMOR, NAUTILUS_ARMOR
        );
        imageAddTransform(
                context, false, BEDROCK_LAPIS_LAZULI, BEDROCK_IMAGE_LAPIS_LAZULI,
                LAPIS_LAZULI
        );
        horizontalTransform(
                context, true, BEDROCK_MATERIALS,
                INGOT, REDSTONE_DUST, LAPIS_LAZULI, QUARTZ, DIAMOND,
                EMERALD, AMETHYST_SHARD
        );
        passThroughTransform(context, true, BEDROCK_BANNER, BANNER);
        passThroughTransform(context, true, BEDROCK_DYE, DYE);
        passThroughTransform(context, true, BEDROCK_BANNER_PATTERN, BANNER_PATTERN);
        horizontalTransform(
                context, true, BEDROCK_TEMPLATES,
                NETHERITE_UPGRADE, ARMOR_TRIM
        );
        passThroughTransform(context, true, BEDROCK_BREWING_FUEL, BREWING_FUEL);
        passThroughTransform(context, true, BEDROCK_LLAMA_ARMOR, LLAMA_ARMOR);
    }

    private void passThroughTransform(@NotNull TransformContext context, boolean poll, Key bedrockOutput, Key javaInput) throws IOException {
        if (!context.isTexturePresent(javaInput)) return;

        BufferedImage img = this.readImage(poll ? context.poll(javaInput) : context.peek(javaInput));

        context.offer(bedrockOutput, img, "png");
    }

    // Not entirely sure what an image version of an icon is, but it has a bg soooo
    private void imageAddTransform(@NotNull TransformContext context, boolean poll, Key bedrockOutput, Key bedrockImageOutput, Key javaInput) throws IOException {
        if (!context.isTexturePresent(javaInput)) return;

        BufferedImage img1 = this.readImage(poll ? context.poll(javaInput) : context.peek(javaInput));

        context.offer(bedrockOutput, img1, "png");

        BufferedImage img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img2.getGraphics();
        graphics.setColor(new Color(139, 139, 139));
        graphics.drawRect(0, 0, img2.getWidth(), img2.getHeight());
        graphics.drawImage(img1, 0, 0, null);

        context.offer(bedrockImageOutput, img2, "png");
    }

    private void horizontalTransform(@NotNull TransformContext context, boolean poll, Key bedrockOutput, Key... javaInputs) throws IOException {
        boolean exists = false;

        for (Key javaInput : javaInputs) {
            if (context.isTexturePresent(javaInput)) {
                exists = true;
                break;
            }
        }

        if (!exists) return;

        List<Texture> textures = Arrays.stream(javaInputs)
                .map(key -> poll ? context.pollOrPeekVanilla(key) : context.peekOrVanilla(key))
                .filter(Objects::nonNull).toList();

        List<BufferedImage> images = new ArrayList<>(textures.size());

        for (Texture texture : textures) {
            images.add(this.readImage(texture));
        }

        float maxScale = (float) images.stream()
                .flatMapToDouble(img -> DoubleStream.of(img.getWidth() / 16f))
                .max().orElseThrow();

        BufferedImage bedrockOutputImage = new BufferedImage((int) (maxScale * 16 * images.size()), (int) (maxScale * 16), BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = bedrockOutputImage.getGraphics();

        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            float scale = maxScale / (image.getWidth() / 16f);

            graphics.drawImage(
                    ImageUtil.scale(image, scale),
                    (int) (maxScale * 16 * i),
                    0, null
            );
        }

        context.offer(bedrockOutput, bedrockOutputImage, "png");
    }
}
