package hospital.gui;

import hospital.dao.*;
import hospital.database.DatabaseConnection;
import hospital.gui.components.AppButton;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * LifeSaver's operational command center. It exposes the database-backed
 * clinical domains that previously had no GUI route, without duplicating the
 * existing specialist CRUD screens.
 */
public class HospitalOperationsPanel extends JPanel implements ThemeManager.ThemeChangeListener {
    private final JTabbedPane tabs = new JTabbedPane();
    private final JLabel status = new JLabel("Ready");
    private final JPanel overview = new JPanel(new BorderLayout(12, 12));
    private final JTable records = table();
    private final JTable admissions = table();
    private final JTable lab = table();
    private final JTable pharmacy = table();
    private final JTable billing = table();
    private final JTable payments = table();
    private final JTable prescriptions = table();
    private final JTable inventory = table();
    private final JTable departments = table();
    private final JTable beds = table();
    private final String initialTab;

    public HospitalOperationsPanel() { this(null); }
    public HospitalOperationsPanel(String initialTab) {
        this.initialTab = initialTab;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setOpaque(false);
        build();
        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
        SwingUtilities.invokeLater(this::refreshAll);
    }

    private void build() {
        JPanel heading = new JPanel(new BorderLayout(10, 5)); heading.setOpaque(false);
        JPanel text = new JPanel(); text.setOpaque(false); text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Hospital Operations Center"); title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        JLabel sub = new JLabel("Clinical records, admissions, laboratory, pharmacy, billing and infrastructure in one workspace."); sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.add(title); text.add(Box.createVerticalStrut(3)); text.add(sub); heading.add(text, BorderLayout.WEST);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0)); actions.setOpaque(false);
        AppButton refresh = new AppButton("REFRESH ALL"); AppButton export = new AppButton("EXPORT SNAPSHOT");
        refresh.addActionListener(e -> refreshAll()); export.addActionListener(e -> exportSnapshot());
        actions.add(refresh); actions.add(export); heading.add(actions, BorderLayout.EAST);
        add(heading, BorderLayout.NORTH);

        tabs.addTab("Overview", overview);
        tabs.addTab("Medical Records", wrap(records,"MedicalRecord"));
        tabs.addTab("Admissions", wrap(admissions,"Admission"));
        tabs.addTab("Laboratory", wrap(lab,"LaboratoryTest"));
        tabs.addTab("Pharmacy", wrap(pharmacy,"Prescription"));
        tabs.addTab("Billing", wrap(billing,"Invoice"));
        tabs.addTab("Payments", wrap(payments,"Payment"));
        tabs.addTab("Prescriptions", wrap(prescriptions,"Prescription"));
        tabs.addTab("Inventory", wrap(inventory,"Medication"));
        tabs.addTab("Departments", wrap(departments,"Department"));
        tabs.addTab("Beds", wrap(beds,"Bed"));
        add(tabs, BorderLayout.CENTER);
        status.setBorder(BorderFactory.createEmptyBorder(3, 5, 0, 5));
        add(status, BorderLayout.SOUTH);
        if (initialTab != null) selectTab(initialTab);
    }

