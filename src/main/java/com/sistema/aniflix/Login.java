package com.sistema.aniflix;

import com.sistema.aniflix.dao.AdministracionDAO;
import com.sistema.aniflix.ui.Administrador;
import com.sistema.aniflix.ui.Trabajador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * @author amcc
 */
@Component
public class Login extends JFrame {

    private final AdministracionDAO administracionDao;

    private final Administrador administrador;

    private final Trabajador trabajador;

    JPanel panel = new JPanel();
    JTextField textField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    @Autowired
    public Login(final AdministracionDAO administracionDao,
                 final Administrador administrador,
                 final Trabajador trabajador) {

        this.administracionDao = administracionDao;
        this.administrador = administrador;
        this.trabajador = trabajador;
    }

    private void inicio() {

        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(450, 175, 400, 400);
        setVisible(true);
        panel.setLayout(null);
        add(panel);

        JLabel lbLogin = new JLabel("Log in");
        lbLogin.setBounds(170, 25, 60, 40);
        panel.add(lbLogin);

        JLabel lbCorreo = new JLabel("Correo");
        lbCorreo.setBounds(70, 75, 80, 40);
        panel.add(lbCorreo);

        textField.setBounds(160, 80, 150, 30);
        panel.add(textField);

        JLabel lbContrasena = new JLabel("Contraseña");
        lbContrasena.setBounds(70, 175, 80, 40);
        panel.add(lbContrasena);

        passwordField.setBounds(160, 180, 150, 30);
        panel.add(passwordField);

        JButton bttnIngresar = new JButton("Ingresar");
        bttnIngresar.setBounds(140, 250, 100, 50);
        panel.add(bttnIngresar);

        bttnIngresar.addActionListener(event -> {

            try {

                int respuesta = administracionDao.filtro(textField.getText(), passwordField.getText());

                switch (respuesta) {

                    case 1 -> {

//                        Administrador administrador = new Administrador();
                        administrador.setUsuario(textField.getText());
                        administrador.ejecutar();

                        setVisible(false);
                    }

                    case 0 -> {

//                        Trabajador trabajador = new Trabajador();
                        trabajador.setUsuario(textField.getText());
                        trabajador.ejecutar();

                        setVisible(false);
                    }

                    default -> {

                        JOptionPane.showMessageDialog(null, "Datos incorrectos");

//                        Login login = new Login();
//                        login.remove(panel);
                        this.remove(panel);
                        ejecutar();
                    }
                }

            } catch (Exception exception) {

            }
        });
    }

    public void ejecutar() {
        inicio();
    }

//    public static void main(String[] args) {
//        Login login = new Login();
//        login.ejecutar();
//    }
}
