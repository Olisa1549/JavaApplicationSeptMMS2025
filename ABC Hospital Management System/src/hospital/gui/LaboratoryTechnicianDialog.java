package hospital.gui;

import hospital.dao.DepartmentDAO;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Department;
import hospital.models.LaboratoryTechnician;
import hospital.services.LaboratoryTechnicianService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class LaboratoryTechnicianDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private final LaboratoryTechnicianService service;
    private final DepartmentDAO departmentDAO;
    private final LaboratoryTechnician existingTechnician;

    private boolean saved = false;

    private AppTextField firstNameField;
    private AppTextField lastNameField;
    private AppTextField dateOfBirthField;

    private JComboBox<String> genderBox;

    private AppTextField phoneField;
    private AppTextField emailField;
    private AppTextField streetField;
    private AppTextField cityField;
    private AppTextField countryField;

    private AppTextField employmentDateField;
    private AppTextField salaryField;

    private JComboBox<Department> departmentBox;

    private AppTextField qualificationField;
    private AppTextField licenseNumberField;

    private AppButton saveButton;
    private AppButton cancelButton;

    public LaboratoryTechnicianDialog(
            Window owner,
            LaboratoryTechnician technician
    ) {

        super(
                owner,
                technician == null
                        ? "Add Laboratory Technician"
                        : "Edit Laboratory Technician",
                ModalityType.APPLICATION_MODAL
        );

        this.existingTechnician = technician;
        this.service = new LaboratoryTechnicianService();
        this.departmentDAO = new DepartmentDAO();

        setSize(820, 650);
        setLocationRelativeTo(owner);
        setResizable(false);

        initUI();
        loadDepartments();

        if (existingTechnician != null) {
            populateFields();
        }

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        JPanel root =
                new JPanel(new BorderLayout());

        root.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        12,
                        25
                )
        );

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        existingTechnician == null
                                ? "Register New Laboratory Technician"
                                : "Edit Laboratory Technician"
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
                        existingTechnician == null
                                ? "Enter the technician's information below."
                                : "Update the technician's information below."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(3)
        );

        header.add(subtitle);

        root.add(
                header,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // FORM
        // -----------------------------------------------------

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
        // PERSONAL INFORMATION
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Personal Information"
        );

        firstNameField =
                new AppTextField();

        lastNameField =
                new AppTextField();

        genderBox =
                new JComboBox<>(
                        new String[]{
                                "M",
                                "F"
                        }
                );

        dateOfBirthField =
                new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "First Name *",
                firstNameField,
                "Enter first name"
        );

        row = addField(
                form,
                gbc,
                row,
                "Last Name *",
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
                "Date of Birth *",
                dateOfBirthField,
                "YYYY-MM-DD"
        );

        // =====================================================
        // CONTACT INFORMATION
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Contact Information"
        );

        phoneField =
                new AppTextField();

        emailField =
                new AppTextField();

        streetField =
                new AppTextField();

        cityField =
                new AppTextField();

        countryField =
                new AppTextField();

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

        // =====================================================
        // EMPLOYMENT INFORMATION
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Employment Information"
        );

        employmentDateField =
                new AppTextField();

        salaryField =
                new AppTextField();

        departmentBox =
                new JComboBox<>();

        row = addField(
                form,
                gbc,
                row,
                "Employment Date *",
                employmentDateField,
                "YYYY-MM-DD"
        );

        row = addField(
                form,
                gbc,
                row,
                "Salary *",
                salaryField,
                "Enter salary"
        );

        row = addField(
                form,
                gbc,
                row,
                "Department *",
                departmentBox,
                null
        );

        // =====================================================
        // PROFESSIONAL INFORMATION
        // =====================================================

        row = addSection(
                form,
                gbc,
                row,
                "Professional Information"
        );

        qualificationField =
                new AppTextField();

        licenseNumberField =
                new AppTextField();

        row = addField(
                form,
                gbc,
                row,
                "Qualification *",
                qualificationField,
                "Professional qualification"
        );

        row = addField(
                form,
                gbc,
                row,
                "License Number *",
                licenseNumberField,
                "Laboratory license number"
        );

        JScrollPane scrollPane =
                new JScrollPane(form);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(14);

        root.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTONS
        // =====================================================

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
                new AppButton("CANCEL");

        saveButton =
                new AppButton(
                        existingTechnician == null
                                ? "SAVE TECHNICIAN"
                                : "UPDATE TECHNICIAN"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveTechnician()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        root.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(root);
    }

    // =========================================================
    // SECTION
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
    // FIELD
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
                new JLabel(labelText);

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
    // DEPARTMENTS
    // =========================================================

    private void loadDepartments() {

        departmentBox.removeAllItems();

        List<Department> departments =
                departmentDAO.findAllDepartments();

        for (Department department : departments) {

            departmentBox.addItem(
                    department
            );
        }

        departmentBox.setRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component
                    getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus
                    ) {

                        super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                        if (value instanceof Department) {

                            setText(
                                    ((Department) value)
                                            .getName()
                            );
                        }

                        return this;
                    }
                }
        );
    }

    // =========================================================
    // POPULATE
    // =========================================================

    private void populateFields() {

        firstNameField.setText(
                safe(
                        existingTechnician
                                .getFirstName()
                )
        );

        lastNameField.setText(
                safe(
                        existingTechnician
                                .getLastName()
                )
        );

        genderBox.setSelectedItem(
                String.valueOf(
                        existingTechnician
                                .getGender()
                )
        );

        if (existingTechnician
                .getDateOfBirth() != null) {

            dateOfBirthField.setText(
                    existingTechnician
                            .getDateOfBirth()
                            .toString()
            );
        }

        phoneField.setText(
                safe(
                        existingTechnician
                                .getPhone()
                )
        );

        emailField.setText(
                safe(
                        existingTechnician
                                .getEmail()
                )
        );

        streetField.setText(
                safe(
                        existingTechnician
                                .getStreet()
                )
        );

        cityField.setText(
                safe(
                        existingTechnician
                                .getCity()
                )
        );

        countryField.setText(
                safe(
                        existingTechnician
                                .getCountry()
                )
        );

        if (existingTechnician
                .getEmploymentDate() != null) {

            employmentDateField.setText(
                    existingTechnician
                            .getEmploymentDate()
                            .toString()
            );
        }

        salaryField.setText(
                String.valueOf(
                        existingTechnician
                                .getSalary()
                )
        );

        qualificationField.setText(
                safe(
                        existingTechnician
                                .getQualification()
                )
        );

        licenseNumberField.setText(
                safe(
                        existingTechnician
                                .getLicenseNumber()
                )
        );

        selectDepartment(
                existingTechnician
                        .getDepartment()
        );
    }

    private void selectDepartment(
            Department existingDepartment
    ) {

        if (existingDepartment == null) {
            return;
        }

        for (int i = 0;
             i < departmentBox.getItemCount();
             i++) {

            Department department =
                    departmentBox.getItemAt(i);

            if (department != null
                    && department.getId()
                    == existingDepartment.getId()) {

                departmentBox.setSelectedIndex(i);

                break;
            }
        }
    }

    // =========================================================
    // SAVE
    // =========================================================

    private void saveTechnician() {

        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String dobText =
                dateOfBirthField.getText().trim();

        String employmentText =
                employmentDateField.getText().trim();

        String salaryText =
                salaryField.getText().trim();

        String qualification =
                qualificationField.getText().trim();

        String licenseNumber =
                licenseNumberField.getText().trim();

        // -----------------------------------------------------
        // REQUIRED
        // -----------------------------------------------------

        if (firstName.isEmpty()
                || lastName.isEmpty()) {

            showValidation(
                    "First name and last name are required."
            );

            return;
        }

        if (dobText.isEmpty()) {

            showValidation(
                    "Date of birth is required."
            );

            return;
        }

        if (employmentText.isEmpty()) {

            showValidation(
                    "Employment date is required."
            );

            return;
        }

        if (salaryText.isEmpty()) {

            showValidation(
                    "Salary is required."
            );

            return;
        }

        if (qualification.isEmpty()) {

            showValidation(
                    "Qualification is required."
            );

            return;
        }

        if (licenseNumber.isEmpty()) {

            showValidation(
                    "License number is required."
            );

            return;
        }

        if (departmentBox.getSelectedItem() == null) {

            showValidation(
                    "Please select a department."
            );

            return;
        }

        // -----------------------------------------------------
        // DATES
        // -----------------------------------------------------

        LocalDate dateOfBirth;

        LocalDate employmentDate;

        try {

            dateOfBirth =
                    LocalDate.parse(
                            dobText
                    );

        } catch (DateTimeParseException ex) {

            showValidation(
                    "Date of birth must use YYYY-MM-DD."
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
                    "Employment date must use YYYY-MM-DD."
            );

            return;
        }

        // -----------------------------------------------------
        // SALARY
        // -----------------------------------------------------

        double salary;

        try {

            salary =
                    Double.parseDouble(
                            salaryText
                    );

            if (salary < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException ex) {

            showValidation(
                    "Salary must be a valid non-negative number."
            );

            return;
        }

        // -----------------------------------------------------
        // GENDER
        // -----------------------------------------------------

        String genderText =
                String.valueOf(
                        genderBox.getSelectedItem()
                );

        char gender =
                genderText.isEmpty()
                        ? 'M'
                        : genderText.charAt(0);

        // -----------------------------------------------------
        // DEPARTMENT
        // -----------------------------------------------------

        Department department =
                (Department)
                        departmentBox.getSelectedItem();

        // -----------------------------------------------------
        // CREATE / UPDATE
        // -----------------------------------------------------

        LaboratoryTechnician technician;

        if (existingTechnician == null) {

            technician =
                    new LaboratoryTechnician();

        } else {

            technician =
                    existingTechnician;
        }

        technician.setFirstName(
                firstName
        );

        technician.setLastName(
                lastName
        );

        technician.setGender(
                gender
        );

        technician.setDateOfBirth(
                dateOfBirth
        );

        technician.setPhone(
                phoneField.getText().trim()
        );

        technician.setEmail(
                emailField.getText().trim()
        );

        technician.setStreet(
                streetField.getText().trim()
        );

        technician.setCity(
                cityField.getText().trim()
        );

        technician.setCountry(
                countryField.getText().trim()
        );

        technician.setEmploymentDate(
                employmentDate
        );

        technician.setSalary(
                salary
        );

        technician.setDepartment(
                department
        );

        technician.setQualification(
                qualification
        );

        technician.setLicenseNumber(
                licenseNumber
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        saveButton.setText(
                existingTechnician == null
                        ? "SAVING..."
                        : "UPDATING..."
        );

        setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR
                )
        );

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        if (existingTechnician == null) {

                            return service
                                    .registerLaboratoryTechnician(
                                            technician
                                    );

                        } else {

                            return service
                                    .updateLaboratoryTechnician(
                                            technician
                                    );
                        }
                    }

                    @Override
                    protected void done() {

                        setCursor(
                                Cursor.getDefaultCursor()
                        );

                        try {

                            boolean success =
                                    get();

                            if (success) {

                                saved = true;

                                JOptionPane.showMessageDialog(
                                        LaboratoryTechnicianDialog.this,
                                        existingTechnician == null
                                                ? "Laboratory technician registered successfully."
                                                : "Laboratory technician updated successfully.",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                dispose();

                            } else {

                                showOperationFailed();
                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    LaboratoryTechnicianDialog.this,
                                    "An error occurred while saving the laboratory technician.\n\n"
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
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showOperationFailed() {

        JOptionPane.showMessageDialog(
                this,
                existingTechnician == null
                        ? "The laboratory technician could not be registered."
                        : "The laboratory technician could not be updated.",
                "Operation Failed",
                JOptionPane.ERROR_MESSAGE
        );

        restoreButtons();
    }

    private void restoreButtons() {

        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        saveButton.setText(
                existingTechnician == null
                        ? "SAVE TECHNICIAN"
                        : "UPDATE TECHNICIAN"
        );

        setCursor(
                Cursor.getDefaultCursor()
        );
    }

    // =========================================================
    // STATUS
    // =========================================================

    public boolean isSaved() {
        return saved;
    }

    // =========================================================
    // SAFE STRING
    // =========================================================

    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }

    // =========================================================
    // THEME
    // =========================================================

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
}