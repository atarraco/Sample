package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{EliminarContenido : (?i)eliminarcontenido}")
public class EliminarContenido {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> eliminaContenido(@FormParam("apikey") String apiKey, @FormParam("contenido_id") int contenido_id) {
        RespuestaWS<String> response = new RespuestaWS<>();
        try {
            if (!GestionContenido.EliminarContenido(apiKey, contenido_id, true))
                throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            response.responseStatus = 0;
            response.message = "OK";
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
