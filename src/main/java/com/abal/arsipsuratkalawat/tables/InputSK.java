/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat.tables;

import com.abal.arsipsuratkalawat.FormMain;
import com.abal.arsipsuratkalawat.FormView;
import com.abal.arsipsuratkalawat.FormViewAgenda;
import com.abal.arsipsuratkalawat.Global;
import com.abal.arsipsuratkalawat.R;
import com.asprise.imaging.core.Imaging;
import com.asprise.imaging.core.Request;
import com.asprise.imaging.core.RequestOutputItem;
import com.asprise.imaging.core.Result;
import com.asprise.imaging.core.scan.twain.TwainConstants;
import com.asprise.imaging.scan.ui.workbench.AspriseScanUI;
import com.thowo.jmjavaframework.JMAsyncTask;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMConnection;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.report.JMWordMM;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.JMPCAsyncLoaderPanel;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCImagesViewerDB;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblafAC;
import com.thowo.jmpcframework.component.form.JMPCSwitchWeblaf;
import com.thowo.jmpcframework.others.JMImageFilter;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Regina
 */
public class InputSK implements JMFormInterface {
    private final String title=R.label("TITLE_SK");
    private final JMTable table;
    private final FormViewAgenda form;
    private final FormMain parent;
    private boolean init=true;
    
    private JMPCInputStringTFWeblaf fIdSK;
    private JMPCInputStringTFWeblaf fAgendaSK;
    private JMPCInputStringTFWeblaf fNoSK;
    private JMPCInputStringTFWeblaf fTglSK;
    private JMPCInputStringTFWeblaf fHalSK;
    private JMPCInputStringTFWeblaf fSifatSK;
    private JMPCInputStringTFWeblaf fLampSK;
    private JMPCInputStringTFWeblaf fTglKeluarSK;
    private JMPCInputStringTFWeblaf fIdUserSK;
    private JMPCInputStringTFWeblaf fNamaUserSK;
    private JMPCInputStringTFWeblaf fTujuanSK;
    private JMPCInputStringTFWeblaf fKetSK;
    private JMPCImagesViewerDB fIdImgSK;
    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    public static InputSK create(JMTable table,FormMain parent,boolean editing,boolean adding){
        return new InputSK(table,parent,editing,adding);
    }
    
    public InputSK(JMTable table,FormMain parent,boolean editing,boolean adding){
        
        this.parent=parent;
        this.form=new FormViewAgenda(this.parent,true);
        this.form.setTitle(this.title);
        this.table=table;
        this.table.addInterface(this);
        this.btnGroup=JMPCDBButtonGroup.create(table,this.title,editing,adding);
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
        
        JPanel pnlButtons=form.getButtonsPanel();
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(this.btnGroup.getEditorPanel(),BorderLayout.WEST);
        pnlButtons.add(this.btnGroup.getNavigationPanel(),BorderLayout.EAST);
        this.row=this.table.getCurrentRow();
        this.view(editing,adding);
    }
    
