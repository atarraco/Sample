package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSContactos;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by lorddarks on 13/2/17.
 * Para subir los contactos telefonicos y comprobar los que estan en oko
 */
@Path("/{UploadPhoneContacts : (?i)uploadphonecontacts}")
public class UploadPhoneContacts {

    @POST
    //@Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<List<RespuestaWSContactos>> subirContactos(@FormParam("apikey") String apiKey,
                                                                  @FormParam("jsonString") String jsonString) {

        RespuestaWS<List<RespuestaWSContactos>> respuestaWS = new RespuestaWS<>();
        try {
            respuestaWS.message = GestionUsuarios.UploadPhoneContact(apiKey, jsonString);
            if (respuestaWS.message == null) throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
            respuestaWS.responseStatus = 0;
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
