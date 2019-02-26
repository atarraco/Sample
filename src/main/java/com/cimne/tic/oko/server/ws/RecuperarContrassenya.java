package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("{RecuperarContrassenya : (?i) recuperarcontrassenya}")
public class RecuperarContrassenya {

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> recuperarContrassenya(@FormParam("email") String email) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            GestionUsuarios.RecuperarContrasenya(email, uriInfo.getBaseUri().toString());
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "";
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
