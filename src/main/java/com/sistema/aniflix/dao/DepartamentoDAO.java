package com.sistema.aniflix.dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

@Repository
public class DepartamentoDAO {

    private final DataSource dataSource;

    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private Object datos[][];

    public DepartamentoDAO(final DataSource dataSource) {

        this.dataSource = dataSource;

    }

    public Object[][] listar_tabla() {

        final var sql = "select * from area_laboral";

        try {
            int x = 0;
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            final var departamentos = new ArrayList<String>();


            while (rs.next()) {

                departamentos.add(rs.getString("departamento"));
                x++;
            }

            datos = new Object[x][3];
            x = 0;
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                datos[x][0] = rs.getInt(1);
                datos[x][1] = rs.getString(2);
                datos[x][2] = rs.getInt(3);
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

    public void crear(String departamento, int estado) {

        final var sql = "insert into area_laboral (departamento, estado) values(?, ?)";

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, departamento);
            ps.setInt(2, estado);
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

    public void actualizar(int id_departamento, String departamento, int estado) {
        final var sql = "update area_laboral set departamento = ?, estado = ? where id_departamento = ?";

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, departamento);
            ps.setInt(2, estado);
            ps.setInt(3, id_departamento);
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

    public void eliminar(int id_departamento) {
        final var sql = "delete from area_laboral where id_departamento = ?";

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_departamento);
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

    public void pdf() throws FileNotFoundException, DocumentException {
        FileOutputStream gen = new FileOutputStream("Departamentos.pdf");
        Document docto = new Document();

        PdfWriter.getInstance(docto, gen);
        docto.open();

        Paragraph parrafo = new Paragraph("Reporte de Departamentos Aniflix");
        parrafo.setAlignment(1);
        docto.add(parrafo);
        docto.add(new Paragraph("\n"));

        final var sql = "select * from area_laboral";
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                docto.add(new Paragraph("ID: " + rs.getInt(1)));
                docto.add(new Paragraph("Departamento: " + rs.getString(2)));
                docto.add(new Paragraph("Estado: " + rs.getInt(3)));
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
            File servidor_doc = new File("Departamentos.pdf");
            Desktop.getDesktop().open(servidor_doc);
        } catch (Exception e){

        }
    }
}
