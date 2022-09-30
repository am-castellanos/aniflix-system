package com.sistema.aniflix.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.DepartamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DepartamentoVista {
    JPanel departamento;
    JTable tabla;
    JScrollPane sp;

    private final DepartamentoDAO departamentoDAO;

    @Autowired
    public DepartamentoVista(final DepartamentoDAO departamentoDAO){

        this.departamento = new JPanel();
        this.tabla = new JTable();
        this.sp = new JScrollPane();
        this.departamentoDAO = departamentoDAO;
    }

    private void botones(){
        JButton crear = new JButton("Crear");
        departamento.setLayout(null);
        crear.setBounds(870, 50, 130, 50);
        departamento.add(crear);

        crear.addActionListener(event -> crear());

        JButton cargar = new JButton("Cargar");
        cargar.setBounds(1040, 50, 130, 50);
        departamento.add(cargar);

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
        departamento.add(actualizar);

        actualizar.addActionListener(event -> actualizar());

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(1040, 150, 130, 50);
        departamento.add(eliminar);

        eliminar.addActionListener(event -> {

            eliminar();
            actualizarTabla();
        });

        JButton exportar = new JButton("Exportar PDF");
        exportar.setBounds(870, 250, 300, 50);
        departamento.add(exportar);

        exportar.addActionListener(event -> {

            try {
                departamentoDAO.pdf();
            } catch (FileNotFoundException fileNotFoundException) {
                throw  new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });
    }

    private void tabla(){
        String columnas [] = {"ID", "Departamento", "Estado"};

        Object filas[][] = departamentoDAO.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 50, 840, 400);
        departamento.add(sp);
    }

    private void actualizarTabla(){
        departamento.remove(sp);
        tabla();
    }

    private void crear(){
        JFrame frameDepartamento = new JFrame();
        frameDepartamento.setTitle("Nuevo Departamento");
        frameDepartamento.setLocationRelativeTo(null);
        frameDepartamento.setBounds(50, 175, 430, 400);
        frameDepartamento.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frameDepartamento.add(p1);

        JLabel lbDepartamento = new JLabel("Departamento");
        lbDepartamento.setBounds(50, 20, 100, 50);
        p1.add(lbDepartamento);

        JTextField edDepartamento = new JTextField();
        edDepartamento.setBounds(150, 35, 200, 20);
        p1.add(edDepartamento);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 80, 80, 50);
        p1.add(lbEstado);

        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 95, 200, 20);
        p1.add(edEstado);

        JButton bttnGuardar = new JButton("Guardar");
        bttnGuardar.setBounds(150, 155, 120, 40);
        p1.add(bttnGuardar);

        bttnGuardar.addActionListener(event -> {

            try {
                departamentoDAO.crear(
                        edDepartamento.getText(),
                        Integer.parseInt(edEstado.getText())
                        );

                frameDepartamento.setVisible(false);
                actualizarTabla();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
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
            departamentoDAO.crear(objeto.get("departamento").getAsString(), objeto.get("estado").getAsInt());
        }
    }

    private void actualizar(){
        JFrame frameDepartamento = new JFrame();
        frameDepartamento.setTitle("Actualizar Servidor");
        frameDepartamento.setLocationRelativeTo(null);
        frameDepartamento.setBounds(50, 175, 430, 400);
        frameDepartamento.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frameDepartamento.add(p1);

        JLabel lbID = new JLabel("ID");
        lbID.setBounds(50, 20, 80, 50);
        p1.add(lbID);

        JTextField edID = new JTextField();
        edID.setBounds(150, 35, 200, 20);
        edID.setText(tabla.getValueAt(tabla.getSelectedRow(), 0)+"");
        edID.setEnabled(false);
        p1.add(edID);

        JLabel lbDepartamento = new JLabel("Departamento");
        lbDepartamento.setBounds(50, 80, 100, 50);
        p1.add(lbDepartamento);

        JTextField edDepartamento = new JTextField();
        edDepartamento.setBounds(150, 95, 200, 20);
        edDepartamento.setText(tabla.getValueAt(tabla.getSelectedRow(), 1)+"");
        p1.add(edDepartamento);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 140, 80, 50);
        p1.add(lbEstado);

        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 155, 200, 20);
        edEstado.setText(tabla.getValueAt(tabla.getSelectedRow(), 2)+"");
        p1.add(edEstado);

        JButton bttnActualizar = new JButton("Actualizar");
        bttnActualizar.setBounds(150, 215, 120, 40);
        p1.add(bttnActualizar);

        bttnActualizar.addActionListener(event -> {

            try {
                departamentoDAO.actualizar(Integer.parseInt(edID.getText()),
                        edDepartamento.getText(),
                        Integer.parseInt(edEstado.getText())
                        );

                frameDepartamento.setVisible(false);
                actualizarTabla();

            } catch (Exception exceptione) {
                exceptione.printStackTrace();
            }
        });
    }

    private void eliminar(){

        departamentoDAO.eliminar(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
    }

    public void ejecutar(){
        botones();
        tabla();
    }
}
