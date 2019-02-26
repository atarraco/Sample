package com.cimne.tic.oko.server.ws.BLL.Push;

import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;

public interface INotificacionesPush {

    //  Enviar
    Boolean EnviarPush(String token, String mensaje, int contenidoMultimediaId, int estadoContenidoMultimediaID) throws OKOBusinessException;

}
