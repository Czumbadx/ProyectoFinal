
package sistemafarmacia.presentacion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemafarmacia.VariablesFunciones;
import sistemafarmacia.datos.ComprobanteBoletaDAT;
import sistemafarmacia.datos.ComprobanteFacturaDAT;
import sistemafarmacia.entidades.ComprobanteBoleta;
import sistemafarmacia.entidades.ComprobanteFactura;
import sistemafarmacia.entidades.DetalleComprobanteBoleta;
import sistemafarmacia.entidades.DetalleComprobanteFactura;

public class DlgListaVenta extends javax.swing.JDialog {

    private static final String TITLE = "Reporte de Ventas";
    VariablesFunciones variables = new VariablesFunciones();
    public DlgListaVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setTitle(variables.getTitle()+TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        lblBanner.setText(TITLE);
        lblBanner.setForeground(Color.WHITE);
        pnlBanner.setBackground(variables.getColor());
        pnlPie.setBackground(variables.getColor()); 
        btnRegresar.setIcon(variables.getIconoBoton(btnRegresar, 120, 10, "atras"));
        llenarListaNatural();
        llenarListaJuridico();
    }

    void llenarListaNatural(){
        ComprobanteBoletaDAT dat = new ComprobanteBoletaDAT();
        try {
            ArrayList<ComprobanteBoleta> lst = dat.getListaAll();
            String resultado = "";
            int cantidad = 195;
            for (int i = 0; i < lst.size(); i++) {
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                resultado+="| ID: "+lst.get(i).getId()+"\n"; 
                resultado+="| FECHA: "+variables.getDateMysql(lst.get(i).getFecha())+"\n"; 
                resultado+="| USUARIO: "+lst.get(i).getUsuario().getPersona().getNombres()+" "+lst.get(i).getUsuario().getPersona().getApellidos()+"\n";  
                resultado+="| CLIENTE: "+lst.get(i).getPersona().getNombres()+" "+lst.get(i).getPersona().getApellidos()+"\n"; 
                resultado+="| MONTO: "+lst.get(i).getTotal()+"\n"; 
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                ArrayList<DetalleComprobanteBoleta> lst2 = lst.get(i).getLstDetalle();
                for (int j = 0; j < lst2.size(); j++) {
                    resultado+="\t| ID: "+lst2.get(j).getProducto().getId()+" \t| "; 
                    resultado+="NOMBRE: "+lst2.get(j).getProducto().getNombre()+" \t| "; 
                    resultado+="P.VENTA: "+lst2.get(j).getPrecioVenta()+" \t| "; 
                    resultado+="CANTIDAD: "+lst2.get(j).getCantidad()+"\t|"; 
                    resultado+="SUB TOTAL: "+lst2.get(j).getSubTotal()+"\n\n";
                }  
            }
            txaResultadoBoleta.setText(resultado);
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarListaNatural()", e.getMessage());
        }
    }
    
    void llenarListaJuridico(){
        ComprobanteFacturaDAT dat = new ComprobanteFacturaDAT();
        try {
            ArrayList<ComprobanteFactura> lst = dat.getListaAll();
            String resultado = "";
            int cantidad = 195;
            for (int i = 0; i < lst.size(); i++) {
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                resultado+="| ID: "+lst.get(i).getId()+"\n"; 
                resultado+="| FECHA: "+variables.getDateMysql(lst.get(i).getFecha())+"\n"; 
                resultado+="| USUARIO: "+lst.get(i).getUsuario().getPersona().getNombres()+" "+lst.get(i).getUsuario().getPersona().getApellidos()+"\n";  
                resultado+="| NIT DE CLIENTE: "+lst.get(i).getClienteJuridico().getRuc()+"\n"; 
                resultado+="| RAZÓN SOCIAL DE CLIENTE: "+lst.get(i).getClienteJuridico().getRazonSocial()+"\n"; 
                resultado+="| MONTO: "+lst.get(i).getTotal()+"\n"; 
                for (int j = 0; j < cantidad; j++) {
                    resultado+="-";
                }
                resultado+="\n";
                ArrayList<DetalleComprobanteFactura> lst2 = lst.get(i).getLstDetalle();
                for (int j = 0; j < lst2.size(); j++) {
                    resultado+="\t| ID: "+lst2.get(j).getProducto().getId()+" \t| "; 
                    resultado+="NOMBRE: "+lst2.get(j).getProducto().getNombre()+" \t| "; 
                    resultado+="P.VENTA: "+lst2.get(j).getPrecioVenta()+" \t| "; 
                    resultado+="CANTIDAD: "+lst2.get(j).getCantidad()+"\t|"; 
                    resultado+="SUB TOTAL: "+lst2.get(j).getSubTotal()+"\n\n"; 
                }
                
            
            }
            txaResultadoFactura.setText(resultado);
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarListaJuridico()", e.getMessage());
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBanner = new javax.swing.JPanel();
        lblBanner = new javax.swing.JLabel();
        panel = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaResultadoBoleta = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaResultadoFactura = new javax.swing.JTextArea();
        pnlPie = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlBanner.setBackground(new java.awt.Color(0, 102, 102));

        lblBanner.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblBanner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBanner.setText("CABECERA");

        javax.swing.GroupLayout pnlBannerLayout = new javax.swing.GroupLayout(pnlBanner);
        pnlBanner.setLayout(pnlBannerLayout);
        pnlBannerLayout.setHorizontalGroup(
            pnlBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBannerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBannerLayout.setVerticalGroup(
            pnlBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBannerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        txaResultadoBoleta.setColumns(20);
        txaResultadoBoleta.setRows(5);
        jScrollPane3.setViewportView(txaResultadoBoleta);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel.addTab("Boletas", jPanel3);

        txaResultadoFactura.setColumns(20);
        txaResultadoFactura.setRows(5);
        jScrollPane4.setViewportView(txaResultadoFactura);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel.addTab("Facturas", jPanel4);

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPieLayout = new javax.swing.GroupLayout(pnlPie);
        pnlPie.setLayout(pnlPieLayout);
        pnlPieLayout.setHorizontalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPieLayout.setVerticalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addComponent(pnlPie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel)
                .addGap(18, 18, 18)
                .addComponent(pnlPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

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
            java.util.logging.Logger.getLogger(DlgListaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgListaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgListaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgListaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgListaVenta dialog = new DlgListaVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnRegresar;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JTabbedPane panel;
    private javax.swing.JPanel pnlBanner;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JTextArea txaResultadoBoleta;
    private javax.swing.JTextArea txaResultadoFactura;
    // End of variables declaration//GEN-END:variables
}
