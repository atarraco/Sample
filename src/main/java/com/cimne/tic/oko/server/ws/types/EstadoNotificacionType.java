package com.cimne.tic.oko.server.ws.types;

public enum EstadoNotificacionType {

    NONE(0),
    PENDING(1),
    READY(2);


    private final int code;

    EstadoNotificacionType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
