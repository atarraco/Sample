package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 2/2/17.
 * Eliminar all Contenido
 */
@Path("/{EliminarTodoElContenido : (?i)eliminartodoelcontenido}")
public class EliminarTodoElContenido {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> eliminarTodoElContenido(@FormParam("uuid") String uuid,
                                                       @FormParam("password") String password) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            if (!GestionUsuarios.AutentificarUsuarioFrame(uuid, password))
                throw new OKOBusinessException(OKOErrorType.LOGIN_ERROR);
            if (!GestionContenido.EliminarTodoElContenido(uuid))
                throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "OK";
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
