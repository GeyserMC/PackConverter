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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

// This isn't really a "transformer", we just include some files if an certain UI elements are present
// in order to get it to appear correctly, the mappings here are still done in textures.json or elsewhere
// We should do this before everything else as we poll locator bar bg
// Credit to Bedrock Tweaks, wouldn't know how to do this without their packs
@AutoService(TextureTransformer.class)
public class UISizeTransformer implements TextureTransformer {

    private static final JsonArray FULLBARSLICE = new JsonArray();

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture emptyXp = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/experience_bar_background.png"));
        if (emptyXp != null) {
            writeUiJson(context, this.readImage(emptyXp), "experiencebarempty", FULLBARSLICE);

            // Since we have the full image, we *don't* want this
            BufferedImage nubImage = new BufferedImage(11, 5, BufferedImage.TYPE_INT_ARGB);
            context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "ui/experiencenub.png"), nubImage, "png");
        }

        Texture fullXp = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/experience_bar_progress.png"));
        if (fullXp != null) {
            writeUiJson(context, this.readImage(fullXp), "experiencebarfull", FULLBARSLICE);
        }

        Texture locatorBg = context.peek(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/locator_bar_background.png"));
        if (locatorBg != null) {
            BufferedImage image = this.readImage(locatorBg);

            int scale = image.getWidth() / 12;

            writeUiJson(context, image, "locator_bg", FULLBARSLICE, scale * 182, image.getHeight());
        }
    }

    @Override
    public int order() {
        return TextureTransformer.ORDER_FIRST;
    }

    private void writeUiJson(TransformContext context, BufferedImage image, String jsonName, JsonArray nineSlice) {
        writeUiJson(context, image, jsonName, nineSlice, image.getWidth(), image.getHeight());
    }

    private void writeUiJson(TransformContext context, BufferedImage image, String jsonName, JsonArray nineSlice, int customWidth, int customHeight) {
        JsonObject rootObject = new JsonObject();
        rootObject.add("nineslice_size", nineSlice);

        JsonArray baseSize = new JsonArray();
        baseSize.add(customWidth);
        baseSize.add(customHeight);
        rootObject.add("base_size", baseSize);

        context.bedrockResourcePack().addExtraFile(rootObject, "textures/ui/%s.json".formatted(jsonName));
    }

    static {
        FULLBARSLICE.add(1);
        FULLBARSLICE.add(0);
        FULLBARSLICE.add(1);
        FULLBARSLICE.add(0);
    }
}
