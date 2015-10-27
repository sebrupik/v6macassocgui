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
    private java.sql.Timestamp[] _timeRange;

    public addressCacheObject(String address) {
        this._CLASS = this.getClass().getName();
        this._address = address;
        this._sessions = new java.util.ArrayList<addressCacheObjectSession>();
        this._timeRange = new java.sql.Timestamp[2];
        
        System.out.println(_CLASS+"-created object for "+_address);
    }
    
    public void addSession(int e, java.sql.Timestamp t) { 
        _match = false;
        this.isTimeExtreme(t);
        
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
    
    /**
     * Check to see if the supplied timestamp argument can take either place in the _timeRange
     * variable.
     * 
     * @param t 
     */
    private void isTimeExtreme(java.sql.Timestamp t) {
        if(_timeRange[0]==null) {
            _timeRange[0] = t;
            _timeRange[1] = t;
        } else if(_timeRange[0].compareTo(t) > 0) {  //argument is before oldest stored value[0]
            if(_timeRange[1] == null)
                _timeRange[1] = _timeRange[0];
            _timeRange[0] = t;
        } else if(_timeRange[0].compareTo(t) < 0) {  //OK lets check it against the newest stored value [1]
            if(_timeRange[1]==null)
                _timeRange[1] = t;
            else if(_timeRange[1].compareTo(t) < 0)
                _timeRange[1] = t;
        }
        System.out.println(_CLASS+"/isTimeExtreme : "+_address+" :: "+_timeRange[0]+" "+_timeRange[1]);
    }
    public void addSession(addressCacheObjectSession acos) { this._sessions.add(acos); }
    public String getAddress() { return _address; }
    public java.sql.Timestamp[] getTimeRange() { return _timeRange; }
    
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
