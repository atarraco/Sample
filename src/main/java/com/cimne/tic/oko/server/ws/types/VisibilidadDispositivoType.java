package com.cimne.tic.oko.server.ws.types;

public enum VisibilidadDispositivoType {

    None(0),
    Privado(1),
    Publico(2),
    Grupo(3);

    private final int code;

    VisibilidadDispositivoType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
