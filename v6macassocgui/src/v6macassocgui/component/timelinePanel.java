package v6macassocgui.component;

import javax.swing.JPanel;
import java.awt.Graphics;

public class timelinePanel extends JPanel {
    public timelinePanel() {
        
    }
    
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawString("timeline!", 20, 20);
    }
}