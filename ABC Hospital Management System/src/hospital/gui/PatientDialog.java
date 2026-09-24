package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Patient;
import hospital.services.PatientService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class PatientDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private AppTextField firstNameField;
    private AppTextField lastNameField;
    private AppTextField dateOfBirthField;

    private JComboBox<String> genderBox;

    private AppTextField phoneField;
    private AppTextField emailField;
    private AppTextField streetField;
    private AppTextField cityField;
    private AppTextField countryField;

    private JComboBox<String> bloodGroupBox;
    private JComboBox<String> genotypeBox;

    private AppTextField allergiesField;

    private AppTextField emergencyContactField;
    private AppTextField emergencyPhoneField;

    private AppButton saveButton;
    private AppButton cancelButton;

    private final PatientService patientService;
    private final Patient patient;

    private boolean saved = false;

    public PatientDialog(Window owner, Patient patient) {

        super(
                owner,
                patient == null ? "Register Patient" : "Edit Patient",
                ModalityType.APPLICATION_MODAL
        );

        this.patient = patient;
        this.patientService = new PatientService();

        setSize(820, 610);
        setLocationRelativeTo(owner);
        setResizable(false);

        initUI();

        if (patient != null) {
            populatePatient();
        }

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
    }

    private void initUI() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(15, 25, 12, 25));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(
                patient == null
                        ? "Register New Patient"
                        : "Edit Patient"
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 24)
        );

        JLabel subtitle = new JLabel(
                patient == null
                        ? "Enter the patient's information below."
                        : "Update the patient's information below."
        );

        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        header.add(title);
        header.add(Box.createVerticalStrut(3));
        header.add(subtitle);

        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(
                new EmptyBorder(8, 0, 5, 0)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(3, 6, 3, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int row = 0;

        row = addSection(
                form,
                gbc,
                row,
                "Personal Information"
        );

        firstNameField = new AppTextField();
        lastNameField = new AppTextField();

        genderBox = new JComboBox<>(
                new String[]{
                        "M",
                        "F"
                }
        );

        dateOfBirthField = new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "First Name",
                firstNameField,
                "Enter first name"
        );

        row = addField(
                form,
                gbc,
                row,
                "Last Name",
                lastNameField,
                "Enter last name"
        );

        row = addField(
                form,
                gbc,
                row,
                "Gender",
                genderBox,
                null
        );

        row = addField(
                form,
                gbc,
                row,
                "Date of Birth",
                dateOfBirthField,
                "YYYY-MM-DD"
        );

        row = addSection(
                form,
                gbc,
                row,
                "Contact Information"
        );

        phoneField = new AppTextField();
        emailField = new AppTextField();
        streetField = new AppTextField();
        cityField = new AppTextField();
        countryField = new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "Phone",
                phoneField,
                "Phone number"
        );

        row = addField(
                form,
                gbc,
                row,
                "Email",
                emailField,
                "Email address"
        );

        row = addField(
                form,
                gbc,
                row,
                "Street",
                streetField,
                "Street address"
        );

        row = addField(
                form,
                gbc,
                row,
                "City",
                cityField,
                "City"
        );

        row = addField(
                form,
                gbc,
                row,
                "Country",
                countryField,
                "Country"
        );

        row = addSection(
                form,
                gbc,
                row,
                "Medical Information"
        );

        bloodGroupBox = new JComboBox<>(
                new String[]{
                        "",
                        "A+",
                        "A-",
                        "B+",
                        "B-",
                        "AB+",
                        "AB-",
                        "O+",
                        "O-"
                }
        );

        genotypeBox = new JComboBox<>(
                new String[]{
                        "",
                        "AA",
                        "AS",
                        "AC",
                        "SS",
                        "SC"
                }
        );

        allergiesField = new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "Blood Group",
                bloodGroupBox,
                null
        );

        row = addField(
                form,
                gbc,
                row,
                "Genotype",
                genotypeBox,
                null
        );

        row = addField(
                form,
                gbc,
                row,
                "Allergies",
                allergiesField,
                "Known allergies"
        );

        row = addSection(
                form,
                gbc,
                row,
                "Emergency Contact"
        );

        emergencyContactField = new AppTextField();
        emergencyPhoneField = new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "Contact Name",
                emergencyContactField,
                "Emergency contact name"
        );

        row = addField(
                form,
                gbc,
                row,
                "Contact Phone",
                emergencyPhoneField,
                "Emergency contact phone"
        );

        JScrollPane scrollPane = new JScrollPane(form);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        root.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        buttonPanel.setOpaque(false);

        cancelButton = new AppButton("CANCEL");

        saveButton = new AppButton(
                patient == null
                        ? "SAVE PATIENT"
                        : "UPDATE PATIENT"
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> savePatient()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        root.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(root);
    }

    private int addSection(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String title
    ) {

        JLabel label = new JLabel(title);

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

        gbc.insets = new Insets(
                10,
                6,
                3,
                6
        );

        panel.add(label, gbc);

        gbc.insets = new Insets(
                3,
                6,
                3,
                6
        );

        return row + 1;
    }

    private int addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component,
            String placeholder
    ) {

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        panel.add(label, gbc);

        if (placeholder != null) {
            component.putClientProperty(
                    "JTextField.placeholderText",
                    placeholder
            );
        }

        component.setPreferredSize(
                new Dimension(280, 36)
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        panel.add(component, gbc);

        return row + 1;
    }

    private void populatePatient() {

        firstNameField.setText(
                safe(patient.getFirstName())
        );

        lastNameField.setText(
                safe(patient.getLastName())
        );

        genderBox.setSelectedItem(
                String.valueOf(
                        patient.getGender()
                )
        );

        if (patient.getDateOfBirth() != null) {
            dateOfBirthField.setText(
                    patient.getDateOfBirth().toString()
            );
        }

        phoneField.setText(
                safe(patient.getPhone())
        );

        emailField.setText(
                safe(patient.getEmail())
        );

        streetField.setText(
                safe(patient.getStreet())
        );

        cityField.setText(
                safe(patient.getCity())
        );

        countryField.setText(
                safe(patient.getCountry())
        );

        bloodGroupBox.setSelectedItem(
                safe(patient.getBloodGroup())
        );

        genotypeBox.setSelectedItem(
                safe(patient.getGenotype())
        );

        allergiesField.setText(
                safe(patient.getAllergies())
        );

        emergencyContactField.setText(
                safe(patient.getEmergencyContact())
        );

        emergencyPhoneField.setText(
                safe(patient.getEmergencyPhone())
        );
    }

    private void savePatient() {

        clearValidation();

        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String dateOfBirth =
                dateOfBirthField.getText().trim();

        if (firstName.isEmpty()) {
            showValidation(
                    firstNameField,
                    "First name is required."
            );
            return;
        }

        if (lastName.isEmpty()) {
            showValidation(
                    lastNameField,
                    "Last name is required."
            );
            return;
        }

        if (dateOfBirth.isEmpty()) {
            showValidation(
                    dateOfBirthField,
                    "Date of birth is required."
            );
            return;
        }

        LocalDate dob;

        try {

            dob = LocalDate.parse(
                    dateOfBirth
            );

        } catch (Exception ex) {

            showValidation(
                    dateOfBirthField,
                    "Use YYYY-MM-DD format."
            );

            return;
        }

        Patient target =
                patient == null
                        ? new Patient()
                        : patient;

        target.setFirstName(firstName);
        target.setLastName(lastName);

        String gender =
                String.valueOf(
                        genderBox.getSelectedItem()
                );

        if (!gender.isEmpty()) {
            target.setGender(
                    gender.charAt(0)
            );
        }

        target.setDateOfBirth(dob);

        target.setPhone(
                phoneField.getText().trim()
        );

        target.setEmail(
                emailField.getText().trim()
        );

        target.setStreet(
                streetField.getText().trim()
        );

        target.setCity(
                cityField.getText().trim()
        );

        target.setCountry(
                countryField.getText().trim()
        );

        target.setBloodGroup(
                String.valueOf(
                        bloodGroupBox.getSelectedItem()
                )
        );

        target.setGenotype(
                String.valueOf(
                        genotypeBox.getSelectedItem()
                )
        );

        target.setAllergies(
                allergiesField.getText().trim()
        );

        target.setEmergencyContact(
                emergencyContactField.getText().trim()
        );

        target.setEmergencyPhone(
                emergencyPhoneField.getText().trim()
        );

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        saveButton.setText(
                patient == null
                        ? "SAVING..."
                        : "UPDATING..."
        );

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        if (patient == null) {
                            return patientService
                                    .registerPatient(target);
                        }

                        return patientService
                                .updatePatient(target);
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean success = get();

                            if (success) {

                                saved = true;

                                JOptionPane.showMessageDialog(
                                        PatientDialog.this,
                                        patient == null
                                                ? "Patient registered successfully."
                                                : "Patient updated successfully.",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                dispose();

                            } else {

                                showOperationFailed();

                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    PatientDialog.this,
                                    "An error occurred:\n\n"
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

    private void showOperationFailed() {

        JOptionPane.showMessageDialog(
                this,
                "The operation could not be completed.",
                "Operation Failed",
                JOptionPane.ERROR_MESSAGE
        );

        restoreButtons();
    }

    private void restoreButtons() {

        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        saveButton.setText(
                patient == null
                        ? "SAVE PATIENT"
                        : "UPDATE PATIENT"
        );
    }

    private void clearValidation() {

        firstNameField.clearValidation();
        lastNameField.clearValidation();
        dateOfBirthField.clearValidation();
    }

    private void showValidation(
            AppTextField field,
            String message
    ) {

        field.setValidState(false);

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );

        field.requestFocus();
    }

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }

    public boolean isSaved() {
        return saved;
    }

    private void applyTheme(Theme theme) {

        if (theme == null) {
            return;
        }

        getContentPane().setBackground(
                theme.getBackgroundColor()
        );

        repaint();
    }

    @Override
    public void themeChanged(Theme newTheme) {
        applyTheme(newTheme);
    }

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.dispose();
    }
}