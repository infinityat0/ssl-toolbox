package com.jmpeax.ssltoolbox.jks;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JKSFileType implements FileType {
    public static final JKSFileType INSTANCE = new JKSFileType();

    @Override
    public @NotNull String getName() {
        return "JKS file";
    }

    @Override
    public @NotNull String getDescription() {
        return "JKS files";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "jks";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.Diff.Lock;
    }

    @Override
    public boolean isBinary() {
        return true;
    }
}
