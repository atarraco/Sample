package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import com.cimne.tic.oko.server.ws.util.Seguridad;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 3/3/17.
 * Registrar marco
 */
@Path("/{RegistrarMarco : (?i)registrarmarco}")
public class RegistrarMarco {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> registrarMarco(@FormParam("macAddress") String macAddress,
                                              @FormParam("token") String token,
                                              @FormParam("version") String version,
                                              @HeaderParam("cmp") String cmp) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        String digestCmp = Seguridad.GenerarSHA56(Seguridad.GenerarSHA56("O" + version + "K" + token + "O" + macAddress));
        try {
            if (!cmp.equals(digestCmp)) throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
            GestionDispositivos.RegistrarMarco(macAddress, token, version);
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "OK";
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
