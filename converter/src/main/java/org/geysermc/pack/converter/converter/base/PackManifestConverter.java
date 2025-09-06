/*
 * Copyright (c) 2025-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.converter.base;

import org.geysermc.pack.bedrock.resource.Manifest;
import org.geysermc.pack.bedrock.resource.manifest.Header;
import org.geysermc.pack.bedrock.resource.manifest.Modules;
import org.geysermc.pack.converter.converter.AssetConverter;
import org.geysermc.pack.converter.converter.ConversionContext;
import team.unnamed.creative.metadata.pack.PackMeta;

import java.util.List;
import java.util.UUID;

public class PackManifestConverter implements AssetConverter<PackMeta, Manifest> {
    public static final PackManifestConverter INSTANCE = new PackManifestConverter();
    private static final int FORMAT_VERSION = 2;

    @Override
    public Manifest convert(PackMeta packMeta, ConversionContext context) throws Exception {
        Manifest manifest = new Manifest();
        manifest.formatVersion(FORMAT_VERSION);

        Header header = new Header();
        header.description(packMeta.description());
        header.name(context.packName());
        header.version(new float[] { 1, 0, 0 });
        header.minEngineVersion(new float[] { 1, 16, 0 });
        header.uuid(UUID.randomUUID().toString());

        manifest.header(header);

        Modules module = new Modules();
        module.description(packMeta.description());
        module.type("resources");
        module.uuid(UUID.randomUUID().toString());
        module.version(new float[] { 1, 0, 0 });

        manifest.modules(List.of(module));

        return manifest;
    }
}
