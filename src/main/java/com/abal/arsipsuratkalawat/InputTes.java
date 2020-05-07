/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMInputInterface;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private FormInput form=new FormInput();
    
    private JMPCInputStringTFWeblaf fInt;
    private JMPCInputStringTFWeblaf fString;
    private JMPCInputStringTFWeblaf fText;
    private JMPCInputStringTFWeblaf fDouble;
    private JMPCInputStringTFWeblaf fBool;
    private JMPCInputStringTFWeblaf fDate;
    private JMPCInputStringTFWeblaf fDateTime;
    private JMRow row;
    private JMPCDBButtonGroup btnGroup;
    private boolean adding=false;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    public static InputTes create(JMTable table){
        return new InputTes(table);
    }
    
    public InputTes(JMTable table){
        this.table=table;
        this.table.addInterface(this);
        this.btnGroup=JMPCDBButtonGroup.create(table);
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
        
        this.table.setFormInterface(this.fInt, 0,true);
        this.table.setFormInterface(this.fString, 1,true);
        this.table.setFormInterface(this.fText, 2,true);
        this.table.setFormInterface(this.fDouble, 3,true);
        this.table.setFormInterface(this.fBool, 4,true);
        this.table.setFormInterface(this.fDate, 5,true);
        this.table.setFormInterface(this.fDateTime, 6,true);
        
        
        //List<Integer> excludedUpdateCols=new ArrayList();
        //excludedUpdateCols.add(1);
        //excludedUpdateCols.add(2);
        //this.table.excludeColumnsFromUpdate(excludedUpdateCols);
        //JMFunctions.trace(this.row.getUpdateSQL());
        this.row.displayInterface(true);
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
        this.editMode=editMode;
        this.fInt.setEditMode(editMode,this.row);
        this.fString.setEditMode(editMode,this.row);
        this.fText.setEditMode(editMode,this.row);
        this.fDouble.setEditMode(editMode,this.row);
        this.fBool.setEditMode(editMode,this.row);
        this.fDate.setEditMode(editMode,this.row);
        this.fDateTime.setEditMode(editMode,this.row);
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
                if(InputTes.this.editMode){
                    InputTes.this.formClosing=true;
                    InputTes.this.btnGroup.btnCancelClick();
                }else{
                    InputTes.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
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
        this.btnGroup.getBtnEdit().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                InputTes.this.setEditMode(true);
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


    @Override
    public void actionAdd(JMRow rowAdded) {
        this.row=rowAdded;
        this.setEditMode(true);
        
        this.adding=true;
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL("select * from tes order by f_int desc", false);
        Integer v=r.getInt(0);
        v++;
        //this.fInt.setText(String.valueOf(v));
        JMFunctions.trace(this.row.getRowNum()+"");
    }

    @Override
    public void actionDelete(JMRow rowDeleted) {
        this.adding=false;
        this.setEditMode(false);
        /*if(JMFunctions.confirmBoxYN(R.label("TITLE_TES"), R.label("MESSAGE_CONFIRM_DELETE"), R.label("YES"), R.label("NO"), true)==JOptionPane.YES_OPTION){
            this.table.deleteRow(this.row,true);
            this.row=this.table.getCurrentRow();
            this.setEditMode(false);
            this.adding=false;
            this.btnGroup.stateNav();
        }*/
    }

    @Override
    public void actionSave(String updateQuery) {
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
    public void actionEdit(JMRow rowEdited) {
        this.row=rowEdited;
        this.setEditMode(true);
        this.adding=false;
    }

    @Override
    public void actionPrint(JMRow rowPrinted) {
        this.row=rowPrinted;
    }

    @Override
    public void actionRefresh(JMRow rowRefreshed) {
        this.row=rowRefreshed;
    }

    @Override
    public void actionView(JMRow rowViewed) {
        this.row=rowViewed;
    }

    @Override
    public void actionNext(JMRow nextRow) {
        this.row=nextRow;
    }

    @Override
    public void actionPrev(JMRow prevRow) {
        this.row=prevRow;
    }

    @Override
    public void actionFirst(JMRow firstRow) {
        this.row=firstRow;
    }

    @Override
    public void actionLast(JMRow lastRow) {
        this.row=lastRow;
    }

    @Override
    public void gotoRecord(JMRow currentRow) {
        this.row=currentRow;
    }

    @Override
    public void actionCancel(JMRow rowCanceled, boolean canceled) {
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
