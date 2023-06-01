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

package org.geysermc.pack.converter.converter.texture.transformer.type.particle;

import com.google.auto.service.AutoService;
import org.geysermc.pack.converter.converter.texture.transformer.TextureTransformer;

@AutoService(TextureTransformer.class)
public class SculkSoulParticleTransformer extends SpritesheetParticleTransformer {
    private static final String PARTICLE_INPUT = "particle/sculk_soul_%s.png";
    private static final String PARTICLE_OUTPUT = "particle/sculk_soul.png";
    private static final String VANILLA_SPRITESHEET = "sculk_soul_spritesheet";
    private static final int ATLAS_COUNT = 11;

    public SculkSoulParticleTransformer() {
        super(PARTICLE_INPUT, PARTICLE_OUTPUT, VANILLA_SPRITESHEET, ATLAS_COUNT);
    }
}
