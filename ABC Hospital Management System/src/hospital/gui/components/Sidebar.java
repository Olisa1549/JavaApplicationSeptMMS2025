package hospital.gui.components;

import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sidebar extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JLabel logoLabel;
    private JLabel hospitalLabel;

    private JButton dashboardButton;
    private JButton patientsButton;
    private JButton doctorsButton;
    private JButton appointmentsButton;
    private JButton recordsButton;
    private JButton pharmacyButton;
    private JButton billingButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton logoutButton;

    public Sidebar() {

        setLayout(
                new BorderLayout()
        );

        setPreferredSize(
                new Dimension(
                        268,
                        0
                )
        );

        setBorder(
                new EmptyBorder(
                        20,
                        15,
                        15,
                        15
                )
        );

        createUI();

        ThemeManager.addThemeChangeListener(
                this
        );

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // CREATE SIDEBAR
    // =========================================================

    private void createUI() {

        JPanel topPanel =
                new JPanel();

        topPanel.setOpaque(false);

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // HOSPITAL BRANDING
        // =====================================================

        JPanel brandingPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        brandingPanel.setOpaque(false);

        // Hospital logo
        logoLabel =
                new JLabel();

        AppIcon logoIcon =
                new AppIcon(
                        AppIcon.IconType.PLUS
                );

        logoIcon.setIconColor(
                Color.WHITE
        );

        brandingPanel.add(
                logoIcon
        );

        brandingPanel.add(
                Box.createHorizontalStrut(10)
        );

        hospitalLabel =
                new JLabel(
                        "<html>" +
                        "<b>LIFESAVER HOSPITAL</b><br>" +
                        "<font size='2'>Management System</font>" +
                        "</html>"
                );

        hospitalLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        brandingPanel.add(
                hospitalLabel
        );

        brandingPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        topPanel.add(
                brandingPanel
        );

        topPanel.add(
                Box.createVerticalStrut(30)
        );

        // =====================================================
        // NAVIGATION
        // =====================================================

        dashboardButton =
                createNavButton(
                        AppIcon.IconType.DASHBOARD,
                        "Dashboard"
                );

        patientsButton =
                createNavButton(
                        AppIcon.IconType.PATIENTS,
                        "Patients"
                );

        doctorsButton =
                createNavButton(
                        AppIcon.IconType.DOCTORS,
                        "Care Team"
                );

        appointmentsButton =
                createNavButton(
                        AppIcon.IconType.APPOINTMENTS,
                        "Appointments"
                );

        recordsButton =
                createNavButton(
                        AppIcon.IconType.RECORDS,
                        "Medical Records"
                );

        pharmacyButton =
                createNavButton(
                        AppIcon.IconType.PHARMACY,
                        "Pharmacy"
                );

        billingButton =
                createNavButton(
                        AppIcon.IconType.FINANCE,
                        "Billing / Finance"
                );

        reportsButton =
                createNavButton(
                        AppIcon.IconType.REPORTS,
                        "Reports"
                );

        settingsButton =
                createNavButton(
                        AppIcon.IconType.SETTINGS,
                        "Settings"
                );

        // -----------------------------------------------------
        // Add navigation buttons
        // -----------------------------------------------------

        addNavigationButton(
                topPanel,
                dashboardButton
        );

        addNavigationButton(
                topPanel,
                patientsButton
        );

        addNavigationButton(
                topPanel,
                doctorsButton
        );

        addNavigationButton(
                topPanel,
                appointmentsButton
        );

        addNavigationButton(
                topPanel,
                recordsButton
        );

        addNavigationButton(
                topPanel,
                pharmacyButton
        );

        addNavigationButton(
                topPanel,
                billingButton
        );

        addNavigationButton(
                topPanel,
                reportsButton
        );

        addNavigationButton(
                topPanel,
                settingsButton
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // LOGOUT
        // =====================================================

        logoutButton =
                createNavButton(
                        AppIcon.IconType.LOGOUT,
                        "Logout"
                );

        add(
                logoutButton,
                BorderLayout.SOUTH
        );
    }

    // =========================================================
    // ADD NAVIGATION BUTTON
    // =========================================================

    private void addNavigationButton(
            JPanel panel,
            JButton button) {

        panel.add(button);

        panel.add(
                Box.createVerticalStrut(6)
        );
    }

    // =========================================================
    // CREATE NAVIGATION BUTTON
    // =========================================================

    private JButton createNavButton(
            AppIcon.IconType iconType,
            String text) {

        JButton button =
                new JButton();

        // -----------------------------------------------------
        // Icon
        // -----------------------------------------------------

        AppIcon icon =
                new AppIcon(iconType);

        icon.setIconColor(
                Color.WHITE
        );

        // -----------------------------------------------------
        // Text
        // -----------------------------------------------------

        JLabel textLabel =
                new JLabel(text);

        textLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        textLabel.setForeground(
                Color.WHITE
        );

        // -----------------------------------------------------
        // Button layout
        // -----------------------------------------------------

        JPanel content =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        content.setOpaque(false);

        content.add(icon);

        content.add(
                Box.createHorizontalStrut(14)
        );

        content.add(textLabel);

        button.setLayout(
                new BorderLayout()
        );

        button.add(
                content,
                BorderLayout.WEST
        );

        // -----------------------------------------------------
        // Appearance
        // -----------------------------------------------------

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setContentAreaFilled(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        11,
                        14,
                        11,
                        14
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        46
                )
        );

        // -----------------------------------------------------
        // Store text/icon for theme and hover updates
        // -----------------------------------------------------

        button.putClientProperty(
                "icon",
                icon
        );

        button.putClientProperty(
                "textLabel",
                textLabel
        );

        // -----------------------------------------------------
        // Hover effect
        // -----------------------------------------------------

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        if (button.isEnabled()) {

                            Theme theme =
                                    ThemeManager
                                            .getCurrentTheme();

                            button.setBackground(
                                    theme.getAccentColor()
                            );

                            icon.setIconColor(
                                    Color.WHITE
                            );

                            textLabel.setForeground(
                                    Color.WHITE
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        if (button.isEnabled()) {

                            applyButtonTheme(
                                    button
                            );
                        }
                    }
                }
        );

        return button;
    }

    // =========================================================
    // ACTIVE BUTTON
    // =========================================================

    public void setActiveButton(
            JButton activeButton) {

        Theme theme =
                ThemeManager.getCurrentTheme();

        resetButton(
                dashboardButton
        );

        resetButton(
                patientsButton
        );

        resetButton(
                doctorsButton
        );

        resetButton(
                appointmentsButton
        );

        resetButton(
                recordsButton
        );

        resetButton(
                pharmacyButton
        );

        resetButton(
                billingButton
        );

        resetButton(
                reportsButton
        );

        resetButton(
                settingsButton
        );

        if (activeButton != null) {

            activeButton.setBackground(
                    theme.getAccentColor()
            );

            activeButton.setForeground(
                    Color.WHITE
            );

            AppIcon icon =
                    (AppIcon)
                            activeButton
                                    .getClientProperty(
                                            "icon"
                                    );

            JLabel textLabel =
                    (JLabel)
                            activeButton
                                    .getClientProperty(
                                            "textLabel"
                                    );

            if (icon != null) {

                icon.setIconColor(
                        Color.WHITE
                );
            }

            if (textLabel != null) {

                textLabel.setForeground(
                        Color.WHITE
                );
            }
        }
    }

    // =========================================================
    // RESET BUTTON
    // =========================================================

    private void resetButton(
            JButton button) {

        if (button == null) {
            return;
        }

        applyButtonTheme(button);
    }

    // =========================================================
    // APPLY BUTTON THEME
    // =========================================================

    private void applyButtonTheme(
            JButton button) {

        if (button == null) {
            return;
        }

        Theme theme =
                ThemeManager.getCurrentTheme();

        button.setBackground(
                theme.getPrimaryColor()
        );

        button.setForeground(
                Color.WHITE
        );

        AppIcon icon =
                (AppIcon)
                        button.getClientProperty(
                                "icon"
                        );

        JLabel textLabel =
                (JLabel)
                        button.getClientProperty(
                                "textLabel"
                        );

        if (icon != null) {

            icon.setIconColor(
                    Color.WHITE
            );
        }

        if (textLabel != null) {

            textLabel.setForeground(
                    Color.WHITE
            );
        }
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public JButton getDashboardButton() {
        return dashboardButton;
    }

    public JButton getPatientsButton() {
        return patientsButton;
    }

    public JButton getDoctorsButton() {
        return doctorsButton;
    }

    public JButton getAppointmentsButton() {
        return appointmentsButton;
    }

    public JButton getRecordsButton() {
        return recordsButton;
    }

    public JButton getPharmacyButton() {
        return pharmacyButton;
    }

    public JButton getBillingButton() {
        return billingButton;
    }

    public JButton getReportsButton() {
        return reportsButton;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    // =========================================================
    // THEME
    // =========================================================

    private void applyTheme(
            Theme theme) {

        setBackground(
                theme.getPrimaryColor()
        );

        applyButtonTheme(
                dashboardButton
        );

        applyButtonTheme(
                patientsButton
        );

        applyButtonTheme(
                doctorsButton
        );

        applyButtonTheme(
                appointmentsButton
        );

        applyButtonTheme(
                recordsButton
        );

        applyButtonTheme(
                pharmacyButton
        );

        applyButtonTheme(
                billingButton
        );

        applyButtonTheme(
                reportsButton
        );

        applyButtonTheme(
                settingsButton
        );

        applyButtonTheme(
                logoutButton
        );

        if (hospitalLabel != null) {

            hospitalLabel.setForeground(
                    Color.WHITE
            );
        }

        repaint();
    }

    // =========================================================
    // THEME LISTENER
    // =========================================================

    @Override
    public void themeChanged(
            Theme newTheme) {

        applyTheme(newTheme);
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );
    }
}
