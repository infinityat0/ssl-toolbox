package com.jmpeax.ssltoolbox.jks.actions;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class PrefillInputDialog extends DialogWrapper {
    private final JTextField textField;
    private final String inputLabel;

    public PrefillInputDialog(String initialText,String title,String inputLabel) {
        super(false);
        this.textField = new JTextField(initialText);
        this.inputLabel = inputLabel;
        init();
        setTitle(title);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(this.inputLabel), BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (textField.getText().trim().isEmpty()) {
            return new ValidationInfo("Input cannot be empty", textField);
        }
        return super.doValidate();
    }

    public String getInput() {
        return textField.getText();
    }
}