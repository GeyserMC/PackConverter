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

package org.geysermc.pack.converter.type.texture.transformer.type;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class WeatherTransformer implements TextureTransformer {
    private static final String SNOW_INPUT = "environment/snow.png";
    private static final String RAIN_INPUT = "environment/rain.png";
    private static final String WEATHER_OUTPUT = "environment/weather.png";

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        Texture snowTexture = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, SNOW_INPUT));
        Texture rainTexture = context.poll(KeyUtil.key(Key.MINECRAFT_NAMESPACE, RAIN_INPUT));
        if (snowTexture == null || rainTexture == null) {
            return;
        }

        context.debug(String.format("Converting weather texture %s", WEATHER_OUTPUT));

        BufferedImage snowImage = this.readImage(snowTexture);
        BufferedImage rainImage = this.readImage(rainTexture);

        float factor = snowImage.getWidth() / 64f;

        BufferedImage weatherImage = new BufferedImage((int) (32 * factor), (int) (32 * factor), BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = weatherImage.getGraphics();

        // Snow
        graphics.drawImage(ImageUtil.cover(ImageUtil.crop(snowImage, snowImage.getWidth(), (int) (3 * factor)), weatherImage.getWidth(), (int) (3 * factor)), 0, 0, null);

        // Rain
        graphics.drawImage(ImageUtil.cover(ImageUtil.crop(rainImage, rainImage.getWidth(), (int) (5 * factor)), weatherImage.getWidth(), (int) (5 * factor)), 0, (int) (5 * factor), null);

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, WEATHER_OUTPUT), weatherImage, "png");
    }
}
