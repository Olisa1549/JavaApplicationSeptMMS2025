package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.LaboratoryTechnician;
import hospital.services.LaboratoryTechnicianService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class LaboratoryTechnicianPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private final LaboratoryTechnicianService service;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel countLabel;

    public LaboratoryTechnicianPanel() {

        service = new LaboratoryTechnicianService();

        setLayout(new BorderLayout(15, 15));
        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        initUI();

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());

        loadTechnicians();
    }

    private void initUI() {

        // =====================================================
        // TOP
        // =====================================================

        JPanel topPanel =
                new JPanel(new BorderLayout(15, 10));

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

        JLabel title =
                new JLabel("Laboratory Technicians");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Manage laboratory technician records."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        countLabel =
                new JLabel("0 technicians");

        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(3)
        );
        titlePanel.add(subtitle);
        titlePanel.add(
                Box.createVerticalStrut(4)
        );
        titlePanel.add(countLabel);

        topPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =====================================================
        // SEARCH + ACTIONS
        // =====================================================

        JPanel rightPanel =
                new JPanel();

        rightPanel.setOpaque(false);

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                0
                        )
                );

        searchPanel.setOpaque(false);

        searchField =
                new AppTextField();

        searchField.setPreferredSize(
                new Dimension(260, 40)
        );

        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Search technicians..."
        );

        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                filterTable();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                filterTable();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e) {
                                filterTable();
                            }
                        }
                );

        searchPanel.add(searchField);

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                5
                        )
                );

        actionPanel.setOpaque(false);

        addButton =
                new AppButton("ADD TECHNICIAN");

        editButton =
                new AppButton("EDIT");

        deleteButton =
                new AppButton("DELETE");

        refreshButton =
                new AppButton("REFRESH");

        addButton.addActionListener(
                e -> openAddDialog()
        );

        editButton.addActionListener(
                e -> openEditDialog()
        );

        deleteButton.addActionListener(
                e -> deleteTechnician()
        );

        refreshButton.addActionListener(
                e -> loadTechnicians()
        );

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(refreshButton);

        rightPanel.add(searchPanel);
        rightPanel.add(actionPanel);

        topPanel.add(
                rightPanel,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Staff ID",
                                "First Name",
                                "Last Name",
                                "Gender",
                                "Department",
                                "Qualification",
                                "License No.",
                                "Phone"
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

        table =
                new JTable(tableModel);

        table.setRowHeight(34);
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader()
                .setReorderingAllowed(false);

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        table.setRowSorter(sorter);

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(60);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(130);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(160);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(120);

        table.addMouseListener(
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

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // LOAD
    // =========================================================

    private void loadTechnicians() {

        addButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        refreshButton.setEnabled(false);

        SwingWorker<List<LaboratoryTechnician>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected List<LaboratoryTechnician> doInBackground() {
                        return service
                                .getAllLaboratoryTechnicians();
                    }

                    @Override
                    protected void done() {

                        try {

                            List<LaboratoryTechnician> technicians =
                                    get();

                            tableModel.setRowCount(0);

                            for (LaboratoryTechnician technician
                                    : technicians) {

                                String department =
                                        technician.getDepartment() == null
                                                ? ""
                                                : technician
                                                .getDepartment()
                                                .getName();

                                tableModel.addRow(
                                        new Object[]{
                                                technician.getStaffID(),
                                                technician.getFirstName(),
                                                technician.getLastName(),
                                                technician.getGender(),
                                                department,
                                                technician.getQualification(),
                                                technician.getLicenseNumber(),
                                                technician.getPhone()
                                        }
                                );
                            }

                            updateCount();

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    LaboratoryTechnicianPanel.this,
                                    "Could not load laboratory technicians.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            addButton.setEnabled(true);
                            editButton.setEnabled(true);
                            deleteButton.setEnabled(true);
                            refreshButton.setEnabled(true);
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void filterTable() {

        String text =
                searchField.getText().trim();

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

        updateCount();
    }

    private void updateCount() {

        int count =
                table.getRowCount();

        countLabel.setText(
                count + (
                        count == 1
                                ? " technician"
                                : " technicians"
                )
        );
    }

    // =========================================================
    // ADD
    // =========================================================

    private void openAddDialog() {

        Window owner =
                SwingUtilities.getWindowAncestor(this);

        LaboratoryTechnicianDialog dialog =
                new LaboratoryTechnicianDialog(
                        owner,
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadTechnicians();
        }
    }

    // =========================================================
    // EDIT
    // =========================================================

    private void openEditDialog() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a laboratory technician first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        int staffId =
                Integer.parseInt(
                        String.valueOf(
                                tableModel.getValueAt(
                                        modelRow,
                                        0
                                )
                        )
                );

        LaboratoryTechnician technician =
                service.getLaboratoryTechnicianById(
                        staffId
                );

        if (technician == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected technician could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Window owner =
                SwingUtilities.getWindowAncestor(this);

        LaboratoryTechnicianDialog dialog =
                new LaboratoryTechnicianDialog(
                        owner,
                        technician
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadTechnicians();
        }
    }

    // =========================================================
    // DELETE
    // =========================================================

    private void deleteTechnician() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a laboratory technician first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        int staffId =
                Integer.parseInt(
                        String.valueOf(
                                tableModel.getValueAt(
                                        modelRow,
                                        0
                                )
                        )
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

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete laboratory technician "
                                + firstName
                                + " "
                                + lastName
                                + "?\n\n"
                                + "This action cannot be undone.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        deleteButton.setEnabled(false);

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {
                        return service.deleteLaboratoryTechnician(
                                staffId
                        );
                    }

                    @Override
                    protected void done() {

                        try {

                            if (get()) {

                                JOptionPane.showMessageDialog(
                                        LaboratoryTechnicianPanel.this,
                                        "Laboratory technician deleted successfully.",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                loadTechnicians();

                            } else {

                                JOptionPane.showMessageDialog(
                                        LaboratoryTechnicianPanel.this,
                                        "The laboratory technician could not be deleted.",
                                        "Operation Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    LaboratoryTechnicianPanel.this,
                                    "An error occurred while deleting the technician.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            deleteButton.setEnabled(true);
                        }
                    }
                };

        worker.execute();
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

        if (table != null) {

            table.setBackground(
                    theme.getSurfaceColor()
            );

            table.setForeground(
                    theme.getTextColor()
            );

            table.getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            table.getTableHeader()
                    .setForeground(
                            theme.getTextColor()
                    );

            table.getTableHeader()
                    .setFont(
                            new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    13
                            )
                    );
        }

        repaint();
    }

    @Override
    public void themeChanged(Theme newTheme) {
        applyTheme(newTheme);
    }

    @Override
    public void removeNotify() {

        ThemeManager.removeThemeChangeListener(this);

        super.removeNotify();
    }
}