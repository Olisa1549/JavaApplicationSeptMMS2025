package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Nurse;
import hospital.services.NurseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NursePanel extends JPanel {

    private final NurseService nurseService;

    private JTable nurseTable;
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

    public NursePanel() {

        nurseService = new NurseService();

        setLayout(new BorderLayout(15, 15));
        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        themeListener = this::applyTheme;

        ThemeManager.addThemeChangeListener(
                themeListener
        );

        initUI();

        loadNurses();

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    private void initUI() {

        // =====================================================
        // TOP PANEL
        // =====================================================

        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

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
                new JLabel("Nurse Management");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Manage nurses, qualifications and professional information."
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
                "Search nurses"
        );

        searchField.setPreferredSize(
                new Dimension(260, 40)
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
                "Nursing License",
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

        nurseTable =
                new JTable(tableModel);

        nurseTable.setRowHeight(34);

        nurseTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        nurseTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        nurseTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        nurseTable.setRowSorter(sorter);

        JScrollPane scrollPane =
                new JScrollPane(
                        nurseTable
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
                new AppButton("Add Nurse");

        editButton =
                new AppButton("Edit");

        deleteButton =
                new AppButton("Delete");

        refreshButton =
                new AppButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
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
                new JLabel("Nurses: 0");

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
                e -> deleteSelectedNurse()
        );

        refreshButton.addActionListener(
                e -> loadNurses()
        );

        // =====================================================
        // DOUBLE CLICK TO EDIT
        // =====================================================

        nurseTable.addMouseListener(
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
    // LOAD NURSES
    // =========================================================

    private void loadNurses() {

        statusLabel.setText(
                "Loading..."
        );

        new SwingWorker<List<Nurse>, Void>() {

            @Override
            protected List<Nurse> doInBackground()
                    throws Exception {

                return nurseService
                        .getAllNurses();
            }

            @Override
            protected void done() {

                try {

                    List<Nurse> nurses =
                            get();

                    tableModel.setRowCount(0);

                    for (Nurse nurse : nurses) {

                        String department = "";

                        if (nurse.getDepartment()
                                != null) {

                            department =
                                    nurse.getDepartment()
                                            .getName();
                        }

                        tableModel.addRow(
                                new Object[]{
                                        nurse.getStaffID(),
                                        nurse.getFirstName(),
                                        nurse.getLastName(),
                                        String.valueOf(
                                                nurse.getGender()
                                        ),
                                        department,
                                        nurse.getQualification(),
                                        nurse.getNursingLicense(),
                                        nurse.getPhone()
                                }
                        );
                    }

                    countLabel.setText(
                            "Nurses: "
                                    + nurses.size()
                    );

                    statusLabel.setText(
                            "Loaded successfully"
                    );

                } catch (Exception ex) {

                    statusLabel.setText(
                            "Load failed"
                    );

                    JOptionPane.showMessageDialog(
                            NursePanel.this,
                            "Unable to load nurses.\n\n"
                                    + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        }.execute();
    }

    // =========================================================
    // ADD NURSE
    // =========================================================

    private void openAddDialog() {

        NurseDialog dialog =
                new NurseDialog(
                        SwingUtilities
                                .getWindowAncestor(this),
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {

            loadNurses();
        }
    }

    // =========================================================
    // EDIT NURSE
    // =========================================================

    private void openEditDialog() {

        int selectedRow =
                nurseTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a nurse first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                nurseTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int staffId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        Nurse nurse =
                nurseService
                        .getNurseById(
                                staffId
                        );

        if (nurse == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected nurse could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        NurseDialog dialog =
                new NurseDialog(
                        SwingUtilities
                                .getWindowAncestor(this),
                        nurse
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {

            loadNurses();
        }
    }

    // =========================================================
    // DELETE NURSE
    // =========================================================

    private void deleteSelectedNurse() {

        int selectedRow =
                nurseTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a nurse first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                nurseTable
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
                        "Delete nurse "
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

                return nurseService
                        .deleteNurse(
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
                                NursePanel.this,
                                "Nurse deleted successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        loadNurses();

                    } else {

                        statusLabel.setText(
                                "Delete failed"
                        );

                        JOptionPane.showMessageDialog(
                                NursePanel.this,
                                "The nurse could not be deleted.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (Exception ex) {

                    statusLabel.setText(
                            "Delete failed"
                    );

                    JOptionPane.showMessageDialog(
                            NursePanel.this,
                            "Error deleting nurse.\n\n"
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

    private void applyTheme(Theme theme) {

        if (theme == null) {
            return;
        }

        setBackground(
                theme.getBackgroundColor()
        );

        if (nurseTable != null) {

            nurseTable.setBackground(
                    theme.getSurfaceColor()
            );

            nurseTable.setForeground(
                    theme.getTextColor()
            );

            nurseTable.getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            nurseTable.getTableHeader()
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