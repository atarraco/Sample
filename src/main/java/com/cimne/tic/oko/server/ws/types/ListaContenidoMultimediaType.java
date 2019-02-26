package com.cimne.tic.oko.server.ws.types;

public enum ListaContenidoMultimediaType {

    None(0),
    ContenidoRecibido(1),
    ContenidoEnviado(2),
    ContenidoOrdenaoPorTipo(3);

    private final int code;

    ListaContenidoMultimediaType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }


}
