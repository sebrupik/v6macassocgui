package v6macassocgui.component;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.awt.image.BufferedImage;

import v6macassocgui.objects.addressCacheObject;

public class timelinePanel extends JPanel {
    private BufferedImage _image;
    
    public timelinePanel() {
        
    }
    
    @Override public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    private void createImage() {
        _image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = _image.createGraphics();
        
        
        g2.drawString("timeline!", getWidth()-20, 20);
        g2.drawRect(20,20,getWidth()-50,getHeight()-50);
        
        g2.drawImage(_image,0,0,null);
        
        this.paintComponent(g2);
    }
    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
    }
    
    public void processHashMap(HashMap<String, addressCacheObject> hm) {
        
    }
}
