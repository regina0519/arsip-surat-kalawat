/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCAsyncLoaderDefault;
import com.thowo.jmpcframework.component.JMPCUIMessenger;
import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.plaf.synth.SynthStyleFactory;

/**
 *
 * @author regina
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        JMPCFunctions.initLookAndFeel(Main.class);
        //new JMPCSplashForm(new LoginForm(),GitIgnoreDBConnection.getDBs(),"img/splash.jpg",Main.class,"id_ID").setVisible(true);
        new SplashScreen().setVisible(true);
    }
    
}
