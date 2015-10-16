package v6macassocgui.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import v6macassocgui.gui.macViewer;
import v6macassocgui.v6macassocgui;

public class macSearchAction implements ActionListener {
    private final v6macassocgui _OWNER;
    private final JTextField _SOURCE;
    private final macViewer _PARENT;

    public macSearchAction(v6macassocgui owner, macViewer parent, JButton srcBut, JTextField source) {
        this._OWNER = owner;
        this._PARENT = parent;
        this._SOURCE = source;
    }
    @Override public void actionPerformed(ActionEvent e) {
        if(_OWNER.getdbConnectionStatus()==v6macassocgui.DBCON_CONNECTED) {
            _PARENT.getTablePanel(0).refreshTable(_SOURCE.getText());
            //_PARENT.getTablePanel(1).refreshTable(_SOURCE.getText());

            _PARENT.getFirstSeenLbl().setText(_PARENT.getFirstSeen(_SOURCE.getText()));
        }
    }
}