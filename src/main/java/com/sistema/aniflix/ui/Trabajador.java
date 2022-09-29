package com.sistema.aniflix.ui;

import javax.swing.*;

public class Trabajador extends JFrame {
    JTabbedPane ventana = new JTabbedPane();
    JPanel trabajador = new JPanel();
    JPanel perfil = new JPanel();

    private String usuario;

    private void inicio(){
        setTitle("Empleado - " + usuario);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 100, 1200, 500);
        setVisible(true);

        TrabajadorVista trabajadorVista = new TrabajadorVista();
        trabajadorVista.setUsuario(usuario);
        trabajadorVista.ejecutar();

        PerfilVista perfilVista = new PerfilVista();
        perfilVista.setUsuario(usuario);
        perfilVista.ejecutar();

        ventana.addTab("Trabajador", trabajadorVista.trabajador);
        ventana.addTab("Perfil", perfilVista.perfil);
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
        Trabajador tr = new Trabajador();
        tr.ejecutar();
    }
}
