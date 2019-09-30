
package com.devs.vistas;

import com.devs.dao.ContratoDao;
import com.devs.dao.DevengoDao;
import com.devs.dao.ObligacionesDao;
import com.devs.entities.Contrato;
import com.devs.entities.Devengos;
import com.devs.entities.Empleados;
import com.devs.entities.Obligaciones;
import ds.desktop.notify.DesktopNotify;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import rojerusan.RSNotifyFade;

//crear word
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author DiegoArevalo
 */
public class frmContrato extends javax.swing.JFrame {
    //crear word
     private XWPFDocument document=new XWPFDocument();
    
    
    
Empleados empleados=null;
    Contrato contratoselect=null;
    ContratoDao contDao=new ContratoDao();
DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    /**
     * Creates new form frmContrato
     */
    public frmContrato() {
        cargarColumnas();
        cargarModeloTabla();
        initComponents();
         this.setResizable(false);
         DesControlesInicio();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(1);
        this.setTitle("Registro y Gestion de Contratos");
    }
     private void DesControlesInicio(){
        btnEliminar.setEnabled(false);
        btnmodificar.setEnabled(false);
        
        //txtid.setEditable(false);
    }
     //crear word con el contrato
     public void  crearword(){
         //asignar filtro para generar archivo en ubicacion
      javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Microsoft Word 2019", "docx");   
          final JFileChooser miArchivo=new JFileChooser();
        miArchivo.setFileFilter(filtroWord);
        //obtener evento del dialogo(respuesta)
        int aceptar=miArchivo.showSaveDialog(null);
        miArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //si selecciona aceptar en el dialog
        if(aceptar==JFileChooser.APPROVE_OPTION){
             File fileWord=miArchivo.getSelectedFile();
            String nombre=fileWord.getName();
            if(nombre.indexOf('.')==-1){
                nombre+=".docx";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }
            try {
                //creando flujo de salida de informacion
                 FileOutputStream output=new FileOutputStream(fileWord);
                 XWPFParagraph paragraphTitulo=document.createParagraph();
                XWPFRun runTitulo=paragraphTitulo.createRun();
                
                //agregando titulo y sus propiedades
                paragraphTitulo.setAlignment(ParagraphAlignment.CENTER);
                runTitulo.setBold(true);
                runTitulo.setFontSize(15);
                runTitulo.setUnderline(UnderlinePatterns.WORDS);
                runTitulo.setText("CONTRATO INDIVIDUAL DE TRABAJO");
                runTitulo.setColor("2f66f2");
                runTitulo.addBreak();
                
                 XWPFParagraph paragraph=document.createParagraph();
                XWPFRun run=paragraph.createRun();
                 run.setText("GENERALES DEL CONTRATANTE PATRONAL              GENERALES DEL TRABAJADOR                                ");
                 run.addBreak();
                run.setText("Nombre: "+contratoselect.getEmpleados().getPuestos().getArea().getGerencia().getNombre()+"                           Nombre: "+contratoselect.getEmpleados().getNombres()+" "+contratoselect.getEmpleados().getApellidos());
                run.addBreak();
                 run.setText("  Direccion: Chalatenango                                     Direccion: "+contratoselect.getEmpleados().getDireccion());
                run.addBreak();
                 run.setText("  DUI: 00023455-3                                     DUI: "+contratoselect.getEmpleados().getDui());
                run.addBreak();
                 run.setText("  Cargo: Gerente de "+contratoselect.getEmpleados().getPuestos().getArea().getNombre()+"                                     Sexo: "+contratoselect.getEmpleados().getPuestos().getPuesto());
                run.addBreak();
                run.setText("   Yo  : "+contratoselect.getEmpleados().getPuestos().getArea().getGerencia().getNombre()+ "  "
                         + " , de las generales antes expresadas, actuando el primero en calidad  de  Representante  Patronal  de       \n" +
"       DEVS SOFTWARE-----------, del  domicilio  de  Chalatenango; y en segundo, actuando por sí, quienes en  lo  sucesivo  seremos  denominado  la \n" +
"       'Empresa Devs Software' y el 'Empleado con el nombre: "+contratoselect.getEmpleados().getNombres()+" "+contratoselect.getEmpleados().getApellidos()+"' respectivamente, en el carácter en que actuamos, hemos  convenido                                                                                                                          \n" +
"       en celebrar el  presente  contrato  individual  de  trabajo  sujeto  a  las  estipulaciones                                                                                                                          \n" +
"       siguientes: "  );
                 run.addBreak();
                ObligacionesDao obdao=new ObligacionesDao();
                List<Obligaciones> lista = obdao.BuscarporContrato(contratoselect.getIdcontrato());//agregarlos registros a la lista
        //int cantidadLista = lista.size();//cantidad de la lista
        //modeloTabla.setRowCount(cantidadLista);//agregar la cantidad al modelo

        for (int i = 0; i < lista.size(); i++) {

            Obligaciones p = lista.get(i);
run.setText(p.getIdobligacion()+" : "+p.getDescripcion() );
run.addBreak();
        }
         run.addBreak();
                 run.setText(" I. DEL TRABAJO.  El empleado prestará sus servicios en forma exclusiva a la Empresa, en las                                                                                                                          \n" +
"       oficinas, plantas e instalaciones que ésta tiene establecidas en la dirección:                                                                                                                                       \n" +
"       "+contratoselect.getLugartrabajo()+"-------------------------------------------------------------------------                                                                                                                          \n" +
"                                                                                                                                                                                                                            \n" +
"       Para la prestacion de servicios; siendo entendido que su cargo asignado será de:                                                                                                                                     \n" +
"       "+contratoselect.getEmpleados().getPuestos().getPuesto()+"-------------------------------. Además de las obligaciones que le impongan las "
        + "leyes laborales y sus reglamentos, el contrato colectivo, si lo hubiere, y el reglamento  interno                                                                                                                          \n" +
"       de trabajo, tendrá como obligaciones propias de su cargo las siguientes:                                                                                                                                             \n" +
"                                                                                                                                                                                                                            \n" +
"                                                                                                                                                                                                                            \n" +
"       Es expresamente entendido que el Empleado no podrá contratar o sub-contratar a otra persona                                                                                                                          \n" +
"       ya sea empleada o no del Patrono, para que éstas  presten  parte  o  la  totalidad  de  los                                                                                                                          \n" +
"       servicios objeto del presente contrato. ");
                 run.addBreak();
//                 run.setText("        II. DEL CONTRATO. "+contrato.getDevengos()+" .                                                                                                                                                  ");
run.addBreak();
run.setText("        III. DEL HORARIO.  El Número de horas que laborará  el  empleado  dentro  o  fuera  de  las                                                                                                                          \n" +
"       instalaciones será de ocho horas en la jornada diurna  y  de  siete  horas  en  la  jornada                                                                                                                          \n" +
"       nocturna. Podra trabajar tiempo extraordinario previa autorización del jefe de departamento                                                                                                                          \n" +
"       correspondiente.");
run.addBreak();
run.setText("        IV. DEL SALARIO: FORMA, PERIODO Y LUGAR DE PAGO.                                                                                                                                                                     \n" +
"       El pago del salario se hará en moneda de curso legal, por medio de depósito  en  cuenta  de                                                                                                                          \n" +
"       ahorro en uno de los Bancos del Sistema Financiero del país, por  quincenas  vencidas,  los                                                                                                                          \n" +
"       días quince y último de cada mes.");
run.addBreak();
run.setText("        V.  DE  LAS  OBLIGACIONES  DEL  EMPLEADO.   El  empleado  estará  obligado  a  observar  las                                                                                                                         \n" +
"       disposiciones del Reglamento Interno de la Empresa, a cumplir con las obligaciones  que  le                                                                                                                          \n" +
"       imponen las leyes laborales, y a prestar sus servicios  en  la  forma  que  la  Empresa  le                                                                                                                          \n" +
"       indique. Adicionalmente estará obligado a prestar su auxilio, servicio  o  colaboración  en                                                                                                                          \n" +
"       cualquier hora y tiempo que se necesite sin retribución obligatoria de la  Empresa,  cuando                                                                                                                          \n" +
"       por siniestro o riesgo inmediato, peligre la vida de  sus  jefes,  compañeros  de  trabajo,                                                                                                                          \n" +
"       materiales, edificios o los intereses de la Empresa en general.");
run.addBreak();
run.setText("        VI. DESIGNACION ESPECIAL. Para los efectos legales, a continuación se detallan las personas                                                                                                                          \n" +
"       y su parentesco, que dependen económicamente del Empleado:                                                                                                                                                         "
        + "                                         ");
run.addBreak();
run.setText("VII.En el presente contrato se entenderán incluidos, según el caso los devengos pactados entre los cuales son: ");
 DevengoDao dvdao=new DevengoDao();
                List<Devengos> listad = dvdao.Buscarporcontrato(contratoselect.getIdcontrato());//agregarlos registros a la lista
        //int cantidadLista = lista.size();//cantidad de la lista
        //modeloTabla.setRowCount(cantidadLista);//agregar la cantidad al modelo

        for (int i = 0; i < listad.size(); i++) {

            Devengos p = listad.get(i);
run.setText(p.getIddevengo()+" : "+p.getDescripcion() );
run.addBreak();
        }
run.addBreak();
run.setText("        VIII. Este contrato sustituye cualquier otro convenio individual de trabajo anterior, ya  sea                                                                                                                          \n" +
"       escrito o verbal, que haya estado vigente entre la Empresa y el Empleado.                                                                                                                                            \n" +
"                                                                                                                                                                                                                            \n" +
"       En fe de lo cual firmamos el presente contrato por triplicado en San Salvador, en la  fecha                                                                                                                          \n" +
"       ---"+contratoselect.getFechainicio()+"--.");
run.addBreak();
        
                run.addBreak();
run.setText(" F.___________________________________        "+" F.___________________________________ ");
run.addBreak();
run.setText("       Jefe Area.                             "+contratoselect.getEmpleados().getNombres()+" "+contratoselect.getEmpleados().getApellidos());

                document.write(output);
                output.close();
                DesktopNotify.showDesktopMessage("Exito", "El Contrato Individual de Trabajo ha sido Creado con Exito", DesktopNotify.SUCCESS);
            
                
                
            } catch (Exception e) {
                DesktopNotify.showDesktopMessage("Error", "No se pudo generar el contrato", DesktopNotify.ERROR);
                System.out.println(e.getMessage().toString());
                
            }
            finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                    }
                    else{
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                    }
                    
                } catch (IOException e) {
                    Logger.getLogger(frmContrato.class.getName()).log(Level.SEVERE, null, e);
                }
                
            }
            
        }
         
         
     }
     
     
     
     
     
     private void cargarModeloTabla() {
        limpiarTabla();
       
 
        List<Contrato> lista = contDao.listAll();//agregarlos registros a la lista
        int cantidadLista = lista.size();//cantidad de la lista
        modeloTabla.setRowCount(cantidadLista);//agregar la cantidad al modelo

        for (int i = 0; i < lista.size(); i++) {

            Contrato p = lista.get(i);
            System.out.println("hola "+p.getLugartrabajo());

            modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getEmpleados().getNombres()+" "+p.getEmpleados().getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getFechainicio(), i, 2);
            modeloTabla.setValueAt(p.getFinalizacion(), i, 3);
            modeloTabla.setValueAt(p.getExtendido(), i, 4);
            modeloTabla.setValueAt(p.getLugartrabajo(), i, 5);
           
        }

    }
     
     public void modificar() {
        if(contratoselect!=null){
            try {
                
                    Date ddesde;
            Date dhasta;
               try {
                Calendar cal;
                int d, m, a;
                //fecha ingreso
                cal = jinicio.getCalendar();
                d = cal.get(Calendar.DAY_OF_MONTH);
                m = cal.get(Calendar.MONTH);
                a = cal.get(Calendar.YEAR) - 1900;

                ddesde = new java.sql.Date(a, m, d);
                //fecha salida
                cal = jfin.getCalendar();
                d = cal.get(Calendar.DAY_OF_MONTH);
                m = cal.get(Calendar.MONTH);
                a = cal.get(Calendar.YEAR) - 1900;

                dhasta = new java.sql.Date(a, m, d);
                
                   
            } catch (Exception e) {
                DesktopNotify.showDesktopMessage("Error", "Hay Errores en las fechas", DesktopNotify.ERROR);
                return;
            }
              // plaselect.setCodigoplanilla(txtid.getText());
                contratoselect.setLugartrabajo(txtlugar.getText());
                contratoselect.setFechainicio(ddesde);
                contratoselect.setFinalizacion(dhasta);
                contratoselect.setExtendido(txtextendido.getText());
                contDao.Update(contratoselect);
                cargarModeloTabla();
                new rojerusan.RSNotifyFade("Proceso Completado", "Registro Modificado Correctamente a: "+contratoselect.getEmpleados().getNombres(), 6, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
                //JOptionPane.showMessageDialog(null,"El Registro fue Modificado Correctamente a: "+cargoselect.getDescripcion() );
                limpiartextbox();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null,"No se pudo modificar el Registro" );
                 DesktopNotify.showDesktopMessage("Error", "No se pudo Modificar el Registro", DesktopNotify.ERROR);
                System.out.println("Error: "+e.getMessage());
            }
            
        }else{
            //JOptionPane.showMessageDialog(null,"Debe de Seleccionar un Registro" );
            DesktopNotify.showDesktopMessage("Informe", "Debes seleccionar un Registro", DesktopNotify.INFORMATION);
        }
    }
     
     
     
      public void buscar(){
        String parametro = this.txtbuscar.getText();
        List<Contrato> lista = contDao.listAllParameter(parametro);
         

        int numFila = lista.size();

        modeloTabla.setNumRows(numFila);

        for (int i = 0; i < lista.size(); i++) {
            Contrato p = lista.get(i);

           modeloTabla.setValueAt(p, i, 0);
            modeloTabla.setValueAt(p.getEmpleados().getNombres()+" "+p.getEmpleados().getApellidos(), i, 1);
            modeloTabla.setValueAt(p.getFechainicio(), i, 2);
            modeloTabla.setValueAt(p.getFinalizacion(), i, 3);
            modeloTabla.setValueAt(p.getExtendido(), i, 4);
            modeloTabla.setValueAt(p.getLugartrabajo(), i, 5);
            
        }
     } 
       private void cargarColumnas() {
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nombre Completo");
        modeloTabla.addColumn("Fecha Inicio");
        modeloTabla.addColumn("Fecha Finalizacion");
        modeloTabla.addColumn("Extendido");
        modeloTabla.addColumn("Lugar Trabajo");
        

    }

   public void getEmpleado(Empleados emp){
       this.empleados=emp;
       txtnom.setText(empleados.getNombres()+" "+empleados.getApellidos());
       txtnom.setEditable(false);
       
       
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
     

     
     
     
     
     public void guardar(){
           Date ddesde=null;
           Date dhasta=null;
    Contrato contrato=new Contrato();
        try {
            if(jinicio.getDate()==null){
                jinicio.requestFocus();
                new rojerusan.RSNotifyFade("Agregar", "Debes agregar ID", 6, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
               // DesktopNotify.showDesktopMessage("Registros Incompletos", "Debes Ingresar una Descripcion", DesktopNotify.ERROR);
            }else  {
              
                //Fecha
                  //Date ddesde;
            //Date dhasta;
            
            //probando calculo de numero de semana
//            DateTime dateTime1 = new DateTime((DateCell) ddesde);
//DateTime dateTime2 = new DateTime((DateCell) dhasta);
//
//int weeks = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
           
               try {
                Calendar cal;
                int d, m, a;
                //fecha ingreso
                cal = jinicio.getCalendar();
                d = cal.get(Calendar.DAY_OF_MONTH);
                m = cal.get(Calendar.MONTH);
                a = cal.get(Calendar.YEAR) - 1900;

                ddesde = new java.sql.Date(a, m, d);
                //fecha salida
                cal = jfin.getCalendar();
                d = cal.get(Calendar.DAY_OF_MONTH);
                m = cal.get(Calendar.MONTH);
                a = cal.get(Calendar.YEAR) - 1900;

                dhasta = new java.sql.Date(a, m, d);
            } catch (Exception e) {
                DesktopNotify.showDesktopMessage("Error", "Hay Errores en las fechas", DesktopNotify.ERROR);
                return;
            }
           
                contrato.setEmpleados(empleados);
                contrato.setExtendido(txtextendido.getText());
                contrato.setLugartrabajo(txtlugar.getText());
                contrato.setFechainicio(ddesde);
                contrato.setFinalizacion(dhasta);
               
                contDao.Save(contrato);
                cargarModeloTabla();
                limpiartextbox();
                new rojerusan.RSNotifyFade("Proceso Completo", "Registro Agregado exitosamente: ", 6, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
                //JOptionPane.showMessageDialog(null,"Se ha Agregado Correctamente el Registro: "+cargos.getDescripcion() );
                
            }
            
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null,"No se pudo Agregar el Registro" );
             DesktopNotify.showDesktopMessage("Registros Incompletos", "No se pudo Agregar el Registro", DesktopNotify.ERROR);
            System.out.println("error: "+e.getMessage());
        }
}
     
     
      public void limpiartextbox(){
        
         txtextendido.setText("");
txtlugar.setText("");
jinicio.setDate(null);
jfin.setDate(null);
         contratoselect=null;
         btnEliminar.setEnabled(false);
         btnmodificar.setEnabled(false);
         btnguardar.setEnabled(true);
     }
      public void eliminar(){
            if(contratoselect!=null){
           try {
               contDao.Delete(contratoselect);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jinicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtextendido = new app.bolivia.swing.JCTextField();
        txtlugar = new app.bolivia.swing.JCTextField();
        txtnom = new app.bolivia.swing.JCTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablabanco = new rojeru_san.complementos.TableMetro();
        btnguardar = new rojeru_san.complementos.ButtonHover();
        btnmodificar = new rojeru_san.complementos.ButtonHover();
        txtbuscar = new app.bolivia.swing.JCTextField();
        btnEliminar = new rojeru_san.complementos.ButtonHover();
        btnnuevo = new rojeru_san.complementos.ButtonHover();
        btnobligaciones = new rojeru_san.complementos.ButtonHover();
        btndevengos = new rojeru_san.complementos.ButtonHover();
        jfin = new com.toedter.calendar.JDateChooser();
        btnimprimir = new rojeru_san.complementos.ButtonHover();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jinicio.setForeground(new java.awt.Color(102, 102, 255));
        jinicio.setToolTipText("Periodo desde");
        jinicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jinicioPropertyChange(evt);
            }
        });

        jLabel1.setText("Fecha Inicio");

        jLabel2.setText("Fecha Finalizacion");

        txtextendido.setToolTipText("Ingrese la Descripcion");
        txtextendido.setPlaceholder("Extendido");
        txtextendido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtextendidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtextendidoKeyTyped(evt);
            }
        });

        txtlugar.setToolTipText("Ingrese la Descripcion");
        txtlugar.setPlaceholder("Lugar de Trabajo");
        txtlugar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtlugarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtlugarKeyTyped(evt);
            }
        });

        txtnom.setToolTipText("Ingrese la Descripcion");
        txtnom.setPlaceholder("Nombre Completo");
        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnomKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnomKeyTyped(evt);
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
                        contratoselect = (Contrato) modeloTabla.getValueAt(filaSeleccionada,0); 
                        // setPrivilegios(usuarioSelect.getPrivilegios());
                        //String descripcion1=eveselect.getProgramacion().getDescripcion();

                        txtextendido.setText(contratoselect.getExtendido());
                        txtlugar.setText(contratoselect.getLugartrabajo());
                        jinicio.setDate(contratoselect.getFechainicio());
                        jfin.setDate(contratoselect.getFinalizacion());

                        // LLenamos los textBoxs atraves del objeto ...

                        //abilitar boton para actualizar
                        btnguardar.setEnabled(false);
                        btnmodificar.setEnabled(true);
                        btnEliminar.setEnabled(true);

                    }

                }
            }

        );
        jScrollPane1.setViewportView(tablabanco);

        btnguardar.setBackground(new java.awt.Color(0, 0, 204));
        btnguardar.setText("Guardar");
        btnguardar.setToolTipText("Guardar Registro");
        btnguardar.setColorHover(new java.awt.Color(51, 51, 255));
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btnmodificar.setBackground(new java.awt.Color(0, 0, 0));
        btnmodificar.setForeground(new java.awt.Color(0, 0, 0));
        btnmodificar.setText("Modificar");
        btnmodificar.setToolTipText("Modificar Registro");
        btnmodificar.setColorHover(new java.awt.Color(255, 255, 102));
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

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

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("Eliminar Registro");
        btnEliminar.setColorHover(new java.awt.Color(255, 51, 51));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        btnobligaciones.setBackground(new java.awt.Color(0, 204, 204));
        btnobligaciones.setText("Obligaciones");
        btnobligaciones.setToolTipText("Nuevo Registro");
        btnobligaciones.setColorHover(new java.awt.Color(102, 255, 255));
        btnobligaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnobligacionesActionPerformed(evt);
            }
        });

        btndevengos.setBackground(new java.awt.Color(0, 204, 204));
        btndevengos.setText("Devengos");
        btndevengos.setToolTipText("Nuevo Registro");
        btndevengos.setColorHover(new java.awt.Color(102, 255, 255));
        btndevengos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndevengosActionPerformed(evt);
            }
        });

        jfin.setForeground(new java.awt.Color(102, 102, 255));
        jfin.setToolTipText("Periodo desde");
        jfin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jfinPropertyChange(evt);
            }
        });

        btnimprimir.setBackground(new java.awt.Color(0, 204, 204));
        btnimprimir.setText("Imprimir");
        btnimprimir.setToolTipText("Nuevo Registro");
        btnimprimir.setColorHover(new java.awt.Color(102, 255, 255));
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtextendido, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jfin, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtlugar, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnobligaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(btndevengos, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnobligaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndevengos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(26, 26, 26))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jfin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(26, 26, 26)
                            .addComponent(txtextendido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(txtlugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jinicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jinicioPropertyChange

    }//GEN-LAST:event_jinicioPropertyChange

    private void txtextendidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtextendidoKeyPressed
//        if(areaseleccionada==null){
//            if(evt.getKeyCode()==evt.VK_ENTER){
//                guardar();
//            }
//        }else if(areaseleccionada!=null){
//            if(evt.getKeyCode()==evt.VK_ENTER){
//                modificar();
//            }
//        }
    }//GEN-LAST:event_txtextendidoKeyPressed

    private void txtextendidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtextendidoKeyTyped

    }//GEN-LAST:event_txtextendidoKeyTyped

    private void txtlugarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtlugarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlugarKeyPressed

    private void txtlugarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtlugarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlugarKeyTyped

    private void txtnomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnomKeyPressed

    private void txtnomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnomKeyTyped

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
      guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnmodificarActionPerformed

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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        limpiartextbox();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnobligacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnobligacionesActionPerformed
       if(contratoselect!=null){
           frmObligacion frm=new frmObligacion();
           frm.getContrato(contratoselect);
           this.dispose();
           frm.setVisible(true);
           
       }else{
            new rojerusan.RSNotifyFade("Info", "Debes Seleccionar un Contrato ", 6, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
       }
    }//GEN-LAST:event_btnobligacionesActionPerformed

    private void btndevengosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndevengosActionPerformed
       if(contratoselect!=null){
           frmDevengos frm=new frmDevengos();
           frm.getContrato(contratoselect);
           this.dispose();
           frm.setVisible(true);
           
       }else{
            new rojerusan.RSNotifyFade("Info", "Debes Seleccionar un Contrato ", 6, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
       }
    }//GEN-LAST:event_btndevengosActionPerformed

    
    
    

    
    
    
    private void jfinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jfinPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jfinPropertyChange

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
      if(contratoselect!=null){
        crearword();
      }else{
         DesktopNotify.showDesktopMessage("Info", "Debe Seleccionar un Registro",DesktopNotify.INFORMATION);
      }
           
            
        
    }//GEN-LAST:event_btnimprimirActionPerformed

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
            java.util.logging.Logger.getLogger(frmContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmContrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmContrato().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.ButtonHover btnEliminar;
    private rojeru_san.complementos.ButtonHover btndevengos;
    private rojeru_san.complementos.ButtonHover btnguardar;
    private rojeru_san.complementos.ButtonHover btnimprimir;
    private rojeru_san.complementos.ButtonHover btnmodificar;
    private rojeru_san.complementos.ButtonHover btnnuevo;
    private rojeru_san.complementos.ButtonHover btnobligaciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jfin;
    private com.toedter.calendar.JDateChooser jinicio;
    private rojeru_san.complementos.TableMetro tablabanco;
    private app.bolivia.swing.JCTextField txtbuscar;
    private app.bolivia.swing.JCTextField txtextendido;
    private app.bolivia.swing.JCTextField txtlugar;
    private app.bolivia.swing.JCTextField txtnom;
    // End of variables declaration//GEN-END:variables
}
