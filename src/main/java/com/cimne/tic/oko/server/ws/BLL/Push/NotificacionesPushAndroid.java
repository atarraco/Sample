package com.cimne.tic.oko.server.ws.BLL.Push;

import com.cimne.tic.oko.server.ws.BLL.GestionCola;
import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.types.PlataformaPushType;
import com.cimne.tic.oko.server.ws.types.TipoDispositivoAndroid;

import java.util.UUID;

/**
 * Created by lorddarks on 9/2/17.
 * Genera e pone en cola las notificaciones para android
 */
public class NotificacionesPushAndroid implements INotificacionesPush {

    private int tipoDispositivo;

    public NotificacionesPushAndroid(int tipoD) {
        tipoDispositivo = tipoD;
    }

    @Override
    public Boolean EnviarPush(String token, String mensaje, int contenidoMultimediaId, int estadoContenidoMultimediaID) throws OKOBusinessException {

        String mensajeId = UUID.randomUUID().toString();

        String mensajeJSON = "{";
        mensajeJSON += "\"data\": " + "{ \"message\" : " + mensaje + "}";
        mensajeJSON += ",\"message_id\": \"" + mensajeId + "\"";
        mensajeJSON += ",\"priority\" : \"high\"";
        mensajeJSON += ",\"to\": \"" + token + "\"";
        mensajeJSON += ",\"delivery_receipt_requested\": true";
        mensajeJSON += "}";

        GestionContenido.UpdatePushMessageIDinCM(mensajeId, contenidoMultimediaId, estadoContenidoMultimediaID);
        if (tipoDispositivo == TipoDispositivoAndroid.APP.toInt()) {
            GestionCola.InsertarMensajeCola(token, TipoDispositivoAndroid.APP.toInt(), PlataformaPushType.ANDROID.toInt(), mensajeJSON, mensajeId);
        } else {
            GestionCola.InsertarMensajeCola(token, TipoDispositivoAndroid.FRAME.toInt(), PlataformaPushType.ANDROID.toInt(), mensajeJSON, mensajeId);
        }
        return true;
    }
}
