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

import org.geysermc.pack.converter.util.LogListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BootstrapLogListener implements LogListener {
    private final ThunderGUI gui;

    public BootstrapLogListener(ThunderGUI gui) {
        this.gui = gui;
    }

    @Override
    public void debug(@NotNull String message) {
        if (gui.debugMode.get()) {
            appendText("DEBUG: " + message);
        }
    }

    @Override
    public void info(@NotNull String message) {
        appendText(message);
    }

    @Override
    public void warn(@NotNull String message) {
        appendText("WARNING: " + message);
    }

    @Override
    public void error(@NotNull String message) {
        appendText("ERROR: " + message);
    }

    @Override
    public void error(@NotNull String message, @Nullable Throwable exception) {
        appendText("ERROR: " + message);
    }

    private void appendText(String text) {
        if (gui.outputArea.getText().isEmpty()) {
            gui.outputArea.setText(
                    text
            );
            return;
        }

        gui.outputArea.setText(
                gui.outputArea.getText() + "\n" + text
        );

        gui.outputArea.setCaretPosition(gui.outputArea.getDocument().getLength());
    }
}
