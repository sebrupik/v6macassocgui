package v6macassocgui.gui.actions;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;

public class MacAddressMunger extends FocusAdapter {
    JTextField source;
    
    public MacAddressMunger(JTextField source) {
        
    }
    
    @Override public void focusGained(FocusEvent e) {
        workTheMagic();
    }
 
    @Override public void focusLost(FocusEvent e) {
        workTheMagic();
    }
    
    private void workTheMagic() {
        
    }
}
