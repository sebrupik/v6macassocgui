package v6macassocgui.gui;

import v6macassocgui.v6macassocgui;
import v6macassocgui.component.projectPanel3;
import v6macassocgui.gui.actions.MacAddressMunger;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class macViewer extends projectPanel3 {
    final v6macassocgui owner;
    
    private JTextField macTxt;
    private JLabel firstSeenLbl, vendorLbl;
    private JTabbedPane jtp;
    
    public macViewer(v6macassocgui owner) {
        super("MAC address viewer");
        this.owner = owner;
        
        this.jtp = new JTabbedPane();
        
        jtp.add("tables", genFirstTab());
        
        this.setLayout(new BorderLayout(2,2));
        
        this.add(genDetailPanel(), BorderLayout.NORTH);
        this.add(jtp, BorderLayout.CENTER);
    }
    
    private JPanel genDetailPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel dPanel = new JPanel(gridbag);
        
        JLabel dPanelLbl[] = new JLabel[]{new JLabel("MAC address: "), new JLabel("First Seen: "), new JLabel("Vendor")};
        
        macTxt = new JTextField();  macTxt.addFocusListener(new MacAddressMunger(macTxt));
        firstSeenLbl = new JLabel("");
        vendorLbl = new JLabel("");
        
        JButton searchBut = new JButton("Search");
        
        buildConstraints(constraints, 0, 0, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(dPanelLbl[0], constraints);
        dPanel.add(dPanelLbl[0]);
        buildConstraints(constraints, 1, 0, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(macTxt, constraints);
        dPanel.add(macTxt);
        buildConstraints(constraints, 2, 0, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(searchBut, constraints);
        dPanel.add(searchBut);
        
        buildConstraints(constraints, 0, 1, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(dPanelLbl[1], constraints);
        dPanel.add(dPanelLbl[1]);
        buildConstraints(constraints, 1, 1, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(firstSeenLbl, constraints);
        dPanel.add(firstSeenLbl);
        
        buildConstraints(constraints, 0, 2, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(dPanelLbl[2], constraints);
        dPanel.add(dPanelLbl[2]);
        buildConstraints(constraints, 1, 2, 1, 1, 100, 100);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(vendorLbl, constraints);
        dPanel.add(vendorLbl);
        
        return dPanel;
    }
    
    private JPanel genFirstTab() {
        JPanel ft = new JPanel();
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        sp.setLeftComponent(this.genAddressPanel());
        sp.setRightComponent(this.genAuthPanel());
        
        ft.add(sp);
        
        return ft;
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
