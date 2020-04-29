/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.JMPCFunctions;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jimi
 */
public class TableOPD {
    private String queryView;
    private JMTable table;
    private JTable jTable;
    
    public static TableOPD create(String query,JTable jTable){
        return new TableOPD(query,jTable);
    }
    
    public TableOPD(String query,JTable jTable){
        this.queryView=query;
        this.jTable=jTable;
        this.view(this.queryView);
    }
    
    public void view(String query){
        this.queryView=query;
        JMResultSet rs=JMFunctions.getCurrentConnection().queryMySQL(this.queryView, true);
        this.table=JMTable.create(rs);
        
        DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("INT");
        model.addColumn("STRING");
        model.addColumn("TEXT");
        model.addColumn("DOUBLE");
        model.addColumn("BOOL");
        model.addColumn("DATE");
        model.addColumn("DATETIME");
        JMPCFunctions.linkTable(this.jTable, this.table);
        //JMFunctions.trace("abis");
    }
}
