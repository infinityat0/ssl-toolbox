package com.jmpeax.ssltoolbox.jks;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.util.ui.JBUI;
import com.jmpeax.ssltoolbox.pem.PemView;
import com.jmpeax.ssltoolbox.svc.CertificateHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JKSView extends JPanel {
    private JBList<String> list;
    private PemView pemView;
    private final VirtualFile file;
    private JPanel listPanel;
    private JPanel pemViewPanel;
    private JBPasswordField passwordField;

    public JKSView(@NotNull VirtualFile file) {
        super(new GridBagLayout());
        this.file = file;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5);
        gbc.fill = GridBagConstraints.BOTH;

        // Add unlock button at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(createUnlockButton(), gbc);

        // Add certificate list panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.45;
        gbc.weighty = 1.0;
        listPanel = createListPanel();
        add(listPanel, gbc);

        // Add PEM view panel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        pemViewPanel = createPemViewPanel();
        add(pemViewPanel, gbc);
    }

    private void loadPemView(X509Certificate certificate) {
        if (pemView != null) {
            pemViewPanel.remove(pemView);
        }
        pemView = new PemView(certificate);
        pemViewPanel.removeAll();
        pemViewPanel.add(pemView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Certificate List", SwingConstants.CENTER);
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(label);
        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPemViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Certificate Details");
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JPanel(), BorderLayout.CENTER); // Placeholder for PemView
        return panel;
    }

    private JPanel createUnlockButton() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.passwordField = new JBPasswordField();
        panel.setBorder(JBUI.Borders.empty(10));
        JButton unlockButton = getButton(passwordField, panel);
        JLabel passwordLabel = new JBLabel("Enter password: ");
        passwordField.requestFocusInWindow();

        // Set focus traversal keys for the password field to move to the unlock button
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(passwordField.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
        passwordField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        unlockButton.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());

        // Add password label (20%)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        panel.add(passwordLabel, gbc);

        // Add password field (60%)
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(passwordField, gbc);

        // Add unlock button (20%)
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        panel.add(unlockButton, gbc);

        return panel;
    }

    private @NotNull JButton getButton(JPasswordField passwordField, JPanel panel) {
        JButton unlockButton = new JButton("Unlock Certificate Store");
        unlockButton.addActionListener(e -> {
            char[] password = passwordField.getPassword();
            if (password != null) {
                try {
                    var certs = CertificateHelper.getKeyStoreCerts(file.getInputStream(), password);
                    updateView(certs);
                    // Remove the unlock button
                    //panel.removeAll();
                   // panel.add(buildToolBar());
                    revalidate();
                    repaint();
                } catch (IOException ex) {
                    throw new RuntimeException("Error reading JKS file", ex);
                }
            }
        });
        return unlockButton;
    }

    private JPanel buildToolBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton openButton = new JButton(AllIcons.Actions.Menu_open);

        openButton.setToolTipText("Open");
        openButton.setPreferredSize(new Dimension(AllIcons.Actions.Menu_open.getIconWidth() + 10, AllIcons.Actions.Menu_open.getIconHeight() + 10));

        openButton.addActionListener(e -> {
        });
        panel.add(openButton);
        JButton saveButton = new JButton(AllIcons.Actions.Menu_saveall);
        saveButton.setPreferredSize(new Dimension(AllIcons.Actions.Menu_saveall.getIconWidth() + 10, AllIcons.Actions.Menu_saveall.getIconHeight() + 10));

        saveButton.setToolTipText("Save");
        saveButton.addActionListener(e -> {
        });
        panel.add(saveButton);

        JButton closeButton = new JButton(AllIcons.Actions.Cancel);
        closeButton.setPreferredSize(new Dimension(AllIcons.Actions.Cancel.getIconWidth() + 10, AllIcons.Actions.Cancel.getIconHeight() + 10));

        closeButton.setToolTipText("Close");
        closeButton.addActionListener(e -> {
        });
        panel.add(closeButton);


        return panel;
    }

    private void updateView(Map<String, X509Certificate> certs) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        certs.keySet().forEach(listModel::addElement);
        list = new JBList<>(listModel);
        list.setCellRenderer(new IconListRenderer());
        // Add a selection listener to handle selection changes
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                var selectedIndex = list.getSelectedValue();
                if (selectedIndex != null && !selectedIndex.isEmpty()) {
                    loadPemView(certs.get(selectedIndex));
                }
            }
        });

        listPanel.removeAll();

        listPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public @Nullable JComponent getUnlockText() {
        return this.passwordField;
    }

    private static class IconListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(AllIcons.FileTypes.Any_type);
            return label;
        }
    }
}