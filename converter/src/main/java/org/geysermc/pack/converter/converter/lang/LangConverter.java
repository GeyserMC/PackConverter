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

package org.geysermc.pack.converter.converter.lang;

import com.google.auto.service.AutoService;
import org.geysermc.pack.converter.PackConversionContext;
import org.geysermc.pack.converter.converter.BaseConverter;
import org.geysermc.pack.converter.converter.Converter;
import org.geysermc.pack.converter.data.BaseConversionData;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.lang.Language;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

@AutoService(Converter.class)
public class LangConverter extends BaseConverter {
    private static final String BEDROCK_TEXTS_LOCATION = "texts";

    private final Pattern positionalStringReplacement = Pattern.compile("%([0-9]+)\\$s");

    @Override
    public void convert(@NotNull PackConversionContext<BaseConversionData> context) throws Exception {
        Collection<Language> languages = context.javaResourcePack().languages();
        for (Language language : languages) {
            Map<String, String> strings = language.translations();

            for (Map.Entry<String, String> entry : strings.entrySet()) {
                String value = entry.getValue();

                // Replace %d with %s
                value = value.replace("%d", "%s");

                // Replace `%x$s` with `%x`
                value = positionalStringReplacement.matcher(value).replaceAll("%$1");

                entry.setValue(value);
            }

            String languageKey = language.key().value();

            // Convert the language key to the Bedrock equivalent
            if (languageKey.equals("no_no")) {
                languageKey = "nb_no";
            }

            context.bedrockResourcePack().addLanguage(languageKey, strings);
        }
    }
}
