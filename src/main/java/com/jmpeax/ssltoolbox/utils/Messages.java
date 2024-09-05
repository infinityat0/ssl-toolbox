package com.jmpeax.ssltoolbox.utils;

import java.util.ResourceBundle;
import org.jetbrains.annotations.PropertyKey;

public class Messages {
    private static final String BUNDLE_NAME = "messages";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
        // Private constructor to prevent instantiation
    }

    public static String getMessage(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
    }
}