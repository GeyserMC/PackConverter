/*
 * Copyright (c) 2026 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture.transformer.type.entity;

import com.google.auto.service.AutoService;
import net.kyori.adventure.key.Key;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.KeyUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@AutoService(TextureTransformer.class)
public class SulfurCubeTntTransformer implements TextureTransformer {
    private final Key TNT_TOP = KeyUtil.key("minecraft", "block/tnt_top.png");
    private final Key TNT_BOTTOM = KeyUtil.key("minecraft", "block/tnt_bottom.png");
    private final Key TNT_SIDE = KeyUtil.key("minecraft", "block/tnt_side.png");

    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        if (!context.isTexturePresent(TNT_TOP) && !context.isTexturePresent(TNT_BOTTOM) && !context.isTexturePresent(TNT_SIDE)) return;

        Texture tntTopTexture = context.peekOrVanilla(TNT_TOP);
        BufferedImage tntTopImage = this.readImage(tntTopTexture);
        Texture tntBottomTexture = context.peekOrVanilla(TNT_BOTTOM);
        BufferedImage tntBottomImage = this.readImage(tntBottomTexture);
        Texture tntSideTexture = context.peekOrVanilla(TNT_SIDE);
        BufferedImage tntSideImage = this.readImage(tntSideTexture);

        float scale = tntTopImage.getWidth() / 8f;

        BufferedImage result = new BufferedImage((int) (scale * 128), (int) (scale * 128), BufferedImage.TYPE_INT_ARGB);
        Graphics g = result.getGraphics();

        g.drawImage(tntTopImage, (int) (scale * 16), (int) (scale * 36), null);
        g.drawImage(tntBottomImage, (int) (scale * 32), (int) (scale * 36), null);
        g.drawImage(tntSideImage, 0, (int) (scale * 52), null);
        g.drawImage(tntSideImage, (int) (scale * 16), (int) (scale * 52), null);
        g.drawImage(tntSideImage, (int) (scale * 32), (int) (scale * 52), null);
        g.drawImage(tntSideImage, (int) (scale * 48), (int) (scale * 52), null);

        context.offer(KeyUtil.key(Key.MINECRAFT_NAMESPACE, "entity/sulfur_cube/sulfur_cube_tnt.png"), result, "PNG");
    }
}
