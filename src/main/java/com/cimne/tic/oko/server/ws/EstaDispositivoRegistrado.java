package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSFEstaDispositivoRegistrado;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Comprobar estado del dispositivo
 */
@Path("/{EstaDispositivoRegistrado : (?i)estadispositivoregistrado}")
public class EstaDispositivoRegistrado {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSFEstaDispositivoRegistrado> estaDispositivoRegistrado(@FormParam("macAdress") String macAdress,
                                                                                        @FormParam("nombreMarco") String nombreMarco,
                                                                                        @FormParam("visibilidad") int visibilidad,
                                                                                        @FormParam("localizacionID") int localizacionID) {
        RespuestaWS<RespuestaWSFEstaDispositivoRegistrado> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.responseStatus = 0;
            respuestaWS.message = GestionDispositivos.EstaDispositivoRegistrado(macAdress, nombreMarco, visibilidad, localizacionID);
            respuestaWS.error = "";
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = null;
            respuestaWS.error = ex.getMessage();
            return respuestaWS;
        }
    }
}
