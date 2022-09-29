/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistema.aniflix.ui;

import javax.swing.*;

/**
 *
 * @author amcc
 */
public class Administrador extends JFrame{            
    JTabbedPane ventana = new JTabbedPane();
    JPanel servidor = new JPanel();
    JPanel plan = new JPanel();
    JPanel empleado = new JPanel();
    JPanel departamento = new JPanel();

    private String usuario;
    
    private void inicio(){
        setTitle("Administracion - " + usuario);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 100, 1200, 500);
        setVisible(true);

        ServidorVista servidorVista = new ServidorVista();
        servidorVista.ejecutar();

        PlanVista planVista = new PlanVista();
        planVista.ejecutar();

        EmpleadoVista empleadoVista = new EmpleadoVista();
        empleadoVista.ejecutar();

        DepartamentoVista departamentoVista = new DepartamentoVista();
        departamentoVista.ejecutar();

        ESVista esVista = new ESVista();
        esVista.ejecutar();

        TrabajadorVista trabajadorVista = new TrabajadorVista();
        trabajadorVista.setUsuario(usuario);
        trabajadorVista.ejecutar();

        PerfilVista perfilVista = new PerfilVista();
        perfilVista.setUsuario(usuario);
        perfilVista.ejecutar();

        ventana.addTab("Jornada", trabajadorVista.trabajador);
        ventana.addTab("Perfil", perfilVista.perfil);
        ventana.addTab("Empleados", empleadoVista.empleado);
        ventana.addTab("Entradas / Salidas", esVista.es);
        ventana.addTab("Departamentos", departamentoVista.departamento);
        ventana.addTab("Planes" , planVista.plan);
        ventana.addTab("Servidores", servidorVista.servidor);
        add(ventana);
    }
    
    public void ejecutar(){
        inicio();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public static void main(String[] args){
        Administrador ad = new Administrador();
        ad.ejecutar();
    }
}
