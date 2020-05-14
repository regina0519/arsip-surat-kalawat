/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.abal.arsipsuratkalawat.tables.TableOPD;
import com.abal.arsipsuratkalawat.tables.TableSK;
import com.abal.arsipsuratkalawat.tables.TableSM;
import com.abal.arsipsuratkalawat.tables.TableSS;
import com.abal.arsipsuratkalawat.tables.TableUser;
import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmpcframework.JMPCFunctions;
import com.thowo.jmpcframework.component.JMPCForm;
import com.thowo.jmpcframework.component.JMPCLoadingSprite;
import com.thowo.jmpcframework.component.JMPCTable;
import com.thowo.jmpcframework.component.form.JMPCInputSpinnerWeblaf;
import com.thowo.jmpcframework.component.form.JMPCInputStringTFWeblaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Regina
 */
public class FormMain extends JMPCForm implements IFilter {
    public static final int MENU_SM=0;
    public static final int MENU_SK=1;
    public static final int MENU_OPD=2;
    public static final int MENU_SS=3;
    public static final int MENU_USER=4;
    
    
    private JMPCInputStringTFWeblaf search;
    private int currentMenu=MENU_SM;
    private FormAdvance filter;
    private boolean filtered;
    private Timer animFilter;
    private String queryFilter;
    

    /**h
     * Creates new form FormMain
     */
    public FormMain() {
        initComponents();
        super.setContent(this.jPanelMain, new JMPCLoadingSprite());
        this.initModule();
        Global.setActiveYear(new JMDate().getYearFull());
        this.jSpinner1.setValue(Global.getActiveYear());
        
        
        this.jPanel10.setLayout(new BorderLayout());
        this.search=JMPCInputStringTFWeblaf.create("", R.label("PROMPT_SEARCH"), 15, 20, true);
        this.jPanel10.add(this.search,BorderLayout.EAST);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.jSpinner1, "#");
        this.jSpinner1.setEditor(editor);
        JMDate now=new JMDate();
        this.jSpinner1.setValue(Integer.valueOf(now.getYearFull()));
        
