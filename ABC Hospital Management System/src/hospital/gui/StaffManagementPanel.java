package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class StaffManagementPanel extends JPanel {

    private final ThemeManager.ThemeChangeListener themeListener;

    private JPanel contentPanel;

    private JLabel titleLabel;
    private JLabel subtitleLabel;

    private AppButton allStaffButton;
    private AppButton doctorsButton;
    private AppButton nursesButton;
    private AppButton pharmacistsButton;
    private AppButton laboratoryButton;
    private AppButton assignmentsButton;
    private AppButton accountsButton;

    public StaffManagementPanel() {

        setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        themeListener = this::applyTheme;

        ThemeManager.addThemeChangeListener(
                themeListener
        );

        initUI();

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // INITIALIZE UI
    // =========================================================
    private void initUI() {

        // =====================================================
        // HEADER
        // =====================================================
        JPanel headerPanel = new JPanel();

        headerPanel.setOpaque(false);

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        titleLabel = new JLabel(
                "Staff Management"
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        subtitleLabel = new JLabel(
                "Manage hospital staff, assignments and staff accounts."
        );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(6)
        );

        headerPanel.add(
                subtitleLabel
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN CONTENT
        // =====================================================
        JPanel mainPanel = new JPanel(
                new BorderLayout(
                        15,
                        15
                )
        );

        mainPanel.setOpaque(false);

        // =====================================================
        // NAVIGATION
        // =====================================================
        JPanel navigationPanel = new JPanel(
                new GridLayout(
                        7,
                        1,
                        0,
                        8
                )
        );

        navigationPanel.setOpaque(false);

        navigationPanel.setPreferredSize(
                new Dimension(
                        210,
                        0
                )
        );

        allStaffButton = new AppButton(
                "All Staff"
        );

        doctorsButton = new AppButton(
                "Doctors"
        );

        nursesButton = new AppButton(
                "Nurses"
        );

        pharmacistsButton = new AppButton(
                "Pharmacists"
        );

        laboratoryButton = new AppButton(
                "Laboratory Technicians"
        );

        assignmentsButton = new AppButton(
                "Nurse Assignments"
        );

        accountsButton = new AppButton(
                "Staff Accounts"
        );

        navigationPanel.add(
                allStaffButton
        );

        navigationPanel.add(
                doctorsButton
        );

        navigationPanel.add(
                nursesButton
        );

        navigationPanel.add(
                pharmacistsButton
        );

        navigationPanel.add(
                laboratoryButton
        );

        navigationPanel.add(
                assignmentsButton
        );

        navigationPanel.add(
                accountsButton
        );

        mainPanel.add(
                navigationPanel,
                BorderLayout.WEST
        );

        // =====================================================
        // CONTENT
        // =====================================================
        contentPanel = new JPanel(
                new BorderLayout()
        );

        contentPanel.setOpaque(false);

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        allStaffButton.addActionListener(
                e -> showAllStaff()
        );

        doctorsButton.addActionListener(
                e -> showDoctors()
        );

        nursesButton.addActionListener(
                e -> showNurses()
        );

        pharmacistsButton.addActionListener(
                e -> showPharmacists()
        );

        // =====================================================
        // LABORATORY TECHNICIANS
        // =====================================================
        laboratoryButton.addActionListener(
                e -> showLaboratoryTechnicians()
        );

        // =====================================================
        // NURSE ASSIGNMENTS
        // =====================================================
        assignmentsButton.addActionListener(
                e -> showNurseAssignments()
        );

        // =====================================================
        // STAFF ACCOUNTS
        // =====================================================
        accountsButton.addActionListener(
                e -> showStaffAccounts()
        );

        // =====================================================
        // DEFAULT SCREEN
        // =====================================================
        showAllStaff();
    }

    // =========================================================
    // ALL STAFF
    // =========================================================
    private void showAllStaff() {

        clearContent();

        contentPanel.add(
                new AllStaffPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // DOCTORS
    // =========================================================
    private void showDoctors() {

        clearContent();

        contentPanel.add(
                new DoctorPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // NURSES
    // =========================================================
    private void showNurses() {

        clearContent();

        contentPanel.add(
                new NursePanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // PHARMACISTS
    // =========================================================
    private void showPharmacists() {

        clearContent();

        contentPanel.add(
                new PharmacistPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // LABORATORY TECHNICIANS
    // =========================================================
    private void showLaboratoryTechnicians() {

        clearContent();

        contentPanel.add(
                new LaboratoryTechnicianPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // NURSE ASSIGNMENTS
    // =========================================================
    private void showNurseAssignments() {

        clearContent();

        contentPanel.add(
                new NurseAssignmentPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // STAFF ACCOUNTS
    // =========================================================
    private void showStaffAccounts() {

        clearContent();

        contentPanel.add(
                new UserManagementPanel(),
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // PLACEHOLDER
    // =========================================================
    private JPanel createPlaceholderPanel(
            String title,
            String description
    ) {

        JPanel panel = new JPanel(
                new GridBagLayout()
        );

        panel.setOpaque(false);

        JPanel inner = new JPanel();

        inner.setOpaque(false);

        inner.setLayout(
                new BoxLayout(
                        inner,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel = new JLabel(
                title
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel descriptionLabel = new JLabel(
                description
        );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        descriptionLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        inner.add(
                titleLabel
        );

        inner.add(
                Box.createVerticalStrut(10)
        );

        inner.add(
                descriptionLabel
        );

        panel.add(
                inner
        );

        return panel;
    }

    // =========================================================
    // CLEAR CONTENT
    // =========================================================
    private void clearContent() {

    if (contentPanel == null) {
        return;
    }

    for (Component component
            : contentPanel.getComponents()) {

        if (component instanceof DoctorPanel) {

            ((DoctorPanel) component).dispose();

        } else if (component instanceof NursePanel) {

            ((NursePanel) component).dispose();

        } else if (component instanceof PharmacistPanel) {

            ((PharmacistPanel) component).dispose();
        }
    }

    contentPanel.removeAll();
}

    // =========================================================
    // REFRESH CONTENT
    // =========================================================
    private void refreshContent() {

        contentPanel.revalidate();

        contentPanel.repaint();
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
                theme.getBackgroundColor()
        );

        if (titleLabel != null) {

            titleLabel.setForeground(
                    theme.getTextColor()
            );
        }

        if (subtitleLabel != null) {

            subtitleLabel.setForeground(
                    theme.getSecondaryTextColor()
            );
        }

        repaint();
    }

    // =========================================================
    // CLEANUP
    // =========================================================
    public void dispose() {

        clearContent();

        ThemeManager.removeThemeChangeListener(
                themeListener
        );
    }
}