package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSUsuario;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{InformacionUsuario : (?i)informacionusuario}")
public class InformacionUsuario {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSUsuario> informacionUsuario(@FormParam("apiKey") String apiKey) {
        RespuestaWS<RespuestaWSUsuario> response = new RespuestaWS<>();
        try {
            response.responseStatus = 0;
            response.message = GestionUsuarios.InformacionUsuario(apiKey);
            response.error = "";
            return response;
        } catch (OKOBusinessException ex) {
            response.responseStatus = ex.getErrorNumber();
            response.message = null;
            response.error = ex.getMessage();
            return response;
        }
    }
}
