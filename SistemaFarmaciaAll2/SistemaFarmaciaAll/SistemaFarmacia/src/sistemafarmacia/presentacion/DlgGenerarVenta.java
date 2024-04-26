package sistemafarmacia.presentacion;

import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sistemafarmacia.VariablesFunciones;
import sistemafarmacia.datos.ClienteJuridicoDAT;
import sistemafarmacia.datos.ComprobanteBoletaDAT;
import sistemafarmacia.datos.ComprobanteFacturaDAT;
import sistemafarmacia.datos.PersonaDAT;
import sistemafarmacia.datos.ProductoDAT;
import sistemafarmacia.entidades.ClienteJuridico;
import sistemafarmacia.entidades.ComprobanteBoleta;
import sistemafarmacia.entidades.ComprobanteFactura;
import sistemafarmacia.entidades.DetalleComprobanteBoleta;
import sistemafarmacia.entidades.DetalleComprobanteFactura;
import sistemafarmacia.entidades.Persona;
import sistemafarmacia.entidades.Producto;


public class DlgGenerarVenta extends javax.swing.JDialog {

    private static final String TITLE = "Generar Venta";
    VariablesFunciones variables = new VariablesFunciones();
    
    Frame f = new Frame();
    ArrayList<DetalleComprobanteBoleta> lstDetalleVentaBoleta = new ArrayList<>();
    ArrayList<DetalleComprobanteFactura> lstDetalleVentaFactura = new ArrayList<>();
    Producto productoAgregar;    
    Persona personaCliente;
    ClienteJuridico clienteJuridico;
    public DlgGenerarVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        f= parent;
        this.setTitle(variables.getTitle()+TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        lblBanner.setText(TITLE);
        lblBanner.setForeground(Color.WHITE);
        pnlBanner.setBackground(variables.getColor());
        pnlPie.setBackground(variables.getColor());                
        btnBuscarProducto.setIcon(variables.getIconoBoton(btnBuscarProducto, 90, 2, "lupa"));        
        btnGuardar.setIcon(variables.getIconoBoton(btnGuardar,  110, 5, "disket"));
        btnCancelar.setIcon(variables.getIconoBoton(btnCancelar, 105, 0, "cancelar")); 
        btnRegresar.setIcon(variables.getIconoBoton(btnRegresar, 120, 10, "atras"));
                
        btnAgregar.setIcon(variables.getIconoBoton(btnAgregar, 90, 2, "descargar"));
        btnQuitar.setIcon(variables.getIconoBoton(btnQuitar, 90, 2, "menos"));
        btnNuevoCli.setIcon(variables.getIconoBoton(btnNuevoCli, 110, 5, "mas"));
        txtIdPersonaEmpresa.setVisible(false);
        txtIdProducto.setVisible(false);
        llenarListaProductos();
    }
    
    void limpiarControlesComprobante(){
        txtDireccion.setText("");
        txtNombres.setText("");
        txtDNIRUC.setText("");
        txtDNIRUC.requestFocus();
    }
        
