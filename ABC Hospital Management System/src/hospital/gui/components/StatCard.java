package hospital.gui.components;

import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatCard extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private final AppIcon.IconType iconType;

    private AppIcon icon;

    private JLabel titleLabel;
    private JLabel valueLabel;
    private JLabel descriptionLabel;

    private JPanel iconPanel;
    private JPanel textPanel;

    public StatCard(
            String title,
            String value,
            String description,
            AppIcon.IconType iconType
    ) {

        this.iconType = iconType;

        setLayout(
                new BorderLayout(
                        15,
                        0
                )
        );

        setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        setOpaque(true);

        createIcon();
        createText();

        // IMPORTANT:
        // Put the constructor values into the labels.
        setTitle(title);
        setValue(value);
        setDescription(description);

        ThemeManager.addThemeChangeListener(
                this
        );

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // ICON
    // =========================================================

    private void createIcon() {

        iconPanel =
                new JPanel(
                        new GridBagLayout()
                );

        iconPanel.setPreferredSize(
                new Dimension(
                        52,
                        52
                )
        );

        iconPanel.setMinimumSize(
                new Dimension(
                        52,
                        52
                )
        );

        iconPanel.setMaximumSize(
                new Dimension(
                        52,
                        52
                )
        );

        icon =
                new AppIcon(iconType);

        iconPanel.add(icon);

        add(
                iconPanel,
                BorderLayout.WEST
        );
    }

    // =========================================================
    // TEXT
    // =========================================================

    private void createText() {

        textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        // Title
        titleLabel =
                new JLabel();

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Value
        valueLabel =
                new JLabel();

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        valueLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Description
        descriptionLabel =
                new JLabel();

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        descriptionLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(4)
        );

        textPanel.add(
                valueLabel
        );

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(
                descriptionLabel
        );

        add(
                textPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // SETTERS
    // =========================================================

    public void setTitle(
            String title
    ) {

        titleLabel.setText(
                title != null
                        ? title
                        : ""
        );
    }

    public void setValue(
            String value
    ) {

        valueLabel.setText(
                value != null
                        ? value
                        : ""
        );
    }

    public void setDescription(
            String description
    ) {

        descriptionLabel.setText(
                description != null
                        ? description
                        : ""
        );
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public String getTitle() {

        return titleLabel.getText();
    }

    public String getValue() {

        return valueLabel.getText();
    }

    public String getDescription() {

        return descriptionLabel.getText();
    }

    // =========================================================
    // THEME
    // =========================================================

    private void applyTheme(
            Theme theme
    ) {

        if (theme == null) {
            return;
        }

        setBackground(
                theme.getCardColor()
        );

        iconPanel.setBackground(
                theme.getAccentColor()
        );

        titleLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        valueLabel.setForeground(
                theme.getTextColor()
        );

        descriptionLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        icon.setIconColor(
                Color.WHITE
        );

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                theme.getBorderColor()
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        revalidate();
        repaint();
    }

    @Override
    public void themeChanged(
            Theme newTheme
    ) {

        applyTheme(newTheme);
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        if (icon != null) {
            icon.dispose();
        }
    }
}