/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.aniflix.dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 *
 * @author amcc
 */
@Repository
public class PlanDAO {

    private final DataSource dataSource;

    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private Object datos[][];

    @Autowired
    public PlanDAO(final DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public Object[][] listar_tabla(){

        final var sql = "select * from plan";

        try{
            int x = 0;
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            final var planes = new ArrayList<String>();


            while(rs.next()){

                planes.add(rs.getString("plan"));
                x++;
            }

            datos = new Object[x][4];
            x = 0;
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                datos[x][0] = rs.getInt(1);
                datos[x][1] = rs.getString(2);
                datos[x][2] = rs.getInt(3);
                datos[x][3] = rs.getInt(4);
                x++;
            }

            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }

        }
        catch(Exception e){

            e.printStackTrace();
        }
        finally {

        }

        return datos;
    }

    public void crear(String plan, int precio_usd, int estado){

        final var sql = "insert into plan(plan, precio_usd, estado) values(?, ?, ?)";

        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, plan);
            ps.setInt(2, precio_usd);
            ps.setInt(3, estado);
            ps.executeUpdate();

            try {
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }
        }
        catch(Exception e){

            e.printStackTrace();
        }
        finally {

        }
    }

    public void actualizar(int id_plan, String plan, int precio_usd, int estado){
        final var sql = "update plan set plan = ?, precio_usd = ?, estado = ? where id_plan = ?";

        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, plan);
            ps.setInt(2, precio_usd);
            ps.setInt(3, estado);
            ps.setInt(4, id_plan);
            ps.executeUpdate();

            try {
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }
        }
        catch(Exception e){

            e.printStackTrace();
        }
        finally {

        }
    }

    public void eliminar(int id_plan){
        final var sql = "delete from plan where id_plan = ?";

        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_plan);
            ps.executeUpdate();

            try {
                ps.close();
                con.close();
            } catch (SQLException sqlException) {

                sqlException.printStackTrace();
            }
        }
        catch(Exception e){

            e.printStackTrace();
        }
        finally {

        }
    }

    public void pdf() throws FileNotFoundException, DocumentException {
        FileOutputStream gen = new FileOutputStream("Planes.pdf");
        Document docto = new Document();

        PdfWriter.getInstance(docto, gen);
        docto.open();

        Paragraph parrafo = new Paragraph("Reporte de Planes Aniflix");
        parrafo.setAlignment(1);
        docto.add(parrafo);
        docto.add(new Paragraph("\n"));

        final var sql = "select * from plan";
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                docto.add(new Paragraph("ID: " + rs.getInt(1)));
                docto.add(new Paragraph("Plan: " + rs.getString(2)));
                docto.add(new Paragraph("Precio: " + rs.getInt(3)));
                docto.add(new Paragraph("Estado: " + rs.getInt(4)));
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
            File plan_doc = new File("Planes.pdf");
            Desktop.getDesktop().open(plan_doc);
        } catch (Exception e){

        }
    }
}
