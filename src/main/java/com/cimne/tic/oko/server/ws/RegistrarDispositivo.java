package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSDispositivos;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{RegistrarDispositivo : (?i)registrardispositivo}")
public class RegistrarDispositivo {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSDispositivos> registrarDispositivo(@FormParam("apikey") String apiKey,
                                                                     @FormParam("macAddress") String macAddress) {
        RespuestaWS<RespuestaWSDispositivos> respuestaWS = new RespuestaWS<>();
        try {
            return GestionDispositivos.registrarDispositivo(apiKey, macAddress);
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = null;
            respuestaWS.error = ex.getMessage();
            return respuestaWS;
        }
    }
}
