/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
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

package org.geysermc.packconverter.api.converters;

import lombok.Getter;
import org.geysermc.packconverter.api.PackConverter;
import org.geysermc.packconverter.api.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AtlasConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"textures/items/clock_", 63, "textures/items/watch_atlas.png"});
        defaultData.add(new Object[] {"textures/items/compass_", 31, "textures/items/compass_atlas.png"});
    }

    public AtlasConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String base = (String) this.data[0];
            int count = (int) this.data[1];
            String to = (String) this.data[2];
            
            BufferedImage atlasImage = null;

            for (int i = 0; i <= count; i++) {
                String step = base + String.format("%1$2s", i).replace(" ", "0") + ".png";
                File stepFile = storage.resolve(step).toFile();
                
                if (!stepFile.exists()) {
                    continue;
                }

                BufferedImage stepImage = ImageIO.read(stepFile);
                
                if (atlasImage == null) {
                    packConverter.log(String.format("Create atlas %s", to));

                    atlasImage = new BufferedImage(stepImage.getWidth(), stepImage.getHeight() * (count + 1), BufferedImage.TYPE_INT_ARGB);
                }

                atlasImage.getGraphics().drawImage(stepImage, 0, (stepImage.getHeight() * i), null);

                delete.add(new DeleteConverter(packConverter, storage, new Object[] {step}));
            }

            if (atlasImage != null) {
                ImageUtils.write(atlasImage, "png", storage.resolve(to).toFile());
            }
        } catch (IOException e) { }

        return delete;
    }
}
