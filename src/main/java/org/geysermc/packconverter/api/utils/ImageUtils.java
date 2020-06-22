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

package org.geysermc.packconverter.api.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    /**
     * @see ImageUtils#crop(BufferedImage, int, int, int, int)
     */
    public static BufferedImage crop(BufferedImage img, int width, int height) {
        return crop(img, 0, 0, width, height);
    }

    /**
     * Crop an image
     *
     * @param img
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage crop(BufferedImage img, int x, int y, int width, int height) {
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = dest.getGraphics();
        g.drawImage(img, 0, 0, width, height, x, y, x + width, y+ height, null);
        g.dispose();
        return dest;
    }

    /**
     * Scale a buffered image
     * Based on https://stackoverflow.com/a/4216635/5299903
     *
     * @param img
     * @param scale
     * @return
     */
    public static BufferedImage scale(BufferedImage img, float scale) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = new BufferedImage(Math.round( w * scale), Math.round(h * scale), BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return scaleOp.filter(img, after);
    }

    /**
     * Scale the image so it has a width that is at least the min
     *
     * @param img
     * @param minWidth
     * @return
     */
    public static BufferedImage ensureMinWidth(BufferedImage img, int minWidth) {
        if (img.getWidth() < minWidth) {
            return scale(img, (minWidth / img.getWidth()));
        }

        return img;
    }

    /**
     * Scale the image to fill the required size, may result in clipping
     *
     * @param img
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage cover(BufferedImage img, int width, int height) {
        int f = width / height > img.getWidth() / img.getHeight() ? width / img.getWidth() : height / img.getHeight();
        return crop(scale(img, f),  ((img.getWidth() - width) / 2), ((img.getHeight() - height) / 2), width, height);
    }

    /**
     * Convert a color to an ARGB int
     *
     * @param color
     * @return
     */
    public static int colorToARGB(Color color) {
        int newPixel = 0;
        newPixel += color.getAlpha();
        newPixel = newPixel << 8;
        newPixel += color.getRed();
        newPixel = newPixel << 8;
        newPixel += color.getGreen();
        newPixel = newPixel << 8;
        newPixel += color.getBlue();

        return newPixel;
    }

    /**
     * Write an image to file and ensure the directory exists
     *
     * @param img Image to write
     * @param format Format to write
     * @param output File to write to
     * @throws IOException
     */
    public static void write(BufferedImage img, String format, File output) throws IOException {
        output.getParentFile().mkdirs();
        ImageIO.write(img, format, output);
    }

    /**
     * Convert a {@link BufferedImage} to grayscale
     *
     * @param img
     * @return
     */
    public static BufferedImage grayscale(BufferedImage img) {
        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(img.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(producer));
    }

    /**
     * Tint a {@link BufferedImage} by a given {@link Color}
     *
     * @param img
     * @param color
     * @return
     */
    public static BufferedImage colorize(BufferedImage img, Color color) {
        BufferedImage newImage = grayscale(img);

        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                Color newCol = new Color(newImage.getRGB(x, y));
                newCol = new Color(newCol.getRed() / 255 * color.getRed(), newCol.getGreen() / 255 * color.getGreen(), newCol.getBlue() / 255 * color.getBlue());
                newImage.setRGB(x, y, ImageUtils.colorToARGB(newCol));
            }
        }

        return newImage;
    }

    /**
     * Convert an {@link Image} to {@link BufferedImage}
     *
     * @param img
     * @return
     */
    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage newImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics g = newImage.getGraphics();
        g.drawImage(img, 0, 0, null);

        return newImage;
    }

    /**
     * Rotate a given {@link BufferedImage} by an angle
     *
     * @param img
     * @param angle
     * @return
     */
    public static BufferedImage rotate(BufferedImage img, int angle) {
        final double rads = Math.toRadians(angle);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(img.getWidth() * cos + img.getHeight() * sin);
        final int h = (int) Math.floor(img.getHeight() * cos + img.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, img.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-img.getWidth() / 2, -img.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return rotateOp.filter(img, rotatedImage);
    }
}
