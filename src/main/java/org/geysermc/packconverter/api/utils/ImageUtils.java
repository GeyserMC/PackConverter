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
     * @param img Image to use
     * @param x Starting X
     * @param y Starting Y
     * @param width Final width
     * @param height Final height
     * @return Cropped image
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
     * @param img Image to use
     * @param scale Amount to scale the image by
     * @return Scaled image
     */
    public static BufferedImage scale(BufferedImage img, float scale) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = new BufferedImage(Math.round(w * scale), Math.round(h * scale), BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return scaleOp.filter(img, after);
    }

    /**
     * Scale the image so it has a width that is at least the min
     *
     * @param img Image to use
     * @param minWidth Width to check
     * @return Scaled image if needed
     */
    public static BufferedImage ensureMinWidth(BufferedImage img, int minWidth) {
        if (img.getWidth() < minWidth) {
            return scale(img, ((float) minWidth / img.getWidth()));
        }

        return img;
    }

    /**
     * Scale the image so it has a height that is at least the min
     *
     * @param img Image to use
     * @param minHeight Height to check
     * @return Scaled image if needed
     */
    public static BufferedImage ensureMinHeight(BufferedImage img, int minHeight) {
        if (img.getHeight() < minHeight) {
            return scale(img, ((float) minHeight / img.getHeight()));
        }

        return img;
    }

    /**
     * Scale the image so it has a width that is at less than the max
     *
     * @param img Image to use
     * @param maxWidth Width to check
     * @return Scaled image if needed
     */
    public static BufferedImage ensureMaxWidth(BufferedImage img, int maxWidth) {
        if (img.getWidth() > maxWidth) {
            return scale(img, ((float) maxWidth / img.getWidth()));
        }

        return img;
    }

    /**
     * Scale the image so it has a height that is at less than the max
     *
     * @param img Image to use
     * @param maxHeight Height to check
     * @return Scaled image if needed
     */
    public static BufferedImage ensureMaxHeight(BufferedImage img, int maxHeight) {
        if (img.getHeight() > maxHeight) {
            return scale(img, ((float) maxHeight / img.getWidth()));
        }

        return img;
    }

    /**
     * Scale the image to fill the required size, may result in clipping
     *
     * @param img Image to use
     * @param width Wanted width
     * @param height Wanted height
     * @return Manipulated image if needed
     */
    public static BufferedImage cover(BufferedImage img, int width, int height) {
        int f = width / height > img.getWidth() / img.getHeight() ? width / img.getWidth() : height / img.getHeight();
        return crop(scale(img, f),  ((img.getWidth() - width) / 2), ((img.getHeight() - height) / 2), width, height);
    }

    /**
     * Convert a color to an ARGB int
     *
     * @param color Color to use
     * @return ARGB int
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
     * @param img Image to use
     * @return A grayscale version of the image
     */
    public static BufferedImage grayscale(BufferedImage img) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                Color newCol = new Color(img.getRGB(x, y), true);

                int grey = Math.round(0.2126f * newCol.getRed() +
                        0.7152f * newCol.getGreen() +
                        0.0722f * newCol.getBlue());

                newCol = new Color(grey, grey, grey, newCol.getAlpha());
                newImage.setRGB(x, y, colorToARGB(newCol));
            }
        }

        return newImage;
    }

    /**
     * Tint a {@link BufferedImage} by a given {@link Color}
     *
     * @param img Image to use
     * @param color Color to tint
     * @return Tinted image
     */
    public static BufferedImage colorize(BufferedImage img, Color color) {
        BufferedImage newImage = grayscale(img);

        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                Color newCol = new Color(newImage.getRGB(x, y), true);
                newCol = new Color(Math.round(newCol.getRed() / 255f * color.getRed()), Math.round(newCol.getGreen() / 255f * color.getGreen()), Math.round(newCol.getBlue() / 255f * color.getBlue()), newCol.getAlpha());
                newImage.setRGB(x, y, colorToARGB(newCol));
            }
        }

        return newImage;
    }

    /**
     * Convert an {@link Image} to {@link BufferedImage}
     *
     * @param img Image to use
     * @return The converted image
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
     * @param img Image to use
     * @param angle Amount to rotate by in degrees
     * @return Rotated image
     */
    public static BufferedImage rotate(BufferedImage img, int angle) {
        final double rads = Math.toRadians(-angle);
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

    /**
     * Check if a given area is empty
     *
     * @param img Image to use
     * @param subX Start X
     * @param subY Start Y
     * @param width Check width
     * @param height Check height
     * @return True if the area is empty
     */
    public static boolean isEmptyArea(BufferedImage img, int subX, int subY, int width, int height) {
        BufferedImage subImage = img.getSubimage(subX, subY, width, height);
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                if (!(new Color(subImage.getRGB(x, y), true).equals(Color.TRANSLUCENT))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Create new image from border image
     *
     * @param img Image to use
     * @param borderLeft Left border width
     * @param borderTop Top border width
     * @param borderRight Right border width
     * @param borderBottom Bottom border width
     * @param newWidth Target width
     * @param newHeight Target height
     * @return The converted image
     */
    public static BufferedImage borderImage(BufferedImage img, int borderLeft, int borderTop, int borderRight, int borderBottom, int newWidth, int newHeight) {
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();

        g.drawImage(crop(img, 0, 0, borderLeft, borderTop), 0, 0, null);
        g.drawImage(resize(crop(img, borderLeft, 0, (img.getWidth() - borderLeft - borderRight), borderTop), (newWidth - borderLeft - borderRight), borderTop), borderLeft, 0, null);
        g.drawImage(crop(img, (img.getWidth() - borderRight), 0, borderRight, borderTop), (newWidth - borderRight), 0, null);

        g.drawImage(resize(crop(img, 0, borderTop, borderLeft, (img.getHeight() - borderTop - borderBottom)), borderLeft, (newHeight - borderTop - borderBottom)), 0, borderTop, null);
        g.drawImage(resize(crop(img, borderLeft, borderTop, (img.getWidth() - borderLeft - borderRight), (img.getHeight() - borderTop - borderBottom)), (newWidth - borderLeft - borderRight), (newHeight - borderTop - borderBottom)), borderLeft, borderTop, null);
        g.drawImage(resize(crop(img, (img.getWidth() - borderRight), borderTop, borderRight, (img.getHeight() - borderTop - borderBottom)), borderRight, (newHeight - borderTop - borderBottom)), (newWidth - borderRight), borderTop, null);

        g.drawImage(crop(img, 0, (img.getHeight() - borderBottom), borderLeft, borderRight), 0, (newHeight - borderBottom), null);
        g.drawImage(resize(crop(img, borderLeft, (img.getHeight() - borderBottom), (img.getWidth() - borderLeft - borderRight), borderRight), (newWidth - borderLeft - borderRight), borderRight), borderLeft, (newHeight - borderBottom), null);
        g.drawImage(crop(img, (img.getWidth() - borderRight), (img.getHeight() - borderBottom), borderRight, borderRight), (newWidth - borderRight), (newHeight - borderBottom), null);

        return newImage;
    }

    /**
     * Resize a {@link BufferedImage} to the requested size
     * Doesnt replicate the jimp way of doing it but should give a similar output
     *
     * @param img Image to use
     * @param newWidth Target width
     * @param netHeight Target height
     * @return Scaled image to size
     */
    public static BufferedImage resize(BufferedImage img, int newWidth, int netHeight) {
        Image scaled = img.getScaledInstance(newWidth, netHeight, BufferedImage.SCALE_SMOOTH);
        return toBufferedImage(scaled);
    }

    /**
     * Alter the saturation of a {@link BufferedImage} by a given amount
     *
     * @param img Image to use
     * @param amount Amount to alter the saturation by
     * @return Saturated image
     */
    public static BufferedImage saturate(BufferedImage img, int amount) {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                Color newCol = new Color(img.getRGB(x, y), true);

                float[] hsb = Color.RGBtoHSB(newCol.getRed(), newCol.getGreen(), newCol.getBlue(), null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                saturation = Math.max(0f, saturation + (amount / 100f));

                int pixel = Color.HSBtoRGB(hue, saturation, brightness);

                int red = (0xff & (pixel >> 16));
                int green = (0xff & (pixel >> 8));
                int blue = (0xff & pixel);

                red = clamp(red, 0, 255);
                green = clamp(green, 0, 255);
                blue = clamp(blue, 0, 255);

                newCol = new Color(red, green, blue, newCol.getAlpha());

                newImage.setRGB(x, y, colorToARGB(newCol));
            }
        }

        return newImage;
    }

    /**
     * Flip the image
     *
     * @param horizontal If true the image will be flipped horizontally
     * @param vertical If true the image will be flipped vertically
     * @return The flipped image
     */
    public static BufferedImage flip(BufferedImage image, boolean horizontal, boolean vertical) {
        AffineTransform at = AffineTransform.getScaleInstance(horizontal ? -1 : 1, vertical ? -1 : 1);

        if (horizontal) {
            at.translate(-image.getWidth(null), 0);
        }

        if (vertical) {
            at.translate(0, -image.getHeight(null));
        }

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * Limit a number between two values
     *
     * @param val Value to limit
     * @param min Lower bound
     * @param max Upper bound
     * @return Limited value
     */
    private static int clamp(int val, int min, int max) {
        return val > max ? max : Math.max(val, min);
    }
}
