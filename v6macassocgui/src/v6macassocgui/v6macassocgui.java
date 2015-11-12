package v6macassocgui;

import v6macassocgui.gui.macViewer;

import java.awt.BorderLayout;
import java.io.IOException;
import jdbcApp.jdbcApp;

import java.awt.event.*;
import java.util.logging.Level;
import javax.swing.*;

public class v6macassocgui extends jdbcApp {
    private final String _CLASS;
    
    public JDesktopPane jdp;
    
    public v6macassocgui(String propsStr, String psRBStr, java.util.logging.Logger myLogger) {
        super(propsStr, psRBStr, myLogger);
        this._CLASS = this.getClass().getName();
        
        this.genMainPanel();
    }
    
    public static void main(String[] args) {
        final java.util.logging.Logger myLogger = java.util.logging.Logger.getLogger("v6macassocgui");
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
        
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    myLogger.logp(java.util.logging.Level.SEVERE, "v6macassocgui", "main", e.toString());
                    // If Nimbus is not available, you can set the GUI to another look and feel.
                }
                v6macassocgui v6MAGUI = new v6macassocgui("settings.properties", "v6macassocgui/preparedstatements.properties", myLogger);
                v6MAGUI.setVisible(true); 
            }
        });
    }

    @Override public void assignSystemVariables() throws IOException {
    }

    @Override public void writeSystemVariables() {
        saveSysProperty("sizeX", String.valueOf(this.getSize().width));
        saveSysProperty("sizeY", String.valueOf(this.getSize().height));
    }
    
    private void genMainPanel() { 
        content.add(genMenuBar(), BorderLayout.NORTH);
        content.add(genDesktop(), BorderLayout.CENTER);
    }
    
    private JDesktopPane genDesktop() {
        jdp = new JDesktopPane();
 
        return jdp;
    }
     
    private JMenuBar genMenuBar() {
        JMenuBar menuBar = new JMenuBar();
         
        //**************** SYSTEM *******************
        JMenu sysMenu = new JMenu("System");
        //JMenuItem connectMI = new JMenuItem("Connection");
        JMenuItem msMI = new JMenuItem("MAC search");
        msMI.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                createFrame("MAC_VIEWER");
            }
        });
         
         
        JMenuItem exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                closeApp();
            }
        });
         
        sysMenu.add(msMI);
        //sysMenu.add(connectMI);
        sysMenu.add(exitMI);
        
        menuBar.add(sysMenu);

        return menuBar;
    }
    
    public void createFrame(String which) {
        JInternalFrame jif = null;
         
        if(getdbConnectionStatus()==DBCON_CONNECTED) {
            if(which.equals("MAC_VIEWER")) {
                jif = new macViewer(this);
                //jif.setSize(200, jdp.getHeight()-20);
                try {
                    jif.setSize(Integer.parseInt(getSysProperty("sizeX.macViewer")), Integer.parseInt(getSysProperty("sizeX.macViewer")) );
                } catch(java.io.IOException ioe) { log(Level.SEVERE, _CLASS, "createFrame", ioe); }
            }
        }
        this.attemptAddingJIF(jif);
    }
    
    private void attemptAddingJIF(JInternalFrame jif) {
        if(jif != null) {
            if(!frameExists(jif)) {
                jif.setVisible(true);
                jdp.add(jif);
             
                log(Level.INFO, _CLASS, "attemptAddingJIF", "frame added??");
 
                try {
                    jif.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println(e); 
                }
            } else {
                log(Level.INFO, _CLASS, "attemptAddingJIF", "Frame exisits, nulling object!");
                jif = null;
            }
        }
    }
     
    public boolean frameExists(JInternalFrame obj) {
        JInternalFrame[] allFrames = jdp.getAllFrames();
         
        //for (int i=0; i<allFrames.length; i++) {
        for(JInternalFrame frame : allFrames) {
            if(frame.toString().equals(obj.toString()))
                return true;
            else
                log(Level.INFO, _CLASS, "frameExists", "JInternalFrame not already on the desktop!");
        }
        return false;
    }
     
    public int findFrame(String query) {
        int index=-1;
        JInternalFrame[] allFrames = jdp.getAllFrames();
         
        for (int i=0; i<allFrames.length; i++) {
            if(allFrames[i].toString().contains(query))
                return i;
            else
                log(Level.INFO, _CLASS, "findFrame", "JInternalFrame not already on the desktop!");
        }
        return index;
    }
}