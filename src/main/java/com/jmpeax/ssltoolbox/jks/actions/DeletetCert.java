package com.jmpeax.ssltoolbox.jks.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;
import com.jmpeax.ssltoolbox.jks.JKSView;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import org.jetbrains.annotations.NotNull;

public class DeletetCert extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        var certificateHelper = ApplicationManager.getApplication().getService(CertificateHelper.class);
        var ksVirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        var view = getViewComponent(e);
        if (view == null) {
            Messages.showErrorDialog("No JKS view found", "No JKS View");
            return;
        }
        var selectedAlias = view.getSelectedCertificate();
        if (selectedAlias == null) {
            Messages.showErrorDialog("No certificate selected", "No Certificate Selected");
            return;
        }
        var pwd = Messages.showPasswordDialog("Keystore password", "KeyStore Password");
        if (pwd != null && !pwd.isBlank()) {
            certificateHelper.removeCertificate(ksVirtualFile, selectedAlias,pwd.toCharArray());
            view.removeCertificate(selectedAlias);
            Messages.showInfoMessage("Certificate removed", "Certificate Removed");
        }
    }



    private JKSView getViewComponent(@NotNull AnActionEvent e) {
        // Retrieve your view component from context, e.g., using the data context from the event
        return e.getData(PlatformDataKeys.CONTEXT_COMPONENT) instanceof JKSView
                ? (JKSView) e.getData(PlatformDataKeys.CONTEXT_COMPONENT)
                : null;
    }
}
