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
import org.geysermc.pack.converter.converter.Converters;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] arguments) throws IOException {
        List<String> args = Arrays.asList(arguments);
        boolean debug = args.contains("debug");

        if (args.contains("nogui")) {
            if (!args.contains("--input")) {
                throw new IllegalArgumentException("No input provided.");
            } else if (args.indexOf("--input") + 1 >= args.size()) {
                throw new IllegalArgumentException("Input specified with no value.");
            }

            String inputPath = args.get(args.indexOf("--input") + 1);

            String outputPath;

            if (args.contains("--output")) {
                if (args.indexOf("--output") + 1 >= args.size()) {
                    throw new IllegalArgumentException("Output specified with no value.");
                }

                outputPath = args.get(args.indexOf("--output") + 1);
            } else {
                outputPath = inputPath.substring(0, inputPath.length() - 4) + ".mcpack";
            }

            new PackConverter()
                    .input(Path.of(inputPath))
                    .output(Path.of(outputPath))
                    .converters(Converters.defaultConverters(debug))
                    .convert()
                    .pack();
        } else {
            FlatArcDarkIJTheme.setup();
            new ThunderGUI(debug);
        }
    }
}
