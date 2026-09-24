package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppComboBox;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.User;
import hospital.services.UserManagementService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Method;

public class UserDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private AppTextField usernameField;
    private AppTextField staffField;

    private JPasswordField passwordField;
    private AppComboBox<String> roleComboBox;

    private JCheckBox activeCheckBox;

    private AppButton saveButton;
    private AppButton cancelButton;

    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel statusLabel;

    private final UserManagementService userService;
    private final User existingUser;

    private boolean saved = false;

    public UserDialog(
            Window owner,
            User user
    ) {

        super(
                owner,
                user == null
                        ? "Add Staff Account"
                        : "Edit Staff Account",
                Dialog.ModalityType.APPLICATION_MODAL
        );

        userService =
                new UserManagementService();

        existingUser = user;

        setSize(
                620,
                520
        );

        setLocationRelativeTo(owner);

        setResizable(false);

        initUI();

        ThemeManager.addThemeChangeListener(
                this
        );

        if (existingUser != null) {
            populateUser();
        }
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        15,
                        25
                )
        );

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setOpaque(false);

        titleLabel =
                new JLabel(
                        existingUser == null
                                ? "Add Staff Account"
                                : "Edit Staff Account"
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        subtitleLabel =
                new JLabel(
                        existingUser == null
                                ? "Create login credentials for a hospital staff member."
                                : "Update the staff account information."
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        header.add(titleLabel);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitleLabel);

        root.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // FORM
        // ---------------------------------------------------------

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        5,
                        0
                )
        );

        form.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        6,
                        6,
                        6
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        int row = 0;

        usernameField =
                new AppTextField();

        usernameField.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addField(
                form,
                gbc,
                row++,
                "Username",
                usernameField
        );

        passwordField =
                new JPasswordField();

        passwordField.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        addField(
                form,
                gbc,
                row++,
                existingUser == null
                        ? "Password"
                        : "New Password",
                passwordField
        );

        roleComboBox =
                new AppComboBox<>();

        roleComboBox.addItem("ADMIN");
        roleComboBox.addItem("RECEPTIONIST");
        roleComboBox.addItem("DOCTOR");
        roleComboBox.addItem("NURSE");
        roleComboBox.addItem("PHARMACIST");
        roleComboBox.addItem("LABORATORY_TECHNICIAN");
        roleComboBox.addItem("STAFF");

        roleComboBox.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addField(
                form,
                gbc,
                row++,
                "Role",
                roleComboBox
        );

        staffField =
                new AppTextField();

        staffField.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addField(
                form,
                gbc,
                row++,
                "Staff ID",
                staffField
        );

        activeCheckBox =
                new JCheckBox(
                        "Account is active"
                );

        activeCheckBox.setSelected(true);

        activeCheckBox.setOpaque(false);

        activeCheckBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.weightx = 1;

        form.add(
                activeCheckBox,
                gbc
        );

        root.add(
                form,
                BorderLayout.CENTER
        );

        // ---------------------------------------------------------
        // FOOTER
        // ---------------------------------------------------------

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setOpaque(false);

        statusLabel =
                new JLabel("");

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        footer.add(
                statusLabel,
                BorderLayout.WEST
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttons.setOpaque(false);

        saveButton =
                new AppButton(
                        existingUser == null
                                ? "Create Account"
                                : "Save Changes"
                );

        cancelButton =
                new AppButton(
                        "Cancel"
                );

        buttons.add(saveButton);
        buttons.add(cancelButton);

        footer.add(
                buttons,
                BorderLayout.EAST
        );

        root.add(
                footer,
                BorderLayout.SOUTH
        );

        setContentPane(root);

        // ---------------------------------------------------------
        // ACTIONS
        // ---------------------------------------------------------

        saveButton.addActionListener(
                e -> saveUser()
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // FIELD HELPER
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        panel.add(
                label,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(
                component,
                gbc
        );
    }

    // =========================================================
    // POPULATE
    // =========================================================

    private void populateUser() {

        usernameField.setText(
                safe(existingUser.getUsername())
        );

        staffField.setText(
                safe(existingUser.getStaff())
        );

        roleComboBox.setSelectedItem(
                safe(existingUser.getRole())
        );

        activeCheckBox.setSelected(
                Boolean.TRUE.equals(
                        existingUser.getActive()
                )
        );

        passwordField.setText("");
    }

    // =========================================================
    // SAVE
    // =========================================================

    private void saveUser() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String staff =
                staffField
                        .getText()
                        .trim();

        String role =
                String.valueOf(
                        roleComboBox
                                .getSelectedItem()
                );

        boolean active =
                activeCheckBox.isSelected();

        if (username.isEmpty()) {

            showValidation(
                    "Username is required."
            );

            usernameField.requestFocus();

            return;
        }

        if (existingUser == null
                && password.isEmpty()) {

            showValidation(
                    "Password is required."
            );

            passwordField.requestFocus();

            return;
        }

        if (role == null
                || role.isBlank()) {

            showValidation(
                    "Please select a role."
            );

            roleComboBox.requestFocus();

            return;
        }

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        statusLabel.setText(
                existingUser == null
                        ? "Creating account..."
                        : "Saving changes..."
        );

        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground()
                    throws Exception {

                User user =
                        existingUser == null
                                ? new User()
                                : existingUser;

                user.setUsername(username);
                user.setRole(role);
                user.setStaff(staff);
                user.setActive(active);

                /*
                 * Keep the existing password hash when editing
                 * unless a new password was entered.
                 */
                if (!password.isEmpty()) {

                    user.setPasswordHash(
                            password
                    );
                }

                if (existingUser == null) {

                    return invokeCreate(
                            user,
                            password
                    );

                } else {

                    return invokeUpdate(
                            user,
                            password
                    );
                }
            }

            @Override
            protected void done() {

                try {

                    boolean result = get();

                    if (!result) {

                        throw new Exception(
                                "The user management service did not complete the operation."
                        );
                    }

                    saved = true;

                    dispose();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    saveButton.setEnabled(true);
                    cancelButton.setEnabled(true);

                    statusLabel.setText(
                            "Save failed."
                    );

                    JOptionPane.showMessageDialog(
                            UserDialog.this,
                            "Unable to save the staff account.\n\n"
                                    + ex.getMessage(),
                            "Save Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    // =========================================================
    // CREATE
    // =========================================================

    private boolean invokeCreate(
            User user,
            String rawPassword
    ) throws Exception {

        String[] methodNames = {
                "createUser",
                "addUser",
                "registerUser"
        };

        for (String name : methodNames) {

            // User parameter
            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        name,
                                        User.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                user
                        );

                return result == null
                        || !(result instanceof Boolean)
                        || Boolean.TRUE.equals(result);

            } catch (NoSuchMethodException ignored) {
            }
        }

        // User + raw password parameter
        for (String name : methodNames) {

            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        name,
                                        User.class,
                                        String.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                user,
                                rawPassword
                        );

                return result == null
                        || !(result instanceof Boolean)
                        || Boolean.TRUE.equals(result);

            } catch (NoSuchMethodException ignored) {
            }
        }

        throw new NoSuchMethodException(
                "No supported user creation method was found."
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    private boolean invokeUpdate(
            User user,
            String rawPassword
    ) throws Exception {

        String[] methodNames = {
                "updateUser",
                "editUser"
        };

        for (String name : methodNames) {

            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        name,
                                        User.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                user
                        );

                return result == null
                        || !(result instanceof Boolean)
                        || Boolean.TRUE.equals(result);

            } catch (NoSuchMethodException ignored) {
            }
        }

        for (String name : methodNames) {

            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        name,
                                        User.class,
                                        String.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                user,
                                rawPassword
                        );

                return result == null
                        || !(result instanceof Boolean)
                        || Boolean.TRUE.equals(result);

            } catch (NoSuchMethodException ignored) {
            }
        }

        throw new NoSuchMethodException(
                "No supported user update method was found."
        );
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void showValidation(
            String message
    ) {

        statusLabel.setText(
                message
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

        getContentPane().setBackground(
                theme.getBackgroundColor()
        );

        titleLabel.setForeground(
                theme.getTextColor()
        );

        subtitleLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        statusLabel.setForeground(
                theme.getSecondaryTextColor()
        );

        activeCheckBox.setForeground(
                theme.getTextColor()
        );

        passwordField.setBackground(
                theme.getSurfaceColor()
        );

        passwordField.setForeground(
                theme.getTextColor()
        );

        repaint();
    }

    @Override
    public void themeChanged(
            Theme newTheme
    ) {

        applyTheme(newTheme);
    }

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.dispose();
    }

    // =========================================================
    // UTILITY
    // =========================================================

    private String safe(
            String value
    ) {

        return value == null
                ? ""
                : value;
    }

    public boolean isSaved() {

        return saved;
    }
}