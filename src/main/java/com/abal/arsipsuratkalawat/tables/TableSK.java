/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat.tables;

import com.abal.arsipsuratkalawat.FormMain;
import com.abal.arsipsuratkalawat.Global;
import com.abal.arsipsuratkalawat.R;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.db.JMResultSetStyle;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.JMPCTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author regina
 */
public class TableSK implements JMFormInterface{
    private final String title=R.label("TITLE_SK");
    private final String queryView;
    private final JMTable dbObject;
    private final JMPCTable table;
    private final JMPCDBButtonGroup btnGroup;
    private final List<Integer> primaryKeys;
    private final FormMain parent;
    private InputSK inputSK;
    private int activeYear=2020;//============== 4 DIGIT ONLY
    
    public static TableSK create(String query,FormMain parent){
        return new TableSK(query,parent);
    }
    
    public TableSK(String query,FormMain parent){
        this.parent=parent;
        this.queryView=query;
        this.activeYear=this.parent.getYear();
        Object[] tembusanImg={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        this.dbObject=JMTable.create(this.queryView,JMTable.DBTYPE_MYSQL);
        
        this.dbObject.getStyle()
                .setLabel(0,R.label("ID_SK"))
                .setLabel(1,R.label("NO_AGENDA_SK"))
                .setLabel(2,R.label("NO_SK"))
                .setLabel(3,R.label("TGL_SK"))
                .setLabel(4,R.label("PERIHAL_SK"))
                .setLabel(5,R.label("SIFAT_SK"))
                .setLabel(6,R.label("LAMPIRAN_SK"))
                .setLabel(7,R.label("TGL_KELUAR_SK"))
                .setLabel(8,R.label("ID_USER_SK"))
                .setLabel(9,R.label("NAMA_USER_SK"))
                .setLabel(10,R.label("TUJUAN_SK"))
                .setLabel(11,R.label("KET_SK"))
                .setLabel(12,R.label("ID_SK"))
                .setColHidden(0).setColHidden(5).setColHidden(6).setColHidden(7).setColHidden(8).setColHidden(11).setColHidden(12);
        this.dbObject.refresh();
        List<Integer> excluded=new ArrayList();
        excluded.add(9);
        excluded.add(12);
        this.dbObject.excludeColumnsFromUpdate(excluded);
        this.dbObject.addInterface(this);
        this.dbObject.setName("surat_keluar");
        this.primaryKeys=new ArrayList();
        this.primaryKeys.add(0);
        this.dbObject.setKeyColumns(this.primaryKeys);
        
        this.table=JMPCTable.create(this.dbObject);
        JScrollPane sp=new JScrollPane(this.table);
        JPanel pnlTable=parent.getPanelTable();
        pnlTable.removeAll();
        pnlTable.setLayout(new BorderLayout());
        pnlTable.add(sp,BorderLayout.CENTER);
        
        this.btnGroup=new JMPCDBButtonGroup(this.dbObject,this.title,false,false);
        this.btnGroup.getBtnAdd().setText(R.label("DB_ADD"));
        this.btnGroup.getBtnDelete().setText(R.label("DB_DELETE"));
        this.btnGroup.getBtnEdit().setText(R.label("DB_EDIT"));
        this.btnGroup.getBtnSave().setText(R.label("DB_SAVE"));
        this.btnGroup.getBtnCancel().setText(R.label("DB_CANCEL"));
        this.btnGroup.getBtnRefresh().setText(R.label("DB_REFRESH"));
        this.btnGroup.getBtnPrint().setText(R.label("DB_PRINT"));
        this.btnGroup.getBtnFirst().setText(R.label("DB_FIRST"));
        this.btnGroup.getBtnLast().setText(R.label("DB_LAST"));
        this.btnGroup.getBtnNext().setText(R.label("DB_NEXT"));
        this.btnGroup.getBtnPrev().setText(R.label("DB_PREV"));
        
        JPanel pnlButtons=parent.getPanelButtons();
        pnlButtons.removeAll();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        
        this.addListener();
        
        //GOTO FIRST RECORD
        if(!this.dbObject.isEmpty()){
            this.dbObject.firstRow(false);
            this.btnGroup.stateInit();
        }
        
        this.lockAccess();
        
    }
    
    private void lockAccess(){
        this.btnGroup.getBtnAdd().setVisible(Global.getEditor());
        this.btnGroup.getBtnDelete().setVisible(Global.getEditor());
        this.btnGroup.getBtnEdit().setVisible(Global.getEditor());
        this.btnGroup.getBtnSave().setVisible(Global.getEditor());
        this.btnGroup.getBtnCancel().setVisible(Global.getEditor());
        this.btnGroup.getBtnPrint().setVisible(Global.getEditor());
    }
    
    private void openForm(boolean editing, boolean adding){
        this.inputSK=InputSK.create(TableSK.this.dbObject,parent,editing,adding);
        //InputSK.create(TableSK.this.dbObject,parent,editing,adding);
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
                    TableSK.this.openForm(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TableSK.this.openForm(false,false);
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
        
        this.btnGroup.getBtnAdd().addAction(new Runnable(){
            @Override
            public void run() {
                TableSK.this.openForm(true,true);
            }
        });
        this.btnGroup.getBtnEdit().addAction(new Runnable(){
            @Override
            public void run() {
                TableSK.this.openForm(true,false);
            }
        });
        this.btnGroup.getBtnView().addAction(new Runnable(){
            @Override
            public void run() {
                TableSK.this.openForm(false,false);
            }
        });
        
    }
    
    public JMPCDBButtonGroup getButtonGroup(){
        return this.btnGroup;
    }
    public JMPCTable getTable(){
        return this.table;
    }
    public Runnable filter(JMPCInputStringTFWeblaf textField){
        return new Runnable(){
            @Override
            public void run() {
                TableSK.this.dbObject.filter(textField.getText());
                TableSK.this.parent.setOnFiltered(!textField.getText().equals(""));
            }
        };
    }

    private String getNewId(){
        String ret="";
        String q="select id_sk from surat_keluar where id_sk like '%SK"+this.activeYear+"%' order by id_sk desc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        Integer v=0;
        if(r.first()){
            String str=r.getString(0);
            str=str.substring(6);
            v=Integer.valueOf(str);
        }
        ret="SK"+this.activeYear+JMFormatCollection.leadingZero(++v, 4);
        return ret;
    }
    private String getNewReg(){
        String ret="";
        String q="select no_agenda from surat_keluar where id_sk like '%SK"+this.activeYear+"%' order by no_agenda desc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        Integer v=0;
        String str="";
        if(r.first()){
            str=r.getString(0);
            try{
                v=Integer.valueOf(str);
                str=JMFormatCollection.leadingZero(++v, 3);
            }catch(NumberFormatException e){
                
            }
        }else str="001";
        ret=str;
        return ret;
    }
    
    

    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        rowAdded.setValueFromString(0, this.getNewId());
        rowAdded.setValueFromString(1, this.getNewReg());
        rowAdded.setValueFromString(8, Global.getUser());
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL("select * from user where id_user='"+Global.getUser()+"'", Boolean.TRUE);
        if(r.first()){
            rowAdded.setValueFromString(9, r.getString("nama_user"));
        }
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted) {
        
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        List<Integer> ex=new ArrayList();
        ex.add(0);
        ex.add(8);
        ex.add(12);
        /*.setLabel(0,R.label("ID_SK"))
        .setLabel(1,R.label("NO_AGENDA_SK"))
        .setLabel(2,R.label("NO_SK"))
        .setLabel(3,R.label("TGL_SK"))
        .setLabel(4,R.label("PERIHAL_SK"))
        .setLabel(5,R.label("SIFAT_SK"))
        .setLabel(6,R.label("LAMPIRAN_SK"))
        .setLabel(7,R.label("TGL_KELUAR_SK"))
        .setLabel(8,R.label("ID_USER_SK"))
        .setLabel(9,R.label("NAMA_USER_SK"))
        .setLabel(10,R.label("TUJUAN_SK"))
        .setLabel(11,R.label("KET_SK"))
        .setLabel(12,R.label("ID_SK"))*/
        JMFunctions.writeTableToExcel(this.dbObject, JMFunctions.getDocDir()+"/"+this.dbObject.getName()+".xlsx", ex);
        try {
            Desktop.getDesktop().open(new File(JMFunctions.getDocDir()+"/"+this.dbObject.getName()+".xlsx"));
        } catch (IOException e) {
            Logger.getLogger(InputSK.class.getName()).log(Level.SEVERE, null, ex);
            JMFunctions.errorMessage("Gagal membuka file laporan");
        }
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        
    }

    @Override
    public void actionAfterCanceled(JMRow rowCanceled, boolean canceled) {
        
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
        
    }
    
}
