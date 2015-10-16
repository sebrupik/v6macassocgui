package v6macassocgui.component;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;

public class timelinePanel extends JPanel {
    public timelinePanel() {
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawString("timeline!", 20, 20);
    }
}
