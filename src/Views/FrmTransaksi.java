/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Factory.Factory;
import Intefaces.IPaketLaundry;
import Intefaces.IPelanggan;
import Intefaces.ITransaksi;
import Models.DetailTransaksi;
import Models.PaketLaundry;
import Models.Pelanggan;
import Models.Transaksi;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author su
 */
public class FrmTransaksi extends javax.swing.JInternalFrame {
    private Factory factory ;
    private ITransaksi transaksiDAO;
    private IPaketLaundry paketDAO;
    private IPelanggan pelangganDAO;
    private ArrayList<Transaksi> listTransaksi;
    private ArrayList<PaketLaundry> listPaket;
    private ArrayList<Pelanggan> listPelanggan;
    private ArrayList<DetailTransaksi> listDetail=new ArrayList<>();
    private Transaksi transaksi;
    private Pelanggan pelanggan;
    private double totalHarga;
    private DefaultTableModel dtmTransaksi,dtmDetail,dtmPaket,dtmPelanggan;
    /**
     * Creates new form FrmTransaksi
     * @param parent
     */
    public FrmTransaksi(JFrame parent) {
        initComponents();
        factory = ((FrmMain)parent).main;
        btnCheckout.setEnabled(false);
        initTable();
        refreshTableTransaksi();
    }
    
    private void initTable(){
        //table transaksi
        tblTransaksi.setModel(new DefaultTableModel(null,
                new String[]{
                    "Tanggal",
                    "Nama Pelanggan",
                    "Total Bayar"
                }));
        dtmTransaksi= (DefaultTableModel) tblTransaksi.getModel();
         
        //tabel detail
        tblDetail.setModel(new DefaultTableModel(null,
                new String[]{
                    "ID",
                    "Paket",
                    "Jumlah",
                    "Satuan",
                    "Jumlah Harga"
                }));
        dtmDetail= (DefaultTableModel) tblDetail.getModel();
        tblDetail.getColumnModel().getColumn(0).setMinWidth(0);
        tblDetail.getColumnModel().getColumn(0).setMaxWidth(0);
        tblDetail.getColumnModel().getColumn(0).setWidth(0);
        
        //tabel paket
        tblPaket.setModel(new DefaultTableModel(null, 
                new String[]{
                    "ID",
                    "Nama",
                    "Tarif",
                    "Satuan"
                }));
        
        //tabel pelanggan
        tblPelanggan.setModel(new DefaultTableModel(null, new String[]{
            "ID",
            "Nama",
            "Telpon",
            "Alamat"
        }));
        
        //tbl nota
        tblNota.setModel(new DefaultTableModel(null, new String[]{
            "Paket",
            "Jumlah",
            "Total Harga"
        }));
        
    }
    
    private void refreshTableTransaksi(){
        transaksiDAO = factory.getTransaksiDAO();
        listTransaksi = transaksiDAO.getAll();
        dtmTransaksi = (DefaultTableModel) tblTransaksi.getModel();
        dtmTransaksi.setRowCount(0);
        for (Transaksi d: listTransaksi) {
            pelanggan = factory.getPelangganDAO().getById(d.getIdPelanggan());            
            dtmTransaksi.addRow(new Object[]{
                d.getTanggal(),
                pelanggan.getNama(),
                d.getTotalHarga()
            });
            
        }
    }
    
    
    private void refreshTablePaket(){
        paketDAO= factory.getPaketDAO();
        listPaket = paketDAO.getPaketByName(txtCariPaket.getText());
        DefaultTableModel dtmPaket = (DefaultTableModel) tblPaket.getModel();
        dtmPaket.setRowCount(0);
        for (PaketLaundry data:listPaket) {
            dtmPaket.addRow(new Object[]{
                data.getId(),
                data.getNamaPaket(),
                data.getTarif(),
                data.getSatuan()
            });
            
        }
    }
    
    private void refreshTablePelanggan(){
        listPelanggan = factory.getPelangganDAO().getPelangganByName(txtCariPelanggan.getText());
        DefaultTableModel dtmPelanggan = (DefaultTableModel) tblPelanggan.getModel();
        dtmPelanggan.setRowCount(0);
        for (Pelanggan data: listPelanggan) {
            dtmPelanggan.addRow(new Object[]{
                data.getId(),
                data.getNama(),
                data.getNoTelpon(),
                data.getAlamat()
            });
            
        }
    }
    
    private void refreshTableNota(){
        DefaultTableModel dtmDetail = (DefaultTableModel) tblNota.getModel();
        dtmDetail.setRowCount(0);
        for (DetailTransaksi d:listDetail) {
            PaketLaundry p=factory.getPaketDAO().getById(d.getIdPaket());
            dtmDetail.addRow(new Object[]{
                p.getNamaPaket(),
                d.getJumlah()+" "+ p.getSatuan(),
                d.getJumlahHarga()
            });
        }
    }
    
