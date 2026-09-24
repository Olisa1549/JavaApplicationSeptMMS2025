package hospital.gui;

import hospital.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

/** Generic database-backed CRUD workspace for operational tables. */
public class DatabaseTableManagerDialog extends JDialog {
    private final String tableName;
    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final JLabel status = new JLabel("Ready");
    private final List<Column> columns = new ArrayList<>();
    private String primaryKey;

    private static final Set<String> ALLOWED = Set.of("MedicalRecord","Admission","LaboratoryTest","Prescription","Invoice","Payment","Medication","Department","Bed","Diagnosis","Treatment","PrescriptionItem","InvoiceItem","MedicationDispensing");

    public DatabaseTableManagerDialog(Window owner, String tableName) {
        super(owner, "LifeSaver • Manage " + tableName, ModalityType.APPLICATION_MODAL);
        if (!ALLOWED.contains(tableName)) throw new IllegalArgumentException("Unsupported table");
        this.tableName=tableName; setSize(1000,650); setLocationRelativeTo(owner); build(); loadMetadata(); loadRows();
    }
    private void build(){
        JPanel root=new JPanel(new BorderLayout(10,10)); root.setBorder(BorderFactory.createEmptyBorder(14,16,12,16));
        JPanel top=new JPanel(new BorderLayout()); JLabel h=new JLabel("Manage " + tableName); h.setFont(new Font("Segoe UI",Font.BOLD,22)); top.add(h,BorderLayout.WEST);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,6,0)); JButton add=new JButton("ADD"), edit=new JButton("EDIT"), del=new JButton("DELETE"), refresh=new JButton("REFRESH"); actions.add(add);actions.add(edit);actions.add(del);actions.add(refresh);top.add(actions,BorderLayout.EAST);root.add(top,BorderLayout.NORTH);
        table.setModel(model); table.setAutoCreateRowSorter(true); table.setRowHeight(30); table.setFillsViewportHeight(true); root.add(new JScrollPane(table),BorderLayout.CENTER); root.add(status,BorderLayout.SOUTH); setContentPane(root);
        add.addActionListener(e->editRecord(null)); edit.addActionListener(e->{Map<String,Object> row=selected();if(row!=null)editRecord(row);}); del.addActionListener(e->deleteSelected()); refresh.addActionListener(e->loadRows());
    }
    private void loadMetadata(){ try(Connection c=DatabaseConnection.getConnection()){DatabaseMetaData md=c.getMetaData();try(ResultSet rs=md.getColumns(null,null,tableName,null)){while(rs.next()){Column x=new Column(rs.getString("COLUMN_NAME"),rs.getInt("DATA_TYPE"),rs.getInt("ORDINAL_POSITION"),"YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT")));columns.add(x);}}try(ResultSet rs=md.getPrimaryKeys(null,null,tableName)){if(rs.next())primaryKey=rs.getString("COLUMN_NAME");} }catch(Exception e){status.setText("Metadata error: "+e.getMessage());}}
    private void loadRows(){ if(columns.isEmpty())return; new SwingWorker<List<Object[]>,Void>(){String[] names;protected List<Object[]> doInBackground()throws Exception{names=columns.stream().map(x->x.name).toArray(String[]::new);String order=primaryKey==null?names[0]:primaryKey;List<Object[]> rows=new ArrayList<>();String sql="SELECT TOP 500 * FROM ["+tableName+"] ORDER BY ["+order+"] DESC";try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){while(r.next()){Object[] row=new Object[names.length];for(int i=0;i<names.length;i++)row[i]=r.getObject(names[i]);rows.add(row);}}return rows;}protected void done(){try{List<Object[]> rows=get();model.setDataVector(rows.toArray(new Object[0][]),names);status.setText(rows.size()+" record(s) • showing up to 500");}catch(Exception e){status.setText("Load failed: "+root(e));}}}.execute();}
    private Map<String,Object> selected(){int r=table.getSelectedRow();if(r<0){JOptionPane.showMessageDialog(this,"Select a record first.");return null;}int mr=table.convertRowIndexToModel(r);Map<String,Object> m=new LinkedHashMap<>();for(int i=0;i<columns.size();i++)m.put(columns.get(i).name,model.getValueAt(mr,i));return m;}
    private void editRecord(Map<String,Object> existing){ if(columns.isEmpty())return; JPanel form=new JPanel(new GridBagLayout());List<JComponent> inputs=new ArrayList<>();GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(4,6,4,6);g.fill=GridBagConstraints.HORIZONTAL;g.weightx=1;int row=0;for(Column c:columns){if(c.autoIncrement)continue;g.gridx=0;g.gridy=row;g.weightx=0;form.add(new JLabel(c.name),g);g.gridx=1;g.weightx=1;JComponent in=input(c,existing==null?null:existing.get(c.name));form.add(in,g);inputs.add(in);row++;}JScrollPane sp=new JScrollPane(form);sp.setPreferredSize(new Dimension(620,Math.min(520,70+row*48)));int result=JOptionPane.showConfirmDialog(this,sp,existing==null?"Add Record":"Edit Record",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);if(result!=JOptionPane.OK_OPTION)return;try(Connection c=DatabaseConnection.getConnection()){if(existing==null)insert(c,inputs);else update(c,existing,inputs);loadRows();}catch(Exception e){JOptionPane.showMessageDialog(this,"Operation failed.\n\n"+root(e),"LifeSaver",JOptionPane.ERROR_MESSAGE);}}
    private JComponent input(Column c,Object value){JTextField f=new JTextField(value==null?"":String.valueOf(value));f.setPreferredSize(new Dimension(320,34));return f;}
    private Object val(Column c,JComponent input)throws Exception{String s=((JTextField)input).getText().trim();if(s.isEmpty())return null;return switch(c.sqlType){case Types.INTEGER,Types.SMALLINT,Types.TINYINT->Integer.valueOf(s);case Types.BIGINT->Long.valueOf(s);case Types.DECIMAL,Types.NUMERIC,Types.FLOAT,Types.REAL,Types.DOUBLE->new java.math.BigDecimal(s);case Types.BOOLEAN,Types.BIT->Boolean.valueOf(s);case Types.DATE->java.sql.Date.valueOf(s);case Types.TIMESTAMP,Types.TIMESTAMP_WITH_TIMEZONE->Timestamp.valueOf(s.replace('T',' '));default->s;};}
    private void insert(Connection c,List<JComponent> inputs)throws Exception{List<Column> active=new ArrayList<>();for(Column x:columns)if(!x.autoIncrement)active.add(x);String cols=String.join(",",active.stream().map(x->"["+x.name+"]").toList());String qs=String.join(",",Collections.nCopies(active.size(),"?"));String sql="INSERT INTO ["+tableName+"] ("+cols+") VALUES ("+qs+")";try(PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<active.size();i++)set(p,i+1,active.get(i),val(active.get(i),inputs.get(i)));p.executeUpdate();}}
    private void update(Connection c,Map<String,Object> old,List<JComponent> inputs)throws Exception{if(primaryKey==null)throw new SQLException("No primary key detected");List<Column> active=new ArrayList<>();for(Column x:columns)if(!x.autoIncrement&&!x.name.equalsIgnoreCase(primaryKey))active.add(x);String sets=String.join(",",active.stream().map(x->"["+x.name+"]=?").toList());String sql="UPDATE ["+tableName+"] SET "+sets+" WHERE ["+primaryKey+"]=?";try(PreparedStatement p=c.prepareStatement(sql)){int i=1;for(int j=0;j<active.size();j++)set(p,i++,active.get(j),val(active.get(j),inputs.get(j)));Column pk=columns.stream().filter(x->x.name.equalsIgnoreCase(primaryKey)).findFirst().orElseThrow();set(p,i,pk,old.get(primaryKey));p.executeUpdate();}}
    private void set(PreparedStatement p,int i,Column c,Object v)throws SQLException{if(v==null)p.setNull(i,c.sqlType);else p.setObject(i,v);}
    private void deleteSelected(){Map<String,Object> row=selected();if(row==null||primaryKey==null)return;if(JOptionPane.showConfirmDialog(this,"Delete selected record?","Confirm deletion",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)!=JOptionPane.YES_OPTION)return;try(Connection c=DatabaseConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM ["+tableName+"] WHERE ["+primaryKey+"]=?")){Column pk=columns.stream().filter(x->x.name.equalsIgnoreCase(primaryKey)).findFirst().orElseThrow();set(p,1,pk,row.get(primaryKey));p.executeUpdate();loadRows();}catch(Exception e){JOptionPane.showMessageDialog(this,"Delete failed.\n\n"+root(e),"LifeSaver",JOptionPane.ERROR_MESSAGE);}}
    private String root(Exception e){Throwable t=e;while(t.getCause()!=null)t=t.getCause();return t.getMessage()==null?t.toString():t.getMessage();}
    private static final class Column{final String name;final int sqlType;final int ordinal;final boolean autoIncrement;Column(String n,int t,int o,boolean a){name=n;sqlType=t;ordinal=o;autoIncrement=a;}}
}
