package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSAutentificacion;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/{AutentificarUsuario : (?i)autentificarusuario}")
public class AutentificarUsuario {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSAutentificacion> autentificarUsuario(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        RespuestaWS<RespuestaWSAutentificacion> respuesta = new RespuestaWS<>();
        try {
            respuesta.responseStatus = 0;
            respuesta.message = GestionUsuarios.AutentificarUsuario(username, password);
            respuesta.error = "";
            return respuesta;
        } catch (OKOBusinessException ex) {
            respuesta.responseStatus = ex.getErrorNumber();
            respuesta.message = null;
            respuesta.error = ex.getMessage();
            return respuesta;
        }
    }
}
