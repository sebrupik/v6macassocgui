package v6macassocgui.component;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import v6macassocgui.v6macassocgui;

public class jtablePanelAuth extends jtablePanel {
    private final String _CLASS;
    
    public jtablePanelAuth(v6macassocgui _owner, projectPanel3 _parent, String title, String[] psStrings, String[] columns, String _macAddress) {
        super(_owner, _parent, title, psStrings, columns, _macAddress);
        this._CLASS = this.getClass().getName();
    }
    
    @Override public void populateFields(ResultSet r) { 
        System.out.println(_CLASS+"/populateFields - starting");
        _rSet = r;
         
        DefaultTableModel dtm = (DefaultTableModel)super.getTable().getModel();
        populateFieldsInit(dtm);
         
        if(_rSet !=null) {
            try {
                while(_rSet.next()) {
                    dtm.addRow(new Object[]{_rSet.getTimestamp("timestamp"), _rSet.getString("source") });
                }
            } catch(SQLException sqle) { super.getOwner().exceptionEncountered(_CLASS+"/populatefields", sqle); }
        }
        System.out.println(_CLASS+"/populateFields - finished");
    }
}