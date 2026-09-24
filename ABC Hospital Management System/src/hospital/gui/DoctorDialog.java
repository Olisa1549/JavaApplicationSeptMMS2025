package hospital.gui;

import hospital.dao.DepartmentDAO;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Department;
import hospital.models.Doctor;
import hospital.services.DoctorService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DoctorDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private AppTextField firstNameField;
    private AppTextField lastNameField;
    private JComboBox<String> genderBox;

    private AppTextField dateOfBirthField;

    private AppTextField phoneField;
    private AppTextField emailField;
    private AppTextField streetField;
    private AppTextField cityField;
    private AppTextField countryField;

    private AppTextField employmentDateField;
    private AppTextField salaryField;

    private JComboBox<DepartmentItem> departmentBox;

    private AppTextField specializationField;
    private AppTextField licenseNumberField;

    private AppButton saveButton;
    private AppButton cancelButton;

    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel statusLabel;

    private final DoctorService doctorService;
    private final DepartmentDAO departmentDAO;
    private final Doctor existingDoctor;

    private boolean saved = false;

    public DoctorDialog(
            Window owner,
            Doctor doctor
    ) {

        super(
                owner,
                doctor == null
                        ? "Add Doctor"
                        : "Edit Doctor",
                ModalityType.APPLICATION_MODAL
        );

        doctorService = new DoctorService();
        departmentDAO = new DepartmentDAO();
        existingDoctor = doctor;

        // Compact dialog size
        setSize(820, 610);
        setLocationRelativeTo(owner);
        setResizable(false);

        initUI();

        ThemeManager.addThemeChangeListener(this);

        loadDepartments();

        if (existingDoctor != null) {
            populateDoctor(existingDoctor);
        }
    }

    // =========================================================
    // INITIAL UI
    // =========================================================

    private void initUI() {

        JPanel rootPanel =
                new JPanel(new BorderLayout());

        rootPanel.setBorder(
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

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setOpaque(false);

        titleLabel =
                new JLabel(
                        existingDoctor == null
                                ? "Add Doctor"
                                : "Edit Doctor"
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        subtitleLabel =
                new JLabel(
                        existingDoctor == null
                                ? "Add a new doctor to the hospital system."
                                : "Update the doctor's information."
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(titleLabel);

        headerPanel.add(
                Box.createVerticalStrut(3)
        );

        headerPanel.add(subtitleLabel);

        rootPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(false);

        formPanel.setBorder(
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

        gbc.weightx = 1.0;

        int row = 0;

        // =====================================================
        // PERSONAL INFORMATION
        // =====================================================

        row = addSectionTitle(
                formPanel,
                gbc,
                row,
                "Personal Information"
        );

        firstNameField = createTextField();
        lastNameField = createTextField();

        genderBox =
                new JComboBox<>(
                        new String[]{
                                "M",
                                "F"
                        }
                );

        styleComboBox(genderBox);

        dateOfBirthField = createTextField();

        dateOfBirthField.putClientProperty(
                "JTextField.placeholderText",
                "YYYY-MM-DD"
        );

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "First Name",
                firstNameField,
                "Last Name",
                lastNameField
        );

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "Gender",
                genderBox,
                "Date of Birth",
                dateOfBirthField
        );

        // =====================================================
        // CONTACT INFORMATION
        // =====================================================

        row = addSectionTitle(
                formPanel,
                gbc,
                row,
                "Contact Information"
        );

        phoneField = createTextField();
        emailField = createTextField();
        streetField = createTextField();
        cityField = createTextField();
        countryField = createTextField();

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "Phone",
                phoneField,
                "Email",
                emailField
        );

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "Street",
                streetField,
                "City",
                cityField
        );

        row = addSingleField(
                formPanel,
                gbc,
                row,
                "Country",
                countryField
        );

        // =====================================================
        // EMPLOYMENT INFORMATION
        // =====================================================

        row = addSectionTitle(
                formPanel,
                gbc,
                row,
                "Employment Information"
        );

        employmentDateField =
                createTextField();

        employmentDateField.putClientProperty(
                "JTextField.placeholderText",
                "YYYY-MM-DD"
        );

        salaryField =
                createTextField();

        salaryField.putClientProperty(
                "JTextField.placeholderText",
                "e.g. 250000.00"
        );

        departmentBox =
                new JComboBox<>();

        styleComboBox(departmentBox);

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "Employment Date",
                employmentDateField,
                "Salary",
                salaryField
        );

        row = addSingleField(
                formPanel,
                gbc,
                row,
                "Department",
                departmentBox
        );

        // =====================================================
        // PROFESSIONAL INFORMATION
        // =====================================================

        row = addSectionTitle(
                formPanel,
                gbc,
                row,
                "Professional Information"
        );

        specializationField =
                createTextField();

        licenseNumberField =
                createTextField();

        row = addTwoFields(
                formPanel,
                gbc,
                row,
                "Specialization",
                specializationField,
                "License Number",
                licenseNumberField
        );

        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        formPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        rootPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footerPanel =
                new JPanel(
                        new BorderLayout()
                );

        footerPanel.setOpaque(false);

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
                        existingDoctor == null
                                ? "SAVE DOCTOR"
                                : "UPDATE DOCTOR"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveDoctor()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        footerPanel.add(
                statusLabel,
                BorderLayout.WEST
        );

        footerPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        rootPanel.add(
                footerPanel,
                BorderLayout.SOUTH
        );

        setContentPane(rootPanel);

        getRootPane()
                .setDefaultButton(saveButton);

        applyTheme(
                ThemeManager.getCurrentTheme()
        );
    }

    // =========================================================
    // FORM HELPERS
    // =========================================================

    private AppTextField createTextField() {

        AppTextField field =
                new AppTextField();

        field.setPreferredSize(
                new Dimension(
                        280,
                        36
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        36
                )
        );

        return field;
    }

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
                        13
                )
        );
    }

    private int addSectionTitle(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String title
    ) {

        JLabel sectionLabel =
                new JLabel(title);

        sectionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 4;

        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(
                        10,
                        6,
                        4,
                        6
                );

        panel.add(
                sectionLabel,
                gbc
        );

        gbc.gridwidth = 1;

        gbc.insets =
                new Insets(
                        3,
                        6,
                        3,
                        6
                );

        return row + 1;
    }

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
        gbc.weightx = 0.12;

        panel.add(
                createLabel(label1),
                gbc
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

        gbc.gridx = 3;
        gbc.weightx = 0.38;

        panel.add(
                field2,
                gbc
        );

        return row + 1;
    }

    private int addSingleField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component field
    ) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0.12;

        panel.add(
                createLabel(labelText),
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.88;

        panel.add(
                field,
                gbc
        );

        gbc.gridwidth = 1;

        return row + 1;
    }

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        return label;
    }

    // =========================================================
    // DEPARTMENTS
    // =========================================================

    private void loadDepartments() {

        departmentBox.removeAllItems();

        try {

            List<Department> departments =
                    departmentDAO.findAllDepartments();

            if (departments != null) {

                for (Department department : departments) {

                    departmentBox.addItem(
                            new DepartmentItem(
                                    department
                            )
                    );
                }
            }

        } catch (Exception ex) {

            statusLabel.setText(
                    "Unable to load departments."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load departments.\n\n"
                            + ex.getMessage(),
                    "Department Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // POPULATE EXISTING DOCTOR
    // =========================================================

    private void populateDoctor(
            Doctor doctor
    ) {

        firstNameField.setText(
                safe(
                        doctor.getFirstName()
                )
        );

        lastNameField.setText(
                safe(
                        doctor.getLastName()
                )
        );

        genderBox.setSelectedItem(
                String.valueOf(
                        doctor.getGender()
                )
        );

        if (doctor.getDateOfBirth() != null) {

            dateOfBirthField.setText(
                    doctor.getDateOfBirth()
                            .toString()
            );
        }

        phoneField.setText(
                safe(
                        doctor.getPhone()
                )
        );

        emailField.setText(
                safe(
                        doctor.getEmail()
                )
        );

        streetField.setText(
                safe(
                        doctor.getStreet()
                )
        );

        cityField.setText(
                safe(
                        doctor.getCity()
                )
        );

        countryField.setText(
                safe(
                        doctor.getCountry()
                )
        );

        if (doctor.getEmploymentDate() != null) {

            employmentDateField.setText(
                    doctor.getEmploymentDate()
                            .toString()
            );
        }

        salaryField.setText(
                String.valueOf(
                        doctor.getSalary()
                )
        );

        specializationField.setText(
                safe(
                        doctor.getSpecialization()
                )
        );

        licenseNumberField.setText(
                safe(
                        doctor.getLicenseNumber()
                )
        );

        selectDepartment(
                doctor.getDepartment()
        );
    }

    private void selectDepartment(
            Department department
    ) {

        if (department == null) {
            return;
        }

        for (
                int i = 0;
                i < departmentBox.getItemCount();
                i++
        ) {

            DepartmentItem item =
                    departmentBox.getItemAt(i);

            if (
                    item.getDepartment()
                            .getId()
                            == department.getId()
            ) {

                departmentBox.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================================================
    // SAVE DOCTOR
    // =========================================================

    private void saveDoctor() {

        clearValidation();

        String firstName =
                firstNameField
                        .getText()
                        .trim();

        String lastName =
                lastNameField
                        .getText()
                        .trim();

        String dobText =
                dateOfBirthField
                        .getText()
                        .trim();

        String employmentText =
                employmentDateField
                        .getText()
                        .trim();

        String salaryText =
                salaryField
                        .getText()
                        .trim();

        String specialization =
                specializationField
                        .getText()
                        .trim();

        String licenseNumber =
                licenseNumberField
                        .getText()
                        .trim();

        // =====================================================
        // VALIDATION
        // =====================================================

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

        if (dobText.isEmpty()) {

            showValidation(
                    dateOfBirthField,
                    "Date of birth is required."
            );

            return;
        }

        if (employmentText.isEmpty()) {

            showValidation(
                    employmentDateField,
                    "Employment date is required."
            );

            return;
        }

        if (salaryText.isEmpty()) {

            showValidation(
                    salaryField,
                    "Salary is required."
            );

            return;
        }

        if (specialization.isEmpty()) {

            showValidation(
                    specializationField,
                    "Specialization is required."
            );

            return;
        }

        if (licenseNumber.isEmpty()) {

            showValidation(
                    licenseNumberField,
                    "License number is required."
            );

            return;
        }

        DepartmentItem selectedDepartment =
                (DepartmentItem)
                        departmentBox.getSelectedItem();

        if (selectedDepartment == null) {

            statusLabel.setText(
                    "Please select a department."
            );

            return;
        }

        // =====================================================
        // PARSE DATES
        // =====================================================

        LocalDate dateOfBirth;
        LocalDate employmentDate;

        try {

            dateOfBirth =
                    LocalDate.parse(
                            dobText
                    );

        } catch (DateTimeParseException ex) {

            showValidation(
                    dateOfBirthField,
                    "Use date format YYYY-MM-DD."
            );

            return;
        }

        try {

            employmentDate =
                    LocalDate.parse(
                            employmentText
                    );

        } catch (DateTimeParseException ex) {

            showValidation(
                    employmentDateField,
                    "Use date format YYYY-MM-DD."
            );

            return;
        }

        // =====================================================
        // PARSE SALARY
        // =====================================================

        double salary;

        try {

            salary =
                    Double.parseDouble(
                            salaryText
                    );

            if (salary < 0) {

                showValidation(
                        salaryField,
                        "Salary cannot be negative."
                );

                return;
            }

        } catch (NumberFormatException ex) {

            showValidation(
                    salaryField,
                    "Enter a valid salary."
            );

            return;
        }

        // =====================================================
        // CREATE / UPDATE DOCTOR
        // =====================================================

        Doctor doctor;

        if (existingDoctor == null) {

            doctor =
                    new Doctor();

        } else {

            doctor =
                    existingDoctor;
        }

        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);

        String gender =
                String.valueOf(
                        genderBox.getSelectedItem()
                );

        if (!gender.isEmpty()) {

            doctor.setGender(
                    gender.charAt(0)
            );
        }

        doctor.setDateOfBirth(dateOfBirth);

        doctor.setPhone(
                phoneField
                        .getText()
                        .trim()
        );

        doctor.setEmail(
                emailField
                        .getText()
                        .trim()
        );

        doctor.setStreet(
                streetField
                        .getText()
                        .trim()
        );

        doctor.setCity(
                cityField
                        .getText()
                        .trim()
        );

        doctor.setCountry(
                countryField
                        .getText()
                        .trim()
        );

        doctor.setEmploymentDate(
                employmentDate
        );

        doctor.setSalary(
                salary
        );

        doctor.setDepartment(
                selectedDepartment.getDepartment()
        );

        doctor.setSpecialization(
                specialization
        );

        doctor.setLicenseNumber(
                licenseNumber
        );

        // =====================================================
        // SAVE
        // =====================================================

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        statusLabel.setText(
                existingDoctor == null
                        ? "Saving doctor..."
                        : "Updating doctor..."
        );

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        if (existingDoctor == null) {

                            return doctorService
                                    .registerDoctor(
                                            doctor
                                    );

                        } else {

                            return doctorService
                                    .updateDoctor(
                                            doctor
                                    );
                        }
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean success =
                                    get();

                            if (success) {

                                saved = true;

                                statusLabel.setText(
                                        existingDoctor == null
                                                ? "Doctor saved successfully."
                                                : "Doctor updated successfully."
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
                                        "Unable to save doctor."
                                );

                                JOptionPane.showMessageDialog(
                                        DoctorDialog.this,
                                        existingDoctor == null
                                                ? "Unable to register doctor."
                                                : "Unable to update doctor.",
                                        "Save Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );

                                saveButton.setEnabled(true);
                                cancelButton.setEnabled(true);
                            }

                        } catch (Exception ex) {

                            statusLabel.setText(
                                    "An error occurred."
                            );

                            JOptionPane.showMessageDialog(
                                    DoctorDialog.this,
                                    "An error occurred while saving the doctor.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            saveButton.setEnabled(true);
                            cancelButton.setEnabled(true);
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

        firstNameField.clearValidation();
        lastNameField.clearValidation();
        dateOfBirthField.clearValidation();
        employmentDateField.clearValidation();
        salaryField.clearValidation();
        specializationField.clearValidation();
        licenseNumberField.clearValidation();

        statusLabel.setText("");
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

        titleLabel.setForeground(
                theme.getTextColor()
        );

        subtitleLabel.setForeground(
                theme.getSecondaryTextColor()
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

    // =========================================================
    // CLEANUP
    // =========================================================

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.dispose();
    }

    // =========================================================
    // DEPARTMENT ITEM
    // =========================================================

    private static class DepartmentItem {

        private final Department department;

        public DepartmentItem(
                Department department
        ) {

            this.department =
                    department;
        }

        public Department getDepartment() {

            return department;
        }

        @Override
        public String toString() {

            if (department == null) {
                return "";
            }

            return department.getName();
        }
    }
}