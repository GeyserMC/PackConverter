package org.geysermc.pack.converter.util;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageBudgetTest {
    @Test
    void readsNormalImageWithinBudget() throws Exception {
        BufferedImage source = new BufferedImage(32, 16, BufferedImage.TYPE_INT_ARGB);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(source, "png", output);

        BufferedImage decoded = ImageBudget.read(output.toByteArray());

        assertEquals(32, decoded.getWidth());
        assertEquals(16, decoded.getHeight());
    }

    @Test
    void rejectsHugeDimensionsBeforePixelDecode() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (var data = new java.io.DataOutputStream(output)) {
            data.writeLong(0x89504E470D0A1A0AL);
            data.writeInt(13);
            byte[] ihdr = new byte[] {'I', 'H', 'D', 'R', 0, 0, 0x4E, 0x20, 0, 0, 0x4E, 0x20, 8, 6, 0, 0, 0};
            data.write(ihdr);
            java.util.zip.CRC32 crc = new java.util.zip.CRC32();
            crc.update(ihdr);
            data.writeInt((int) crc.getValue());
        }

        assertThrows(java.io.IOException.class, () -> ImageBudget.read(output.toByteArray()));
    }
}
