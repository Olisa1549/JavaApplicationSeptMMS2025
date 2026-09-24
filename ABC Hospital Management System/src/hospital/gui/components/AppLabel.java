package hospital.gui.components;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class AppLabel extends JLabel
        implements ThemeManager.ThemeChangeListener {

    public enum LabelType {
        TITLE,
        SUBTITLE,
        BODY,
        MUTED,
        LABEL
    }

    private final LabelType type;

    public AppLabel(String text, LabelType type) {
        super(text);

        this.type = type;

        setFont(getFontForType(type));

        applyTheme(ThemeManager.getCurrentTheme());

        ThemeManager.addThemeChangeListener(this);
    }

    private Font getFontForType(LabelType type) {

        switch (type) {

            case TITLE:
                return new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                );

            case SUBTITLE:
                return new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                );

            case BODY:
                return new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                );

            case MUTED:
                return new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                );

            case LABEL:
                return new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                );

            default:
                return new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                );
        }
    }

    private void applyTheme(Theme theme) {

        switch (type) {

            case TITLE:
                setForeground(theme.getTextColor());
                break;

            case SUBTITLE:
                setForeground(theme.getSecondaryTextColor());
                break;

            case BODY:
                setForeground(theme.getTextColor());
                break;

            case MUTED:
                setForeground(theme.getSecondaryTextColor());
                break;

            case LABEL:
                setForeground(theme.getTextColor());
                break;
        }
    }

    @Override
    public void themeChanged(Theme newTheme) {

        applyTheme(newTheme);
        repaint();
    }
}