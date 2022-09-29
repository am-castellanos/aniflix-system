package com.sistema.aniflix.ui;

import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.ESDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;

@Component
public class ESVista {
    JPanel es;
    JTable tabla;
    JScrollPane sp;

    private final ESDAO esDAO;

    @Autowired
    public ESVista(final ESDAO esDAO) {

        this.es = new JPanel();
        this.tabla = new JTable();
        this.sp = new JScrollPane();
        this.esDAO = esDAO;
    }

    private void botones(){
        JButton exportar = new JButton("Exportar PDF");
        es.setLayout(null);
        exportar.setBounds(870, 200, 300, 50);
        es.add(exportar);

        exportar.addActionListener(event -> {

            try {
                esDAO.pdf();
            } catch (FileNotFoundException fileNotFoundException) {
                throw  new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });

        JButton listar = new JButton("Listar");
        listar.setBounds(870, 100, 300, 50);
        es.add(listar);

        listar.addActionListener(event -> actualizarTabla());
    }

    private void tabla(){
        String columnas [] = {"Nombre", "Correo", "Evento", "Fecha"};

        Object filas[][] = esDAO.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(10, 50, 840, 400);
        es.add(sp);
    }

    private void actualizarTabla(){

        es.remove(sp);
        tabla();
    }

    public void ejecutar(){
        botones();
        tabla();
    }
}
