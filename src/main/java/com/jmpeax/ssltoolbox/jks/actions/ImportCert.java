package com.jmpeax.ssltoolbox.jks.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ImportCert extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            var certificateHelper = ApplicationManager.getApplication().getService(CertificateHelper.class);
            var ksVirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
            if (ksVirtualFile == null) {
                Messages.showErrorDialog("No Keystore file", "No Keystore File");
                return;
            }
            var descriptor = new FileChooserDescriptor(
                    true,  // Choose Files
                    false,
                    false,
                    false,
                    false,
                    false
            );
            VirtualFile file = FileChooser.chooseFile(descriptor, null, null);
            if (file == null) {
                Messages.showErrorDialog("No certificate selected", "No Certificate Selected");
                return;
            }
            var certAlias = certificateHelper.getCertificate(file)
                    .stream().findFirst().map(certificateHelper::getCommonName).orElse("");
            var selectedAlias = new PrefillInputDialog(certAlias, "Certificate Alias", "Certificate Alias");
            var ok = selectedAlias.showAndGet();
            if (!ok) {
                Messages.showErrorDialog("No alias provided", "No Alias Provided");
                return;
            }
            var pwd = Messages.showPasswordDialog("Keystore password", "KeyStore Password");
            if (pwd != null && !pwd.isBlank()) {
                certificateHelper.importCertificate(ksVirtualFile, file,selectedAlias.getInput(), pwd.toCharArray());
                Messages.showInfoMessage("Certificate imported", "Certificate Imported");

            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
