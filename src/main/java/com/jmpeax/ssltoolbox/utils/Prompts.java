package com.jmpeax.ssltoolbox.utils;

import javax.swing.*;

public class Prompts {


    public static char[] askPassword(JPanel parent) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.requestFocusInWindow();
        int option = JOptionPane.showConfirmDialog(parent, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            return passwordField.getPassword();
        } else {
            return null;
        }
    }
}
