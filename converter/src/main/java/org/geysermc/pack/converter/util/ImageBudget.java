package org.geysermc.pack.converter.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

/** Rejects decompression-bomb dimensions before ImageIO allocates pixel storage. */
public final class ImageBudget {
    private static final int MAX_EDGE = 16_384;
    private static final long MAX_PIXELS = 128L * 1024 * 1024;
    private static final int MAX_ENCODED_BYTES = 64 * 1024 * 1024;

    private ImageBudget() {
    }

    public static BufferedImage read(byte[] bytes) throws IOException {
        if (bytes.length > MAX_ENCODED_BYTES) throw new IOException("encoded image exceeds budget");
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
            if (input == null) throw new IOException("unsupported image input");
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) throw new IOException("unsupported image format");
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                int width = reader.getWidth(0);
                int height = reader.getHeight(0);
                if (width <= 0 || height <= 0 || width > MAX_EDGE || height > MAX_EDGE
                        || (long) width * height > MAX_PIXELS) {
                    throw new IOException("image dimensions exceed budget: " + width + "x" + height);
                }
                return reader.read(0);
            } finally {
                reader.dispose();
            }
        }
    }
}
