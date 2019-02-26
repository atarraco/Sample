package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{RegistrarUsuarioPush : (?i)registrarusuariopush}")
public class RegistrarUsuarioPush {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> registrarUsuarioPush(
            @FormParam("apiKey") String apiKey,
            @FormParam("token") String token,
            @FormParam("tipo") int tipo) {
        RespuestaWS<String> response = new RespuestaWS<>();
        try {
            GestionUsuarios.RegistroPushMovil(apiKey, token, tipo);
            response.responseStatus = 0;
            response.message = "OK";
            response.error = "";
            return response;
        } catch (OKOBusinessException ex) {
            // Si error GENERICO -1
            response.responseStatus = ex.getErrorNumber();
            response.message = "";
            response.error = ex.getMessage();
            return response;
        }

    }
}
