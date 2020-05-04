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
import com.thowo.jmpcframework.component.JMPCCellImageRenderer;
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
    private JTable jTable;
    private JMPCDBButtonGroup btnGroup;
    private int backUpRow;
    private List<Integer> primaryKeys;
    private boolean tableFiltered=false;
    
    public static TableTes create(String query,JTable jTable, JPanel dbButtons){
        return new TableTes(query,jTable, dbButtons);
    }
    
    public TableTes(String query,JTable jTable, JPanel pnlButtons){
        this.queryView=query;
        this.jTable=jTable;
        
        this.queryView=query;
        JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        JMResultSetStyle style=JMResultSetStyle.create(rs.getSQLResultSet());
        style=style.setColHidden(0);
        this.dbObject=JMTable.create(rs,style);
        this.dbObject.setName("tes");
        this.primaryKeys=new ArrayList();
        this.primaryKeys.add(0);
        this.dbObject.setKeyColumns(this.primaryKeys);
        
        this.btnGroup=new JMPCDBButtonGroup(this,this.dbObject);
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
        //model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn(R.label("INT"));
        model.addColumn(R.label("STRING"));
        model.addColumn(R.label("TEXT"));
        model.addColumn(R.label("DOUBLE"));
        model.addColumn(R.label("BOOL"));
        model.addColumn(R.label("DATE"));
        model.addColumn(R.label("DATETIME"));
        
        
        JMPCFunctions.linkTable(this.jTable, this.dbObject);
        this.jTable.setDefaultEditor(Object.class, null);
        this.jTable.getColumnModel().getColumn(1).setCellRenderer(JMPCCellImageRenderer.create(this.getClass()));
        for(int i=0;i<this.jTable.getColumnCount();i++){
            if(!style.getVisible(i))this.jTable.removeColumn(this.jTable.getColumnModel().getColumn(i));
        }
        this.addTableListener();
        
        //GOTO FIRST RECORD
        if(!this.dbObject.isEmpty()){
            this.jTable.setRowSelectionInterval(0, 0);
            this.dbObject.firstRow(false);
            this.btnGroup.stateInit();
        }
        
        
    }
    
    private void openForm(boolean editMode,JMPCDBButtonGroup state){
        if(this.tableFiltered){
            this.dbObjectSelectRow();
        }
        InputTes.create(new FormInput(),this.dbObject, state).setEditMode(editMode).setTableTes(this);
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
                
                if(e.getKeyCode()==e.VK_ENTER){
                    JMPCDBButtonGroup tmp=new JMPCDBButtonGroup(TableTes.this.dbObject);
                    tmp.stateView();
                    TableTes.this.openForm(false,tmp);
                }
                if(e.getKeyCode()==e.VK_DOWN)TableTes.this.dbObjectmoveNext();
                if(e.getKeyCode()==e.VK_UP)TableTes.this.dbObjectmovePrev();
            }
        });
        this.jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TableTes.this.dbObjectSelectRow();
                    JMPCDBButtonGroup tmp=new JMPCDBButtonGroup(TableTes.this.dbObject);
                    tmp.stateView();
                    TableTes.this.openForm(false,tmp);
                }else{
                    JMFunctions.trace("KLIK");
                    TableTes.this.dbObjectSelectRow();
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
    
    public JMPCDBButtonGroup getButtonGroup(){
        return this.btnGroup;
    }
    public JTable getJTable(){
        return this.jTable;
    }
    
    private void dbObjectSelectRow(){
        if(!this.tableFiltered && this.jTable.getSelectedRow()!=this.backUpRow){
            DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
            List<String> keyValues=new ArrayList();
            for(int i=0;i<this.primaryKeys.size();i++){
                if(this.primaryKeys.contains(Integer.valueOf(i))){
                    String key=(String) model.getValueAt(this.jTable.getSelectedRow(), i);
                    keyValues.add(key);
                }
            }
            JMRow tmp=this.dbObject.findByKeys(keyValues);
            this.backUpRow=this.jTable.getSelectedRow();
            this.btnGroup.stateNav();
        }
    }
    private void dbObjectmoveNext(){
        
        if(!this.tableFiltered && this.jTable.getSelectedRow()!=this.backUpRow){
            JMFunctions.trace(this.jTable.getSelectedRow()+"");
            this.dbObject.nextRow(false);
            this.backUpRow=this.jTable.getSelectedRow();
            this.btnGroup.stateNav();
        }
    }
    private void dbObjectmovePrev(){
        
        if(!this.tableFiltered && this.jTable.getSelectedRow()!=this.backUpRow){
            JMFunctions.trace(this.jTable.getSelectedRow()+"");
            this.dbObject.prevRow(false);
            this.backUpRow=this.jTable.getSelectedRow();
            this.btnGroup.stateNav();
        }
    }
    

    @Override
    public void actionAdd() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.dbObject.addNewRow(true);
        JMPCDBButtonGroup tmp=new JMPCDBButtonGroup(this.dbObject);
        tmp.stateAdd();
        this.openForm(true,tmp);
    }
    

    @Override
    public void actionDelete() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionSave() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionEdit() {
        JMPCDBButtonGroup tmp=new JMPCDBButtonGroup(this.dbObject);
        tmp.stateEdit();
        this.openForm(true,tmp);
    }

    @Override
    public void actionPrint() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionRefresh() {
        JMPCFunctions.linkTable(this.jTable, this.dbObject);
        //GOTO FIRST RECORD
        if(!this.dbObject.isEmpty()){
            this.jTable.setRowSelectionInterval(0, 0);
            this.dbObject.firstRow(false);
        }
    }

    @Override
    public void actionView() {
        JMPCDBButtonGroup tmp=new JMPCDBButtonGroup(this.dbObject);
        tmp.stateView();
        this.openForm(false,tmp);
    }

    @Override
    public void actionNext() {
        this.dbObject.nextRow(false);
        JMPCFunctions.jTableMoveNext(this.jTable, this.backUpRow);
    }

    @Override
    public void actionPrev() {
        this.dbObject.prevRow(false);
        JMPCFunctions.jTableMovePrev(this.jTable, this.backUpRow);
    }

    @Override
    public void actionFirst() {
        this.dbObject.firstRow(false);
        JMPCFunctions.jTableMoveFirst(this.jTable, this.backUpRow);
    }

    @Override
    public void actionLast() {
        this.dbObject.lastRow(false);
        JMPCFunctions.jTableMoveLast(this.jTable, this.backUpRow);
    }

    @Override
    public void actionCancel() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

