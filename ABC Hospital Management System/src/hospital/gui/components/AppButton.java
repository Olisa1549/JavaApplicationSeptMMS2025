package hospital.gui.components;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppButton extends JButton implements ThemeManager.ThemeChangeListener {

    private final Color originalBackground;
    private final Color originalForeground;

    public AppButton(String text) {
        super(text);

        originalBackground = null;
        originalForeground = null;

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);

        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setBorder(new EmptyBorder(10, 18, 10, 18));

        applyTheme(ThemeManager.getCurrentTheme());

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (isEnabled()) {
                    setBackground(ThemeManager.getCurrentTheme().getAccentColor());
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (isEnabled()) {
                    applyTheme(ThemeManager.getCurrentTheme());
                }
            }
        });

        ThemeManager.addThemeChangeListener(this);
    }

    private void applyTheme(Theme theme) {
        setBackground(theme.getPrimaryColor());
        setForeground(Color.WHITE);
    }

    @Override
    public void themeChanged(Theme newTheme) {
        applyTheme(newTheme);
        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            applyTheme(ThemeManager.getCurrentTheme());
        } else {
            setBackground(new Color(210, 214, 220));
            setForeground(new Color(130, 135, 140));
        }
    }
}