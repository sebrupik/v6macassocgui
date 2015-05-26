package v6macassocgui.gui;

import v6macassocgui.v6macassocgui;
import v6macassocgui.component.projectPanel3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class macViewer extends projectPanel3 {
    final v6macassocgui owner;
    
    public macViewer(v6macassocgui owner) {
        super("MAC address viewer");
        this.owner = owner;
    }
    
    private JPanel genDetailPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel dPanel = new JPanel(gridbag);
        
        
        return dPanel;
    }
    
    private JPanel genAddressPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel addPanel = new JPanel(gridbag);
        
        
        return addPanel;
    }
    
    private JPanel genAuthPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel authPanel = new JPanel(gridbag);
        
        
        return authPanel;
    }
    

    @Override public void closingActions() { writeProperties(); }
    @Override public void writeProperties() {
    }
    
}
