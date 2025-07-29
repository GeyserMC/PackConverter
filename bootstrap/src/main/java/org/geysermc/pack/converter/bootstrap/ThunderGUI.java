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

package org.geysermc.pack.converter.bootstrap;

import com.twelvemonkeys.image.BufferedImageIcon;
import org.geysermc.pack.converter.PackConverter;
import org.geysermc.pack.converter.converter.Converters;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.ZipUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ThunderGUI extends JFrame {
    private final DecimalFormat decimalFormat;
    private final AtomicBoolean converting = new AtomicBoolean(false);
    private final AtomicLong startTime = new AtomicLong(0);
    private final BootstrapLogListener logListener = new BootstrapLogListener(this);
    protected final AtomicBoolean debugMode = new AtomicBoolean(false);
    protected final JTextArea outputArea;

    private Path inputPath = null;
    private Path outputPath = null;

    public ThunderGUI(boolean debug) throws IOException {
        decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        this.setTitle("Thunder");
        InputStream iconStream = Main.class.getResourceAsStream("/icon.png");
        if (iconStream != null) this.setIconImage(ImageIO.read(iconStream));
        this.setSize(800, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JLabel dataLabel = new JLabel("Input: None | Output: None");
        dataLabel.setBounds(225, 20, 535, 50);
        this.add(dataLabel);

        this.outputArea = new JTextArea("");
        this.outputArea.setEditable(false);
        this.outputArea.setFocusable(false);
        this.outputArea.setDisabledTextColor(Color.BLACK);
        this.outputArea.setLineWrap(true);

        JScrollPane scrollArea = new JScrollPane(this.outputArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollArea.setBounds(225, 90, 535, 125);

        this.add(scrollArea);

        JCheckBox debugCheckbox;

        if (debug) {
            debugCheckbox = new JCheckBox("Debug Mode");
            debugCheckbox.setBounds(650, 225, 105, 25);
            debugCheckbox.addActionListener(event -> {
                this.debugMode.set(debugCheckbox.isSelected());
            });
            this.add(debugCheckbox);
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
            this.outputArea.setText("");
            dataLabel.setText("Converting %s...".formatted(inputPath.getFileName().toString()));
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            try {
                new Thread(() -> {
                    try {
                        new PackConverter()
                                .input(inputPath)
                                .output(outputPath)
                                .converters(Converters.defaultConverters(this.debugMode.get()))
                                .logListener(logListener)
                                .convert()
                                .pack();

                        logListener.info("Converted pack.");
                        dataLabel.setText("%s Converted! Time elapsed: %ss".formatted(inputPath.getFileName().toString(), decimalFormat.format((System.currentTimeMillis() - startTime.get()) / 1000d)));
                        converting.set(false);
                        convertButton.setEnabled(true);
                        if (debugCheckbox != null) debugCheckbox.setEnabled(true);
                        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
                converting.set(false);
                convertButton.setEnabled(true);
                if (debugCheckbox != null) debugCheckbox.setEnabled(true);
                this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        this.add(convertButton);

        JButton javaPackButton = new JButton("Select Pack");
        javaPackButton.setBounds(20, 20, 200, 200);
        javaPackButton.addActionListener(event -> {
            FileDialog chooser = new FileDialog(this);
            chooser.setTitle("Select Resource Pack");
            chooser.setMultipleMode(false);
            chooser.setMode(FileDialog.LOAD);
            chooser.setFilenameFilter((dir, name) -> name.endsWith(".zip") || name.endsWith(".jar"));
            chooser.setVisible(true);

            if (chooser.getFile() == null) return;

            inputPath = Path.of(chooser.getDirectory(), chooser.getFile());

            if (!inputPath.toString().endsWith(".zip") && !inputPath.toString().endsWith(".jar")) {
                dataLabel.setText("Please provide a Java Edition resource pack.");
                convertButton.setEnabled(false);
            } else {
                String name = inputPath.toAbsolutePath().toString();
                outputPath = Path.of(name.substring(0, name.length() - 4) + ".mcpack");

                dataLabel.setText(
                        "Input: %s | Output: %s"
                                .formatted(inputPath.toAbsolutePath().toString(), outputPath.toAbsolutePath().toString())
                );
                convertButton.setEnabled(true);

                try {
                    ZipUtils.openFileSystem(inputPath, true, path -> {
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
        this.add(javaPackButton);

        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setBounds(225, 50, 535, 50);
        this.add(outputLabel);

        this.setVisible(true);
    }
}
