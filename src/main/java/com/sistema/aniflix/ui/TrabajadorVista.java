package com.sistema.aniflix.ui;

import com.sistema.aniflix.dao.TrabajadorDAO;
import com.sistema.aniflix.domain.type.TipoEvento;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TrabajadorVista extends JFrame {
    JPanel trabajador = new JPanel();

    private JLabel lbTimer;

    private final Timer timer;

    private String usuario;

    private final TrabajadorDAO trabajadorDao;
    private final ESVista esVista;

    public TrabajadorVista() {

        this.lbTimer = new JLabel("");
        this.timer = new Timer(1000, event -> tick());
        this.trabajadorDao = new TrabajadorDAO();
        this.esVista = new ESVista();
    }

    public void ejecutar() {
        botones();
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private void tick() {
        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        lbTimer.setText(formatter.format(Instant.now()));
    }

    private void botones() {
        JLabel lbUsuario = new JLabel("Bienvenid@ - " + usuario);
        lbUsuario.setBounds(330, 50, 700, 40);
        lbUsuario.setFont(new Font("Monserrat",Font.BOLD+Font.ITALIC,30));
        trabajador.add(lbUsuario);
        //jf.setTitle("Bienvenid@ - " + usuario);
        setLocationRelativeTo(null);

        timer.start();

        lbTimer.setBounds(290, 120, 1000, 60);
        lbTimer.setFont(new Font("Monserrat",Font.BOLD+Font.ITALIC,60));
        trabajador.add(lbTimer);

        JButton iniciar = new JButton("Iniciar Jornada");
        iniciar.addActionListener(event -> {

            trabajadorDao.guardarEvento(usuario, TipoEvento.ENTRADA);

            JOptionPane.showMessageDialog(null, "Jornada Iniciada");
        });
        trabajador.setLayout(null);
        iniciar.setBounds(250, 250, 300, 50);
        trabajador.add(iniciar);

        JButton finalizar = new JButton("Finalizar Jornada");
        finalizar.addActionListener(event -> {

            trabajadorDao.guardarEvento(usuario, TipoEvento.SALIDA);

            JOptionPane.showMessageDialog(null, "Jornada Finalizada");
        });
        finalizar.setBounds(630, 250, 300, 50);
        trabajador.add(finalizar);
    }
}
