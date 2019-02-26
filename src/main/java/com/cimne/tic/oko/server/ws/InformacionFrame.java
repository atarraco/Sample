package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSFrameInfo;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Informacion del marco
 */
@Path("/{InformacionFrame : (?i)informacionframe}")
public class InformacionFrame {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSFrameInfo> informacionFrame(@FormParam("uuid") String uuid) {
        RespuestaWS<RespuestaWSFrameInfo> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.responseStatus = 0;
            respuestaWS.message = GestionDispositivos.GetFrame(uuid);
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
