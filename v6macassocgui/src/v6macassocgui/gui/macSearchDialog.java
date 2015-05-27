package v6macassocgui.gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import javax.swing.JTextField;
import v6macassocgui.gui.actions.MacAddressMunger;
import v6macassocgui.v6macassocgui;
//import v6macassocgui.component.projectPanel3;

public class macSearchDialog extends JDialog {
    private v6macassocgui _owner;
    
    private JTextField macTxt;
    
    public macSearchDialog(v6macassocgui _owner, String title) {
        super(_owner, title);
        
        JPanel p = new JPanel(new GridLayout(1,2));
        macTxt = new JTextField("");  macTxt.addFocusListener(new MacAddressMunger(macTxt));
        JButton searchBut = new JButton("Search"); searchBut.addActionListener(new macSearchAction(_owner, searchBut));
        
        p.add(macTxt);
        p.add(searchBut);
        
        getContentPane().add(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    
    
    class macSearchAction implements ActionListener {
        public macSearchAction(v6macassocgui _owner, JButton srcBut) {
            
        }
        @Override public void actionPerformed(ActionEvent e) {
            
        }
    }
}