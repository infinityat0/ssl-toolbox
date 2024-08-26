package com.jmpeax.ssltoolbox.ui.pem;

import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import com.jmpeax.ssltoolbox.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

/**
 * The `PemView` class represents a panel that displays information about an X.509 certificate in PEM format.
 * It extends the `JBPanel` class and provides a graphical user interface for displaying the certificate details.
 * <p>
 * The constructor of the `PemView` class takes an `X509Certificate` parameter, which is used to initialize the panel
 * with the certificate information.
 * <p>
 * The `PemView` panel layout consists of a grid with labels and text fields. Each label represents a certain
 * certificate detail, such as the subject, issuer, serial number, valid from/to dates, public key, and signature algorithm.
 * The corresponding certificate information is displayed in the adjacent text field.
 * <p>
 * The `PemView` class also provides two private helper methods: `formatDate()` and `buildText()`.
 * <p>
 * The `formatDate()` method is used to convert a `java.util.Date` object to a formatted string representation
 * using the ISO 8601 date and time format.
 * <p>
 * The `buildText()` method is used to create and configure a read-only `JTextField` with a specified text value.
 * This method is used to create the text fields displaying the certificate details in the panel.
 * <p>
 * The `PemView` class does not provide any public methods or properties other than the constructor.*/
public class PemView extends JBPanel {

    public PemView(@NotNull X509Certificate certificate) {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = JBUI.insets(5, 15, 5, 5); // Padding between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(buildLabel("x509.subject"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buildText(certificate.getSubjectX500Principal().getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label
        add(buildLabel("x509.issuer"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buildText(certificate.getIssuerX500Principal().getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label
        add( buildLabel("x509.serial-number"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buildText(certificate.getSerialNumber().toString(16)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label
        add(buildLabel("x509.valid-from"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(buildText(formatDate(certificate.getNotBefore())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label

        //add(new JLabel("Valid To:", AllIcons.Ide.FatalError,SwingConstants.TRAILING), gbc);
        add(buildLabel("x509.valid-to"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buildText(formatDate(certificate.getNotAfter())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label
        add(buildLabel("x509.public-key"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(buildText(Base64.getEncoder().encodeToString(certificate.getPublicKey().getEncoded())), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;    // Reset weightx for the label
        gbc.fill = GridBagConstraints.NONE; // Reset fill for the label
        add(buildLabel("x509.signature-algorithm"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buildText(certificate.getSigAlgName()), gbc);

        // Add spacer to push content to the top
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weighty = 1.0; // This will take up the remaining vertical space
        gbc.fill = GridBagConstraints.BOTH;
        add(new JPanel(), gbc);
    }

    private String formatDate(Date date) {
        Instant instant = date.toInstant();
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
       return isoFormatter.format(offsetDateTime);
    }

    private JTextField buildText(String text){
        var textField = new JTextField(text,30);
        textField.setEditable(false);
        return textField;
    }

    private JLabel buildLabel(String nlsKey){
        return new JLabel(Messages.getMessage(nlsKey));
    }

}