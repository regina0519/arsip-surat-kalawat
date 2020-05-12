/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCImagesViewer;
import com.thowo.jmpcframework.component.form.JMPCImagesViewerDB;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblafAC;
import com.thowo.jmpcframework.others.JMImageFilter;
import com.thowo.jmpcframework.others.JMPCAutoComplete;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Regina
 */
public class FormTes3 extends javax.swing.JFrame {
    private JMPCImagesViewerDB imgs;

    /**
     * Creates new form FormTes3
     */
    public FormTes3() {
        initComponents();
    }
    
    public void tesImgs(){
        List<String> paths=new ArrayList();
        /*paths.add("/home/jimi/Desktop/tes deploy/samples/admin_settings.jpeg");
        paths.add("/home/jimi/Desktop/tes deploy/samples/Archive.jpg");
        paths.add("/home/jimi/Desktop/tes deploy/samples/btn.png");
        paths.add("/home/jimi/Desktop/tes deploy/samples/inbox.png");
        paths.add("/home/jimi/Desktop/tes deploy/samples/outbox.png");
        paths.add("/home/jimi/Desktop/tes deploy/samples/splash.jpg");*/
        //this.imgs=JMPCImagesViewerDB.create(paths, 200, 200);
        this.imgs=JMPCImagesViewerDB.create(200,200);
        this.jPanel1.setLayout(new FlowLayout());
        this.jPanel1.add(this.imgs);
    }
    
    private void deleteImageTmps(){
        int i=1;
        String dest=JMFunctions.getCacheDir()+"/image.tmp";
        while(JMFunctions.fileExist(new File(dest))){
            JMFunctions.deleteFile(new File(dest));
            dest+=i++;
        }
    }
    private String imageFromBrowser(){
        String ret="";
        String source="";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new JMImageFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
           File file = fileChooser.getSelectedFile();
           source= file.getPath();
        }else{
           JMFunctions.trace("Open Image canceled");
        }
        
        int i=1;
        String tmp=JMFunctions.getCacheDir()+"/image";
        String dest=JMFunctions.getCacheDir()+"/image.tmp";
        while(JMFunctions.fileExist(new File(dest))){
            dest=tmp+i+++".tmp";
        }
        //this.deleteImageTmps();
        
        JMFunctions.copyFile(source, dest);//SCANNED IMAGE
        ret=dest;
        JMFunctions.trace("++++++++++++++++++++++++++++++++++++++++     "+ret);
        this.imgs.addImage(ret);
        //JMFunctions.trace(JMFunctions.getCacheDir()+"/image.tmp");
        return ret;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1)
                                .addComponent(jComboBox1, 0, 281, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        /*this.tesImgs();
        this.imgs.setAddAction(new Runnable(){
            @Override
            public void run() {
                FormTes3.this.imageFromBrowser();
            }
        });*/
        
        //this.jPanel1.setLayout(new FlowLayout());
        //this.jPanel1.add(JMPCFunctions.tes());
        
        
        //this.jTextField1.setFocusTraversalKeysEnabled(false);
        // Our words to complete
        List<String> keywords = new ArrayList<String>(5);
                keywords.add("example");
                keywords.add("autocomplete");
                keywords.add("stackabuse");
                keywords.add("java");
        //JMPCInputStringTFWeblafAC txt=JMPCInputStringTFWeblafAC.create(R.label("ASAL_SM"),R.label("PROMPT_ASAL_SM"), 20, 50, true).setEditable(true);
        //        JMPCAutoComplete autoComplete = new JMPCAutoComplete(txt, keywords);
        JMPCInputStringTFWeblafAC txt=JMPCInputStringTFWeblafAC.create(R.label("ASAL_SM"),R.label("PROMPT_ASAL_SM"), 20, 50, true).setEditable(true);
        txt.setKeyword(keywords);
        this.jPanel2.setLayout(new FlowLayout());
        this.jPanel2.add(txt);
        //tmp.getDocument().addDocumentListener(autoComplete);

        // Maps the tab key to the commit action, which finishes the autocomplete
        // when given a suggestion
        
        //this.jTextField1.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "commit");
        //this.jTextField1.getActionMap().put("commit", autoComplete.new CommitAction());
        
        this.jTextField1.getDocument().addDocumentListener(new DocumentListener(){
            private void trace(){
                JMFunctions.trace(FormTes3.this.jTextField1.getText());
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                this.trace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.trace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.trace();
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //this.imgs.addImage("");
        JMFunctions.moveFile(new File(JMFunctions.getCacheDir()+"/tesgina"), new File(JMFunctions.getCacheDir()+"/haha/tesgina"));
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(FormTes3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTes3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTes3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTes3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTes3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
