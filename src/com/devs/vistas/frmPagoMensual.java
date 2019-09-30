
package com.devs.vistas;

import com.devs.dao.PagoMensualDao;
import com.devs.dao.PlanillaDao;
import com.devs.entities.Pagomensual;
import com.devs.entities.Planillas;
import ds.desktop.notify.DesktopNotify;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DiegoArevalo
 */
public class frmPagoMensual extends javax.swing.JFrame {
    DefaultComboBoxModel<Planillas> modelocombo = new DefaultComboBoxModel<Planillas>();
    Planillas plnilla=null;
    PlanillaDao plDao=new PlanillaDao();
    Pagomensual pmensualselect=null;
    PagoMensualDao pmDao=new PagoMensualDao();
    
    
DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public frmPagoMensual() {
         cargarColumnas();
        cargarModeloTabla();
        cargarModeloSegmento();
        initComponents();
         this.setResizable(false);
         DesControlesInicio();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(1);
        this.setTitle("Gestion de Pagos Mensuales");
    }
    public void eliminar(){
            if(pmensualselect!=null){
           try {
               pmDao.Delete(pmensualselect);
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
    //cargar modelo tabla
      private void cargarModeloTabla() {
        limpiarTabla();
       
 
        List<Pagomensual> lista = pmDao.listAll();//agregarlos registros a la lista
        int cantidadLista = lista.size();//cantidad de la lista
        modeloTabla.setRowCount(cantidadLista);//agregar la cantidad al modelo

        for (int i = 0; i < lista.size(); i++) {

            Pagomensual p = lista.get(i);

            modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getEmpleados().getNombres()+" "+p.getEmpleados().getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getPlanillas().getIdplanilla(), i, 2);
            modeloTabla.setValueAt(p.getEmpleados().getSalario(), i, 3);
            modeloTabla.setValueAt(p.getIsss(), i, 4);
            modeloTabla.setValueAt(p.getAfp(), i, 5);
            modeloTabla.setValueAt(p.getSalarionetomensual(), i, 6);
            
           
        }
    }
      
      private void cargarModeloSegmento() {

        limpiarModelos(modelocombo); // limpiamos el modelo por las dudas
        // crea una List para recibir ls clases
        List<Planillas> lista = plDao.listAll();

        for (Planillas c : lista) {
            modelocombo.addElement(c);
        }

        modelocombo.setSelectedItem(null);

        if (plnilla != null) {
            for (Planillas c1 : lista) {

                if (plnilla.getIdplanilla() == c1.getIdplanilla()) {
                    modelocombo.setSelectedItem(c1.toString()+" desde: "+c1.getDesdeFecha()+"Hasta: "+c1.getHastaFecha());

                }
            }

        }
    }
      
       //cargando columnas
    private void cargarColumnas() {
        modeloTabla.addColumn("IdPago");
        modeloTabla.addColumn("Nombre Completo");
        modeloTabla.addColumn("Id Planilla");
        modeloTabla.addColumn("Salario Normal");
        modeloTabla.addColumn("ISSS");
        modeloTabla.addColumn("AFP");
        modeloTabla.addColumn("Salario Neto");

    }
      
      
      
      //limpiar tabla
      private void limpiarTabla() {
        int numFila = modeloTabla.getRowCount(); // cantidad de filas de la tabla
        if (numFila > 0) {
            // debe de ser i mayor o igual a cero
            for (int i = numFila - 1; i >= 0; i--) { // recorre de mayor a menor
                modeloTabla.removeRow(i); // borra la fila encontrada en la iteracion
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
      public void limpiartextbox(){
         
         btnEliminar.setEnabled(false);
     }
    
     private void DesControlesInicio(){
        btnEliminar.setEnabled(false);
        
    }
     public void buscar(Planillas pl){
       List<Pagomensual> lista = pmDao.buscarporPlanilla(pl.getIdplanilla());
       int numFila = lista.size();

        modeloTabla.setNumRows(numFila);
        
         for (int i = 0; i < lista.size(); i++) {
            Pagomensual p = lista.get(i);

           modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getEmpleados().getNombres()+" "+p.getEmpleados().getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getPlanillas().getIdplanilla(), i, 2);
            modeloTabla.setValueAt(p.getEmpleados().getSalario(), i, 3);
            modeloTabla.setValueAt(p.getIsss(), i, 4);
            modeloTabla.setValueAt(p.getAfp(), i, 5);
            modeloTabla.setValueAt(p.getSalarionetomensual(), i, 6);
            
        }
         
     }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEliminar = new rojeru_san.complementos.ButtonHover();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablabanco = new rojeru_san.complementos.TableMetro();
        jcplanilla = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("Eliminar Registro");
        btnEliminar.setColorHover(new java.awt.Color(255, 51, 51));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tablabanco.setModel(modeloTabla);
        tablabanco.setColorBackgoundHead(new java.awt.Color(0, 61, 113));
        tablabanco.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        //Este codigo se coloca en la tabla en su propiedad Post-Init-Code
        tablabanco.getSelectionModel().addListSelectionListener( // capturamos la linea seleccionada

            new ListSelectionListener(){ // Instanciamos

                public void valueChanged (ListSelectionEvent event){ // evento de la tabla
                    if(!event.getValueIsAdjusting() && (tablabanco.getSelectedRow()>=0) ) {
                        int filaSeleccionada = tablabanco.getSelectedRow(); // tomamos la fila seleccionda
                        /*creamos el obj y le pasamos la fila seleccionada y la columna 1 xq ayi 
                        esta alojado el obj marca en el campo nombre....
                        */     
                        pmensualselect = (Pagomensual) modeloTabla.getValueAt(filaSeleccionada,0); 
                        // setPrivilegios(usuarioSelect.getPrivilegios());
                        //String descripcion1=eveselect.getProgramacion().getDescripcion();

                        // LLenamos los textBoxs atraves del objeto ...

                    }

                }
            }

        );
        jScrollPane1.setViewportView(tablabanco);

        jcplanilla.setModel(modelocombo);
        jcplanilla.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcplanillaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(jcplanilla, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcplanilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jcplanillaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcplanillaItemStateChanged
        Planillas pmen=(Planillas)jcplanilla.getSelectedItem();
        if(pmen!=null){
             buscar(pmen);
        }
        
       
    }//GEN-LAST:event_jcplanillaItemStateChanged

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
            java.util.logging.Logger.getLogger(frmPagoMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPagoMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPagoMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPagoMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPagoMensual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.ButtonHover btnEliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<Planillas> jcplanilla;
    private rojeru_san.complementos.TableMetro tablabanco;
    // End of variables declaration//GEN-END:variables
}
