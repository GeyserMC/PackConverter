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

package org.geysermc.packconverter.api;

import org.geysermc.packconverter.ZipUtils;
import org.geysermc.packconverter.api.converters.AbstractConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackConverter {

    private Path input;
    private Path output;

    private Path tmpDir;

    public PackConverter(Path input, Path output) throws IOException {
        this.input = input;
        this.output = output;

        // Load any image plugins
        ImageIO.scanForPlugins();

        // Extract the zip to a temp location
        tmpDir = input.toAbsolutePath().getParent().resolve(input.getFileName() + "_mcpack/");
        ZipFile zipFile = new ZipFile(input.toFile());

        ZipEntry entry;
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();

            if (!entry.isDirectory()) {
                File newFile = tmpDir.resolve(entry.getName()).toFile();
                newFile.getParentFile().mkdirs();

                InputStream fileStream = zipFile.getInputStream(entry);
                FileOutputStream outStream = new FileOutputStream(newFile);

                byte[] buf = new byte[fileStream.available()];
                int length;
                while ((length = fileStream.read(buf)) != -1) {
                    outStream.write(buf, 0, length);
                }

                outStream.flush();
                outStream.close();
            }
        }


    }

    public void convert() {
        List<AbstractConverter> additionalConverters = new ArrayList<>();

        for (Class<? extends AbstractConverter> converterClass : ConveterHandler.converterList) {
            try {
                List<Object[]> defaultData = (List<Object[]>) converterClass.getMethod("getDefaultData").invoke(null);

                AbstractConverter converter;
                for (Object[] data : defaultData) {
                    converter = converterClass.getDeclaredConstructor(Path.class, Object[].class).newInstance(tmpDir, data);

                    additionalConverters.addAll(converter.convert());
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) { }
        }

        for (AbstractConverter converter : additionalConverters) {
            converter.convert();
        }
    }

    public void pack() {
        ZipUtils zipUtils = new ZipUtils(tmpDir.toFile());
        zipUtils.generateFileList();
        zipUtils.zipIt(output.toString());
    }
}