    public void view(boolean editing,boolean adding){
        int width=400;
        boolean horizontal=true;
        this.fIdSK=JMPCInputStringTFWeblaf.create(R.label("ID_SK"),R.label("PROMPT_ID_SK"), 20, width, horizontal).setEditable(false);
        this.fAgendaSK=JMPCInputStringTFWeblaf.create(R.label("NO_AGENDA_SK"),R.label("PROMPT_NO_AGENDA_SK"), 20, width, horizontal).setEditable(true);
        this.fNoSK=JMPCInputStringTFWeblaf.create(R.label("NO_SK"),R.label("PROMPT_NO_SK"), 20, width, horizontal).setEditable(true);
        this.fTglSK=JMPCInputStringTFWeblaf.create(R.label("TGL_SK"),R.label("PROMPT_TGL_SK"), 20, width, horizontal).setEditable(true);
        this.fHalSK=JMPCInputStringTFWeblaf.create(R.label("PERIHAL_SK"),R.label("PROMPT_PERIHAL_SK"), 20, width, horizontal).setEditable(true);
        this.fSifatSK=JMPCInputStringTFWeblaf.create(R.label("SIFAT_SK"),R.label("PROMPT_SIFAT_SK"), 20, width, horizontal).setEditable(true);
        this.fLampSK=JMPCInputStringTFWeblaf.create(R.label("LAMPIRAN_SK"),R.label("PROMPT_LAMPIRAN_SK"), 20, width, horizontal).setEditable(true);
        this.fTglKeluarSK=JMPCInputStringTFWeblaf.create(R.label("TGL_KELUAR_SK"),R.label("PROMPT_TGL_KELUAR_SK"), 20, width, horizontal).setEditable(true);
        this.fIdUserSK=JMPCInputStringTFWeblaf.create(R.label("ID_USER_SK"),R.label("PROMPT_ID_USER_SK"), 20, width, horizontal).setEditable(false);
        this.fNamaUserSK=JMPCInputStringTFWeblaf.create(R.label("NAMA_USER_SK"),R.label("PROMPT_NAMA_USER_SK"), 20, width, horizontal).setEditable(false);
        this.fTujuanSK=JMPCInputStringTFWeblaf.create(R.label("TUJUAN_SK"),R.label("PROMPT_TUJUAN_SK"), 20, width, horizontal).setEditable(true);
        this.fKetSK=JMPCInputStringTFWeblaf.create(R.label("KET_SK"),R.label("PROMPT_KET_SK"), 20, width, horizontal).setEditable(true);
        this.fIdImgSK=JMPCImagesViewerDB.create(250,250).setEditable(true);
        
        
        this.table.setFormInterface(this.fIdSK, 0,true);
        this.table.setFormInterface(this.fAgendaSK, 1,true);
        this.table.setFormInterface(this.fNoSK, 2,true);
        this.table.setFormInterface(this.fTglSK, 3,true);
        this.table.setFormInterface(this.fHalSK, 4,true);
        this.table.setFormInterface(this.fSifatSK, 5,true);
        this.table.setFormInterface(this.fLampSK, 6,true);
        this.table.setFormInterface(this.fTglKeluarSK, 7,true);
        this.table.setFormInterface(this.fIdUserSK, 8,true);
        this.table.setFormInterface(this.fNamaUserSK, 9,true);
        this.table.setFormInterface(this.fTujuanSK, 10,true);
        this.table.setFormInterface(this.fKetSK, 11,true);
        this.table.setFormInterface(this.fIdImgSK, 12,true);
        
        this.row.displayInterface(true);
        this.fIdUserSK.setVisible(false);
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdSK);
        box.add(this.fAgendaSK);
        box.add(this.fNoSK);
        box.add(this.fTglSK);
        box.add(this.fHalSK);
        box.add(this.fSifatSK);
        box.add(this.fLampSK);
        box.add(this.fTglKeluarSK);
        box.add(this.fIdUserSK);
        box.add(this.fNamaUserSK);
        box.add(this.fTujuanSK);
        box.add(this.fKetSK);
        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        
        form.getImagesViewer().setLayout(new FlowLayout());
        form.getImagesViewer().add(this.fIdImgSK);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        if(!adding){
            this.table.viewRow();
            if(editing){
                this.table.editRow();
            }
            
        }
        this.init=false;
        
        this.lockAccess();
        
        this.btnGroup.getBtnView().setVisible(false);
        
        this.btnGroup.getBtnPrint().setVisible(false);
        
