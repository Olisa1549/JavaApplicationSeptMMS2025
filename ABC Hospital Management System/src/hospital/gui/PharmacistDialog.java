package hospital.gui;

import hospital.dao.DepartmentDAO;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppComboBox;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Department;
import hospital.models.Pharmacist;
import hospital.services.PharmacistService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PharmacistDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private final PharmacistService pharmacistService;
    private final DepartmentDAO departmentDAO;

    private final Pharmacist pharmacist;
    private final boolean editMode;

    private boolean saved = false;

    private AppTextField firstNameField;
    private AppTextField lastNameField;
    private AppTextField dateOfBirthField;

    private AppTextField phoneField;
    private AppTextField emailField;
    private AppTextField streetField;
    private AppTextField cityField;
    private AppTextField countryField;

    private AppTextField employmentDateField;
    private AppTextField salaryField;
    private AppTextField qualificationField;
    private AppTextField licenseNumberField;

    private AppComboBox<String> genderComboBox;
    private AppComboBox<Department> departmentComboBox;

    private AppButton saveButton;
    private AppButton cancelButton;

    private JLabel titleLabel;
    private JLabel subtitleLabel;

    public PharmacistDialog(
            Window owner,
            Pharmacist pharmacist
    ) {

        super(
                owner,
                pharmacist == null
                        ? "Add Pharmacist"
                        : "Edit Pharmacist",
                ModalityType.APPLICATION_MODAL
        );

        this.pharmacistService =
                new PharmacistService();

        this.departmentDAO =
                new DepartmentDAO();

        this.pharmacist =
                pharmacist;

        this.editMode =
                pharmacist != null;

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

        loadDepartments();

        if (editMode) {
            populateFields();
        }

        ThemeManager.addThemeChangeListener(this);

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
                        editMode
                                ? "Edit Pharmacist"
                                : "Register New Pharmacist"
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
                        editMode
                                ? "Update the pharmacist's information below."
                                : "Enter the pharmacist's information below."
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

        genderComboBox =
                new AppComboBox<>();

        genderComboBox.addItem("Male");
        genderComboBox.addItem("Female");

        dateOfBirthField =
                new AppTextField();

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
                genderComboBox,
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

        departmentComboBox =
                new AppComboBox<>();

        row = addField(
                form,
                gbc,
                row,
                "Employment Date",
                employmentDateField,
                "YYYY-MM-DD"
        );

        row = addField(
                form,
                gbc,
                row,
                "Salary",
                salaryField,
                "Enter salary"
        );

        row = addField(
                form,
                gbc,
                row,
                "Department",
                departmentComboBox,
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
                "Qualification",
                qualificationField,
                "Professional qualification"
        );

        row = addField(
                form,
                gbc,
                row,
                "License Number",
                licenseNumberField,
                "Professional license number"
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
                new AppButton(
                        "CANCEL"
                );

        saveButton =
                new AppButton(
                        editMode
                                ? "UPDATE PHARMACIST"
                                : "SAVE PHARMACIST"
                );

        cancelButton.addActionListener(
                e -> closeDialog()
        );

        saveButton.addActionListener(
                e -> savePharmacist()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        root.add(
                buttonPanel,
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
    // LOAD DEPARTMENTS
    // =========================================================

    private void loadDepartments() {

        try {

            List<Department> departments =
                    departmentDAO.findAllDepartments();

            departmentComboBox.removeAllItems();

            for (Department department :
                    departments) {

                departmentComboBox.addItem(
                        department
                );
            }

            departmentComboBox.setRenderer(
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

                            if (value
                                    instanceof Department department) {

                                setText(
                                        department.getName()
                                );
                            }

                            return this;
                        }
                    }
            );

        } catch (Exception ex) {

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
    // POPULATE EDIT FIELDS
    // =========================================================

    private void populateFields() {

        if (pharmacist == null) {
            return;
        }

        firstNameField.setText(
                safe(pharmacist.getFirstName())
        );

        lastNameField.setText(
                safe(pharmacist.getLastName())
        );

        genderComboBox.setSelectedItem(
                pharmacist.getGender() == 'M'
                        ? "Male"
                        : "Female"
        );

        if (pharmacist.getDateOfBirth() != null) {

            dateOfBirthField.setText(
                    pharmacist
                            .getDateOfBirth()
                            .toString()
            );
        }

        phoneField.setText(
                safe(pharmacist.getPhone())
        );

        emailField.setText(
                safe(pharmacist.getEmail())
        );

        streetField.setText(
                safe(pharmacist.getStreet())
        );

        cityField.setText(
                safe(pharmacist.getCity())
        );

        countryField.setText(
                safe(pharmacist.getCountry())
        );

        if (pharmacist.getEmploymentDate() != null) {

            employmentDateField.setText(
                    pharmacist
                            .getEmploymentDate()
                            .toString()
            );
        }

        salaryField.setText(
                String.valueOf(
                        pharmacist.getSalary()
                )
        );

        qualificationField.setText(
                safe(
                        pharmacist.getQualification()
                )
        );

        licenseNumberField.setText(
                safe(
                        pharmacist.getLicenseNumber()
                )
        );

        selectDepartment(
                pharmacist.getDepartment()
        );
    }

    // =========================================================
    // SELECT DEPARTMENT
    // =========================================================

    private void selectDepartment(
            Department existingDepartment
    ) {

        if (existingDepartment == null) {
            return;
        }

        for (int i = 0;
             i < departmentComboBox.getItemCount();
             i++) {

            Department department =
                    departmentComboBox.getItemAt(i);

            if (department != null
                    && department.getId()
                    == existingDepartment.getId()) {

                departmentComboBox.setSelectedIndex(i);

                break;
            }
        }
    }

    // =========================================================
    // SAVE PHARMACIST
    // =========================================================

    private void savePharmacist() {

        if (!validateFields()) {
            return;
        }

        try {

            char gender =
                    "Male".equals(
                            genderComboBox
                                    .getSelectedItem()
                    )
                            ? 'M'
                            : 'F';

            LocalDate dateOfBirth =
                    LocalDate.parse(
                            dateOfBirthField
                                    .getText()
                                    .trim()
                    );

            LocalDate employmentDate =
                    LocalDate.parse(
                            employmentDateField
                                    .getText()
                                    .trim()
                    );

            double salary =
                    Double.parseDouble(
                            salaryField
                                    .getText()
                                    .trim()
                    );

            if (salary < 0) {

                throw new NumberFormatException();
            }

            Department department =
                    (Department)
                            departmentComboBox
                                    .getSelectedItem();

            if (editMode) {

                pharmacist.setFirstName(
                        firstNameField
                                .getText()
                                .trim()
                );

                pharmacist.setLastName(
                        lastNameField
                                .getText()
                                .trim()
                );

                pharmacist.setGender(
                        gender
                );

                pharmacist.setDateOfBirth(
                        dateOfBirth
                );

                pharmacist.setPhone(
                        phoneField
                                .getText()
                                .trim()
                );

                pharmacist.setEmail(
                        emailField
                                .getText()
                                .trim()
                );

                pharmacist.setStreet(
                        streetField
                                .getText()
                                .trim()
                );

                pharmacist.setCity(
                        cityField
                                .getText()
                                .trim()
                );

                pharmacist.setCountry(
                        countryField
                                .getText()
                                .trim()
                );

                pharmacist.setEmploymentDate(
                        employmentDate
                );

                pharmacist.setSalary(
                        salary
                );

                pharmacist.setDepartment(
                        department
                );

                pharmacist.setQualification(
                        qualificationField
                                .getText()
                                .trim()
                );

                pharmacist.setLicenseNumber(
                        licenseNumberField
                                .getText()
                                .trim()
                );

                setSavingState();

                boolean success =
                        pharmacistService
                                .updatePharmacist(
                                        pharmacist
                                );

                if (success) {

                    saved = true;

                    JOptionPane.showMessageDialog(
                            this,
                            "Pharmacist updated successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    closeDialog();

                } else {

                    restoreButtons();

                    JOptionPane.showMessageDialog(
                            this,
                            "Unable to update pharmacist.",
                            "Update Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } else {

                Pharmacist newPharmacist =
                        new Pharmacist();

                newPharmacist.setFirstName(
                        firstNameField
                                .getText()
                                .trim()
                );

                newPharmacist.setLastName(
                        lastNameField
                                .getText()
                                .trim()
                );

                newPharmacist.setGender(
                        gender
                );

                newPharmacist.setDateOfBirth(
                        dateOfBirth
                );

                newPharmacist.setPhone(
                        phoneField
                                .getText()
                                .trim()
                );

                newPharmacist.setEmail(
                        emailField
                                .getText()
                                .trim()
                );

                newPharmacist.setStreet(
                        streetField
                                .getText()
                                .trim()
                );

                newPharmacist.setCity(
                        cityField
                                .getText()
                                .trim()
                );

                newPharmacist.setCountry(
                        countryField
                                .getText()
                                .trim()
                );

                newPharmacist.setEmploymentDate(
                        employmentDate
                );

                newPharmacist.setSalary(
                        salary
                );

                newPharmacist.setDepartment(
                        department
                );

                newPharmacist.setQualification(
                        qualificationField
                                .getText()
                                .trim()
                );

                newPharmacist.setLicenseNumber(
                        licenseNumberField
                                .getText()
                                .trim()
                );

                setSavingState();

                boolean success =
                        pharmacistService
                                .registerPharmacist(
                                        newPharmacist
                                );

                if (success) {

                    saved = true;

                    JOptionPane.showMessageDialog(
                            this,
                            "Pharmacist registered successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    closeDialog();

                } else {

                    restoreButtons();

                    JOptionPane.showMessageDialog(
                            this,
                            "Unable to register pharmacist.",
                            "Registration Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } catch (NumberFormatException ex) {

            restoreButtons();

            JOptionPane.showMessageDialog(
                    this,
                    "Salary must be a valid non-negative number.",
                    "Invalid Salary",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (DateTimeParseException ex) {

            restoreButtons();

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates using the format:\n"
                            + "YYYY-MM-DD",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception ex) {

            restoreButtons();

            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while saving the pharmacist.\n\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateFields() {

        if (firstNameField.getText().trim().isEmpty()) {

            showValidationError(
                    "First name is required."
            );

            firstNameField.requestFocus();

            return false;
        }

        if (lastNameField.getText().trim().isEmpty()) {

            showValidationError(
                    "Last name is required."
            );

            lastNameField.requestFocus();

            return false;
        }

        if (dateOfBirthField.getText().trim().isEmpty()) {

            showValidationError(
                    "Date of birth is required."
            );

            dateOfBirthField.requestFocus();

            return false;
        }

        if (employmentDateField.getText().trim().isEmpty()) {

            showValidationError(
                    "Employment date is required."
            );

            employmentDateField.requestFocus();

            return false;
        }

        if (salaryField.getText().trim().isEmpty()) {

            showValidationError(
                    "Salary is required."
            );

            salaryField.requestFocus();

            return false;
        }

        if (qualificationField.getText().trim().isEmpty()) {

            showValidationError(
                    "Qualification is required."
            );

            qualificationField.requestFocus();

            return false;
        }

        if (licenseNumberField.getText().trim().isEmpty()) {

            showValidationError(
                    "License number is required."
            );

            licenseNumberField.requestFocus();

            return false;
        }

        if (departmentComboBox.getSelectedItem() == null) {

            showValidationError(
                    "Please select a department."
            );

            departmentComboBox.requestFocus();

            return false;
        }

        return true;
    }

    private void showValidationError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =========================================================
    // SAVING STATE
    // =========================================================

    private void setSavingState() {

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        saveButton.setText(
                editMode
                        ? "UPDATING..."
                        : "SAVING..."
        );
    }

    private void restoreButtons() {

        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        saveButton.setText(
                editMode
                        ? "UPDATE PHARMACIST"
                        : "SAVE PHARMACIST"
        );
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

        repaint();
    }

    @Override
    public void themeChanged(
            Theme newTheme
    ) {

        applyTheme(newTheme);
    }

    // =========================================================
    // CLOSE
    // =========================================================

    private void closeDialog() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        dispose();
    }

    @Override
    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );

        super.dispose();
    }

    // =========================================================
    // SAVED STATUS
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
}