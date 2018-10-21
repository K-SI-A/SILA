/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Factory.Factory;
import javax.swing.JFrame;

/**
 *
 * @author Rahmat Subekti
 */
public class FrmDashboard extends javax.swing.JFrame {
    protected Factory data = new Factory();

    /**
     * Creates new form FrmDashboars
     */
    public FrmDashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void showFrmPelanggan(){
        FrmPelanggan p = new FrmPelanggan();
        p.dash=this;
        p.setVisible(true);
        
    }
    private void showFrmPaket(){
        FrmPaket p = new FrmPaket();
        p.dash=this;
        p.setVisible(true);
        
    }
    private void showFrmTransaksi(){
        FrmTransaksi t= new FrmTransaksi();
        t.dash=this;
        t.setVisible(true);
        
    }
    private void showFrmLaporan(){
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPelanggan = new javax.swing.JLabel();
        frmPaket = new javax.swing.JLabel();
        lblTransaksi = new javax.swing.JLabel();
        frmLaporan = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnPelanggan = new javax.swing.JMenu();
        mnPaket = new javax.swing.JMenu();
        mnTransaksi = new javax.swing.JMenu();
        mnLaporan = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPelanggan.setText("Pelanggan");
        lblPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPelangganMouseClicked(evt);
            }
        });

        frmPaket.setText("Paket");

        lblTransaksi.setText("Transaksi");

        frmLaporan.setText("Laporan");

        mnPelanggan.setText("Pelanggan");
        mnPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnPelangganMouseClicked(evt);
            }
        });
        jMenuBar1.add(mnPelanggan);

        mnPaket.setText("Paket");
        mnPaket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnPaketMouseClicked(evt);
            }
        });
        jMenuBar1.add(mnPaket);

        mnTransaksi.setText("Transaksi");
        mnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnTransaksiMouseClicked(evt);
            }
        });
        jMenuBar1.add(mnTransaksi);

        mnLaporan.setText("Laporan");
        mnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnLaporanMouseClicked(evt);
            }
        });
        jMenuBar1.add(mnLaporan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTransaksi)
                    .addComponent(lblPelanggan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(frmPaket)
                    .addComponent(frmLaporan))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPelanggan)
                    .addComponent(frmPaket))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTransaksi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(frmLaporan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnPelangganMouseClicked
        // TODO add your handling code here:
        showFrmPelanggan();
    }//GEN-LAST:event_mnPelangganMouseClicked

    private void mnPaketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnPaketMouseClicked
        // TODO add your handling code here:
        showFrmPaket();
    }//GEN-LAST:event_mnPaketMouseClicked

    private void mnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnTransaksiMouseClicked
        // TODO add your handling code here:
        showFrmTransaksi();
    }//GEN-LAST:event_mnTransaksiMouseClicked

    private void lblPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPelangganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPelangganMouseClicked

    private void mnLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnLaporanMouseClicked
        // TODO add your handling code here:
        FrmLaporan l= new FrmLaporan(null,true);
        l.dash=this;
        l.setVisible(true);
    }//GEN-LAST:event_mnLaporanMouseClicked

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
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel frmLaporan;
    private javax.swing.JLabel frmPaket;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblPelanggan;
    private javax.swing.JLabel lblTransaksi;
    private javax.swing.JMenu mnLaporan;
    private javax.swing.JMenu mnPaket;
    private javax.swing.JMenu mnPelanggan;
    private javax.swing.JMenu mnTransaksi;
    // End of variables declaration//GEN-END:variables
}
