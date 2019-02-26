package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSUpdate;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 7/2/17.
 * Para descargar las updates de la app o del launcher del marco
 */

@Path("/{CheckUpdates : (?i)checkupdates}")
public class CheckUpdates {
    //String versionNameLauncher, String versionNameOkoFrame, int versionCodeOkoFrame, String serialNumber)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSUpdate> checkUpdates(@FormParam("versionUpdater") String versionUpdater,
                                                       @FormParam("currentVersion") String currentVersion,
                                                       @FormParam("versionCode") String versionCode,
                                                       @FormParam("serialNumber") String serialNumber,
                                                       @FormParam("tipo") int tipo) {
        try {
            return GestionDispositivos.CheckUpdates(versionUpdater, currentVersion, versionCode, serialNumber, tipo);
        } catch (OKOBusinessException ex) {
            RespuestaWS<RespuestaWSUpdate> response = new RespuestaWS<>();
            response.responseStatus = ex.getErrorNumber();
            response.message = null;
            response.error = ex.getMessage();
            return response;
        }
    }

}
