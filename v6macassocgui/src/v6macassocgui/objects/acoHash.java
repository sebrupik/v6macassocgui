package v6macassocgui.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.HashMap;



public class acoHash {
    private HashMap<String, addressCacheObject> _addressHash;
    private java.sql.Timestamp[] _timeRange;
    private BufferedImage _image;
    
    public acoHash() {
        this._addressHash = new HashMap<String, addressCacheObject>();
        this._timeRange = new java.sql.Timestamp[2];
    }
    
    /**
     * If an address is not present, save it to hash along with epoch value and the timestamp it appeared.
     * If an address is seen a second time, check that the epoch value is consecutive, if it is assume 
     * address is part of the same session. If not, then some time must have past since it was visible in
     * neighbor cache so it must be part of a new session.
     * 
     * 
     * @param ts
     * @param epoch if value if -1 create an new addressCacheObjectSession, don't try to create contiguous sessions.
     * @param address 
     */
    public void addNewAddressTimestamp(String address, int epoch, Timestamp ts) {
        addressCacheObject aco;
        if (_addressHash.containsKey(address)) {
            aco = _addressHash.get(address);
            aco.addSession(epoch, ts);
            _addressHash.put(address, aco);
        } else {
            aco = new addressCacheObject(address);
            aco.addSession(epoch, ts);
            _addressHash.put(address, aco);
        }
    }
    
    public void draw(Graphics2D g2, int w, int h) {
        _image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D imgG2 = _image.createGraphics();
        int border= 10;
        
        this.calcTimeRange();
        
        if(_timeRange[0]==null | _timeRange[1]==null ) {
            System.out.println("No Data");
            imgG2.setColor(java.awt.Color.RED);
            imgG2.drawString("No Data", w/2, h/2);
            
            imgG2.drawRect(10, 10, 1, 1);
            imgG2.drawRect(10, h-10, 1, 1);
            imgG2.drawRect(w-10, 10, 1, 1);
            imgG2.drawRect(w-10, h-10, 1, 1);
        } else {
            double minutes = (((_timeRange[1].getTime() - _timeRange[0].getTime())/1000)/60);
            //float scaleW = w / (((_timeRange[1].getTime() - _timeRange[0].getTime())/1000)/60);
            float scaleW = (float)(w / minutes);
            float scaleH = h / _addressHash.size();

            System.out.println("calc :"+w+" / "+minutes+" -- > "+(w / minutes));
            System.out.println("scaleW "+w+" / "+_timeRange[1].getTime()+" - "+_timeRange[0].getTime()+" --> "+(((_timeRange[1].getTime() - _timeRange[0].getTime())/1000)/60));
            System.out.println("scale "+scaleW+" "+scaleH);
            
            addressCacheObject acoT;
            int index = 0;
            
            for (java.util.Map.Entry pair : _addressHash.entrySet()) {
                acoT = (addressCacheObject)pair.getValue();
                
                imgG2.setColor(Color.yellow);
                int x1 = (int)( (((acoT.getTimeRange()[0].getTime() - _timeRange[0].getTime())/1000)/60) *scaleW);
                int y1 = (int)(index*scaleH);
                int x2 = (int)( (((acoT.getTimeRange()[1].getTime() - _timeRange[0].getTime())/1000)/60) *scaleW);
                int y2 = (int)(index*scaleH);
                imgG2.drawString(acoT.getAddress(), x1, y1);
                
                System.out.println("line : "+x1+":"+y1+" - "+x2+":"+y2);
                
                imgG2.drawLine(x1+border, y1, x2-border, y2);

                index++;
            }
        }
        g2.drawImage(_image,0,0,null);
    }
    
    public void calcTimeRange() {
        addressCacheObject acoT;
        for (java.util.Map.Entry pair : _addressHash.entrySet()) {
            acoT = (addressCacheObject)pair.getValue();
            this.isTimeExtreme(acoT.getTimeRange()[0]);
            this.isTimeExtreme(acoT.getTimeRange()[1]);
        }
        acoT=null;
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
    }
    
    @Override public String toString() {
        String s = "";
        for (java.util.Map.Entry pair : _addressHash.entrySet()) {
            s += pair.getValue().toString()+"\n";
        }
        return s;
    }
}
