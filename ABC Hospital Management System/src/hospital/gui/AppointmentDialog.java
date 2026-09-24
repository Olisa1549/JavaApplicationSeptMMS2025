package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Appointment;
import hospital.models.Doctor;
import hospital.models.Patient;
import hospital.services.AppointmentService;
import hospital.services.DoctorService;
import hospital.services.PatientService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AppointmentDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private JComboBox<PatientItem> patientBox;
    private JComboBox<DoctorItem> doctorBox;

    private AppTextField dateField;
    private AppTextField timeField;
    private AppTextField reasonField;

    private JComboBox<String> statusBox;

    private JTextArea notesArea;

    private AppButton saveButton;
    private AppButton cancelButton;

    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel statusLabel;

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    private final Appointment existingAppointment;

    private boolean saved = false;

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public AppointmentDialog(
            Window owner,
            Appointment appointment
    ) {

        super(
                owner,
                appointment == null
                        ? "Add Appointment"
                        : "Edit Appointment",
                ModalityType.APPLICATION_MODAL
        );

        this.appointmentService =
                new AppointmentService();

        this.patientService =
                new PatientService();

        this.doctorService =
                new DoctorService();

        this.existingAppointment =
                appointment;

        setDefaultCloseOperation(
                DISPOSE_ON_CLOSE
        );

        setSize(
                820,
                650
        );

        setLocationRelativeTo(owner);
        setResizable(false);

        initUI();

        ThemeManager.addThemeChangeListener(
                this
        );

        loadPatients();
        loadDoctors();

        if (existingAppointment != null) {

            populateAppointment(
                    existingAppointment
            );
        }

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // INITIALIZE UI
    // =========================================================

    private void initUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        12,
                        25
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        titleLabel =
                new JLabel(
                        existingAppointment == null
                                ? "Add Appointment"
                                : "Edit Appointment"
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
                        existingAppointment == null
                                ? "Schedule a new patient appointment."
                                : "Update the appointment information below."
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
                Box.createVerticalStrut(3)
        );

        header.add(subtitleLabel);

        root.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);

        form.setBorder(
                new EmptyBorder(
                        8,
                        0,
                        5,
                        0
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        3,
                        6,
                        3,
                        6
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        int row = 0;

        // =====================================================
        // APPOINTMENT DETAILS
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Appointment Details"
        );

        patientBox =
                new JComboBox<>();

        doctorBox =
                new JComboBox<>();

        styleComboBox(patientBox);
        styleComboBox(doctorBox);

        row = addField(
                form,
                gbc,
                row,
                "Patient",
                patientBox,
                null
        );

        row = addField(
                form,
                gbc,
                row,
                "Doctor",
                doctorBox,
                null
        );

        // =====================================================
        // DATE AND TIME
        // =====================================================

        dateField =
                new AppTextField();

        timeField =
                new AppTextField();

        dateField.putClientProperty(
                "JTextField.placeholderText",
                "YYYY-MM-DD"
        );

        timeField.putClientProperty(
                "JTextField.placeholderText",
                "HH:MM"
        );

        row = addTwoFields(
                form,
                gbc,
                row,
                "Date",
                dateField,
                "Time",
                timeField
        );

        // =====================================================
        // APPOINTMENT INFORMATION
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Appointment Information"
        );

        reasonField =
                new AppTextField();

        statusBox =
                new JComboBox<>(
                        new String[]{
                                "Scheduled",
                                "Completed",
                                "Cancelled",
                                "No Show"
                        }
                );

        styleComboBox(statusBox);

        row = addField(
                form,
                gbc,
                row,
                "Reason",
                reasonField,
                "Reason for appointment"
        );

        row = addField(
                form,
                gbc,
                row,
                "Status",
                statusBox,
                null
        );

        // =====================================================
        // NOTES
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Notes"
        );

        notesArea =
                new JTextArea(
                        5,
                        20
                );

        notesArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        notesArea.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        JScrollPane notesScroll =
                new JScrollPane(
                        notesArea
                );

        notesScroll.setPreferredSize(
                new Dimension(
                        280,
                        110
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        form.add(
                createLabel("Notes"),
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;

        form.add(
                notesScroll,
                gbc
        );

        JScrollPane formScroll =
                new JScrollPane(form);

        formScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        formScroll.getVerticalScrollBar()
                .setUnitIncrement(14);

        root.add(
                formScroll,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

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

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        cancelButton =
                new AppButton(
                        "CANCEL"
                );

        saveButton =
                new AppButton(
                        existingAppointment == null
                                ? "SAVE APPOINTMENT"
                                : "UPDATE APPOINTMENT"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveAppointment()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        footer.add(
                statusLabel,
                BorderLayout.WEST
        );

        footer.add(
                buttonPanel,
                BorderLayout.EAST
        );

        root.add(
                footer,
                BorderLayout.SOUTH
        );

        setContentPane(root);

        getRootPane().setDefaultButton(
                saveButton
        );
    }

    // =========================================================
    // ADD SECTION
    // =========================================================

    private int addSection(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String title
    ) {

        JLabel label =
                new JLabel(title);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.gridwidth = 2;
        gbc.weightx = 1;

        gbc.insets =
                new Insets(
                        10,
                        6,
                        3,
                        6
                );

        panel.add(
                label,
                gbc
        );

        gbc.insets =
                new Insets(
                        3,
                        6,
                        3,
                        6
                );

        return row + 1;
    }

    // =========================================================
    // ADD FIELD
    // =========================================================

    private int addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component,
            String placeholder
    ) {

        JLabel label =
                createLabel(labelText);

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        gbc.anchor =
                GridBagConstraints.CENTER;

        panel.add(
                label,
                gbc
        );

        if (placeholder != null) {

            component.putClientProperty(
                    "JTextField.placeholderText",
                    placeholder
            );
        }

        component.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        panel.add(
                component,
                gbc
        );

        return row + 1;
    }

    // =========================================================
    // TWO FIELDS
    // =========================================================

    private int addTwoFields(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label1,
            Component field1,
            String label2,
            Component field2
    ) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.12;

        panel.add(
                createLabel(label1),
                gbc
        );

        field1.setPreferredSize(
                new Dimension(
                        180,
                        36
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 0.38;

        panel.add(
                field1,
                gbc
        );

        gbc.gridx = 2;
        gbc.weightx = 0.12;

        panel.add(
                createLabel(label2),
                gbc
        );

        field2.setPreferredSize(
                new Dimension(
                        180,
                        36
                )
        );

        gbc.gridx = 3;
        gbc.weightx = 0.38;

        panel.add(
                field2,
                gbc
        );

        return row + 1;
    }

    // =========================================================
    // COMBO BOX
    // =========================================================

    private void styleComboBox(
            JComboBox<?> comboBox
    ) {

        comboBox.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        return label;
    }

    // =========================================================
    // LOAD PATIENTS
    // =========================================================

    private void loadPatients() {

        patientBox.removeAllItems();

        try {

            List<Patient> patients =
                    patientService.getAllPatients();

            if (patients != null) {

                for (Patient patient : patients) {

                    patientBox.addItem(
                            new PatientItem(patient)
                    );
                }
            }

        } catch (Exception ex) {

            statusLabel.setText(
                    "Unable to load patients."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients.\n\n"
                            + ex.getMessage(),
                    "Patient Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD DOCTORS
    // =========================================================

    private void loadDoctors() {

        doctorBox.removeAllItems();

        try {

            List<Doctor> doctors =
                    doctorService.getAllDoctors();

            if (doctors != null) {

                for (Doctor doctor : doctors) {

                    doctorBox.addItem(
                            new DoctorItem(doctor)
                    );
                }
            }

        } catch (Exception ex) {

            statusLabel.setText(
                    "Unable to load doctors."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors.\n\n"
                            + ex.getMessage(),
                    "Doctor Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // POPULATE EDIT
    // =========================================================

    private void populateAppointment(
            Appointment appointment
    ) {

        selectPatient(
                appointment.getPatient()
        );

        selectDoctor(
                appointment.getDoctor()
        );

        if (appointment.getAppointmentDate()
                != null) {

            LocalDateTime dateTime =
                    appointment.getAppointmentDate();

            dateField.setText(
                    dateTime
                            .toLocalDate()
                            .toString()
            );

            timeField.setText(
                    dateTime
                            .toLocalTime()
                            .format(
                                    timeFormatter
                            )
            );
        }

        reasonField.setText(
                safe(
                        appointment.getReason()
                )
        );

        String status =
                safe(
                        appointment.getStatus()
                );

        if (!status.isEmpty()) {

            statusBox.setSelectedItem(
                    status
            );
        }

        notesArea.setText(
                safe(
                        appointment.getNotes()
                )
        );
    }

    private void selectPatient(
            Patient patient
    ) {

        if (patient == null) {
            return;
        }

        for (
                int i = 0;
                i < patientBox.getItemCount();
                i++
        ) {

            PatientItem item =
                    patientBox.getItemAt(i);

            if (item != null
                    && item.getPatient()
                    .getPatientID()
                    == patient.getPatientID()) {

                patientBox.setSelectedIndex(i);

                return;
            }
        }
    }

    private void selectDoctor(
            Doctor doctor
    ) {

        if (doctor == null) {
            return;
        }

        for (
                int i = 0;
                i < doctorBox.getItemCount();
                i++
        ) {

            DoctorItem item =
                    doctorBox.getItemAt(i);

            if (item != null
                    && item.getDoctor()
                    .getStaffID()
                    == doctor.getStaffID()) {

                doctorBox.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================================================
    // SAVE APPOINTMENT
    // =========================================================

    private void saveAppointment() {

        clearValidation();

        PatientItem patientItem =
                (PatientItem)
                        patientBox.getSelectedItem();

        DoctorItem doctorItem =
                (DoctorItem)
                        doctorBox.getSelectedItem();

        String dateText =
                dateField.getText().trim();

        String timeText =
                timeField.getText().trim();

        String reason =
                reasonField.getText().trim();

        String status =
                String.valueOf(
                        statusBox.getSelectedItem()
                );

        String notes =
                notesArea.getText().trim();

        if (patientItem == null) {

            statusLabel.setText(
                    "Please select a patient."
            );

            patientBox.requestFocus();

            return;
        }

        if (doctorItem == null) {

            statusLabel.setText(
                    "Please select a doctor."
            );

            doctorBox.requestFocus();

            return;
        }

        if (dateText.isEmpty()) {

            showValidation(
                    dateField,
                    "Appointment date is required."
            );

            return;
        }

        if (timeText.isEmpty()) {

            showValidation(
                    timeField,
                    "Appointment time is required."
            );

            return;
        }

        if (reason.isEmpty()) {

            showValidation(
                    reasonField,
                    "Appointment reason is required."
            );

            return;
        }

        LocalDate date;

        LocalTime time;

        try {

            date =
                    LocalDate.parse(
                            dateText
                    );

        } catch (DateTimeParseException ex) {

            showValidation(
                    dateField,
                    "Use date format YYYY-MM-DD."
            );

            return;
        }

        try {

            time =
                    LocalTime.parse(
                            timeText,
                            timeFormatter
                    );

        } catch (DateTimeParseException ex) {

            showValidation(
                    timeField,
                    "Use time format HH:MM."
            );

            return;
        }

        LocalDateTime appointmentDate =
                LocalDateTime.of(
                        date,
                        time
                );

        Appointment appointment;

        if (existingAppointment == null) {

            appointment =
                    new Appointment();

        } else {

            appointment =
                    existingAppointment;
        }

        appointment.setPatient(
                patientItem.getPatient()
        );

        appointment.setDoctor(
                doctorItem.getDoctor()
        );

        appointment.setAppointmentDate(
                appointmentDate
        );

        appointment.setReason(
                reason
        );

        appointment.setStatus(
                status
        );

        appointment.setNotes(
                notes
        );

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        saveButton.setText(
                existingAppointment == null
                        ? "SAVING..."
                        : "UPDATING..."
        );

        statusLabel.setText(
                existingAppointment == null
                        ? "Saving appointment..."
                        : "Updating appointment..."
        );

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        if (existingAppointment == null) {

                            return appointmentService
                                    .addAppointment(
                                            appointment
                                    );

                        } else {

                            return appointmentService
                                    .updateAppointment(
                                            appointment
                                    );
                        }
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean success = get();

                            if (success) {

                                saved = true;

                                statusLabel.setText(
                                        existingAppointment == null
                                                ? "Appointment saved successfully."
                                                : "Appointment updated successfully."
                                );

                                Timer timer =
                                        new Timer(
                                                350,
                                                e -> dispose()
                                        );

                                timer.setRepeats(false);
                                timer.start();

                            } else {

                                statusLabel.setText(
                                        "Unable to save appointment."
                                );

                                JOptionPane.showMessageDialog(
                                        AppointmentDialog.this,
                                        existingAppointment == null
                                                ? "Unable to create appointment."
                                                : "Unable to update appointment.",
                                        "Save Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );

                                restoreButtons();
                            }

                        } catch (Exception ex) {

                            statusLabel.setText(
                                    "An error occurred."
                            );

                            JOptionPane.showMessageDialog(
                                    AppointmentDialog.this,
                                    "An error occurred while saving the appointment.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            restoreButtons();
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void showValidation(
            AppTextField field,
            String message
    ) {

        field.setValidState(false);

        statusLabel.setText(
                message
        );

        field.requestFocus();
    }

    private void clearValidation() {

        dateField.clearValidation();
        timeField.clearValidation();
        reasonField.clearValidation();

        statusLabel.setText("");
    }

    private void restoreButtons() {

        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        saveButton.setText(
                existingAppointment == null
                        ? "SAVE APPOINTMENT"
                        : "UPDATE APPOINTMENT"
        );
    }

    // =========================================================
    // UTILITIES
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

        if (titleLabel != null) {

            titleLabel.setForeground(
                    theme.getTextColor()
            );
        }

        if (subtitleLabel != null) {

            subtitleLabel.setForeground(
                    theme.getSecondaryTextColor()
            );
        }

        if (statusLabel != null) {

            statusLabel.setForeground(
                    theme.getSecondaryTextColor()
            );
        }

        if (notesArea != null) {

            notesArea.setBackground(
                    theme.getSurfaceColor()
            );

            notesArea.setForeground(
                    theme.getTextColor()
            );
        }

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
    // PATIENT ITEM
    // =========================================================

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

            if (patient == null) {
                return "";
            }

            return patient.getPatientID()
                    + " - "
                    + safeStatic(
                    patient.getFirstName()
            )
                    + " "
                    + safeStatic(
                    patient.getLastName()
            );
        }
    }

    // =========================================================
    // DOCTOR ITEM
    // =========================================================

    private static class DoctorItem {

        private final Doctor doctor;

        DoctorItem(Doctor doctor) {

            this.doctor = doctor;
        }

        Doctor getDoctor() {

            return doctor;
        }

        @Override
        public String toString() {

            if (doctor == null) {
                return "";
            }

            return doctor.getStaffID()
                    + " - Dr. "
                    + safeStatic(
                    doctor.getFirstName()
            )
                    + " "
                    + safeStatic(
                    doctor.getLastName()
            );
        }
    }

    private static String safeStatic(
            String value
    ) {

        return value == null
                ? ""
                : value;
    }
}