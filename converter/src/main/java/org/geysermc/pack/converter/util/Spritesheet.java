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

package org.geysermc.pack.converter.util;

import org.jetbrains.annotations.NotNull;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Util for creating spritesheets in code and
 * writing them to disk.
 */
public class Spritesheet {
    private final List<Image[]> rows = new ArrayList<>();

    public void addRow(@NotNull Image... sprites) {
        this.rows.add(sprites);
    }

    public boolean hasSprites() {
        return !this.rows.isEmpty();
    }

    /**
     * Compiles all the sprites into a single spritesheet image.
     *
     * @return the compiled spritesheet image
     */
    @NotNull
    public BufferedImage compile() {
        int width = 0;
        int height = 0;

        for (Image[] row : this.rows) {
            int rowWidth = 0;
            for (Image sprite : row) {
                rowWidth += sprite.getWidth(null);
            }

            width = Math.max(width, rowWidth);
            height += firstPresent(row).getHeight(null);
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int y = 0;
        for (Image[] row : this.rows) {
            int x = 0;
            for (Image sprite : row) {
                image.getGraphics().drawImage(sprite, x, y, null);
                x += sprite.getWidth(null);
            }

            y += firstPresent(row).getHeight(null);
        }

        return image;
    }

    @NotNull
    private static Image firstPresent(Image[] row) {
        for (Image sprite : row) {
            if (sprite != null) {
                return sprite;
            }
        }

        throw new IllegalArgumentException("No sprites present in row");
    }
}
