package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Enviar Alerta al dispositivo
 */
@Path("/{EnviarAlerta : (?i)enviaralerta}")
public class EnviarAlerta {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> enviarAlerta(@FormParam("apiKey") String apiKey,
                                            @FormParam("uuid") String uuid,
                                            @FormParam("mensaje") String mensaje) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.responseStatus = 0;
            respuestaWS.message = GestionDispositivos.EnviarAlerta(apiKey, uuid, mensaje);
            respuestaWS.error = "";
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = "";
            respuestaWS.error = ex.getMessage();
            return respuestaWS;
        }

    }
}