package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.tables.records.DispositivosvirtualesRecord;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import com.cimne.tic.oko.server.ws.util.Fechas;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 1/2/17.
 * Cambiar Privacidad Dispositivo
 */
@Path("/{CambiarControlContenido : (?i)cambiarcontrolcontenido}")
public class CambiarControlContenido {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> cambiarControlContenido(@FormParam("apiKey") String apiKey,
                                                       @FormParam("uuid") String uuid,
                                                       @FormParam("privacidad") String privacidad) {
        RespuestaWS<String> respuestaWS = new RespuestaWS<>();
        try {
            DispositivosvirtualesRecord dispVirtual = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            if (!dispVirtual.fetchParent(Keys.FK_USUARIOSPROPIETARIOS).getApikey().equals(apiKey))
                throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            dispVirtual.setFechamodificacion(Fechas.GetCurrentTimestampLong());
            dispVirtual.setControlcontenido(Integer.valueOf(privacidad));
            dispVirtual.store();
            respuestaWS.responseStatus = 0;
            respuestaWS.message = "Provacidad cambiada";
            respuestaWS.error = "";
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.error = ex.getMessage();
            respuestaWS.message = "";
            return respuestaWS;
        }
    }


}
