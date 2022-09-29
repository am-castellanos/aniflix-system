package com.sistema.aniflix.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class Trabajador extends JFrame {
    JTabbedPane ventana = new JTabbedPane();
    JPanel trabajador = new JPanel();
    JPanel perfil = new JPanel();

    private final PerfilVista perfilVista;

    private final TrabajadorVista trabajadorVista;

    private String usuario;

    @Autowired
    public Trabajador(final PerfilVista perfilVista,
                      final TrabajadorVista trabajadorVista) {

        this.perfilVista = perfilVista;
        this.trabajadorVista = trabajadorVista;
    }

    private void inicio(){
        setTitle("Empleado - " + usuario);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 100, 1200, 500);
        setVisible(true);

        trabajadorVista.setUsuario(usuario);
        trabajadorVista.ejecutar();

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

}
