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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.converter.texture.transformer.TransformContext;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// This isn't really a "transformer", we just include some files if an certain UI elements are present
// in order to get it to appear correctly, the mappings here are still done in textures.json
// Credit to Bedrock Tweaks, wouldn't know how to do this without their packs
@AutoService(TextureTransformer.class)
public class UISizeTransformer implements TextureTransformer {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final JsonArray XPSLICE = new JsonArray();
    private static final JsonArray LOCATORSLICE = new JsonArray();

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture emptyXp = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/experience_bar_background.png"));
        if (emptyXp != null) {
            writeUiJson(context, this.readImage(emptyXp), "experiencebarempty", XPSLICE);

            // Since we have the full image, we *don't* want this
            BufferedImage nubImage = new BufferedImage(11, 5, BufferedImage.TYPE_INT_ARGB);
            context.offer(Key.key(Key.MINECRAFT_NAMESPACE, "ui/experiencenub.png"), nubImage, "png");
        }

        Texture fullXp = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/experience_bar_progress.png"));
        if (fullXp != null) {
            writeUiJson(context, this.readImage(fullXp), "experiencebarfull", XPSLICE);
        }

        Texture locatorBg = context.peek(Key.key(Key.MINECRAFT_NAMESPACE, "gui/sprites/hud/locator_bar_background.png"));
        if (locatorBg != null) {
            writeUiJson(context, this.readImage(locatorBg), "locator_bg", LOCATORSLICE);
        }
    }

    private void writeUiJson(TransformContext context, BufferedImage image, String jsonName, JsonArray nineSlice) {
        JsonObject rootObject = new JsonObject();
        rootObject.add("nineslice_size", nineSlice);

        JsonArray baseSize = new JsonArray();
        baseSize.add(image.getWidth());
        baseSize.add(image.getHeight());
        rootObject.add("base_size", baseSize);

        context.bedrockResourcePack().addExtraFile(GSON.toJson(rootObject).getBytes(StandardCharsets.UTF_8), "textures/ui/%s.json".formatted(jsonName));
    }

    static {
        XPSLICE.add(1);
        XPSLICE.add(0);
        XPSLICE.add(1);
        XPSLICE.add(0);

        LOCATORSLICE.add(5);
        LOCATORSLICE.add(1);
        LOCATORSLICE.add(5);
        LOCATORSLICE.add(1);
    }
}
