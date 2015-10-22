package v6macassocgui.component;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

import v6macassocgui.v6macassocgui;
import v6macassocgui.objects.addressCacheObject;

public class jtablePanelAddress extends jtablePanel {
    private final String _CLASS;
    
    private timelinePanel _timelinepanel;
    private HashMap<String, addressCacheObject> _addressHash;
    
    public jtablePanelAddress(v6macassocgui _owner, projectPanel3 _parent, String title, String[] psStrings, String[] columns) {
        super(_owner, _parent, title, psStrings, columns);
        this._CLASS = this.getClass().getName();
        this._addressHash = new HashMap();
        
        this._timelinepanel = new timelinePanel();
        
        
        super.add(_timelinepanel, BorderLayout.SOUTH);
    }
    
    @Override public void populateFields(ResultSet r) { 
        System.out.println(_CLASS+"/populateFields - starting");
        _rSet = r;
         
        DefaultTableModel dtm = (DefaultTableModel)super.getTable().getModel();
        populateFieldsInit(dtm);
        
        System.out.println(_CLASS+"/populateFields - is dtm null "+dtm);
        System.out.println(_CLASS+"/populateFields - is _rset null "+_rSet);
        
        int i=0;
        
        if(_rSet !=null) {
            try {
                while(_rSet.next()) {
                    dtm.addRow(new Object[]{_rSet.getTimestamp("timestamp"), _rSet.getString("ipv6_address"), _rSet.getString("source") });
                    this.addNewAddressTimestamp(_rSet.getString("ipv6_address"), -1, _rSet.getTimestamp("timestamp"));
                    i++;
                }
                System.out.println(_CLASS+"populateFields - results returned: "+i);
            } catch(SQLException sqle) { super.getOwner().exceptionEncountered(_CLASS+"/populatefields", sqle); }
        }
        System.out.println(_CLASS+"/populateFields - finished");
        
        java.util.Iterator it = _addressHash.entrySet().iterator();
        String key;
        while(it.hasNext()) {
            java.util.Map.Entry pair = (java.util.Map.Entry)it.next();
            System.out.println(pair.getValue().toString());
        }
    }
    
    /**
     * If an address is not present, save it to hash along with epoch value and the timestamp it appeared.
     * If an address is seen a second time, check that the epoch value is consecutive, if it is assume 
     * address is part of the same session. If not, then some time must have past since it was visible in
     * neighbor cache so it must be part of a new session.
     * 
     * 
     * @param ts
     * @param epcoh if value if -1 create an new addressCacheObjectSession, don't try to create contiguous sessions.
     * @param address 
     */
    
    private void addNewAddressTimestamp(String address, int epoch, Timestamp ts) {
        addressCacheObject aco;
        if (_addressHash.containsKey(address)) {
            aco = _addressHash.get(address);
            aco.addSession(epoch, ts);
            _addressHash.put(address, aco);
        } else {
            _addressHash.put(address, new addressCacheObject(address));
        }
    }
}