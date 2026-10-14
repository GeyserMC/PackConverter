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

package org.geysermc.pack.converter.type.texture.transformer.type.particle;

import java.awt.image.BufferedImage;

public abstract class BaseLeafParticleTransformer extends GridSpritesheetParticleTransformer {
    public BaseLeafParticleTransformer(String bedrockPath, String javaPath) {
        super(bedrockPath, javaPath, 3, 4, 7, 7);
    }

    public BaseLeafParticleTransformer(String bedrockPath, int rows, int columns, String... javaPaths) {
        super(bedrockPath, rows, columns, 7, 7, javaPaths);
    }

    @Override
    public BufferedImage preProcessImage(BufferedImage image) {
        BufferedImage canvas = new BufferedImage(image.getWidth() + 2, image.getHeight() + 2, BufferedImage.TYPE_INT_ARGB);
        canvas.getGraphics().drawImage(image, 1, 1, null);
        return canvas;
    }
}
