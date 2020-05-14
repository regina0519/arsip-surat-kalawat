/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmpcframework.component.JMPCFormModal;
import com.thowo.jmpcframework.component.JMPCLoadingSprite;
import com.thowo.jmpcframework.component.JMPCWebSwitch;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 *
 * @author Regina
 */
public class FormAdvance extends JMPCFormModal {
    public static final int MODE_SURAT_MASUK=0;
    public static final int MODE_SURAT_KELUAR=1;
    
    public static final int TEMBUSAN_NULL=0;
    public static final int TEMBUSAN_TRUE=1;
    public static final int TEMBUSAN_FALSE=2;
    
    private JMPCWebSwitch tembusan;
    private JMPCWebSwitch onFilter;
    private JMPCWebSwitch cbTglSurat;
    private JMPCWebSwitch cbTglAkses;
    private JMPCWebSwitch cbAktifTemb;
    private int mode=MODE_SURAT_MASUK;
    private IFilter f;
    
    private Date tgl1;
    private Date tgl2;
    private Date tgl3;
    private Date tgl4;
    private boolean temb;
    private boolean active1;
    private boolean active2;
    private boolean active3;
    private boolean on;
    private boolean commit=false;

    /**
     * Creates new form FormAdvance
     */
    
    public static FormAdvance create(java.awt.Frame parent, boolean modal){
        return new FormAdvance(parent,modal);
    }
    
    public FormAdvance(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        super.setContent(this.jPanelMain, new JMPCLoadingSprite());
        this.init();
        
    }
    public FormAdvance mode(int mode){
        this.mode=mode;
        if(mode==FormAdvance.MODE_SURAT_MASUK)this.jLabel1.setText(R.label("ADV_SEARCH_SM"));
        else this.jLabel1.setText(R.label("ADV_SEARCH_SK"));
        return this;
    }
    public FormAdvance formMain(FormMain formMain){
        this.f=formMain;
        return this;
    }
    
