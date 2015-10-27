package v6macassocgui.component;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import v6macassocgui.objects.addressCacheObject;

public class timelinePanel extends JPanel {
    private jtablePanelAddress _owner;
    private BufferedImage _image;
    
    public timelinePanel(jtablePanelAddress _owner) {
        this._owner = _owner;
        
    }
    
    @Override public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        
        _owner.getAcoHash().draw((Graphics2D)g, getWidth(), getHeight());
    }
    
    public void processACO(addressCacheObject aco, Graphics2D g2, int w, int h) {
        
    }
    
    public void processHashMap(HashMap<String, addressCacheObject> hm) {
        
    }
    
}
