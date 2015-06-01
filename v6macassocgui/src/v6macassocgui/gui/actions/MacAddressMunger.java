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
    private final Pattern _NORMALISEMAC;
    
    public MacAddressMunger(JTextField source) {
        this.source = source;
        //this._GOODMAC = Pattern.compile("^([[:XDigit:]]{2}[\\.:-]?){5}([[:XDigit:]]{2})$");
        this._GOODMAC = Pattern.compile("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$");
        this._NORMALISEMAC = Pattern.compile("^([0-9a-fA-F]{12})$");
    }
    
    @Override public void focusGained(FocusEvent e) { //workTheMagic(); 
        
    }
    @Override public void focusLost(FocusEvent e) {  workTheMagic(); }
    
    private void workTheMagic() {
        String text = source.getText().trim();
        
        //if(!validate(_GOODMAC, text))
        if(!validate(_NORMALISEMAC, normalise(text))) {
            JOptionPane.showMessageDialog(source, "This MAC address has the wrong format : "+source.getText()); }
    }
    
    private String normalise(String mac) {
        return mac.replaceAll("[^a-fA-F0-9]", "");
    }
    
    private boolean validate(Pattern p, String mac) {
        Matcher m = p.matcher(mac);
        System.out.println("matcher : "+m.find());
        
        return m.find();
    }
    
}