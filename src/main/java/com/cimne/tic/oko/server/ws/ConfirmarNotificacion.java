package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.NotificacionesRecord;
import com.cimne.tic.oko.server.ws.types.EstadoNotificacionType;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;

@Path("/{ConfirmarNotificacion : (?i)confirmarnotificacion}")
public class ConfirmarNotificacion {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> confirmarNotificacion(@FormParam("notificacionID") int notificacionID,
                                                     @FormParam("apikey") String apiKey,
                                                     @FormParam("userGuid") String userGuid,
                                                     @FormParam("tipo") int tipo) {
        RespuestaWS<String> response = new RespuestaWS<>();
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            NotificacionesRecord notificacion = create.select()
                    .from(Tables.NOTIFICACIONES)
                    .where(Tables.NOTIFICACIONES.IDNOTIFICACION.equal(notificacionID))
                    .and(Tables.NOTIFICACIONES.STATUS.equal(EstadoNotificacionType.PENDING.toInt()))
                    .fetchAnyInto(NotificacionesRecord.class);
            if (notificacion == null) throw new OKOBusinessException(OKOErrorType.NOTIFICATION_NOT_FOUND);
            notificacion.setStatus(EstadoNotificacionType.READY.toInt());
            notificacion.store();
            create.close();
            conn.close();
            response.responseStatus = 0;
            response.message = "";
            return response;
        } catch (OKOBusinessException ex) {
            response.responseStatus = ex.getErrorNumber();
            response.message = "";
            response.error = ex.getMessage();
            return response;
        } catch (Exception ex) {
            response.responseStatus = OKOErrorType.GENERIC_ERROR.getCode();
            response.message = "";
            response.error = OKOErrorType.GENERIC_ERROR.getMessage();
            return response;
        }
    }

}
