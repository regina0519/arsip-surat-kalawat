/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMVec2;
import com.thowo.jmjavaframework.db.JMConnection;
import com.thowo.jmjavaframework.db.JMDBMySQL;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCAsyncLoaderDefault;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author regina
 */
public class SplashScreen extends javax.swing.JFrame {

    /**
     * Creates new form SplashScreen
     */
    public SplashScreen() {
        String localeCode="in_ID";
        JMPCFunctions.init(localeCode);
        this.initSplash();
        JMFunctions.setAsyncListener(new JMPCAsyncLoaderDefault( this.jLabel1 , null ));
        R.set(localeCode);
    }
    public void initSplash(){
        JMVec2 splashSize=new JMVec2(553,339);
        Image img=new ImageIcon(JMFunctions.getResourcePath("img/splash.jpg", this.getClass())).getImage();
        JMVec2 imgSize=new JMVec2(img.getWidth(rootPane),img.getHeight(rootPane));
        imgSize.trace();
        List<JMVec2> scaled=JMFunctions.scaledSize(imgSize, splashSize, JMFunctions.SCALE_FIT);
        scaled.get(0).trace();
        scaled.get(1).trace();
        ImageIcon ico=new ImageIcon(img.getScaledInstance(scaled.get(0).getIntX(), scaled.get(0).getIntY(), Image.SCALE_SMOOTH));
        JLabel lblImg=new JLabel(ico);
        this.setContentPane(lblImg);
        lblImg.setBounds(scaled.get(1).getIntX(), scaled.get(1).getIntY(), scaled.get(0).getIntX(), scaled.get(0).getIntY());
        
        initComponents();
        
        this.setSize(splashSize.getIntX(), splashSize.getIntY());
        this.setLocationRelativeTo(null);
        this.jLabel1.setForeground(Color.WHITE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Loading");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        Thread t=new Thread(new Runnable(){
            @Override
            public void run() {
                JMFunctions.setConnection(new JMConnection((File)GitIgnoreDBConnection.getDBs().get(0),(JMDBMySQL)GitIgnoreDBConnection.getDBs().get(1)));
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        if(!JMFunctions.getCurrentConnection().mySQLConnected()){
                            JMFunctions.traceAndShow("NDA");
                            System.exit(0);
                        }else{
                            SplashScreen.this.setVisible(false);
                            new FormLogin(SplashScreen.this,false).setVisible(true);
                            
                        }
                    }
                });
            }
        });
        t.start();
        
        //JMFunctions.setConnection(new JMConnection((File)GitIgnoreDBConnection.getDBs().get(0),(JMDBMySQL)GitIgnoreDBConnection.getDBs().get(1)));
        //new LoginForm().setVisible(true);
        //SplashScreen.this.setVisible(false);
    }//GEN-LAST:event_formFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SplashScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
