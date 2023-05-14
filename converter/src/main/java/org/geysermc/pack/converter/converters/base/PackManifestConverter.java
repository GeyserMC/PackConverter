/*
 * Copyright (c) 2019-2023 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.converters.base;

import com.google.auto.service.AutoService;
import org.geysermc.pack.bedrock.resource.Manifest;
import org.geysermc.pack.bedrock.resource.manifest.Header;
import org.geysermc.pack.bedrock.resource.manifest.Modules;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converters.BaseConverter;
import org.geysermc.pack.converter.converters.Converter;
import org.geysermc.pack.converter.data.BaseConversionData;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

import java.util.List;
import java.util.UUID;

@AutoService(Converter.class)
public class PackManifestConverter extends BaseConverter {
    private static final int FORMAT_VERSION = 2;

    @Override
    public void convert(@NotNull PackConversionContext<BaseConversionData> context) throws Exception {
        ResourcePack javaPack = context.javaResourcePack();

        Manifest manifest = new Manifest();
        manifest.formatVersion(FORMAT_VERSION);

        Header header = new Header();
        header.description(javaPack.description());
        header.name(context.outputDirectory().getFileName().toString());
        header.version(new float[] { 1, 0, 0 });
        header.minEngineVersion(new float[] { 1, 16, 0 });
        header.uuid(UUID.randomUUID().toString());

        manifest.header(header);

        Modules module = new Modules();
        module.description(javaPack.description());
        module.type("resources");
        module.uuid(UUID.randomUUID().toString());
        module.version(new float[] { 1, 0, 0 });

        manifest.modules(List.of(module));
        context.bedrockResourcePack().manifest(manifest);
    }
}