        form.setVisible(true);
    }
    
    private void lockAccess(){
        this.btnGroup.getBtnAdd().setVisible(Global.getEditor());
        this.btnGroup.getBtnDelete().setVisible(Global.getEditor());
        this.btnGroup.getBtnEdit().setVisible(Global.getEditor());
        this.btnGroup.getBtnSave().setVisible(Global.getEditor());
        this.btnGroup.getBtnCancel().setVisible(Global.getEditor());
        this.btnGroup.getBtnPrint().setVisible(Global.getEditor());
    }
    
    private void setEditMode(boolean editMode){
        this.editMode=editMode;
        this.fIdSK.setEditMode(editMode,this.row,0);
        this.fAgendaSK.setEditMode(editMode,this.row,1);
        this.fNoSK.setEditMode(editMode,this.row,2);
        this.fTglSK.setEditMode(editMode,this.row,3);
        this.fHalSK.setEditMode(editMode,this.row,4);
        this.fSifatSK.setEditMode(editMode,this.row,5);
        this.fLampSK.setEditMode(editMode,this.row,6);
        this.fTglKeluarSK.setEditMode(editMode,this.row,7);
        this.fIdUserSK.setEditMode(editMode,this.row,8);
        this.fNamaUserSK.setEditMode(editMode,this.row,9);
        this.fTujuanSK.setEditMode(editMode,this.row,10);
        this.fKetSK.setEditMode(editMode,this.row,11);
        this.fIdImgSK.setEditMode(editMode,this.row,12);
    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputSK.this.editMode){
                    InputSK.this.formClosing=true;
                    InputSK.this.btnGroup.btnCancelClick();
                }else{
                    InputSK.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        this.fIdImgSK.setAddAction(new Runnable(){
            @Override
            public void run() {
                
                String operSys = System.getProperty("os.name").toLowerCase();
                if (operSys.contains("win")) {
                    InputSK.this.imageFromScanner();
                }else{
                    InputSK.this.imageFromBrowser();
                }
            }
        });
        /*this.btnGroup.getBtnEdit().setAction(new Runnable(){
            @Override
            public void run() {
                InputTes.this.setEditMode(true);
            }
        });*/
    }
    
    
    private void refreshAutocomplete(){
        List<String> kOpd=new ArrayList();
        JMResultSet rOpd=JMFunctions.getCurrentConnection().queryMySQL("select * from opd", false);
        if(rOpd.first()){
            do{
                kOpd.add(rOpd.getString(1));
            }while(rOpd.next());
        }
        if(!kOpd.isEmpty()){
            //this.fAsalSK.setKeyword(kOpd);
        }
        
        List<String> kSS=new ArrayList();
        JMResultSet rSS=JMFunctions.getCurrentConnection().queryMySQL("select * from sifat_surat", false);
        if(rSS.first()){
            do{
                kSS.add(rSS.getString(1));
            }while(rSS.next());
        }
        if(!kSS.isEmpty()){
            //this.fSifatSK.setKeyword(kSS);
        }
    } 
    private void refreshImages(){
        if(!InputSK.this.form.isVisible() && !InputSK.this.init)return;
        InputSK.this.fIdImgSK.clearPaths();
        String key=InputSK.this.fIdSK.getText();
        String q="select * from gambar_sk where id_sk='"+key+"' order by halaman_sk asc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        InputSK.this.fIdImgSK.setKeyValue(key);
        if(r.first()){
            do{
                InputSK.this.fIdImgSK.addImage(r.getString(2));
            }while(r.next());
        }
    } 
    private void setTembusan(){
        /*if(this.editMode){
            if(this.fTembusanSK.getValueString().equals("true")){
                this.fTujuanSK.unlock();
                this.fTujuanSK.setText("");
            }else{
                this.fTujuanSK.lock();
                this.fTujuanSK.setText("Kantor Camat Kalawat");
            }
        }else{
            this.fTujuanSK.lock();
            this.fTujuanSK.setText(this.fTujuanSK.getValueString());
        }*/
        
    }
    private void deleteImageTmps(){
        int i=1;
        String dest=JMFunctions.getCacheDir()+"/image.tmp";
        while(JMFunctions.fileExist(new File(dest))){
            JMFunctions.deleteFile(new File(dest));
            dest=JMFunctions.getCacheDir()+"/image"+ i++ +".tmp";
        }
    }
    private String imageFromBrowser(){
        String ret="";
        String source="";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new JMImageFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(null);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            source= file.getPath();
            int i=1;
            
            String tmp=JMFunctions.getCacheDir()+"/image";
            String dest=JMFunctions.getCacheDir()+"/image.tmp";
            while(JMFunctions.fileExist(new File(dest))){
                dest=tmp+i+++".tmp";
            }
            //this.deleteImageTmps();

            JMFunctions.copyFile(source, dest);//SCANNED IMAGE
            ret=dest;
            this.fIdImgSK.addImage(ret);
            
            this.table.getCurrentRow().setValueFromString(12, "0");
        }else{
           JMFunctions.trace("Open Image canceled");
        }
        
        
        return ret;
    }
    private List<File> scan(){
        Result result = new AspriseScanUI().setRequest(new Request()
        .setTwainCap( // Scan in color:
          TwainConstants.ICAP_PIXELTYPE, TwainConstants.TWPT_RGB)
        .setTwainCap( // Paper size: US letter
          TwainConstants.ICAP_SUPPORTEDSIZES, TwainConstants.TWSS_USLETTER)
        .addOutputItem(
           new RequestOutputItem(Imaging.OUTPUT_SAVE, Imaging.FORMAT_JPG)
             .setSavePath(".\\${TMS}${EXT}") // Timestamp as file name
        )
        .addOutputItem(
           new RequestOutputItem(Imaging.OUTPUT_SAVE_THUMB, 
             Imaging.FORMAT_JPG).setSavePath(".\\${TMS}-thumb${EXT}")
        )
       ).showDialog(null, "Scan", true, null); // owner can be null

      return result.getImageFiles(); // Gets files
    }
    private void imageFromScanner(){
        List<File> imgs=this.scan();
        if(imgs==null)return;
        for(File img:imgs){
            String source= img.getPath();
            int i=1;
            
            String tmp=JMFunctions.getCacheDir()+"/image";
            String dest=JMFunctions.getCacheDir()+"/image.tmp";
            while(JMFunctions.fileExist(new File(dest))){
                dest=tmp+i+++".tmp";
            }
            JMFunctions.copyFile(source, dest);//SCANNED IMAGE
            this.fIdImgSK.addImage(dest);
        }
        this.table.getCurrentRow().setValueFromString(14, "0");
    }
    
    private void saveImages(){
        List<String> paths=this.fIdImgSK.getPaths();
        if(paths==null)return;
        String id=this.fIdSK.getText();
        String qD="delete from gambar_sk where id_sk='"+id+"'";
        String qU="replace into gambar_sk values(";
        if(!paths.isEmpty()){
            for(int i=0;i<paths.size();i++){
                String idDet=id+JMFormatCollection.leadingZero(i+1, 10);
                String url=JMFunctions.getDocDir()+"/docs/sk/"+id+"/"+(i+1)+".jpg";
                
                String validS=paths.get(i).replaceAll("\\\\", "/");
                String validD=url.replaceAll("\\\\", "/");
                
                if(!validS.equals(validD)){
                    JMFunctions.moveFileReplace(new File(validS), new File(validD));
                }
                
                if(i==0)qU+="'"+idDet+"','"+id+"','"+validD+"','"+(i+1)+"')";
                else qU+=",('"+idDet+"','"+id+"','"+validD+"','"+(i+1)+"')";
            }
            JMFunctions.getCurrentConnection().queryUpdateMySQL(qD, true);
            JMFunctions.getCurrentConnection().queryUpdateMySQL(qU, true);
        }
        this.deleteImageTmps();
    }
    
    
    
    
    
    @Override
    public void actionAfterAdded(JMRow rowAdded) {
        this.row=rowAdded;
        this.setEditMode(true);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterDeleted(JMRow rowDeleted, boolean deleted) {
        if(deleted){
            JMFunctions.trace("DELETED");
            JMFunctions.getCurrentConnection().queryUpdateMySQL("delete from gambar_sk where id_sk='"+rowDeleted.getCells().get(0).getDBValue()+"'", true);
            JMFunctions.deleteFolder(new File(JMFunctions.getDocDir()+"/docs/sk/"+rowDeleted.getCells().get(0).getDBValue()));
        }else{
            JMFunctions.trace("NOT DELETED");
        }
        this.setEditMode(false);
        this.row=this.table.getCurrentRow();
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
        
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
        JMFunctions.trace("===============================   SAVED");
        this.setEditMode(!saved);
        this.btnGroup.stateNav();
        this.saveImages();
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterEdited(JMRow rowEdited) {
        this.row=rowEdited;
        this.refreshImages();
        this.setEditMode(true);
        this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        
    }

    @Override
    public void actionAfterRefreshed(JMRow rowRefreshed) {
        this.row=rowRefreshed;
        this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterViewed(JMRow rowViewed) {
        this.row=rowViewed;
        this.setEditMode(false);
        this.refreshImages();
        this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterMovedNext(JMRow nextRow) {
        this.row=nextRow;
        //this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterMovedPrev(JMRow prevRow) {
        this.row=prevRow;
        //this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterMovedFirst(JMRow firstRow) {
        this.row=firstRow;
        //this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterMovedLast(JMRow lastRow) {
        this.row=lastRow;
        //this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterMovedtoRecord(JMRow currentRow) {
        this.row=currentRow;
        //this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterCanceled(JMRow rowCanceled, boolean canceled) {
        if(this.formClosing){
            if(canceled){
                this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }else{
                InputSK.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }else{
            this.setEditMode(!canceled);
            if(canceled)this.row=rowCanceled;
        }
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionBeforeRefresh(JMRow rowRefreshed) {
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
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
