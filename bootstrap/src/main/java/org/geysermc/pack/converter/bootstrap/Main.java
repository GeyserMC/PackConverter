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
import com.twelvemonkeys.image.BufferedImageIcon;
import org.geysermc.pack.converter.PackConverter;
import org.geysermc.pack.converter.converter.Converters;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.ZipUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private Path javaPackPath = null;
    private Path output = null;

    private final JFrame frame;
    private final AtomicBoolean converting = new AtomicBoolean(false);
    private final AtomicLong startTime = new AtomicLong(0);
    private final BootstrapLogListener logListener;

    public static void main(String[] arguments) throws IOException {
        List<String> args = Arrays.asList(arguments);
        boolean debug = args.contains("debug");

        if (args.contains("nogui")) {
            if (!args.contains("--input")) {
                throw new IllegalArgumentException("No input provided.");
            }

            String inputPath = args.get(args.indexOf("--input") + 1);

            new PackConverter()
                    .input(Path.of(inputPath))
                    .output(Path.of(inputPath.substring(0, inputPath.length() - 4)))
                    .converters(Converters.defaultConverters(debug))
                    .convert()
                    .pack();
        } else {
            new Main(debug);
        }
    }

    private Main(boolean debug) throws IOException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        FlatArcDarkIJTheme.setup();

        frame = new JFrame();
        frame.setTitle("Thunder");
        InputStream iconStream = Main.class.getResourceAsStream("/icon.png");
        if (iconStream != null) frame.setIconImage(ImageIO.read(iconStream));
        frame.setSize(800, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JLabel dataLabel = new JLabel("Input: None | Output: None");
        dataLabel.setBounds(225, 20, 535, 50);
        frame.add(dataLabel);

        JTextArea outputArea = new JTextArea("");
        outputArea.setEditable(false);
        outputArea.setFocusable(false);
        outputArea.setDisabledTextColor(Color.BLACK);
        outputArea.setLineWrap(true);
        logListener = new BootstrapLogListener(outputArea);

        JScrollPane scrollArea = new JScrollPane(outputArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollArea.setBounds(225, 90, 535, 125);

        frame.add(scrollArea);

        JCheckBox debugCheckbox;

        if (debug) {
            debugCheckbox = new JCheckBox("Debug Mode");
            debugCheckbox.setBounds(650, 225, 105, 25);
            debugCheckbox.addActionListener(event -> {
                logListener.isDebug.set(debugCheckbox.isSelected());
            });
            frame.add(debugCheckbox);
        } else {
            debugCheckbox = null;
        }

        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(20, 225, debug ? 625 : 740, 25);
        convertButton.setEnabled(false);
        convertButton.addActionListener(event -> {
            if (converting.get()) return;

            startTime.set(System.currentTimeMillis());
            converting.set(true);
            if (debugCheckbox != null) debugCheckbox.setEnabled(false);
            convertButton.setEnabled(false);
            outputArea.setText("");
            dataLabel.setText("Converting %s...".formatted(javaPackPath.getFileName().toString()));
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            try {
                new Thread(() -> {
                    try {
                        new PackConverter()
                                .input(javaPackPath)
                                .output(output)
                                .converters(Converters.defaultConverters(logListener.isDebug.get()))
                                .logListener(logListener)
                                .convert()
                                .pack();

                        logListener.info("Converted pack.");
                        dataLabel.setText("%s Converted! Time elapsed: %ss".formatted(javaPackPath.getFileName().toString(), df.format((System.currentTimeMillis() - startTime.get()) / 1000d)));
                        converting.set(false);
                        convertButton.setEnabled(true);
                        if (debugCheckbox != null) debugCheckbox.setEnabled(true);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
                converting.set(false);
                convertButton.setEnabled(true);
                if (debugCheckbox != null) debugCheckbox.setEnabled(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        frame.add(convertButton);

        JButton javaPackButton = new JButton("Select Pack");
        javaPackButton.setBounds(20, 20, 200, 200);
        javaPackButton.addActionListener(event -> {
            FileDialog chooser = new FileDialog(frame);
            chooser.setTitle("Select Resource Pack");
            chooser.setMultipleMode(false);
            chooser.setMode(FileDialog.LOAD);
            chooser.setFilenameFilter((dir, name) -> name.endsWith(".zip") || name.endsWith(".jar"));
            chooser.setVisible(true);

            if (chooser.getFile() == null) return;

            javaPackPath = Path.of(chooser.getDirectory(), chooser.getFile());

            if (!javaPackPath.toString().endsWith(".zip") && !javaPackPath.toString().endsWith(".jar")) {
                dataLabel.setText("Please provide a Java Edition resource pack.");
                convertButton.setEnabled(false);
            } else {
                String name = javaPackPath.toAbsolutePath().toString();
                output = Path.of(name.substring(0, name.length() - 4) + ".mcpack");

                dataLabel.setText(
                        "Input: %s | Output: %s"
                                .formatted(javaPackPath.toAbsolutePath().toString(), output.toAbsolutePath().toString())
                );
                convertButton.setEnabled(true);

                try {
                    ZipUtils.openFileSystem(javaPackPath, true, path -> {
                        Path iconPath = path.resolve("pack.png");

                        if (!Files.exists(iconPath)) {
                            javaPackButton.setText("No Icon.");
                            javaPackButton.setIcon(null);
                        } else {
                            javaPackButton.setText(null);
                            javaPackButton.setIcon(
                                    new BufferedImageIcon(
                                            ImageUtil.resize(ImageIO.read(Files.newInputStream(iconPath)), 175, 175)
                                    )
                            );
                        }
                    });
                } catch (IOException e) {
                    javaPackButton.setText("No Icon.");
                    javaPackButton.setIcon(null);
                }
            }
        });
        frame.add(javaPackButton);

        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setBounds(225, 50, 535, 50);
        frame.add(outputLabel);


        frame.setVisible(true);
    }
}
