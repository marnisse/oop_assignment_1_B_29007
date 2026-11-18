package com.auca.interfaces;

/**
 * Identifiable interface - all domain entities implement this.
 * Interface constants are public static final by definition.
 */
public interface Identifiable {
    String ID_PREFIX = "AUCA";
    int MIN_ID_LENGTH = 4;
    int MAX_ID_LENGTH = 30;
    String ID_SEPARATOR = "-";
    String DEFAULT_STATUS = "ACTIVE";
    int ENTITY_VERSION = 1;

    String getId();
    String getDisplayName();
    boolean isActive();
}
