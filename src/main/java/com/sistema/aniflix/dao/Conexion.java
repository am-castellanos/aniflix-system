/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.sistema.aniflix.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author amcc
 */
public class Conexion {
    Connection con;
    
    String url="jdbc:mysql://localhost:3306/aniflix";
    String user="root";
    String pass="";
    
    public Connection conectar() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con =  DriverManager.getConnection(url, user, pass);
            
        } catch (Exception exception) {
            
            System.out.println(exception);
            throw exception;
        }      
        return con;
        
    }
}
