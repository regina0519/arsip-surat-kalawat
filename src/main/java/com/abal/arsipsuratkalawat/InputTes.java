/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCForm;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author jimi
 */
public class InputTes implements JMFormInterface {
    private String title=R.label("TITLE_TES");
    private JMTable table;
    private FormInput form;
    
    private JMPCInputStringTFWeblaf fInt;
    private JMPCInputStringTFWeblaf fString;
    private JMPCInputStringTFWeblaf fText;
    private JMPCInputStringTFWeblaf fDouble;
    private JMPCInputStringTFWeblaf fBool;
    private JMPCInputStringTFWeblaf fDate;
    private JMPCInputStringTFWeblaf fDateTime;
    private JMRow row;
    private JMPCDBButtonGroup btnGroup;
    private TableTes tableTes;
    private boolean adding=false;
    
    public static InputTes create(FormInput form, JMTable table,JMPCDBButtonGroup btnGroup){
        return new InputTes(form, table,btnGroup);
    }
    
    public InputTes setTableTes(TableTes tableTes){
        this.tableTes=tableTes;
        return this;
    }
    public InputTes(FormInput form, JMTable table, JMPCDBButtonGroup btnGroup){
        this.form=form;
        this.table=table;
        this.btnGroup=btnGroup;
        this.btnGroup.setFormInterface(this);
        JPanel pnlButtons=form.getButtonsPanel();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        this.row=this.table.getCurrentRow();
        this.view();
        
    }
    
    public void view(){
        //JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        //JMResultSetStyle style=JMResultSetStyle.create(rs.getSQLResultSet());
        //style=style.setColHidden(0);
        //this.table=JMTable.create(rs,style);
        
        int width=400;
        boolean horizontal=true;
        //this.fInt=JMPCInputStringTFWeblaf.create("value", "", "error", "Tes", "hahah", 10,400,true);
        this.fInt=JMPCInputStringTFWeblaf.create(R.label("INT"),"Input integer", 6, width, horizontal).setEditable(false);
        this.fString=JMPCInputStringTFWeblaf.create(R.label("STRING"),"Input string", 20, width, horizontal).setEditable(true);
        this.fText=JMPCInputStringTFWeblaf.create(R.label("TEXT"),"Input text", 20, width, horizontal).setEditable(true);
        this.fDouble=JMPCInputStringTFWeblaf.create(R.label("DOUBLE"),"Input double", 10, width, horizontal).setEditable(true);
        this.fBool=JMPCInputStringTFWeblaf.create(R.label("BOOL"),"Input boolean", 6, width, horizontal).setEditable(true);
        this.fDate=JMPCInputStringTFWeblaf.create(R.label("DATE"),"Input date", 15, width, horizontal).setEditable(true);
        this.fDateTime=JMPCInputStringTFWeblaf.create(R.label("DATETIME"),"Input date time", 20, width, horizontal).setEditable(true);
        
        this.table.setFormInterface(this.fInt, 0);
        this.table.setFormInterface(this.fString, 1);
        this.table.setFormInterface(this.fText, 2);
        this.table.setFormInterface(this.fDouble, 3);
        this.table.setFormInterface(this.fBool, 4);
        this.table.setFormInterface(this.fDate, 5);
        this.table.setFormInterface(this.fDateTime, 6);
        
        
        //List<Integer> excludedUpdateCols=new ArrayList();
        //excludedUpdateCols.add(1);
        //excludedUpdateCols.add(2);
        //this.table.excludeColumnsFromUpdate(excludedUpdateCols);
        //JMFunctions.trace(this.row.getUpdateSQL());
        this.row.displayInterface();
        this.fInt.setVisible(true);
        
        Box box=Box.createVerticalBox();
        box.add(this.fInt);
        box.add(this.fString);
        box.add(this.fText);
        box.add(this.fDouble);
        box.add(this.fBool);
        box.add(this.fDate);
        box.add(this.fDateTime);
        
        
        
        //form.getInputPanel().add(new JLabel("LASO"));
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.getInputPanel().repaint();
        form.setVisible(true);
        this.addListener();
    }
    
    public InputTes setEditMode(boolean editMode){
        this.fInt.setEditMode(editMode);
        this.fString.setEditMode(editMode);
        this.fText.setEditMode(editMode);
        this.fDouble.setEditMode(editMode);
        this.fBool.setEditMode(editMode);
        this.fDate.setEditMode(editMode);
        this.fDateTime.setEditMode(editMode);
        return this;
    }
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(InputTes.this.fInt.onEditMode()){
                    int res=JMFunctions.confirmBoxYN(InputTes.this.title, R.label("MESSAGE_CLOSE_ON_EDIT_MODE"), R.label("YES"), R.label("NO"), true);
                    if(res==JOptionPane.NO_OPTION)InputTes.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    else {
                        InputTes.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                }
                JMPCFunctions.linkTable(InputTes.this.tableTes.getJTable(), InputTes.this.table);
                InputTes.this.tableTes.getButtonGroup().stateInit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @Override
    public void actionAdd() {
        this.setEditMode(true);
        this.row=this.table.addNewRow(true);
        
        this.adding=true;
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL("select * from tes order by f_int desc", false);
        int v=r.getInt(0);
        v++;
        this.fInt.setText(String.valueOf(v));
    }

    @Override
    public void actionDelete() {
        if(JMFunctions.confirmBoxYN(R.label("TITLE_TES"), R.label("MESSAGE_CONFIRM_DELETE"), R.label("YES"), R.label("NO"), true)==JOptionPane.YES_OPTION){
            this.table.deleteRow(this.row,true);
            this.row=this.table.getCurrentRow();
            this.setEditMode(false);
            this.adding=false;
            this.btnGroup.stateNav();
        }
    }

    @Override
    public void actionSave() {
        JMFunctions.trace("BABI========     "+this.row.getUpdateSQL());
        this.setEditMode(false);
        this.adding=false;
        this.btnGroup.stateNav();
        /*if(JMFunctions.getCurrentConnection().queryUpdateMySQL(this.table.getCurrentRow().getUpdateSQL(), true)){
            JMFunctions.traceAndShow(R.label("SAVE_SUCCESS"));
        }else{
            JMFunctions.traceAndShow(R.label("SAVE_FAIhLED"));
        }*/
        
    }

    @Override
    public void actionEdit() {
        this.setEditMode(true);
        this.adding=false;
    }

    @Override
    public void actionPrint() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionRefresh() {
        //JMPCFunctions.linkTable(this.tableTes.getJTable(), table);
        //this.btnGroup.stateNav();
    }

    @Override
    public void actionView() {
        //NOTHING
    }

    @Override
    public void actionNext() {
        this.row=this.table.nextRow(true);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionPrev() {
        this.row=this.table.prevRow(true);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionFirst() {
        this.row=this.table.firstRow(true);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionLast() {
        this.row=this.table.lastRow(true);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionCancel() {
        this.setEditMode(false);
        if(this.adding){
            this.table.deleteRow(this.row,true);
            this.row=this.table.getCurrentRow();
        }
        this.adding=false;
        this.btnGroup.stateNav();
    }
    
}
