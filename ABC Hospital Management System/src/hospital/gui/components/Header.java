package hospital.gui.components;

import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.security.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Header extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JLabel pageTitleLabel;
    private JLabel breadcrumbLabel;

    private JLabel usernameLabel;
    private JLabel roleLabel;

    private JPanel titlePanel;
    private JPanel userPanel;

    private AppIcon notificationIcon;
    private AppIcon userIcon;

    private JTextField searchField;

    public Header() {

        setLayout(new BorderLayout());

        setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );

        createLeftSection();
        createRightSection();

        ThemeManager.addThemeChangeListener(this);

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // LEFT SECTION
    // =========================================================

    private void createLeftSection() {

        titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        // Page title
        pageTitleLabel =
                new JLabel("Dashboard");

        pageTitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        pageTitleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        titlePanel.add(
                pageTitleLabel
        );

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        // Breadcrumb
        breadcrumbLabel =
                new JLabel("Home");

        breadcrumbLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        breadcrumbLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        titlePanel.add(
                breadcrumbLabel
        );

        add(
                titlePanel,
                BorderLayout.WEST
        );
    }

    // =========================================================
    // RIGHT SECTION
    // =========================================================

    private void createRightSection() {

        userPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                0
                        )
                );

        userPanel.setOpaque(false);

        // Search
        JPanel searchPanel =
                createSearchPanel();

        userPanel.add(
                searchPanel
        );

        // Notification
        notificationIcon =
                new AppIcon(
                        AppIcon.IconType.NOTIFICATION
                );

        notificationIcon.setToolTipText(
                "Notifications"
        );

        userPanel.add(
                notificationIcon
        );

        // User icon
        userIcon =
                new AppIcon(
                        AppIcon.IconType.USER
                );

        // User information
        JPanel userInfo =
                new JPanel();

        userInfo.setOpaque(false);

        userInfo.setLayout(
                new BoxLayout(
                        userInfo,
                        BoxLayout.Y_AXIS
                )
        );

        usernameLabel =
                new JLabel(
                        getUsername()
                );

        usernameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        usernameLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        roleLabel =
                new JLabel(
                        getRole()
                );

        roleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        roleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        userInfo.add(
                usernameLabel
        );

        userInfo.add(
                Box.createVerticalStrut(2)
        );

        userInfo.add(
                roleLabel
        );

        userPanel.add(
                userIcon
        );

        userPanel.add(
                userInfo
        );

        add(
                userPanel,
                BorderLayout.EAST
        );
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private JPanel createSearchPanel() {

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        searchPanel.setPreferredSize(
                new Dimension(
                        220,
                        38
                )
        );

        searchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        215,
                                        219,
                                        225
                                )
                        ),
                        new EmptyBorder(
                                0,
                                10,
                                0,
                                10
                        )
                )
        );

        AppIcon searchIcon =
                new AppIcon(
                        AppIcon.IconType.SEARCH
                );

        searchIcon.setPreferredSize(
                new Dimension(
                        20,
                        20
                )
        );

        searchField =
                new JTextField();

        searchField.setBorder(
                BorderFactory.createEmptyBorder()
        );

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setOpaque(false);

        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Search..."
        );

        searchPanel.add(
                searchIcon,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        return searchPanel;
    }

    // =========================================================
    // PAGE INFORMATION
    // =========================================================

    public void setPageTitle(
            String title
    ) {

        if (title == null ||
                title.trim().isEmpty()) {

            pageTitleLabel.setText(
                    "Dashboard"
            );

            return;
        }

        pageTitleLabel.setText(
                title
        );
    }

    public void setBreadcrumb(
            String breadcrumb
    ) {

        if (breadcrumb == null ||
                breadcrumb.trim().isEmpty()) {

            breadcrumbLabel.setText(
                    "Home"
            );

            return;
        }

        breadcrumbLabel.setText(
                breadcrumb
        );
    }

    public void setPage(
            String title,
            String breadcrumb
    ) {

        setPageTitle(title);
        setBreadcrumb(breadcrumb);
    }

    // =========================================================
    // SESSION INFORMATION
    // =========================================================

    private String getUsername() {

        String username =
                Session.getUsername();

        if (username == null ||
                username.trim().isEmpty()) {

            return "User";
        }

        return username;
    }

    private String getRole() {

        String role =
                Session.getRole();

        if (role == null ||
                role.trim().isEmpty()) {

            return "User";
        }

        return formatRole(role);
    }

    private String formatRole(
            String role
    ) {

        String normalized =
                role.trim()
                        .toLowerCase();

        switch (normalized) {

            case "admin":
            case "administrator":
                return "Administrator";

            case "doctor":
                return "Doctor";

            case "nurse":
                return "Nurse";

            case "receptionist":
                return "Receptionist";

            case "pharmacist":
                return "Pharmacist";

            case "accountant":
                return "Accountant";

            default:
                return capitalizeWords(role);
        }
    }

    private String capitalizeWords(
            String text
    ) {

        String[] words =
                text.trim().split("\\s+");

        StringBuilder result =
                new StringBuilder();

        for (String word : words) {

            if (word.isEmpty()) {
                continue;
            }

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );

            if (word.length() > 1) {
                result.append(
                        word.substring(1)
                                .toLowerCase()
                );
            }
        }

        return result.toString();
    }

    // =========================================================
    // REFRESH SESSION INFORMATION
    // =========================================================

    public void refreshUserInformation() {

        usernameLabel.setText(
                getUsername()
        );

        roleLabel.setText(
                getRole()
        );

        revalidate();
        repaint();
    }

    // =========================================================
    // SEARCH ACCESS
    // =========================================================

    public JTextField getSearchField() {

        return searchField;
    }

    public String getSearchText() {

        return searchField
                .getText()
                .trim();
    }

    public void clearSearch() {

        searchField.setText("");
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
                theme.getSurfaceColor()
        );

        pageTitleLabel.setForeground(
                theme.getTextColor()
        );

        breadcrumbLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        usernameLabel.setForeground(
                theme.getTextColor()
        );

        roleLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        notificationIcon.setIconColor(
                theme.getSecondaryTextColor()
        );

        userIcon.setIconColor(
                theme.getSecondaryTextColor()
        );

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

        if (notificationIcon != null) {
            notificationIcon.dispose();
        }

        if (userIcon != null) {
            userIcon.dispose();
        }
    }
}