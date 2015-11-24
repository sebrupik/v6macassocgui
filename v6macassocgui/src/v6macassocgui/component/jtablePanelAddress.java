package v6macassocgui.component;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

import v6macassocgui.v6macassocgui;
import v6macassocgui.objects.addressCacheObject;
import v6macassocgui.objects.acoHash;

public class jtablePanelAddress extends jtablePanel {
    private final String _CLASS;
    
    private timelinePanel _timelinepanel;
    //private HashMap<String, addressCacheObject> _addressHash;
    private acoHash _acoh;
    
    /**
     * 
     * 
     * 
     * @param _owner
     * @param _parent
     * @param title
     * @param psStrings
     * @param columns
     * @param split Do you want the the JTable to be added to a JSplitPane, allowing to you add a resizeable component to the bottom of the panel
     */
    public jtablePanelAddress(v6macassocgui _owner, projectPanel3 _parent, String title, String[] psStrings, String[] columns, boolean split) {
        super(_owner, _parent, title, psStrings, columns, split);
        this._CLASS = this.getClass().getName();
        //this._addressHash = new HashMap();
        //this._acoh = new acoHash();
        
        this._timelinepanel = new timelinePanel(this);
        
        if(split) {
            ((javax.swing.JSplitPane)((BorderLayout)super.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setBottomComponent(_timelinepanel);
        } else {
            super.add(_timelinepanel, BorderLayout.SOUTH);
        }
        
    }
    
    @Override public void populateFields(ResultSet r) { 
        System.out.println(_CLASS+"/populateFields - starting");
        _rSet = r;
        
        _acoh = null;
        
        DefaultTableModel dtm = (DefaultTableModel)super.getTable().getModel();
        populateFieldsInit(dtm);
        
        System.out.println(_CLASS+"/populateFields - is dtm null "+dtm);
        System.out.println(_CLASS+"/populateFields - is _rset null "+_rSet);
        
        int i=0;
        
        if(_rSet !=null) {
            try {     
                while(_rSet.next()) {
                    if(_acoh == null)
                        _acoh = new acoHash(_rSet.getString("ipv6_address"));
                    dtm.addRow(new Object[]{_rSet.getTimestamp("timestamp"), _rSet.getString("ipv6_address"), _rSet.getString("source") });
                    _acoh.addNewAddressTimestamp(_rSet.getString("ipv6_address"), -1, _rSet.getTimestamp("timestamp"));
                    i++;
                }
                System.out.println(_CLASS+"populateFields - results returned: "+i);
            } catch(SQLException sqle) { super.getOwner().log(java.util.logging.Level.SEVERE, _CLASS, "populatesFields", sqle); }
        }
        System.out.println(_CLASS+"/populateFields - finished");
        
        /*java.util.Iterator it = _addressHash.entrySet().iterator();
        while(it.hasNext()) {
            java.util.Map.Entry pair = (java.util.Map.Entry)it.next();
            System.out.println(pair.getValue().toString());
        }*/
    }
    
    public acoHash getAcoHash() { return this._acoh; }
}