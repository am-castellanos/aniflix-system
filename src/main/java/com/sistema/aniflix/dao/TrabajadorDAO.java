package com.sistema.aniflix.dao;

import com.sistema.aniflix.domain.type.TipoEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Objects.nonNull;

public class TrabajadorDAO {

    private static final String AGREGAR_EVENTO = "insert into empleado_evento (empleado_id, evento_id, fecha) " +
            " values (?, ?, ?)";

    private static final String OBTENER_EMPLEADO_ID = "select id_empleado from empleado where correo = ?";

    private static final String OBTENER_TIPO = "select id_evento from evento where tipo = ?";

//    SELECT e.nombre, e.correo, ev.tipo, ee.fecha
//    FROM `empleado_evento` ee
//    INNER JOIN empleado e on ee.empleado_id = e.id_empleado
//    inner JOIN evento ev on ee.evento_id = ev.id_evento
//    where e.correo = ?


    private final Conexion conexion;

    public TrabajadorDAO() {

        this.conexion = new Conexion();
    }

    public boolean guardarEvento(final String usuario, final TipoEvento tipoEvento) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {

            final var empleadoId = obtenerEmpleadoId(usuario);
            final var eventoId = obtenerEventoId(tipoEvento);

            if ((empleadoId < 0) || (eventoId < 0)) {
                return false;
            }

            cn = conexion.conectar();
            ps = cn.prepareStatement(AGREGAR_EVENTO);
            ps.setLong(1, empleadoId);
            ps.setLong(2, eventoId);
            ps.setTimestamp(3, new Timestamp(Instant.now().toEpochMilli()));

            ps.executeUpdate();

            return true;
        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {
            try {
                if (nonNull(ps)) {

                    ps.close();
                }

                if (nonNull(cn)) {

                    cn.close();
                }
            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

        return false;
    }

    public long obtenerEmpleadoId(final String correo) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            cn = conexion.conectar();
            ps = cn.prepareStatement(OBTENER_EMPLEADO_ID);
            ps.setString(1, correo);

            rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getLong("id_empleado");
            }
        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {
            try {
                if (nonNull(rs)) {

                    rs.close();
                }

                if (nonNull(ps)) {

                    ps.close();
                }

                if (nonNull(cn)) {

                    cn.close();
                }
            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

        return -1;
    }

    public long obtenerEventoId(final TipoEvento tipoEvento) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            cn = conexion.conectar();
            ps = cn.prepareStatement(OBTENER_TIPO);
            ps.setString(1, tipoEvento.getValor());

            rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getLong("id_evento");
            }
        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {
            try {
                if (nonNull(rs)) {

                    rs.close();
                }

                if (nonNull(ps)) {

                    ps.close();
                }

                if (nonNull(cn)) {

                    cn.close();
                }
            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

        return -1;
    }
}
