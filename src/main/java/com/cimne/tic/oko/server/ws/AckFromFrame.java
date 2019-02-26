package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.EstadocontenidomultimediaRecord;
import com.cimne.tic.oko.server.ws.types.EstadoContenidoMultimediaType;
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

/**
 * Created by lorddarks on 7/2/17.
 * Clase para recibir los ACK del marco al recibir la push de google
 */
@Path("/{AckFromFrame : (?i)ackfromframe}")
public class AckFromFrame {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> ackFromFrame(@FormParam("idContenidoMultimedia") String idContenidoMultimedia,
                                            @FormParam("uuid") String uuid) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            EstadocontenidomultimediaRecord estadoCM = create.select().from(Tables.ESTADOCONTENIDOMULTIMEDIA).innerJoin(Tables.DISPOSITIVOSVIRTUALES).on(Tables.ESTADOCONTENIDOMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(Tables.DISPOSITIVOSVIRTUALES.IDDISPOSITIVOVIRTUAL)).where(Tables.ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(Integer.parseInt(idContenidoMultimedia))).and(Tables.DISPOSITIVOSVIRTUALES.UUID.equal(uuid)).and(Tables.ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Ack_From_Google.toInt())).fetchAnyInto(EstadocontenidomultimediaRecord.class);
            if (estadoCM != null) {
                estadoCM.setEstado(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt());
                estadoCM.store();
                respuestaWS.responseStatus = 0;
                respuestaWS.message = "OK";
                respuestaWS.error = "";
            } else {
                respuestaWS.responseStatus = -1;
                respuestaWS.message = "";
                respuestaWS.error = "NO OK";
            }
            create.close();
            conn.close();
            return respuestaWS;
        } catch (Exception ex) {
            respuestaWS.responseStatus = OKOErrorType.GENERIC_ERROR.getCode();
            respuestaWS.message = "";
            respuestaWS.error = OKOErrorType.GENERIC_ERROR.getMessage();
            return respuestaWS;
        }
    }
}
