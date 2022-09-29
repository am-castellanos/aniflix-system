/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.aniflix.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.PlanDAO;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amcc
 */
@Component
public class PlanVista {
    JPanel plan;
    JTable tabla;
    JScrollPane sp;

    private final PlanDAO planDAO;

    public PlanVista(final PlanDAO planDAO){

        this.plan = new JPanel();
        this.tabla = new JTable();
        this.sp = new JScrollPane();
        this.planDAO = planDAO;
    }

    private void botones(){
        JButton crear = new JButton("Crear");
        plan.setLayout(null);
        crear.setBounds(870, 50, 130, 50);
        plan.add(crear);

        crear.addActionListener(event -> crear());

        JButton cargar = new JButton("Cargar");
        cargar.setBounds(1040, 50, 130, 50);
        plan.add(cargar);

        cargar.addActionListener(event -> {

            try {

                carga_masiva();
                actualizarTabla();
            } catch (IOException ioException) {
                Logger.getLogger(ServidorVista.class.getName()).log(Level.SEVERE, null, ioException);
            } catch (ParseException parseException) {
                Logger.getLogger(ServidorVista.class.getName()).log(Level.SEVERE, null, parseException);
            }
        });

        JButton actualizar = new JButton("Actualizar");
        actualizar.setBounds(870, 150, 130, 50);
        plan.add(actualizar);

        actualizar.addActionListener(event -> actualizar());

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(1040, 150, 130, 50);
        plan.add(eliminar);

        eliminar.addActionListener(event -> {
            eliminar();
            actualizarTabla();
        });

        JButton exportar = new JButton("Exportar PDF");
        exportar.setBounds(870, 250, 300, 50);
        plan.add(exportar);

        exportar.addActionListener(event -> {

            try {
                planDAO.pdf();
            } catch (FileNotFoundException fileNotFoundException) {
                throw new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });
    }

    private void tabla(){
        String columnas [] = {"ID", "Plan", "Precio (USD)", "Estado"};

        Object filas[][] = planDAO.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 50, 840, 400);
        plan.add(sp);
    }

    private void actualizarTabla(){
        plan.remove(sp);
        tabla();
    }

    private void crear(){
        JFrame frame_plan = new JFrame();
        frame_plan.setTitle("Nuevo Plan");
        frame_plan.setLocationRelativeTo(null);
        frame_plan.setBounds(50, 175, 430, 400);
        frame_plan.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_plan.add(p1);

        JLabel lbPlan = new JLabel("Plan");
        lbPlan.setBounds(50, 20, 80, 50);
        p1.add(lbPlan);

        JTextField edPlan = new JTextField();
        edPlan.setBounds(150, 35, 200, 20);
        p1.add(edPlan);

        JLabel lbPrecio = new JLabel("Precio");
        lbPrecio.setBounds(50, 80, 80, 50);
        p1.add(lbPrecio);

        JTextField edPrecio = new JTextField();
        edPrecio.setBounds(150, 95, 200, 20);
        p1.add(edPrecio);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 140, 80, 50);
        p1.add(lbEstado);

        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 155, 200, 20);
        p1.add(edEstado);

        JButton bttnGuardar = new JButton("Guardar");
        bttnGuardar.setBounds(150, 200, 120, 40);
        p1.add(bttnGuardar);

        bttnGuardar.addActionListener(event -> {

            try {
                planDAO.crear(edPlan.getText(),
                        Integer.parseInt(edPrecio.getText()),
                        Integer.parseInt(edEstado.getText())
                        );

                frame_plan.setVisible(false);
                actualizarTabla();

            } catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

    private String leerarchivo(){
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

    private void carga_masiva() throws FileNotFoundException, IOException, ParseException {
        String archivo_retorno = leerarchivo();
        JsonParser p = new JsonParser();
        JsonArray matriz = p.parse(archivo_retorno).getAsJsonArray();

        for (int i = 0; i < matriz.size(); i++){
            JsonObject objeto = matriz.get(i).getAsJsonObject();
            planDAO.crear(objeto.get("plan").getAsString(), objeto.get("precio_usd").getAsInt(), objeto.get("estado").getAsInt());
        }
    }

    private void actualizar(){
        JFrame frame_plan = new JFrame();
        frame_plan.setTitle("Actualizar Plan");
        frame_plan.setLocationRelativeTo(null);
        frame_plan.setBounds(50, 175, 430, 400);
        frame_plan.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_plan.add(p1);

        JLabel lbID = new JLabel("ID");
        lbID.setBounds(50, 20, 80, 50);
        p1.add(lbID);

        JTextField edID = new JTextField();
        edID.setBounds(150, 35, 200, 20);
        edID.setText(tabla.getValueAt(tabla.getSelectedRow(), 0)+"");
        edID.setEnabled(false);
        p1.add(edID);

        JLabel lbPlan = new JLabel("Plan");
        lbPlan.setBounds(50, 80, 80, 50);
        p1.add(lbPlan);

        JTextField edPlan = new JTextField();
        edPlan.setBounds(150, 95, 200, 20);
        edPlan.setText(tabla.getValueAt(tabla.getSelectedRow(), 1)+"");
        p1.add(edPlan);

        JLabel lbPrecio = new JLabel("Precio (USD)");
        lbPrecio.setBounds(50, 140, 80, 50);
        p1.add(lbPrecio);

        JTextField edPrecio = new JTextField();
        edPrecio.setBounds(150, 155, 200, 20);
        edPrecio.setText(tabla.getValueAt(tabla.getSelectedRow(), 2)+"");
        p1.add(edPrecio);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 200, 80, 50);
        p1.add(lbEstado);

        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 215, 200, 20);
        edEstado.setText(tabla.getValueAt(tabla.getSelectedRow(), 2)+"");
        p1.add(edEstado);

        JButton bttnActualizar = new JButton("Actualizar");
        bttnActualizar.setBounds(150, 290, 120, 40);
        p1.add(bttnActualizar);

        bttnActualizar.addActionListener(event -> {

            try {
                planDAO.actualizar(Integer.parseInt(edID.getText()),
                        edPlan.getText(),
                        Integer.parseInt(edPrecio.getText()),
                        Integer.parseInt(edEstado.getText())
                );

                frame_plan.setVisible(false);
                actualizarTabla();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void eliminar(){

        planDAO.eliminar(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
    }

    public void ejecutar(){
        botones();
        tabla();
    }
}
