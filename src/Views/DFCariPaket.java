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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahmat Subekti
 */
public class DFCariPaket extends javax.swing.JDialog {
    protected FrmTransaksi Tr;
    private Factory data = new Factory();
    private DefaultTableModel dtmPaket;
    private String[] tableHeader;
    private IPaketLaundry paketDAO;
    private ArrayList<PaketLaundry> listPaket;

    /**
     * Creates new form DFCariPaket
     */
    public DFCariPaket(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initTabelPaket();
        refreshIsiTable();
    }
    private void initTabelPaket(){
        tableHeader = new String[]{
            "ID",
            "Nama",
            "Tarif",
            "Satuan"
        };
        dtmPaket = new DefaultTableModel(null, tableHeader);
        tblPaket.setModel(dtmPaket);
    }
    private void refreshIsiTable(){
        paketDAO= data.getPaketDAO();
        listPaket = paketDAO.getPaketByName(txtCari.getText());
                dtmPaket = (DefaultTableModel) tblPaket.getModel();
        dtmPaket.setRowCount(0);
        
        listPaket.stream().forEach((data) -> {
            dtmPaket.addRow(new Object[]{
                data.getIdPaket(),
                data.getNamaPaket(),
                data.getTarif(),
                data.getSatuan()
            });
        });
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
        txtCari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
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
        jScrollPane1.setViewportView(tblPaket);

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addComponent(txtCari))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblPaketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPaketMouseClicked
        // TODO add your handling code here:
        int baris;
        baris = tblPaket.getSelectedRow();
        Tr.jumlah = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Jumlah Berat:"));
        Tr.paket = new PaketLaundry(
                tblPaket.getValueAt(baris, 0).toString(),
                tblPaket.getValueAt(baris, 1).toString(),
                Integer.parseInt(tblPaket.getValueAt(baris, 2).toString()),
                tblPaket.getValueAt(baris, 3).toString()
                
        );
        this.dispose();
    }//GEN-LAST:event_tblPaketMouseClicked

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        // TODO add your handling code here:
        refreshIsiTable();
    }//GEN-LAST:event_txtCariKeyTyped

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        this.data = Tr.data;
        refreshIsiTable();
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(DFCariPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DFCariPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DFCariPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DFCariPaket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DFCariPaket dialog = new DFCariPaket(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPaket;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}