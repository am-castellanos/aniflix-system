package com.sistema.aniflix.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.EmpleadoDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EmpleadoVista {
    JPanel empleado;
    JTable tabla;
    JScrollPane sp;

    private final EmpleadoDAO empleadoDAO;

    @Autowired
    public EmpleadoVista(final EmpleadoDAO empleadoDAO){

        this.empleado = new JPanel();
        this.tabla = new JTable();
        this.sp = new JScrollPane();
        this.empleadoDAO = empleadoDAO;
    }

    private void botones(){
        JButton crear = new JButton("Crear");
        empleado.setLayout(null);
        crear.setBounds(870, 50, 130, 50);
        empleado.add(crear);

        crear.addActionListener(event -> crear());

        JButton cargar = new JButton("Cargar");
        cargar.setBounds(1040, 50, 130, 50);
        empleado.add(cargar);

        cargar.addActionListener(event -> {

            try {
                cargaMasiva();
                actualizarTabla();
            } catch (IOException ioException) {
                Logger.getLogger(ServidorVista.class.getName()).log(Level.SEVERE, null, ioException);
            } catch (ParseException parseException) {
                Logger.getLogger(ServidorVista.class.getName()).log(Level.SEVERE, null, parseException);
            }
        });

        JButton actualizar = new JButton("Actualizar");
        actualizar.setBounds(870, 150, 130, 50);
        empleado.add(actualizar);

        actualizar.addActionListener(event -> actualizar());

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(1040, 150, 130, 50);
        empleado.add(eliminar);

        eliminar.addActionListener(event -> eliminar());

        JButton exportar = new JButton("Exportar PDF");
        exportar.setBounds(870, 250, 300, 50);
        empleado.add(exportar);

        exportar.addActionListener(event -> {

            try {
                empleadoDAO.pdf();
            } catch (FileNotFoundException fileNotFoundException) {
                throw  new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });
    }

    private void tabla() {
        String columnas[] = {"ID", "CUI", "Nombre", "Salario", "Departamento", "Correo", "Contraseña", "Ingreso", "Egreso", "Rol"};

        Object filas[][] = empleadoDAO.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 50, 840, 400);
        empleado.add(sp);
    }

    private void actualizarTabla() {

        empleado.remove(sp);
        tabla();
    }

    private void crear(){
        JFrame frameEmpleado = new JFrame();
        frameEmpleado.setTitle("Nuevo Empleado");
        frameEmpleado.setLocationRelativeTo(null);
        frameEmpleado.setBounds(50, 175, 430, 500);
        frameEmpleado.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frameEmpleado.add(p1);

        JLabel lbCUI = new JLabel("CUI");
        lbCUI.setBounds(50, 10, 80, 50);
        p1.add(lbCUI);

        JTextField edCUI = new JTextField();
        edCUI.setBounds(150, 25, 200, 20);
        p1.add(edCUI);

        JLabel lbNombre = new JLabel("Nombre");
        lbNombre.setBounds(50, 50, 80, 50);
        p1.add(lbNombre);

        JTextField edNombre = new JTextField();
        edNombre.setBounds(150, 65, 200, 20);
        p1.add(edNombre);

        JLabel lbSalario= new JLabel("Salario");
        lbSalario.setBounds(50, 90, 80, 50);
        p1.add(lbSalario);

        JTextField edSalario = new JTextField();
        edSalario.setBounds(150, 105, 200, 20);
        p1.add(edSalario);

        JLabel lbDepartamento = new JLabel("Departamento");
        lbDepartamento.setBounds(50, 130, 100, 50);
        p1.add(lbDepartamento);

        JTextField edDepartamento = new JTextField();
        edDepartamento.setBounds(150, 145, 200, 20);
        p1.add(edDepartamento);

        final var lbCorreo = new JLabel("Correo");
        lbCorreo.setBounds(50, 170, 80, 50);
        p1.add(lbCorreo);

        final var edCorreo = new JTextField();
        edCorreo.setBounds(150, 185, 200, 20);
        p1.add(edCorreo);

        JLabel lbContrasena = new JLabel("Contraseña");
        lbContrasena.setBounds(50, 210, 80, 50);
        p1.add(lbContrasena);

        JTextField edContrasena = new JTextField();
        edContrasena.setBounds(150, 225, 200, 20);
        p1.add(edContrasena);

        JLabel lbFechaIngreso = new JLabel("Fecha Ingreso");
        lbFechaIngreso.setBounds(50, 250, 100, 50);
        p1.add(lbFechaIngreso);

        JTextField edFechaIngreso = new JTextField();
        edFechaIngreso.setBounds(150, 265, 200, 20);
        p1.add(edFechaIngreso);

        JLabel lbFechaEgreso = new JLabel("Fecha Egreso");
        lbFechaEgreso.setBounds(50, 290, 80, 50);
        p1.add(lbFechaEgreso);

        JTextField edFechaEgreso = new JTextField();
        edFechaEgreso.setBounds(150, 305, 200, 20);
        p1.add(edFechaEgreso);

        JLabel lbRol = new JLabel("Rol");
        lbRol.setBounds(50, 330, 80, 50);
        p1.add(lbRol);

        JTextField edRol = new JTextField();
        edRol.setBounds(150, 345, 200, 20);
        p1.add(edRol);

        JButton bttnGuardar = new JButton("Guardar");
        bttnGuardar.setBounds(150, 385, 120, 40);
        p1.add(bttnGuardar);

        bttnGuardar.addActionListener(event -> {

            try {

                empleadoDAO.crear(Integer.parseInt(edCUI.getText()),
                        edNombre.getText(),
                        Integer.parseInt(edSalario.getText()),
                        edDepartamento.getText(),
                        edCorreo.getText(),
                        edContrasena.getText(),
                        convertToInstant(edFechaIngreso.getText()),
                        convertToInstant(edFechaEgreso.getText()),
                        Integer.parseInt(edRol.getText())
                );

                frameEmpleado.setVisible(false);
                actualizarTabla();

            } catch (Exception exception) {

                exception.printStackTrace();
            }
        });
    }

    private Instant convertToInstant(final String date) throws ParseException {

        if (StringUtils.isNotBlank(date)) {

            final var parseDate = DateUtils.parseDate(date, "yyyy-MM-dd");
            return parseDate.toInstant();
        }

        return null;
    }

    private String leerArchivo(){
        JPanel c1 = new JPanel();
        JFileChooser fc = new JFileChooser();
        int op = fc.showOpenDialog(c1);
        String content = "";

        if(op == JFileChooser.APPROVE_OPTION){
            File pRuta = fc.getSelectedFile();
            String ruta = ((File) pRuta).getAbsolutePath();
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try{

                archivo = new File(ruta);
                fr =new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea = "";

                while ((linea = br.readLine()) != null){
                    content += linea + "\n";
                }

                return content;

            } catch (FileNotFoundException ex) {
                String resp = (String) JOptionPane.showInputDialog(null, "Archivo no encontrado");
            } catch (IOException ex) {
                String resp = (String) JOptionPane.showInputDialog(null, "No se pudo abrir el archivo");
            } finally {

                try{

                    if (null != fr){
                        fr.close();
                    }
                } catch (Exception e) {
                    String resp = (String) JOptionPane.showInputDialog(null, "Archivo no encontrado");
                    return "";
                }
            }
            return content;
        }
        return null;
    }

    private void cargaMasiva() throws FileNotFoundException, IOException, ParseException {
        String archivoRetorno = leerArchivo();
        JsonParser parser = new JsonParser();
        JsonArray matriz = parser.parse(archivoRetorno).getAsJsonArray();

        for (int i = 0; i < matriz.size(); i++){
            JsonObject objeto = matriz.get(i).getAsJsonObject();
            empleadoDAO.crear(objeto.get("cui").getAsInt(), objeto.get("nombre").getAsString(), objeto.get("salario").getAsInt(), objeto.get("departamento").getAsString(), objeto.get("correo").toString(), objeto.get("contrasena").getAsString(), convertToInstant(objeto.get("fecha_ingreso").getAsString()), convertToInstant(objeto.get("fecha_egreso").getAsString()), objeto.get("rol").getAsInt());
        }
    }

    private void actualizar(){
        JFrame frameEmpleado = new JFrame();
        frameEmpleado.setTitle("Actualizar Empleado");
        frameEmpleado.setLocationRelativeTo(null);
        frameEmpleado.setBounds(50, 175, 430, 500);
        frameEmpleado.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frameEmpleado.add(p1);

        JLabel lbID = new JLabel("ID");
        lbID.setBounds(50, 10, 80, 50);
        p1.add(lbID);

        JTextField edID = new JTextField();
        edID.setBounds(150, 35, 200, 20);
        edID.setText(tabla.getValueAt(tabla.getSelectedRow(), 0)+"");
        edID.setEnabled(false);
        p1.add(edID);

        JLabel lbCUI = new JLabel("CUI");
        lbCUI.setBounds(50, 50, 80, 50);
        p1.add(lbCUI);

        JTextField edCUI = new JTextField();
        edCUI.setBounds(150, 65, 200, 20);
        edCUI.setText(tabla.getValueAt(tabla.getSelectedRow(), 1)+"");
        p1.add(edCUI);

        JLabel lbNombre = new JLabel("Nombre");
        lbNombre.setBounds(50, 90, 80, 50);
        p1.add(lbNombre);

        JTextField edNombre = new JTextField();
        edNombre.setBounds(150, 105, 200, 20);
        edNombre.setText(tabla.getValueAt(tabla.getSelectedRow(), 2)+"");
        p1.add(edNombre);

        JLabel lbSalario = new JLabel("Salario");
        lbSalario.setBounds(50, 130, 80, 50);
        p1.add(lbSalario);

        JTextField edSalario = new JTextField();
        edSalario.setBounds(150, 145, 200, 20);
        edSalario.setText(tabla.getValueAt(tabla.getSelectedRow(), 3)+"");
        p1.add(edSalario);

        JLabel lbDepartamento = new JLabel("Departamento");
        lbDepartamento.setBounds(50, 170, 100, 50);
        p1.add(lbDepartamento);

        JTextField edDepartamento = new JTextField();
        edDepartamento.setBounds(150, 185, 200, 20);
        edDepartamento.setText(tabla.getValueAt(tabla.getSelectedRow(), 4)+"");
        p1.add(edDepartamento);

        JLabel lbCorreo = new JLabel("Correo");
        lbCorreo.setBounds(50, 210, 80, 50);
        p1.add(lbCorreo);

        JTextField edCorreo = new JTextField();
        edCorreo.setBounds(150, 225, 200, 20);
        edCorreo.setText(tabla.getValueAt(tabla.getSelectedRow(), 5)+"");
        p1.add(edCorreo);

        JLabel lbContrasena = new JLabel("Contraseña");
        lbContrasena.setBounds(50, 250, 80, 50);
        p1.add(lbContrasena);

        JTextField edContrasena = new JTextField();
        edContrasena.setBounds(150, 265, 200, 20);
        edContrasena.setText(tabla.getValueAt(tabla.getSelectedRow(), 6)+"");
        p1.add(edContrasena);

        JLabel lbFechaIngreso = new JLabel("Fecha Ingreso");
        lbFechaIngreso.setBounds(50, 290, 100, 50);
        p1.add(lbFechaIngreso);

        JTextField edFechaIngreso = new JTextField();
        edFechaIngreso.setBounds(150, 305, 200, 20);

        final var fechaIngreso = tabla.getValueAt(tabla.getSelectedRow(), 7);
        edFechaIngreso.setText(fechaIngreso != null ? fechaIngreso.toString() : "");
        p1.add(edFechaIngreso);

        JLabel lbFechaEgreso = new JLabel("Fecha Egreso");
        lbFechaEgreso.setBounds(50, 330, 80, 50);
        p1.add(lbFechaEgreso);

        JTextField edFechaEgreso = new JTextField();
        edFechaEgreso.setBounds(150, 345, 200, 20);

        final var fechaEgreso = tabla.getValueAt(tabla.getSelectedRow(), 8);
        edFechaEgreso.setText(fechaEgreso != null ? fechaEgreso.toString() : "");
        p1.add(edFechaEgreso);

        JLabel lbRol = new JLabel("Rol");
        lbRol.setBounds(50, 370, 80, 50);
        p1.add(lbRol);

        JTextField edRol = new JTextField();
        edRol.setBounds(150, 385, 200, 20);

        final var rol = tabla.getValueAt(tabla.getSelectedRow(), 9);
        edRol.setText(rol.toString());
        p1.add(edRol);

        JButton bttonActualizar = new JButton("Actualizar");
        bttonActualizar.setBounds(150, 425, 120, 40);
        p1.add(bttonActualizar);

        bttonActualizar.addActionListener(event -> {

            try {

                empleadoDAO.actualizar(Integer.parseInt(edID.getText()),
                        Integer.parseInt(edCUI.getText()),
                        edNombre.getText(),
                        Integer.parseInt(edSalario.getText()),
                        edDepartamento.getText(),
                        edCorreo.getText(),
                        edContrasena.getText(),
                        convertToInstant(edFechaIngreso.getText()),
                        convertToInstant(edFechaEgreso.getText()),
                        Integer.parseInt(edRol.getText())
                );

                frameEmpleado.setVisible(false);
                actualizarTabla();

            } catch (Exception exception) {

                exception.printStackTrace();
            }
        });
    }

    private void eliminar(){

        empleadoDAO.eliminar(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
    }


    public void ejecutar(){
        botones();
        tabla();
    }
}
