package org.geysermc.pack.converter.type.entity;

import org.geysermc.pack.bedrock.resource.models.entity.modelentity.geometry.bones.cubes.Uv;
import org.geysermc.pack.converter.type.entity.blockbench.BlockbenchEntityParser;
import org.geysermc.pack.converter.type.entity.gecko.GeckoLibEntityParser;
import org.geysermc.pack.converter.type.model.BedrockModel;
import org.junit.jupiter.api.Test;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityParserUvTest {
    @Test
    void geckoParserPreservesBoxUvAnchor() {
        Uv uv = parse(new GeckoLibEntityParser(), "assets/example/geo/beast.geo.json", "[10,20]");

        assertArrayEquals(new float[] { 12, 20 }, uv.down().uv());
        assertArrayEquals(new float[] { 18, 22 }, uv.south().uv());
    }

    @Test
    void blockbenchParserPreservesPerFaceUv() {
        Uv uv = parse(new BlockbenchEntityParser(), "assets/example/models/beast.bbmodel", """
                {"north":{"uv":[1,2],"uv_size":[3,4],"material_instance":"cutout"},
                 "up":{"uv":[5,6],"uv_size":[-7,8]}}
                """);

        assertArrayEquals(new float[] { 1, 2 }, uv.north().uv());
        assertArrayEquals(new float[] { 3, 4 }, uv.north().uvSize());
        assertEquals("cutout", uv.north().materialInstance());
        assertArrayEquals(new float[] { -7, 8 }, uv.up().uvSize());
    }

    private static Uv parse(EntityModelParser parser, String path, String uv) {
        ResourcePack pack = ResourcePack.resourcePack();
        pack.unknownFile(path, Writable.stringUtf8("""
                {"format_version":"1.12.0","minecraft:geometry":[{
                  "description":{"identifier":"geometry.example.beast","texture_width":64,"texture_height":64},
                  "bones":[{"name":"body","pivot":[0,0,0],"cubes":[
                    {"origin":[0,0,0],"size":[4,12,2],"uv":%s}
                  ]}]
                }]}
                """.formatted(uv)));
        BedrockModel converted = parser.parse(path, pack);
        assertNotNull(converted);
        return converted.model().geometry().getFirst().bones().getFirst().cubes().getFirst().uv();
    }
}
