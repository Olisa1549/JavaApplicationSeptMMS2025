package hospital.gui.theme;

import java.awt.Color;

/**
 * Represents the complete visual theme of the application.
 */
public class Theme {

    private final String name;

    private final Color primaryColor;
    private final Color secondaryColor;
    private final Color accentColor;

    private final Color backgroundColor;
    private final Color surfaceColor;
    private final Color cardColor;

    private final Color textColor;
    private final Color secondaryTextColor;

    private final Color borderColor;

    private final Color successColor;
    private final Color warningColor;
    private final Color errorColor;
    private final Color infoColor;

    public Theme(
            String name,
            Color primaryColor,
            Color secondaryColor,
            Color accentColor,
            Color backgroundColor,
            Color surfaceColor,
            Color cardColor,
            Color textColor,
            Color secondaryTextColor,
            Color borderColor,
            Color successColor,
            Color warningColor,
            Color errorColor,
            Color infoColor
    ) {

        this.name = name;

        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.accentColor = accentColor;

        this.backgroundColor = backgroundColor;
        this.surfaceColor = surfaceColor;
        this.cardColor = cardColor;

        this.textColor = textColor;
        this.secondaryTextColor = secondaryTextColor;

        this.borderColor = borderColor;

        this.successColor = successColor;
        this.warningColor = warningColor;
        this.errorColor = errorColor;
        this.infoColor = infoColor;
    }

    public String getName() {
        return name;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public Color getAccentColor() {
        return accentColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getSurfaceColor() {
        return surfaceColor;
    }

    public Color getCardColor() {
        return cardColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getSecondaryTextColor() {
        return secondaryTextColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getSuccessColor() {
        return successColor;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getInfoColor() {
        return infoColor;
    }

    @Override
    public String toString() {
        return name;
    }
}