package v6macassocgui.objects;

import java.util.ListIterator;

/**
 * A container identified by an address containing multiple addressCacheObjectSession objects
 * 
 * @see addressCacheObjectSession, jtablePanelAddress
 */
public class addressCacheObject {
    private final String _CLASS;
    private String _address;
    private java.util.ArrayList<addressCacheObjectSession> _sessions;
    
    private ListIterator<addressCacheObjectSession> _itr;
    private addressCacheObjectSession _acosT;
    private boolean _match;

    public addressCacheObject(String address) {
        this._CLASS = this.getClass().getName();
        this._address = address;
        this._sessions = new java.util.ArrayList();
        
        System.out.println(_CLASS+"-created object for "+_address);
    }
    
    public void addSession(int e, java.sql.Timestamp t) { 
        _match = false;
        
        if(e == -1) {
            this.addSession(new addressCacheObjectSession(e,new java.sql.Timestamp[]{t,null})); 
        } else {
            _itr = _sessions.listIterator();
            while(_itr.hasNext()) {
                _acosT = _itr.next();
                if ( (_acosT.getEpoch()+1)==e) {
                    _acosT.setTimestamp(e, new java.sql.Timestamp[]{_acosT.getTimestamp()[0], t});
                    _match=true;
                }
            }
            if(!_match)
                this.addSession(new addressCacheObjectSession(e,new java.sql.Timestamp[]{t,null}));
        }
    }
    public void addSession(addressCacheObjectSession acos) { this._sessions.add(acos); }
    public String getAddress() { return _address; }
    
    @Override public String toString() {
        String s = _address+" \n";
        _itr = _sessions.listIterator();
        while(_itr.hasNext()) {
            _acosT = _itr.next();
            s +="   "+_acosT.toString()+"\n";
        }
        return s;
    }
}