    private void init(){
        JMFunctions.trace("init");
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(this.spTglSrt0, "dd-MMM-yyyy");
        this.spTglSrt0.setEditor(editor1);
        try {
            this.spTglSrt0.setValue(new JMDate(Global.getActiveYear()+"-01-01").getDate());
        } catch (ParseException ex) {
            Logger.getLogger(FormAdvance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(this.spTglSrt1, "dd-MMM-yyyy");
        this.spTglSrt1.setEditor(editor2);
        try {
            this.spTglSrt1.setValue(new JMDate(Global.getActiveYear()+"-12-31").getDate());
        } catch (ParseException ex) {
            Logger.getLogger(FormAdvance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSpinner.DateEditor editor3 = new JSpinner.DateEditor(this.spTglAkses0, "dd-MMM-yyyy");
        this.spTglAkses0.setEditor(editor3);
        try {
            this.spTglAkses0.setValue(new JMDate(Global.getActiveYear()+"-01-01").getDate());
        } catch (ParseException ex) {
            Logger.getLogger(FormAdvance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSpinner.DateEditor editor4 = new JSpinner.DateEditor(this.spTglAkses1, "dd-MMM-yyyy");
        this.spTglAkses1.setEditor(editor4);
        try {
            this.spTglAkses1.setValue(new JMDate(Global.getActiveYear()+"-12-31").getDate());
        } catch (ParseException ex) {
            Logger.getLogger(FormAdvance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.pnlTemb.setLayout(new BorderLayout());
        this.tembusan=JMPCWebSwitch.create(R.label("TEMBUSAN_TRUE"), R.label("TEMBUSAN_FALSE"));
        this.pnlTemb.add(this.tembusan,BorderLayout.EAST);
        this.tembusan.setSelected(false);
        this.tembusan.setAnimate(false);
        
        this.pnlTglSrt.setLayout(new BorderLayout());
        this.cbTglSurat=JMPCWebSwitch.create(R.label("ON"), R.label("OFF"));
        this.pnlTglSrt.add(this.cbTglSurat,BorderLayout.WEST);
        this.cbTglSurat.setSelected(false);
        this.cbTglSurat.setAnimate(false);
        this.cbTglSurat.setLocked(true);
        
        this.pnlTglAkses.setLayout(new BorderLayout());
        this.cbTglAkses=JMPCWebSwitch.create(R.label("ON"), R.label("OFF"));
        this.pnlTglAkses.add(this.cbTglAkses,BorderLayout.WEST);
        this.cbTglAkses.setSelected(false);
        this.cbTglAkses.setAnimate(false);
        this.cbTglAkses.setLocked(true);
        
        this.pnlAktifTemb.setLayout(new BorderLayout());
        this.cbAktifTemb=JMPCWebSwitch.create(R.label("ON"), R.label("OFF"));
        this.pnlAktifTemb.add(this.cbAktifTemb,BorderLayout.WEST);
        this.cbAktifTemb.setSelected(false);
        this.cbAktifTemb.setAnimate(false);
        this.cbAktifTemb.setLocked(true);
        
        
        this.jPanel7.setLayout(new BorderLayout());
        this.onFilter=JMPCWebSwitch.create(R.label("ON"), R.label("OFF"));
        this.jPanel7.add(this.onFilter,BorderLayout.EAST);
        this.onFilter.setSelected(false);
        this.onFilter.setAnimate(false);
        
        
        this.setFilterTglSurat(false);
        this.setFilterTglAkses(false);
        this.setFilterTembusan(false);
        
        this.setListeners();
    }
    private void setListeners(){
        this.cbTglSurat.setAction(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FormAdvance.this.setFilterTglSurat(FormAdvance.this.cbTglSurat.isSelected());
            }
        });
        this.cbTglAkses.setAction(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FormAdvance.this.setFilterTglAkses(FormAdvance.this.cbTglAkses.isSelected());
            }
        });
        this.cbAktifTemb.setAction(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FormAdvance.this.setFilterTembusan(FormAdvance.this.cbAktifTemb.isSelected());
            }
        });
        this.onFilter.setOnClicked(new Runnable(){
            @Override
            public void run() {
                //FormAdvance.this.on=FormAdvance.this.onFilter.isSelected();
                if(!FormAdvance.this.onFilter.isSelected()){
                    FormAdvance.this.setFilterTglSurat(false);
                    FormAdvance.this.setFilterTglAkses(false);
                    FormAdvance.this.setFilterTembusan(false);
                }else{
                    FormAdvance.this.setFilterTglSurat(true);
                    FormAdvance.this.setFilterTglAkses(true);
                    FormAdvance.this.setFilterTembusan(true);
                }
                FormAdvance.this.cbTglSurat.setLocked(!FormAdvance.this.onFilter.isSelected());
                FormAdvance.this.cbTglAkses.setLocked(!FormAdvance.this.onFilter.isSelected());
                FormAdvance.this.cbAktifTemb.setLocked(!FormAdvance.this.onFilter.isSelected());
            }
        });
        this.onFilter.setAction(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FormAdvance.this.cbTglSurat.setLocked(!FormAdvance.this.onFilter.isSelected());
                FormAdvance.this.cbTglAkses.setLocked(!FormAdvance.this.onFilter.isSelected());
                FormAdvance.this.cbAktifTemb.setLocked(!FormAdvance.this.onFilter.isSelected());
            }
        });
        this.addComponentListener(new ComponentListener(){
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                FormAdvance.this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                FormAdvance.this.backUp();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                if(FormAdvance.this.commit){
                    JMFunctions.trace("COMMIT");
                    if(!FormAdvance.this.onFilter.isSelected()){
                        JMFunctions.trace("COMMIT TIDAK ON");
                        FormAdvance.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }else{
                        FormAdvance.this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                    }
                }else{
                    JMFunctions.trace("TIDAK COMMIT");
                    FormAdvance.this.restore();
                    FormAdvance.this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                }
                //FormAdvance.this.restore();
                
                
                //if(!FormAdvance.this.commit)FormAdvance.this.restore();
                //if(!FormAdvance.this.on)FormAdvance.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
    }
    
    private void backUp(){
        
        this.tgl1=(Date) this.spTglSrt0.getValue();
        this.tgl2=(Date) this.spTglSrt1.getValue();
        this.tgl3=(Date) this.spTglAkses0.getValue();
        this.tgl4=(Date) this.spTglAkses1.getValue();
        this.temb=this.tembusan.isSelected();
        this.active1=this.cbTglSurat.isSelected();
        this.active2=this.cbTglAkses.isSelected();
        this.active3=this.cbAktifTemb.isSelected();
        this.on=this.onFilter.isSelected();
        this.commit=false;
        JMFunctions.trace("BACKED UP : "+this.active2);
    }
    private void restore(){
        JMFunctions.trace("RESTORE A: "+this.active2);
        this.spTglSrt0.setValue(this.tgl1);
        this.spTglSrt1.setValue(this.tgl2);
        this.spTglAkses0.setValue(this.tgl3);
        this.spTglAkses1.setValue(this.tgl4);
        this.tembusan.setSelected(this.temb);
        this.cbTglSurat.setSelected(this.active1);
        this.cbTglAkses.setSelected(this.active2);
        this.cbTglAkses.validate();
        this.cbAktifTemb.setSelected(this.active3);
        this.onFilter.setSelected(this.on);
        this.doLayout();
        JMFunctions.trace("RESTORE B: "+this.active2);
    }
    public void setFilterTglSurat(boolean filtered){
        if(this.cbTglSurat.isSelected()!=filtered)this.cbTglSurat.setSelected(filtered);
        this.spTglSrt0.setEnabled(filtered);
        this.spTglSrt1.setEnabled(filtered);
    }
    public void setFilterTglAkses(boolean filtered){
        if(this.cbTglAkses.isSelected()!=filtered)this.cbTglAkses.setSelected(filtered);
        this.spTglAkses0.setEnabled(filtered);
        this.spTglAkses1.setEnabled(filtered);
    }
    public void setFilterTembusan(boolean filtered){
        if(this.cbAktifTemb.isSelected()!=filtered)this.cbAktifTemb.setSelected(filtered);
        this.tembusan.setLocked(!filtered);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        pnlTglSrt = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        spTglSrt1 = new javax.swing.JSpinner();
        spTglSrt0 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        pnlTglAkses = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        spTglAkses1 = new javax.swing.JSpinner();
        spTglAkses0 = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        pnlAktifTemb = new javax.swing.JPanel();
        pnlTemb = new javax.swing.JPanel();

        setUndecorated(true);

        jPanelMain.setBackground(new java.awt.Color(35, 78, 121));
        jPanelMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(238, 238, 238));
        jPanel1.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(238, 238, 238));
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(238, 238, 238));
        jLabel1.setText("Pencarian Lanjutan Surat Masuk");

