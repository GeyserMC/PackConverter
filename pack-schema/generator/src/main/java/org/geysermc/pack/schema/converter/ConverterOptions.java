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

package org.geysermc.pack.schema.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConverterOptions {
    private final String collisionPrefix;
    private final String rootPackage;
    private final SchemaConfig schemaConfig;

    private ConverterOptions(String collisionPrefix, String rootPackage, String schemaConfig) {
        this.collisionPrefix = collisionPrefix;
        this.rootPackage = rootPackage;

        InputStream schemaResource = ConverterOptions.class.getResourceAsStream("/" + schemaConfig);
        this.schemaConfig = new Gson().fromJson(new InputStreamReader(schemaResource), SchemaConfig.class);
    }

    public String collisionPrefix() {
        return this.collisionPrefix;
    }

    public String rootPackage() {
        return this.rootPackage;
    }

    public SchemaConfig schemaConfig() {
        return this.schemaConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String collisionPrefix = "Json";
        private String rootPackage = "";
        private String schemaConfig = "schema-config.json";

        public Builder collisionPrefix(String collisionPrefix) {
            this.collisionPrefix = collisionPrefix;
            return this;
        }

        public Builder rootPackage(String rootPackage) {
            this.rootPackage = rootPackage;
            return this;
        }

        public Builder schemaConfig(String schemaConfig) {
            this.schemaConfig = schemaConfig;
            return this;
        }

        public ConverterOptions build() {
            return new ConverterOptions(this.collisionPrefix, this.rootPackage, this.schemaConfig);
        }
    }
}
