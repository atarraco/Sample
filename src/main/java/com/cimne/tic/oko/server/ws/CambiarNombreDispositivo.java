package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.model.tables.records.DispositivosvirtualesRecord;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Cambiar nombre del Dispositivo
 */
@Path("/{CambiarNombreDispositivo : (?i)cambiarnombredispositivo}")
public class CambiarNombreDispositivo {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> cambiarNombreDispositivo(@FormParam("uuid") String uuid,
                                                        @FormParam("newName") String newName) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            DispositivosvirtualesRecord dispVirtual = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            dispVirtual.setNombre(newName);
            dispVirtual.store();
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "OK";
            respuestaWS.error = "";
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = "";// "Error";
            respuestaWS.error = ex.getMessage();
            return respuestaWS;
        }
    }
}
