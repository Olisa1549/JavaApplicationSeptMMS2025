package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Doctor;
import hospital.services.DoctorService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel countLabel;
    private JLabel statusLabel;

    private final DoctorService doctorService;

    public DoctorPanel() {

        doctorService =
                new DoctorService();

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        setBorder(
                new EmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );

        initUI();

        ThemeManager.addThemeChangeListener(
                this
        );

        loadDoctors();
    }

    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initUI() {

        createTopPanel();

        createTable();

        createBottomPanel();

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // TOP PANEL
    // =========================================================

    private void createTopPanel() {

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        topPanel.setOpaque(false);

        // =====================================================
        // TITLE SECTION
        // =====================================================

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
                new JLabel(
                        "Doctor Management"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Manage doctors and their professional information."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(
                subtitle
        );

        // =====================================================
        // SEARCH SECTION
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        searchField =
                new AppTextField();

        searchField.setPreferredSize(
                new Dimension(
                        260,
                        40
                )
        );

        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Search doctors..."
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        // =====================================================
        // ACTION BUTTONS
        // =====================================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        actionPanel.setOpaque(false);

        addButton =
                new AppButton(
                        "Add Doctor"
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

        editButton.setEnabled(
                false
        );

        deleteButton.setEnabled(
                false
        );

        addButton.addActionListener(
                e -> addDoctor()
        );

        editButton.addActionListener(
                e -> editDoctor()
        );

        deleteButton.addActionListener(
                e -> deleteDoctor()
        );

        refreshButton.addActionListener(
                e -> loadDoctors()
        );

        actionPanel.add(
                addButton
        );

        actionPanel.add(
                editButton
        );

        actionPanel.add(
                deleteButton
        );

        actionPanel.add(
                refreshButton
        );

        // =====================================================
        // SEARCH FILTER
        // =====================================================

        searchField
                .getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterDoctors();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterDoctors();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filterDoctors();
                            }
                        }
                );

        // =====================================================
        // TOP LAYOUT
        // =====================================================

        topPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        /*
         * Search and actions are placed together on the right,
         * but inside their own panel so they cannot overlap
         * the title section.
         */

        JPanel rightPanel =
                new JPanel();

        rightPanel.setOpaque(false);

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        searchPanel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        actionPanel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        rightPanel.add(
                searchPanel
        );

        rightPanel.add(
                Box.createVerticalStrut(8)
        );

        rightPanel.add(
                actionPanel
        );

        topPanel.add(
                rightPanel,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );
    }

    // =========================================================
    // TABLE
    // =========================================================

    private void createTable() {

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Staff ID",
                                "First Name",
                                "Last Name",
                                "Gender",
                                "Department",
                                "Specialization",
                                "Phone",
                                "License No."
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

        doctorTable =
                new JTable(
                        tableModel
                );

        doctorTable.setRowHeight(
                34
        );

        doctorTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        doctorTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        doctorTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        doctorTable.setRowSorter(
                sorter
        );

        doctorTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> updateButtonState()
                );

        doctorTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (e.getClickCount() == 2 &&
                                doctorTable.getSelectedRow() != -1) {

                            editDoctor();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        doctorTable
                );

        add(
                scrollPane,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // BOTTOM PANEL
    // =========================================================

    private void createBottomPanel() {

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        countLabel =
                new JLabel(
                        "Doctors: 0"
                );

        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        statusLabel =
                new JLabel(
                        "Ready"
                );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        bottomPanel.add(
                countLabel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                statusLabel,
                BorderLayout.EAST
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }

    // =========================================================
    // LOAD DOCTORS
    // =========================================================

    private void loadDoctors() {

        statusLabel.setText(
                "Loading doctors..."
        );

        addButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        refreshButton.setEnabled(false);

        SwingWorker<List<Doctor>, Void>
                worker =
                new SwingWorker<>() {

                    @Override
                    protected List<Doctor>
                    doInBackground() {

                        return doctorService
                                .getAllDoctors();
                    }

                    @Override
                    protected void done() {

                        try {

                            List<Doctor> doctors =
                                    get();

                            tableModel.setRowCount(
                                    0
                            );

                            for (Doctor doctor :
                                    doctors) {

                                String departmentName =
                                        "";

                                if (doctor.getDepartment() != null) {

                                    departmentName =
                                            doctor
                                                    .getDepartment()
                                                    .getName();
                                }

                                tableModel.addRow(
                                        new Object[]{
                                                doctor.getStaffID(),
                                                doctor.getFirstName(),
                                                doctor.getLastName(),
                                                doctor.getGender(),
                                                departmentName,
                                                doctor.getSpecialization(),
                                                doctor.getPhone(),
                                                doctor.getLicenseNumber()
                                        }
                                );
                            }

                            countLabel.setText(
                                    "Doctors: "
                                            + doctors.size()
                            );

                            statusLabel.setText(
                                    "Doctors loaded successfully."
                            );

                        } catch (Exception e) {

                            e.printStackTrace();

                            statusLabel.setText(
                                    "Failed to load doctors."
                            );

                            JOptionPane.showMessageDialog(
                                    DoctorPanel.this,
                                    "Unable to load doctors.\n\n"
                                            + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            addButton.setEnabled(
                                    true
                            );

                            refreshButton.setEnabled(
                                    true
                            );

                            updateButtonState();
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void filterDoctors() {

        if (sorter == null) {
            return;
        }

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

    // =========================================================
    // BUTTON STATE
    // =========================================================

    private void updateButtonState() {

        boolean selected =
                doctorTable != null &&
                        doctorTable.getSelectedRow() != -1;

        if (editButton != null) {

            editButton.setEnabled(
                    selected
            );
        }

        if (deleteButton != null) {

            deleteButton.setEnabled(
                    selected
            );
        }
    }

    // =========================================================
    // ADD DOCTOR
    // =========================================================

    private void addDoctor() {

        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );

        DoctorDialog dialog =
                new DoctorDialog(
                        window,
                        null
                );

        dialog.setVisible(
                true
        );

        if (dialog.isSaved()) {

            loadDoctors();
        }
    }

    // =========================================================
    // EDIT DOCTOR
    // =========================================================

    private void editDoctor() {

        int selectedRow =
                doctorTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                doctorTable.convertRowIndexToModel(
                        selectedRow
                );

        int staffId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        Doctor doctor =
                doctorService.getDoctorById(
                        staffId
                );

        if (doctor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to find the selected doctor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Window window =
                SwingUtilities.getWindowAncestor(
                        this
                );

        DoctorDialog dialog =
                new DoctorDialog(
                        window,
                        doctor
                );

        dialog.setVisible(
                true
        );

        if (dialog.isSaved()) {

            loadDoctors();
        }
    }

    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    private void deleteDoctor() {

        int selectedRow =
                doctorTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                doctorTable.convertRowIndexToModel(
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

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete doctor "
                                + firstName
                                + " "
                                + lastName
                                + "?\n\n"
                                + "This action cannot be undone.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        statusLabel.setText(
                "Deleting doctor..."
        );

        deleteButton.setEnabled(
                false
        );

        SwingWorker<Boolean, Void>
                worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean
                    doInBackground() {

                        return doctorService
                                .deleteDoctor(
                                        staffId
                                );
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean deleted =
                                    get();

                            if (deleted) {

                                statusLabel.setText(
                                        "Doctor deleted successfully."
                                );

                                loadDoctors();

                            } else {

                                statusLabel.setText(
                                        "Unable to delete doctor."
                                );

                                JOptionPane.showMessageDialog(
                                        DoctorPanel.this,
                                        "The doctor could not be deleted.",
                                        "Delete Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );

                                updateButtonState();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                            statusLabel.setText(
                                    "Delete failed."
                            );

                            JOptionPane.showMessageDialog(
                                    DoctorPanel.this,
                                    "Unable to delete doctor.\n\n"
                                            + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            updateButtonState();
                        }
                    }
                };

        worker.execute();
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

        if (doctorTable != null) {

            doctorTable.setBackground(
                    theme.getSurfaceColor()
            );

            doctorTable.setForeground(
                    theme.getTextColor()
            );

            doctorTable
                    .getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            doctorTable
                    .getTableHeader()
                    .setForeground(
                            theme.getTextColor()
                    );

            doctorTable
                    .getTableHeader()
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

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );
    }
}