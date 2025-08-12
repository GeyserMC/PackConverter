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

package org.geysermc.pack.converter.util;

import java.util.Locale;

public class HexUtils {
    // Seems stupid, but keeps leading zeros
    public static String hexToBinary(String hex) {
        return hex.toUpperCase(Locale.ROOT).replace("0", "0000")
                .replace("1", "0001")
                .replace("2", "0010")
                .replace("3", "0011")
                .replace("4", "0100")
                .replace("5", "0101")
                .replace("6", "0110")
                .replace("7", "0111")
                .replace("8", "1000")
                .replace("9", "1001")
                .replace("A", "1010")
                .replace("B", "1011")
                .replace("C", "1100")
                .replace("D", "1101")
                .replace("E", "1110")
                .replace("F", "1111");

    }
}
