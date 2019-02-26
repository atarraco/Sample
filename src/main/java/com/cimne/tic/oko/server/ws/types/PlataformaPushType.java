package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 9/2/17.
 * tipo de plataforma para enviar push
 */
public enum PlataformaPushType {

    ANDROID(1),
    IPHONE(2);

    private final int code;

    PlataformaPushType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
