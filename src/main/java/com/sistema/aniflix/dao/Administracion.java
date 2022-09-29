/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.aniflix.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author amcc
 */
public class Administracion{
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();
    
    public int filtro(String correo, String pass){
        final var sql = "select * from empleado where correo = ? and contrasena = ?";
        
        try{
            con = conectar.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getInt("rol");
            }
                        
        } catch (Exception e){
            
            e.printStackTrace();
        
        } finally {
            
            try {
                if (rs != null) {
                    rs.close();                
                }
                
                if (ps != null) {
                    
                    ps.close();
                }
                
                if (con != null) {
                    
                    con.close();
                }
                
            } catch (Exception ex) {
                
                ex.printStackTrace();
            }            
        }
        return -1;
    }
}
