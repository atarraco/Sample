package com.cimne.tic.oko.server.ws;

import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.PhoneVerification;
import com.authy.api.Verification;
import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 13/2/17.
 * Clase para pedir que se valide el telefono mandado un codigo SMS/llamada
 */
@Path("/{ValidarTelefono : (?i)validartelefono}")
public class ValidarTelefono {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<String> validarTelefono(@FormParam("telefono") String telefono,
                                               @FormParam("countryCode") String countryCode,
                                               @FormParam("idioma") String idioma,
                                               @DefaultValue("sms") @FormParam("tipo") String tipo) {
        RespuestaWS<String> response = new RespuestaWS<>();
        try {
            AuthyApiClient client = new AuthyApiClient(Constantes.AUTHY_API_KEY);
            PhoneVerification phoneVerification = client.getPhoneVerification();
            Params params = new Params();
            params.setAttribute("locale", idioma);
            params.setAttribute("code_length", "6");
            Verification verification = phoneVerification.start(telefono, countryCode, tipo, params);
            if (!verification.isOk()) throw new OKOBusinessException(OKOErrorType.ERROR_VALIDATING_PHONE);
            response.responseStatus = 0;
            response.message = verification.getMessage();
            response.error = "";
            return response;
        } catch (OKOBusinessException ex) {
            response.responseStatus = ex.getErrorNumber();
            response.message = "";
            response.error = ex.getMessage();
            return response;
        }
    }
}
