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
    private final PerfilVista perfilVista;
    private final PlanVista planVista;
    private final TrabajadorVista trabajadorVista;

    JTabbedPane ventana = new JTabbedPane();

    private String usuario;

    @Autowired
    public Administrador(final DepartamentoVista departamentoVista,
                         final ServidorVista servidorVista,
                         final EmpleadoVista empleadoVista,
                         final ESVista esVista,
                         final PerfilVista perfilVista,
                         final PlanVista planVista,
                         final TrabajadorVista trabajadorVista) {

        this.departamentoVista = departamentoVista;
        this.servidorVista = servidorVista;
        this.empleadoVista = empleadoVista;
        this.esVista = esVista;
        this.perfilVista = perfilVista;
        this.planVista = planVista;
        this.trabajadorVista = trabajadorVista;
    }

    private void inicio(){
        setTitle("Administracion - " + usuario);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(50, 100, 1200, 500);
        setVisible(true);

//        ServidorVista servidorVista = new ServidorVista();
        servidorVista.ejecutar();

//        PlanVista planVista = new PlanVista();
        planVista.ejecutar();

//        EmpleadoVista empleadoVista = new EmpleadoVista();
        empleadoVista.ejecutar();

//        DepartamentoVista departamentoVista = new DepartamentoVista();
        departamentoVista.ejecutar();

//        ESVista esVista = new ESVista();
        esVista.ejecutar();

//        TrabajadorVista trabajadorVista = new TrabajadorVista();
        trabajadorVista.setUsuario(usuario);
        trabajadorVista.ejecutar();

//        PerfilVista perfilVista = new PerfilVista();
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

//    public static void main(String[] args){
//        Administrador ad = new Administrador();
//        ad.ejecutar();
//    }
}
