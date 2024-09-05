package com.jmpeax.ssltoolbox.jks;

import com.intellij.diff.util.FileEditorBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import com.jmpeax.ssltoolbox.utils.Prompts;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

public class JKSFileEditor extends FileEditorBase {

    private final VirtualFile file;
    private final JKSView jksView;

    public JKSFileEditor(VirtualFile file) {
        this.file = file;
        this.jksView= new JKSView(file);
    }

    @Override
    public @NotNull JComponent getComponent() {

        return this.jksView;

    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return this.jksView.getUnlockText();
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) @NotNull String getName() {
        return "Keystore Viewer";
    }

    @Override
    public VirtualFile getFile() {
        return file;
    }
}
