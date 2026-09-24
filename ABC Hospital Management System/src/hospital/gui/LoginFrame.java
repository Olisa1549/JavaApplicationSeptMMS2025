package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppLabel;
import hospital.gui.components.AppTextField;
import hospital.gui.components.LifeSaverBrandPanel;
import hospital.gui.components.ModernPasswordField;
import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.User;
import hospital.security.Session;
import hospital.services.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame
        implements ThemeManager.ThemeChangeListener {

    private AppTextField usernameField;
    private ModernPasswordField passwordField;
    private AppButton loginButton;

    private AppLabel statusLabel;

    private JPanel rootPanel;
    private JPanel brandingPanel;
    private JPanel loginPanel;

    private final UserService userService;

    public LoginFrame() {

        userService = new UserService();

        setTitle("LifeSaver Hospital Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();

        ThemeManager.addThemeChangeListener(this);
    }

    private void initUI() {

        rootPanel = new JPanel(new GridLayout(1, 2));

        createBrandingPanel();
        createLoginPanel();

        rootPanel.add(brandingPanel);
        rootPanel.add(loginPanel);

        setContentPane(rootPanel);

        applyTheme(ThemeManager.getCurrentTheme());
    }

    // =========================================================
    // LEFT BRANDING PANEL
    // =========================================================

    private void createBrandingPanel() {

        brandingPanel = new LifeSaverBrandPanel();

        brandingPanel.setLayout(
                new BoxLayout(
                        brandingPanel,
                        BoxLayout.Y_AXIS
                )
        );

        brandingPanel.setBorder(
                new EmptyBorder(
                        55,
                        60,
                        40,
                        55
                )
        );

        // Hospital icon
        AppIcon medicalIcon =
                new AppIcon(AppIcon.IconType.PLUS);

        medicalIcon.setPreferredSize(
                new Dimension(40, 40)
        );

        medicalIcon.setMinimumSize(
                new Dimension(40, 40)
        );

        medicalIcon.setMaximumSize(
                new Dimension(40, 40)
        );

        medicalIcon.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(medicalIcon);

        brandingPanel.add(
                Box.createVerticalStrut(18)
        );

        // Hospital name
        JLabel hospitalName =
                new JLabel("LIFESAVER HOSPITAL");

        hospitalName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        36
                )
        );

        hospitalName.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(hospitalName);

        brandingPanel.add(
                Box.createVerticalStrut(5)
        );

        // System name
        JLabel systemName =
                new JLabel("Management System");

        systemName.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        19
                )
        );

        systemName.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(systemName);

        brandingPanel.add(
                Box.createVerticalStrut(45)
        );

        // Tagline
        JLabel tagline =
                new JLabel(
                        "<html>Healthcare management<br>"
                        + "made simple.</html>"
                );

        tagline.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        tagline.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(tagline);

        brandingPanel.add(
                Box.createVerticalStrut(35)
        );

        // Features
        addFeature(
                "Secure",
                "Your hospital data stays protected."
        );

        addFeature(
                "Efficient",
                "Manage daily hospital operations with ease."
        );

        addFeature(
                "Connected",
                "Everything your hospital needs in one place."
        );

        brandingPanel.add(
                Box.createVerticalGlue()
        );

        // Footer
        JLabel footer =
                new JLabel(
                        "LifeSaver Hospital © 2026"
                );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        footer.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(footer);
    }

    // =========================================================
    // BRANDING FEATURES
    // =========================================================

    private void addFeature(
            String title,
            String description
    ) {

        JPanel featurePanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        featurePanel.setOpaque(false);

        // Check icon
        AppIcon checkIcon =
                new AppIcon(
                        AppIcon.IconType.PLUS
                );

        checkIcon.setPreferredSize(
                new Dimension(22, 22)
        );

        checkIcon.setMinimumSize(
                new Dimension(22, 22)
        );

        checkIcon.setMaximumSize(
                new Dimension(22, 22)
        );

        // Feature text
        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                        + description
                        + "</html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(descriptionLabel);

        featurePanel.add(
                checkIcon,
                BorderLayout.WEST
        );

        featurePanel.add(
                textPanel,
                BorderLayout.CENTER
        );

        featurePanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        55
                )
        );

        featurePanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brandingPanel.add(featurePanel);

        brandingPanel.add(
                Box.createVerticalStrut(12)
        );
    }

    // =========================================================
    // LOGIN PANEL
    // =========================================================

    private void createLoginPanel() {

        loginPanel = new JPanel();

        loginPanel.setLayout(
                new GridBagLayout()
        );

        loginPanel.setBackground(
                Color.WHITE
        );

        JPanel form =
                new JPanel();

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS
                )
        );

        form.setPreferredSize(
                new Dimension(
                        410,
                        500
                )
        );

        form.setOpaque(false);

        // Welcome
        JLabel eyebrow =
                new JLabel("LIFESAVER  /  CLINICAL PLATFORM");

        eyebrow.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
        );

        eyebrow.setForeground(new Color(9, 113, 128));
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(eyebrow);
        form.add(Box.createVerticalStrut(12));

        JLabel welcome =
                new JLabel(
                        "Welcome back."
                );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        welcome.setForeground(
                new Color(
                        35,
                        40,
                        48
                )
        );

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(welcome);

        form.add(
                Box.createVerticalStrut(8)
        );

        // Instruction
        AppLabel instruction =
                new AppLabel(
                        "Sign in securely to your hospital workspace.",
                        AppLabel.LabelType.SUBTITLE
                );

        instruction.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(instruction);

        form.add(
                Box.createVerticalStrut(40)
        );

        // Username label
        AppLabel usernameLabel =
                new AppLabel(
                        "Username",
                        AppLabel.LabelType.LABEL
                );

        usernameLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(usernameLabel);

        form.add(
                Box.createVerticalStrut(8)
        );

        // Username field
        usernameField =
                new AppTextField();

        usernameField.setPreferredSize(
                new Dimension(
                        410,
                        48
                )
        );

        usernameField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        usernameField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(usernameField);

        form.add(
                Box.createVerticalStrut(22)
        );

        // Password label
        AppLabel passwordLabel =
                new AppLabel(
                        "Password",
                        AppLabel.LabelType.LABEL
                );

        passwordLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(passwordLabel);

        form.add(
                Box.createVerticalStrut(8)
        );

        // Password field
        passwordField =
                new ModernPasswordField();

        passwordField.setPreferredSize(
                new Dimension(
                        410,
                        48
                )
        );

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        passwordField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        form.add(passwordField);

        form.add(
                Box.createVerticalStrut(28)
        );

        // Login button
        loginButton =
                new AppButton(
                        "SIGN IN"
                );

        loginButton.setPreferredSize(
                new Dimension(
                        180,
                        46
                )
        );

        loginButton.setMinimumSize(
                new Dimension(
                        180,
                        46
                )
        );

        loginButton.setMaximumSize(
                new Dimension(
                        180,
                        46
                )
        );

        loginButton.addActionListener(
                e -> login()
        );

        JPanel buttonWrapper =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                0
                        )
                );

        buttonWrapper.setOpaque(false);

        buttonWrapper.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        buttonWrapper.add(
                loginButton
        );

        form.add(buttonWrapper);

        form.add(
                Box.createVerticalStrut(18)
        );

        // Status
        statusLabel =
                new AppLabel(
                        "",
                        AppLabel.LabelType.MUTED
                );

        statusLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        form.add(statusLabel);

        form.add(
                Box.createVerticalGlue()
        );

        // Security indicator
        JPanel securePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                7,
                                0
                        )
                );

        securePanel.setOpaque(false);

        securePanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        AppIcon secureIcon =
                new AppIcon(
                        AppIcon.IconType.SETTINGS
                );

        secureIcon.setIconColor(
                ThemeManager
                        .getCurrentTheme()
                        .getSecondaryTextColor()
        );

        AppLabel secureLabel =
                new AppLabel(
                        "Secure hospital access",
                        AppLabel.LabelType.MUTED
                );

        securePanel.add(
                secureIcon
        );

        securePanel.add(
                secureLabel
        );

        form.add(securePanel);

        // Center form
        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        loginPanel.add(
                form,
                gbc
        );

        // Enter key triggers login
        getRootPane().setDefaultButton(
                loginButton
        );
    }

    // =========================================================
    // LOGIN LOGIC
    // =========================================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        usernameField.clearValidation();
        passwordField.clearValidation();

        statusLabel.setText("");

        // Username validation
        if (username.isEmpty()) {

            usernameField.setValidState(
                    false
            );

            statusLabel.setText(
                    "Username is required."
            );

            usernameField.requestFocus();

            return;
        }

        // Password validation
        if (password.isEmpty()) {

            passwordField.setValidState(
                    false
            );

            statusLabel.setText(
                    "Password is required."
            );

            passwordField.requestFocusInField();

            return;
        }

        // Disable button while authenticating
        loginButton.setEnabled(false);

        loginButton.setText(
                "SIGNING IN..."
        );

        // Existing authentication service
        User user =
                userService.authenticate(
                        username,
                        password
                );

        // Authentication failed
        if (user == null) {

            usernameField.setValidState(
                    false
            );

            passwordField.setValidState(
                    false
            );

            statusLabel.setText(
                    "Invalid username or password."
            );

            passwordField.requestFocusInField();

            loginButton.setText(
                    "SIGN IN"
            );

            loginButton.setEnabled(true);

            return;
        }

        // Authentication successful
        usernameField.setValidState(
                true
        );

        passwordField.setValidState(
                true
        );

        statusLabel.setText(
                "✓ Login successful"
        );

        // Start existing session
        Session.start(user);

        // Open dashboard
        SwingUtilities.invokeLater(
                () -> {

                    DashboardFrame dashboard =
                            new DashboardFrame();

                    dashboard.setVisible(true);

                    dispose();
                }
        );
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

        brandingPanel.setBackground(
                theme.getPrimaryColor()
        );

        loginPanel.setBackground(theme.getCardColor());
    }

    @Override
    public void themeChanged(
            Theme newTheme
    ) {

        applyTheme(newTheme);

        repaint();
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    @Override
    public void dispose() {

        ThemeManager
                .removeThemeChangeListener(
                        this
                );

        super.dispose();
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    new LoginFrame()
                            .setVisible(true);
                }
        );
    }
}
