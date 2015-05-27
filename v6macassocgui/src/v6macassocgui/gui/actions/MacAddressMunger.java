package v6macassocgui.gui.actions;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;

import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Takes a string assumed to be a MAC address and runs some checks to ensure it 
 * meets the correct characteristics:
 * Length: 12 (no delimiters)
 *         15 (delimiter every 4 characters)
 *         17 (delimiter every 2 characters)
 * Delimiters : . or : or -
 * Characters :  0-9a-fA-F
 * 
 * 
 * @author snr
 */
public class MacAddressMunger extends FocusAdapter {
    JTextField source;
    
    private final Pattern _GOODMAC;
    
    public MacAddressMunger(JTextField source) {
        this._GOODMAC = Pattern.compile("^([[:XDigit:]]{2}[\\.:-]?){5}[[:XDigit:]]{2}");
    }
    
    @Override public void focusGained(FocusEvent e) { workTheMagic(); }
    @Override public void focusLost(FocusEvent e) {  workTheMagic(); }
    
    private void workTheMagic() {
        String text = source.getText().trim();
        
        if(!validate(normalise(text)))
            JOptionPane.showMessageDialog(source, "This MAC address has the wrong format : "+source.getText());
    }
    
    private String normalise(String mac) {
        return mac.replaceAll("[^a-fA-F0-9]", "");
    }
    
    private boolean validate(String mac) {
        Matcher m = _GOODMAC.matcher(mac);
        return m.find();
    }
}