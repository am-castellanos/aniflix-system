/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.aniflix.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.ServidorDAO;

import java.io.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author amcc
 */
public class ServidorVista extends Administrador{
    JPanel servidor;
    JTable tabla;
    JScrollPane sp;

    private final ServidorDAO servidorDAO;

    public ServidorVista() {

        this.servidor = new JPanel();
        this.tabla = new JTable();
        this.sp = new JScrollPane();
        this.servidorDAO = new ServidorDAO();
    }

    private void botones(){
        JButton crear = new JButton("Crear");
        servidor.setLayout(null);
        crear.setBounds(870, 50, 130, 50);
        servidor.add(crear);

        crear.addActionListener(event -> crear());
        
        JButton cargar = new JButton("Cargar");
        cargar.setBounds(1040, 50, 130, 50);
        servidor.add(cargar);

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
        servidor.add(actualizar);

        actualizar.addActionListener(event -> actualizar());
        
        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(1040, 150, 130, 50);
        servidor.add(eliminar);

        eliminar.addActionListener(event -> {
            eliminar();
            actualizarTabla();
        });

        JButton exportar = new JButton("Exportar PDF");
        exportar.setBounds(870, 250, 300, 50);
        servidor.add(exportar);

        exportar.addActionListener(event -> {

            try {
                servidorDAO.pdf();
            } catch (FileNotFoundException fileNotFoundException) {
                throw  new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });
    }

    private void tabla(){
        String columnas [] = {"ID", "Servidor", "Estado"};

        Object filas[][] = servidorDAO.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 50, 840, 400);
        servidor.add(sp);
    }

    private void actualizarTabla(){
        servidor.remove(sp);
        tabla();
    }

    private void crear(){
        JFrame frame_servidor = new JFrame();
        frame_servidor.setTitle("Nuevo Servidor");
        frame_servidor.setLocationRelativeTo(null);
        frame_servidor.setBounds(50, 175, 430, 400);    
        frame_servidor.setVisible(true);
        
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_servidor.add(p1);
        
        JLabel lbServidor = new JLabel("Servidor");
        lbServidor.setBounds(50, 20, 80, 50);
        p1.add(lbServidor);
        
        JTextField edServidor = new JTextField();
        edServidor.setBounds(150, 35, 200, 20);
        p1.add(edServidor);
        
        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 80, 80, 50);
        p1.add(lbEstado);
        
        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 95, 200, 20);
        p1.add(edEstado);
        
        JButton bttonGuardar = new JButton("Guardar");
        bttonGuardar.setBounds(150, 155, 120, 40);
        p1.add(bttonGuardar);

        bttonGuardar.addActionListener(event -> {

            try {
                servidorDAO.crear(edServidor.getText(),
                        Integer.parseInt(edEstado.getText())
                );

                frame_servidor.setVisible(false);
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
            servidorDAO.crear(objeto.get("servidor").getAsString(), objeto.get("estado").getAsInt());
        }
    }
    
    private void actualizar(){
        JFrame frame_servidor = new JFrame();
        frame_servidor.setTitle("Actualizar Servidor");
        frame_servidor.setLocationRelativeTo(null);
        frame_servidor.setBounds(50, 175, 430, 400);
        frame_servidor.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frame_servidor.add(p1);

        JLabel lbID = new JLabel("ID");
        lbID.setBounds(50, 20, 80, 50);
        p1.add(lbID);

        JTextField edID = new JTextField();
        edID.setBounds(150, 35, 200, 20);
        edID.setText(tabla.getValueAt(tabla.getSelectedRow(), 0)+"");
        edID.setEnabled(false);
        p1.add(edID);

        JLabel lbServidor = new JLabel("Servidor");
        lbServidor.setBounds(50, 80, 80, 50);
        p1.add(lbServidor);

        JTextField edServidor = new JTextField();
        edServidor.setBounds(150, 95, 200, 20);
        edServidor.setText(tabla.getValueAt(tabla.getSelectedRow(), 1)+"");
        p1.add(edServidor);

        JLabel lbEstado = new JLabel("Estado");
        lbEstado.setBounds(50, 140, 80, 50);
        p1.add(lbEstado);

        JTextField edEstado = new JTextField();
        edEstado.setBounds(150, 155, 200, 20);
        edEstado.setText(tabla.getValueAt(tabla.getSelectedRow(), 2)+"");
        p1.add(edEstado);

        JButton bttonGuardar = new JButton("Actualizar");
        bttonGuardar.setBounds(150, 215, 120, 40);
        p1.add(bttonGuardar);

        bttonGuardar.addActionListener(event -> {
            try {
                servidorDAO.actualizar(Integer.parseInt(edID.getText()),
                        edServidor.getText(),
                        Integer.parseInt(edEstado.getText())
                );

                frame_servidor.setVisible(false);
                actualizarTabla();

            } catch (Exception exception){

                exception.printStackTrace();
            }

        });

    }
    
    private void eliminar(){

        servidorDAO.eliminar(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
    }
    
    public void ejecutar(){
        botones();
        tabla();
    }
}
