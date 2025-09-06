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

package org.geysermc.pack.converter.converter.lang;

import org.geysermc.pack.bedrock.resource.BedrockResourcePack;
import org.geysermc.pack.converter.converter.AssetCollector;
import org.geysermc.pack.converter.converter.CollectionContext;
import org.geysermc.pack.converter.converter.ConversionContext;
import org.geysermc.pack.converter.converter.KeyedAssetConverter;
import team.unnamed.creative.lang.Language;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LangConverter implements KeyedAssetConverter<Language, BedrockLanguage>, AssetCollector<BedrockLanguage> {
    public static final LangConverter INSTANCE = new LangConverter();

    private static final Pattern POSITIONAL_STRING_REPLACEMENT = Pattern.compile("%([0-9]+)\\$s");

    @Override
    public BedrockLanguage convert(Language language, ConversionContext context) throws Exception {
        Map<String, String> strings = language.translations();

        for (Map.Entry<String, String> entry : strings.entrySet()) {
            String value = entry.getValue();

            // Replace %d with %s
            value = value.replace("%d", "%s");

            // Replace `%x$s` with `%x`
            value = POSITIONAL_STRING_REPLACEMENT.matcher(value).replaceAll("%$1");

            entry.setValue(value);
        }

        String languageKey = language.key().value();

        // Convert the language key to the Bedrock equivalent
        if (languageKey.equals("no_no")) {
            languageKey = "nb_no";
        }

        return new BedrockLanguage(languageKey, strings);
    }

    @Override
    public void include(BedrockResourcePack pack, List<BedrockLanguage> languages, CollectionContext context) {
        Map<String, Map<String, String>> merged = new HashMap<>();

        for (BedrockLanguage language : languages) {
            Map<String, String> mergedLanguage = merged.computeIfAbsent(language.language(), name -> new HashMap<>());
            for (Map.Entry<String, String> entry : language.strings().entrySet()) {
                if (mergedLanguage.containsKey(entry.getKey())) {
                    context.warn("Conflicting language string " + entry.getKey() + "!");
                    continue;
                }
                mergedLanguage.put(entry.getKey(), entry.getValue());
            }
        }

        merged.forEach(pack::addLanguage);
    }
}
