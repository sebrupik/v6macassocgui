package v6macassocgui.objects;

import java.sql.Timestamp;

/**
 * 
 * 
 * @ see addressCacheObject
 */
public class addressCacheObjectSession {
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
    public void setTimestamp(int e, Timestamp[] ts) { this.setEpoch(e);  this.setTimestamp(ts); }
    
    @Override public String toString() { return "e: "+_lastEpochSeen+" ts["+_ts[0]+","+_ts[1]+"]"; }
}
