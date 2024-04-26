package sistemafarmacia.presentacion;

import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sistemafarmacia.VariablesFunciones;
import sistemafarmacia.datos.LaboratorioDAT;
import sistemafarmacia.datos.ProductoDAT;
import sistemafarmacia.datos.ProductoTipoDAT;
import sistemafarmacia.entidades.Laboratorio;
import sistemafarmacia.entidades.Producto;
import sistemafarmacia.entidades.ProductoTipo;

public class DlgProducto extends javax.swing.JDialog {

    private static final String TITLE = "Productos";
    VariablesFunciones variables = new VariablesFunciones();
    Frame f = new Frame();
    ProductoDAT datos = new ProductoDAT();
    DlgProductoTipo dlgTipoProducto;
    DlgLaboratorio dlgLaboratorio;
    public DlgProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        f = parent;
        this.setTitle(variables.getTitle()+TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        lblBanner.setText(TITLE);
        lblBanner.setForeground(Color.WHITE);
        pnlBanner.setBackground(variables.getColor());
        pnlPie.setBackground(variables.getColor());                
        btnBuscar.setIcon(variables.getIconoBoton(btnBuscar, 90, 2, "lupa"));        
        btnGuardar.setIcon(variables.getIconoBoton(btnGuardar, 120, 10, "disket"));
        btnCancelar.setIcon(variables.getIconoBoton(btnCancelar, 105, 0, "cancelar")); 
        btnRegresar.setIcon(variables.getIconoBoton(btnRegresar, 120, 10, "atras"));
        
        btnNuevoLab.setIcon(variables.getIconoBoton(btnNuevoLab, 110, 5, "mas")); 
        btnNuevoTipo.setIcon(variables.getIconoBoton(btnNuevoTipo, 110, 5, "mas"));
        llenarComboLaboratorio();
        llenarComboTipo();
        controlFunciones(1);
        llenarLista();
    }

