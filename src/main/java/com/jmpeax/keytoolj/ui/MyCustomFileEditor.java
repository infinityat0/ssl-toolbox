package com.jmpeax.keytoolj.ui;

import com.intellij.diff.util.FileEditorBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBPanel;
import com.jmpeax.keytoolj.svc.CertificateHelper;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class MyCustomFileEditor extends FileEditorBase {
    private final VirtualFile file;
    private final JBPanel panel;
    private final @NotNull Project project;


    public MyCustomFileEditor(@NotNull VirtualFile file, @NotNull Project project)
            throws CertificateException, IOException {
        this.file = file;
        this.project = project;
        this.panel = new JBPanel();
        var c = CertificateHelper.getCertificate(file.getInputStream());
        if (c.isEmpty()){
            this.panel.add(new JLabel("Error loading " + file.getName()));

        }else {
            this.panel.add(new JLabel("Custom editor for " + file.getName()));
            this.panel.add(new JLabel("Sub" + c.get().getSubjectDN().getName()));
        }
    }


    @Override
    public @NotNull JComponent getComponent() {
        return panel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) @NotNull String getName() {
        return "Keytool";
    }

    @Override
    public VirtualFile getFile() {
        return file;
    }
}