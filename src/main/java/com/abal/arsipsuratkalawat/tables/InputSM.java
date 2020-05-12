/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat.tables;

import com.abal.arsipsuratkalawat.FormMain;
import com.abal.arsipsuratkalawat.FormView;
import com.abal.arsipsuratkalawat.FormViewAgenda;
import com.abal.arsipsuratkalawat.R;
import com.asprise.imaging.core.Imaging;
import com.asprise.imaging.core.Request;
import com.asprise.imaging.core.RequestOutputItem;
import com.asprise.imaging.core.Result;
import com.asprise.imaging.core.scan.twain.TwainConstants;
import com.asprise.imaging.scan.ui.workbench.AspriseScanUI;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.table.JMRow;
import com.thowo.jmjavaframework.table.JMTable;
import com.thowo.jmpcframework.component.form.JMPCDBButtonGroup;
import com.thowo.jmpcframework.component.form.JMPCImagesViewerDB;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblafAC;
import com.thowo.jmpcframework.component.form.JMPCSwitchWeblaf;
import com.thowo.jmpcframework.others.JMImageFilter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Regina
 */
public class InputSM implements JMFormInterface {
    private final String title=R.label("TITLE_SM");
    private final JMTable table;
    private final FormViewAgenda form;
    private final FormMain parent;
    private boolean init=true;
    
    private JMPCInputStringTFWeblaf fIdSM;
    private JMPCInputStringTFWeblaf fAgendaSM;
    private JMPCInputStringTFWeblaf fNoSM;
    private JMPCInputStringTFWeblaf fTglSM;
    private JMPCInputStringTFWeblaf fAsalSM;
    private JMPCInputStringTFWeblaf fHalSM;
    private JMPCInputStringTFWeblaf fSifatSM;
    private JMPCInputStringTFWeblaf fLampSM;
    private JMPCInputStringTFWeblaf fTglTerimaSM;
    private JMPCInputStringTFWeblaf fIdUserSM;
    private JMPCInputStringTFWeblaf fNamaUserSM;
    private JMPCSwitchWeblaf fTembusanSM;
    private JMPCInputStringTFWeblaf fTujuanSM;
    private JMPCInputStringTFWeblaf fKetSM;
    private JMPCImagesViewerDB fIdImgSM;
    private JMRow row;
    private final JMPCDBButtonGroup btnGroup;
    private boolean editMode=false;
    private boolean formClosing=false;
    
    public static InputSM create(JMTable table,FormMain parent,boolean editing,boolean adding){
        return new InputSM(table,parent,editing,adding);
    }
    
