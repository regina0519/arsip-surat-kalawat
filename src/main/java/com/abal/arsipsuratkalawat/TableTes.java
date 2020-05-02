/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCCellImageRenderer;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jimi
 */
public class TableTes {
    private String queryView;
    private JMTable table;
    private JTable jTable;
    
    public static TableTes create(String query,JTable jTable){
        return new TableTes(query,jTable);
    }
    
    public TableTes(String query,JTable jTable){
        this.queryView=query;
        this.jTable=jTable;
        this.view(this.queryView);
    }
    
    public void view(String query){
        this.queryView=query;
        JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        JMResultSetStyle style=JMResultSetStyle.create(rs.getSQLResultSet());
        style=style.setColHidden(0);
        this.table=JMTable.create(rs,style);
        
        DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn(R.label("INT"));
        model.addColumn(R.label("STRING"));
        model.addColumn(R.label("TEXT"));
        model.addColumn(R.label("DOUBLE"));
        model.addColumn(R.label("BOOL"));
        model.addColumn(R.label("DATE"));
        model.addColumn(R.label("DATETIME"));
        
        
        JMPCFunctions.linkTable(this.jTable, this.table);
        this.jTable.setDefaultEditor(Object.class, null);
        this.jTable.getColumnModel().getColumn(1).setCellRenderer(JMPCCellImageRenderer.create(this.getClass()));
        for(int i=0;i<this.jTable.getColumnCount();i++){
            if(!style.getVisible(i))this.jTable.removeColumn(this.jTable.getColumnModel().getColumn(i));
        }
        this.addTableListener();
    }
    
    private void openForm(){
        if(TableTes.this.jTable.getSelectedRow()>=0){
            InputTes.create(new FormInput());
        }
    }
    
    private void addTableListener(){
        this.jTable.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getKeyCode()==e.VK_ENTER)e.consume();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getKeyCode()==e.VK_ENTER)TableTes.this.openForm();
            }
        });
        this.jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TableTes.this.openForm();
                }
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
}

