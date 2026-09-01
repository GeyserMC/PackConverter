package org.geysermc.pack.converter.util;

/** One input budget shared by mounted directories and compressed mod archives. */
final class ResourceBudget {
    static final int MAX_FILES = 100_000;
    static final int MAX_DEPTH = 64;
    static final long MAX_FILE_BYTES = 256L * 1024 * 1024;
    static final long MAX_TOTAL_BYTES = 2L * 1024 * 1024 * 1024;

    private ResourceBudget() {
    }
}