    private void buscarCliente(boolean dniruc, boolean finalizado){
        
        PersonaDAT datosNatural = new PersonaDAT();
        ClienteJuridicoDAT datosJuridico = new ClienteJuridicoDAT();
        try {            
            
            if(txtDNIRUC.getText().length()>2 ||txtNombres.getText().length()>2){
                if(dniruc)txtNombres.setText("");
                if(!dniruc)txtDNIRUC.setText("");
                txtDireccion.setText("");
                txtIdPersonaEmpresa.setText("");
                if(optBoleta.isSelected()){
                    ArrayList<Persona> lst = datosNatural.getLista((dniruc)?txtDNIRUC.getText():txtNombres.getText(), 1, 1);
                    if(lst.size()>0){
                        if(finalizado){
                            txtNombres.setText(lst.get(0).getNombres());
                            txtDNIRUC.setText(String.valueOf(lst.get(0).getDni()));                            
                        }else{
                            if(dniruc)txtNombres.setText(lst.get(0).getNombres());
                            if(!dniruc)txtDNIRUC.setText(String.valueOf(lst.get(0).getDni()));
                        }                    
                        txtDireccion.setText(lst.get(0).getDireccion());
                        txtIdPersonaEmpresa.setText(String.valueOf(lst.get(0).getId()));
                        personaCliente = lst.get(0);
                    }                
                }else if(optFactura.isSelected()){
                    ArrayList<ClienteJuridico> lst = datosJuridico.getLista((dniruc)?txtDNIRUC.getText():txtNombres.getText(), 1, 1);
                    if(lst.size()>0){
                        if(finalizado){
                            txtNombres.setText(lst.get(0).getRazonSocial());
                            txtDNIRUC.setText(String.valueOf(lst.get(0).getRuc()));                            
                        }else{
                            if(dniruc)txtNombres.setText(lst.get(0).getRazonSocial());
                            if(!dniruc)txtDNIRUC.setText(String.valueOf(lst.get(0).getRuc()));
                        }
                        txtDireccion.setText(lst.get(0).getDireccion());
                        txtIdPersonaEmpresa.setText(String.valueOf(lst.get(0).getId()));
                        clienteJuridico = lst.get(0);
                    }
                }
            }
            
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "buscarCliente()", e.getMessage());
        }
    }
    
    private void llenarListaProductos(){
        DefaultTableModel dtm = new DefaultTableModel();
        variables.limpiarDefaulTableModel(dtm);
        String cabecera[] = {"NOMBRE","PRECIO","STOCK","FEC. VEN.","TIPO","LABORATORIO","ESTADO","TIPO_ID","LAB_ID","ESTADO_ID","ID"};
        dtm.setColumnIdentifiers(cabecera);
        tbListaProductos.setModel(dtm);
        try {
            ArrayList<Producto> lista = new ProductoDAT().getLista(txtBuscarProducto.getText(),1,100000);/*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
            String datos[] = new String[11];
            
            for (int i = 0; i<lista.size(); i++) {
                datos[0] = String.valueOf(lista.get(i).getNombre());
                datos[1] = String.valueOf(lista.get(i).getPrecio());
                datos[2] = String.valueOf(lista.get(i).getStock());
                datos[3] = String.valueOf(variables.getDateMysql(lista.get(i).getFechaVencimiento()));
                datos[4] = String.valueOf(lista.get(i).getTipo().getNombre());
                datos[5] = String.valueOf(lista.get(i).getLaboratorio().getNombre());
                datos[6] = String.valueOf(lista.get(i).getEstadoDescripcion());
                datos[7] = String.valueOf(lista.get(i).getTipo().getId());
                datos[8] = String.valueOf(lista.get(i).getLaboratorio().getId());
                datos[9] = String.valueOf(lista.get(i).getEstado());
                datos[10] = String.valueOf(lista.get(i).getId());
                dtm.addRow(datos);               
            }

        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarLista()", e.getMessage());
        }   
        tbListaProductos.setModel(dtm);
        variables.ocultarColumnasUltimas(cabecera.length, tbListaProductos, 5);
    }
    
    void limpiarControlesBuscarProducto(){
        txtIdProducto.setText("");
        txtBuscarProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtBuscarProducto.requestFocus();
    }
    
    private void mostrarDatosTablaProductos(){
        
        TableModel tbm = (TableModel) tbListaProductos.getModel();
        int columns = tbm.getColumnCount();
        productoAgregar = new Producto(
                Integer.valueOf(tbm.getValueAt(tbListaProductos.getSelectedRow(), columns-1).toString()),
                tbm.getValueAt(tbListaProductos.getSelectedRow(), 0).toString(), 
                Double.parseDouble(tbm.getValueAt(tbListaProductos.getSelectedRow(), 1).toString()), 
                Integer.valueOf(tbm.getValueAt(tbListaProductos.getSelectedRow(), 2).toString()), 
                null,null,1, null);
        txtIdProducto.setText(String.valueOf(productoAgregar.getId()));        
        txtBuscarProducto.setText(productoAgregar.getNombre());
        txtPrecio.setText(String.valueOf(productoAgregar.getPrecio()));
        txtCantidad.setText(String.valueOf(1)); 
        txtCantidad.requestFocus();
        //controlFunciones(3);
    }
    
    
    void insertarProductoDetalle(){
        ArrayList<DetalleComprobanteBoleta> lstTemporal = new ArrayList<>();
        boolean existe = false;
        int cantidad =  Integer.valueOf(txtCantidad.getText());
        for (int i = 0; i < lstDetalleVentaBoleta.size(); i++) {
            DetalleComprobanteBoleta detalle = lstDetalleVentaBoleta.get(i);
            Producto producto = detalle.getProducto();
            if(producto.getId()==productoAgregar.getId()){
                if((cantidad+detalle.getCantidad())>producto.getStock()){
                    variables.mostrarMensajeLinea("No hay la cantidad suficiente para este producto, quedan: "+producto.getStock());
                }else{
                    detalle.setCantidad(cantidad+detalle.getCantidad());
                } 
                existe = true;
            }            
            lstTemporal.add(detalle);
        }        
        if(!existe){
             if(cantidad>productoAgregar.getStock()){
                variables.mostrarMensajeLinea("No hay la cantidad suficiente para este producto, quedan: "+productoAgregar.getStock());                
            }else{
                lstTemporal.add(new DetalleComprobanteBoleta(productoAgregar, cantidad,productoAgregar.getPrecio()));
            }
        }
        lstDetalleVentaBoleta = lstTemporal; 
    }
    
    void vaciarControlesAll(){
        limpiarControlesComprobante();
        limpiarControlesBuscarProducto();        
        lstDetalleVentaBoleta = new ArrayList<>();
        lstDetalleVentaFactura = new ArrayList<>();
        productoAgregar = new Producto();    
        personaCliente = new Persona();
        clienteJuridico = new ClienteJuridico();
        llenarDetalleVenta();
    }
    
    void llenarDetalleVenta(){        
        DefaultTableModel dtm = new DefaultTableModel();
        variables.limpiarDefaulTableModel(dtm);
        String cabecera[] = {"CANTIDAD","PRODUCTO","P. UNIT.","IMPORTE","ID"};
        dtm.setColumnIdentifiers(cabecera);
        tbDetalleComprobante.setModel(dtm);
        String datos[] = new String[5];
        double importe=0,subTotal = 0,igv=0,total= 0;
            for (int i = 0; i<lstDetalleVentaBoleta.size(); i++) {
                importe = lstDetalleVentaBoleta.get(i).getCantidad()*lstDetalleVentaBoleta.get(i).getProducto().getPrecio();
                datos[0] = String.valueOf(lstDetalleVentaBoleta.get(i).getCantidad());
                datos[1] = String.valueOf(lstDetalleVentaBoleta.get(i).getProducto().getNombre());
                datos[2] = String.valueOf(lstDetalleVentaBoleta.get(i).getProducto().getPrecio());               
                datos[3] = String.valueOf(importe);
                datos[4] = String.valueOf(lstDetalleVentaBoleta.get(i).getProducto().getId());               
                dtm.addRow(datos);
                subTotal+=importe;
            }
        subTotal = variables.redondear(subTotal,2);
        igv = variables.redondear(subTotal*variables.getIGV(),2) ;
        total = variables.redondear(subTotal+igv,2);
        txtSubTotal.setText(String.valueOf(subTotal));
        txtIGV.setText(String.valueOf(igv));
        txtTotal.setText(String.valueOf(total));
        tbDetalleComprobante.setModel(dtm);
        variables.ocultarColumnasUltimas(cabecera.length, tbDetalleComprobante, 1);
    }
    
    void mostrarProductoDetalle(){
        TableModel tbm = (TableModel) tbDetalleComprobante.getModel();
        int columns = tbm.getColumnCount();
        productoAgregar = new Producto(
                Integer.valueOf(tbm.getValueAt(tbDetalleComprobante.getSelectedRow(), columns-1).toString()),
                tbm.getValueAt(tbDetalleComprobante.getSelectedRow(), 1).toString(), 
                Double.parseDouble(tbm.getValueAt(tbDetalleComprobante.getSelectedRow(), 2).toString()), 
                productoAgregar.getStock(), 
                null,null,1, null);
        txtIdProducto.setText(String.valueOf(productoAgregar.getId()));        
        txtBuscarProducto.setText(productoAgregar.getNombre());
        txtPrecio.setText(String.valueOf(productoAgregar.getPrecio()));
        txtCantidad.setText(tbm.getValueAt(tbDetalleComprobante.getSelectedRow(), 0).toString()); 
        txtCantidad.requestFocus();
    }
    
    void quitarProductoDetalle(){
        ArrayList<DetalleComprobanteBoleta> lstTemporal = new ArrayList<>();
        for (int i = 0; i<lstDetalleVentaBoleta.size(); i++) {
            if(lstDetalleVentaBoleta.get(i).getProducto().getId()!=productoAgregar.getId()) lstTemporal.add(lstDetalleVentaBoleta.get(i));        
        }
        lstDetalleVentaBoleta = lstTemporal;
        llenarDetalleVenta();
        limpiarControlesBuscarProducto();
    }
    
    void cambiarDetalleFactura(){
        for (int i = 0; i<lstDetalleVentaBoleta.size(); i++) {
            lstDetalleVentaFactura.add(new DetalleComprobanteFactura(
                    lstDetalleVentaBoleta.get(i).getProducto(), 
                    lstDetalleVentaBoleta.get(i).getCantidad(), 
                    lstDetalleVentaBoleta.get(i).getPrecioVenta()));
        }
    }
    
    void insert(){
         try {
            int res=0;
            if(optBoleta.isSelected()){
                ComprobanteBoletaDAT datosBoleta = new ComprobanteBoletaDAT();
    
                ComprobanteBoleta comprobanteBoleta = new ComprobanteBoleta(                  
                    variables.getDateForMySQL(variables.getDate()),personaCliente,FrmPrincipal.getUsuarioSession(), lstDetalleVentaBoleta);
                res = datosBoleta.insert(comprobanteBoleta);
            }else{
                ComprobanteFacturaDAT datosFactura = new ComprobanteFacturaDAT();
                cambiarDetalleFactura();
                ComprobanteFactura comprobanteFactura = new ComprobanteFactura(
                    variables.getDateForMySQL(variables.getDate()), clienteJuridico, FrmPrincipal.getUsuarioSession(), lstDetalleVentaFactura);
                res = datosFactura.insert(comprobanteFactura);
            }
            if(res>0) {
                variables.mostrarMensajeSucces(TITLE, "insert()", "Correcto.");
                vaciarControlesAll();
            }else{
                variables.mostrarMensajeError(TITLE, "insert()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "insert()", e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opgComprobante = new javax.swing.ButtonGroup();
        pnlBanner = new javax.swing.JPanel();
        lblBanner = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarProducto = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaProductos = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDetalleComprobante = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIGV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtDNIRUC = new javax.swing.JTextField();
        optFactura = new javax.swing.JRadioButton();
        optBoleta = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        btnNuevoCli = new javax.swing.JButton();
        pnlPie = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        txtIdProducto = new javax.swing.JTextField();
        txtIdPersonaEmpresa = new javax.swing.JTextField();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Productos"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Buscar:");

        txtBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProductoActionPerformed(evt);
            }
        });
        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });

        btnBuscarProducto.setText("Buscar");
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });

        tbListaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListaProductos);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Cantidad:");

        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Precio:");

        txtPrecio.setEditable(false);
        txtPrecio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Comprobante"));

        tbDetalleComprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDetalleComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetalleComprobanteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDetalleComprobante);

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Sub.Tot.:");

        txtSubTotal.setEditable(false);
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Impuestos");

        txtIGV.setEditable(false);
        txtIGV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Total:");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprobante"));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("DNI o RUC:");

        txtDNIRUC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIRUCActionPerformed(evt);
            }
        });
        txtDNIRUC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDNIRUCKeyReleased(evt);
            }
        });

        opgComprobante.add(optFactura);
        optFactura.setText("Factura");
        optFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFacturaActionPerformed(evt);
            }
        });

        opgComprobante.add(optBoleta);
        optBoleta.setSelected(true);
        optBoleta.setText("Boleta");
        optBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBoletaActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Nombre o Raz. Social:");

        txtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombresActionPerformed(evt);
            }
        });
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombresKeyReleased(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Dirección:");

        txtDireccion.setEditable(false);

        btnNuevoCli.setText("Nuevo");
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optBoleta, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(optFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDNIRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtDireccion)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDNIRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        txtIdProducto.setEditable(false);
        txtIdProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoActionPerformed(evt);
            }
        });
        txtIdProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdProductoKeyReleased(evt);
            }
        });

        txtIdPersonaEmpresa.setEditable(false);
        txtIdPersonaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPersonaEmpresaActionPerformed(evt);
            }
        });
        txtIdPersonaEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdPersonaEmpresaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlPieLayout = new javax.swing.GroupLayout(pnlPie);
        pnlPie.setLayout(pnlPieLayout);
        pnlPieLayout.setHorizontalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtIdPersonaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlPieLayout.setVerticalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdPersonaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
    DlgCliente cliente= new DlgCliente(f, rootPaneCheckingEnabled);
    cliente.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnNuevoCliActionPerformed
   
    
    private void txtDNIRUCKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIRUCKeyReleased
        buscarCliente(true,false);        
    }//GEN-LAST:event_txtDNIRUCKeyReleased

    private void optBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBoletaActionPerformed
        limpiarControlesComprobante();
        buscarCliente(true,true);
    }//GEN-LAST:event_optBoletaActionPerformed

    private void optFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFacturaActionPerformed
        limpiarControlesComprobante();
        buscarCliente(true,true);
    }//GEN-LAST:event_optFacturaActionPerformed

    private void txtNombresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyReleased
        buscarCliente(false,false);
    }//GEN-LAST:event_txtNombresKeyReleased

    private void txtDNIRUCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIRUCActionPerformed
        buscarCliente(true,true);
    }//GEN-LAST:event_txtDNIRUCActionPerformed

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
         buscarCliente(false,true);
    }//GEN-LAST:event_txtNombresActionPerformed

    private void txtBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProductoActionPerformed
        llenarListaProductos();
    }//GEN-LAST:event_txtBuscarProductoActionPerformed

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
      llenarListaProductos();
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        llenarListaProductos();
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void tbListaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaProductosMouseClicked
mostrarDatosTablaProductos();
    }//GEN-LAST:event_tbListaProductosMouseClicked

    private void txtIdProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoActionPerformed

    private void txtIdProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdProductoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoKeyReleased

    private void tbDetalleComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetalleComprobanteMouseClicked
mostrarProductoDetalle();
    }//GEN-LAST:event_tbDetalleComprobanteMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
    insertarProductoDetalle();
    llenarDetalleVenta();
    
  
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        quitarProductoDetalle();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        insert();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtIdPersonaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPersonaEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPersonaEmpresaActionPerformed

    private void txtIdPersonaEmpresaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPersonaEmpresaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPersonaEmpresaKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        vaciarControlesAll();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(DlgGenerarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgGenerarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgGenerarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgGenerarVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgGenerarVenta dialog = new DlgGenerarVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoCli;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBanner;
    private javax.swing.ButtonGroup opgComprobante;
    private javax.swing.JRadioButton optBoleta;
    private javax.swing.JRadioButton optFactura;
    private javax.swing.JPanel pnlBanner;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JTable tbDetalleComprobante;
    private javax.swing.JTable tbListaProductos;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDNIRUC;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtIGV;
    private javax.swing.JTextField txtIdPersonaEmpresa;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}

