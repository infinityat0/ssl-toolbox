package com.jmpeax.ssltoolbox.jks.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ImportCert extends AnAction {

    private static final Logger LOGGER = Logger.getInstance(ImportCert.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            var f = e.getData(CommonDataKeys.VIRTUAL_FILE);
            if (f == null) {
                LOGGER.warn("JKS not send to the Action");
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
                LOGGER.warn("User did not select a certificate");
                return;
            }
            Messages.showInputDialog("Cert alias","Certificate Alias", Messages.getQuestionIcon());
            var pwd = Messages.showPasswordDialog("Keystore password", "KeyStore Password");
            if (pwd != null && !pwd.isBlank()) {
                CertificateHelper.importCertificate(f.getInputStream(), file.getInputStream(), pwd.toCharArray());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
