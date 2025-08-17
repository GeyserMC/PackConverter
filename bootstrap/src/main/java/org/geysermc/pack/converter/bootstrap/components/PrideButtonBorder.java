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

import java.awt.*;
import java.awt.geom.Point2D;

public class PrideButtonBorder extends DefaultButtonBorder {
    @Override
    public Color[] getColors(Component c) {
        if (c.isFocusOwner()) {
            return new Color[]{
                    new Color(244, 124, 124),
                    new Color(255, 194, 104),
                    new Color(247, 244, 139),
                    new Color(161, 222, 147),
                    new Color(112, 161, 215),
                    new Color(149, 125, 173)
            };
        } else if (c.isEnabled()) {
            return new Color[]{
                    new Color(151, 162, 168, 120),
                    new Color(124, 136, 143, 120)
            };
        } else {
            return new Color[]{
                    new Color(244, 124, 124, 120),
                    new Color(255, 194, 104, 120),
                    new Color(247, 244, 139, 120),
                    new Color(161, 222, 147, 120),
                    new Color(112, 161, 215, 120),
                    new Color(149, 125, 173, 120)
            };
        }
    }

    @Override
    public Point2D getEndPoint(Component c) {
        return new Point2D.Float(c.getWidth(), c.getHeight());
    }
}
