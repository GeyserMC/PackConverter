/*
 * Copyright (c) 2019-2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.type.texture;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.pipeline.AssetCombiner;
import org.geysermc.pack.converter.pipeline.AssetConverter;
import org.geysermc.pack.converter.pipeline.AssetExtractor;
import org.geysermc.pack.converter.pipeline.CombineContext;
import org.geysermc.pack.converter.pipeline.ConversionContext;
import org.geysermc.pack.converter.pipeline.ExtractionContext;
import org.geysermc.pack.converter.type.texture.transformer.TextureTransformer;
import org.geysermc.pack.converter.type.texture.transformer.TransformContext;
import org.geysermc.pack.converter.type.texture.transformer.TransformedTexture;
import org.geysermc.pack.converter.util.ImageUtil;
import org.geysermc.pack.converter.util.JsonMappings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class TextureConverter implements AssetExtractor<Texture>, AssetConverter<Texture, TransformedTexture>, AssetCombiner<TransformedTexture> {
    public static final TextureConverter INSTANCE = new TextureConverter();
    public static final String BEDROCK_TEXTURES_LOCATION = "textures";

    private final List<TextureTransformer> transformers = StreamSupport.stream(ServiceLoader.load(TextureTransformer.class).spliterator(), false)
            .sorted(Comparator.comparingInt(TextureTransformer::order))
            .toList();

    public static final Map<String, String> DIRECTORY_LOCATIONS = Map.of(
            "block", "blocks",
            "item", "items",
            "gui", "ui"
    );

    @Override
    public Collection<Texture> extract(ResourcePack pack, ExtractionContext context) {
        // TODO ideally textures should be transformed individually in the convert process, and not together in the extraction process, but this is hard to achieve,
        // TODO and will need another big refactor to the texture transformation code
        // TODO for now this will work, but for library users it might be nice to be able to properly convert singular textures with transformations
        JsonMappings mappings = JsonMappings.getMapping("textures");
        List<Texture> textures = new ArrayList<>(pack.textures());

        context.info("Transforming textures...");
        TransformContext transformContext = new TransformContext(
                mappings,
                textures,
                context.bedrockResourcePack(),
                pack,
                context.vanillaPack(),
                context.logListener()
        );
        for (TextureTransformer transformer : this.transformers) {
            try {
                transformer.transform(transformContext);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        context.info("Transformed textures!");

        return textures;
    }

    @Override
    public @NotNull TransformedTexture convert(Texture texture, ConversionContext context) throws Exception {
        JsonMappings mappings = JsonMappings.getMapping("textures");
        TransformedTexture transformed = new TransformedTexture(texture);

        String input = texture.key().value();
        String relativePath = input.replaceAll("\\.png$", "");

        int slashIndex = relativePath.indexOf('/');
        String javaRoot = slashIndex != -1 ? relativePath.substring(0, slashIndex) : "";

        List<String> mapping = mappings.map(relativePath);
        List<String> transformedOutputs = new ArrayList<>();
        for (String item : mapping) {
            // Mappings without a root directory are relative to the java texture's own root
            String path = item.indexOf('/') != -1 ? item : javaRoot + "/" + item;

            String rootPath = path.substring(0, path.indexOf('/'));
            transformedOutputs.add(DIRECTORY_LOCATIONS.getOrDefault(rootPath, rootPath) + path.substring(path.indexOf('/')) + ".png");
        }

        transformed.output(transformedOutputs);

        return transformed;
    }

    /**
     * Resolve {@code combined} against the {@code texturePath} directory, but only
     * if the result stays inside it. {@link Path#resolve(String)} silently replaces
     * the receiver when the argument is absolute — that is how the
     * {@code viaversion:logo.png} case ended up writing to {@code /viaversion/logo.png}
     * (host filesystem root) and producing a {@code FileSystemException: Read-only file system}
     * on Linux containers. The previous {@code misc/} fix only patched the
     * {@code namespace+value} case, not the underlying class of bug.
     *
     * <p>Defence-in-depth: even if a future caller passes a string that starts
     * with the right prefix, the post-resolve {@code startsWith} check guarantees
     * the result never escapes {@code texturePath}.</p>
     *
     * @param texturePath the pack's {@code textures/} directory
     * @param combined    the relative path produced by the bedrockDirectory format
     * @return a {@link Path} strictly inside {@code texturePath}
     * @throws IOException if {@code combined} is absolute or escapes {@code texturePath}
     */
    static Path resolveSafeRelative(Path texturePath, String combined) throws IOException {
        if (combined.startsWith(File.separator) || combined.startsWith("/")) {
            throw new IOException("Refusing absolute path: " + combined);
        }
        Path resolved = texturePath.resolve(combined).normalize();
        Path root = texturePath.normalize();
        if (!resolved.startsWith(root)) {
            throw new IOException("Refusing path " + combined + " that escapes " + root);
        }
        return resolved;
    }

    @Override
    public void include(BedrockResourcePack pack, List<TransformedTexture> transformedTextures, CombineContext context) {
        Path texturePath = pack.directory().resolve(BEDROCK_TEXTURES_LOCATION);

        // Transformer results are added last, so the last texture to claim an output wins
        Map<String, TransformedTexture> outputOwners = new HashMap<>();
        for (TransformedTexture texture : transformedTextures) {
            for (String outputPath : texture.output()) {
                outputOwners.put(outputPath, texture);
            }
        }

        Set<String> exportedPaths = new HashSet<>();

        for (TransformedTexture textureToExport : transformedTextures) {
            String bedrockDirectory = "%s/%s";
            // The "misc/<namespace>" branch already places the mod's namespace
            // in the path. Appending textureSubDirectory (which is also the
            // mod namespace) would produce "misc/<ns>/<ns>/<file>" — a duplicated
            // namespace that the previous misc/ fix did not anticipate. Skip the
            // subdirectory prefix when we know root already includes the namespace.
            boolean skipSubDirectory = false;
            int firstSlash = textureToExport.output().isEmpty() ? -1
                    : textureToExport.output().get(0).indexOf('/');
            if (firstSlash > 0) {
                String firstRoot = textureToExport.output().get(0).substring(0, firstSlash);
                if ("misc".equals(firstRoot) && context.textureSubDirectory() != null) {
                    skipSubDirectory = true;
                }
            }
            if (!skipSubDirectory && context.textureSubDirectory() != null) {
                bedrockDirectory = "%s/" + context.textureSubDirectory() + "/%s";
            }

            List<Path> outputs = new ArrayList<>();
            for (String outputPath : textureToExport.output()) {
                if (outputOwners.get(outputPath) != textureToExport) {
                    context.debug("Conflicting texture " + outputPath + ", skipping " + textureToExport.texture().key() + "!");
                    continue;
                }

                if (!exportedPaths.add(outputPath)) {
                    continue;
                }

                int slashIndex = outputPath.indexOf('/');
                String root = slashIndex != -1 ? outputPath.substring(0, slashIndex) : "";
                String value = slashIndex != -1 ? outputPath.substring(slashIndex + 1) : outputPath;

                String combined = bedrockDirectory.formatted(root, value)
                        .replace('/', File.separatorChar);
                try {
                    outputs.add(resolveSafeRelative(texturePath, combined));
                } catch (IOException exception) {
                    context.error("Refusing to write texture outside pack directory: " + combined, exception);
                }
            }
            if (outputs.isEmpty()) {
                context.warn("Skipping texture " + textureToExport.texture().key()
                        + " — all output paths were rejected as unsafe");
                continue;
            }

            try {
                byte[] bytes = textureToExport.texture().data().toByteArray();

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
                if (image == null) {
                    context.warn("Invalid texture " + texturePath + "!");
                    continue;
                }

                for (Path output : outputs) {
                    if (output.getParent() != null && Files.notExists(output.getParent())) {
                        Files.createDirectories(output.getParent());
                    }

                    BufferedImage bedrockImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g = bedrockImage.createGraphics();
                    g.setComposite(AlphaComposite.Src);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();

                    String pngKey = pack.directory().relativize(output).toString().replace(File.separatorChar, '/');
                    PngToTgaMappings.TgaMapping mapping = PngToTgaMappings.mapping(pngKey);
                    if (mapping != null) {
                        Path tgaPath = pack.directory().resolve(mapping.value());
                        if (Files.notExists(tgaPath.getParent())) {
                            Files.createDirectories(tgaPath.getParent());
                        }

                        ImageUtil.writeTGA(tgaPath, bedrockImage);
                        if (!mapping.keep()) {
                            Files.deleteIfExists(output);
                            continue;
                        }
                    }

                    Files.createDirectories(output.getParent());

                    try (OutputStream stream = Files.newOutputStream(output)) {
                        ImageIO.write(bedrockImage, "png", stream);
                    }
                }
            } catch (IOException exception) {
                context.error("Failed to write texture " + textureToExport.texture().key() + "!", exception);
            }
        }
    }
}
