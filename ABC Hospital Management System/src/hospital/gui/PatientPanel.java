package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.icons.AppIcon;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Patient;
import hospital.services.PatientService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class PatientPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JTable patientTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel patientCountLabel;
    private JLabel statusLabel;

    private final PatientService patientService;

    public PatientPanel() {
        patientService = new PatientService();

        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 25, 20, 25));

        initUI();

        ThemeManager.addThemeChangeListener(this);

        loadPatients();
    }

    private void initUI() {
        createTopSection();
        createTable();
    }

    private void createTopSection() {

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Patient Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel subtitleLabel = new JLabel(
                "Manage registered patients and their information."
        );
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        patientCountLabel = new JLabel("Patients: 0");
        patientCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(patientCountLabel);

        topPanel.add(titlePanel);
        topPanel.add(Box.createVerticalStrut(12));

        JPanel actionPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                8,
                0
        ));
        actionPanel.setOpaque(false);

        searchField = new AppTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Search by name, ID, phone..."
        );

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterPatients();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterPatients();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterPatients();
                    }
                }
        );

        addButton = new AppButton("ADD PATIENT");
        editButton = new AppButton("EDIT");
        deleteButton = new AppButton("DELETE");
        refreshButton = new AppButton("REFRESH");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        refreshButton.addActionListener(e -> loadPatients());

        actionPanel.add(searchField);
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(refreshButton);

        actionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topPanel.add(actionPanel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createTable() {

        String[] columns = {
                "ID",
                "First Name",
                "Last Name",
                "Gender",
                "Date of Birth",
                "Phone",
                "Blood Group",
                "Genotype"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);

        patientTable.setRowHeight(38);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patientTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        patientTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        patientTable.setAutoCreateRowSorter(true);

        patientTable.setShowGrid(false);
        patientTable.setIntercellSpacing(new Dimension(0, 0));

        patientTable.getSelectionModel().addListSelectionListener(e -> {

            boolean selected =
                    patientTable.getSelectedRow() != -1;

            editButton.setEnabled(selected);
            deleteButton.setEnabled(selected);

        });

        patientTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2 &&
                                patientTable.getSelectedRow() != -1) {

                            editPatient();
                        }
                    }
                }
        );

        configureColumns();

        JScrollPane scrollPane = new JScrollPane(patientTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(
                new BorderLayout()
        );

        bottomPanel.setOpaque(false);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        bottomPanel.add(
                statusLabel,
                BorderLayout.WEST
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }

    private void configureColumns() {

        patientTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(60);

        patientTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(130);

        patientTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(130);

        patientTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(70);

        patientTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(110);

        patientTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(130);

        patientTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);

        patientTable.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(90);
    }

    private void loadPatients() {

        statusLabel.setText("Loading patients...");

        addButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        refreshButton.setEnabled(false);

        SwingWorker<List<Patient>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected List<Patient> doInBackground() {
                        return patientService.getAllPatients();
                    }

                    @Override
                    protected void done() {

                        try {

                            List<Patient> patients = get();

                            tableModel.setRowCount(0);

                            for (Patient patient : patients) {

                                tableModel.addRow(
                                        new Object[]{
                                                patient.getPatientID(),
                                                patient.getFirstName(),
                                                patient.getLastName(),
                                                patient.getGender(),
                                                patient.getDateOfBirth(),
                                                patient.getPhone(),
                                                patient.getBloodGroup(),
                                                patient.getGenotype()
                                        }
                                );
                            }

                            patientCountLabel.setText(
                                    "Patients: " + patients.size()
                            );

                            statusLabel.setText(
                                    patients.size()
                                            + " patient(s) loaded."
                            );

                        } catch (Exception ex) {

                            statusLabel.setText(
                                    "Unable to load patients."
                            );

                            JOptionPane.showMessageDialog(
                                    PatientPanel.this,
                                    "Unable to load patients.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            addButton.setEnabled(true);
                            refreshButton.setEnabled(true);

                        }
                    }
                };

        worker.execute();
    }

    private void filterPatients() {

        if (sorter == null) {
            sorter = new TableRowSorter<>(tableModel);
            patientTable.setRowSorter(sorter);
        }

        String text = searchField.getText().trim();

        if (text.isEmpty()) {

            sorter.setRowFilter(null);

        } else {

            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)" + Pattern.quote(text)
                    )
            );
        }
    }

    private void addPatient() {

        PatientDialog dialog =
                new PatientDialog(
                        SwingUtilities.getWindowAncestor(this),
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadPatients();
        }
    }

    private void editPatient() {

        int selectedRow =
                patientTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                patientTable.convertRowIndexToModel(
                        selectedRow
                );

        int patientId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
                );

        Patient patient =
                patientService.getPatientById(
                        patientId
                );

        if (patient == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        PatientDialog dialog =
                new PatientDialog(
                        SwingUtilities.getWindowAncestor(this),
                        patient
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadPatients();
        }
    }

    private void deletePatient() {

        int selectedRow =
                patientTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                patientTable.convertRowIndexToModel(
                        selectedRow
                );

        int patientId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                modelRow,
                                0
                        ).toString()
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
                        "Are you sure you want to delete "
                                + firstName
                                + " "
                                + lastName
                                + "?",
                        "Delete Patient",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted =
                patientService.deletePatient(
                        patientId
                );

        if (deleted) {

            statusLabel.setText(
                    "Patient deleted successfully."
            );

            loadPatients();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete patient.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void themeChanged(Theme newTheme) {

        if (newTheme == null) {
            return;
        }

        setBackground(
                newTheme.getBackgroundColor()
        );

        patientTable.setBackground(
                newTheme.getSurfaceColor()
        );

        patientTable.setForeground(
                newTheme.getTextColor()
        );

        patientTable.getTableHeader().setBackground(
                newTheme.getSurfaceColor()
        );

        patientTable.getTableHeader().setForeground(
                newTheme.getTextColor()
        );

        repaint();
    }

    @Override
    public void removeNotify() {

        ThemeManager.removeThemeChangeListener(this);

        super.removeNotify();
    }
}