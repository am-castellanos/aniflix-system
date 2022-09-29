package com.sistema.aniflix.dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerfilDAO {
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private Conexion conectar;
    private Object datos[][];
    private Object entradaSalida[][];

    public PerfilDAO() {

        this.conectar = new Conexion();

    }

    public Object[][] listarTabla(final String correo) {

        final var sql = "select * from empleado where correo = ?";

        try {
            int x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            while (rs.next()) {
                x++;
            }

            datos = new Object[x][10];
            x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            while (rs.next()) {
                datos[x][0] = rs.getInt(1);
                datos[x][1] = rs.getInt(2);
                datos[x][2] = rs.getString(3);
                datos[x][3] = rs.getInt(4);
                datos[x][4] = rs.getString(5);
                datos[x][5] = rs.getString(6);
                datos[x][6] = rs.getString(7);
                datos[x][7] = rs.getDate(8);
                datos[x][8] = rs.getDate(9);
                datos[x][9] = rs.getInt(10);

                x++;
            }

            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }

        return datos;
    }

    public Object[][] listarEntradaSalida(final String correo) {

        final var sql = "SELECT e.nombre, e.correo, ev.tipo, ee.fecha\n" +
                "FROM `empleado_evento` ee\n" +
                "\tINNER JOIN empleado e on ee.empleado_id = e.id_empleado\n" +
                "    inner JOIN evento ev on ee.evento_id = ev.id_evento WHERE correo = ?";

        try {
            int x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            while (rs.next()) {
                x++;
            }

            datos = new Object[x][4];
            x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            while (rs.next()) {
                datos[x][0] = rs.getString(1);
                datos[x][1] = rs.getString(2);
                datos[x][2] = rs.getString(3);
                datos[x][3] = rs.getString(4);

                x++;
            }

            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }

        return datos;
    }

    public void actualizarContrasenna(String contrasena, String correo) {

        final var sql = "update empleado set contrasena = ? where correo = ?";

        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, contrasena);
            ps.setString(2, correo);
            ps.executeUpdate();

            try {
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }
    }

    public void pdf(final String correo) throws FileNotFoundException, DocumentException {
        FileOutputStream gen = new FileOutputStream("Entradas-Salidas.pdf");
        Document docto = new Document();

        PdfWriter.getInstance(docto, gen);
        docto.open();

        Paragraph parrafo = new Paragraph("Reporte de Entradas y Salidas Aniflix");
        parrafo.setAlignment(1);
        docto.add(parrafo);
        docto.add(new Paragraph("\n"));

        final var sql = "SELECT e.nombre, e.correo, ev.tipo, ee.fecha\n" +
                "FROM `empleado_evento` ee\n" +
                "\tINNER JOIN empleado e on ee.empleado_id = e.id_empleado\n" +
                "    inner JOIN evento ev on ee.evento_id = ev.id_evento WHERE correo = ?";

        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            while (rs.next()) {
                docto.add(new Paragraph("Nombre: " + rs.getString(1)));
                docto.add(new Paragraph("Correo: " + rs.getString(2)));
                docto.add(new Paragraph("Tipo: " + rs.getString(3)));
                docto.add(new Paragraph("Fecha: " + rs.getString(4)));
                docto.add(new Paragraph("\n"));
            }

            try {
                ps.close();
                con.close();
                rs.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }

        docto.close();
        JOptionPane.showMessageDialog(null, "Archivo creado Correctamente");
        try {
            File servidor_doc = new File("Entradas-Salidas.pdf");
            Desktop.getDesktop().open(servidor_doc);
        } catch (Exception e){

        }
    }
}
