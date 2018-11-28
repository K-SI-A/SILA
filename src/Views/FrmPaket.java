/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Factory.Factory;
import Intefaces.IPaketLaundry;
import Models.PaketLaundry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahmat Subekti
 */
public class FrmPaket extends javax.swing.JFrame {

    private Factory factory = new Factory();
    private boolean newRecord, needSave,status=false;
    private DefaultTableModel dtmPaket;
    private String[] tableHeader;
    private ArrayList<PaketLaundry> listPaket;
    private PaketLaundry paket;
    private IPaketLaundry paketDAO;
    private int baris;

    /**
     * Creates new form FrmPaket
     */
    public FrmPaket() {
        initComponents();
        initTablePaket();
        tblPaket.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            isSaved();
            btnHapus.setEnabled(true);
            btnTambah.setEnabled(true);
            baris = tblPaket.getSelectedRow();
            if (baris>=0) {
                txtID.setText(dtmPaket.getValueAt(baris, 0).toString());
                txtNama.setText(dtmPaket.getValueAt(baris, 1).toString());
                txtTarif.setText(dtmPaket.getValueAt(baris, 2).toString());
                txtSatuan.setText(dtmPaket.getValueAt(baris, 3).toString());
            }
        });
        btnSimpan.setEnabled(false);
        factory = new Factory();
        refreshIsiTable();
    }

    private void initTablePaket() {
        tableHeader = new String[]{
            "ID",
            "Nama",
            "Tarif",
            "Satuan"
        };
        dtmPaket = new DefaultTableModel(null,tableHeader);
        tblPaket.setModel(dtmPaket);
    }
    private void refreshIsiTable() {
        paketDAO = factory.getPaketDAO();
        listPaket = paketDAO.getAll();
        dtmPaket = (DefaultTableModel) tblPaket.getModel();
        dtmPaket.setRowCount(0);
        
        for (PaketLaundry d:listPaket) {
            dtmPaket.addRow(new Object[]{
                d.getId(),
                d.getNamaPaket(),
                d.getTarif(),
                d.getSatuan()
            });
        }
        
        if (tblPaket.getRowCount()>0) {
            baris = tblPaket.getRowCount()-1;
            tblPaket.setRowSelectionInterval(baris, baris);
        }
    }
    
    private void saveRecord(){
        if (newRecord) {
            paket= new PaketLaundry(
                    txtNama.getText(),
                    Integer.parseInt(txtTarif.getText()),
                    txtSatuan.getText()
            );
            status = paketDAO.save(paket);
            newRecord = false;
        }else{
            paket= new PaketLaundry(
                    Long.parseLong(txtID.getText()),
                    txtNama.getText(),
                    Integer.parseInt(txtTarif.getText()),
                    txtSatuan.getText()
            );
            status = paketDAO.update(paket);
        }
        if (!status) {
            JOptionPane.showMessageDialog(null, "Data tidak tersimpan.", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
        status=false;
        needSave=false;
        btnTambah.setEnabled(false);
        refreshIsiTable();
    }
    
    private void clearText(){
        txtID.setText("");
        txtNama.setText("");
        txtTarif.setText("");
        txtSatuan.setText("");
    }
    
    private void recordChanged(){
        needSave=true;
        btnHapus.setEnabled(false);
        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(true);
    }
    
    private void isSaved(){
        if (needSave) {
            if (JOptionPane.showConfirmDialog(null, "Data yang diubah belum disimpan. Simpan sekarang?",
                    "Simpan perubahan?", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
                saveRecord();
            }
        }
        needSave=false;
        newRecord=false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPaket = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtTarif = new javax.swing.JTextField();
        txtSatuan = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
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
        jScrollPane1.setViewportView(tblPaket);

        jLabel1.setText("ID");

        jLabel2.setText("Nama");

        jLabel3.setText("Tarif");

        jLabel4.setText("Satuan");

        txtID.setEnabled(false);

        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaKeyTyped(evt);
            }
        });

        txtTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTarifKeyTyped(evt);
            }
        });

        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanKeyTyped(evt);
            }
        });

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

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID)
                            .addComponent(txtNama)
                            .addComponent(txtTarif)
                            .addComponent(txtSatuan)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus)
                    .addComponent(btnSimpan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        factory.Close();
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        // TODO add your handling code here:
        recordChanged();
    }//GEN-LAST:event_txtNamaKeyTyped

    private void txtTarifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifKeyTyped
        // TODO add your handling code here:
        recordChanged();
    }//GEN-LAST:event_txtTarifKeyTyped

    private void txtSatuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyTyped
        // TODO add your handling code here:
        recordChanged();
    }//GEN-LAST:event_txtSatuanKeyTyped

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        isSaved();
        btnTambah.setEnabled(false);
        newRecord = true;
        needSave=true;
        clearText();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here: 
        isSaved();
        if (factory.isUsed(txtID.getText())) {
            JOptionPane.showMessageDialog(null, 
                    txtNama.getText() + " Sedang digunakan.", 
                    "Tidak dihapus.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if (JOptionPane.showConfirmDialog(null, txtNama.getText() + 
                    " akan di hapus. Lanjutkan?",
                    "Konfirmasi Hapus " + txtNama.getText(),
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                paketDAO.delete(Long.parseLong(txtID.getText()));
        }
        btnHapus.setEnabled(false);
        refreshIsiTable();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        saveRecord();
    }//GEN-LAST:event_btnSimpanActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPaket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPaket;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSatuan;
    private javax.swing.JTextField txtTarif;
    // End of variables declaration//GEN-END:variables


}
