/*
 * Deftly lifted from my old AimApp code
 */
package v6macassocgui.component;

import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
 
public abstract class projectPanel3 extends JInternalFrame {
    private final String _CLASS;
     
    public projectPanel3(String title) {
        super(title, true, true, true, true);
         
        this._CLASS = this.getClass().getName();
         
        addInternalFrameListener(new InternalFrameAdapter()  {
            @Override public void internalFrameClosing(InternalFrameEvent e)  {
                closingActions();
            }
        });
    }
     
    public abstract void closingActions();
    public abstract void writeProperties();
     
    //****************************
     
    public void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }
     
    //****************************
 
}