    private JPanel wrap(JTable table, String dbTable) {
        JPanel p = new JPanel(new BorderLayout(8,8)); p.setOpaque(false);
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT,6,0)); bar.setOpaque(false);
        JButton manage = new JButton("MANAGE RECORDS");
        manage.addActionListener(e -> new DatabaseTableManagerDialog(SwingUtilities.getWindowAncestor(this), dbTable).setVisible(true));
        bar.add(manage); p.add(bar, BorderLayout.NORTH); p.add(new JScrollPane(table), BorderLayout.CENTER); return p;
    }
    private JTable table() { JTable t = new JTable(); t.setRowHeight(33); t.setFont(new Font("Segoe UI", Font.PLAIN, 12)); t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); t.setAutoCreateRowSorter(true); t.setFillsViewportHeight(true); return t; }

    private void refreshAll() {
        status.setText("Refreshing clinical workspace…");
        new SwingWorker<Snapshot, Void>() {
            @Override protected Snapshot doInBackground() { return Snapshot.load(); }
            @Override protected void done() {
                try { Snapshot s = get(); render(s); status.setText("Live data refreshed • " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))); }
                catch (Exception e) { status.setText("Refresh failed: " + message(e)); }
            }
        }.execute();
    }

    private void render(Snapshot s) {
        fill(records, new String[]{"Record ID","Patient","Created"}, s.records);
        fill(admissions, new String[]{"Admission ID","Patient","Bed","Admission","Discharge","Status","Reason"}, s.admissions);
        fill(lab, new String[]{"Test ID","Patient","Technician","Test","Date","Status","Result"}, s.lab);
        fill(pharmacy, new String[]{"Prescription ID","Patient","Doctor","Date","Items"}, s.prescriptions);
        fill(billing, new String[]{"Invoice ID","Patient","Date","Amount","Status"}, s.billing);
        fill(payments, new String[]{"Payment ID","Invoice","Amount","Date","Method"}, s.payments);
        fill(prescriptions, new String[]{"Prescription ID","Patient","Doctor","Date","Items"}, s.prescriptions);
        fill(inventory, new String[]{"Medication ID","Medication","Form","Price","Stock"}, s.inventory);
        fill(departments, new String[]{"Department ID","Department","Description"}, s.departments);
        fill(beds, new String[]{"Bed ID","Bed","Room","Ward","Occupied"}, s.beds);
        buildOverview(s);
    }

    private void buildOverview(Snapshot s) {
        overview.removeAll();
        JPanel cards = new JPanel(new GridLayout(2, 4, 12, 12)); cards.setOpaque(false);
        cards.add(card("MEDICAL RECORDS", s.records.size())); cards.add(card("ADMISSIONS", s.admissions.size()));
        cards.add(card("LAB TESTS", s.lab.size())); cards.add(card("PRESCRIPTIONS", s.prescriptions.size()));
        cards.add(card("INVOICES", s.billing.size())); cards.add(card("PAYMENTS", s.payments.size()));
        cards.add(card("MEDICATIONS", s.inventory.size())); cards.add(card("BEDS", s.beds.size()));
        overview.add(cards, BorderLayout.NORTH);
        JPanel info = new JPanel(new GridLayout(1, 2, 12, 12)); info.setOpaque(false);
        info.add(summary("FINANCIAL POSITION", "Outstanding invoices", money(s.pending), "Recorded payments", money(s.paid)));
        info.add(summary("CLINICAL FLOW", "Open admissions", String.valueOf(s.openAdmissions), "Available beds", String.valueOf(s.availableBeds)));
        overview.add(info, BorderLayout.CENTER);
        overview.revalidate(); overview.repaint();
    }

    private JPanel card(String title, int value) {
        JPanel p = new JPanel(new BorderLayout(5, 5)); p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210,215,220)), BorderFactory.createEmptyBorder(14,16,14,16)));
        JLabel a = new JLabel(title); a.setFont(new Font("Segoe UI", Font.BOLD, 11)); JLabel b = new JLabel(String.valueOf(value)); b.setFont(new Font("Segoe UI", Font.BOLD, 27)); p.add(a, BorderLayout.NORTH); p.add(b, BorderLayout.CENTER); return p;
    }
    private JPanel summary(String title, String a, String av, String b, String bv) {
        JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210,215,220)), BorderFactory.createEmptyBorder(16,18,16,18)));
        JLabel h = new JLabel(title); h.setFont(new Font("Segoe UI", Font.BOLD, 12)); p.add(h); p.add(Box.createVerticalStrut(12)); p.add(line(a,av)); p.add(Box.createVerticalStrut(8)); p.add(line(b,bv)); return p;
    }
    private JPanel line(String a, String b) { JPanel p = new JPanel(new BorderLayout()); p.setOpaque(false); p.add(new JLabel(a), BorderLayout.WEST); JLabel v = new JLabel(b); v.setFont(new Font("Segoe UI", Font.BOLD, 14)); p.add(v, BorderLayout.EAST); return p; }

    private void fill(JTable table, String[] columns, List<Object[]> rows) {
        DefaultTableModel m = new DefaultTableModel(columns, 0) { @Override public boolean isCellEditable(int r,int c){return false;} };
        for (Object[] row: rows) m.addRow(row); table.setModel(m);
    }

    private void selectTab(String name) { for (int i=0;i<tabs.getTabCount();i++) if (tabs.getTitleAt(i).equalsIgnoreCase(name)) { tabs.setSelectedIndex(i); return; } }

    private void exportSnapshot() {
        JFileChooser chooser = new JFileChooser(); chooser.setSelectedFile(new File("LifeSaver_Operations_Snapshot.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter out = new PrintWriter(chooser.getSelectedFile())) {
            out.println("LIFESAVER HOSPITAL OPERATIONS SNAPSHOT"); out.println("Generated," + java.time.LocalDateTime.now()); out.println();
            out.println("Metric,Value");
            out.println("Medical Records," + records.getRowCount()); out.println("Admissions," + admissions.getRowCount()); out.println("Laboratory Tests," + lab.getRowCount());
            out.println("Prescriptions," + prescriptions.getRowCount()); out.println("Invoices," + billing.getRowCount()); out.println("Payments," + payments.getRowCount()); out.println("Medications," + inventory.getRowCount()); out.println("Beds," + beds.getRowCount());
            JOptionPane.showMessageDialog(this, "Snapshot exported successfully.", "LifeSaver", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Export failed: " + message(e), "LifeSaver", JOptionPane.ERROR_MESSAGE); }
    }

    private String money(double value) { return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-NG")).format(value); }
    private String message(Exception e) { Throwable t=e; while(t.getCause()!=null)t=t.getCause(); return t.getMessage()==null?t.toString():t.getMessage(); }

    @Override public void themeChanged(Theme newTheme) { applyTheme(newTheme); }
    private void applyTheme(Theme theme) {
        if (theme == null) return; setBackground(theme.getBackgroundColor()); status.setForeground(theme.getSecondaryTextColor());
        for (Component c : overview.getComponents()) styleTree(c, theme); repaint();
    }
    private void styleTree(Component c, Theme theme) {
        if (c instanceof JComponent jc) { jc.setForeground(theme.getTextColor()); }
        if (c instanceof Container con) for (Component child: con.getComponents()) styleTree(child, theme);
    }
    public void dispose() { ThemeManager.removeThemeChangeListener(this); }

    private static class Snapshot {
        List<Object[]> records=new java.util.ArrayList<>(), admissions=new java.util.ArrayList<>(), lab=new java.util.ArrayList<>(), billing=new java.util.ArrayList<>(), payments=new java.util.ArrayList<>(), prescriptions=new java.util.ArrayList<>(), inventory=new java.util.ArrayList<>(), departments=new java.util.ArrayList<>(), beds=new java.util.ArrayList<>();
        double pending, paid; int openAdmissions, availableBeds;
        static Snapshot load() {
            Snapshot s=new Snapshot();
            try {
                for (MedicalRecord r:new MedicalRecordDAO().findAllMedicalRecords()) s.records.add(new Object[]{r.getId(), name(r.getPatient()), r.getCreatedDate()});
                for (Admission a:new AdmissionDAO().findAllAdmissions()) { boolean open=a.getStatus()!=null&&!a.getStatus().equalsIgnoreCase("Discharged"); if(open)s.openAdmissions++; s.admissions.add(new Object[]{a.getId(),name(a.getPatient()),a.getBed()==null?"—":a.getBed().getBedNumber(),a.getAdmissionDate(),a.getDischargeDate(),a.getStatus(),a.getReason()}); }
                for (LaboratoryTest t:new LaboratoryTestDAO().findAllLaboratoryTests()) s.lab.add(new Object[]{t.getId(),name(t.getPatient()),t.getTechnician()==null?"—":name(t.getTechnician()),t.getTestName(),t.getTestDate(),t.getStatus(),t.getResult()});
                for (Prescription p:new PrescriptionDAO().findAllPrescriptions()) s.prescriptions.add(new Object[]{p.getId(),name(p.getPatient()),p.getDoctor()==null?"—":name(p.getDoctor()),p.getPrescriptionDate(),p.getItems()==null?0:p.getItems().size()});
                for (Invoice i:new InvoiceDAO().findAllInvoices()) { if(i.getStatus()!=null&&i.getStatus().equalsIgnoreCase("Paid"))s.paid+=i.getTotalAmount();else s.pending+=i.getTotalAmount(); s.billing.add(new Object[]{i.getId(),name(i.getPatient()),i.getInvoiceDate(),i.getTotalAmount(),i.getStatus()}); }
                for (Payment p:new PaymentDAO().findAllPayments()) s.payments.add(new Object[]{p.getId(),p.getInvoice()==null?"—":p.getInvoice().getId(),p.getAmount(),p.getPaymentDate(),p.getPaymentMethod()});
                for (Medication m:new MedicationDAO().findAllMedications()) s.inventory.add(new Object[]{m.getId(),m.getName(),m.getDosageForm(),m.getPrice(),m.getQuantityInStock()});
                for (Department d:new DepartmentDAO().findAllDepartments()) s.departments.add(new Object[]{d.getId(),d.getName(),d.getDescription()});
                for (Bed b:new BedDAO().findAllBeds()) { if(!b.isOccupied())s.availableBeds++; s.beds.add(new Object[]{b.getId(),b.getBedNumber(),b.getRoom()==null?"—":b.getRoom().getRoomNumber(),b.getRoom()==null||b.getRoom().getWard()==null?"—":b.getRoom().getWard().getName(),b.isOccupied()?"Occupied":"Available"}); }
            } catch(Exception e) { throw new RuntimeException(e); }
            return s;
        }
        static String name(Patient p){return p==null?"—":p.getFirstName()+" "+p.getLastName();}
        static String name(Staff s){return s==null?"—":s.getFirstName()+" "+s.getLastName();}
    }
}
