package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 14/2/17.
 * Tipos de configuraciones para un nuevo oko
 */
public enum TiposConfiguracionNuevoOKO {

    NUEVO(0),
    ESPEJO(1),
    COPIAR_CONTENIDO(2);

    private final int code;

    TiposConfiguracionNuevoOKO(int code) {
        this.code = code;
    }

    public static TiposConfiguracionNuevoOKO getTipoFromInt(int status) {
        switch (status) {
            case 0:
                return NUEVO;
            case 1:
                return ESPEJO;
            case 2:
                return COPIAR_CONTENIDO;
        }
        return NUEVO;
    }

    public int toInt() {
        return code;
    }


}
