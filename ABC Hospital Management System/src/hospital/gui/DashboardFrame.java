package hospital.gui;

import hospital.gui.components.Header;
import hospital.gui.components.Sidebar;
import hospital.gui.components.StatCard;
import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.database.DatabaseConnection;
import hospital.models.DashboardStats;
import hospital.security.Session;
import hospital.services.DashboardService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class DashboardFrame extends JFrame
        implements ThemeManager.ThemeChangeListener {

    private JPanel rootPanel;
    private JPanel contentPanel;
    private JPanel mainPanel;

    private Header header;
    private Sidebar sidebar;

    private DashboardService dashboardService;

    private StatCard patientsCard;
    private StatCard appointmentsCard;
    private StatCard doctorsCard;
    private StatCard financeCard;
    private JLabel clinicalStatusLabel;

    public DashboardFrame() {

        setTitle("LifeSaver Hospital Management System");

        setSize(1440, 900);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setMinimumSize(
                new Dimension(1180, 760)
        );

        dashboardService =
                new DashboardService();

        initUI();

        ThemeManager.addThemeChangeListener(
                this
        );
    }

    private void initUI() {

        rootPanel =
                new JPanel(
                        new BorderLayout()
                );

        createSidebar();

        createMainArea();

        setContentPane(rootPanel);

        applyTheme(
                ThemeManager.getCurrentTheme()
        );

        showDashboard();
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private void createSidebar() {

        sidebar = new Sidebar();

        sidebar.getDashboardButton()
                .addActionListener(
                        e -> showDashboard()
                );

        sidebar.getPatientsButton()
                .addActionListener(
                        e -> showPatients()
                );

        // =====================================================
        // STAFF MANAGEMENT
        // =====================================================
        // The Sidebar button is labelled "Staffs".
        // It opens the unified Staff Management hub.
        // =====================================================

        sidebar.getDoctorsButton()
                .addActionListener(
                        e -> showStaffManagement()
                );

        sidebar.getAppointmentsButton()
                .addActionListener(
                        e -> showAppointments()
                );

        sidebar.getRecordsButton()
                .addActionListener(
                        e -> showOperations("Medical Records")
                );

        sidebar.getPharmacyButton()
                .addActionListener(
                        e -> showOperations("Pharmacy")
                );

        sidebar.getBillingButton()
                .addActionListener(
                        e -> showOperations("Billing")
                );

        sidebar.getReportsButton()
                .addActionListener(
                        e -> showOperations("Overview")
                );

        sidebar.getSettingsButton()
                .addActionListener(
                        e -> showSettings()
                );

        sidebar.getLogoutButton()
                .addActionListener(
                        e -> logout()
                );

        rootPanel.add(
                sidebar,
                BorderLayout.WEST
        );
    }

    // =========================================================
    // MAIN AREA
    // =========================================================

    private void createMainArea() {

        mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        header = new Header();

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        rootPanel.add(
                mainPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    private void showDashboard() {

        sidebar.setActiveButton(
                sidebar.getDashboardButton()
        );

        header.setPage(
                "Dashboard",
                "Home"
        );

        contentPanel.removeAll();

        JPanel dashboard =
                new JPanel();

        dashboard.setLayout(
                new BoxLayout(
                        dashboard,
                        BoxLayout.Y_AXIS
                )
        );

        dashboard.setOpaque(false);

        // -----------------------------------------------------
        // WELCOME
        // -----------------------------------------------------

        JLabel welcome =
                new JLabel(
                        "Welcome back, "
                                + getUserDisplayName()
                                + "!"
                );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        dashboard.add(welcome);

        dashboard.add(
                Box.createVerticalStrut(8)
        );

        JLabel description =
                new JLabel(
                        "Clinical operations overview  •  Live workspace for LifeSaver Hospital"
                );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        dashboard.add(description);

        dashboard.add(
                Box.createVerticalStrut(30)
        );

        // -----------------------------------------------------
        // STAT CARDS
        // -----------------------------------------------------

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        patientsCard =
                new StatCard(
                        "Patients",
                        "Loading...",
                        "Registered patients",
                        AppIcon.IconType.PATIENTS
                );

        appointmentsCard =
                new StatCard(
                        "Appointments",
                        "Loading...",
                        "Today's appointments",
                        AppIcon.IconType.APPOINTMENTS
                );

        doctorsCard =
                new StatCard(
                        "Doctors",
                        "Loading...",
                        "Active doctors",
                        AppIcon.IconType.DOCTORS
                );

        financeCard =
                new StatCard(
                        "Pending Bills",
                        "Loading...",
                        "Outstanding balance",
                        AppIcon.IconType.FINANCE
                );

        statsPanel.add(
                patientsCard
        );

        statsPanel.add(
                appointmentsCard
        );

        statsPanel.add(
                doctorsCard
        );

        statsPanel.add(
                financeCard
        );

        JPanel statusStrip = new JPanel(new BorderLayout(14, 0));
        statusStrip.setOpaque(true);
        statusStrip.setBackground(new Color(232, 247, 247));
        statusStrip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(177, 224, 224)),
                new EmptyBorder(12, 16, 12, 16)
        ));
        JLabel statusTitle = new JLabel("LIFESAVER CLINICAL NETWORK");
        statusTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusTitle.setForeground(new Color(8, 91, 105));
        clinicalStatusLabel = new JLabel("Checking database connection…");
        clinicalStatusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clinicalStatusLabel.setForeground(new Color(55, 92, 98));
        statusStrip.add(statusTitle, BorderLayout.WEST);
        statusStrip.add(clinicalStatusLabel, BorderLayout.CENTER);
        dashboard.add(statusStrip);
        dashboard.add(Box.createVerticalStrut(18));

        dashboard.add(
                statsPanel
        );

        dashboard.add(
                Box.createVerticalStrut(25)
        );

        // -----------------------------------------------------
        // QUICK ACTIONS
        // -----------------------------------------------------

        JLabel quickTitle =
                new JLabel(
                        "Quick Actions"
                );

        quickTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        dashboard.add(
                quickTitle
        );

        dashboard.add(
                Box.createVerticalStrut(12)
        );

        JPanel quickActions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        quickActions.setOpaque(false);

        JButton registerPatient =
                new JButton(
                        "+ Register Patient"
                );

        JButton viewPatients =
                new JButton(
                        "View Patients"
                );

        JButton appointments =
                new JButton(
                        "Appointments"
                );

        styleQuickButton(
                registerPatient
        );

        styleQuickButton(
                viewPatients
        );

        styleQuickButton(
                appointments
        );

        registerPatient.addActionListener(
                e -> showPatients()
        );

        viewPatients.addActionListener(
                e -> showPatients()
        );

        appointments.addActionListener(e -> showAppointments());

        quickActions.add(
                registerPatient
        );

        quickActions.add(
                viewPatients
        );

        quickActions.add(
                appointments
        );

        dashboard.add(
                quickActions
        );

        dashboard.add(
                Box.createVerticalStrut(30)
        );

        // -----------------------------------------------------
        // RECENT ACTIVITY
        // -----------------------------------------------------

        JLabel activityTitle =
                new JLabel(
                        "Recent Activity"
                );

        activityTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        activityTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        dashboard.add(
                activityTitle
        );

        dashboard.add(
                Box.createVerticalStrut(10)
        );

        JPanel activityCard =
                new JPanel(
                        new BorderLayout()
                );

        activityCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        224,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        activityCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        150
                )
        );

        JLabel emptyActivity =
                new JLabel(
                        "No recent activity to display."
                );

        emptyActivity.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        activityCard.add(
                emptyActivity,
                BorderLayout.CENTER
        );

        dashboard.add(
                activityCard
        );

        contentPanel.add(
                dashboard,
                BorderLayout.CENTER
        );

        refreshContent();

        loadDashboardData();
    }

    // =========================================================
    // STAFF MANAGEMENT
    // =========================================================

    private void showStaffManagement() {

        sidebar.setActiveButton(
                sidebar.getDoctorsButton()
        );

        header.setPage(
                "Staff Management",
                "Home  /  Staff Management"
        );

        contentPanel.removeAll();

        StaffManagementPanel staffManagementPanel =
                new StaffManagementPanel();

        contentPanel.add(
                staffManagementPanel,
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // QUICK BUTTON STYLE
    // =========================================================

    private void styleQuickButton(
            JButton button
    ) {

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );
    }

    // =========================================================
    // LOAD DASHBOARD DATA
    // =========================================================

    private void loadDashboardData() {

        patientsCard.setValue(
                "Loading..."
        );

        appointmentsCard.setValue(
                "Loading..."
        );

        doctorsCard.setValue(
                "Loading..."
        );

        financeCard.setValue(
                "Loading..."
        );

        SwingWorker<DashboardStats, Void>
                worker =
                new SwingWorker<>() {

                    @Override
                    protected DashboardStats
                    doInBackground() {
                        boolean available = DatabaseConnection.isAvailable();
                        SwingUtilities.invokeLater(
                                () -> updateClinicalStatus(available)
                        );
                        return dashboardService
                                .getDashboardStats();
                    }

                    @Override
                    protected void done() {

                        try {

                            DashboardStats stats =
                                    get();

                            patientsCard.setValue(
                                    String.valueOf(
                                            stats.getPatientCount()
                                    )
                            );

                            appointmentsCard.setValue(
                                    String.valueOf(
                                            stats.getTodayAppointmentCount()
                                    )
                            );

                            doctorsCard.setValue(
                                    String.valueOf(
                                            stats.getDoctorCount()
                                    )
                            );

                            financeCard.setValue(
                                    formatCurrency(
                                            stats.getPendingBillsAmount()
                                    )
                            );

                        } catch (Exception e) {

                            e.printStackTrace();

                            patientsCard.setValue("-");
                            appointmentsCard.setValue("-");
                            doctorsCard.setValue("-");
                            financeCard.setValue("-");
                        }
                    }
                };

        worker.execute();
    }

    private void updateClinicalStatus(boolean available) {
        if (clinicalStatusLabel == null) {
            return;
        }
        String role = Session.getCurrentUser() == null
                ? "Unknown role"
                : Session.getCurrentUser().getRole();
        clinicalStatusLabel.setText(available
                ? "Database online  •  Role: " + role
                : "Database unavailable  •  Check SQL Server and properties.properties");
        clinicalStatusLabel.setForeground(available
                ? new Color(32, 118, 82)
                : new Color(176, 74, 62));
    }

    // =========================================================
    // CURRENCY
    // =========================================================

    private String formatCurrency(
            double amount
    ) {

        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(
                        Locale.forLanguageTag(
                                "en-NG"
                        )
                );

        return formatter.format(
                amount
        );
    }

    // =========================================================
    // PATIENTS
    // =========================================================

    private void showAppointments() {
        sidebar.setActiveButton(sidebar.getAppointmentsButton());
        header.setPage("Appointments", "Home  / Appointments");
        contentPanel.removeAll();
        contentPanel.add(new AppointmentPanel(), BorderLayout.CENTER);
        refreshContent();
    }

    private void showSettings() {
        sidebar.setActiveButton(sidebar.getSettingsButton());
        header.setPage("Settings", "Home  / Settings");
        contentPanel.removeAll();
        contentPanel.add(new SettingsPanel(), BorderLayout.CENTER);
        refreshContent();
    }

    private void showOperations(String tab) {
        if ("Pharmacy".equalsIgnoreCase(tab)) sidebar.setActiveButton(sidebar.getPharmacyButton());
        else if ("Billing".equalsIgnoreCase(tab)) sidebar.setActiveButton(sidebar.getBillingButton());
        else if ("Overview".equalsIgnoreCase(tab)) sidebar.setActiveButton(sidebar.getReportsButton());
        else sidebar.setActiveButton(sidebar.getRecordsButton());
        header.setPage(tab == null ? "Operations" : tab, "Home  / " + (tab == null ? "Operations" : tab));
        contentPanel.removeAll();
        contentPanel.add(new HospitalOperationsPanel(tab), BorderLayout.CENTER);
        refreshContent();
    }

    private void showPatients() {

        sidebar.setActiveButton(
                sidebar.getPatientsButton()
        );

        header.setPage(
                "Patients",
                "Home  /  Patients"
        );

        contentPanel.removeAll();

        PatientPanel patientPanel =
                new PatientPanel();

        contentPanel.add(
                patientPanel,
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // COMING SOON
    // =========================================================

    private void showComingSoon(
            String section
    ) {

        contentPanel.removeAll();

        header.setPage(
                section,
                "Home  /  " + section
        );

        JLabel title =
                new JLabel(
                        section + " module"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        JLabel message =
                new JLabel(
                        "This section will be connected "
                                + "to the existing system next."
                );

        message.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        40,
                        40,
                        40,
                        40
                )
        );

        panel.setOpaque(false);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        message.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(message);

        contentPanel.add(
                panel,
                BorderLayout.CENTER
        );

        refreshContent();
    }

    // =========================================================
    // USER DISPLAY NAME
    // =========================================================

    private String getUserDisplayName() {

        String username =
                Session.getUsername();

        if (username == null ||
                username.trim().isEmpty()) {

            return "User";
        }

        return username;
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            Session.end();

            new LoginFrame()
                    .setVisible(true);

            dispose();
        }
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

        rootPanel.setBackground(
                theme.getBackgroundColor()
        );

        mainPanel.setBackground(
                theme.getBackgroundColor()
        );

        contentPanel.setBackground(
                theme.getBackgroundColor()
        );

        repaint();
    }

    @Override
    public void themeChanged(
            Theme newTheme
    ) {

        applyTheme(
                newTheme
        );
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        if (header != null) {
            header.dispose();
        }

        if (sidebar != null) {
            sidebar.dispose();
        }

        super.dispose();
    }
}
