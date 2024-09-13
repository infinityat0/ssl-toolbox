package com.jmpeax.ssltoolbox.jks.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.jmpeax.ssltoolbox.jks.JKSView;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class ExportCert extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        var certificateHelper = ApplicationManager.getApplication().getService(CertificateHelper.class);
        var ksVirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        var view = getViewComponent(e);
        if (view == null) {
            Messages.showErrorDialog("No JKS view found", "No JKS View");
        }
        var selectedAlias = view.getSelectedCertificate();
        if (selectedAlias == null) {
            Messages.showErrorDialog("No certificate selected", "No Certificate Selected");
        }
        var descriptor = new FileChooserDescriptor(
                false,  // Choose Files
                true,
                false,
                false,
                false,
                false
        );
        var pwd = Messages.showPasswordDialog("Keystore password", "KeyStore Password");
        if (pwd != null && !pwd.isBlank()) {
            certificateHelper.exportCertificate(ksVirtualFile, selectedAlias, pwd.toCharArray());
            Messages.showInfoMessage("Certificate exported", "Certificate Exported");
            view.removeCertificate(selectedAlias);
        }
    }

    private JKSView getViewComponent(@NotNull AnActionEvent e) {
        // Retrieve your view component from context, e.g., using the data context from the event
        return e.getData(PlatformDataKeys.CONTEXT_COMPONENT) instanceof JKSView
                ? (JKSView) e.getData(PlatformDataKeys.CONTEXT_COMPONENT)
                : null;
    }
}