    public InputSM(JMTable table,FormMain parent,boolean editing,boolean adding){
        
        this.parent=parent;
        this.form=new FormViewAgenda(this.parent,true);
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
        this.fIdSM=JMPCInputStringTFWeblaf.create(R.label("ID_SM"),R.label("PROMPT_ID_SM"), 20, width, horizontal).setEditable(false);
        this.fAgendaSM=JMPCInputStringTFWeblaf.create(R.label("NO_AGENDA_SM"),R.label("PROMPT_NO_AGENDA_SM"), 20, width, horizontal).setEditable(true);
        this.fNoSM=JMPCInputStringTFWeblaf.create(R.label("NO_SM"),R.label("PROMPT_NO_SM"), 20, width, horizontal).setEditable(true);
        this.fTglSM=JMPCInputStringTFWeblaf.create(R.label("TGL_SM"),R.label("PROMPT_TGL_SM"), 20, width, horizontal).setEditable(true);
        this.fAsalSM=JMPCInputStringTFWeblaf.create(R.label("ASAL_SM"),R.label("PROMPT_ASAL_SM"), 20, width, horizontal).setEditable(true);
        this.fHalSM=JMPCInputStringTFWeblaf.create(R.label("PERIHAL_SM"),R.label("PROMPT_PERIHAL_SM"), 20, width, horizontal).setEditable(true);
        this.fSifatSM=JMPCInputStringTFWeblaf.create(R.label("SIFAT_SM"),R.label("PROMPT_SIFAT_SM"), 20, width, horizontal).setEditable(true);
        this.fLampSM=JMPCInputStringTFWeblaf.create(R.label("LAMPIRAN_SM"),R.label("PROMPT_LAMPIRAN_SM"), 20, width, horizontal).setEditable(true);
        this.fTglTerimaSM=JMPCInputStringTFWeblaf.create(R.label("TGL_TERIMA_SM"),R.label("PROMPT_TGL_TERIMA_SM"), 20, width, horizontal).setEditable(true);
        this.fIdUserSM=JMPCInputStringTFWeblaf.create(R.label("ID_USER_SM"),R.label("PROMPT_ID_USER_SM"), 20, width, horizontal).setEditable(false);
        this.fNamaUserSM=JMPCInputStringTFWeblaf.create(R.label("NAMA_USER_SM"),R.label("PROMPT_NAMA_USER_SM"), 20, width, horizontal).setEditable(false);
        this.fTembusanSM=JMPCSwitchWeblaf.create(R.label("TEMBUSAN_TRUE"),R.label("TEMBUSAN_FALSE"), 20, width, horizontal).setEditable(true);
        this.fTujuanSM=JMPCInputStringTFWeblaf.create(R.label("TUJUAN_SM"),R.label("PROMPT_TUJUAN_SM"), 20, width, horizontal).setEditable(true);
        this.fKetSM=JMPCInputStringTFWeblaf.create(R.label("KET_SM"),R.label("PROMPT_KET_SM"), 20, width, horizontal).setEditable(true);
        this.fIdImgSM=JMPCImagesViewerDB.create(250,250).setEditable(true);
        
        
        this.table.setFormInterface(this.fIdSM, 0,true);
        this.table.setFormInterface(this.fAgendaSM, 1,true);
        this.table.setFormInterface(this.fNoSM, 2,true);
        this.table.setFormInterface(this.fTglSM, 3,true);
        this.table.setFormInterface(this.fAsalSM, 4,true);
        this.table.setFormInterface(this.fHalSM, 5,true);
        this.table.setFormInterface(this.fSifatSM, 6,true);
        this.table.setFormInterface(this.fLampSM, 7,true);
        this.table.setFormInterface(this.fTglTerimaSM, 8,true);
        this.table.setFormInterface(this.fIdUserSM, 9,true);
        this.table.setFormInterface(this.fNamaUserSM, 10,true);
        this.table.setFormInterface(this.fTembusanSM, 11,true);
        this.table.setFormInterface(this.fTujuanSM, 12,true);
        this.table.setFormInterface(this.fKetSM, 13,true);
        this.table.setFormInterface(this.fIdImgSM, 14,true);
        
        this.row.displayInterface(true);
        this.fIdUserSM.setVisible(false);
        
        Box box=Box.createVerticalBox();
        box.add(this.fIdSM);
        box.add(this.fAgendaSM);
        box.add(this.fNoSM);
        box.add(this.fTglSM);
        box.add(this.fAsalSM);
        box.add(this.fHalSM);
        box.add(this.fSifatSM);
        box.add(this.fLampSM);
        box.add(this.fTglTerimaSM);
        box.add(this.fIdUserSM);
        box.add(this.fNamaUserSM);
        box.add(this.fTembusanSM);
        box.add(this.fTujuanSM);
        box.add(this.fKetSM);
        
        
        form.getInputPanel().setLayout(new FlowLayout());
        form.getInputPanel().add(box);
        
        form.getImagesViewer().setLayout(new FlowLayout());
        form.getImagesViewer().add(this.fIdImgSM);
        form.pack();
        this.addListener();
        
        
        this.setEditMode(editing);
        //this.table.getCurrentRow().displayInterface(false);
        this.table.viewRow();
        this.init=false;
        
        
        
        this.btnGroup.getBtnView().setVisible(false);
        
        form.setVisible(true);
    }
    
