package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppComboBox;
import hospital.gui.components.AppTextField;
import hospital.gui.components.icons.AppIcon;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.User;
import hospital.services.UserManagementService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;

public class UserManagementPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton toggleButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel countLabel;
    private JLabel statusLabel;

    private final UserManagementService userService;

    public UserManagementPanel() {

        userService = new UserManagementService();

        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initUI();

        ThemeManager.addThemeChangeListener(this);

        loadUsers();
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(
                headerPanel,
                BoxLayout.Y_AXIS
        ));
        headerPanel.setOpaque(false);

        JPanel titleRow = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        8,
                        0
                )
        );
        titleRow.setOpaque(false);

        AppIcon userIcon =
                new AppIcon(AppIcon.IconType.USER);

        titleRow.add(userIcon);

        JLabel titleLabel =
                new JLabel("Staff Accounts");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titleRow.add(titleLabel);

        headerPanel.add(titleRow);

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Create and manage hospital staff login accounts."
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        headerPanel.add(subtitleLabel);

        headerPanel.add(
                Box.createVerticalStrut(15)
        );

        // ---------------------------------------------------------
        // SEARCH
        // ---------------------------------------------------------

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(8, 0)
                );

        searchPanel.setOpaque(false);

        searchField =
                new AppTextField();

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        36
                )
        );

        searchPanel.add(
                searchField,
                BorderLayout.WEST
        );

        headerPanel.add(searchPanel);

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================================================
        // TABLE
        // =========================================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "User ID",
                                "Username",
                                "Role",
                                "Staff ID",
                                "Status"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        userTable =
                new JTable(tableModel);

        userTable.setRowHeight(34);
        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        userTable.setAutoCreateRowSorter(true);

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        userTable.setRowSorter(sorter);

        userTable.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        userTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(userTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================================================
        // BOTTOM
        // =========================================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        bottomPanel.setOpaque(false);

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        addButton =
                new AppButton("Add Account");

        editButton =
                new AppButton("Edit");

        toggleButton =
                new AppButton("Activate / Deactivate");

        deleteButton =
                new AppButton("Delete");

        refreshButton =
                new AppButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(toggleButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        bottomPanel.add(
                buttonPanel,
                BorderLayout.WEST
        );

        JPanel infoPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        infoPanel.setOpaque(false);

        countLabel =
                new JLabel("Accounts: 0");

        statusLabel =
                new JLabel("Ready");

        infoPanel.add(countLabel);
        infoPanel.add(statusLabel);

        bottomPanel.add(
                infoPanel,
                BorderLayout.EAST
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =========================================================
        // ACTIONS
        // =========================================================

        addButton.addActionListener(
                e -> openAddDialog()
        );

        editButton.addActionListener(
                e -> openEditDialog()
        );

        toggleButton.addActionListener(
                e -> toggleSelectedUser()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedUser()
        );

        refreshButton.addActionListener(
                e -> loadUsers()
        );

        userTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (e.getClickCount() == 2
                                && SwingUtilities
                                .isLeftMouseButton(e)) {

                            openEditDialog();
                        }
                    }
                }
        );

        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            private void filter() {

                                String text =
                                        searchField
                                                .getText()
                                                .trim();

                                if (text.isEmpty()) {

                                    sorter.setRowFilter(null);

                                } else {

                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(
                                                    "(?i)"
                                                            + java.util.regex.Pattern
                                                            .quote(text)
                                            )
                                    );
                                }
                            }

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                filter();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                filter();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                filter();
                            }
                        }
                );
    }

    // =========================================================
    // LOAD USERS
    // =========================================================

    private void loadUsers() {

        statusLabel.setText("Loading...");

        new SwingWorker<List<User>, Void>() {

            @Override
            protected List<User> doInBackground()
                    throws Exception {

                return userService.getAllUsers();
            }

            @Override
            protected void done() {

                try {

                    List<User> users = get();

                    tableModel.setRowCount(0);

                    for (User user : users) {

                        tableModel.addRow(
                                new Object[]{
                                        user.getId(),
                                        safe(user.getUsername()),
                                        safe(user.getRole()),
                                        safe(user.getStaff()),
                                        Boolean.TRUE.equals(
                                                user.getActive()
                                        )
                                                ? "Active"
                                                : "Inactive"
                                }
                        );
                    }

                    countLabel.setText(
                            "Accounts: "
                                    + users.size()
                    );

                    statusLabel.setText(
                            "Ready"
                    );

                } catch (Exception ex) {

                    ex.printStackTrace();

                    statusLabel.setText(
                            "Unable to load accounts."
                    );

                    JOptionPane.showMessageDialog(
                            UserManagementPanel.this,
                            "Unable to load staff accounts.\n\n"
                                    + ex.getMessage(),
                            "Load Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    // =========================================================
    // ADD
    // =========================================================

    private void openAddDialog() {

        Window owner =
                SwingUtilities.getWindowAncestor(this);

        UserDialog dialog =
                new UserDialog(
                        owner,
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadUsers();
        }
    }

    // =========================================================
    // EDIT
    // =========================================================

    private void openEditDialog() {

        User user =
                getSelectedUser();

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user account first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Window owner =
                SwingUtilities.getWindowAncestor(this);

        UserDialog dialog =
                new UserDialog(
                        owner,
                        user
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadUsers();
        }
    }

    // =========================================================
    // GET SELECTED USER
    // =========================================================

    private User getSelectedUser() {

        int viewRow =
                userTable.getSelectedRow();

        if (viewRow < 0) {
            return null;
        }

        int modelRow =
                userTable.convertRowIndexToModel(
                        viewRow
                );

        int userId =
                Integer.parseInt(
                        String.valueOf(
                                tableModel.getValueAt(
                                        modelRow,
                                        0
                                )
                        )
                );

        try {

            return findUserById(userId);

        } catch (Exception ex) {

            ex.printStackTrace();

            return null;
        }
    }

    // =========================================================
    // FIND USER
    // =========================================================

    private User findUserById(int id)
            throws Exception {

        String[] methods = {
                "getUserById",
                "findUserById",
                "getById",
                "findById"
        };

        for (String methodName : methods) {

            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        methodName,
                                        int.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                id
                        );

                if (result instanceof User) {
                    return (User) result;
                }

            } catch (NoSuchMethodException ignored) {
            }
        }

        List<User> users =
                userService.getAllUsers();

        for (User user : users) {

            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    // =========================================================
    // TOGGLE
    // =========================================================

    private void toggleSelectedUser() {

        User user =
                getSelectedUser();

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user account first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String currentStatus =
                Boolean.TRUE.equals(user.getActive())
                        ? "active"
                        : "inactive";

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Change this account from "
                                + currentStatus
                                + " to "
                                + (
                                Boolean.TRUE.equals(
                                        user.getActive()
                                )
                                        ? "inactive"
                                        : "active"
                        )
                                + "?",
                        "Change Account Status",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        statusLabel.setText("Updating...");

        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground()
                    throws Exception {

                return invokeToggleMethod(
                        user.getId()
                );
            }

            @Override
            protected void done() {

                try {

                    Boolean result = get();

                    if (Boolean.TRUE.equals(result)) {

                        loadUsers();

                    } else {

                        statusLabel.setText(
                                "Unable to update status."
                        );

                        JOptionPane.showMessageDialog(
                                UserManagementPanel.this,
                                "The account status could not be changed.",
                                "Update Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (Exception ex) {

                    ex.printStackTrace();

                    statusLabel.setText(
                            "Update failed."
                    );

                    JOptionPane.showMessageDialog(
                            UserManagementPanel.this,
                            "Unable to update account status.\n\n"
                                    + ex.getMessage(),
                            "Update Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    private boolean invokeToggleMethod(int userId)
            throws Exception {

        String[] methods = {
                "toggleUserActive",
                "toggleActive",
                "toggleUserStatus",
                "setUserActive"
        };

        for (String methodName : methods) {

            try {

                Method method =
                        userService
                                .getClass()
                                .getMethod(
                                        methodName,
                                        int.class
                                );

                Object result =
                        method.invoke(
                                userService,
                                userId
                        );

                if (result instanceof Boolean) {
                    return (Boolean) result;
                }

                return true;

            } catch (NoSuchMethodException ignored) {
            }
        }

        return false;
    }

    // =========================================================
    // DELETE
    // =========================================================

    private void deleteSelectedUser() {

        User user =
                getSelectedUser();

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user account first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete the account for \""
                                + user.getUsername()
                                + "\"?\n\n"
                                + "This action cannot be undone.",
                        "Delete Account",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        statusLabel.setText("Deleting...");

        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                userService.deleteUser(
                        user.getId()
                );

                return null;
            }

            @Override
            protected void done() {

                try {

                    get();

                    loadUsers();

                } catch (Exception ex) {

                    ex.printStackTrace();

                    statusLabel.setText(
                            "Delete failed."
                    );

                    JOptionPane.showMessageDialog(
                            UserManagementPanel.this,
                            "Unable to delete the account.\n\n"
                                    + ex.getMessage(),
                            "Delete Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
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

        userTable.setBackground(
                theme.getSurfaceColor()
        );

        userTable.setForeground(
                theme.getTextColor()
        );

        userTable.getTableHeader()
                .setBackground(
                        theme.getSurfaceColor()
                );

        userTable.getTableHeader()
                .setForeground(
                        theme.getTextColor()
                );

        countLabel.setForeground(
                theme.getTextColor()
        );

        statusLabel.setForeground(
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

    @Override
    public void removeNotify() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.removeNotify();
    }

    // =========================================================
    // UTILITY
    // =========================================================

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }
}