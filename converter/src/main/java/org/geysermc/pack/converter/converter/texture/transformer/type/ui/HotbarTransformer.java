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

package org.geysermc.pack.converter.converter.texture.transformer.type.ui;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class HotbarTransformer implements TextureTransformer {
    private static final String HOTBAR = "gui/sprites/hud/hotbar.png";
    private static final String HOTBAR_SELECTION = "gui/sprites/hud/hotbar_selection.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture javaHotbarTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, HOTBAR));
        if (javaHotbarTexture == null) return;

        BufferedImage javaHotbarImage = readImage(javaHotbarTexture);

        int scale = javaHotbarImage.getHeight() / 22;

        int height = javaHotbarImage.getHeight();

        // The texture is 1 wide, so 1 * scale = scale
        BufferedImage bedrockStartHotbar = new BufferedImage(scale, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = bedrockStartHotbar.getGraphics();

        g.drawImage(ImageUtil.crop(javaHotbarImage, 0, 0, scale, height), 0, 0, null);

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/hotbar_start_cap.png"), bedrockStartHotbar, "png");

        for (int i = 0; i <= 8; i++) {
            BufferedImage bedrockHotbarPart = new BufferedImage(scale * 20, height, BufferedImage.TYPE_INT_ARGB);

            Graphics graphics = bedrockHotbarPart.getGraphics();

            graphics.drawImage(ImageUtil.crop(javaHotbarImage, (i * 20 * scale) + scale, 0, scale * 20, height), 0, 0, null);

            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/hotbar_" + i + ".png"), bedrockHotbarPart, "png");
        }

        // The texture is 1 wide, so 1 * scale = scale
        BufferedImage bedrockEndHotbar = new BufferedImage(scale, height, BufferedImage.TYPE_INT_ARGB);

        g = bedrockEndHotbar.getGraphics();

        g.drawImage(ImageUtil.crop(javaHotbarImage, javaHotbarImage.getWidth() - scale, 0, scale, height), 0, 0, null);

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/hotbar_end_cap.png"), bedrockEndHotbar, "png");

        Texture javaHotbarSelectionTexture = context.poll(Key.key(Key.MINECRAFT_NAMESPACE, HOTBAR_SELECTION));
        if (javaHotbarSelectionTexture == null) return;

        BufferedImage javaHotbarSelectionImage = readImage(javaHotbarSelectionTexture);

        int selectionScale = javaHotbarSelectionImage.getWidth() / 24;

        int size = javaHotbarSelectionImage.getWidth();

        BufferedImage bedrockHotbarSelection = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        g = bedrockHotbarSelection.getGraphics();

        g.drawImage(javaHotbarSelectionImage, 0, 0, null);

        g.drawImage(ImageUtil.flip(ImageUtil.crop(javaHotbarSelectionImage, 0, 0, size, selectionScale), false, true), 0, size - selectionScale, null);

        context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/selected_hotbar_slot.png"), bedrockHotbarSelection, "png");
    }
}