    private void setEditMode(boolean editMode){
        this.editMode=editMode;
        this.fIdSM.setEditMode(editMode,this.row,0);
        this.fAgendaSM.setEditMode(editMode,this.row,1);
        this.fNoSM.setEditMode(editMode,this.row,2);
        this.fTglSM.setEditMode(editMode,this.row,3);
        this.fAsalSM.setEditMode(editMode,this.row,4);
        this.fHalSM.setEditMode(editMode,this.row,5);
        this.fSifatSM.setEditMode(editMode,this.row,6);
        this.fLampSM.setEditMode(editMode,this.row,7);
        this.fTglTerimaSM.setEditMode(editMode,this.row,8);
        this.fIdUserSM.setEditMode(editMode,this.row,9);
        this.fNamaUserSM.setEditMode(editMode,this.row,10);
        this.fTembusanSM.setEditMode(editMode,this.row,11);
        this.fTujuanSM.setEditMode(editMode,this.row,12);
        this.fKetSM.setEditMode(editMode,this.row,13);
        this.fIdImgSM.setEditMode(editMode,this.row,14);
    }
    
    
    
    
    private void addListener(){
        this.form.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(InputSM.this.editMode){
                    InputSM.this.formClosing=true;
                    InputSM.this.btnGroup.btnCancelClick();
                }else{
                    InputSM.this.form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        this.fTembusanSM.setAction(new Runnable(){
            @Override
            public void run() {
                InputSM.this.setTembusan();
            }
        });
        this.fIdImgSM.setAddAction(new Runnable(){
            @Override
            public void run() {
                //InputSM.this.imageFromBrowser();
                InputSM.this.imageFromScanner();
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
            //this.fAsalSM.setKeyword(kOpd);
        }
        
        List<String> kSS=new ArrayList();
        JMResultSet rSS=JMFunctions.getCurrentConnection().queryMySQL("select * from sifat_surat", false);
        if(rSS.first()){
            do{
                JMFunctions.trace(rSS.getString(1));
                kSS.add(rSS.getString(1));
            }while(rSS.next());
        }
        if(!kSS.isEmpty()){
            //this.fSifatSM.setKeyword(kSS);
        }
    } 
    private void refreshImages(){
        if(!this.form.isVisible() && !this.init)return;
        this.fIdImgSM.clearPaths();
        String key=this.fIdSM.getText();
        JMFunctions.trace("KEY : "+key);
        String q="select * from gambar_sm where id_sm='"+key+"' order by halaman_sm asc";
        JMResultSet r=JMFunctions.getCurrentConnection().queryMySQL(q, true);
        this.fIdImgSM.setKeyValue(key);
        if(r.first()){
            do{
                JMFunctions.trace(r.getString(2));
                this.fIdImgSM.addImage(r.getString(2));
            }while(r.next());
        }
    } 
    private void setTembusan(){
        /*if(this.editMode){
            if(this.fTembusanSM.getValueString().equals("true")){
                this.fTujuanSM.unlock();
                this.fTujuanSM.setText("");
            }else{
                this.fTujuanSM.lock();
                this.fTujuanSM.setText("Kantor Camat Kalawat");
            }
        }else{
            this.fTujuanSM.lock();
            this.fTujuanSM.setText(this.fTujuanSM.getValueString());
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
            JMFunctions.trace("++++++++++++++++++++++++++++++++++++++++     "+ret);
            this.fIdImgSM.addImage(ret);
            
            this.table.getCurrentRow().setValueFromString(14, "0");
            //JMFunctions.trace(JMFunctions.getCacheDir()+"/image.tmp");
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
            this.fIdImgSM.addImage(dest);
        }
        this.table.getCurrentRow().setValueFromString(14, "0");
    }
    
    private void saveImages(){
        List<String> paths=this.fIdImgSM.getPaths();
        String id=this.fIdSM.getText();
        String qD="delete from gambar_sm where id_sm='"+id+"'";
        String qU="replace into gambar_sm values(";
        if(!paths.isEmpty()){
            for(int i=0;i<paths.size();i++){
                String idDet=id+JMFormatCollection.leadingZero(i+1, 10);
                String url=JMFunctions.getDocDir()+"/docs/sm/"+id+"/"+(i+1)+".jpg";
                
                String validS=paths.get(i).replaceAll("\\\\", "/");
                String validD=url.replaceAll("\\\\", "/");
                
                if(!validS.equals(validD)){
                    JMFunctions.trace(validS+"                 KE               "+validD);
                    //JMFunctions.deleteFile(new File(validD));
                    JMFunctions.moveFile(new File(validS), new File(validD));
                }
                
                if(i==0)qU+="'"+idDet+"','"+id+"','"+validD+"','"+(i+1)+"')";
                else qU+=",('"+idDet+"','"+id+"','"+validD+"','"+(i+1)+"')";
            }
        }
        this.deleteImageTmps();
        JMFunctions.getCurrentConnection().queryUpdateMySQL(qD, true);
        JMFunctions.getCurrentConnection().queryUpdateMySQL(qU, true);
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
        this.setEditMode(false);
        this.row=this.table.getCurrentRow();
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterSaved(String updateQuery,boolean saved) {
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
        this.setEditMode(true);
        this.refreshImages();
        this.refreshAutocomplete();
        this.setTembusan();
    }

    @Override
    public void actionAfterPrinted(JMRow rowPrinted) {
        this.row=rowPrinted;
        this.setEditMode(false);
        this.refreshImages();
        //this.refreshAutocomplete();
        this.setTembusan();
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
                InputSM.this.form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
