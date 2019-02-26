package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 28/1/17.
 */
public enum DispositivoEspejoType {

    NORMAL(0),
    CONTROLADOR(1), //PADRE
    ESPEJO(2);


    private final int code;

    DispositivoEspejoType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
