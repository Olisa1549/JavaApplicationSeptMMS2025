package hospital.gui;

import hospital.database.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Unified staff directory backed directly by the LifeSaver database. */
public class AllStaffPanel extends JPanel implements ThemeManager.ThemeChangeListener {
    private final DefaultTableModel model;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final AppTextField searchField;
    private final JLabel countLabel;

    public AllStaffPanel() {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        JLabel heading = new JLabel("All Staff");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 25));
        JLabel subtitle = new JLabel("One live directory for every member of the LifeSaver care team.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel = new JLabel("Loading staff…");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        title.add(heading);
        title.add(Box.createVerticalStrut(3));
        title.add(subtitle);
        title.add(Box.createVerticalStrut(4));
        title.add(countLabel);
        top.add(title, BorderLayout.NORTH);

        JPanel actions = new JPanel(new BorderLayout(10, 0));
        actions.setOpaque(false);

        searchField = new AppTextField();
        searchField.setPreferredSize(new Dimension(240, 38));
        searchField.putClientProperty("JTextField.placeholderText", "Search staff, role, department…");
        actions.add(searchField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        buttonPanel.setOpaque(false);
        AppButton add = new AppButton("ADD");
        AppButton edit = new AppButton("EDIT");
        AppButton delete = new AppButton("DELETE");
        AppButton refresh = new AppButton("REFRESH");
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(refresh);
        actions.add(buttonPanel, BorderLayout.EAST);

        top.add(actions, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Staff ID", "Name", "Gender", "Role", "Department", "Phone", "Employment Date"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        DocumentListener filter = new DocumentListener() {
            private void apply() {
                String text = searchField.getText().trim();
                sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text)));
            }
            public void insertUpdate(DocumentEvent e) { apply(); }
            public void removeUpdate(DocumentEvent e) { apply(); }
            public void changedUpdate(DocumentEvent e) { apply(); }
        };
        searchField.getDocument().addDocumentListener(filter);
        refresh.addActionListener(e -> loadStaff());
        add.addActionListener(e -> openEditor(null));
        edit.addActionListener(e -> { Integer id = selectedStaffId(); if(id != null) openEditor(id); });
        delete.addActionListener(e -> deleteSelected());

        ThemeManager.addThemeChangeListener(this);
        applyTheme(ThemeManager.getCurrentTheme());
        loadStaff();
    }

    private Integer selectedStaffId() { int r=table.getSelectedRow(); if(r<0){JOptionPane.showMessageDialog(this,"Select a staff member first.","LifeSaver",JOptionPane.INFORMATION_MESSAGE);return null;} return Integer.valueOf(table.getValueAt(table.convertRowIndexToModel(r),0).toString()); }
    private void openEditor(Integer id){ Window w=SwingUtilities.getWindowAncestor(this); StaffDialog d=new StaffDialog(w,id); d.setVisible(true); if(d.isSaved()) loadStaff(); }
    private void deleteSelected(){ Integer id=selectedStaffId(); if(id==null)return; if(JOptionPane.showConfirmDialog(this,"Delete staff member #"+id+"? This removes the base staff record and linked account/subtype records.","Confirm deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)!=JOptionPane.YES_OPTION)return; try(Connection c=DatabaseConnection.getConnection()){c.setAutoCommit(false);try(PreparedStatement p=c.prepareStatement("SELECT PersonId FROM Staff WHERE StaffId=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Staff member not found.");int pid=r.getInt(1);String[] sql={"DELETE FROM Users WHERE StaffId=?","DELETE FROM Doctor WHERE StaffId=?","DELETE FROM Nurse WHERE StaffId=?","DELETE FROM Pharmacist WHERE StaffId=?","DELETE FROM LaboratoryTechnician WHERE StaffId=?","DELETE FROM Staff WHERE StaffId=?"};for(String q:sql)try(PreparedStatement x=c.prepareStatement(q)){x.setInt(1,id);x.executeUpdate();}try(PreparedStatement x=c.prepareStatement("DELETE FROM Person WHERE PersonId=?")){x.setInt(1,pid);x.executeUpdate();}}}c.commit();loadStaff();}catch(Exception ex){JOptionPane.showMessageDialog(this,"Unable to delete staff member.\n\n"+ex.getMessage(),"LifeSaver",JOptionPane.ERROR_MESSAGE);}}

    private void loadStaff() {
        countLabel.setText("Loading staff…");
        new SwingWorker<List<Object[]>, Void>() {
            @Override protected List<Object[]> doInBackground() throws Exception {
                List<Object[]> rows = new ArrayList<>();
                String sql = "SELECT s.StaffId, p.FirstName, p.LastName, p.Gender, s.StaffRole, " +
                        "d.Name AS DepartmentName, p.Phone, s.EmploymentDate " +
                        "FROM Staff s JOIN Person p ON p.PersonId=s.PersonId " +
                        "LEFT JOIN Department d ON d.DepartmentId=s.DepartmentId ORDER BY p.FirstName, p.LastName";
                try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rows.add(new Object[]{rs.getInt("StaffId"),
                                rs.getString("FirstName") + " " + rs.getString("LastName"),
                                rs.getString("Gender"), rs.getString("StaffRole"),
                                rs.getString("DepartmentName"), rs.getString("Phone"), rs.getDate("EmploymentDate")});
                    }
                }
                return rows;
            }
            @Override protected void done() {
                try {
                    List<Object[]> rows = get();
                    model.setRowCount(0);
                    for (Object[] row : rows) model.addRow(row);
                    countLabel.setText(rows.size() + " staff member" + (rows.size() == 1 ? "" : "s") + " • live database");
                } catch (Exception ex) {
                    model.setRowCount(0);
                    countLabel.setText("Database unavailable");
                    JOptionPane.showMessageDialog(AllStaffPanel.this,
                            "Unable to load staff directory.\n\n" + rootMessage(ex),
                            "LifeSaver Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private String rootMessage(Exception ex) {
        Throwable t = ex;
        while (t.getCause() != null) t = t.getCause();
        return t.getMessage() == null ? t.toString() : t.getMessage();
    }

    @Override public void themeChanged(Theme newTheme) { applyTheme(newTheme); }
    private void applyTheme(Theme theme) {
        if (theme == null || table == null) return;
        setBackground(theme.getBackgroundColor());
        table.setBackground(theme.getSurfaceColor());
        table.setForeground(theme.getTextColor());
        table.getTableHeader().setBackground(theme.getSurfaceColor());
        table.getTableHeader().setForeground(theme.getTextColor());
        countLabel.setForeground(theme.getSecondaryTextColor());
        repaint();
    }

    public void dispose() { ThemeManager.removeThemeChangeListener(this); }
}
