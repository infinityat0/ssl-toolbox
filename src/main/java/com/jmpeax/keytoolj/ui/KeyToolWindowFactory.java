package com.jmpeax.keytoolj.ui;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class KeyToolWindowFactory implements ToolWindowFactory,
        FileEditorManagerListener,DumbAware {

    private final KeystoreToolWindow keystoreToolWindow;

    public KeyToolWindowFactory() {
        this.keystoreToolWindow = new KeystoreToolWindow();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        Content content = ContentFactory.getInstance().createContent(
               this.keystoreToolWindow,
                "",true);
        toolWindow.getContentManager().addContent(content);
        project.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this);
    }


    public static VirtualFile getSelectedFile(Project project) {
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        VirtualFile[] selectedFiles = fileEditorManager.getSelectedFiles();
        return selectedFiles.length > 0 ? selectedFiles[0] : null;
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        FileEditorManagerListener.super.selectionChanged(event);
        VirtualFile newFile = event.getNewFile();
        if (newFile != null) {
            keystoreToolWindow.updateContent(newFile);
        }
    }
}
