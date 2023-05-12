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

package org.geysermc.packconverter.bootstrap;

import org.geysermc.packconverter.PackConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new AssertionError("Please choose a .zip file to convert");
        } else {
            Path packFile = Paths.get(args[0]);

            // Check the file exists
            if (!packFile.toFile().exists()) {
                throw new FileNotFoundException(String.format("Specified pack zip file not found (%s)", packFile.toString()));
            }

            // Check its a zip
            if (!packFile.toString().endsWith(".zip")) {
                throw new AssertionError(String.format("Specified pack is not a zip (%s)", packFile.toString()));
            }

            try {
                PackConverter packConverter = new PackConverter(packFile, Paths.get(packFile.toString().replaceFirst("[.][^.]+$", ".mcpack")));
                packConverter.convert();
                packConverter.pack();
                packConverter.cleanup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
