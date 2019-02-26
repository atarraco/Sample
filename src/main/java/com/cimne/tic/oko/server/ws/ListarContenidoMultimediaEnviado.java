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

@Path("/{ListarContenidoMultimediaEnviado : (?i)listarcontenidomultimediaenviado}")
public class ListarContenidoMultimediaEnviado {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSContenidoMultimedia> listarContenidoMultimediaEnviado(@FormParam("apikey") String apiKey, @FormParam("uuid") String uuid) {
        RespuestaWS<RespuestaWSContenidoMultimedia> response = new RespuestaWS<>();
        try {
            response.responseStatus = 0;
            response.message = GestionContenido.ListarContenidoMultimedia(apiKey, uuid, ListaContenidoMultimediaType.ContenidoEnviado);
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
