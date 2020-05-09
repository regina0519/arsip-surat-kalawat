/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author jimi
 */
public class InputTes implements JMFormInterface {
    private final String title=R.label("TITLE_TES");
    private final JMTable table;
    private final FormView form;
    private final FormMain parent;
    
    private JMPCInputStringTFWeblaf fInt;
    private JMPCInputStringTFWeblaf fString;
    private JMPCInputStringTFWeblaf fText;
    private JMPCInputStringTFWeblaf fDouble;
    private JMPCInputStringTFWeblaf fBool;
    private JMPCInputStringTFWeblaf fDate;
    private JMPCInputStringTFWeblaf fDateTime;
    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    public static InputTes create(JMTable table,FormMain parent,boolean editing,boolean adding){
        return new InputTes(table,parent,editing,adding);
    }
    
    public InputTes(JMTable table,FormMain parent,boolean editing,boolean adding){
        
        this.parent=parent;
        this.form=new FormView(this.parent,true);
        this.form.setTitle(this.title);
        this.table=table;
        this.table.addInterface(this);
        this.btnGroup=JMPCDBButtonGroup.create(table,this.title,editing,adding);
        JPanel pnlButtons=form.getButtonsPanel();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        this.row=this.table.getCurrentRow();
        JMFunctions.trace(this.row.getDataContainers().get(0).getValueString());
        this.view(editing,adding);
    }
    
    public void view(boolean editing,boolean adding){
        int width=400;
        boolean horizontal=true;
        this.fInt=JMPCInputStringTFWeblaf.create(R.label("INT"),"Input integer", 6, width, horizontal).setEditable(false);
        this.fString=JMPCInputStringTFWeblaf.create(R.label("STRING"),"Input string", 20, width, horizontal).setEditable(true);
        this.fText=JMPCInputStringTFWeblaf.create(R.label("TEXT"),"Input text", 20, width, horizontal).setEditable(true);
        this.fDouble=JMPCInputStringTFWeblaf.create(R.label("DOUBLE"),"Input double", 10, width, horizontal).setEditable(true);
        this.fBool=JMPCInputStringTFWeblaf.create(R.label("BOOL"),"Input boolean", 6, width, horizontal).setEditable(true);
        this.fDate=JMPCInputStringTFWeblaf.create(R.label("DATE"),"Input date", 15, width, horizontal).setEditable(true);
        this.fDateTime=JMPCInputStringTFWeblaf.create(R.label("DATETIME"),"Input date time", 20, width, horizontal).setEditable(true);
        
        
        this.table.setFormInterface(this.fInt, 0,true);
        this.table.setFormInterface(this.fString, 1,true);
        this.table.setFormInterface(this.fText, 2,true);
        this.table.setFormInterface(this.fDouble, 3,true);
        this.table.setFormInterface(this.fBool, 4,true);
        this.table.setFormInterface(this.fDate, 5,true);
        this.table.setFormInterface(this.fDateTime, 6,true);
        
        this.row.displayInterface(true);
        //this.fInt.setVisible(true);
        
        Box box=Box.createVerticalBox();
        box.add(this.fInt);
        box.add(this.fString);
        box.add(this.fText);
        box.add(this.fDouble);
        box.add(this.fBool);
        box.add(this.fDate);
        box.add(this.fDateTime);
        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        form.setVisible(true);
    }
    
    private void setEditMode(boolean editMode){
        this.editMode=editMode;
        this.fInt.setEditMode(editMode,this.row,0);
        this.fString.setEditMode(editMode,this.row,1);
        this.fText.setEditMode(editMode,this.row,2);
        this.fDouble.setEditMode(editMode,this.row,3);
        this.fBool.setEditMode(editMode,this.row,4);
        this.fDate.setEditMode(editMode,this.row,5);
        this.fDateTime.setEditMode(editMode,this.row,6);
    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputTes.this.editMode){
                    InputTes.this.formClosing=true;
                    InputTes.this.btnGroup.btnCancelClick();
                }else{
                    InputTes.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
            }

            @Override
            public void windowIconified(WindowEvent e) {
                
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                
            }

            @Override
            public void windowActivated(WindowEvent e) {
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                
            }
        });
        /*this.btnGroup.getBtnEdit().setAction(new Runnable(){
            @Override
            public void run() {
                InputTes.this.setEditMode(true);
            }
        });*/
    }
    
    
    
    
    
    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        JMFunctions.trace("ADDED RESPONSE FROM INPUT TES");
        this.row=rowAdded;
        this.setEditMode(true);
        
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted) {
        this.setEditMode(false);
        this.row=this.table.getCurrentRow();
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        this.setEditMode(!saved);
        this.btnGroup.stateNav();
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        this.row=rowEdited;
        this.setEditMode(true);
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        this.row=rowPrinted;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        this.row=rowRefreshed;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        this.row=rowViewed;
        this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        this.row=nextRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        this.row=prevRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        this.row=firstRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        this.row=lastRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        this.row=currentRow;
        //this.setEditMode(false);
    }

    @Override
    public void actionAfterCanceled(JMRow rowCanceled, boolean canceled) {
        if(this.formClosing){
            if(canceled){
                this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }else{
                InputTes.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }else{
            this.setEditMode(!canceled);
            if(canceled)this.row=rowCanceled;
        }
    }
    
}
