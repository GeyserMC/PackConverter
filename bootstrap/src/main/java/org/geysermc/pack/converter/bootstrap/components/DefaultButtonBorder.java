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

package org.geysermc.pack.converter.bootstrap.components;

import com.formdev.flatlaf.ui.FlatButtonBorder;

import java.awt.*;
import java.awt.geom.Point2D;

public class DefaultButtonBorder extends FlatButtonBorder {
    @Override
    protected Paint getBorderColor(Component c) {
        Color[] colors = getColors(c);

        if (colors.length == 1) {
            return colors[0];
        }

        float size = ((float) 90 / colors.length) / 100;
        float[] sizes = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            sizes[i] = size * (i + 1);
        }

        return new LinearGradientPaint(getStartPoint(c), getEndPoint(c), sizes, colors);
    }

    @Override
    protected float getBorderWidth(Component c) {
        float base = super.getBorderWidth(c);
        if (c.isFocusOwner()) {
            return base * 2f;
        } else if (!c.isEnabled()) {
            return base;
        } else {
            return base * 0.5f;
        }
    }

    @Override
    protected int getFocusWidth(Component c) {
        return 0;
    }

    public Color[] getColors(Component c) {
        if (c.isFocusOwner()) {
            return new Color[]{
                    new Color(51, 140, 242),
                    new Color(35, 118, 213)
            };
        } else if (c.isEnabled()) {
            return new Color[]{
                    new Color(151, 162, 168, 120),
                    new Color(124, 136, 143, 120)
            };
        } else {
            return new Color[]{
                    new Color(151, 162, 168, 80),
                    new Color(124, 136, 143, 80)
            };
        }
    }

    public Point2D getStartPoint(Component ignoredC) {
        return new Point2D.Float(0, 0);
    }

    public Point2D getEndPoint(Component c) {
        return new Point2D.Float(0, c.getHeight());
    }
}
