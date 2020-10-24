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

package org.geysermc.packconverter.api.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.geysermc.packconverter.api.PackConverter;
import org.geysermc.packconverter.api.utils.ResourcePackManifest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MetadataConverter extends AbstractConverter {

    @Getter
    public static final List<Object[]> defaultData = new ArrayList<>();

    static {
        defaultData.add(new Object[] {"pack.mcmeta", "manifest.json"});
    }

    public MetadataConverter(PackConverter packConverter, Path storage, Object[] data) {
        super(packConverter, storage, data);
    }

    @Override
    public List<AbstractConverter> convert() {
        List<AbstractConverter> delete = new ArrayList<>();

        try {
            String from = (String) this.data[0];
            String to = (String) this.data[1];

            packConverter.log(String.format("Create metadata %s", to));

            if (!storage.resolve(from).toFile().exists()) {
                throw new FileNotFoundException(String.format("Missing %s! Is this really a Java texture pack?", from));
            }

            ObjectMapper mapper = new ObjectMapper().enable(JsonParser.Feature.ALLOW_COMMENTS);

            JsonNode packmeta = mapper.readTree(storage.resolve(from).toFile()).get("pack");
            int packFormat = packmeta.get("pack_format").asInt();
            String packDesc = packmeta.get("description").asText();

            // Convert the description if needed to make sure its valid on bedrock
            try {
                Component description = GsonComponentSerializer.colorDownsamplingGson().deserialize(packmeta.get("description").toString());
                packDesc = LegacyComponentSerializer.legacySection().serialize(description);
            } catch (JsonSyntaxException ignored) { }

            if (packFormat != 4 && packFormat != 5 && packFormat != 6) {
                throw new AssertionError("Only supports pack_format 4 (v1.13 or v1.14) or 5 (v1.15 or v1.16) or 6 (>= v1.16.2)!");
            }

            ResourcePackManifest.Header header = new ResourcePackManifest.Header();
            header.setName(storage.getFileName().toString().replace(".zip_mcpack", ""));
            header.setDescription(packDesc);
            header.setUuid(UUID.randomUUID());
            header.setVersion(new int[] { 1, 0, 0});

            ResourcePackManifest.Module module = new ResourcePackManifest.Module();
            module.setDescription(packDesc);
            module.setType("resources");
            module.setUuid(UUID.randomUUID());
            module.setVersion(new int[] { 1, 0, 0});

            ResourcePackManifest manifest = new ResourcePackManifest();
            manifest.setFormatVersion(1);
            manifest.setHeader(header);
            Collection<ResourcePackManifest.Module> modules = new ArrayList();
            modules.add(module);
            manifest.setModules(modules);

            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(storage.resolve(to).toFile(), manifest);

            delete.add(new DeleteConverter(packConverter, storage, new Object[] {from}));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return delete;
    }
}
