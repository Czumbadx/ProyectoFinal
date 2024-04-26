package sistemafarmacia.presentacion;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sistemafarmacia.VariablesFunciones;
import sistemafarmacia.datos.ClienteJuridicoDAT;
import sistemafarmacia.datos.PersonaDAT;
import sistemafarmacia.entidades.ClienteJuridico;
import sistemafarmacia.entidades.Persona;


public class DlgCliente extends javax.swing.JDialog {

    private static final String TITLE = "Clientes";
    VariablesFunciones variables = new VariablesFunciones();
    PersonaDAT datosNatural = new PersonaDAT();
    ClienteJuridicoDAT datosJuridico = new ClienteJuridicoDAT();
    public DlgCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setTitle(variables.getTitle()+TITLE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        lblBanner.setText(TITLE);
        lblBanner.setForeground(Color.WHITE);
        pnlBanner.setBackground(variables.getColor());
        pnlPie.setBackground(variables.getColor());
        pnlPie1.setBackground(variables.getColor());
        
        
        btnBuscarNatural.setIcon(variables.getIconoBoton(btnBuscarNatural, 90, 2, "lupa"));        
        btnGuardarNatural.setIcon(variables.getIconoBoton(btnGuardarNatural, 120, 10, "disket"));
        btnCancelarNatural.setIcon(variables.getIconoBoton(btnCancelarNatural, 105, 0, "cancelar")); 
        btnRegresarNatural.setIcon(variables.getIconoBoton(btnRegresarNatural, 120, 10, "atras"));
        
        btnBuscarJuridico.setIcon(variables.getIconoBoton(btnBuscarJuridico, 90, 2, "lupa"));        
        btnGuardarJuridico.setIcon(variables.getIconoBoton(btnGuardarJuridico, 120, 10, "disket"));
        btnCancelarJuridico.setIcon(variables.getIconoBoton(btnCancelarJuridico, 105, 0, "cancelar")); 
        btnRegresarJuridico.setIcon(variables.getIconoBoton(btnRegresarJuridico, 120, 10, "atras"));
       
        llenarListaJuridico();
        controlFuncionesJuridico(1);
        llenarListaNatural();
        controlFuncionesNatural(1);
    }
    
