package hospital.gui;

import hospital.database.DatabaseConnection;
import hospital.gui.components.AppButton;
import hospital.gui.components.AppComboBox;
import hospital.gui.components.AppTextField;
import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;
import hospital.models.Department;
import hospital.dao.DepartmentDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/** Base staff create/edit dialog. Specialist details remain managed by specialist screens. */
public class StaffDialog extends JDialog implements ThemeManager.ThemeChangeListener {
    private final Integer staffId;
    private final AppTextField first = new AppTextField(), last = new AppTextField(), phone = new AppTextField(), email = new AppTextField(), salary = new AppTextField();
    private final JComboBox<String> gender = new AppComboBox<>(), role = new AppComboBox<>();
    private final JComboBox<Department> department = new AppComboBox<>();
    private final AppTextField employment = new AppTextField();
    private boolean saved;

    public StaffDialog(Window owner, Integer staffId) {
        super(owner, staffId == null ? "Add Staff Member" : "Edit Staff Member", ModalityType.APPLICATION_MODAL);
        this.staffId=staffId; setSize(700,520); setLocationRelativeTo(owner); setResizable(false); build(); loadDepartments(); if(staffId!=null) loadStaff(); ThemeManager.addThemeChangeListener(this); applyTheme(ThemeManager.getCurrentTheme());
    }
    private void build(){
        JPanel root=new JPanel(new BorderLayout(12,12)); root.setBorder(BorderFactory.createEmptyBorder(18,22,16,22));
        JLabel title=new JLabel(staffId==null?"Add Staff Member":"Edit Staff Member"); title.setFont(new Font("Segoe UI",Font.BOLD,22)); root.add(title,BorderLayout.NORTH);
        JPanel form=new JPanel(new GridLayout(5,2,10,10)); form.setOpaque(false);
        add(form,"First Name",first); add(form,"Last Name",last); add(form,"Gender",gender); add(form,"Phone",phone); add(form,"Email",email); add(form,"Employment Date (YYYY-MM-DD)",employment); add(form,"Salary",salary); add(form,"Role",role); add(form,"Department",department); root.add(form,BorderLayout.CENTER);
        gender.addItem("Male"); gender.addItem("Female"); gender.addItem("Other");
        role.addItem("STAFF"); role.addItem("DOCTOR"); role.addItem("NURSE"); role.addItem("PHARMACIST"); role.addItem("LABORATORY_TECHNICIAN");
        JPanel foot=new JPanel(new FlowLayout(FlowLayout.RIGHT)); AppButton cancel=new AppButton("CANCEL"), save=new AppButton(staffId==null?"ADD STAFF":"SAVE CHANGES"); cancel.addActionListener(e->dispose()); save.addActionListener(e->save()); foot.add(cancel); foot.add(save); root.add(foot,BorderLayout.SOUTH); setContentPane(root);
    }
    private void add(JPanel p,String label,JComponent c){ JPanel cell=new JPanel(new BorderLayout(5,4)); cell.setOpaque(false); JLabel l=new JLabel(label); l.setFont(new Font("Segoe UI",Font.PLAIN,11)); cell.add(l,BorderLayout.NORTH); c.setPreferredSize(new Dimension(250,34)); cell.add(c,BorderLayout.CENTER); p.add(cell); }
    private void loadDepartments(){ try{ List<Department> ds=new DepartmentDAO().findAllDepartments(); for(Department d:ds) department.addItem(d); department.setRenderer(new DefaultListCellRenderer(){@Override public Component getListCellRendererComponent(JList<?> l,Object v,int i,boolean s,boolean f){super.getListCellRendererComponent(l,v,i,s,f);setText(v instanceof Department?((Department)v).getName():"");return this;}});}catch(Exception ignored){} }
    private void loadStaff(){ String sql="SELECT p.FirstName,p.LastName,p.Gender,p.Phone,p.Email,s.EmploymentDate,s.Salary,s.StaffRole,s.DepartmentId FROM Staff s JOIN Person p ON p.PersonId=s.PersonId WHERE s.StaffId=?"; try(Connection c=DatabaseConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,staffId);try(ResultSet r=ps.executeQuery()){if(r.next()){first.setText(r.getString("FirstName"));last.setText(r.getString("LastName"));gender.setSelectedItem(r.getString("Gender"));phone.setText(r.getString("Phone"));email.setText(r.getString("Email"));Date d=r.getDate("EmploymentDate"); if(d!=null)employment.setText(d.toLocalDate().toString());salary.setText(r.getString("Salary"));role.setSelectedItem(r.getString("StaffRole"));int did=r.getInt("DepartmentId");for(int i=0;i<department.getItemCount();i++)if(department.getItemAt(i).getId()==did)department.setSelectedIndex(i);}}}catch(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"LifeSaver",JOptionPane.ERROR_MESSAGE);}}
    private void save(){ if(first.getText().trim().isEmpty()||last.getText().trim().isEmpty()){JOptionPane.showMessageDialog(this,"First and last name are required.");return;} try(Connection c=DatabaseConnection.getConnection()){c.setAutoCommit(false); try{if(staffId==null){String pi="INSERT INTO Person(FirstName,LastName,Gender,Phone,Email) VALUES(?,?,?,?,?)";int pid;try(PreparedStatement p=c.prepareStatement(pi,Statement.RETURN_GENERATED_KEYS)){p.setString(1,first.getText().trim());p.setString(2,last.getText().trim());p.setString(3,(String)gender.getSelectedItem());p.setString(4,phone.getText().trim());p.setString(5,email.getText().trim());p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();pid=k.getInt(1);}} String ss="INSERT INTO Staff(StaffRole,EmploymentDate,Salary,DepartmentId,PersonId) VALUES(?,?,?,?,?)";try(PreparedStatement p=c.prepareStatement(ss)){p.setString(1,(String)role.getSelectedItem());setDate(p,2,employment.getText());setSalary(p,3,salary.getText());setDept(p,4);p.setInt(5,pid);p.executeUpdate();}}else{int pid;try(PreparedStatement p=c.prepareStatement("SELECT PersonId FROM Staff WHERE StaffId=?")){p.setInt(1,staffId);try(ResultSet r=p.executeQuery()){r.next();pid=r.getInt(1);}}try(PreparedStatement p=c.prepareStatement("UPDATE Person SET FirstName=?,LastName=?,Gender=?,Phone=?,Email=? WHERE PersonId=?")){p.setString(1,first.getText().trim());p.setString(2,last.getText().trim());p.setString(3,(String)gender.getSelectedItem());p.setString(4,phone.getText().trim());p.setString(5,email.getText().trim());p.setInt(6,pid);p.executeUpdate();}try(PreparedStatement p=c.prepareStatement("UPDATE Staff SET StaffRole=?,EmploymentDate=?,Salary=?,DepartmentId=? WHERE StaffId=?")){p.setString(1,(String)role.getSelectedItem());setDate(p,2,employment.getText());setSalary(p,3,salary.getText());setDept(p,4);p.setInt(5,staffId);p.executeUpdate();}}c.commit();saved=true;dispose();}catch(Exception e){c.rollback();throw e;}}catch(Exception e){JOptionPane.showMessageDialog(this,"Unable to save staff.\n\n"+e.getMessage(),"LifeSaver",JOptionPane.ERROR_MESSAGE);}}
    private void setDate(PreparedStatement p,int i,String s)throws SQLException{if(s.trim().isEmpty())p.setNull(i,Types.DATE);else p.setDate(i,Date.valueOf(LocalDate.parse(s.trim())));}
    private void setSalary(PreparedStatement p,int i,String s)throws SQLException{if(s.trim().isEmpty())p.setNull(i,Types.DECIMAL);else p.setBigDecimal(i,new java.math.BigDecimal(s.trim()));}
    private void setDept(PreparedStatement p,int i)throws SQLException{Department d=(Department)department.getSelectedItem();if(d==null)p.setNull(i,Types.INTEGER);else p.setInt(i,d.getId());}
    public boolean isSaved(){return saved;}
    @Override public void themeChanged(Theme t){applyTheme(t);} private void applyTheme(Theme t){getContentPane().setBackground(t.getBackgroundColor());}
    @Override public void dispose(){ThemeManager.removeThemeChangeListener(this);super.dispose();}
}
