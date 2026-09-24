package hospital.gui;

import hospital.dao.NurseAssignmentDAO;
import hospital.dao.NurseDAO;
import hospital.dao.PatientDAO;
import hospital.models.Nurse;
import hospital.models.NurseAssignment;
import hospital.models.Patient;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppComboBox;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class NurseAssignmentDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private AppTextField dateField;
    private AppTextField detailsField;

    private AppComboBox<NurseItem> nurseBox;
    private AppComboBox<PatientItem> patientBox;

    private AppButton saveButton;
    private AppButton cancelButton;

    private NurseAssignment assignment;

    private boolean saved = false;

    private final NurseDAO nurseDAO;
    private final PatientDAO patientDAO;
    private final NurseAssignmentDAO assignmentDAO;

    public NurseAssignmentDialog(
            Window owner,
            NurseAssignment assignment) {

        super(
                owner,
                assignment == null
                        ? "Add Nurse Assignment"
                        : "Edit Nurse Assignment",
                ModalityType.APPLICATION_MODAL
        );

        this.assignment = assignment;

        nurseDAO = new NurseDAO();
        patientDAO = new PatientDAO();
        assignmentDAO = new NurseAssignmentDAO();

        setSize(820, 610);
        setLocationRelativeTo(owner);
        setResizable(false);

        initUI();

        loadNurses();
        loadPatients();

        if (assignment != null) {
            populateAssignment();
        }

        ThemeManager.addThemeChangeListener(this);
        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    private void initUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        root.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        25,
                        12,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        assignment == null
                                ? "Add Nurse Assignment"
                                : "Edit Nurse Assignment"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Assign a nurse to a patient"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        JPanel heading =
                new JPanel();

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        heading.add(title);

        heading.add(
                Box.createVerticalStrut(3)
        );

        heading.add(subtitle);

        root.add(
                heading,
                BorderLayout.NORTH
        );

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        0,
                        10,
                        0
                )
        );

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

        addLabel(
                form,
                gbc,
                "Nurse",
                0,
                0
        );

        nurseBox =
                new AppComboBox<>();

        nurseBox.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addComponent(
                form,
                gbc,
                nurseBox,
                1,
                0
        );

        addLabel(
                form,
                gbc,
                "Patient",
                0,
                1
        );

        patientBox =
                new AppComboBox<>();

        patientBox.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addComponent(
                form,
                gbc,
                patientBox,
                1,
                1
        );

        addLabel(
                form,
                gbc,
                "Assignment Date",
                0,
                2
        );

        dateField =
                new AppTextField();

        dateField.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addComponent(
                form,
                gbc,
                dateField,
                1,
                2
        );

        addLabel(
                form,
                gbc,
                "Details / Notes",
                0,
                3
        );

        detailsField =
                new AppTextField();

        detailsField.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        addComponent(
                form,
                gbc,
                detailsField,
                1,
                3
        );

        root.add(
                form,
                BorderLayout.CENTER
        );

        JPanel footer =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        saveButton =
                new AppButton(
                        assignment == null
                                ? "Save Assignment"
                                : "Update Assignment"
                );

        cancelButton =
                new AppButton(
                        "Cancel"
                );

        footer.add(cancelButton);
        footer.add(saveButton);

        root.add(
                footer,
                BorderLayout.SOUTH
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveAssignment()
        );

        setContentPane(root);
    }

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int x,
            int y) {

        GridBagConstraints c =
                (GridBagConstraints) gbc.clone();

        c.gridx = x;
        c.gridy = y;
        c.weightx = 0;

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        panel.add(
                label,
                c
        );
    }

    private void addComponent(
            JPanel panel,
            GridBagConstraints gbc,
            Component component,
            int x,
            int y) {

        GridBagConstraints c =
                (GridBagConstraints) gbc.clone();

        c.gridx = x;
        c.gridy = y;
        c.weightx = 1;

        panel.add(
                component,
                c
        );
    }

    private void loadNurses() {

        try {

            List<Nurse> nurses =
                    nurseDAO.findAllNurses();

            nurseBox.removeAllItems();

            for (Nurse nurse : nurses) {

                nurseBox.addItem(
                        new NurseItem(nurse)
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load nurses.\n\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadPatients() {

        try {

            List<Patient> patients =
                    patientDAO.findAllPatient();

            patientBox.removeAllItems();

            for (Patient patient : patients) {

                patientBox.addItem(
                        new PatientItem(patient)
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients.\n\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void populateAssignment() {

        if (assignment == null) {
            return;
        }

        if (assignment.getAssignmentDate() != null) {

            dateField.setText(
                    assignment
                            .getAssignmentDate()
                            .toLocalDate()
                            .toString()
            );
        }

        detailsField.setText(
                assignment.getNotes() == null
                        ? ""
                        : assignment.getNotes()
        );

        if (assignment.getNurse() != null) {

            int staffId =
                    assignment
                            .getNurse()
                            .getStaffID();

            for (int i = 0;
                 i < nurseBox.getItemCount();
                 i++) {

                NurseItem item =
                        nurseBox.getItemAt(i);

                if (item.getNurse()
                        .getStaffID()
                        == staffId) {

                    nurseBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        if (assignment.getPatient() != null) {

            int patientId =
                    assignment
                            .getPatient()
                            .getId();

            for (int i = 0;
                 i < patientBox.getItemCount();
                 i++) {

                PatientItem item =
                        patientBox.getItemAt(i);

                if (item.getPatient()
                        .getId()
                        == patientId) {

                    patientBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void saveAssignment() {

        NurseItem nurseItem =
                (NurseItem)
                        nurseBox.getSelectedItem();

        PatientItem patientItem =
                (PatientItem)
                        patientBox.getSelectedItem();

        if (nurseItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a nurse.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (patientItem == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String dateText =
                dateField.getText().trim();

        if (dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an assignment date.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        LocalDate date;

        try {

            date =
                    LocalDate.parse(
                            dateText
                    );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String details =
                detailsField.getText().trim();

        NurseAssignment assignmentToSave =
                assignment == null
                        ? new NurseAssignment()
                        : assignment;

        assignmentToSave.setNurse(
                nurseItem.getNurse()
        );

        assignmentToSave.setPatient(
                patientItem.getPatient()
        );

        assignmentToSave.setAssignmentDate(
                date.atStartOfDay()
        );

        assignmentToSave.setNotes(
                details
        );

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        SwingWorker<Void, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground()
                            throws Exception {

                        if (assignmentToSave.getId() == 0) {

                            assignmentDAO
                                    .addNurseAssignment(
                                            assignmentToSave
                                    );

                        } else {

                            assignmentDAO
                                    .updateNurseAssignment(
                                            assignmentToSave
                                    );
                        }

                        return null;
                    }

                    @Override
                    protected void done() {

                        try {

                            get();

                            saved = true;

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentDialog.this,
                                    assignmentToSave.getId() == 0
                                            ? "Nurse assignment saved successfully."
                                            : "Nurse assignment updated successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dispose();

                        } catch (Exception ex) {

                            saveButton.setEnabled(true);
                            cancelButton.setEnabled(true);

                            Throwable cause =
                                    ex.getCause() != null
                                            ? ex.getCause()
                                            : ex;

                            JOptionPane.showMessageDialog(
                                    NurseAssignmentDialog.this,
                                    "Unable to save nurse assignment.\n\n"
                                            + cause.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    public boolean isSaved() {
        return saved;
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

        getContentPane().setBackground(
                theme.getBackgroundColor()
        );

        if (saveButton != null) {

            saveButton.setBackground(
                    theme.getPrimaryColor()
            );

            saveButton.setForeground(
                    Color.WHITE
            );
        }

        if (cancelButton != null) {

            cancelButton.setBackground(
                    theme.getSurfaceColor()
            );

            cancelButton.setForeground(
                    theme.getTextColor()
            );
        }

        repaint();
    }

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.dispose();
    }

    private static class NurseItem {

        private final Nurse nurse;

        NurseItem(Nurse nurse) {
            this.nurse = nurse;
        }

        Nurse getNurse() {
            return nurse;
        }

        @Override
        public String toString() {

            String first =
                    nurse.getFirstName() == null
                            ? ""
                            : nurse.getFirstName();

            String last =
                    nurse.getLastName() == null
                            ? ""
                            : nurse.getLastName();

            String name =
                    (first + " " + last).trim();

            if (name.isEmpty()) {
                name = "Nurse";
            }

            return name
                    + " (Staff ID: "
                    + nurse.getStaffID()
                    + ")";
        }
    }

    private static class PatientItem {

        private final Patient patient;

        PatientItem(Patient patient) {
            this.patient = patient;
        }

        Patient getPatient() {
            return patient;
        }

        @Override
        public String toString() {

            String first =
                    patient.getFirstName() == null
                            ? ""
                            : patient.getFirstName();

            String last =
                    patient.getLastName() == null
                            ? ""
                            : patient.getLastName();

            String name =
                    (first + " " + last).trim();

            if (name.isEmpty()) {
                name = "Patient";
            }

            return name
                    + " (Patient ID: "
                    + patient.getId()
                    + ")";
        }
    }
}