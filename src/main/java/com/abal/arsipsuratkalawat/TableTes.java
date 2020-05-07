/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
public class TableTes implements JMFormInterface{
    private String queryView;
    private JMTable dbObject;
    private JMPCTable table;
    private JMPCDBButtonGroup btnGroup;
    private List<Integer> primaryKeys;
    
    public static TableTes create(String query,JPanel pnlTable, JPanel pnlButtons){
        return new TableTes(query,pnlTable, pnlButtons);
    }
    
    public TableTes(String query,JPanel pnlTable, JPanel pnlButtons){
        this.queryView=query;
        JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        Object[] boolImg={JMFunctions.getResourcePath("img/inbox.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/outbox.png", this.getClass()).getPath()};
        
        JMResultSetStyle style=JMResultSetStyle.create(rs).setColHidden(0)
                .setLabel(0,R.label("INT"))
                .setLabel(1,R.label("STRING"))
                .setLabel(2,R.label("TEXT"))
                .setLabel(3,R.label("DOUBLE"))
                .setLabel(4,R.label("BOOL"))
                .setLabel(5,R.label("DATE"))
                .setLabel(6,R.label("DATETIME"))
                .addFormat(4, JMResultSetStyle.FORMAT_IMAGE, boolImg);
        this.dbObject=JMTable.create(rs,style);
        this.dbObject.addInterface(this);
        this.dbObject.setName("tes");
        this.primaryKeys=new ArrayList();
        this.primaryKeys.add(0);
        this.dbObject.setKeyColumns(this.primaryKeys);
        
        this.table=JMPCTable.create(this.dbObject);
        JScrollPane sp=new JScrollPane(this.table);
        pnlTable.setLayout(new BorderLayout());
        pnlTable.add(sp,BorderLayout.CENTER);
        
        this.btnGroup=new JMPCDBButtonGroup(this.dbObject);
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        
        this.addListener();
        
        //GOTO FIRST RECORD
        if(!this.dbObject.isEmpty()){
            this.dbObject.firstRow(false);
            this.btnGroup.stateInit();
        }
        
        
    }
    
    private void openForm(boolean editMode){
        InputTes.create(this.dbObject).setEditMode(editMode);
    }
    
    
    private void addListener(){
        this.table.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==e.VK_ENTER)e.consume();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==e.VK_ENTER){
                    TableTes.this.openForm(false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TableTes.this.openForm(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        
    }
    
    public JMPCDBButtonGroup getButtonGroup(){
        return this.btnGroup;
    }
    public JMPCTable getTable(){
        return this.table;
    }
    

    @Override
    public void actionAdd(JMRow rowAdded) {
        
    }

    @Override
    public void actionDelete(JMRow rowDeleted) {
        
    }

    @Override
    public void actionSave(String updateQuery) {
        
    }

    @Override
    public void actionEdit(JMRow rowEdited) {
        //JMFunctions.trace("Tabel row" + this.dbObject.getCurrentRow().getRowNum());
    }

    @Override
    public void actionPrint(JMRow rowPrinted) {
        
    }

    @Override
    public void actionRefresh(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionView(JMRow rowViewed) {
        
    }

    @Override
    public void actionNext(JMRow nextRow) {
        //this.table.setRowSelectionInterval(nextRow.getRowNum(), 0);
    }

    @Override
    public void actionPrev(JMRow prevRow) {
        
    }

    @Override
    public void actionFirst(JMRow firstRow) {
        
    }

    @Override
    public void actionLast(JMRow lastRow) {
        
    }

    @Override
    public void gotoRecord(JMRow currentRow) {
        
    }

    @Override
    public void actionCancel(JMRow rowCanceled, boolean canceled) {
        
    }
    
}

