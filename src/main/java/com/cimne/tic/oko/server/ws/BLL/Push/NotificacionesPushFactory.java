package com.cimne.tic.oko.server.ws.BLL.Push;

import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.types.NotificacionesPushFactoryType;
import com.cimne.tic.oko.server.ws.types.TipoDispositivoAndroid;

public final class NotificacionesPushFactory {

    private NotificacionesPushFactory() {
        throw new AssertionError();
    }

    public static INotificacionesPush Crear(NotificacionesPushFactoryType tipo) throws OKOBusinessException {
        switch (tipo) {
            case ANDROID_PUSH_FRAME:
                return new NotificacionesPushAndroid(TipoDispositivoAndroid.FRAME.toInt());

            case ANDROID_PUSH_APP:
                return new NotificacionesPushAndroid(TipoDispositivoAndroid.APP.toInt());

            case IPHONE_PUSH_APP:
                return new NotificacionesPushApple();

            default:
                throw new OKOBusinessException("tipo", -2);
        }
    }
}
