/*
 * Copyright (c) 2025 GeyserMC. http://geysermc.org
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

package org.geysermc.pack.converter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class WebUtils {
    /**
     * Makes a web request to the given URL and returns the body as a string
     *
     * @param reqURL URL to fetch
     * @return body content or
     * @throws IOException / a wrapped UnknownHostException for nicer errors.
     */
    public static String getBody(String reqURL) throws IOException {
        try {
            URL url = httpsUrl(reqURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", getUserAgent()); // Otherwise Java 8 fails on checking updates
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            try {
                return connectionToString(con);
            } finally {
                con.disconnect();
            }
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Unable to resolve requested url (%s)! Are you offline?".formatted(reqURL), e);
        }
    }

    /**
     * Downloads a URL to a file with connect and read timeouts. Unlike
     * {@link URL#openStream()}, a connection that stalls mid-transfer cannot
     * block the caller indefinitely - the read timeout fires after 10 seconds
     * without data.
     *
     * @param reqURL URL to download
     * @param target file to write; any previous content is replaced
     * @throws IOException if the download fails or times out
     */
    public static void downloadToFile(String reqURL, Path target) throws IOException {
        downloadToFile(reqURL, target, null, -1);
    }

    /** Downloads and verifies a Mojang artifact before atomically publishing it. */
    public static void downloadToFile(String reqURL, Path target, String expectedSha1, long expectedSize) throws IOException {
        HttpURLConnection con = (HttpURLConnection) httpsUrl(reqURL).openConnection();
        Path temporary = target.resolveSibling(target.getFileName() + ".part");
        try {
            con.setRequestProperty("User-Agent", getUserAgent());
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            requireSuccess(con);

            MessageDigest digest = sha1();
            try (InputStream in = new DigestInputStream(con.getInputStream(), digest)) {
                Files.copy(in, temporary, StandardCopyOption.REPLACE_EXISTING);
            }
            long size = Files.size(temporary);
            if (expectedSize >= 0 && size != expectedSize) {
                throw new IOException("Downloaded size mismatch for " + reqURL + ": expected " + expectedSize + ", got " + size);
            }
            String actualSha1 = HexFormat.of().formatHex(digest.digest());
            if (expectedSha1 != null && !actualSha1.equalsIgnoreCase(expectedSha1)) {
                throw new IOException("Downloaded SHA-1 mismatch for " + reqURL);
            }
            try {
                Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    /**
     * Get the string output from the passed {@link HttpURLConnection}
     *
     * @param con The connection to get the string from
     * @return The body of the returned page
     * @throws IOException If the request fails
     */
    private static String connectionToString(HttpURLConnection con) throws IOException {
        requireSuccess(con);
        InputStream inputStream = con.getInputStream();

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                content.append("\n");
            }

            con.disconnect();
        }

        return content.toString();
    }

    public static String getUserAgent() {
        return "Geyser-PackConverter/3.4.3-SNAPSHOT"; // TODO Pull this from buildscript, BuildConstants?
    }

    private static URL httpsUrl(String value) throws IOException {
        URI uri;
        try {
            uri = URI.create(value);
        } catch (IllegalArgumentException exception) {
            throw new IOException("Invalid download URL", exception);
        }
        if (!"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IOException("Refusing non-HTTPS download URL: " + value);
        }
        return uri.toURL();
    }

    private static void requireSuccess(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        if (status < 200 || status >= 300) {
            throw new IOException("HTTP " + status + " from " + connection.getURL());
        }
    }

    private static MessageDigest sha1() {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-1 is unavailable", exception);
        }
    }
}
