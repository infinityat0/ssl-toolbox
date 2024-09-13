package com.jmpeax.ssltoolbox.jks.actions;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.cert.X509Certificate;

public class ListenerDataContext implements DataContext {
    private final VirtualFile file;
    private final OnImport onImport;

    public ListenerDataContext(VirtualFile file,OnImport onImport) {
        this.file = file;
        this.onImport = onImport;
    }

    @Override
    public @Nullable Object getData(@NotNull String dataId) {
        return file;
    }

    @Override
    public <T> @Nullable T getData(@NotNull DataKey<T> key) {
        return DataContext.super.getData(key);
    }

    public void update(String alias, X509Certificate certificate){
        onImport.imported(alias,certificate);
    }

    public interface OnImport {
        void imported(String alias, X509Certificate cert);
    }
}
