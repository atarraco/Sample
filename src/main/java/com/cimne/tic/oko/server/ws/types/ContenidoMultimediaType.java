package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 1/2/17.
 * Tipos de contenido multimedia
 */
public enum ContenidoMultimediaType {

    None(0),
    Foto(1),
    Video(2),
    Audio(3),
    Alert(4);

    private final int code;

    ContenidoMultimediaType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
