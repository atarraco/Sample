package com.cimne.tic.oko.server.ws.types;

public enum EstadoVinculacionType {

    None(-1),
    Insert_APP(0),
    FinishOK_Frame(1),
    Vinculado(2),
    Desvinculado(3),
    DesvinculadoCompleted(4),
    EsperandoConfiguracion(5);

    private final int code;

    EstadoVinculacionType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
