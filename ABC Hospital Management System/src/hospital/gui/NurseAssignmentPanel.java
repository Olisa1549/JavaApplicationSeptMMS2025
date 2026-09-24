package hospital.gui;

import hospital.dao.NurseAssignmentDAO;
import hospital.models.NurseAssignment;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NurseAssignmentPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private final NurseAssignmentDAO assignmentDAO;

    private JTable assignmentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel countLabel;
    private JLabel statusLabel;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    public NurseAssignmentPanel() {

        assignmentDAO = new NurseAssignmentDAO();

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());

        loadAssignments();
    }

    private void initUI() {

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Nurse Assignments");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel(
                "Manage nurse assignments to patients"
        );
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitleLabel);

        topPanel.add(titlePanel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        addButton = new AppButton("Add Assignment");
        editButton = new AppButton("Edit");
        deleteButton = new AppButton("Delete");
        refreshButton = new AppButton("Refresh");

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(refreshButton);

        topPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel searchPanel =
                new JPanel(new BorderLayout(10, 0));

        searchField = new AppTextField();

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        countLabel = new JLabel("0 assignments");

        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        searchPanel.add(
                countLabel,
                BorderLayout.EAST
        );

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Assignment ID",
                                "Nurse",
                                "Patient",
                                "Assignment Date",
                                "Details"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        assignmentTable =
                new JTable(tableModel);

        assignmentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        assignmentTable.setRowHeight(34);
        assignmentTable.setAutoCreateRowSorter(true);

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        assignmentTable.setRowSorter(sorter);

        assignmentTable.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        assignmentTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && SwingUtilities
                                .isLeftMouseButton(e)) {

                            editSelected();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        assignmentTable
                );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        statusLabel =
                new JLabel("Ready");

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        add(
                statusLabel,
                BorderLayout.SOUTH
        );

        addButton.addActionListener(
                e -> openDialog(null)
        );

        editButton.addActionListener(
                e -> editSelected()
        );

        deleteButton.addActionListener(
                e -> deleteSelected()
        );

        refreshButton.addActionListener(
                e -> loadAssignments()
        );

        searchField.getDocument()
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

                                updateCount();
                            }

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e) {
                                filter();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e) {
                                filter();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e) {
                                filter();
                            }
                        }
                );
    }

    private void loadAssignments() {

        statusLabel.setText(
                "Loading assignments..."
        );

        SwingWorker<List<NurseAssignment>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected List<NurseAssignment>
                    doInBackground()
                            throws Exception {

                        return assignmentDAO
                                .findAllNurseAssignments();
                    }

                    @Override
                    protected void done() {

                        try {

                            List<NurseAssignment>
                                    assignments = get();

                            tableModel.setRowCount(0);

                            for (
                                    NurseAssignment assignment
                                    : assignments) {

                                tableModel.addRow(
                                        new Object[]{
                                                assignment.getId(),
                                                getNurseDisplayName(
                                                        assignment.getNurse()
                                                ),
                                                getPatientDisplayName(
                                                        assignment.getPatient()
                                                ),
                                                formatDate(
                                                        assignment
                                                                .getAssignmentDate()
                                                ),
                                                safe(
                                                        assignment.getNotes()
                                                )
                                        }
                                );
                            }

                            updateCount();

                            statusLabel.setText(
                                    assignments.size()
                                            + " assignment(s) loaded"
                            );

                        } catch (Exception ex) {

                            statusLabel.setText(
                                    "Failed to load assignments"
                            );

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentPanel.this,
                                    "Unable to load nurse assignments.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    private void openDialog(
            NurseAssignment assignment) {

        Window owner =
                SwingUtilities
                        .getWindowAncestor(this);

        NurseAssignmentDialog dialog =
                new NurseAssignmentDialog(
                        owner,
                        assignment
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadAssignments();
        }
    }

    private void editSelected() {

        int viewRow =
                assignmentTable.getSelectedRow();

        if (viewRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a nurse assignment first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                assignmentTable
                        .convertRowIndexToModel(
                                viewRow
                        );

        int assignmentId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        modelRow,
                                        0
                                )
                                .toString()
                );

        statusLabel.setText(
                "Loading assignment..."
        );

        SwingWorker<NurseAssignment, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected NurseAssignment
                    doInBackground()
                            throws Exception {

                        return assignmentDAO
                                .findNurseAssignmentById(
                                        assignmentId
                                );
                    }

                    @Override
                    protected void done() {

                        try {

                            NurseAssignment assignment =
                                    get();

                            if (assignment == null) {

                                JOptionPane.showMessageDialog(
                                        NurseAssignmentPanel.this,
                                        "Assignment could not be found.",
                                        "Not Found",
                                        JOptionPane.WARNING_MESSAGE
                                );

                                return;
                            }

                            openDialog(assignment);

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentPanel.this,
                                    "Unable to load the selected assignment.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            statusLabel.setText(
                                    "Ready"
                            );
                        }
                    }
                };

        worker.execute();
    }

    private void deleteSelected() {

        int viewRow =
                assignmentTable.getSelectedRow();

        if (viewRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a nurse assignment first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                assignmentTable
                        .convertRowIndexToModel(
                                viewRow
                        );

        int assignmentId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        modelRow,
                                        0
                                )
                                .toString()
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this nurse assignment?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        statusLabel.setText(
                "Deleting assignment..."
        );

        SwingWorker<Void, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground()
                            throws Exception {

                        assignmentDAO
                                .deleteNurseAssignment(
                                        assignmentId
                                );

                        return null;
                    }

                    @Override
                    protected void done() {

                        try {

                            get();

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentPanel.this,
                                    "Nurse assignment deleted successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            loadAssignments();

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentPanel.this,
                                    "Unable to delete the assignment.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            statusLabel.setText(
                                    "Delete failed"
                            );
                        }
                    }
                };

        worker.execute();
    }

    private String getNurseDisplayName(
            hospital.models.Nurse nurse) {

        if (nurse == null) {
            return "—";
        }

        String first =
                safe(nurse.getFirstName());

        String last =
                safe(nurse.getLastName());

        String full =
                (first + " " + last).trim();

        if (!full.isEmpty()) {
            return full;
        }

        return "Staff ID: "
                + nurse.getStaffID();
    }

    private String getPatientDisplayName(
            hospital.models.Patient patient) {

        if (patient == null) {
            return "—";
        }

        String first =
                safe(patient.getFirstName());

        String last =
                safe(patient.getLastName());

        String full =
                (first + " " + last).trim();

        if (!full.isEmpty()) {
            return full;
        }

        return "Patient ID: "
                + patient.getId();
    }

    private String formatDate(
            LocalDateTime dateTime) {

        if (dateTime == null) {
            return "—";
        }

        return dateTime.format(
                dateFormatter
        );
    }

    private String safe(String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }

    private void updateCount() {

        if (countLabel == null
                || tableModel == null) {
            return;
        }

        int count =
                assignmentTable.getRowCount();

        countLabel.setText(
                count
                        + (count == 1
                        ? " assignment"
                        : " assignments")
        );
    }

    @Override
    public void themeChanged(
            Theme newTheme) {

        applyTheme(newTheme);
    }

    private void applyTheme(
            Theme theme) {

        if (theme == null) {
            return;
        }

        setBackground(
                theme.getBackgroundColor()
        );

        if (assignmentTable != null) {

            assignmentTable.setBackground(
                    theme.getSurfaceColor()
            );

            assignmentTable.setForeground(
                    theme.getTextColor()
            );

            assignmentTable
                    .getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            assignmentTable
                    .getTableHeader()
                    .setForeground(
                            theme.getTextColor()
                    );
        }

        repaint();
    }

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );
    }
}