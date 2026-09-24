package hospital.gui.components;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AppTextField extends JTextField
        implements ThemeManager.ThemeChangeListener {

    private boolean valid = true;
    private boolean error = false;

    public AppTextField() {
        super();

        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(new LineBorder(
                ThemeManager.getCurrentTheme().getBorderColor(),
                1
        ));

        setBackground(Color.WHITE);
        setForeground(new Color(40, 40, 40));

        setCaretColor(
                ThemeManager.getCurrentTheme().getPrimaryColor()
        );

        setBorderColor(
                ThemeManager.getCurrentTheme().getBorderColor()
        );

        addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (!error) {
                    setBorderColor(
                            ThemeManager.getCurrentTheme().getPrimaryColor()
                    );
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (!error) {
                    setBorderColor(
                            ThemeManager.getCurrentTheme().getBorderColor()
                    );
                }
            }
        });

        ThemeManager.addThemeChangeListener(this);
    }

    private void setBorderColor(Color color) {
        setBorder(new LineBorder(color, 2));
    }

    public void setValidState(boolean valid) {

        this.valid = valid;
        this.error = !valid;

        Theme theme = ThemeManager.getCurrentTheme();

        if (valid) {
            setBorderColor(theme.getSuccessColor());
        } else {
            setBorderColor(theme.getErrorColor());
        }
    }

    public void clearValidation() {

        this.error = false;
        this.valid = true;

        setBorderColor(
                ThemeManager.getCurrentTheme().getBorderColor()
        );
    }

    @Override
    public void themeChanged(Theme newTheme) {

        setCaretColor(newTheme.getPrimaryColor());

        if (!error) {
            setBorderColor(newTheme.getBorderColor());
        }

        repaint();
    }
}