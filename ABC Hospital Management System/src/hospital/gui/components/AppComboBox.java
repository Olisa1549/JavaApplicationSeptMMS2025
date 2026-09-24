package hospital.gui.components;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class AppComboBox<T> extends JComboBox<T>
        implements ThemeManager.ThemeChangeListener {

    public AppComboBox() {
        super();

        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        setBackground(Color.WHITE);
        setForeground(new Color(40, 40, 40));

        setBorder(
                BorderFactory.createLineBorder(
                        ThemeManager.getCurrentTheme().getBorderColor()
                )
        );

        ThemeManager.addThemeChangeListener(this);
    }

    @Override
    public void themeChanged(Theme newTheme) {

        setBorder(
                BorderFactory.createLineBorder(
                        newTheme.getBorderColor()
                )
        );

        repaint();
    }
}