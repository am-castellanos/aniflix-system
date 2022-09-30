package com.sistema.aniflix.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 *
 * @author amcc
 */
@Component
public class Administrador extends JFrame {

    private final DepartamentoVista departamentoVista;
    private final ServidorVista servidorVista;
    private final EmpleadoVista empleadoVista;
    private final ESVista esVista;
    private final PlanVista planVista;

    JTabbedPane ventana = new JTabbedPane();

    private String usuario;

    @Autowired
    public Administrador(final DepartamentoVista departamentoVista,
                         final ServidorVista servidorVista,
                         final EmpleadoVista empleadoVista,
                         final ESVista esVista,
                         final PlanVista planVista) {

        this.departamentoVista = departamentoVista;
        this.servidorVista = servidorVista;
        this.empleadoVista = empleadoVista;
        this.esVista = esVista;
        this.planVista = planVista;
    }

    private void inicio(){
        setTitle("Administracion - " + usuario);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 100, 1200, 500);
        setVisible(true);

        servidorVista.ejecutar();

        planVista.ejecutar();

        empleadoVista.ejecutar();

        departamentoVista.ejecutar();

        esVista.ejecutar();

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
}