    private void printNota(){
        lblNota.setText(transaksi.getId().toString());
        lblNama.setText(pelanggan.getNama());
        lblAlamat.setText(pelanggan.getAlamat());
        lblTelpon.setText(pelanggan.getNoTelpon());
        lblNotaTotal.setText("Rp. " +transaksi.getTotalHarga());
        dNota.pack();
        dNota.setModal(true);
        dNota.setLocationRelativeTo(this);
        refreshTableNota();
        dNota.setVisible(true);
    }
    
    private void clearText(){
        txtID.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelpon.setText("");
        dtmDetail.setRowCount(0);
        lblTotal.setText("Rp.0");
        btnCheckout.setEnabled(false);        
        listDetail.clear();
        totalHarga=0;
        pelanggan = new Pelanggan();

    }
    
    private void saveTransaksi(){
        if (txtID.getText().equals("")) {
            pelanggan = new Pelanggan(
                    txtNama.getText(),
                    txtTelpon.getText(),
                    txtAlamat.getText()
            );
            factory.getPelangganDAO().save(pelanggan);
        }
        
        transaksi = new Transaksi();
        for (DetailTransaksi d:listDetail) {
            d.setIdTransaksi(transaksi.getId());
            factory.getDetailDAO().save(d);
        }
        transaksi.setIdPelanggan(pelanggan.getId());
        transaksi.setTotalHarga(totalHarga);
        transaksi.setTanggal();
        transaksiDAO.save(transaksi);
        
        printNota();
        
        refreshTableTransaksi();
        clearText();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dCariPelanggan = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPelanggan = new javax.swing.JTable();
        txtCariPelanggan = new javax.swing.JTextField();
        dCariPaket = new javax.swing.JDialog();
        txtCariPaket = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPaket = new javax.swing.JTable();
        dNota = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTelpon = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNota = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblNotaTotal = new javax.swing.JLabel();
        clsNota = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtTelpon = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        btnPelanggan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        btnCheckout = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();

        tblPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPelangganMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblPelanggan);

        txtCariPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariPelangganKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout dCariPelangganLayout = new javax.swing.GroupLayout(dCariPelanggan.getContentPane());
        dCariPelanggan.getContentPane().setLayout(dCariPelangganLayout);
        dCariPelangganLayout.setHorizontalGroup(
            dCariPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dCariPelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dCariPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(txtCariPelanggan))
                .addContainerGap())
        );
        dCariPelangganLayout.setVerticalGroup(
            dCariPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dCariPelangganLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCariPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtCariPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariPaketKeyTyped(evt);
            }
        });

        tblPaket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPaket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPaketMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPaket);

        javax.swing.GroupLayout dCariPaketLayout = new javax.swing.GroupLayout(dCariPaket.getContentPane());
        dCariPaket.getContentPane().setLayout(dCariPaketLayout);
        dCariPaketLayout.setHorizontalGroup(
            dCariPaketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dCariPaketLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(dCariPaketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addComponent(txtCariPaket))
                .addContainerGap())
        );
        dCariPaketLayout.setVerticalGroup(
            dCariPaketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dCariPaketLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCariPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Laundry Laundryan");

        jLabel7.setText("Kami melayani sepenuh hati :)");

        jLabel8.setText("Alamat kami tidak diketahui");

        jLabel9.setText("Telpon kami sudah rusak");

        jLabel10.setText("----------------------------------------------------------------------------------------------");

        jLabel11.setText("Telpon                   :");

        jLabel14.setText("Alamat                  :");

        lblNota.setText("jLabel15");

        lblNama.setText("jLabel16");

        jLabel12.setText("No. Nota               :");

        lblTelpon.setText("jLabel17");

        jLabel13.setText("Nama Pelanggan   :");

        lblAlamat.setText("jLabel18");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9))
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTelpon))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAlamat))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNota))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNama)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblNota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblNama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTelpon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblAlamat))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        tblNota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblNota);

        jLabel15.setText("Total Bayar");

        jLabel16.setText("----------------------------------------------------------------------------------------");

        jLabel17.setText("Kami tidak bertanggung jawab atas pakaian yang luntur, rusak ");

        jLabel18.setText("yang penting sudah kami cuci.");

        jLabel19.setText("Terimakasih Atas Kepercayaan Anda");

        lblNotaTotal.setText("jLabel19");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(227, 227, 227)
                .addComponent(lblNotaTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addGap(100, 100, 100))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblNotaTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel19))
        );

        clsNota.setText("[x]");
        clsNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clsNotaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dNotaLayout = new javax.swing.GroupLayout(dNota.getContentPane());
        dNota.getContentPane().setLayout(dNotaLayout);
        dNotaLayout.setHorizontalGroup(
            dNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dNotaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(clsNota))
        );
        dNotaLayout.setVerticalGroup(
            dNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dNotaLayout.createSequentialGroup()
                .addComponent(clsNota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(0, 0, 102));
        setBorder(null);
        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel2.setText("Nama");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel3.setText("Telpon");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 11)); // NOI18N
        jLabel4.setText("Alamat");

        txtID.setBorder(null);
        txtID.setEnabled(false);

        txtNama.setBorder(null);
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtTelpon.setBorder(null);

        txtAlamat.setBorder(null);

        btnPelanggan.setBackground(new java.awt.Color(255, 255, 255));
        btnPelanggan.setText("...");
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N
        jLabel5.setText("Pilih Paket");

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDetail);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N
        jLabel20.setText("Masukan Data Pelanggan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(49, 49, 49)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(jLabel5)))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHapus)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPelanggan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNama)
                        .addGap(1, 1, 1)))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnTambah))
                .addGap(25, 25, 25))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("Rp.0");

        btnCheckout.setText("Chekout");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(95, 113, 211));

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTransaksi.setGridColor(new java.awt.Color(153, 153, 153));
        jScrollPane2.setViewportView(tblTransaksi);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnCheckout)
                                .addGap(298, 298, 298)
                                .addComponent(btnBatal)
                                .addGap(222, 222, 222))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(lblTotal)
                                .addGap(420, 420, 420))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal)
                    .addComponent(btnCheckout))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        // TODO add your handling code here:
        dCariPelanggan.pack();
        dCariPelanggan.setModal(true);
        dCariPelanggan.setLocationRelativeTo(this);
        refreshTablePelanggan();
        dCariPelanggan.setVisible(true);
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        dCariPaket.pack();
        dCariPaket.setModal(true);
        dCariPaket.setLocationRelativeTo(this);
        refreshTablePaket();
        dCariPaket.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int index=-1,i=0,baris = tblDetail.getSelectedRow();
        for (DetailTransaksi d:listDetail) {
            if (d.getIdPaket().toString().equals(tblDetail.getValueAt(baris, 0).toString())) {
                index=i;
                break;
            }
            i++;
        }
        if (index>-1) listDetail.remove(index);
        this.totalHarga-= Double.parseDouble(tblDetail.getValueAt(baris, 4).toString());
        lblTotal.setText("Rp."+totalHarga);
        dtmDetail.removeRow(baris);
        if (listDetail.isEmpty()) {
            btnCheckout.setEnabled(false);
        }

    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        // TODO add your handling code here:
        if (txtNama.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Setidaknya Nama Pelanggan diisi");
        }else{
            saveTransaksi();
            btnCheckout.setEnabled(false);
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void txtCariPaketKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariPaketKeyTyped
        // TODO add your handling code here:
        refreshTablePaket();
    }//GEN-LAST:event_txtCariPaketKeyTyped

    private void tblPaketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaketMouseClicked
        // TODO add your handling code here:
        int baris = tblPaket.getSelectedRow();
        Long idPaket = Long.parseLong(tblPaket.getValueAt(baris, 0).toString());
        String namaPaket = tblPaket.getValueAt(baris, 1).toString();
        Double jumlah = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Jumlah Berat:"));
        String satuanPaket =tblPaket.getValueAt(baris, 3).toString();

        int tarif = Integer.parseInt(tblPaket.getValueAt(baris, 2).toString());
        Double jumlahHarga = jumlah * tarif;
        
        //tambah ke list
        listDetail.add(new DetailTransaksi(idPaket,jumlah, jumlahHarga));
        
        //stambah ke tabel
        dtmDetail.addRow(new Object[]{
            idPaket,
            namaPaket,
            jumlah,
            satuanPaket,
            jumlahHarga
        });
        
        //update label harga total
        totalHarga += jumlahHarga;
        lblTotal.setText("Rp. "+totalHarga);
        dCariPaket.setVisible(false);
        btnCheckout.setEnabled(true);
    }//GEN-LAST:event_tblPaketMouseClicked

    private void tblPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPelangganMouseClicked
        // TODO add your handling code here:
        int baris;
        baris = tblPelanggan.getSelectedRow();
        this.pelanggan = new Pelanggan(
            Long.parseLong(tblPelanggan.getValueAt(baris, 0).toString()),
            tblPelanggan.getValueAt(baris, 1).toString(),
            tblPelanggan.getValueAt(baris, 2).toString(),
            tblPelanggan.getValueAt(baris, 3).toString()
        );
        
        txtID.setText(pelanggan.toString());
        txtNama.setText(pelanggan.getNama());
        txtAlamat.setText(pelanggan.getAlamat());
        txtTelpon.setText(pelanggan.getNoTelpon());
        dCariPelanggan.setVisible(false);
    }//GEN-LAST:event_tblPelangganMouseClicked

    private void txtCariPelangganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariPelangganKeyTyped
        // TODO add your handling code here:
        refreshTablePelanggan();
    }//GEN-LAST:event_txtCariPelangganKeyTyped

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        clearText();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void clsNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clsNotaMouseClicked
        // TODO add your handling code here:
        dNota.setVisible(false);
    }//GEN-LAST:event_clsNotaMouseClicked

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPelanggan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel clsNota;
    private javax.swing.JDialog dCariPaket;
    private javax.swing.JDialog dCariPelanggan;
    private javax.swing.JDialog dNota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblNotaTotal;
    private javax.swing.JLabel lblTelpon;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblNota;
    private javax.swing.JTable tblPaket;
    private javax.swing.JTable tblPelanggan;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCariPaket;
    private javax.swing.JTextField txtCariPelanggan;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTelpon;
    // End of variables declaration//GEN-END:variables
}