        super.toggleFullscreen(true);
        this.initAccess();
        this.gotoMenu(this.currentMenu);
        this.addListener();
        this.setOnFiltered(false);
        
    }
    
    public void setOnFiltered(boolean filtered){
        JMFunctions.trace(filtered+"");
        this.jLabel2.setVisible(!filtered);
        this.jSpinner1.setVisible(!filtered);
        if(this.animFilter!=null)this.animFilter.stop();
        if(filtered){
            this.animFilter=new Timer(300,new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    FormMain.this.jLabel3.setVisible(!FormMain.this.jLabel3.isVisible());
                }
            });
            this.animFilter.start();
        }else{
            this.animFilter=null;
        }
        this.jLabel3.setVisible(filtered);
        
    }
    
    
    private void setActive(int menu){
        JPanel me=null;
        JLabel txt=null;
        JLabel icon=null;
        String img="";
        if(menu==FormMain.MENU_SM){
            me=this.menuSM;
            txt=this.lblSM;
            icon=this.iconSM;
            img="/img/sm_s.png";
            if(this.currentMenu!=FormMain.MENU_USER && this.currentMenu!=FormMain.MENU_OPD && this.currentMenu!=FormMain.MENU_SS)this.setInactive(-1);
        }else if(menu==FormMain.MENU_SK){
            me=this.menuSK;
            txt=this.lblSK;
            icon=this.iconSK;
            img="/img/sk_s.png";
            if(this.currentMenu!=FormMain.MENU_USER && this.currentMenu!=FormMain.MENU_OPD && this.currentMenu!=FormMain.MENU_SS)this.setInactive(-1);
        }else if(menu==FormMain.MENU_OPD){
            me=this.menuOPD;
            txt=this.lblOPD;
            icon=this.iconOPD;
            img="/img/opd_s.png";
            this.setActive(-1);
        }else if(menu==FormMain.MENU_SS){
            me=this.menuSifat;
            txt=this.lblSifat;
            icon=this.iconSifat;
            img="/img/sifat_s.png";
            this.setActive(-1);
        }else if(menu==FormMain.MENU_USER){
            me=this.menuUser;
            txt=this.lblUser;
            icon=this.iconUser;
            img="/img/users_s.png";
            this.setActive(-1);
        }else{
            me=this.menuSetting;
            txt=this.lblSetting;
            icon=this.iconSetting;
            img="/img/setting_s.png";
            this.showSubMenuSetting(true);
        }
        me.setBackground(Color.decode("#EEEEEE"));
        txt.setForeground(Color.decode("#234E79"));
        icon.setIcon(new ImageIcon(this.getClass().getResource(img)));
    }
    private void setInactive(int menu){
        JPanel me=null;
        JLabel txt=null;
        JLabel icon=null;
        String img="";
        if(menu==FormMain.MENU_SM){
            me=this.menuSM;
            txt=this.lblSM;
            icon=this.iconSM;
            img="/img/sm.png";
        }else if(menu==FormMain.MENU_SK){
            me=this.menuSK;
            txt=this.lblSK;
            icon=this.iconSK;
            img="/img/sk.png";
        }else if(menu==FormMain.MENU_OPD){
            me=this.menuOPD;
            txt=this.lblOPD;
            icon=this.iconOPD;
            img="/img/opd.png";
        }else if(menu==FormMain.MENU_SS){
            me=this.menuSifat;
            txt=this.lblSifat;
            icon=this.iconSifat;
            img="/img/sifat.png";
        }else if(menu==FormMain.MENU_USER){
            me=this.menuUser;
            txt=this.lblUser;
            icon=this.iconUser;
            img="/img/users.png";
        }else{
            me=this.menuSetting;
            txt=this.lblSetting;
            icon=this.iconSetting;
            img="/img/setting.png";
            this.showSubMenuSetting(false);
        }
        me.setBackground(Color.decode("#234E79"));
        txt.setForeground(Color.decode("#EEEEEE"));
        icon.setIcon(new ImageIcon(this.getClass().getResource(img)));
    }
    private void showSubMenuSetting(boolean show){
        this.subMenuSetting.setVisible(show && Global.getAdmin());
        //this.menuOPD.setVisible(show && Global.getAdmin());
        //this.menuSifat.setVisible(show && Global.getAdmin());
        //this.menuUser.setVisible(show && Global.getAdmin());
    }
    
    private void addListener(){
        this.jSpinner1.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(FormMain.this.currentMenu==FormMain.MENU_SM)FormMain.this.gotoMenu(MENU_SM);
                else if(FormMain.this.currentMenu==FormMain.MENU_SK)FormMain.this.gotoMenu(MENU_SK);
            }
        });
        
        //MENU====================================
        this.menuSM.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                FormMain.this.gotoMenu(FormMain.MENU_SM);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(FormMain.MENU_SM);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_SM)FormMain.this.setInactive(FormMain.MENU_SM);
            }
        });
        this.menuSK.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                FormMain.this.gotoMenu(FormMain.MENU_SK);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(FormMain.MENU_SK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_SK)FormMain.this.setInactive(FormMain.MENU_SK);
            }
        });
        this.menuSifat.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                FormMain.this.gotoMenu(FormMain.MENU_SS);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(FormMain.MENU_SS);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_SS)FormMain.this.setInactive(FormMain.MENU_SS);
            }
        });
        this.menuUser.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                FormMain.this.gotoMenu(FormMain.MENU_USER);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(FormMain.MENU_USER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_USER)FormMain.this.setInactive(FormMain.MENU_USER);
            }
        });
        this.menuOPD.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                FormMain.this.gotoMenu(FormMain.MENU_OPD);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(FormMain.MENU_OPD);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_OPD)FormMain.this.setInactive(FormMain.MENU_OPD);
            }
        });
        this.menuSetting.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //FormMain.this.showSubMenuSetting(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FormMain.this.setActive(-1);
                FormMain.this.showSubMenuSetting(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(FormMain.this.currentMenu!=FormMain.MENU_USER && FormMain.this.currentMenu!=FormMain.MENU_OPD && FormMain.this.currentMenu!=FormMain.MENU_SS){
                    //FormMain.this.setInactive(-1);
                    //FormMain.this.showSubMenuSetting(false);
                }
            }
        });
        
    }
    
    public void gotoMenu(int menu){
        this.setInactive(this.currentMenu);
        this.jButton6.setVisible(false);
        this.filtered=false;
        this.search.setText("");
        if(menu==FormMain.MENU_SM){
            this.gotoSM();
            this.currentMenu=FormMain.MENU_SM;
            this.jButton6.setVisible(true);
        }else if(menu==FormMain.MENU_SK){
            this.gotoSK();
            this.currentMenu=FormMain.MENU_SK;
            this.jButton6.setVisible(true);
        }else if(menu==FormMain.MENU_OPD){
            this.gotoOPD();
            this.currentMenu=FormMain.MENU_OPD;
        }else if(menu==FormMain.MENU_SS){
            this.gotoSS();
            this.currentMenu=FormMain.MENU_SS;
        }else if(menu==FormMain.MENU_USER){
            this.gotoUSER();
            this.currentMenu=FormMain.MENU_USER;
        }
        this.setActive(this.currentMenu);
    }
    
    private void gotoSM(){
        this.jLabel1.setText("Agenda Surat Masuk");
        String q="SELECT \n" +
            "surat_masuk.id_sm AS id_sm,\n" +
            "surat_masuk.no_agenda AS no_agenda,\n" +
            "surat_masuk.no_sm AS no_sm,\n" +
            "surat_masuk.tgl_sm AS tgl,\n" +
            "surat_masuk.asal_sm AS asal_sm,\n" +
            "surat_masuk.perihal_sm AS perihal_sm,\n" +
            "surat_masuk.sifat_sm AS sifat_sm,\n" +
            "surat_masuk.lampiran_sm AS lampiran_sm,\n" +
            "surat_masuk.tgl_terima AS tgl_terima,\n" +
            "surat_masuk.id_user AS id_user,\n" +
            "user.nama_user AS nama_user,\n" +
            "surat_masuk.tembusan_sm AS tembusan_sm,\n" +
            "surat_masuk.tujuan_sm AS tujuan_sm,\n" +
            "surat_masuk.ket_sm AS ket_sm,\n" +
            "surat_masuk.id_sm AS id_img\n" +
            "FROM surat_masuk,user\n" +
            "WHERE surat_masuk.id_user=user.id_user\n" +
            " and YEAR(surat_masuk.tgl_terima)='"+this.jSpinner1.getValue()+"'\n" +
            "ORDER BY tgl_terima desc";
        
        TableSM smTbl=TableSM.create(q, FormMain.this);
        this.search.setAction(smTbl.filter(this.search));
        
    }
    private void gotoSK(){
        this.jLabel1.setText("Agenda Surat Keluar");
        String q="SELECT \n" +
            "surat_keluar.id_sk AS id_sk,\n" +
            "surat_keluar.no_agenda AS no_agenda,\n" +
            "surat_keluar.no_sk AS no_sk,\n" +
            "surat_keluar.tgl_sk AS tgl,\n" +
            "surat_keluar.perihal_sk AS perihal_sk,\n" +
            "surat_keluar.sifat_sk AS sifat_sk,\n" +
            "surat_keluar.lampiran_sk AS lampiran_sk,\n" +
            "surat_keluar.tgl_keluar AS tgl_keluar,\n" +
            "surat_keluar.id_user AS id_user,\n" +
            "user.nama_user AS nama_user,\n" +
            "surat_keluar.tujuan_sk AS tujuan_sk,\n" +
            "surat_keluar.ket_sk AS ket_sk,\n" +
            "surat_keluar.id_sk AS id_img\n" +
            "FROM surat_keluar,user\n" +
            "WHERE surat_keluar.id_user=user.id_user\n" +
            " and YEAR(surat_keluar.tgl_keluar)='"+this.jSpinner1.getValue()+"'\n" +
            "ORDER BY tgl_keluar desc";
        TableSK skTbl=TableSK.create(q, FormMain.this);
        this.search.setAction(skTbl.filter(this.search));
        
    }
    private void gotoOPD(){
        if(!Global.getAdmin())return;
        TableOPD opdTbl=TableOPD.create("select * from opd", FormMain.this);
        this.search.setAction(opdTbl.filter(this.search));
        this.jLabel1.setText("Pengaturan OPD");
    }
    private void gotoSS(){
        if(!Global.getAdmin())return;
        TableSS ssTbl=TableSS.create("select * from sifat_surat", FormMain.this);
        this.search.setAction(ssTbl.filter(this.search));
        this.jLabel1.setText("Pengaturan Sifat Surat");
    }
    private void gotoUSER(){
        if(!Global.getAdmin())return;
        TableUser userTbl=TableUser.create("select * from user", FormMain.this);
        this.search.setAction(userTbl.filter(this.search));
        
        this.jLabel1.setText("Pengaturan Pengguna");
    }
    
    private void initAccess(){
        //this.jButton3.setEnabled(Global.getAdmin());
        //this.jButton4.setEnabled(Global.getAdmin());        
        //this.jButton5.setEnabled(Global.getAdmin());        
    }
    
    public int getYear(){
        return (int)this.jSpinner1.getValue();
    }
    
    public JMPCInputStringTFWeblaf getSearch(){
        return this.search;
    }
    
    public void setSearch(String search){
        
        this.search.setText(search);
    }
    
    
    public JPanel getPanelTable(){
        return this.jPanel5;
    }
    public JPanel getPanelButtons(){
        return this.jPanel6;
    }
    
    public void initModule(){
        TableTes tesTbl=TableTes.create("select * from tes", FormMain.this);
        FormMain.this.pack();
        
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
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        menuSM = new javax.swing.JPanel();
        iconSM = new javax.swing.JLabel();
        lblSM = new javax.swing.JLabel();
        menuSK = new javax.swing.JPanel();
        iconSK = new javax.swing.JLabel();
        lblSK = new javax.swing.JLabel();
        menuSetting = new javax.swing.JPanel();
        iconSetting = new javax.swing.JLabel();
        lblSetting = new javax.swing.JLabel();
        subMenuSetting = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        menuUser = new javax.swing.JPanel();
        iconUser = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        menuSifat = new javax.swing.JPanel();
        iconSifat = new javax.swing.JLabel();
        lblSifat = new javax.swing.JLabel();
        menuOPD = new javax.swing.JPanel();
        iconOPD = new javax.swing.JLabel();
        lblOPD = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jFilterMsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanelMain.setBackground(new java.awt.Color(35, 78, 121));

        jPanel1.setBackground(new java.awt.Color(35, 78, 121));

        jPanel12.setOpaque(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/main.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setOpaque(false);

        menuSM.setBackground(new java.awt.Color(35, 78, 121));

        iconSM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sm.png"))); // NOI18N

        lblSM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSM.setForeground(new java.awt.Color(204, 204, 204));
        lblSM.setText("Surat Masuk             ");

        javax.swing.GroupLayout menuSMLayout = new javax.swing.GroupLayout(menuSM);
        menuSM.setLayout(menuSMLayout);
        menuSMLayout.setHorizontalGroup(
            menuSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconSM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuSMLayout.setVerticalGroup(
            menuSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconSM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuSK.setBackground(new java.awt.Color(35, 78, 121));

        iconSK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sk.png"))); // NOI18N

        lblSK.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSK.setForeground(new java.awt.Color(204, 204, 204));
        lblSK.setText("Surat Keluar");

        javax.swing.GroupLayout menuSKLayout = new javax.swing.GroupLayout(menuSK);
        menuSK.setLayout(menuSKLayout);
        menuSKLayout.setHorizontalGroup(
            menuSKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconSK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuSKLayout.setVerticalGroup(
            menuSKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSKLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconSK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuSetting.setBackground(new java.awt.Color(35, 78, 121));

        iconSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N

        lblSetting.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetting.setForeground(new java.awt.Color(204, 204, 204));
        lblSetting.setText("Pengaturan");

        javax.swing.GroupLayout menuSettingLayout = new javax.swing.GroupLayout(menuSetting);
        menuSetting.setLayout(menuSettingLayout);
        menuSettingLayout.setHorizontalGroup(
            menuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconSetting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuSettingLayout.setVerticalGroup(
            menuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        subMenuSetting.setBackground(new java.awt.Color(35, 78, 121));

        jPanel23.setBackground(new java.awt.Color(35, 78, 121));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(35, 78, 121));

        menuUser.setBackground(new java.awt.Color(35, 78, 121));

        iconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N

        lblUser.setForeground(new java.awt.Color(204, 204, 204));
        lblUser.setText("Pengguna");

        javax.swing.GroupLayout menuUserLayout = new javax.swing.GroupLayout(menuUser);
        menuUser.setLayout(menuUserLayout);
        menuUserLayout.setHorizontalGroup(
            menuUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuUserLayout.setVerticalGroup(
            menuUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuSifat.setBackground(new java.awt.Color(35, 78, 121));

        iconSifat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sifat.png"))); // NOI18N

        lblSifat.setForeground(new java.awt.Color(204, 204, 204));
        lblSifat.setText("Sifat Surat");

        javax.swing.GroupLayout menuSifatLayout = new javax.swing.GroupLayout(menuSifat);
        menuSifat.setLayout(menuSifatLayout);
        menuSifatLayout.setHorizontalGroup(
            menuSifatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSifatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconSifat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSifat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuSifatLayout.setVerticalGroup(
            menuSifatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuSifatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSifatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconSifat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSifat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuOPD.setBackground(new java.awt.Color(35, 78, 121));

        iconOPD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/opd.png"))); // NOI18N

        lblOPD.setForeground(new java.awt.Color(204, 204, 204));
        lblOPD.setText("OPD");

        javax.swing.GroupLayout menuOPDLayout = new javax.swing.GroupLayout(menuOPD);
        menuOPD.setLayout(menuOPDLayout);
        menuOPDLayout.setHorizontalGroup(
            menuOPDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOPDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconOPD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOPD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuOPDLayout.setVerticalGroup(
            menuOPDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuOPDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOPDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconOPD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblOPD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuSifat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuOPD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(menuUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuSifat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuOPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout subMenuSettingLayout = new javax.swing.GroupLayout(subMenuSetting);
        subMenuSetting.setLayout(subMenuSettingLayout);
        subMenuSettingLayout.setHorizontalGroup(
            subMenuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subMenuSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        subMenuSettingLayout.setVerticalGroup(
            subMenuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subMenuSettingLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(subMenuSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuSM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuSK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subMenuSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuSK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menuSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subMenuSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(35, 78, 121));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(238, 238, 238));
        jPanel3.setForeground(new java.awt.Color(204, 204, 204));

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setOpaque(false);

        jPanel7.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Title");

        jPanel9.setOpaque(false);

        jLabel2.setText("Tahun");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(2020, 2020, 9999, 1));
        jSpinner1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSpinner1MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 14, 32));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/warning_small.png"))); // NOI18N
        jLabel3.setText("mode pencarian sedang aktif");
        jLabel3.setIconTextGap(10);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanel15.setOpaque(false);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSpinner1)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setOpaque(false);

        jPanel10.setOpaque(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        jPanel11.setOpaque(false);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/advance_search_small.png"))); // NOI18N
        jButton6.setText("Pencarian Lanjutan");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setOpaque(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setOpaque(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel14.setOpaque(false);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFilterMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFilterMsg)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(this.currentMenu==FormMain.MENU_SM){
            if(this.filter==null)this.filter=FormAdvance.create(this, true).formMain(this).mode(FormAdvance.MODE_SURAT_MASUK);
            this.filter.setVisible(true);
        }else if(this.currentMenu==FormMain.MENU_SK){
            if(this.filter==null)this.filter=FormAdvance.create(this, true).formMain(this).mode(FormAdvance.MODE_SURAT_KELUAR);
            this.filter.setVisible(true);
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jSpinner1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSpinner1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jSpinner1MouseClicked

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
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconOPD;
    private javax.swing.JLabel iconSK;
    private javax.swing.JLabel iconSM;
    private javax.swing.JLabel iconSetting;
    private javax.swing.JLabel iconSifat;
    private javax.swing.JLabel iconUser;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jFilterMsg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel lblOPD;
    private javax.swing.JLabel lblSK;
    private javax.swing.JLabel lblSM;
    private javax.swing.JLabel lblSetting;
    private javax.swing.JLabel lblSifat;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel menuOPD;
    private javax.swing.JPanel menuSK;
    private javax.swing.JPanel menuSM;
    private javax.swing.JPanel menuSetting;
    private javax.swing.JPanel menuSifat;
    private javax.swing.JPanel menuUser;
    private javax.swing.JPanel subMenuSetting;
    // End of variables declaration//GEN-END:variables


    @Override
    public void filterSK(JMDate tglSuratFrom, JMDate tglSuratTo, JMDate tglKirimFrom, JMDate tglKirimTo) {
        this.setOnFiltered(!(tglSuratFrom==null && tglSuratTo==null && tglKirimFrom==null && tglKirimTo==null));
    }

    @Override
    public void filterSM(JMDate tglSuratFrom, JMDate tglSuratTo, JMDate tglTerimaFrom, JMDate tglTerimaTo, int tembusanMode) {
        boolean onFiltered=!(tglSuratFrom==null && tglSuratTo==null && tglTerimaFrom==null && tglTerimaTo==null && tembusanMode==FormAdvance.TEMBUSAN_NULL);
        this.setOnFiltered(onFiltered);
        if(!onFiltered)this.gotoMenu(MENU_SM);
        this.search.setText("");
        
        String fltrTglSuratQ=null;
        String fltrTglTerimaQ=null;
        String fltrTembQ=null;
        
        if(tglSuratFrom!=null && tglSuratTo!=null)fltrTglSuratQ="(surat_masuk.tgl_sm>='"+tglSuratFrom.dateDB()+"' and surat_masuk.tgl_sm<='"+tglSuratTo.dateDB()+"')";
        if(tglTerimaFrom!=null && tglTerimaTo!=null)fltrTglTerimaQ="(surat_masuk.tgl_terima>='"+tglTerimaFrom.dateDB()+"' and surat_masuk.tgl_terima<='"+tglTerimaTo.dateDB()+"')";
        if(tembusanMode==FormAdvance.TEMBUSAN_TRUE)fltrTembQ="(surat_masuk.tembusan_sm='1')";
        else if(tembusanMode==FormAdvance.TEMBUSAN_FALSE)fltrTembQ="(surat_masuk.tembusan_sm='0')";
        
        String qAdd="";
        if(fltrTglSuratQ!=null)qAdd=fltrTglSuratQ;
        if(fltrTglTerimaQ!=null){
            if(qAdd.equals(""))qAdd=fltrTglTerimaQ;
            else qAdd+=" and "+fltrTglTerimaQ;
        }
        if(fltrTembQ!=null){
            if(qAdd.equals(""))qAdd=fltrTembQ;
            else qAdd+=" and "+fltrTembQ;
        }
        
        this.queryFilter="SELECT \n" +
            "surat_masuk.id_sm AS id_sm,\n" +
            "surat_masuk.no_agenda AS no_agenda,\n" +
            "surat_masuk.no_sm AS no_sm,\n" +
            "surat_masuk.tgl_sm AS tgl,\n" +
            "surat_masuk.asal_sm AS asal_sm,\n" +
            "surat_masuk.perihal_sm AS perihal_sm,\n" +
            "surat_masuk.sifat_sm AS sifat_sm,\n" +
            "surat_masuk.lampiran_sm AS lampiran_sm,\n" +
            "surat_masuk.tgl_terima AS tgl_terima,\n" +
            "surat_masuk.id_user AS id_user,\n" +
            "user.nama_user AS nama_user,\n" +
            "surat_masuk.tembusan_sm AS tembusan_sm,\n" +
            "surat_masuk.tujuan_sm AS tujuan_sm,\n" +
            "surat_masuk.ket_sm AS ket_sm,\n" +
            "surat_masuk.id_sm AS id_img\n" +
            "FROM surat_masuk,user\n" +
            "WHERE (surat_masuk.id_user=user.id_user)\n";
        
        if(!qAdd.equals("")) this.queryFilter+=" and "+qAdd+" ORDER BY tgl_terima desc";
        
        JMFunctions.trace(this.queryFilter);
        
        
        TableSM smTbl=TableSM.create(this.queryFilter, FormMain.this);
        //this.search.setAction(smTbl.filter(this.search));
        this.jLabel1.setText("Agenda Surat Masuk");
        
        
        this.currentMenu=FormMain.MENU_SM;
        
    }
}
