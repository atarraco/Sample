package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSNotificacion;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/{ListarNotificaciones : (?i)listarnotificaciones}")
public class ListarNotificaciones {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<List<RespuestaWSNotificacion>> listarNotificaciones(@FormParam("apikey") String apiKey) {
        RespuestaWS<List<RespuestaWSNotificacion>> response = new RespuestaWS<List<RespuestaWSNotificacion>>();
        try {
            response.responseStatus = 0;
            response.message = GestionUsuarios.ListarNofitifaciones(apiKey);
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
