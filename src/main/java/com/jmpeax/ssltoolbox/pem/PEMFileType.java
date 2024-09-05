package com.jmpeax.ssltoolbox.pem;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PEMFileType implements FileType {

    public static final PEMFileType INSTANCE = new PEMFileType();
    @Override
    public @NonNls @NotNull String getName() {
        return "PEM file";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "Pem Files";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "pem";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Any_type;
    }

    @Override
    public boolean isBinary() {
        return true;
    }
}
