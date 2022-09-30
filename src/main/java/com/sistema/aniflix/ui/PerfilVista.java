package com.sistema.aniflix.ui;

import com.itextpdf.text.DocumentException;
import com.sistema.aniflix.dao.PerfilDAO;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.FileNotFoundException;

@Component
public class PerfilVista {
    JPanel perfil;
    JTable info;
    JTable entradaSalida;
    JScrollPane sp;

    private final PerfilDAO perfilDAO;

    private String usuario;

    public PerfilVista(final PerfilDAO perfilDAO) {

        this.perfil = new JPanel();
        this.info = new JTable();
        this.entradaSalida = new JTable();
        this.sp = new JScrollPane();
        this.perfilDAO = perfilDAO;
    }

    private void info(){

        String columnas[] = {"ID", "CUI", "Nombre", "Salario", "Departamento", "Correo", "Contraseña", "Ingreso", "Egreso", "Rol"};

        var filas= perfilDAO.listarTabla(usuario);
        info = new JTable(filas,columnas);
        sp = new JScrollPane(info);
        sp.setBounds(10,50,840,50);
        perfil.add(sp);
    }

    private void actualizarInfo(){

        perfil.remove(sp);
        info();
    }

    private void entradaSalida(){

        String columnas[] = {"Nombre", "Correo", "Evento", "Fecha" };

        var filas = perfilDAO.listarEntradaSalida(usuario);
        entradaSalida = new JTable(filas,columnas);
        sp = new JScrollPane(entradaSalida);
        sp.setBounds(10, 130, 840, 300);
        perfil.add(sp);
    }

    private void actualizarEntradaSalida(){

        perfil.remove(sp);
        entradaSalida();
    }

    private void botones(){
        JButton contrasena = new JButton("Cambiar contraseña");
        perfil.setLayout(null);
        contrasena.setBounds(870,50,300,50);
        perfil.add(contrasena);

        contrasena.addActionListener(event -> actualizar());

        JButton exportar = new JButton("Exportar Entradas/Salidas");
        exportar.setBounds(870, 200,300,50);
        perfil.add(exportar);

        exportar.addActionListener(event -> {

            try {
                perfilDAO.pdf(usuario);
            } catch (FileNotFoundException fileNotFoundException) {
                throw  new RuntimeException();
            } catch (DocumentException documentException) {
                throw new RuntimeException();
            }
        });
    }

    private void actualizar(){
        JFrame frameContrasena = new JFrame();
        frameContrasena.setTitle("Cambiar Contraseña");
        frameContrasena.setLocationRelativeTo(null);
        frameContrasena.setBounds(50,175,450,300);
        frameContrasena.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        frameContrasena.add(p1);

        JLabel lbContrasenaActual = new JLabel("Contraseña Actual");
        lbContrasenaActual.setBounds(50,20,150,50);
        p1.add(lbContrasenaActual);

        JTextField edContrasenaActual = new JTextField();
        edContrasenaActual.setBounds(200,35,200,20);
        edContrasenaActual.setText(info.getValueAt(info.getSelectedRow(),6)+"");
        edContrasenaActual.setEnabled(false);
        p1.add(edContrasenaActual);

        JLabel lbNuevaContrasena = new JLabel("Nueva Contraseña");
        lbNuevaContrasena.setBounds(50,80,150,50);
        p1.add(lbNuevaContrasena);

        JTextField edNuevaContrasena = new JTextField();
        edNuevaContrasena.setBounds(200,95,200,20);
        p1.add(edNuevaContrasena);

        JButton bttnCambiar = new JButton("Cambiar Contraseña");
        bttnCambiar.setBounds(150,150,120,40);
        p1.add(bttnCambiar);

        bttnCambiar.addActionListener(event -> {

            try {
                perfilDAO.actualizarContrasenna(edNuevaContrasena.getText(),
                        usuario
                        );

                JOptionPane.showMessageDialog(null, "Cambio Exitoso");
                frameContrasena.setVisible(false);
                actualizarInfo();

            } catch (Exception exception) {

                exception.printStackTrace();
            }
        });
    }

    public void ejecutar(){
        info();
        entradaSalida();
        botones();
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

}
