package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{LogOut : (?i)logout}")
public class LogOut {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> logOut(@FormParam("apiKey") String apiKey) {
        RespuestaWS<String> response = new RespuestaWS<>();
        try {
            response.responseStatus = 0;
            response.message = GestionUsuarios.LogOut(apiKey);
            response.error = "";
            return response;
        } catch (OKOBusinessException ex) {
            response.responseStatus = ex.getErrorNumber();
            response.message = "";
            response.error = ex.getMessage();
            return response;
        }
    }
}
