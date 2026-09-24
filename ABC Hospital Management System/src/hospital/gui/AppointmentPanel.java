package hospital.gui;

import hospital.gui.components.AppButton;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Appointment;
import hospital.services.AppointmentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class AppointmentPanel extends JPanel
        implements ThemeManager.ThemeChangeListener {

    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private AppTextField searchField;

    private AppButton addButton;
    private AppButton editButton;
    private AppButton deleteButton;
    private AppButton refreshButton;

    private JLabel countLabel;
    private JLabel statusLabel;

    private final AppointmentService appointmentService;

    private final DateTimeFormatter displayFormatter =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    public AppointmentPanel() {

        appointmentService =
                new AppointmentService();

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

        ThemeManager.addThemeChangeListener(this);

        loadAppointments();
    }

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

        JPanel topPanel = new JPanel(new BorderLayout(0, 12));
        topPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Appointment Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel subtitle = new JLabel("Schedule and manage patient appointments.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel(new BorderLayout(10, 0));
        actionPanel.setOpaque(false);

        searchField = new AppTextField();
        searchField.setPreferredSize(new Dimension(240, 40));
        searchField.putClientProperty("JTextField.placeholderText", "Search appointments...");
        actionPanel.add(searchField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setOpaque(false);

        addButton = new AppButton("Add Appointment");
        editButton = new AppButton("Edit");
        deleteButton = new AppButton("Delete");
        refreshButton = new AppButton("Refresh");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        refreshButton.addActionListener(e -> loadAppointments());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        actionPanel.add(buttonPanel, BorderLayout.EAST);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterAppointments(); }
            @Override public void removeUpdate(DocumentEvent e) { filterAppointments(); }
            @Override public void changedUpdate(DocumentEvent e) { filterAppointments(); }
        });

        topPanel.add(actionPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    // =========================================================
    // TABLE
    // =========================================================

    private void createTable() {

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Patient",
                                "Doctor",
                                "Date & Time",
                                "Reason",
                                "Status"
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

        appointmentTable =
                new JTable(
                        tableModel
                );

        appointmentTable.setRowHeight(42);

        appointmentTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        appointmentTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        appointmentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        appointmentTable.setAutoCreateRowSorter(
                true
        );

        sorter =
                new TableRowSorter<>(
                        tableModel
                );

        appointmentTable.setRowSorter(
                sorter
        );

        appointmentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> updateButtonState()
                );

        appointmentTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 2
                                        &&
                                appointmentTable
                                        .getSelectedRow()
                                        != -1
                        ) {

                            editAppointment();
                        }
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        appointmentTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                224,
                                230
                        )
                )
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
                        "Appointments: 0"
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
    // LOAD
    // =========================================================

    private void loadAppointments() {

        statusLabel.setText(
                "Loading appointments..."
        );

        setButtonsEnabled(
                false
        );

        SwingWorker<List<Appointment>, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected List<Appointment> doInBackground() {

                        return appointmentService
                                .getAllAppointments();
                    }

                    @Override
                    protected void done() {

                        try {

                            List<Appointment> appointments =
                                    get();

                            tableModel.setRowCount(0);

                            for (
                                    Appointment appointment :
                                    appointments
                            ) {

                                String patientName =
                                        getPatientName(
                                                appointment
                                        );

                                String doctorName =
                                        getDoctorName(
                                                appointment
                                        );

                                String dateTime =
                                        formatDateTime(
                                                appointment
                                                        .getAppointmentDate()
                                        );

                                tableModel.addRow(
                                        new Object[]{
                                                appointment.getId(),
                                                patientName,
                                                doctorName,
                                                dateTime,
                                                appointment.getReason(),
                                                appointment.getStatus()
                                        }
                                );
                            }

                            countLabel.setText(
                                    "Appointments: "
                                            + appointments.size()
                            );

                            statusLabel.setText(
                                    "Appointments loaded successfully."
                            );

                        } catch (Exception ex) {

                            ex.printStackTrace();

                            statusLabel.setText(
                                    "Failed to load appointments."
                            );

                            JOptionPane.showMessageDialog(
                                    AppointmentPanel.this,
                                    "Unable to load appointments.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            addButton.setEnabled(true);
                            refreshButton.setEnabled(true);

                            updateButtonState();
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void filterAppointments() {

        if (sorter == null) {
            return;
        }

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
                                    + Pattern.quote(text)
                    )
            );
        }
    }

    // =========================================================
    // BUTTON STATE
    // =========================================================

    private void updateButtonState() {

        boolean selected =
                appointmentTable != null
                        &&
                        appointmentTable
                                .getSelectedRow()
                                != -1;

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

    private void setButtonsEnabled(
            boolean enabled
    ) {

        addButton.setEnabled(
                enabled
        );

        editButton.setEnabled(
                enabled
        );

        deleteButton.setEnabled(
                enabled
        );

        refreshButton.setEnabled(
                enabled
        );
    }

    // =========================================================
    // ADD
    // =========================================================

    private void addAppointment() {

        Window window =
                SwingUtilities
                        .getWindowAncestor(this);

        AppointmentDialog dialog =
                new AppointmentDialog(
                        window,
                        null
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadAppointments();
        }
    }

    // =========================================================
    // EDIT
    // =========================================================

    private void editAppointment() {

        int selectedRow =
                appointmentTable
                        .getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                appointmentTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int appointmentId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );

        Appointment appointment =
                appointmentService
                        .getAppointmentById(
                                appointmentId
                        );

        if (appointment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to find the selected appointment.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Window window =
                SwingUtilities
                        .getWindowAncestor(this);

        AppointmentDialog dialog =
                new AppointmentDialog(
                        window,
                        appointment
                );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            loadAppointments();
        }
    }

    // =========================================================
    // DELETE
    // =========================================================

    private void deleteAppointment() {

        int selectedRow =
                appointmentTable
                        .getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int modelRow =
                appointmentTable
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int appointmentId =
                (int)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );

        String patient =
                String.valueOf(
                        tableModel.getValueAt(
                                modelRow,
                                1
                        )
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the appointment for "
                                + patient
                                + "?",
                        "Delete Appointment",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                result
                        !=
                        JOptionPane.YES_OPTION
        ) {
            return;
        }

        statusLabel.setText(
                "Deleting appointment..."
        );

        setButtonsEnabled(
                false
        );

        SwingWorker<Boolean, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected Boolean doInBackground() {

                        return appointmentService
                                .deleteAppointment(
                                        appointmentId
                                );
                    }

                    @Override
                    protected void done() {

                        try {

                            boolean deleted =
                                    get();

                            if (deleted) {

                                statusLabel.setText(
                                        "Appointment deleted successfully."
                                );

                                loadAppointments();

                            } else {

                                statusLabel.setText(
                                        "Unable to delete appointment."
                                );

                                JOptionPane.showMessageDialog(
                                        AppointmentPanel.this,
                                        "The appointment could not be deleted.",
                                        "Delete Failed",
                                        JOptionPane.ERROR_MESSAGE
                                );

                                addButton.setEnabled(true);
                                refreshButton.setEnabled(true);
                                updateButtonState();
                            }

                        } catch (Exception ex) {

                            ex.printStackTrace();

                            JOptionPane.showMessageDialog(
                                    AppointmentPanel.this,
                                    "An error occurred while deleting the appointment.\n\n"
                                            + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            addButton.setEnabled(true);
                            refreshButton.setEnabled(true);
                            updateButtonState();
                        }
                    }
                };

        worker.execute();
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private String getPatientName(
            Appointment appointment
    ) {

        if (
                appointment.getPatient()
                        == null
        ) {
            return "";
        }

        return safe(
                appointment.getPatient()
                        .getFirstName()
        )
                + " "
                + safe(
                appointment.getPatient()
                        .getLastName()
        );
    }

    private String getDoctorName(
            Appointment appointment
    ) {

        if (
                appointment.getDoctor()
                        == null
        ) {
            return "";
        }

        return "Dr. "
                + safe(
                appointment.getDoctor()
                        .getFirstName()
        )
                + " "
                + safe(
                appointment.getDoctor()
                        .getLastName()
        );
    }

    private String formatDateTime(
            LocalDateTime dateTime
    ) {

        if (dateTime == null) {
            return "";
        }

        return dateTime.format(
                displayFormatter
        );
    }

    private String safe(
            String value
    ) {

        return value == null
                ? ""
                : value;
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

        if (appointmentTable != null) {

            appointmentTable.setBackground(
                    theme.getSurfaceColor()
            );

            appointmentTable.setForeground(
                    theme.getTextColor()
            );

            appointmentTable
                    .getTableHeader()
                    .setBackground(
                            theme.getSurfaceColor()
                    );

            appointmentTable
                    .getTableHeader()
                    .setForeground(
                            theme.getTextColor()
                    );

            appointmentTable
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

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );
    }
}