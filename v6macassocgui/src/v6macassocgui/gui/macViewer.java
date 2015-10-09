package v6macassocgui.gui;

import v6macassocgui.v6macassocgui;
import v6macassocgui.component.projectPanel3;
import v6macassocgui.component.jtablePanel;
import v6macassocgui.component.jtablePanelAddress;
import v6macassocgui.component.jtablePanelAuth;
import v6macassocgui.gui.actions.MacAddressMunger;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.sql.ResultSet;

public class macViewer extends projectPanel3 {
    private v6macassocgui _owner;
    
    private JTextField macTxt;
    private JLabel firstSeenLbl, vendorLbl;
    private JTable addressTbl, authTbl;
    private JTabbedPane jtp;
    
    private jtablePanelAddress jtpadd;
    private jtablePanelAuth jtpauth;
    
    private PreparedStatement _firstSeenPS;
    
    
    public macViewer(v6macassocgui owner) {
        super("MAC address viewer");
        this._owner = owner;
        
        this._firstSeenPS = _owner.getdbConnection().getPS("ps_select_mac_first_seen");
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
        
        macTxt = new JTextField("aa:bb:cc:dd:ee:ff");  macTxt.addFocusListener(new MacAddressMunger(macTxt));
        firstSeenLbl = new JLabel("");
        vendorLbl = new JLabel("");
        
        JButton searchBut = new JButton("Search");  searchBut.addActionListener(new macSearchAction(_owner, this, searchBut, macTxt));
        
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
        JPanel ft = new JPanel(new BorderLayout(2,2));
        jtpadd = new jtablePanelAddress(_owner, this, "Addresses Seen", new String[]{"ps_select_mac_address", "ps_select_mac_address_detail"}, new String[]{"Timestamp", "IPv6 Address", "Source"});
        jtpauth = new jtablePanelAuth(_owner, this, "Authenticated Sessions", new String[]{"ps_select_mac_auth", "ps_select_mac_auth_detail"}, new String[]{"Timestamp", "username", "Source"});
        
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);  sp.setOneTouchExpandable(true);
        
        sp.setLeftComponent(jtpadd);
        sp.setRightComponent(jtpauth);
        
        ft.add(sp, BorderLayout.CENTER);
        
        return ft;
    }
    
    private String getFirstSeen(String ma) {
        String s ="unknown";
        try {
            _firstSeenPS.setString(1, ma);
            ResultSet r = _firstSeenPS.executeQuery();

            if(r.first()) {
                s = r.getTimestamp("timestamp").toString();
            } 
        } catch(java.sql.SQLException sqle) { }
        return s;
    }

    @Override public void closingActions() { writeProperties(); }
    @Override public void writeProperties() {
    }
    public jtablePanel getTablePanel(int lr) {
        if(lr==0)
            return jtpadd;
        else
            return jtpauth;
    }
    public JLabel getFirstSeenLbl() { return firstSeenLbl; }
    public JLabel getVendorLbl() { return vendorLbl; }
    
    class macSearchAction implements ActionListener {
        private final JTextField _SOURCE;
        private final macViewer _PARENT;
        
        public macSearchAction(v6macassocgui _owner, macViewer parent, JButton srcBut, JTextField source) {
            this._PARENT = parent;
            this._SOURCE = source;
        }
        @Override public void actionPerformed(ActionEvent e) {
            if(_owner.getdbConnectionStatus()==v6macassocgui.DBCON_CONNECTED) {
                _PARENT.getTablePanel(0).refreshTable(_SOURCE.getText());
                //_PARENT.getTablePanel(1).refreshTable(_SOURCE.getText());
                
                _PARENT.getFirstSeenLbl().setText(_PARENT.getFirstSeen(_SOURCE.getText()));
            }
        }
    }
}