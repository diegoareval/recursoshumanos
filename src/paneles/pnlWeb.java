
package paneles;

import com.devs.dao.EmpleadoDao;
import com.devs.dao.PagoMensualDao;
import com.devs.dao.PlanillaDao;
import com.devs.dao.PuestoDao;
import com.devs.entities.Empleados;
import com.devs.entities.Pagomensual;
import com.devs.entities.Planillas;
import com.devs.entities.Puestos;
import com.devs.vistas.frmContrato;
import ds.desktop.notify.DesktopNotify;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class pnlWeb extends javax.swing.JPanel {
   DefaultComboBoxModel<Puestos> modelocombo = new DefaultComboBoxModel<Puestos>(); 
   Puestos puestoseleccionado=null;
    Empleados empseleccionado=null;
    EmpleadoDao empDao=new EmpleadoDao();
    PuestoDao puDao=new PuestoDao();
    
    
DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    /**
     * Creates new form pnlHome
     */
    public pnlWeb() {
        cargarColumnas();
        cargarModeloTabla();
        cargarModeloSegmento();
        initComponents();
               
         DesControlesInicio();
       
       
    }
    public void modificar() {
        if(empseleccionado!=null){
            try {
                empseleccionado.setNombres(txtnombre.getText());
                empseleccionado.setApellidos(txtapellidos.getText());
                empseleccionado.setDui(txtdui.getText());
                empseleccionado.setDireccion(txtdireccion.getText());
                empseleccionado.setTelefono(txttelefono.getText());
                empseleccionado.setCorreo(txtcorreo.getText());
                empseleccionado.setPuestos((Puestos)jcpuesto.getSelectedItem());
                empseleccionado.setEstado((String)jcestado.getSelectedItem());
                empseleccionado.setSalario(BigDecimal.valueOf(Double.parseDouble(txtsalario.getText())));
                empDao.Update(empseleccionado);
                cargarModeloTabla();
                
                DesktopNotify.showDesktopMessage("Exito","Se ha modificado Correctamente el Registro: "+empseleccionado.getNombres(), DesktopNotify.SUCCESS);
                limpiartextbox();
            } catch (Exception e) {
                DesktopNotify.showDesktopMessage("Error","No se pudo modificar el registro", DesktopNotify.ERROR);
                //JOptionPane.showMessageDialog(null,"No se pudo modificar el Registro" );
                System.out.println("Error: "+e.getMessage());
            }
            
        }else{
           DesktopNotify.showDesktopMessage("Informacion","No se pudo modificar el registro", DesktopNotify.INFORMATION);
        }
    }
    
     public void guardar(){
    Empleados emp=new Empleados();
        try {
            if(txtnombre.getText().length()==0){
                txtnombre.requestFocus();
                
                DesktopNotify.showDesktopMessage("Info", "Debe ingresar el nombre", DesktopNotify.INFORMATION);
                
            }else if(jcpuesto.getSelectedItem()==null){
                 
                
                DesktopNotify.showDesktopMessage("Info", "Debe ingresar el telefono", DesktopNotify.INFORMATION);
            }else if(txtapellidos.getText().length()==0){
                txtapellidos.requestFocus();
            }else if(txtdui.getText().length()==0){
                txtdui.requestFocus();
            }
            
            else{
                //txtdescripcion.setToolTipText("Descripcion");
                
                emp.setNombres(txtnombre.getText());
                emp.setApellidos(txtapellidos.getText());
                emp.setDui(txtdui.getText());
                emp.setDireccion(txtdireccion.getText());
                emp.setTelefono(txttelefono.getText());
                emp.setCorreo(txtcorreo.getText());
                emp.setPuestos((Puestos)jcpuesto.getSelectedItem());
                emp.setEstado((String)jcestado.getSelectedItem());
                emp.setSalario(BigDecimal.valueOf(Double.parseDouble(txtsalario.getText())));
                
                empDao.Save(emp);
                cargarModeloTabla();
                limpiartextbox();
                DesktopNotify.showDesktopMessage("Exito","Se ha Agregado Correctamente el Registro: "+emp.getNombres(), DesktopNotify.SUCCESS);
                //JOptionPane.showMessageDialog(null,"Se ha Agregado Correctamente el Registro: "+bancos.getDescripcion() );
                
            }
            
        } catch (Exception e) {
           DesktopNotify.showDesktopMessage("Error","No se pudo Agregar el Registro", DesktopNotify.ERROR);
            System.out.println("error: "+e.getMessage());
        }
}

    
    public void eliminar(){
            if(empseleccionado!=null){
           try {
               empDao.Delete(empseleccionado);
           cargarModeloTabla();
           
            DesktopNotify.showDesktopMessage("Exito", "Registro eliminado Correctamente", DesktopNotify.INFORMATION);
          limpiartextbox();
           } catch (Exception e) {
                DesktopNotify.showDesktopMessage("Error", "No se pudo eliminar el registro", DesktopNotify.ERROR);
                System.out.println("Error: "+e.getMessage());
           }
           
           
       }else{
          DesktopNotify.showDesktopMessage("Info", "Debe Seleccionar un Registro", DesktopNotify.INFORMATION);
       }
       }
    
    public void buscar(){
        String parametro = this.txtbuscar.getText();
        List<Empleados> lista = empDao.listAllParameter(parametro);
         

        int numFila = lista.size();

        modeloTabla.setNumRows(numFila);

        for (int i = 0; i < lista.size(); i++) {
            Empleados p = lista.get(i);

             modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getNombres()+" "+p.getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getDui(), i, 2);
             modeloTabla.setValueAt(p.getDireccion(), i, 3);
              modeloTabla.setValueAt(p.getTelefono(), i, 4);
               modeloTabla.setValueAt(p.getPuestos(), i, 5);
                modeloTabla.setValueAt(p.getEstado(), i, 6);
                 modeloTabla.setValueAt(p.getSalario(), i, 7);
            
        }
     } 
    private void cargarModeloSegmento() {

        limpiarModelos(modelocombo); // limpiamos el modelo por las dudas
        // crea una List para recibir ls clases
        List<Puestos> lista = puDao.listAll();

        for (Puestos c : lista) {
            modelocombo.addElement(c);
        }

        modelocombo.setSelectedItem(null);

        if (puestoseleccionado != null) {
            for (Puestos c1 : lista) {

                if (puestoseleccionado.getPuesto() == c1.getPuesto()) {
                    modelocombo.setSelectedItem(c1.toString());

                }
            }

        }
    }
     private void limpiarModelos(DefaultComboBoxModel modelo) {
        int numFila = modelo.getSize(); // cantidad de filas
        if (numFila > 0) {
            // debe de ser i mayor o igual a cero
            for (int i = numFila - 1; i >= 0; i--) { // recorre de mayor a menor
                modelo.removeElementAt(i); // borra la fila
            }
        }
    }
     private void cargarModeloTabla() {
        limpiarTabla();
       
 
        List<Empleados> lista = empDao.listAll();//agregarlos registros a la lista
        int cantidadLista = lista.size();//cantidad de la lista
        modeloTabla.setRowCount(cantidadLista);//agregar la cantidad al modelo

        for (int i = 0; i < lista.size(); i++) {

            Empleados p = lista.get(i);

            modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getNombres()+" "+p.getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getDui(), i, 2);
             modeloTabla.setValueAt(p.getDireccion(), i, 3);
              modeloTabla.setValueAt(p.getTelefono(), i, 4);
               modeloTabla.setValueAt(p.getPuestos(), i, 5);
                modeloTabla.setValueAt(p.getEstado(), i, 6);
                 modeloTabla.setValueAt(p.getSalario(), i, 7);
                 modeloTabla.setValueAt(p.getCorreo(), i, 8);
           
        }

    }
      private void cargarColumnas() {
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nombre Completo");
        modeloTabla.addColumn("DUI");
        modeloTabla.addColumn("Direccion");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Puesto");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Salario");
         modeloTabla.addColumn("Correo");

    }
     
     
      public void limpiartextbox(){
         txtid.setText("");
         txtnombre.setText("");
         txtapellidos.setText("");
         txtcorreo.setText("");
         txtdireccion.setText("");
         txtdui.setText("");
         txtnomcompleto.setText("");
         txttelefono.setText("");
         
         modelocombo.setSelectedItem(null);
         empseleccionado=null;
         btneliminar.setEnabled(false);
         btnModificar.setEnabled(false);
         btnguardar.setEnabled(true);
     }
     
     
     
     
    private void DesControlesInicio(){
        btneliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        txtid.setEditable(false);
        txtnomcompleto.setEditable(false);
    }
    private void limpiarTabla() {
        int numFila = modeloTabla.getRowCount(); // cantidad de filas de la tabla
        if (numFila > 0) {
            // debe de ser i mayor o igual a cero
            for (int i = numFila - 1; i >= 0; i--) { // recorre de mayor a menor
                modeloTabla.removeRow(i); // borra la fila encontrada en la iteracion
            }
        }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        txtbuscar = new app.bolivia.swing.JCTextField();
        btnnuevo = new rojeru_san.complementos.ButtonHover();
        txtid = new app.bolivia.swing.JCTextField();
        txtnombre = new app.bolivia.swing.JCTextField();
        txtapellidos = new app.bolivia.swing.JCTextField();
        txtdui = new app.bolivia.swing.JCTextField();
        txtdireccion = new app.bolivia.swing.JCTextField();
        txttelefono = new app.bolivia.swing.JCTextField();
        txtcorreo = new app.bolivia.swing.JCTextField();
        txtsalario = new app.bolivia.swing.JCTextField();
        jcestado = new javax.swing.JComboBox<>();
        jcpuesto = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablapersonal = new rojeru_san.complementos.TableMetro();
        btnguardar = new rojeru_san.complementos.ButtonHover();
        btncontrato = new rojeru_san.complementos.ButtonHover();
        btneliminar = new rojeru_san.complementos.ButtonHover();
        btnModificar = new rojeru_san.complementos.ButtonHover();
        txtnomcompleto = new app.bolivia.swing.JCTextField();
        btnadd = new rojeru_san.complementos.ButtonHover();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(38, 86, 186));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Gestion de Empleados");

        txtbuscar.setToolTipText("Buscar Registros");
        txtbuscar.setPlaceholder("Realice una Busqueda");
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscarKeyTyped(evt);
            }
        });

        btnnuevo.setBackground(new java.awt.Color(0, 204, 204));
        btnnuevo.setText("Nuevo");
        btnnuevo.setToolTipText("Nuevo Registro");
        btnnuevo.setColorHover(new java.awt.Color(102, 255, 255));
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        txtid.setToolTipText("Codigo del Banco");
        txtid.setPlaceholder("ID");

        txtnombre.setToolTipText("Codigo del Banco");
        txtnombre.setPlaceholder("Nombre");
        txtnombre.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtnombrePropertyChange(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreKeyTyped(evt);
            }
        });

        txtapellidos.setToolTipText("Codigo del Banco");
        txtapellidos.setPlaceholder("Apellido");
        txtapellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtapellidosKeyReleased(evt);
            }
        });

        txtdui.setToolTipText("Codigo del Banco");
        txtdui.setPlaceholder("DUI");
        txtdui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtduiActionPerformed(evt);
            }
        });

        txtdireccion.setToolTipText("Codigo del Banco");
        txtdireccion.setPlaceholder("Direccion");

        txttelefono.setToolTipText("Codigo del Banco");
        txttelefono.setPlaceholder("Telefono");

        txtcorreo.setToolTipText("Codigo del Banco");
        txtcorreo.setPlaceholder("Correo");

        txtsalario.setToolTipText("Codigo del Banco");
        txtsalario.setPlaceholder("Salario");

        jcestado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Habilitado", "Deshabilitado" }));

        jcpuesto.setModel(modelocombo);

        tablapersonal.setModel(modeloTabla);
        tablapersonal.setColorBackgoundHead(new java.awt.Color(0, 61, 113));
        tablapersonal.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        //Este codigo se coloca en la tabla en su propiedad Post-Init-Code
        tablapersonal.getSelectionModel().addListSelectionListener( // capturamos la linea seleccionada

            new ListSelectionListener(){ // Instanciamos

                public void valueChanged (ListSelectionEvent event){ // evento de la tabla
                    if(!event.getValueIsAdjusting() && (tablapersonal.getSelectedRow()>=0) ) {
                        int filaSeleccionada = tablapersonal.getSelectedRow(); // tomamos la fila seleccionda
                        /*creamos el obj y le pasamos la fila seleccionada y la columna 1 xq ayi 
                        esta alojado el obj marca en el campo nombre....
                        */     
                        empseleccionado = (Empleados) modeloTabla.getValueAt(filaSeleccionada,0); 
                        // setPrivilegios(usuarioSelect.getPrivilegios());
                        //String descripcion1=eveselect.getProgramacion().getDescripcion();

                        txtid.setText(empseleccionado.getIdempleado().toString());
                        txtnombre.setText(empseleccionado.getNombres());
                        txtapellidos.setText(empseleccionado.getApellidos());
                        txtdui.setText(empseleccionado.getApellidos());
                        txtcorreo.setText(empseleccionado.getCorreo());
                        txttelefono.setText(empseleccionado.getTelefono());
                        txtsalario.setText(empseleccionado.getSalario().toString());
                        txtdireccion.setText(empseleccionado.getDireccion());
                        txtnomcompleto.setText(empseleccionado.getNombres()+" "+empseleccionado.getApellidos());

                        modelocombo.setSelectedItem(empseleccionado.getPuestos());
                        jcestado.setSelectedItem(empseleccionado.getEstado());

                        // LLenamos los textBoxs atraves del objeto ...

                        //abilitar boton para actualizar
                        btnguardar.setEnabled(false);
                        btnModificar.setEnabled(true);
                        btneliminar.setEnabled(true);

                    }

                }
            }

        );
        jScrollPane1.setViewportView(tablapersonal);

        btnguardar.setBackground(new java.awt.Color(0, 204, 204));
        btnguardar.setText("Guardar");
        btnguardar.setToolTipText("Nuevo Registro");
        btnguardar.setColorHover(new java.awt.Color(102, 255, 255));
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btncontrato.setBackground(new java.awt.Color(0, 204, 204));
        btncontrato.setText("Contrato");
        btncontrato.setToolTipText("Nuevo Registro");
        btncontrato.setColorHover(new java.awt.Color(102, 255, 255));
        btncontrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncontratoActionPerformed(evt);
            }
        });

        btneliminar.setBackground(new java.awt.Color(0, 204, 204));
        btneliminar.setText("Eliminar");
        btneliminar.setToolTipText("Nuevo Registro");
        btneliminar.setColorHover(new java.awt.Color(102, 255, 255));
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(0, 204, 204));
        btnModificar.setText("Modificar");
        btnModificar.setToolTipText("Nuevo Registro");
        btnModificar.setColorHover(new java.awt.Color(102, 255, 255));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        txtnomcompleto.setToolTipText("Buscar Registros");
        txtnomcompleto.setPlaceholder("Nombre Completo");
        txtnomcompleto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnomcompletoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomcompletoKeyTyped(evt);
            }
        });

        btnadd.setBackground(new java.awt.Color(0, 204, 204));
        btnadd.setText("Ag. Emp");
        btnadd.setToolTipText("Nuevo Registro");
        btnadd.setColorHover(new java.awt.Color(102, 255, 255));
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtapellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                            .addComponent(txtid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                    .addComponent(txtdireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtdui, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                                .addGap(41, 41, 41)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtcorreo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtsalario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jcpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jcestado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btncontrato, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtnomcompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnomcompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncontrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        limpiarTabla();
        buscar();
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyTyped
        if(txtbuscar.getText().length()>=2){
            limpiarTabla();
            buscar();
        }
    }//GEN-LAST:event_txtbuscarKeyTyped

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        limpiartextbox();
       
        
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btncontratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncontratoActionPerformed
       if(empseleccionado!=null){
           frmContrato contrato=new frmContrato();
           contrato.getEmpleado(empseleccionado);
           contrato.setVisible(true);
       }else{
           DesktopNotify.showDesktopMessage("Informacion","Debes seleccionar un empleado", DesktopNotify.INFORMATION);
       }
    }//GEN-LAST:event_btncontratoActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtnomcompletoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomcompletoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnomcompletoKeyReleased

    private void txtnomcompletoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomcompletoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnomcompletoKeyTyped

    private void txtnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyTyped
      
    }//GEN-LAST:event_txtnombreKeyTyped

    private void txtnombrePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtnombrePropertyChange
        
    }//GEN-LAST:event_txtnombrePropertyChange

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        String nombre = txtnombre.getText();
        txtnomcompleto.setText(nombre);
       
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtapellidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapellidosKeyReleased
String nombre = txtnombre.getText();
String apellido=txtapellidos.getText();

        txtnomcompleto.setText(nombre+" "+apellido);
    }//GEN-LAST:event_txtapellidosKeyReleased

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        Pagomensual pagomensual=new Pagomensual();
        PagoMensualDao pagomDao=new PagoMensualDao();
        PlanillaDao pldao=new PlanillaDao();
        Planillas plan=new Planillas();
        //List<Planillas> listplanilla=pldao.buscarporID(1);
        List<Planillas> listplanilla=pldao.buscarUltimoRegistro();
        if(listplanilla.size()>0){
            //obtener primer planilla descendente
            int ultimo=0;
            //recorrer todas las planillas
            for(Planillas pl:listplanilla){
                //incrementar el valor
                ultimo++;
                //cuando el valor de incremental sea 1 entonces agregar esa planilla
                if(ultimo==1){
                    plan=pl; 
                }
               
            }
        }else{
             DesktopNotify.showDesktopMessage("Info", "No existe la planilla");
        }
        if(empseleccionado!=null){
            try {
                 Double salario=Double.parseDouble(empseleccionado.getSalario().toString());
           Double isss=salario*0.075;
           Double afp=salario*0.0675;
           Double salarioneto=salario-(isss+afp);
           pagomensual.setEmpleados(empseleccionado);
           pagomensual.setPlanillas(plan);
           pagomensual.setIsss(BigDecimal.valueOf(isss));
           pagomensual.setAfp(BigDecimal.valueOf(afp));
           pagomensual.setSalarionetomensual(BigDecimal.valueOf(salarioneto));
           pagomDao.Save(pagomensual);
          DesktopNotify.showDesktopMessage("Exito", "Registro Agregado correctamente a la Planilla N. " +plan.getIdplanilla() ,DesktopNotify.SUCCESS);
            } catch (Exception e) {
                System.out.println("error: "+e.getMessage());
                DesktopNotify.showDesktopMessage("Error", "No se pudo agregar el empleado a la planilla: "+empseleccionado.getNombres());
            }
            
           
           
       }else{
            EmpleadoDao empdao=new EmpleadoDao();
            List<Empleados>emphabilitados=empdao.listAll();
           if(emphabilitados.size()>0){
               try {
                     for(Empleados empfind:emphabilitados){
                         if(empfind.getEstado().equalsIgnoreCase("Habilitado")){
                             System.out.println("empfind: "+empfind.getNombres());
                    Double salario=Double.parseDouble(empfind.getSalario().toString());
           Double isss=salario*0.075;
           Double afp=salario*0.0675;
           Double salarioneto=salario-(isss+afp);
           pagomensual.setEmpleados(empfind);
           pagomensual.setPlanillas(plan);
           pagomensual.setIsss(BigDecimal.valueOf(isss));
           pagomensual.setAfp(BigDecimal.valueOf(afp));
           pagomensual.setSalarionetomensual(BigDecimal.valueOf(salarioneto));
           pagomDao.Save(pagomensual);
          DesktopNotify.showDesktopMessage("Exito", "Registro Agregado correctamente: "+empfind.getNombres(),DesktopNotify.SUCCESS);
                         }                   
       
               }
                      DesktopNotify.showDesktopMessage("Exito", "Registros Agregado correctamente",DesktopNotify.SUCCESS);
                   
               } catch (Exception e) {
                   DesktopNotify.showDesktopMessage("Error", "No se pudieron Ingresar los registros a la planilla",DesktopNotify.ERROR);
                   System.out.println("error: "+e.getMessage());
               }
             
           }else{
               DesktopNotify.showDesktopMessage("Info", "No hay empleados Habilitados",DesktopNotify.INFORMATION);
           }
       }
    }//GEN-LAST:event_btnaddActionPerformed

    private void txtduiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtduiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtduiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.ButtonHover btnModificar;
    private rojeru_san.complementos.ButtonHover btnadd;
    private rojeru_san.complementos.ButtonHover btncontrato;
    private rojeru_san.complementos.ButtonHover btneliminar;
    private rojeru_san.complementos.ButtonHover btnguardar;
    private rojeru_san.complementos.ButtonHover btnnuevo;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcestado;
    private javax.swing.JComboBox<Puestos> jcpuesto;
    private rojeru_san.complementos.TableMetro tablapersonal;
    private app.bolivia.swing.JCTextField txtapellidos;
    private app.bolivia.swing.JCTextField txtbuscar;
    private app.bolivia.swing.JCTextField txtcorreo;
    private app.bolivia.swing.JCTextField txtdireccion;
    private app.bolivia.swing.JCTextField txtdui;
    private app.bolivia.swing.JCTextField txtid;
    private app.bolivia.swing.JCTextField txtnombre;
    private app.bolivia.swing.JCTextField txtnomcompleto;
    private app.bolivia.swing.JCTextField txtsalario;
    private app.bolivia.swing.JCTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
