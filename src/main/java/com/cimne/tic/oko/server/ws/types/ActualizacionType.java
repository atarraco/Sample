package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 7/2/17.
 * Tipos de acutalizacion posibles
 */
public enum ActualizacionType {

    None(0),
    Frame(1),
    Launcher(2);
    private final int code;

    ActualizacionType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
