package v6macassocgui.component;

import v6macassocgui.v6macassocgui;
import v6macassocgui.component.projectPanel3;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.*;
//import javax.swing.border.TitledBorder;
import java.sql.*;


/**
 * A wrapper class around a JTable with various methods to update and extract 
 * info
 * 
 * @author snr
 */
public abstract class jtablePanel extends JPanel {
    private final String _CLASS;
    private String _macAddress;
    private String[] _columns;
    private JTable _table;
    
    private v6macassocgui _owner;
    private projectPanel3 _parent;
    
    private PreparedStatement selectPS, selectDetailPS;
    ResultSet _rSet;
    
    public jtablePanel(v6macassocgui _owner, projectPanel3 _parent, String title, String[] psStrings, String[] _columns) {
        this._owner = _owner;
        this._parent = _parent;
        this._columns = _columns;
        this._CLASS = this.getClass().getName();
        
        
        selectPS = _owner.getdbConnection().getPS(psStrings[0]);
        selectDetailPS = _owner.getdbConnection().getPS(psStrings[1]);
        
        setLayout(new BorderLayout(2,2));
        addPanels(title);
    }
    
    private void addPanels(String title) {
        add(genTablePanel(title), BorderLayout.CENTER);
    }
    
    private JPanel genTablePanel(String title) {
        JPanel tablePanel = new JPanel();  tablePanel.setLayout(new BorderLayout(2,2));  tablePanel.setBorder(BorderFactory.createTitledBorder(title));
        
        _table = new JTable(new DefaultTableModel());
        /*_table = new JTable(new DefaultTableModel() {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            @Override public Class getColumnClass(int column) {
                    return getValueAt(0, column).getClass();
            } 
        });*/
        
        _table.setComponentPopupMenu(genTableMenu());
         
        tablePanel.add(new JScrollPane(_table), BorderLayout.CENTER);
        return tablePanel;
    }
    
    public JPopupMenu genTableMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem detailMI = new JMenuItem("Detail");  detailMI.addActionListener(new popupDetailAct());
         
        menu.add(detailMI);
         
        return menu;
    }
    
    public void refreshTable() {
        refreshTable(_macAddress);
    }
     
    public void refreshTable(String ma) {
        _macAddress = ma;
        try {
            selectPS.setString(1, _macAddress);
            populateFields(selectPS.executeQuery());
            //populateFields( _owner.getdbConnection().executeQuery(selectPS) );
        //} catch(jdbcApp.exceptions.NullDBConnectionException ndbce) { _owner.exceptionEncountered(_CLASS+"/refreshTable", ndbce);
        } catch(SQLException sqle) { _owner.exceptionEncountered(_CLASS+"/refreshTable", sqle);
        }
    }
    
     public void clearTable() {
        System.out.println(_CLASS+"/clearTable - entered");
        DefaultTableModel dtm = (DefaultTableModel)_table.getModel();
        dtm.setColumnCount(0);
        dtm.setRowCount(0);
        System.out.println(_CLASS+"/clearTable - exited");
    }
    
    public abstract void populateFields(ResultSet r);
    
    
    public void populateFieldsInit(DefaultTableModel dtm) {
        clearTable();
        
        for(String name :  _columns)
            dtm.addColumn(name);
    }
    
    public JTable getTable() { return _table; }
    public v6macassocgui getOwner() { return _owner; }
    
    //*****************************
    
    class popupDetailAct extends AbstractAction {
        @Override public void actionPerformed(java.awt.event.ActionEvent e) {
            int row = _table.getSelectedRow();
            
            
        }
    }
}
