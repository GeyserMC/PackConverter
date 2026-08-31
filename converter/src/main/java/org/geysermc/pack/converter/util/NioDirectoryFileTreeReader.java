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

package org.geysermc.pack.converter.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.serialize.minecraft.fs.FileTreeReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public final class NioDirectoryFileTreeReader implements FileTreeReader {

    private final List<Directory> directories = new ArrayList<>();
    private final Set<String> emitted = new HashSet<>();
    private int directoryCursor;
    private Path @Nullable [] paths;
    private int pathCursor;
    private Path currentRoot;
    private Path nextPath;
    private String nextName;

    private InputStream currentStream;

    NioDirectoryFileTreeReader(Path root) {
        this(List.of(root));
    }

    NioDirectoryFileTreeReader(Collection<Path> roots) {
        roots.forEach(root -> directories.add(new Directory(root, root)));
    }

    @Override
    public boolean hasNext() {
        if (nextPath != null) return true;
        while (true) {
            if (paths == null) {
                // we must look for a folder and make
                // files be equal to its children
                if (directoryCursor >= directories.size()) {
                    // no more folders
                    return false;
                }

                Directory directory = directories.get(directoryCursor++);
                currentRoot = directory.root();
                try {
                    try (var children = Files.list(directory.path())) {
                        paths = children.sorted(Comparator.comparing(Path::toString)).toArray(Path[]::new);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Exception listing directories from " + directory, e);
                }

                if (paths == null) {
                    throw new IllegalStateException("Null children from path " + directory);
                }
            }

            while (pathCursor < paths.length) {
                Path path = paths[pathCursor++];
                // Resource roots are data. Following a symlink could escape the
                // selected pack root or create a directory cycle.
                if (Files.isSymbolicLink(path)) {
                    continue;
                }
                if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                    directories.add(new Directory(currentRoot, path));
                } else {
                    String name = relativize(currentRoot, path);
                    if (emitted.add(name)) {
                        nextPath = path;
                        nextName = name;
                        return true;
                    }
                }
            }
            paths = null;
            pathCursor = 0;
        }
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }

        if (currentStream != null) {
            closeUnchecked(currentStream);
            currentStream = null;
        }

        Path current = nextPath;
        String name = nextName;
        nextPath = null;
        nextName = null;
        try {
            currentStream = Files.newInputStream(current);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't open InputStream for: " + current, e);
        }
        return name;
    }

    @Override
    public @NotNull InputStream stream() {
        return currentStream;
    }

    @Override
    public void close() {
        if (currentStream != null) {
            closeUnchecked(currentStream);
            currentStream = null;
        }
    }

    private static String relativize(Path parent, Path child) {
        StringBuilder builder = new StringBuilder();
        builder.append(child.getFileName());

        while ((child = child.getParent()) != null) {
            if (parent.equals(child)) {
                // finished!
                return builder.toString();
            }
            // prepend current folder to the string builder
            builder.insert(0, child.getFileName() + "/");
        }

        throw new IllegalStateException("");
    }

    private static void closeUnchecked(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static NioDirectoryFileTreeReader read(Path root) {
        return new NioDirectoryFileTreeReader(root);
    }

    /** Reads layered resource roots in order; the first occurrence of a path wins. */
    public static NioDirectoryFileTreeReader read(Collection<Path> roots) {
        return new NioDirectoryFileTreeReader(roots);
    }

    private record Directory(Path root, Path path) {
    }
}
