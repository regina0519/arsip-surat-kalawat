/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat.tables;

import com.abal.arsipsuratkalawat.FormMain;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Regina
 */
public class TableSM implements JMFormInterface{
    private final String title=R.label("TITLE_SM");
    private final String queryView;
    private final JMTable dbObject;
    private final JMPCTable table;
    private final JMPCDBButtonGroup btnGroup;
    private final List<Integer> primaryKeys;
    private final FormMain parent;
    private InputSM inputSM;
    private int activeYear=2020;//============== 4 DIGIT ONLY
    
    public static TableSM create(String query,FormMain parent){
        return new TableSM(query,parent);
    }
    
    public TableSM(String query,FormMain parent){
        this.parent=parent;
        this.queryView=query;
        this.activeYear=this.parent.getYear();
        Object[] tembusanImg={JMFunctions.getResourcePath("img/true.png", this.getClass()).getPath(),JMFunctions.getResourcePath("img/false.png", this.getClass()).getPath()};
        
        this.dbObject=JMTable.create(this.queryView,JMTable.DBTYPE_MYSQL);
        
        this.dbObject.getStyle()
                .setLabel(0,R.label("ID_SM"))
                .setLabel(1,R.label("NO_AGENDA_SM"))
                .setLabel(2,R.label("NO_SM"))
                .setLabel(3,R.label("TGL_SM"))
                .setLabel(4,R.label("ASAL_SM"))
                .setLabel(5,R.label("PERIHAL_SM"))
                .setLabel(6,R.label("SIFAT_SM"))
                .setLabel(7,R.label("LAMPIRAN_SM"))
                .setLabel(8,R.label("TGL_TERIMA_SM"))
                .setLabel(9,R.label("ID_USER_SM"))
                .setLabel(10,R.label("NAMA_USER_SM"))
                .setLabel(11,R.label("TEMBUSAN_SM"))
                .setLabel(12,R.label("TUJUAN_SM"))
                .setLabel(13,R.label("KET_SM"))
                .setLabel(14,R.label("ID_SM"))
                .setColHidden(0).setColHidden(3).setColHidden(6).setColHidden(7).setColHidden(9).setColHidden(12).setColHidden(13).setColHidden(14)
                .addFormat(11, JMResultSetStyle.FORMAT_IMAGE, tembusanImg);
        this.dbObject.refresh();
        List<Integer> excluded=new ArrayList();
        excluded.add(10);
        excluded.add(14);
        this.dbObject.excludeColumnsFromUpdate(excluded);
        this.dbObject.addInterface(this);
        this.dbObject.setName("surat_masuk");
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
        
        
    }
    
    private void openForm(boolean editing, boolean adding){
        this.inputSM=InputSM.create(TableSM.this.dbObject,parent,editing,adding);
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
                    TableSM.this.openForm(false,false);
                }
            }
        });
        
        this.table.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 && !e.isConsumed()){
                    TableSM.this.openForm(false,false);
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
                TableSM.this.openForm(true,true);
            }
        });
        this.btnGroup.getBtnEdit().addAction(new Runnable(){
            @Override
            public void run() {
                TableSM.this.openForm(true,false);
            }
        });
        this.btnGroup.getBtnView().addAction(new Runnable(){
            @Override
            public void run() {
                TableSM.this.openForm(false,false);
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
                TableSM.this.dbObject.filter(textField.getText());
                TableSM.this.parent.setOnFiltered(!textField.getText().equals(""));
            }
        };
    }

    private String getNewId(){
        String ret="";
        int id=1;
        String q="select id_sm from surat_masuk where id_sm like '%SM"+this.activeYear+"%' order by id_sm desc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        Integer v=1;
        if(r.first()){
            String str=r.getString(0);
            str=str.substring(6);
            v=Integer.valueOf(str);
        }
        ret="SM"+this.activeYear+JMFormatCollection.leadingZero(++v, 4);
        return ret;
    }
    private String getNewReg(){
        String ret="";
        int id=1;
        String q="select no_agenda from surat_masuk where id_sm like '%SM"+this.activeYear+"%' order by no_agenda desc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        Integer v=1;
        String str="";
        if(r.first()){
            str=r.getString(0);
            try{
                v=Integer.valueOf(str);
                str=JMFormatCollection.leadingZero(++v, 3);
            }catch(NumberFormatException e){
                
            }
        }
        ret=str;
        return ret;
    }
    
    

    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        rowAdded.setValueFromString(0, this.getNewId());
        rowAdded.setValueFromString(1, this.getNewReg());
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
