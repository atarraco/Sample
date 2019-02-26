package com.cimne.tic.oko.server.ws.BLL;

import com.cimne.tic.oko.server.ws.model.tables.records.NotificacionespushRecord;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import com.cimne.tic.oko.server.ws.util.Fechas;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.cimne.tic.oko.server.ws.model.Tables.NOTIFICACIONESPUSH;

/**
 * Created by lorddarks on 9/2/17.
 * Gestion de la cola de push
 */
public class GestionCola {

    public static List<NotificacionespushRecord> ObtenerMensajeCola() throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            List<NotificacionespushRecord> notificaciones = create.select().from(NOTIFICACIONESPUSH)
                    .where(NOTIFICACIONESPUSH.ESTADO.equal(0))
                    .fetchInto(NotificacionespushRecord.class);
            create.close();
            conn.close();
            return notificaciones;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void InsertarMensajeCola(String token, int tipoDevice, int plataforma, String mensaje, String idMensaje) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            NotificacionespushRecord notificacion = create.newRecord(NOTIFICACIONESPUSH);
            notificacion.setToken(token);
            notificacion.setMensaje(mensaje);
            notificacion.setIdmensaje(idMensaje);
            notificacion.setTipodispositivo(tipoDevice);
            notificacion.setPlataforma(plataforma);
            notificacion.setEstado(0);
            notificacion.setFechainsercion(Fechas.GetCurrentTimestampLong());
            notificacion.setFechalanzamiento(Fechas.GetCurrentTimestampLong());
            notificacion.store();
            create.close();
            conn.close();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void ModificarMensajeCola(int notificacionesPushId) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            NotificacionespushRecord notificacion = create.select().from(NOTIFICACIONESPUSH)
                    .where(NOTIFICACIONESPUSH.IDNOTIFICACIONPUSH.equal(notificacionesPushId))
                    .fetchAnyInto(NotificacionespushRecord.class);

            if (notificacion != null) {
                notificacion.setFechalanzamiento(Fechas.GetCurrentTimestampLong());
                notificacion.setEstado(1);
                notificacion.setFechainsercion(Fechas.GetCurrentTimestampLong());
                notificacion.store();
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }
}
