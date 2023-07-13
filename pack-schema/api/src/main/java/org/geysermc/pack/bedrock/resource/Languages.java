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

package org.geysermc.pack.bedrock.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Languages {
    private final Map<String, Map<String, String>> languages = new HashMap<>();

    public Map<String, Map<String, String>> languages() {
        return Collections.unmodifiableMap(this.languages);
    }

    /**
     * Get the language data for a specific language
     *
     * @param language The language code to get the data for
     * @return The language data, or an empty map if the language does not exist
     */
    public Map<String, String> language(String language) {
        return this.languages.getOrDefault(getLanguageCode(language), Map.of());
    }

    /**
     * Set the language data for a specific language
     *
     * @param language The language code to set the data for
     * @param data     The translation strings to put against the language
     */
    public void language(String language, Map<String, String> data) {
        this.languages.put(getLanguageCode(language), data);
    }

    public String translation(String language, String key) {
        return this.language(getLanguageCode(language)).getOrDefault(key, key);
    }

    /**
     * Set the translation for a specific language
     *
     * @param language The language code to set the translation for
     * @param key      The key to set the translation for
     * @param value    The value to set the translation to
     */
    public void translation(String language, String key, String value) {
        this.language(getLanguageCode(language)).put(key, value);
    }

    /**
     * Get a list of all the language codes
     *
     * @return A list of all the language codes
     */
    public String[] languageCodes() {
        return this.languages.keySet().toArray(new String[0]);
    }

    private String getLanguageCode(String language) {
        String[] parts = language.split("_");
        return parts[0] + "_" + parts[1].toUpperCase();
    }
}
