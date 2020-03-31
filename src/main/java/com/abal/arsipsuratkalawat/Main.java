/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCAsyncLoaderDefault;
import com.thowo.jmpcframework.component.JMPCSplashForm;
import com.thowo.jmpcframework.component.JMPCUIMessenger;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jimi
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        new JMPCSplashForm(new MainForm(),GitIgnoreDBConnection.getDBs()).setVisible(true);
    }
    
}
