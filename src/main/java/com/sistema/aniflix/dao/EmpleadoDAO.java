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
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class EmpleadoDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private Conexion conectar;
    private Object datos[][];

    public EmpleadoDAO() {

        this.conectar = new Conexion();

    }

    public Object[][] listar_tabla() {

        final var sql = "select * from empleado";

        try {
            int x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            final var empleados = new ArrayList<String>();


            while (rs.next()) {

                empleados.add(rs.getString("cui"));
                x++;
            }

            datos = new Object[x][10];
            x = 0;
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
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

    public void crear(int cui,
                      String nombre,
                      int salario,
                      String departamento,
                      String correo,
                      String contrasena,
                      Instant fecha_ingreso,
                      Instant fecha_egreso,
                      int rol) {

        final var sql = "insert into empleado (cui, nombre, salario, departamento, correo, contrasena, fecha_ingreso, fecha_egreso, rol) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);

            ps.setInt(1, cui);
            ps.setString(2, nombre);
            ps.setInt(3, salario);
            ps.setString(4, departamento);
            ps.setString(5, correo);
            ps.setString(6, contrasena);
            ps.setTimestamp(7, new Timestamp(fecha_ingreso.toEpochMilli()));

            ps.setTimestamp(8, nonNull(fecha_egreso) ? new Timestamp(fecha_egreso.toEpochMilli())
                    : null);

            ps.setInt(9, rol);
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

    public void actualizar(int id_empleado,
                           int cui,
                           String nombre,
                           int salario,
                           String departamento,
                           String correo,
                           String contrasena,
                           Instant fecha_ingreso,
                           Instant fecha_egreso,
                           int rol) {
        final var sql = "update empleado set cui = ?, nombre = ?, salario = ?, departamento = ?, correo = ?, contrasena = ?, fecha_ingreso = ?, fecha_egreso = ?, rol = ? where id_empleado = ?";

        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cui);
            ps.setString(2, nombre);
            ps.setInt(3, salario);
            ps.setString(4, departamento);
            ps.setString(5, correo);
            ps.setString(6, contrasena);
            ps.setTimestamp(7, new Timestamp(fecha_ingreso.toEpochMilli()));

            ps.setTimestamp(8, nonNull(fecha_egreso) ? new Timestamp(fecha_egreso.toEpochMilli())
                    : null);

            ps.setInt(9, rol);
            ps.setInt(10, id_empleado);
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

    public void eliminar(int id_empleado) {
        final var sql = "delete from empleado where id_empleado = ?";

        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_empleado);
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
        FileOutputStream gen = new FileOutputStream("Empleados.pdf");
        Document docto = new Document();

        PdfWriter.getInstance(docto, gen);
        docto.open();

        Paragraph parrafo = new Paragraph("Reporte de Empleados Aniflix");
        parrafo.setAlignment(1);
        docto.add(parrafo);
        docto.add(new Paragraph("\n"));

        final var sql = "select * from empleado";
        try {
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                docto.add(new Paragraph("ID: " + rs.getInt(1)));
                docto.add(new Paragraph("CUI: " + rs.getInt(2)));
                docto.add(new Paragraph("Nombre: " + rs.getString(3)));
                docto.add(new Paragraph("Salario: " + rs.getInt(4)));
                docto.add(new Paragraph("Departamento: " + rs.getString(5)));
                docto.add(new Paragraph("Correo: " + rs.getString(6)));
                docto.add(new Paragraph("Contraseña: " + rs.getString(7)));
                docto.add(new Paragraph("Fecha Ingreso: " + rs.getDate(8)));
                docto.add(new Paragraph("Fecha Egreso: " + rs.getDate(9)));
                docto.add(new Paragraph("Rol: " + rs.getInt(10)));
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
            File servidor_doc = new File("Empleados.pdf");
            Desktop.getDesktop().open(servidor_doc);
        } catch (Exception e){

        }
    }
}
