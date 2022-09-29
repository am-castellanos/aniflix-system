package com.sistema.aniflix.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class AdministracionDAO {

    private final DataSource dataSource;

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Autowired
    public AdministracionDAO(final DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public int filtro(String correo, String pass) {
        final var sql = "select * from empleado where correo = ? and contrasena = ?";

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("rol");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {

                    ps.close();
                }

                if (con != null) {

                    con.close();
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }

        return -1;
    }
}
