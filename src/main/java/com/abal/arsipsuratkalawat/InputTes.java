/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.JMPCForm;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;

/**
 *
 * @author jimi
 */
public class InputTes {
    private String queryView;
    private JMTable table;
    private FormInput form;
    
    private JMPCInputStringTFWeblaf fInt;
    private JMPCInputStringTFWeblaf fString;
    private JMPCInputStringTFWeblaf fText;
    private JMPCInputStringTFWeblaf fDouble;
    private JMPCInputStringTFWeblaf fBool;
    private JMPCInputStringTFWeblaf fDate;
    private JMPCInputStringTFWeblaf fDateTime;
    
    public static InputTes create(FormInput form){
        return new InputTes(form);
    }
    
    public InputTes(FormInput form){
        this.queryView="select * from tes";
        this.form=form;
        this.view();
    }
    
    public void view(){
        JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        JMResultSetStyle style=JMResultSetStyle.create(rs.getSQLResultSet());
        style=style.setColHidden(0);
        this.table=JMTable.create(rs,style);
        
        int width=400;
        boolean horizontal=false;
        //this.fInt=JMPCInputStringTFWeblaf.create("value", "", "error", "Tes", "hahah", 10,400,true);
        this.fInt=JMPCInputStringTFWeblaf.create(R.label("INT"),"Input integer", 6, width, horizontal);
        this.fString=JMPCInputStringTFWeblaf.create(R.label("STRING"),"Input string", 20, width, horizontal);
        this.fText=JMPCInputStringTFWeblaf.create(R.label("TEXT"),"Input text", 20, width, horizontal);
        this.fDouble=JMPCInputStringTFWeblaf.create(R.label("DOUBLE"),"Input double", 10, width, horizontal);
        this.fBool=JMPCInputStringTFWeblaf.create(R.label("BOOL"),"Input boolean", 6, width, horizontal);
        this.fDate=JMPCInputStringTFWeblaf.create(R.label("DATE"),"Input date", 15, width, horizontal);
        this.fDateTime=JMPCInputStringTFWeblaf.create(R.label("DATETIME"),"Input date time", 6, width, horizontal);
        
        this.table.setFormInterface(this.fInt, 0);
        this.table.setFormInterface(this.fString, 1);
        this.table.setFormInterface(this.fText, 2);
        this.table.setFormInterface(this.fDouble, 3);
        this.table.setFormInterface(this.fBool, 4);
        this.table.setFormInterface(this.fDate, 5);
        this.table.setFormInterface(this.fDateTime, 6);
        
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
        
    }
    
}
