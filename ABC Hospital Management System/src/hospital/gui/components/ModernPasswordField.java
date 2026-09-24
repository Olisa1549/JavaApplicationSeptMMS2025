package hospital.gui.components;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernPasswordField extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private final JPasswordField passwordField;
    private final JButton toggleButton;

    private boolean visible = false;
    private boolean error = false;

    public ModernPasswordField() {

        setLayout(new BorderLayout());

        setOpaque(true);
        setBackground(Color.WHITE);

        passwordField = new JPasswordField();

        passwordField.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        passwordField.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 12, 0, 5
                )
        );

        passwordField.setEchoChar('•');

        toggleButton = new JButton("Show");

        toggleButton.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
        );

        toggleButton.setForeground(
                ThemeManager.getCurrentTheme().getPrimaryColor()
        );

        toggleButton.setBackground(Color.WHITE);

        toggleButton.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 5, 0, 10
                )
        );

        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        toggleButton.addActionListener(
                e -> togglePassword()
        );

        add(passwordField, BorderLayout.CENTER);
        add(toggleButton, BorderLayout.EAST);

        updateBorder(
                ThemeManager.getCurrentTheme()
                        .getBorderColor()
        );

        passwordField.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(
                            java.awt.event.FocusEvent e) {

                        if (!error) {
                            updateBorder(
                                    ThemeManager
                                            .getCurrentTheme()
                                            .getPrimaryColor()
                            );
                        }
                    }

                    @Override
                    public void focusLost(
                            java.awt.event.FocusEvent e) {

                        if (!error) {
                            updateBorder(
                                    ThemeManager
                                            .getCurrentTheme()
                                            .getBorderColor()
                            );
                        }
                    }
                }
        );

        ThemeManager.addThemeChangeListener(this);
    }

    private void togglePassword() {

        visible = !visible;

        if (visible) {
            passwordField.setEchoChar((char) 0);
            toggleButton.setText("Hide");
        } else {
            passwordField.setEchoChar('•');
            toggleButton.setText("Show");
        }
    }

    private void updateBorder(Color color) {

        setBorder(
                new LineBorder(color, 2)
        );
    }

    public char[] getPassword() {

        return passwordField.getPassword();
    }

    public void requestFocusInField() {

        passwordField.requestFocus();
    }

    public void setValidState(boolean valid) {

        error = !valid;

        Theme theme =
                ThemeManager.getCurrentTheme();

        updateBorder(
                valid
                        ? theme.getSuccessColor()
                        : theme.getErrorColor()
        );
    }

    public void clearValidation() {

        error = false;

        updateBorder(
                ThemeManager
                        .getCurrentTheme()
                        .getBorderColor()
        );
    }

    @Override
    public void themeChanged(Theme newTheme) {

        toggleButton.setForeground(
                newTheme.getPrimaryColor()
        );

        if (!error) {
            updateBorder(
                    newTheme.getBorderColor()
            );
        }

        repaint();
    }
}