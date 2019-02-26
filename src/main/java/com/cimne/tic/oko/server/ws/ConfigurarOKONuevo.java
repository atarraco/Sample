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
 * Created by lorddarks on 14/2/17.
 * Para configurar el dispositivo una vez vinculado
 */
@Path("/{ConfigurarOKONuevo : (?i)configurarokonuevo}")
public class ConfigurarOKONuevo {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> configurarNuevoOKO(@FormParam("apikey") String apiKey,
                                                  @FormParam("uuid") String uuid,
                                                  @FormParam("tipo") int tipo,
                                                  @FormParam("uuidDispExistente") String uuidDispExistente) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.message = GestionDispositivos.ConfigurarNuevoOKO(apiKey, uuid, tipo, uuidDispExistente);
            respuestaWS.responseStatus = 0;
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
