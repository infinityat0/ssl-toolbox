package com.jmpeax.keytoolj.ui;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.cert.CertificateException;

public class PEMFileEditorProvider implements FileEditorProvider {
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        // Define the criteria to accept the file type
        return "pem".equals(file.getExtension());
    }

    @Override
    @NotNull
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        try {
            return new PEMFileEditor(file);
        } catch (CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public String getEditorTypeId() {
        return "my-custom-file-editor";
    }

    @Override
    @NotNull
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR;
    }
}