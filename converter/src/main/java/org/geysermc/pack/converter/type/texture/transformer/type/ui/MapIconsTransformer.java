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

package org.geysermc.pack.converter.type.texture.transformer.type.ui;

import com.google.auto.service.AutoService;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@AutoService(TextureTransformer.class)
public class MapIconsTransformer implements TextureTransformer {
    @Override
    public void transform(@NotNull TransformContext context) throws IOException {
        this.gridTransform(
                context, true, 4, 4, "map/map_icons.png",
                "map/decorations/player.png", "map/decorations/frame.png", "map/decorations/red_marker.png", "map/decorations/blue_marker.png",
                "map/decorations/red_x.png", "map/decorations/target_point.png", "map/decorations/player_off_map.png", null,
                null, null, null, null, // TODO Colorise player, god damn you bedrock
                null, "map/decorations/player_off_limits.png", "map/decorations/woodland_mansion.png", "map/decorations/ocean_monument.png"
        );
    }
}
