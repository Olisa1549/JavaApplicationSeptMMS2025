package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Pharmacist;
import hospital.services.PharmacistService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PharmacistPanel extends JPanel {

    private final PharmacistService pharmacistService;

    private JTable pharmacistTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private JLabel countLabel;
    private JLabel statusLabel;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private final ThemeManager.ThemeChangeListener themeListener;

    public PharmacistPanel() {

        pharmacistService = new PharmacistService();

        setLayout(
                new BorderLayout(
                        15,
                        15
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

        loadPharmacists();

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initUI() {

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        topPanel.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Pharmacist Management"
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Manage pharmacists, qualifications and professional information."
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titlePanel.add(titleLabel);

        titlePanel.add(
                Box.createVerticalStrut(4)
        );

        titlePanel.add(subtitleLabel);

        topPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =====================================================
        // SEARCH
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        searchField =
                new AppTextField();

        searchField.setToolTipText(
                "Search pharmacists"
        );

        searchField.setPreferredSize(
                new Dimension(
                        260,
                        40
                )
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        topPanel.add(
                searchPanel,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Staff ID",
                "First Name",
                "Last Name",
                "Gender",
                "Department",
                "Qualification",
                "License No.",
                "Phone"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
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

        pharmacistTable =
                new JTable(
                        tableModel
                );

        pharmacistTable.setRowHeight(
                34
        );

        pharmacistTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        pharmacistTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        pharmacistTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        pharmacistTable.setRowSorter(
                sorter
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        pharmacistTable
                );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // SEARCH FILTER
        // =====================================================

        searchField
                .getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            private void filter() {

                                String text =
                                        searchField
                                                .getText()
                                                .trim();

                                if (text.isEmpty()) {

                                    sorter.setRowFilter(
                                            null
                                    );

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
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }
                        }
                );

        // =====================================================
        // BOTTOM PANEL
        // =====================================================

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
                new AppButton(
                        "Add Pharmacist"
                );

        editButton =
                new AppButton(
                        "Edit"
                );

        deleteButton =
                new AppButton(
                        "Delete"
                );

        refreshButton =
                new AppButton(
                        "Refresh"
                );

        buttonPanel.add(
                addButton
        );

        buttonPanel.add(
                editButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                refreshButton
        );

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
                new JLabel(
                        "Pharmacists: 0"
                );

        statusLabel =
                new JLabel(
                        "Ready"
                );

        infoPanel.add(
                countLabel
        );

        infoPanel.add(
                statusLabel
        );

        bottomPanel.add(
                infoPanel,
                BorderLayout.EAST
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> openAddDialog()
        );

        editButton.addActionListener(
                e -> openEditDialog()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedPharmacist()
        );

        refreshButton.addActionListener(
                e -> loadPharmacists()
        );

        // =====================================================
        // DOUBLE CLICK
        // =====================================================

        pharmacistTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2
                                && SwingUtilities
                                .isLeftMouseButton(e)) {

                            openEditDialog();
                        }
                    }
                }
        );
    }

    // =========================================================
    // LOAD PHARMACISTS
    // =========================================================

    private void loadPharmacists() {

        statusLabel.setText(
                "Loading..."
        );

        new SwingWorker<List<Pharmacist>, Void>() {

            @Override
            protected List<Pharmacist> doInBackground()
                    throws Exception {

                return pharmacistService
                        .getAllPharmacists();
            }

            @Override
            protected void done() {

                try {

                    List<Pharmacist> pharmacists =
                            get();

                    tableModel.setRowCount(
                            0
                    );

                    for (Pharmacist pharmacist :
                            pharmacists) {

                        String department = "";

                        if (pharmacist.getDepartment() != null) {

                            department =
                                    pharmacist
                                            .getDepartment()
                                            .getName();
                        }

                        tableModel.addRow(
                                new Object[]{
                                        pharmacist.getStaffID(),
                                        pharmacist.getFirstName(),
                                        pharmacist.getLastName(),
                                        String.valueOf(
                                                pharmacist.getGender()
                                        ),
                                        department,
                                        pharmacist.getQualification(),
                                        pharmacist.getLicenseNumber(),
                                        pharmacist.getPhone()
                                }
                        );
                    }

                    countLabel.setText(
                            "Pharmacists: "
                                    + pharmacists.size()
                    );

                    statusLabel.setText(
                            "Loaded successfully"
                    );

                } catch (Exception ex) {

                    statusLabel.setText(
                            "Load failed"
                    );

                    JOptionPane.showMessageDialog(
                            PharmacistPanel.this,
                            "Unable to load pharmacists.\n\n"
                                    + ex.getMessage(),
                            "Error",
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

        PharmacistDialog dialog =
                new PharmacistDialog(
                        SwingUtilities
                                .getWindowAncestor(this),
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {

            loadPharmacists();
        }
    }

    // =========================================================
    // EDIT
    // =========================================================

    private void openEditDialog() {

        int selectedRow =
                pharmacistTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a pharmacist first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                pharmacistTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int staffId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        Pharmacist pharmacist =
                pharmacistService
                        .getPharmacistById(
                                staffId
                        );

        if (pharmacist == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected pharmacist could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        PharmacistDialog dialog =
                new PharmacistDialog(
                        SwingUtilities
                                .getWindowAncestor(this),
                        pharmacist
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {

            loadPharmacists();
        }
    }

    // =========================================================
    // DELETE
    // =========================================================

    private void deleteSelectedPharmacist() {

        int selectedRow =
                pharmacistTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a pharmacist first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                pharmacistTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int staffId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        String firstName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );

        String lastName =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                2
                        )
                );

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete pharmacist "
                                + firstName
                                + " "
                                + lastName
                                + "?\n\n"
                                + "This action cannot be undone.",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation
                != JOptionPane.YES_OPTION) {

            return;
        }

        statusLabel.setText(
                "Deleting..."
        );

        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground()
                    throws Exception {

                return pharmacistService
                        .deletePharmacist(
                                staffId
                        );
            }

            @Override
            protected void done() {

                try {

                    boolean success =
                            get();

                    if (success) {

                        JOptionPane.showMessageDialog(
                                PharmacistPanel.this,
                                "Pharmacist deleted successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        loadPharmacists();

                    } else {

                        statusLabel.setText(
                                "Delete failed"
                        );

                        JOptionPane.showMessageDialog(
                                PharmacistPanel.this,
                                "The pharmacist could not be deleted.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (Exception ex) {

                    statusLabel.setText(
                            "Delete failed"
                    );

                    JOptionPane.showMessageDialog(
                            PharmacistPanel.this,
                            "Error deleting pharmacist.\n\n"
                                    + ex.getMessage(),
                            "Error",
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

        if (pharmacistTable != null) {

            pharmacistTable.setBackground(
                    theme.getSurfaceColor()
            );

            pharmacistTable.setForeground(
                    theme.getTextColor()
            );

            pharmacistTable
                    .getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            pharmacistTable
                    .getTableHeader()
                    .setForeground(
                            theme.getTextColor()
                    );
        }

        if (countLabel != null) {

            countLabel.setForeground(
                    theme.getTextColor()
            );
        }

        if (statusLabel != null) {

            statusLabel.setForeground(
                    theme.getSecondaryTextColor()
            );
        }

        repaint();
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                themeListener
        );
    }
}