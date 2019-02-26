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

@Path("/{RegistrarUsuario : (?i)registrarusuario}")
public class RegistrarUsuario {

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> registrarUsuario(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("usuario") String usuario,
            @FormParam("salt") String salt,
            @FormParam("idioma") String idioma,
            @FormParam("telefono") String telefono) {
        //telefono con el siguiente formato PREFIJO+NUMERO SIN + delante
        // Ex: 34666666666
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.responseStatus = 0;
            respuestaWS.message = GestionUsuarios.InsertarUsuario(email, password, usuario, salt, idioma, telefono, uriInfo.getBaseUri().toString());
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
