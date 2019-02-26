package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lorddarks on 7/2/17.
 * Obtener la hora
 */
@Path("/{ObtenerHora : (?i)obtenerhora}")
public class ObtenerHora {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> obtenerHora() {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        respuestaWS.message = df.format(new Date());
        respuestaWS.responseStatus = 0;
        respuestaWS.error = "";
        return respuestaWS;
    }
}