        jPanel7.setOpaque(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 219, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(238, 238, 238));
        jPanel5.setOpaque(false);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(238, 238, 238));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnlTglSrt.setOpaque(false);

        javax.swing.GroupLayout pnlTglSrtLayout = new javax.swing.GroupLayout(pnlTglSrt);
        pnlTglSrt.setLayout(pnlTglSrtLayout);
        pnlTglSrtLayout.setHorizontalGroup(
            pnlTglSrtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        pnlTglSrtLayout.setVerticalGroup(
            pnlTglSrtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jPanel10.setOpaque(false);

        jLabel9.setText("Sampai");

        spTglSrt1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        spTglSrt0.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jLabel10.setText("Dari");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTglSrt0, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTglSrt1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spTglSrt0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(spTglSrt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTglSrt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTglSrt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(238, 238, 238));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnlTglAkses.setOpaque(false);

        javax.swing.GroupLayout pnlTglAksesLayout = new javax.swing.GroupLayout(pnlTglAkses);
        pnlTglAkses.setLayout(pnlTglAksesLayout);
        pnlTglAksesLayout.setHorizontalGroup(
            pnlTglAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        pnlTglAksesLayout.setVerticalGroup(
            pnlTglAksesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jPanel13.setOpaque(false);

        jLabel11.setText("Sampai");

        spTglAkses1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        spTglAkses0.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jLabel12.setText("Dari");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTglAkses0, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTglAkses1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(spTglAkses0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(spTglAkses1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTglAkses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTglAkses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(238, 238, 238));
        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnlAktifTemb.setOpaque(false);

        javax.swing.GroupLayout pnlAktifTembLayout = new javax.swing.GroupLayout(pnlAktifTemb);
        pnlAktifTemb.setLayout(pnlAktifTembLayout);
        pnlAktifTembLayout.setHorizontalGroup(
            pnlAktifTembLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        pnlAktifTembLayout.setVerticalGroup(
            pnlAktifTembLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        pnlTemb.setOpaque(false);

        javax.swing.GroupLayout pnlTembLayout = new javax.swing.GroupLayout(pnlTemb);
        pnlTemb.setLayout(pnlTembLayout);
        pnlTembLayout.setHorizontalGroup(
            pnlTembLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        pnlTembLayout.setVerticalGroup(
            pnlTembLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAktifTemb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTemb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAktifTemb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTemb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(this.f==null)return;
        JMDate tgl1=null;
        JMDate tgl2=null;
        JMDate tgl3=null;
        JMDate tgl4=null;
        int tembMode=0;
        if(this.cbTglSurat.isSelected()){
            tgl1=new JMDate((Date) this.spTglSrt0.getValue());
            tgl2=new JMDate((Date) this.spTglSrt1.getValue());
        }
        if(this.cbTglAkses.isSelected()){
            tgl3=new JMDate((Date) this.spTglAkses0.getValue());
            tgl4=new JMDate((Date) this.spTglAkses1.getValue());
        }
        if(this.mode==FormAdvance.MODE_SURAT_MASUK){
            if(this.cbAktifTemb.isSelected()){
                if(this.tembusan.isSelected())tembMode=FormAdvance.TEMBUSAN_TRUE;
                else tembMode=FormAdvance.TEMBUSAN_FALSE;
            }else tembMode=FormAdvance.TEMBUSAN_NULL;
            this.f.filterSM(tgl1, tgl2, tgl3, tgl4, tembMode);
        }else this.f.filterSK(tgl1, tgl2, tgl3, tgl4);
        this.commit=true;
        this.close();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormAdvance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdvance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdvance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdvance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormAdvance dialog = new FormAdvance(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel pnlAktifTemb;
    private javax.swing.JPanel pnlTemb;
    private javax.swing.JPanel pnlTglAkses;
    private javax.swing.JPanel pnlTglSrt;
    private javax.swing.JSpinner spTglAkses0;
    private javax.swing.JSpinner spTglAkses1;
    private javax.swing.JSpinner spTglSrt0;
    private javax.swing.JSpinner spTglSrt1;
    // End of variables declaration//GEN-END:variables
}
