package v6macassocgui.component;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import v6macassocgui.v6macassocgui;

public class jtablePanelAddress extends jtablePanel {
    private final String _CLASS;
    
    private timelinePanel timelinepanel;
    private HashMap<String, Timestamp[]> addressHash;
    
    public jtablePanelAddress(v6macassocgui _owner, projectPanel3 _parent, String title, String[] psStrings, String[] columns) {
        super(_owner, _parent, title, psStrings, columns);
        this._CLASS = this.getClass().getName();
        
        this.timelinepanel = new timelinePanel();
        
        super.add(timelinepanel, BorderLayout.SOUTH);
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
                    i++;
                }
                System.out.println(_CLASS+"populateFields - results returned: "+i);
            } catch(SQLException sqle) { super.getOwner().exceptionEncountered(_CLASS+"/populatefields", sqle); }
        }
        System.out.println(_CLASS+"/populateFields - finished");
    }
    
    /**
     * If an address is not present, save it to hash along with epoch value and the timestamp it appeared.
     * If an address is seen a second time, check that the epoch value is consecutive, if it is assume 
     * address is part of the same session. If not, then some time must have past since it was visible in
     * neighbor cache so it must be part of a new session.
     * 
     * @param ts
     * @param address 
     */
    
    private void addNewAddressTimestamp(String address, int epoch, Timestamp ts) {
        java.sql.Timestamp[] tsAr;
        addressCacheObject aco;
        if (addressHash.containsKey(address)) {
            aco = addressHash.get(address);
            tsAr = new java.sql.Timestamp[]{ts,null};      
            addressHash.put(address, tsAr);
        } else {
            tsAr = addressHash.get(address);
            
        }
        
    }
    
    
    /**
     * A container identified by an address containing multiple addressCacheObjectSession objects
     * 
     */
    class addressCacheObject {
        private String _address;
        private java.util.ArrayList<addressCacheObjectSession> _sessions;
        
        public addressCacheObject(String address) {
            this._address = address;
            this._sessions = new java.util.ArrayList();
        }
        
        public String getAddress() { return _address; }
        public void addSession(int e, Timestamp[] t) { this.addSession(new addressCacheObjectSession(e,t)); }
        public void addSession(addressCacheObjectSession acos) { this._sessions.add(acos); }
    }
    
    class addressCacheObjectSession {
        
        private int _lastEpochSeen;
        private Timestamp[] _ts;
               
        public addressCacheObjectSession(int lastEpochSeen, Timestamp[] ts) {
            this._lastEpochSeen = lastEpochSeen;
            this._ts = ts;
        }
        
        
        public int getEpoch() { return _lastEpochSeen; }
        public Timestamp[] getTimestamp() { return _ts; }
        
        public void setEpoch(int e) { this._lastEpochSeen = e; }
        public void setTimestamp(Timestamp[] ts) { this._ts = ts; }
    }
}