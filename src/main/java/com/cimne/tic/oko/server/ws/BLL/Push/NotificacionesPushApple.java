package com.cimne.tic.oko.server.ws.BLL.Push;

import com.cimne.tic.oko.server.ws.BLL.GestionCola;
import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.types.PlataformaPushType;

import java.util.UUID;

public class NotificacionesPushApple implements INotificacionesPush {

    public String uuid = "";
    public String nombreMarco = "";


    @Override
    public Boolean EnviarPush(String token, String mensaje, int contenidoMultimediaId, int estadoContenidoMultimediaID) throws OKOBusinessException {

        //identificador unico de la push
        String mensajeId = UUID.randomUUID().toString();
        //creamos el mensaje para APN
        String mensajeJSON = "{";
        mensajeJSON += "\"aps\": " + "{ \"alert\" : {" + mensaje + "},\"badge\":1},";
        mensajeJSON += "\"idTipoMensaje\": " + String.valueOf(contenidoMultimediaId) + "\",";
        mensajeJSON += ",\"uuid\" :\"" + uuid + "\",";
        mensajeJSON += ",\"nombre\": \"" + nombreMarco + "\"}";

        GestionContenido.UpdatePushMessageIDinCM(mensajeId, contenidoMultimediaId, estadoContenidoMultimediaID);
        GestionCola.InsertarMensajeCola(token, PlataformaPushType.IPHONE.toInt(), PlataformaPushType.IPHONE.toInt(), mensajeJSON, mensajeId);
        return true;
    }


}
