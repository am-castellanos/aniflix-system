package com.sistema.aniflix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class AniflixApplication extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(AniflixApplication.class);

    public static void main(String[] args) {

        final var context = new SpringApplicationBuilder(AniflixApplication.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {

            LOGGER.info("Loading initial screen");
            final var login = context.getBean(Login.class);
            login.ejecutar();
        });
    }

}