    private void llenarListaNatural(){
        DefaultTableModel dtm = new DefaultTableModel();
        variables.limpiarDefaulTableModel(dtm);
        String cabecera[] = {"NOMBRES","APELLIDOS","D.N.I","DIRECCIÓN","ESTADO","ESTADO_ID","ID"};
        dtm.setColumnIdentifiers(cabecera);
        tbListaNatural.setModel(dtm);
        try {
            ArrayList<Persona> lista = datosNatural.getLista(txtBuscarNatural.getText(),2,0); /*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
            String datos[] = new String[7];
            
            for (int i = 0; i<lista.size(); i++) {
                datos[0] = String.valueOf(lista.get(i).getNombres());
                datos[1] = String.valueOf(lista.get(i).getApellidos());
                datos[2] = String.valueOf(lista.get(i).getDni());
                datos[3] = String.valueOf(lista.get(i).getDireccion());
                datos[4] = String.valueOf(lista.get(i).getEstadoDescripcion());
                datos[5] = String.valueOf(lista.get(i).getEstado());
                datos[6] = String.valueOf(lista.get(i).getId());
                dtm.addRow(datos);               
            }

        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarListaNatural()", e.getMessage());
        }
        tbListaNatural.setModel(dtm);
        variables.ocultarColumnasUltimas(cabecera.length, tbListaNatural, 2);
    }  
    
    private void insertNatural(){
        try {
            int res = datosNatural.insert(new Persona(txtNombres.getText(),txtApellidos.getText(),Integer.valueOf(txtDNI.getText()),txtDireccionNatural.getText()));
            if(res>0) {
                llenarListaNatural();
                controlFuncionesNatural(1);
            }else{
                variables.mostrarMensajeError(TITLE, "insert()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "insert()", e.getMessage());
        }
    }
    
    private void updateNatural(){
        try {
            int res = datosNatural.update(
                    new Persona(Integer.parseInt(txtIdNatural.getText()),txtNombres.getText(),txtApellidos.getText(),Integer.valueOf(txtDNI.getText()),txtDireccionNatural.getText(),(optActivoNatural.isSelected()) ? 1 : 0 )
            );
            if(res>0) {
                llenarListaNatural();
                controlFuncionesNatural(1);
            }else{
                variables.mostrarMensajeError(TITLE, "update()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "update()", e.getMessage());
        }
    }
    
    private void seleccionarEstadoNatural(boolean activo){
        optActivoNatural.setSelected(activo);
        optInactivoNatural.setSelected(!activo);
    }
    
    private void mostrarDatosTablaNatural(){
        
        TableModel tbm = (TableModel) tbListaNatural.getModel();
        int columns = tbm.getColumnCount();      
        txtIdNatural.setText(tbm.getValueAt(tbListaNatural.getSelectedRow(), columns-1).toString());
        txtNombres.setText(tbm.getValueAt(tbListaNatural.getSelectedRow(), 0).toString());
        txtApellidos.setText(tbm.getValueAt(tbListaNatural.getSelectedRow(), 1).toString());
        txtDNI.setText(tbm.getValueAt(tbListaNatural.getSelectedRow(), 2).toString());
        txtDireccionNatural.setText(tbm.getValueAt(tbListaNatural.getSelectedRow(), 3).toString());
        seleccionarEstadoNatural((tbm.getValueAt(tbListaNatural.getSelectedRow(), columns-2).toString().equalsIgnoreCase("1")));
        controlFuncionesNatural(3);
    }
    
    private void controlFuncionesNatural(int control){
        btnGuardarNatural.setIcon(variables.getIconoBoton(btnGuardarNatural, 120, 10, "disket"));
        if(control==1) //guardar nuevo
        {
            controlBotonesNatural(true,false, "Guardar");
            limpiaControlesNatural();
        }            
        if(control==2)// actualizar
            controlBotonesNatural(true,true, "Guardar");
        if(control==3)// seleccionado
        {
            controlBotonesNatural(false,true, "Editar");
            btnGuardarNatural.setIcon(variables.getIconoBoton(btnGuardarNatural, 120, 10, "editar"));
        }
            
    }
    
    private void limpiaControlesNatural(){  
        txtIdNatural.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDNI.setText("");
        txtDireccionNatural.setText("");
        txtDNI.requestFocus();
    }
    
    private void controlBotonesNatural(boolean activo, boolean estado, String guardar){
        txtNombres.setEditable(activo);
        txtApellidos.setEditable(activo);
        txtDNI.setEditable(activo);
        txtDireccionNatural.setEditable(activo);
        optActivoNatural.setEnabled(activo);
        optInactivoNatural.setEnabled(activo);
        btnGuardarNatural.setText(guardar);
        pnlEstadoNatural.setVisible(estado);
    }
    
    private void llenarListaJuridico(){
        DefaultTableModel dtm = new DefaultTableModel();
        variables.limpiarDefaulTableModel(dtm);
        String cabecera[] = {"RAZÓN SOCIAL","DIRECCIÓN","R.U.C.","ESTADO","ESTADO_ID","ID"};
        dtm.setColumnIdentifiers(cabecera);
        tbListaJuridico.setModel(dtm);
        try {
            ArrayList<ClienteJuridico> lista = datosJuridico.getLista(txtBuscarJuridico.getText(),2,0);/*all={2{todos},1{activos},0{inactivos}},limit ={0{todos},n{limite}}*/
            String datos[] = new String[6];
            
            for (int i = 0; i<lista.size(); i++) {
                datos[0] = String.valueOf(lista.get(i).getRazonSocial());
                datos[1] = String.valueOf(lista.get(i).getDireccion());
                datos[2] = String.valueOf(lista.get(i).getRuc());
                datos[3] = String.valueOf(lista.get(i).getEstadoDescripcion());
                datos[4] = String.valueOf(lista.get(i).getEstado());
                datos[5] = String.valueOf(lista.get(i).getId());
                dtm.addRow(datos);               
            }

        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "llenarListaJuridico()", e.getMessage());
        }
        tbListaJuridico.setModel(dtm);
        variables.ocultarColumnasUltimas(cabecera.length, tbListaJuridico, 2);
    } 
    
    private void insertJuridico(){
        try {
            int res = datosJuridico.insert(new ClienteJuridico(0,txtRazonSocial.getText(),txtDireccionJuridico.getText(),txtRUC.getText(),0));
            if(res>0) {
                llenarListaJuridico();
                controlFuncionesJuridico(1);
            }else{
                variables.mostrarMensajeError(TITLE, "insert()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "insert()", e.getMessage());
        }
    }
    
    private void updateJuridico(){
        try {
            int res = datosJuridico.update(
                    new ClienteJuridico(Integer.parseInt(txtIdJuridico.getText()),txtRazonSocial.getText(),txtDireccionJuridico.getText(),txtRUC.getText(),(optActivoJuridico.isSelected()) ? 1 : 0 )
            );
            if(res>0) {
                llenarListaJuridico();
                controlFuncionesJuridico(1);
            }else{
                variables.mostrarMensajeError(TITLE, "update()", "No se pudo completar la operación.");
            }
        } catch (Exception e) {
            variables.mostrarMensajeError(TITLE, "update()", e.getMessage());
        }
    }
    
    private void seleccionarEstadoJuridico(boolean activo){
        optActivoJuridico.setSelected(activo);
        optInactivoJuridico.setSelected(!activo);
    }
    
    private void mostrarDatosTablaJuridico(){
        
        TableModel tbm = (TableModel) tbListaJuridico.getModel();
        int columns = tbm.getColumnCount();      
        txtIdJuridico.setText(tbm.getValueAt(tbListaJuridico.getSelectedRow(), columns-1).toString());
        txtRazonSocial.setText(tbm.getValueAt(tbListaJuridico.getSelectedRow(), 0).toString());
        txtDireccionJuridico.setText(tbm.getValueAt(tbListaJuridico.getSelectedRow(), 1).toString());
        txtRUC.setText(tbm.getValueAt(tbListaJuridico.getSelectedRow(), 2).toString());
        seleccionarEstadoJuridico((tbm.getValueAt(tbListaJuridico.getSelectedRow(), columns-2).toString().equalsIgnoreCase("1")));
        controlFuncionesJuridico(3);
    }
    
    private void controlFuncionesJuridico(int control){
        btnGuardarJuridico.setIcon(variables.getIconoBoton(btnGuardarJuridico, 120, 10, "disket"));
        if(control==1) //guardar nuevo
        {
            controlBotonesJuridico(true,false, "Guardar");
            limpiaControlesJuridico();
        }            
        if(control==2)// actualizar
            controlBotonesJuridico(true,true, "Guardar");
        if(control==3)// seleccionado
        {
            controlBotonesJuridico(false,true, "Editar");
            btnGuardarJuridico.setIcon(variables.getIconoBoton(btnGuardarJuridico, 120, 10, "editar"));
        }
            
    }
    
    private void limpiaControlesJuridico(){  
        txtIdJuridico.setText("");
        txtRazonSocial.setText("");
        txtDireccionJuridico.setText("");
        txtRUC.setText("");
        txtRUC.requestFocus();
    }
    
    private void controlBotonesJuridico(boolean activo, boolean estado, String guardar){
        txtRazonSocial.setEditable(activo);
        txtDireccionJuridico.setEditable(activo);
        txtRUC.setEditable(activo);
        optActivoJuridico.setEnabled(activo);
        optInactivoJuridico.setEnabled(activo);
        btnGuardarJuridico.setText(guardar);
        pnlEstadoJuridico.setVisible(estado);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgEstadoNatural = new javax.swing.ButtonGroup();
        btgEstadoJuridico = new javax.swing.ButtonGroup();
        pnlBanner = new javax.swing.JPanel();
        lblBanner = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarNatural = new javax.swing.JTextField();
        btnBuscarNatural = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaNatural = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtIdNatural = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDireccionNatural = new javax.swing.JTextField();
        pnlEstadoNatural = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        optActivoNatural = new javax.swing.JRadioButton();
        optInactivoNatural = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        pnlPie = new javax.swing.JPanel();
        btnCancelarNatural = new javax.swing.JButton();
        btnGuardarNatural = new javax.swing.JButton();
        btnRegresarNatural = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBuscarJuridico = new javax.swing.JTextField();
        btnBuscarJuridico = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListaJuridico = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtIdJuridico = new javax.swing.JTextField();
        txtRUC = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDireccionJuridico = new javax.swing.JTextField();
        pnlEstadoJuridico = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        optActivoJuridico = new javax.swing.JRadioButton();
        optInactivoJuridico = new javax.swing.JRadioButton();
        pnlPie1 = new javax.swing.JPanel();
        btnCancelarJuridico = new javax.swing.JButton();
        btnGuardarJuridico = new javax.swing.JButton();
        btnRegresarJuridico = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlBanner.setBackground(new java.awt.Color(0, 153, 255));

        lblBanner.setBackground(new java.awt.Color(0, 153, 153));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Cliente Natural"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Buscar:");

        txtBuscarNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarNaturalActionPerformed(evt);
            }
        });
        txtBuscarNatural.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarNaturalKeyPressed(evt);
            }
        });

        btnBuscarNatural.setText("Buscar");
        btnBuscarNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNaturalActionPerformed(evt);
            }
        });

        tbListaNatural.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListaNatural.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaNaturalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListaNatural);

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
                        .addComponent(txtBuscarNatural)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Cliente Natural"));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("ID:");

        txtIdNatural.setEditable(false);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("D.N.I.:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Nombres:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Dirección:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Estado:");

        btgEstadoNatural.add(optActivoNatural);
        optActivoNatural.setText("Activo");

        btgEstadoNatural.add(optInactivoNatural);
        optInactivoNatural.setText("Inactivo");

        javax.swing.GroupLayout pnlEstadoNaturalLayout = new javax.swing.GroupLayout(pnlEstadoNatural);
        pnlEstadoNatural.setLayout(pnlEstadoNaturalLayout);
        pnlEstadoNaturalLayout.setHorizontalGroup(
            pnlEstadoNaturalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoNaturalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optActivoNatural, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optInactivoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEstadoNaturalLayout.setVerticalGroup(
            pnlEstadoNaturalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoNaturalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(optActivoNatural)
                .addComponent(optInactivoNatural))
        );

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Apellidos:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtIdNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApellidos))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDireccionNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlEstadoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 99, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDireccionNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlEstadoNatural, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnCancelarNatural.setText("Cancelar");
        btnCancelarNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarNaturalActionPerformed(evt);
            }
        });

        btnGuardarNatural.setText("Guardar");
        btnGuardarNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarNaturalActionPerformed(evt);
            }
        });

        btnRegresarNatural.setText("Regresar");
        btnRegresarNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarNaturalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPieLayout = new javax.swing.GroupLayout(pnlPie);
        pnlPie.setLayout(pnlPieLayout);
        pnlPieLayout.setHorizontalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlPieLayout.setVerticalGroup(
            pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresarNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cliente Natural", jPanel3);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Cliente Jurídico"));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Buscar:");

        txtBuscarJuridico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarJuridicoActionPerformed(evt);
            }
        });
        txtBuscarJuridico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarJuridicoKeyPressed(evt);
            }
        });

        btnBuscarJuridico.setText("Buscar");
        btnBuscarJuridico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarJuridicoActionPerformed(evt);
            }
        });

        tbListaJuridico.setModel(new javax.swing.table.DefaultTableModel(
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
        tbListaJuridico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaJuridicoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbListaJuridico);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarJuridico)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Cliente Jurídico"));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("ID:");

        txtIdJuridico.setEditable(false);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("R.U.C");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Razón Social:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Dirección:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Estado:");

        btgEstadoJuridico.add(optActivoJuridico);
        optActivoJuridico.setText("Activo");

        btgEstadoJuridico.add(optInactivoJuridico);
        optInactivoJuridico.setText("Inactivo");

        javax.swing.GroupLayout pnlEstadoJuridicoLayout = new javax.swing.GroupLayout(pnlEstadoJuridico);
        pnlEstadoJuridico.setLayout(pnlEstadoJuridicoLayout);
        pnlEstadoJuridicoLayout.setHorizontalGroup(
            pnlEstadoJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoJuridicoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optActivoJuridico, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optInactivoJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEstadoJuridicoLayout.setVerticalGroup(
            pnlEstadoJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(optActivoJuridico)
                .addComponent(optInactivoJuridico))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtDireccionJuridico)
                        .addGap(33, 33, 33)
                        .addComponent(pnlEstadoJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtIdJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonSocial)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDireccionJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlEstadoJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelarJuridico.setText("Cancelar");
        btnCancelarJuridico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarJuridicoActionPerformed(evt);
            }
        });

        btnGuardarJuridico.setText("Guardar");
        btnGuardarJuridico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarJuridicoActionPerformed(evt);
            }
        });

        btnRegresarJuridico.setText("Regresar");
        btnRegresarJuridico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarJuridicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPie1Layout = new javax.swing.GroupLayout(pnlPie1);
        pnlPie1.setLayout(pnlPie1Layout);
        pnlPie1Layout.setHorizontalGroup(
            pnlPie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPie1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlPie1Layout.setVerticalGroup(
            pnlPie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPie1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresarJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPie1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPie1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cliente Jurídico", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarNaturalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarNaturalActionPerformed

    private void btnRegresarJuridicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarJuridicoActionPerformed
    this.dispose();
    }//GEN-LAST:event_btnRegresarJuridicoActionPerformed

    private void btnCancelarNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarNaturalActionPerformed
        controlFuncionesNatural(1);
    }//GEN-LAST:event_btnCancelarNaturalActionPerformed

    private void btnGuardarNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarNaturalActionPerformed
        if(btnGuardarNatural.getText().equalsIgnoreCase("Guardar") && !pnlEstadoNatural.isVisible()) insertNatural();
        else if(btnGuardarNatural.getText().equalsIgnoreCase("Editar") && pnlEstadoNatural.isVisible())  controlFuncionesNatural(2);
        else if(btnGuardarNatural.getText().equalsIgnoreCase("Guardar") && pnlEstadoNatural.isVisible())  updateNatural();        
    }//GEN-LAST:event_btnGuardarNaturalActionPerformed

    private void tbListaNaturalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaNaturalMouseClicked
        mostrarDatosTablaNatural();
    }//GEN-LAST:event_tbListaNaturalMouseClicked

    private void btnGuardarJuridicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarJuridicoActionPerformed
        if(btnGuardarJuridico.getText().equalsIgnoreCase("Guardar") && !pnlEstadoJuridico.isVisible()) insertJuridico();
        else if(btnGuardarJuridico.getText().equalsIgnoreCase("Editar") && pnlEstadoJuridico.isVisible())  controlFuncionesJuridico(2);
        else if(btnGuardarJuridico.getText().equalsIgnoreCase("Guardar") && pnlEstadoJuridico.isVisible())  updateJuridico();
    }//GEN-LAST:event_btnGuardarJuridicoActionPerformed

    private void btnCancelarJuridicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarJuridicoActionPerformed
        controlFuncionesJuridico(1);
    }//GEN-LAST:event_btnCancelarJuridicoActionPerformed

    private void btnBuscarJuridicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarJuridicoActionPerformed
        llenarListaJuridico();
    }//GEN-LAST:event_btnBuscarJuridicoActionPerformed

    private void btnBuscarNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNaturalActionPerformed
        llenarListaNatural();
    }//GEN-LAST:event_btnBuscarNaturalActionPerformed

    private void txtBuscarNaturalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNaturalKeyPressed
        llenarListaNatural();
    }//GEN-LAST:event_txtBuscarNaturalKeyPressed

    private void txtBuscarNaturalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarNaturalActionPerformed
        llenarListaNatural();
    }//GEN-LAST:event_txtBuscarNaturalActionPerformed

    private void txtBuscarJuridicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarJuridicoActionPerformed
        llenarListaJuridico();
    }//GEN-LAST:event_txtBuscarJuridicoActionPerformed

    private void txtBuscarJuridicoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarJuridicoKeyPressed
        llenarListaJuridico();
    }//GEN-LAST:event_txtBuscarJuridicoKeyPressed

    private void tbListaJuridicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaJuridicoMouseClicked
        mostrarDatosTablaJuridico();
    }//GEN-LAST:event_tbListaJuridicoMouseClicked

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
            java.util.logging.Logger.getLogger(DlgCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgCliente dialog = new DlgCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup btgEstadoJuridico;
    private javax.swing.ButtonGroup btgEstadoNatural;
    private javax.swing.JButton btnBuscarJuridico;
    private javax.swing.JButton btnBuscarNatural;
    private javax.swing.JButton btnCancelarJuridico;
    private javax.swing.JButton btnCancelarNatural;
    private javax.swing.JButton btnGuardarJuridico;
    private javax.swing.JButton btnGuardarNatural;
    private javax.swing.JButton btnRegresarJuridico;
    private javax.swing.JButton btnRegresarNatural;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JRadioButton optActivoJuridico;
    private javax.swing.JRadioButton optActivoNatural;
    private javax.swing.JRadioButton optInactivoJuridico;
    private javax.swing.JRadioButton optInactivoNatural;
    private javax.swing.JPanel pnlBanner;
    private javax.swing.JPanel pnlEstadoJuridico;
    private javax.swing.JPanel pnlEstadoNatural;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JPanel pnlPie1;
    private javax.swing.JTable tbListaJuridico;
    private javax.swing.JTable tbListaNatural;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarJuridico;
    private javax.swing.JTextField txtBuscarNatural;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccionJuridico;
    private javax.swing.JTextField txtDireccionNatural;
    private javax.swing.JTextField txtIdJuridico;
    private javax.swing.JTextField txtIdNatural;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables
}