    private void llenarComboLaboratorio(){
        try {
            cboLaboratorio.removeAllItems();
            ArrayList<Laboratorio> lst = new LaboratorioDAT().getLista("",1,100000);/*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
            lst.stream().forEach((laboratorio) -> {
                cboLaboratorio.addItem(laboratorio);
            });           
            
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarComboLaboratorio()", e.getMessage());
        }
    }
    
    private void llenarComboTipo(){
        try {
            cboTipo.removeAllItems();
            ArrayList<ProductoTipo> lst = new ProductoTipoDAT().getLista("",1,100000);/*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
            lst.stream().forEach((tipo) -> {                
                cboTipo.addItem(tipo);
            });           
            
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarComboTipo()", e.getMessage());
        }
    }
    
    private void llenarLista(){
        DefaultTableModel dtm = new DefaultTableModel();
        variables.limpiarDefaulTableModel(dtm);
        String cabecera[] = {"NOMBRE","PRECIO","STOCK","FEC. VEN.","TIPO","LABORATORIO","ESTADO","TIPO_ID","LAB_ID","ESTADO_ID","ID"};
        dtm.setColumnIdentifiers(cabecera);
        tbLista.setModel(dtm);
        try {
            ArrayList<Producto> lista = datos.getLista(txtBuscar.getText(),2,0);/*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
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
        tbLista.setModel(dtm);
        variables.ocultarColumnasUltimas(cabecera.length, tbLista, 4);
    } 
    
    private void mostrarDatosTabla(){
        
        TableModel tbm = (TableModel) tbLista.getModel();
        int columns = tbm.getColumnCount();      
        txtId.setText(tbm.getValueAt(tbLista.getSelectedRow(), columns-1).toString());
        seleccionarEstado((tbm.getValueAt(tbLista.getSelectedRow(), columns-2).toString().equalsIgnoreCase("1")));
        txtNombre.setText(tbm.getValueAt(tbLista.getSelectedRow(), 0).toString());
        txtPrecio.setText(tbm.getValueAt(tbLista.getSelectedRow(), 1).toString());
        txtStock.setText(tbm.getValueAt(tbLista.getSelectedRow(), 2).toString());
        dcFechaVencimiento.setDate(variables.getDateFromString(tbm.getValueAt(tbLista.getSelectedRow(), 3).toString()));
        cboTipo.setSelectedItem(new ProductoTipo(
                (Integer.valueOf(tbm.getValueAt(tbLista.getSelectedRow(), 7).toString())), 
                tbm.getValueAt(tbLista.getSelectedRow(), 4).toString(), "", 1));
      
        seleccionarComboLaboratorio(Integer.valueOf(tbm.getValueAt(tbLista.getSelectedRow(), 8).toString()));
        seleccionarComboTipo(Integer.valueOf(tbm.getValueAt(tbLista.getSelectedRow(), 7).toString()));
        controlFunciones(3);
    }
    
    void seleccionarComboLaboratorio(int id){
        for (int i = 0; i < cboLaboratorio.getItemCount(); i++)
        {
            Laboratorio laboratorio = (Laboratorio)cboLaboratorio.getItemAt(i);
            if(laboratorio.getId()==id){
                cboLaboratorio.setSelectedIndex(i);
                break;
            }
        }
    }
    
    void seleccionarComboTipo(int id){
        for (int i = 0; i < cboTipo.getItemCount(); i++)
        {
            ProductoTipo tipo = (ProductoTipo)cboTipo.getItemAt(i);
            if(tipo.getId()==id){
                cboTipo.setSelectedIndex(i);
                break;
            }
        }
    }
    
    
    private void insert(){
        try {
            int res = datos.insert(new Producto(
                    0, 
                    txtNombre.getText(), 
                    Double.valueOf(txtPrecio.getText()), 
                    Integer.valueOf(txtStock.getText()), 
                    (ProductoTipo)cboTipo.getSelectedItem(), 
                    (Laboratorio)cboLaboratorio.getSelectedItem(), 
                    0, 
                    variables.getDateForMySQL(dcFechaVencimiento.getDate())
            ));
            if(res>0) {
                llenarLista();
                controlFunciones(1);
            }else{
                variables.mostrarMensajeError(TITLE, "insert()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "insert()", e.getMessage());
        }
    }
    
    private void update(){
        try {
            int res = datos.update(new Producto(
                    Integer.valueOf(txtId.getText()), 
                    txtNombre.getText(), 
                    Double.valueOf(txtPrecio.getText()), 
                    Integer.valueOf(txtStock.getText()), 
                    (ProductoTipo)cboTipo.getSelectedItem(), 
                    (Laboratorio)cboLaboratorio.getSelectedItem(), 
                    (optActivo.isSelected()) ? 1 : 0 , 
                    variables.getDateForMySQL(dcFechaVencimiento.getDate())
            ));
            if(res>0) {
                llenarLista();
                controlFunciones(1);
            }else{
                variables.mostrarMensajeError(TITLE, "update()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "update()", e.getMessage());
        }
    }
    
    private void seleccionarEstado(boolean activo){
        optActivo.setSelected(activo);
        optInactivo.setSelected(!activo);
    }
        
    private void controlFunciones(int control){
        btnGuardar.setIcon(variables.getIconoBoton(btnGuardar, 120, 10, "disket"));
        if(control==1) //guardar nuevo
        {
            controlBotones(true,false, "Guardar");
            limpiaControles();
        }            
        if(control==2)// actualizar
            controlBotones(true,true, "Guardar");
        if(control==3)// seleccionado
        {
            controlBotones(false,true, "Editar");
            btnGuardar.setIcon(variables.getIconoBoton(btnGuardar, 120, 10, "editar"));
        }
            
    }
    
    private void limpiaControles(){  
        txtId.setText("");
        txtNombre.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
        dcFechaVencimiento.setDate(variables.getDate());        
        txtNombre.requestFocus();
    }
    
    private void controlBotones(boolean activo, boolean estado, String guardar){
        txtNombre.setEditable(activo);
        txtStock.setEditable(activo);
        txtPrecio.setEditable(activo);
        optActivo.setEnabled(activo);
        optInactivo.setEnabled(activo);
        cboLaboratorio.setEnabled(activo);
        cboTipo.setEnabled(activo);
        dcFechaVencimiento.setEnabled(activo);
        btnGuardar.setText(guardar);
        pnlEstado.setVisible(estado);
    } 
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgEstado = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        pnlEstado = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        optActivo = new javax.swing.JRadioButton();
        optInactivo = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnNuevoTipo = new javax.swing.JButton();
        btnNuevoLab = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        dcFechaVencimiento = new com.toedter.calendar.JDateChooser();
        cboTipo = new javax.swing.JComboBox();
        cboLaboratorio = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLista = new javax.swing.JTable();
        pnlBanner = new javax.swing.JPanel();
        lblBanner = new javax.swing.JLabel();
        pnlPie = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Producto"));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("ID:");

        txtId.setEditable(false);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Nombre:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Stock:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Estado:");

        btgEstado.add(optActivo);
        optActivo.setText("Activo");

        btgEstado.add(optInactivo);
        optInactivo.setText("Inactivo");

        javax.swing.GroupLayout pnlEstadoLayout = new javax.swing.GroupLayout(pnlEstado);
        pnlEstado.setLayout(pnlEstadoLayout);
        pnlEstadoLayout.setHorizontalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEstadoLayout.setVerticalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(optActivo)
                .addComponent(optInactivo))
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Fecha Vencimiento:");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Tipo:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Laboratorio:");

        btnNuevoTipo.setText("Nuevo");
        btnNuevoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoActionPerformed(evt);
            }
        });

        btnNuevoLab.setText("Nuevo");
        btnNuevoLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoLabActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Precio:");

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboLaboratorio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboTipo, 0, 252, Short.MAX_VALUE)
                            .addComponent(cboLaboratorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnNuevoLab, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(pnlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtStock, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevoLab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Productos"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Buscar:");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tbLista.setModel(new javax.swing.table.DefaultTableModel(
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
        tbLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbLista);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout pnlPieLayout = new javax.swing.GroupLayout(pnlPie);
        pnlPie.setLayout(pnlPieLayout);
        pnlPieLayout.setHorizontalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(pnlPie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(pnlPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        llenarLista();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        llenarLista();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        llenarLista();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tbListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaMouseClicked
        mostrarDatosTabla();
    }//GEN-LAST:event_tbListaMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        controlFunciones(1);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(btnGuardar.getText().equalsIgnoreCase("Guardar") && !pnlEstado.isVisible()) insert();
        else if(btnGuardar.getText().equalsIgnoreCase("Editar") && pnlEstado.isVisible())  controlFunciones(2);
        else if(btnGuardar.getText().equalsIgnoreCase("Guardar") && pnlEstado.isVisible())  update();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnNuevoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoActionPerformed
        ProductoTipo tipo = (ProductoTipo)cboTipo.getSelectedItem();
        dlgTipoProducto= new DlgProductoTipo(f, rootPaneCheckingEnabled,tipo.getId());
        dlgTipoProducto.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnNuevoTipoActionPerformed
    
    private void btnNuevoLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoLabActionPerformed
        Laboratorio laboratorio = (Laboratorio)cboLaboratorio.getSelectedItem();
        dlgLaboratorio= new DlgLaboratorio(f, rootPaneCheckingEnabled,laboratorio.getId());
        dlgLaboratorio.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnNuevoLabActionPerformed
    
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        
        if(dlgLaboratorio!=null && dlgLaboratorio.getIdDevuelve()>0){
            llenarComboLaboratorio();
            seleccionarComboLaboratorio(dlgLaboratorio.getIdDevuelve());
            dlgLaboratorio.setIdDevuelve(0);
        }
        
        if(dlgTipoProducto!=null && dlgTipoProducto.getIdProductoTipo()>0){
            llenarComboTipo();
            seleccionarComboTipo(dlgTipoProducto.getIdProductoTipo());
            dlgTipoProducto.setIdProductoTipo(0);
        }
        
        
    }//GEN-LAST:event_formWindowGainedFocus

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
            java.util.logging.Logger.getLogger(DlgProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgProducto dialog = new DlgProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup btgEstado;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoLab;
    private javax.swing.JButton btnNuevoTipo;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox cboLaboratorio;
    private javax.swing.JComboBox cboTipo;
    private com.toedter.calendar.JDateChooser dcFechaVencimiento;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JRadioButton optActivo;
    private javax.swing.JRadioButton optInactivo;
    private javax.swing.JPanel pnlBanner;
    private javax.swing.JPanel pnlEstado;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JTable tbLista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
