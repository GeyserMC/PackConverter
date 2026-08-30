package org.geysermc.pack.converter.type.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

/** Exact, immutable runtime source and classpath for one conversion. */
public record ReflectionInput(@NotNull Path modJar, @NotNull List<Path> classpath, @Nullable Path clientRuntime) {
    public ReflectionInput {
        modJar = modJar.toAbsolutePath().normalize();
        classpath = classpath.stream().map(path -> path.toAbsolutePath().normalize()).distinct().toList();
        if (clientRuntime != null) clientRuntime = clientRuntime.toAbsolutePath().normalize();
    }
}
