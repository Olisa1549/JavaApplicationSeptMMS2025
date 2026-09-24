package hospital.gui;

import hospital.dao.DepartmentDAO;
import hospital.gui.components.AppButton;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Department;
import hospital.models.Nurse;
import hospital.services.NurseService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class NurseDialog extends JDialog
        implements ThemeManager.ThemeChangeListener {

    private final NurseService nurseService;
    private final DepartmentDAO departmentDAO;

    private final Nurse existingNurse;

    private boolean saved = false;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> genderBox;
    private JTextField dateOfBirthField;

    private JTextField phoneField;
    private JTextField emailField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField countryField;

    private JTextField employmentDateField;
    private JTextField salaryField;

    private JComboBox<Department> departmentBox;

    private JTextField nursingLicenseField;
    private JTextField qualificationField;

    private AppButton saveButton;
    private AppButton cancelButton;

    public NurseDialog(Window parent, Nurse nurse) {

        super(
                parent,
                nurse == null ? "Add Nurse" : "Edit Nurse",
                ModalityType.APPLICATION_MODAL
        );

        this.existingNurse = nurse;
        this.nurseService = new NurseService();
        this.departmentDAO = new DepartmentDAO();

        setSize(820, 650);
        setLocationRelativeTo(parent);
        setResizable(false);

        initUI();
        loadDepartments();

        if (existingNurse != null) {
            populateFields();
        }

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
    }

    private void initUI() {

        JPanel root = new JPanel(new BorderLayout());

        root.setBorder(
                new EmptyBorder(15, 25, 12, 25)
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                existingNurse == null
                        ? "Register New Nurse"
                        : "Edit Nurse Information"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                existingNurse == null
                        ? "Enter the nurse's information below."
                        : "Update the nurse's information below."
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

        // =====================================================
        // FORM
        // =====================================================

        JPanel form = new JPanel(
                new GridBagLayout()
        );

        form.setOpaque(false);

        form.setBorder(
                new EmptyBorder(8, 0, 5, 0)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(3, 6, 3, 6);

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

        firstNameField = new JTextField();
        lastNameField = new JTextField();

        genderBox = new JComboBox<>(
                new String[]{
                        "M",
                        "F"
                }
        );

        dateOfBirthField = new JTextField();

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

        phoneField = new JTextField();
        emailField = new JTextField();
        streetField = new JTextField();
        cityField = new JTextField();
        countryField = new JTextField();

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

        employmentDateField = new JTextField();
        salaryField = new JTextField();

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

        nursingLicenseField = new JTextField();
        qualificationField = new JTextField();

        row = addField(
                form,
                gbc,
                row,
                "Nursing License *",
                nursingLicenseField,
                "Nursing license number"
        );

        row = addField(
                form,
                gbc,
                row,
                "Qualification *",
                qualificationField,
                "Professional qualification"
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
                        existingNurse == null
                                ? "SAVE NURSE"
                                : "UPDATE NURSE"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveNurse()
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

        panel.add(label, gbc);

        gbc.insets =
                new Insets(
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

    // =========================================================
    // LOAD DEPARTMENTS
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
    // POPULATE EDIT FIELDS
    // =========================================================

    private void populateFields() {

        if (existingNurse == null) {
            return;
        }

        firstNameField.setText(
                safe(existingNurse.getFirstName())
        );

        lastNameField.setText(
                safe(existingNurse.getLastName())
        );

        genderBox.setSelectedItem(
                String.valueOf(
                        existingNurse.getGender()
                )
        );

        if (existingNurse.getDateOfBirth() != null) {

            dateOfBirthField.setText(
                    existingNurse
                            .getDateOfBirth()
                            .toString()
            );
        }

        phoneField.setText(
                safe(existingNurse.getPhone())
        );

        emailField.setText(
                safe(existingNurse.getEmail())
        );

        streetField.setText(
                safe(existingNurse.getStreet())
        );

        cityField.setText(
                safe(existingNurse.getCity())
        );

        countryField.setText(
                safe(existingNurse.getCountry())
        );

        if (existingNurse.getEmploymentDate() != null) {

            employmentDateField.setText(
                    existingNurse
                            .getEmploymentDate()
                            .toString()
            );
        }

        salaryField.setText(
                String.valueOf(
                        existingNurse.getSalary()
                )
        );

        nursingLicenseField.setText(
                safe(
                        existingNurse
                                .getNursingLicense()
                )
        );

        qualificationField.setText(
                safe(
                        existingNurse
                                .getQualification()
                )
        );

        selectDepartment(
                existingNurse.getDepartment()
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
    // SAVE NURSE
    // =========================================================

    private void saveNurse() {

        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String dateOfBirthText =
                dateOfBirthField.getText().trim();

        String employmentDateText =
                employmentDateField.getText().trim();

        String salaryText =
                salaryField.getText().trim();

        String nursingLicense =
                nursingLicenseField.getText().trim();

        String qualification =
                qualificationField.getText().trim();

        // -----------------------------------------------------
        // REQUIRED FIELDS
        // -----------------------------------------------------

        if (firstName.isEmpty()
                || lastName.isEmpty()) {

            showValidation(
                    "First name and last name are required."
            );

            return;
        }

        if (dateOfBirthText.isEmpty()) {

            showValidation(
                    "Date of birth is required."
            );

            return;
        }

        if (employmentDateText.isEmpty()) {

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

        if (nursingLicense.isEmpty()) {

            showValidation(
                    "Nursing license is required."
            );

            return;
        }

        if (qualification.isEmpty()) {

            showValidation(
                    "Qualification is required."
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
                            dateOfBirthText
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
                            employmentDateText
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

        Nurse nurse;

        if (existingNurse == null) {

            nurse = new Nurse();

        } else {

            nurse = existingNurse;
        }

        nurse.setFirstName(firstName);
        nurse.setLastName(lastName);
        nurse.setGender(gender);
        nurse.setDateOfBirth(dateOfBirth);

        nurse.setPhone(
                phoneField.getText().trim()
        );

        nurse.setEmail(
                emailField.getText().trim()
        );

        nurse.setStreet(
                streetField.getText().trim()
        );

        nurse.setCity(
                cityField.getText().trim()
        );

        nurse.setCountry(
                countryField.getText().trim()
        );

        nurse.setEmploymentDate(
                employmentDate
        );

        nurse.setSalary(
                salary
        );

        nurse.setDepartment(
                department
        );

        nurse.setNursingLicense(
                nursingLicense
        );

        nurse.setQualification(
                qualification
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        saveButton.setText(
                existingNurse == null
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
                    protected Boolean doInBackground()
                            throws Exception {

                        if (existingNurse == null) {

                            return nurseService
                                    .registerNurse(nurse);

                        } else {

                            return nurseService
                                    .updateNurse(nurse);
                        }
                    }

                    @Override
                    protected void done() {

                        setCursor(
                                Cursor.getDefaultCursor()
                        );

                        try {

                            boolean success = get();

                            if (success) {

                                saved = true;

                                JOptionPane.showMessageDialog(
                                        NurseDialog.this,
                                        existingNurse == null
                                                ? "Nurse registered successfully."
                                                : "Nurse information updated successfully.",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                dispose();

                            } else {

                                showOperationFailed();
                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    NurseDialog.this,
                                    "An error occurred while saving the nurse.\n\n"
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
                existingNurse == null
                        ? "The nurse could not be registered."
                        : "The nurse could not be updated.",
                "Operation Failed",
                JOptionPane.ERROR_MESSAGE
        );

        restoreButtons();
    }

    private void restoreButtons() {

        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        saveButton.setText(
                existingNurse == null
                        ? "SAVE NURSE"
                        : "UPDATE NURSE"
        );

        setCursor(
                Cursor.getDefaultCursor()
        );
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