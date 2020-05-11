/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat.tables;

import com.abal.arsipsuratkalawat.FormMain;
import com.abal.arsipsuratkalawat.FormView;
import com.abal.arsipsuratkalawat.InputTes;
import com.abal.arsipsuratkalawat.R;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import com.thowo.jmpcframework.component.form.JMPCSwitchWeblaf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Regina
 */
public class InputUser implements JMFormInterface {
    private final String title=R.label("TITLE_USER");
    private final JMTable table;
    private final FormView form;
    private final FormMain parent;
    
    private JMPCInputStringTFWeblaf fId;
    private JMPCInputStringTFWeblaf fPass;
    private JMPCInputStringTFWeblaf fNama;
    private JMPCSwitchWeblaf fEditor;
    private JMPCSwitchWeblaf fAdmin;
    private JButton reset;
    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    private boolean resetClicked=false;
    
    public static InputUser create(JMTable table,FormMain parent,boolean editing,boolean adding){
        return new InputUser(table,parent,editing,adding);
    }
    
    public InputUser(JMTable table,FormMain parent,boolean editing,boolean adding){
        
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
        this.fId=JMPCInputStringTFWeblaf.create(R.label("ID_USER"),R.label("PROMPT_ID_USER"), 6, width, horizontal).setEditable(true);
        this.fPass=JMPCInputStringTFWeblaf.create(R.label("PASS_USER"),R.label("PROMPT_PASS_USER"), 20, width, horizontal).setEditable(false);
        this.fNama=JMPCInputStringTFWeblaf.create(R.label("NAMA_USER"),R.label("PROMPT_NAMA_USER"), 20, width, horizontal).setEditable(true);
        this.fEditor=JMPCSwitchWeblaf.create("Editor","Non-editor", 10, width, horizontal).setEditable(true);
        this.fAdmin=JMPCSwitchWeblaf.create("Admin","Non-admin", 6, width, horizontal).setEditable(true);
        
        
        this.table.setFormInterface(this.fId, 0,true);
        this.table.setFormInterface(this.fPass, 1,true);
        this.table.setFormInterface(this.fNama, 2,true);
        this.table.setFormInterface(this.fEditor, 3,true);
        this.table.setFormInterface(this.fAdmin, 4,true);
        
        this.row.displayInterface(true);
        this.fPass.setVisible(false);
        
        Box box=Box.createVerticalBox();
        box.add(this.fId);
        box.add(this.fPass);
        box.add(this.fNama);
        box.add(this.fEditor);
        box.add(this.fAdmin);
        this.reset=new JButton("Reset Password");
        box.add(this.reset);
        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        
        
        this.btnGroup.getBtnPrint().setVisible(false);
        this.btnGroup.getBtnView().setVisible(false);
        
        form.setVisible(true);
    }
    
    private void setEditMode(boolean editMode){
        this.editMode=editMode;
        this.fId.setEditMode(editMode,this.row,0);
        this.fPass.setEditMode(editMode,this.row,1);
        this.fNama.setEditMode(editMode,this.row,2);
        this.fEditor.setEditMode(editMode,this.row,3);
        this.fAdmin.setEditMode(editMode,this.row,4);
        this.reset.setEnabled(editMode);
        
    }
    
    
    
    
    private void addListener(){
        this.reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //InputUser.this.fPass.setText(JMPCFunctions.encrypt("kalawat"));
                InputUser.this.table.getCurrentRow().setValueFromString(1, JMPCFunctions.encrypt("kalawat"));
            }
        });
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputUser.this.editMode){
                    InputUser.this.formClosing=true;
                    InputUser.this.btnGroup.btnCancelClick();
                }else{
                    InputUser.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
                InputUser.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }else{
            this.setEditMode(!canceled);
            if(canceled)this.row=rowCanceled;
        }
    }

    @Override
    public void actionBeforeRefresh(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionAfterFiltered(String filter) {
        this.parent.setSearch(filter);
    }

    @Override
    public void actionBeforeFilter(String filter) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
