package com.jmpeax.ssltoolbox.utils;

import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.PropertyKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Service
public final class Messages {
    private static final String BUNDLE_NAME = "messages";
    private static final Logger LOG = LoggerFactory.getLogger(Messages.class);
    private ResourceBundle bundle;

    public Messages() {
        try {
            this.bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (MissingResourceException ex) {
            LOG.warn("No bundle for current locale");
            this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
        }
    }

    public String getMessage(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        try {
            return this.bundle.getString(key);
        } catch (MissingResourceException e) {
            LOG.warn("No message for {} in bundle {}", key, bundle.getLocale().getLanguage());
            return key;
        }
    }
}