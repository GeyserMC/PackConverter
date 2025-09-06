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

package org.geysermc.pack.converter.bootstrap;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import org.geysermc.pack.converter.PackConverter;
import org.geysermc.pack.converter.converter.AssetConverters;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] arguments) throws IOException {
        List<String> args = Arrays.asList(arguments);
        boolean debug = args.contains("--debug") || args.contains("-d");

        if (args.contains("nogui")) {
            if (!args.contains("--input") || args.indexOf("--input") + 1 >= args.size()) {
                throw new IllegalArgumentException("No input provided.");
            }

            String inputPath = args.get(args.indexOf("--input") + 1);

            String outputPath;
            String packName;

            if (args.contains("--output")) {
                if (args.indexOf("--output") + 1 >= args.size()) {
                    throw new IllegalArgumentException("Output specified with no value.");
                }

                outputPath = args.get(args.indexOf("--output") + 1);
            } else {
                outputPath = inputPath.replaceFirst("[.][^.]+$", ".mcpack");
            }

            if (args.contains("--name")) {
                if (args.indexOf("--name") + 1 >= args.size()) {
                    throw new IllegalArgumentException("Name specified with no value.");
                }

                packName = args.get(args.indexOf("--name") + 1);
            } else {
                packName = inputPath.replaceFirst("[.][^.]+$", "");
            }

            System.setProperty("PackConverter.Debug", String.valueOf(debug));

            new PackConverter()
                    .enforcePackCheck(true)
                    .input(Path.of(inputPath))
                    .output(Path.of(outputPath))
                    .packName(packName)
                    .converters(AssetConverters.converters(debug))
                    .convert()
                    .pack();
        } else {
            FlatArcDarkIJTheme.setup();
            new ThunderGUI(debug);
        }
    }
}
