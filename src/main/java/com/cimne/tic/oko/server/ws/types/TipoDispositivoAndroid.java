package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 9/2/17.
 * Tipos de dispositivos al que enviarPushAndroid
 */
public enum TipoDispositivoAndroid {

    APP(1),
    FRAME(0);

    private final int code;

    TipoDispositivoAndroid(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }


}