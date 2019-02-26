package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSContenidoMultimedia;
import com.cimne.tic.oko.server.ws.types.ListaContenidoMultimediaType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Restaurar Marco a estado anterior
 */
@Path("({RestaurarMarco : (?i)restaurarmarco}")
public class RestaurarMarco {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSContenidoMultimedia> restaurarMarco(@FormParam("uuid") String uuid,
                                                                      @FormParam("apiKey") String apiKey) {
        RespuestaWS<RespuestaWSContenidoMultimedia> response = new RespuestaWS<>();
        try {
            response.responseStatus = 0;
            response.message = GestionContenido.ListarContenidoMultimedia(uuid, apiKey, ListaContenidoMultimediaType.ContenidoOrdenaoPorTipo);
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
