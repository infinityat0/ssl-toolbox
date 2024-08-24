package com.jmpeax.keytoolj.ui;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;

public class KeystoreToolWindow extends JPanel{

    private final JBLabel fileLabel;

    public KeystoreToolWindow(){
        super(true);
        fileLabel = new JBLabel("No file selected.");
        this.add(fileLabel);
    }

    public void updateContent(VirtualFile f){
        fileLabel.setText(f.getName());
    }
}
