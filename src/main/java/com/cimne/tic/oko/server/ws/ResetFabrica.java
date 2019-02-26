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
 * Created by lorddarks on 2/2/17.
 * Resetear marco a estado salido de fabrica
 */

@Path("/{ResetFabrica : (?i)resetfabrica}")
public class ResetFabrica {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> resetFabrica(@FormParam("uuid") String uuid) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            if (!GestionDispositivos.EliminarMarco(uuid)) throw new OKOBusinessException("Error eliminando marco", -2);
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "OK";
            respuestaWS.error = "";
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = "";
            respuestaWS.error = "Error";
            return respuestaWS;
        }
    }

}
