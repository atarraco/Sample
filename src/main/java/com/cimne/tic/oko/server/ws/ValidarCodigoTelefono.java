package com.cimne.tic.oko.server.ws;

import com.authy.AuthyApiClient;
import com.authy.api.PhoneVerification;
import com.authy.api.Verification;
import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSTelefono;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lorddarks on 13/2/17.
 * Validar el codigo recibido por sms
 */
@Path("/{ValidarCodigoTelefono : (?i)validarcodigotelefono}")
public class ValidarCodigoTelefono {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSTelefono> validarCodigo(@FormParam("telefono") String telefono,
                                                          @FormParam("counteyCode") String countryCode,
                                                          @FormParam("smsCode") String smsCode) {
        RespuestaWS<RespuestaWSTelefono> respuestaWS = new RespuestaWS<>();
        try {
            AuthyApiClient client = new AuthyApiClient(Constantes.AUTHY_API_KEY);
            PhoneVerification phoneVerification = client.getPhoneVerification();
            Verification verification = phoneVerification.check(telefono, countryCode, smsCode);
            int nuevo = 0;
            if (GestionUsuarios.ExisteTelefonoUsuario(countryCode + telefono))
                nuevo = 1;
            if (!verification.isOk()) throw new OKOBusinessException(OKOErrorType.ERROR_VALIDATING_PHONE);
            respuestaWS.responseStatus = 0;
            respuestaWS.message = new RespuestaWSTelefono(verification.getMessage(), nuevo);
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
